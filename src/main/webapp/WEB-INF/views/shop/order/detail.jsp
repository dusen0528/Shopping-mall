<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container py-5">
    <h2 class="mb-4">주문 상세</h2>

    <div class="card mb-4">
        <div class="card-header">
            <h5 class="mb-0">주문 정보</h5>
        </div>
        <div class="card-body">
            <dl class="row">
                <dt class="col-sm-3">주문번호</dt>
                <dd class="col-sm-9">${order.orderId}</dd>

                <dt class="col-sm-3">주문일시</dt>
                <dd class="col-sm-9">
                    <fmt:formatDate value="${order.createdAt}"
                                    pattern="yyyy-MM-dd HH:mm:ss"/>
                </dd>

                <dt class="col-sm-3">주문상태</dt>
                <dd class="col-sm-9">${order.orderStatus}</dd>

                <dt class="col-sm-3">배송지</dt>
                <dd class="col-sm-9">
                    ${order.address.recipientName}<br>
                    ${order.address.address}
                </dd>
            </dl>
        </div>
    </div>

    <div class="card">
        <div class="card-header">
            <h5 class="mb-0">주문 상품</h5>
        </div>
        <div class="card-body">
            <table class="table">
                <thead>
                <tr>
                    <th>상품명</th>
                    <th>수량</th>
                    <th>가격</th>
                    <th>소계</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="detail" items="${order.orderDetails}">
                    <tr>
                        <td>${detail.productName}</td>
                        <td>${detail.quantity}</td>
                        <td>
                            <fmt:formatNumber value="${detail.priceAtTime}"
                                              type="currency" currencySymbol="₩"/>
                        </td>
                        <td>
                            <fmt:formatNumber value="${detail.priceAtTime * detail.quantity}"
                                              type="currency" currencySymbol="₩"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="3" class="text-end"><strong>총 결제금액</strong></td>
                    <td>
                        <strong>
                            <fmt:formatNumber value="${order.totalPrice}"
                                              type="currency" currencySymbol="₩"/>
                        </strong>
                    </td>
                </tr>
                </tfoot>
            </table>
        </div>
    </div>
</div>