<!-- mypage.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    .card {
        box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
    }

    .address-box {
        border: 1px solid #dee2e6;
        border-radius: 0.5rem;
        padding: 1rem;
        margin-bottom: 1rem;
        position: relative;
    }

    .address-box.default-address {
        border-color: #0d6efd;
        background-color: #f8f9fa;
    }

    .badge {
        position: absolute;
        top: 0.5rem;
        right: 0.5rem;
    }

    .action-buttons {
        margin-top: 0.5rem;
        text-align: right;
    }
</style>

<div class="container mt-5">
    <h2 class="mb-4">마이페이지</h2>

    <div class="card">
        <div class="card-body">
            <h5 class="card-title mb-4">사용자 정보</h5>
            <table class="table table-hover">
                <tr>
                    <th class="bg-light" style="width: 150px;">아이디</th>
                    <td>${user.userId}</td>
                </tr>
                <tr>
                    <th class="bg-light">이름</th>
                    <td>${user.userName}</td>
                </tr>
                <tr>
                    <th class="bg-light">생년월일</th>
                    <td>${user.userBirth}</td>
                </tr>
                <tr>
                    <th class="bg-light">포인트</th>
                    <td><span class="text-primary fw-bold">${user.userPoint} P</span></td>
                </tr>
                <tr>
                    <th class="bg-light">가입일</th>
                    <td>${user.createdAt}</td>
                </tr>
                <tr>
                    <th class="bg-light">최근 로그인</th>
                    <td>${user.latestLoginAt}</td>
                </tr>
            </table>

            <div class="mt-4 d-flex gap-2">
                <a href="/update_profile.do" class="btn btn-primary">
                    <i class="bi bi-pencil-square"></i> 정보 수정
                </a>
                <a href="/change_password.do" class="btn btn-outline-secondary">
                    <i class="bi bi-key"></i> 비밀번호 변경
                </a>
            </div>
        </div>
    </div>

    <div class="card mt-4">
        <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h5 class="card-title mb-0">배송지 관리</h5>
                <a href="/address/register.do" class="btn btn-success">
                    <i class="bi bi-plus-lg"></i> 새 배송지 추가
                </a>
            </div>

            <c:forEach items="${addresses}" var="address">
                <div class="address-box ${address.isDefault() ? 'default-address' : ''}">
                    <c:if test="${address.isDefault()}">
                        <span class="badge bg-primary">기본 배송지</span>
                    </c:if>
                    <div class="row">
                        <div class="col-md-8">
                            <p class="mb-2"><strong>받는 사람:</strong> ${address.recipientName}</p>
                            <p class="mb-2"><strong>연락처:</strong> ${address.recipientPhone}</p>
                            <p class="mb-0"><strong>주소:</strong> ${address.address}</p>
                        </div>
                        <div class="col-md-4">
                            <div class="action-buttons">
                                <button onclick="editAddress('${address.addressId}')"
                                        class="btn btn-sm btn-outline-primary">수정</button>
                                <button onclick="deleteAddress('${address.addressId}')"
                                        class="btn btn-sm btn-outline-danger">삭제</button>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty addresses}">
                <div class="text-center py-5 text-muted">
                    <p>등록된 배송지가 없습니다.</p>
                    <a href="/address/register.do" class="btn btn-primary">첫 배송지 등록하기</a>
                </div>
            </c:if>
        </div>
    </div>
</div>

<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function editAddress(addressId) {
        window.location.href = `/address/update.do?addressId=`+addressId;
    }

    function deleteAddress(addressId) {
        if(confirm('이 배송지를 삭제하시겠습니까?')) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = '/address/delete.do';

            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'addressId';
            input.value = addressId;

            form.appendChild(input);
            document.body.appendChild(form);
            form.submit();
        }
    }
</script>