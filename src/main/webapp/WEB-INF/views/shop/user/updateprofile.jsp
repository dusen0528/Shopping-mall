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
          <h4 class="card-title mb-4">프로필 수정</h4>

          <form action="/update_profile.do" method="post">
            <div class="mb-3">
              <label for="userName" class="form-label">이름</label>
              <input type="text" class="form-control" id="userName" name="userName"
                     value="${user.userName}" required>
            </div>

            <div class="mb-3">
              <label for="userBirth" class="form-label">생년월일</label>
              <input type="date" class="form-control" id="userBirth" name="userBirth"
                     value="${user.userBirth}" required>
            </div>

            <div class="mt-4">
              <button type="submit" class="btn btn-primary">
                <i class="bi bi-check-lg"></i> 수정 완료
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