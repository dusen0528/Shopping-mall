<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
    <c:forEach items="${products}" var="product">
        <div class="col">
            <div class="card shadow-sm">
                <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 썸네일" preserveAspectRatio="xMidYMid slice" focusable="false"><title>썸네일</title><rect width="100%" height="100%" fill="#55595c"></rect><text x="50%" y="50%" fill="#eceeef" dy=".3em">${product.productName}</text></svg>

                <div class="card-body">
                    <p class="card-text">${product.productName}</p>
                    <p class="card-text">가격: ${product.productPrice}원</p>
                    <div class="d-flex justify-content-between align-items-center">
                        <div class="btn-group">
                            <button type="button" class="btn btn-sm btn-outline-secondary">상세보기</button>
                            <button type="button" class="btn btn-sm btn-outline-secondary">장바구니에 추가</button>
                        </div>
                        <small class="text-muted">재고: ${product.productStock}</small>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
