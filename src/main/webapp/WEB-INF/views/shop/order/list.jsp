<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container py-5">
    <h2 class="mb-4">주문 내역</h2>

    <c:if test="${empty orders}">
        <div class="alert alert-info">
            주문 내역이 없습니다.
        </div>
    </c:if>

    <c:if test="${not empty orders}">
        <div class="list-group">
            <c:forEach var="order" items="${orders}">
                <a href="/order/detail.do?orderId=${order.orderId}"
                   class="list-group-item list-group-item-action">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">주문번호: ${order.orderId}</h5>
                        <small>
                            <fmt:formatDate value="${order.createdAt}"
                                            pattern="yyyy-MM-dd HH:mm"/>
                        </small>
                    </div>
                    <p class="mb-1">
                        총 결제금액:
                        <fmt:formatNumber value="${order.totalPrice}"
                                          type="currency" currencySymbol="₩"/>
                    </p>
                    <small>주문상태: ${order.orderStatus}</small>
                </a>
            </c:forEach>
        </div>
    </c:if>
</div>