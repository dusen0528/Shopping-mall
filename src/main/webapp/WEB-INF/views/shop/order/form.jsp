<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container py-5">
    <h2 class="mb-4">주문하기</h2>

    <form action="/order/create.do" method="post">
        <div class="row">
            <div class="col-md-8">
                <div class="card mb-4">
                    <div class="card-header">
                        <h5 class="mb-0">배송 정보</h5>
                    </div>
                    <div class="card-body">
                        <div class="mb-3">
                            <label class="form-label">배송지 선택</label>
                            <select name="addressId" class="form-select" required>
                                <c:forEach var="address" items="${addresses}">
                                    <option value="${address.addressId}"
                                        ${address.isDefault ? 'selected' : ''}>
                                            ${address.recipientName} - ${address.address}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">주문 요약</h5>
                    </div>
                    <div class="card-body">
                        <dl class="row">
                            <dt class="col-6">상품 금액</dt>
                            <dd class="col-6 text-end">
                                <fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="₩"/>
                            </dd>

                            <dt class="col-6">사용 포인트</dt>
                            <dd class="col-6 text-end">
                                <input type="number" name="usePoint" value="0" min="0"
                                       max="${userPoint}" class="form-control text-end">
                            </dd>

                            <dt class="col-6">최종 결제 금액</dt>
                            <dd class="col-6 text-end">
                                <strong>
                                    <fmt:formatNumber value="${totalPrice}" type="currency" currencySymbol="₩"/>
                                </strong>
                            </dd>
                        </dl>

                        <button type="submit" class="btn btn-primary w-100">주문하기</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>