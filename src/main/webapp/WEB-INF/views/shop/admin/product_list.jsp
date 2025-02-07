<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container mt-4">
    <c:choose>
        <c:when test="${empty products}">
            <div class="alert alert-warning">등록된 상품이 없습니다</div>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <c:forEach items="${products}" var="product">
                    <div class="col">
                        <div class="card h-100 shadow">
                            <img src="${product.productImage}" class="card-img-top" alt="${product.productName}" style="height: 250px; object-fit: cover;">
                            <div class="card-body">
                                <h5 class="card-title">${product.productName}</h5>
                                <p class="card-text">
                                    <span class="badge bg-primary">${product.categoryId}</span>
                                    <c:if test="${product.productStock < 5}">
                                        <span class="badge bg-danger ms-2">품절 임박</span>
                                    </c:if>
                                </p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <fmt:formatNumber value="${product.productPrice}" type="currency" currencySymbol="₩"/>
                                    <small class="text-muted">재고: ${product.productStock}</small>
                                </div>
                            </div>
                            <div class="card-footer bg-transparent">
                                <div class="d-grid gap-2">
                                    <a href="/admin/product/edit.do?id=${product.productId}" class="btn btn-outline-secondary btn-sm">수정</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>
