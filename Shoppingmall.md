# Shoppingmall Project

## 1. 애플리케이션 시작 단계
1. 서버 시작시 Application Listener 실행
2. WepAppInitializer 실행되어 모든 컨트롤러 초기화 후 ControllerFactory에 등록
```java
@Slf4j
@HandlesTypes(
        value = {
                BaseController.class
        }
)
public class WebAppInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        ControllerFactory controllerFactory = new ControllerFactory();
        controllerFactory.initialize(c,ctx);
    }
}
```
3. PointThreadInitializer가 실행되어 포인트 처리를 위한 RequestChannel, WorkerThread 초기화

## 2. 요청 처리 단계 (사용자가 웹 페이지 접속시)

> 브라우저 요청 → 필터체인 → FrontServlet → Controller → View → 응답

#### 필터체인처리
- CharacterEncodingFilter : 모든 요청 인코딩 UTF-8로 설정
- WelcomePageFilter : "/" 요청을 "index.do"로 리다이렉트
- LoginCheckFilter : "/mypage/*" 접근 시 로그인 체크
- AdminCheckFilter : "/admin/*" 접근 시 관리자 권한 체크

#### FrontServlet 처리
- 모든 *.do 요청을 받아 처리
- URL, HTTP METHOD 분석 후 ControllerFactory에 적절한 컨트롤러를 찾음
- 해당 컨트롤러가 @Transactional 어노테이션이 있으면 TransactionalControllerProxy로 감싸서 트랜잭션 처리

#### Controller 실행
- 컨트롤러의 execute()메소드 실행
- 필요시 DbConnectionThreadLocal을 통해 DB 작업 수행 
- 비즈니스 로직 처리 후 적절한 뷰 이름 반환

#### View 처리
- ViewResolver가 컨트롤러가 반환한 뷰 이름을 실제 JSP 경로로 변환
- 해당 JSP 실행후 응답 생성 

## 3. 특수 케이스 : 로그인 프로세스
> 로그인 요청 → LoginController → LoginPostController → 세션 생성 → 메인 페이지

- 로그인 페이지 접속 시 LoginController 실행 
- 로그인 폼 제출 시 LoginPostController가 처리  
- 인증 성공 시 세션에 사용자 정보 저장 
- index.do로 리다이렉트


---
- 필터로 공통 관심사 처리 (인코딩, 인증/인가)
- MVC 패턴으로 관심사 분리
- 트랜잭션 처리로 데이터 일관성 보장

## TransactionalControllerProxy
```java
public class TransactionalControllerProxy implements BaseController {

    private final BaseController controller;

    public TransactionalControllerProxy(BaseController baseController){
        controller =  baseController;

    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if(controller.getClass().isAnnotationPresent(Transactional.class)){
            try{
                DbConnectionThreadLocal.initialize();
                return controller.execute(req, resp);
            } catch (Exception e) {
                DbConnectionThreadLocal.setSqlError(true);
                throw e;
            }finally {
                DbConnectionThreadLocal.reset();
            }
        }
        return controller.execute(req, resp);
    }
}
```
이를 통해 트랜잭션 작업이 필요한 Controller에 @Transactional 어노테이션을 달아서 사용

---
# 쇼핑몰 기능 구현 요구사항

## 🔧 기본 구현 사항

| 구분 | 요구사항 | 세부내용 |
|------|----------|----------|
| Pagination | 페이지 번호 규칙 | • 1부터 시작<br>• Page.java 기반으로 구현 |
| View | JSP 구현 규칙 | • FrontServlet으로만 호출<br>• 과도한 Scriptlet 사용 금지 |

## 👥 회원 관리

| 기능 | 요구사항 | 세부내용 |
|------|----------|----------|
| 로그인 | 사용자 인증 | • ID/PW 기반 로그인<br>• 세션 60분 유지<br>• SQL Injection 방어 |
| 회원가입 | 초기 포인트 | • 가입 시 100만 포인트 지급 |

## 🛍️ 상품 관리

| 기능 | 요구사항 | 세부내용 |
|------|----------|----------|
| 관리자 기능 | CRUD 작업 | • 상품 등록/수정/삭제 |
| 카테고리 | 필수 요구사항 | • 모든 상품은 카테고리 필수 지정 |
| 이미지 | 이미지 처리 | • 이미지 업로드 기능<br>• 미등록시 no-image.png 표시 |

## 🔄 최근 본 상품

| 기능 | 요구사항 | 세부내용 |
|------|----------|----------|
| 표시 | 노출 규칙 | • index 페이지에 5개 노출<br>• 비로그인도 확인 가능 |
| 구현 | 구현 방식 | • Cookie/Session/DB 중 선택<br>• 장단점 고려하여 결정 |

## 🛒 장바구니

| 기능 | 요구사항 | 세부내용 |
|------|----------|----------|
| 구현 방식 | 세션 기반 | • Session 기반으로 구현 |
| 수량 관리 | 수량 변경 | • 수량 변경 가능<br>• 중복 등록 불가 |
| 주문 연동 | 주문 처리 | • 주문 완료시 장바구니에서 삭제 |

## 📦 주문 처리

| 기능 | 요구사항 | 세부내용 |
|------|----------|----------|
| 포인트 적립 | 처리 방식 | • 독립 Thread에서 처리<br>• 실패시 error 로그 기록<br>• 실패해도 주문은 정상 처리 |
| 오류 처리 | 주문 제한 | • 수량 부족시 주문 불가<br>• 포인트 부족시 주문 불가 |

## 💰 포인트 시스템

| 기능 | 요구사항 | 세부내용 |
|------|----------|----------|
| 적립 | 적립 규칙 | • 주문 금액의 10% 적립 |
| 이력 관리 | 조회 기능 | • 포인트 사용 이력 조회 가능 |