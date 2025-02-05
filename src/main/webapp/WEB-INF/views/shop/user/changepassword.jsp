<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container mt-5">
    <h2 class="mb-4">비밀번호 변경</h2>

    <div class="card">
        <div class="card-body">
            <form action="/change_password.do" method="post">
                <div class="mb-3">
                    <label for="currentPassword" class="form-label">현재 비밀번호</label>
                    <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                </div>

                <div class="mb-3">
                    <label for="newPassword" class="form-label">새 비밀번호</label>
                    <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                </div>

                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">비밀번호 확인</label>
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                </div>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger" role="alert">
                            ${errorMessage}
                    </div>
                </c:if>

                <button type="submit" class="btn btn-primary">비밀번호 변경</button>
                <a href="/mypage.do" class="btn btn-secondary">취소</a>
            </form>
        </div>
    </div>
</div>