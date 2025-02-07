<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${sessionScope.user.userAuth == 'ROLE_ADMIN'}">
    <div class="admin-controls mb-3">
        <a href="/admin/product/add.do" class="btn btn-primary">상품 추가</a>
    </div>
</c:if>

<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
    <div class="col">
        <div class="card shadow-sm">
            <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 썸네일" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"></rect><text x="50%" y="50%" fill="#eceeef" dy=".3em">썸네일</text></svg>

            <div class="card-body">
                <p class="card-text">이것은 추가 콘텐츠로 이어지는 자연스러운 도입부로 지원 텍스트가 있는 더 넓은 카드입니다. 이 내용은 조금 더 깁니다.</p>
                <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                        <button type="button" class="btn btn-sm btn-outline-secondary">보기</button>
                        <button type="button" class="btn btn-sm btn-outline-secondary">수정</button>
                    </div>
                    <small class="text-muted">9분 전</small>
                </div>
            </div>
        </div>
    </div>
    <!-- 추가 8개의 제품 카드를 위와 동일한 구조로 반복 -->
</div>
