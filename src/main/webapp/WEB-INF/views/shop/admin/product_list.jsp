<!-- /WEB-INF/views/shop/admin/product_list.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container py-5">
    <h2>상품 관리</h2>
    <div class="mb-3">
        <a href="/admin/product/add.do" class="btn btn-primary">상품 추가</a>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>이미지</th>
            <th>상품명</th>
            <th>가격</th>
            <th>재고</th>
            <th>상태</th>
            <th>관리</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.productId}</td>
                <td>
                    <img src="${empty product.productImage ? '/resources/no-image.png' : product.productImage}"
                         alt="${product.productName}" width="50">
                </td>
                <td>${product.productName}</td>
                <td>${product.productPrice}</td>
                <td>${product.productStock}</td>
                <td>${product.productStatus}</td>
                <td>
                    <a href="/admin/product/edit.do?id=${product.productId}"
                       class="btn btn-sm btn-primary">수정</a>
                    <a href="/admin/product/delete.do?id=${product.productId}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>