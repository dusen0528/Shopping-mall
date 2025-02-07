<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-4">
  <h2 class="mb-4">상품 등록</h2>
  <form action="/admin/product/add.do" method="post" enctype="multipart/form-data">
    <div class="row g-3">
      <div class="col-md-6">
        <label class="form-label">상품 ID</label>
        <input type="text" name="productId" class="form-control" required>
      </div>

      <div class="col-md-6">
        <label class="form-label">카테고리</label>
        <select name="categoryId" class="form-select" required>
          <option value="">선택하세요</option>
          <c:forEach items="${categories}" var="category">
            <option value="${category.categoryId}">${category.categoryName}</option>
          </c:forEach>
        </select>
      </div>

      <div class="col-12">
        <label class="form-label">상품명</label>
        <input type="text" name="productName" class="form-control" required>
      </div>

      <div class="col-md-6">
        <label class="form-label">가격</label>
        <input type="number" name="productPrice" class="form-control" min="0" required>
      </div>

      <div class="col-md-6">
        <label class="form-label">재고</label>
        <input type="number" name="productStock" class="form-control" min="0" required>
      </div>

      <div class="col-12">
        <label class="form-label">상품 이미지</label>
        <input type="file" name="productImage" class="form-control" accept="image/*" required>
      </div>

      <div class="col-12">
        <label class="form-label">상품 설명</label>
        <textarea name="productDescription" class="form-control" rows="4"></textarea>
      </div>

      <div class="col-12">
        <button type="submit" class="btn btn-primary">등록하기</button>
        <a href="/admin/products.do" class="btn btn-outline-secondary">취소</a>
      </div>
    </div>
  </form>
</div>
