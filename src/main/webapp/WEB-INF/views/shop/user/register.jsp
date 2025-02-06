<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-5">
    <h2 class="mb-4">마이페이지</h2>

    <div class="card">
        <div class="card-body">
            <h5 class="card-title">사용자 정보</h5>
            <table class="table">
                <tr>
                    <th style="width: 150px;">아이디</th>
                    <td>${user.userId}</td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td>${user.userName}</td>
                </tr>
                <tr>
                    <th>생년월일</th>
                    <td>${user.userBirth}</td>
                </tr>
                <tr>
                    <th>포인트</th>
                    <td>${user.userPoint} P</td>
                </tr>
                <tr>
                    <th>가입일</th>
                    <td>${user.createdAt}</td>
                </tr>
                <tr>
                    <th>최근 로그인</th>
                    <td>${user.latestLoginAt}</td>
                </tr>
            </table>

            <div class="mt-3">
                <a href="/update_profile.do" class="btn btn-primary">정보 수정</a>
            </div>
        </div>
    </div>

    <div class="card mt-4">
        <div class="card-body">
            <h5 class="card-title">배송지 정보</h5>
            <div class="mb-3">
                <button class="btn btn-success" onclick="showAddAddressForm()">새 배송지 추가</button>
            </div>

            <c:forEach items="${addresses}" var="address">
                <div class="address-box border p-3 mb-3 ${address.default ? 'bg-light' : ''}">
                    <div class="d-flex justify-content-between align-items-top">
                        <div>
                            <c:if test="${address.default}">
                                <span class="badge bg-primary mb-2">기본 배송지</span>
                            </c:if>
                            <p class="mb-1"><strong>받는 사람:</strong> ${address.recipientName}</p>
                            <p class="mb-1"><strong>연락처:</strong> ${address.recipientPhone}</p>
                            <p class="mb-1"><strong>주소:</strong> ${address.address}</p>
                        </div>
                        <div>
                            <button class="btn btn-sm btn-outline-primary" onclick="editAddress('${address.addressId}')">수정</button>
                            <button class="btn btn-sm btn-outline-danger" onclick="deleteAddress('${address.addressId}')">삭제</button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<!-- 배송지 추가 모달 -->
<div class="modal fade" id="addAddressModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">새 배송지 추가</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="/address/register.do" method="post">
                    <div class="mb-3">
                        <label for="recipientName" class="form-label">받는 사람</label>
                        <input type="text" class="form-control" id="recipientName" name="recipientName" required>
                    </div>
                    <div class="mb-3">
                        <label for="recipientPhone" class="form-label">연락처</label>
                        <input type="text" class="form-control" id="recipientPhone" name="recipientPhone" required>
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">주소</label>
                        <input type="text" class="form-control" id="address" name="address" required>
                    </div>
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" id="isDefault" name="isDefault" value="true">
                        <label class="form-check-label" for="isDefault">기본 배송지로 설정</label>
                    </div>
                    <button type="submit" class="btn btn-primary">저장</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    function showAddAddressForm() {
        var modal = new bootstrap.Modal(document.getElementById('addAddressModal'));
        modal.show();
    }

    function editAddress(addressId) {
        // 수정 페이지로 이동 또는 모달 표시
        window.location.href = '/address/update.do?addressId=' + addressId;
    }

    function deleteAddress(addressId) {
        if(confirm('이 배송지를 삭제하시겠습니까?')) {
            // form을 생성하여 POST 요청 전송
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = '/address/delete.do';

            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'addressId';
            input.value = addressId;

            form.appendChild(input);
            document.body.appendChild(form);
            form.submit();
        }
    }
</script>