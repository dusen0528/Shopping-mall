<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>프로필 수정</title>
  <style>
    form {
      width: 300px;
      margin: 0 auto;
    }
    label {
      display: inline-block;
      width: 100px;
      margin-bottom: 10px;
    }
    input[type="text"], input[type="email"], input[type="date"] {
      width: 200px;
      padding: 5px;
      margin-bottom: 10px;
    }
    input[type="submit"] {
      margin-top: 10px;
      padding: 5px 10px;
    }
  </style>
</head>
<body>
<h1>프로필 수정</h1>

<c:if test="${not empty user}">
  <form action="/update_profile.do" method="post">
    <label for="userName">이름:</label>
    <input type="text" id="userName" name="userName" value="${user.userName}" required><br>

    <label for="userBirth">생년월일:</label>
    <input type="date" id="userBirth" name="userBirth" value="${user.userBirth}" required><br>

    <input type="submit" value="정보 수정">
  </form>

  <div class="mt-3">
    <a href="/change_password.do" class="btn btn-secondary">비밀번호 변경</a>
  </div>
</c:if>

<c:if test="${empty user}">
  <p>사용자 정보를 불러올 수 없습니다. 다시 로그인해주세요.</p>
  <a href="/login.do">로그인 페이지로 이동</a>
</c:if>

<br>
<a href="/index.do">마이페이지로 돌아가기</a>
</body>
</html>
