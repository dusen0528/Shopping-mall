<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <title>상품 관리</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
  <div class="row">
    <!-- 사이드바 -->
    <nav class="col-md-2 d-none d-md-block bg-light sidebar">
      <div class="position-sticky pt-3">
        <ul class="nav flex-column">
          <li class="nav-item">
            <a class="nav-link active" href="/admin/products.do">
              상품 관리
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/admin/orders.do">
              주문 관리
            </a>
          </li>
        </ul>
      </div>
    </nav>

    <!-- 메인 컨텐츠 -->
    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
      <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">상품 관리 패널</h1>
        <a href="/admin/product/add.do" class="btn btn-success">신규 상품 등록</a>
      </div>

      <!-- 상품 목록 -->
      <jsp:include page="/WEB-INF/views/shop/admin/product_list.jsp"/>
    </main>
  </div>
</div>
</body>
</html>
