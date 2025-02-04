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