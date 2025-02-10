<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
    <c:if test="${sessionScope.userRole == 'ROLE_ADMIN'}">
        <div class="admin-controls mb-3">
            <a href="/admin/product/add.do" class="btn btn-primary">상품 추가</a>
            <a href="/admin/products.do" class="btn btn-primary">상품 관리</a>
            <a href="/admin/orders.do" class="btn btn-primary">주문 관리</a>
            <a href="/admin/users.do" class="btn btn-primary">회원 관리</a>
        </div>
    </c:if>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <c:choose>
            <c:when test="${not empty products}">
                <c:forEach var="product" items="${products}">
                    <div class="col">
                        <div class="card shadow-sm">
                            <img src="${empty product.productImage ? '/resources/no-image.png' : product.productImage}"
                                 class="card-img-top" alt="${product.productName}" height="225">
                            <div class="card-body">
                                <h5 class="card-title">${product.productName}</h5>
                                <p class="card-text">가격: ${product.productPrice}원</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <div class="btn-group">
                                        <c:if test="${not empty sessionScope.user}">
                                            <form action="/cart/add.do" method="post" class="d-inline">
                                                <input type="hidden" name="productId" value="${product.productId}">
                                                <input type="hidden" name="quantity" value="1">
                                                <button type="submit" class="btn btn-sm btn-outline-secondary">장바구니</button>
                                            </form>
                                        </c:if>
                                        <a href="/product/detail.do?id=${product.productId}"
                                           class="btn btn-sm btn-outline-secondary">상세보기</a>
                                    </div>
                                    <small class="text-muted">재고: ${product.productStock}</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="col-12 text-center">
                    <p>등록된 상품이 없습니다.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>