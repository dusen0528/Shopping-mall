<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>관리자 패널</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .admin-sidebar {
      width: 250px;
      height: 100vh;
      background: #2c3e50;
      position: fixed;
      padding-top: 20px;
    }
    .admin-main {
      margin-left: 250px;
      padding: 20px;
    }
  </style>
</head>
<body>
<div class="admin-sidebar text-white">
  <div class="px-3">
    <h4>관리자 메뉴</h4>
    <ul class="nav flex-column">
      <li class="nav-item">
        <a class="nav-link text-white" href="/admin/products.do">상품 관리</a>
      </li>
      <li class="nav-item">
        <a class="nav-link text-white" href="/admin/orders.do">주문 관리</a>
      </li>
      <li class="nav-item">
        <a class="nav-link text-white" href="/admin/users.do">회원 관리</a>
      </li>
    </ul>
  </div>
</div>

<div class="admin-main">
  <div class="container-fluid">
    <div class="d-flex justify-content-between mb-4">
      <h2>관리자 대시보드</h2>
      <div>
        <span class="badge bg-primary">접속자: ${sessionScope.user.userName}</span>
        <a href="/logout.do" class="btn btn-danger btn-sm">로그아웃</a>
      </div>
    </div>

    <!-- 통계 카드 영역 -->
    <div class="row mb-4">
      <div class="col-md-3">
        <div class="card bg-primary text-white">
          <div class="card-body">
            <h5>총 회원수</h5>
            <p class="h2">${userCount}</p>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card bg-success text-white">
          <div class="card-body">
            <h5>주문 현황</h5>
            <p class="h2">${orderCount}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 컨텐츠 영역 -->
    <jsp:include page="/WEB-INF/views/shop/admin/${contentPage}" />
  </div>
</div>
</body>
</html>
