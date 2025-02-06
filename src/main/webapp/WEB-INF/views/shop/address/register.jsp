<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-body">
                    <h4 class="card-title mb-4">새 배송지 등록</h4>

                    <form action="/address/register.do" method="post">
                        <div class="mb-3">
                            <label for="recipientName" class="form-label">받는 사람</label>
                            <input type="text" class="form-control" id="recipientName"
                                   name="recipientName" required>
                        </div>

                        <div class="mb-3">
                            <label for="recipientPhone" class="form-label">연락처</label>
                            <input type="text" class="form-control" id="recipientPhone"
                                   name="recipientPhone" required>
                        </div>

                        <div class="mb-3">
                            <label for="address" class="form-label">주소</label>
                            <input type="text" class="form-control" id="address"
                                   name="address" required>
                        </div>

                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" id="isDefault"
                                   name="isDefault" value="true">
                            <label class="form-check-label" for="isDefault">기본 배송지로 설정</label>
                        </div>

                        <div class="mt-4">
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-plus-lg"></i> 배송지 등록
                            </button>
                            <a href="/mypage.do" class="btn btn-secondary">
                                <i class="bi bi-arrow-left"></i> 뒤로가기
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>