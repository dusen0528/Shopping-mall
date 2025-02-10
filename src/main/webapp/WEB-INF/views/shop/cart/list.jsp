<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container py-5">
  <h2 class="mb-4">장바구니</h2>

  <c:if test="${empty cartItems}">
    <div class="alert alert-info">
      장바구니가 비어있습니다.
    </div>
  </c:if>

  <c:if test="${not empty cartItems}">
    <table class="table">
      <thead>
      <tr>
        <th>상품명</th>
        <th>가격</th>
        <th>수량</th>
        <th>소계</th>
        <th>관리</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${cartItems}">
        <tr>
          <td>${item.productName}</td>
          <td><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₩"/></td>
          <td>
            <form action="/cart/update.do" method="post" class="d-inline">
              <input type="hidden" name="cartId" value="${item.cartId}">
              <input type="number" name="quantity" value="${item.quantity}" min="1" class="form-control d-inline" style="width: 80px">
              <button type="submit" class="btn btn-sm btn-secondary">변경</button>
            </form>
          </td>
          <td><fmt:formatNumber value="${item.price * item.quantity}" type="currency" currencySymbol="₩"/></td>
          <td>
            <form action="/cart/delete.do" method="post" class="d-inline">
              <input type="hidden" name="cartId" value="${item.cartId}">
              <button type="submit" class="btn btn-sm btn-danger">삭제</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
      <tfoot>
      <tr>
        <td colspan="3" class="text-end"><strong>총계:</strong></td>
        <td>
          <strong>
            <fmt:formatNumber value="${cartItems.stream().map(item -> item.price * item.quantity).sum()}"
                              type="currency" currencySymbol="₩"/>
          </strong>
        </td>
        <td></td>
      </tr>
      </tfoot>
    </table>

    <div class="text-end mt-4">
      <a href="/products" class="btn btn-secondary">쇼핑 계속하기</a>
      <a href="/order/form.do" class="btn btn-primary">주문하기</a>
    </div>
  </c:if>
</div>