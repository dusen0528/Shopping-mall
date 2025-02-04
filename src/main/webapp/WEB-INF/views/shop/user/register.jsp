<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>회원가입</title>
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
        input[type="text"], input[type="password"], input[type="date"] {
            width: 200px;
            padding: 5px;
            margin-bottom: 10px;
        }
        input[type="submit"] {
            margin-top: 10px;
            padding: 5px 10px;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
<h1>회원가입</h1>

<form action="/signup.do" method="post">
    <label for="userId">사용자 ID:</label>
    <input type="text" id="userId" name="userId" required><br>

    <label for="userPassword">비밀번호:</label>
    <input type="password" id="userPassword" name="userPassword" required><br>

    <label for="userName">이름:</label>
    <input type="text" id="userName" name="userName" required><br>

    <label for="userBirth">생년월일:</label>
    <input type="date" id="userBirth" name="userBirth" required><br>

    <input type="hidden" name="userAuth" value="ROLE_USER">
    <input type="hidden" name="userPoint" value="0">

    <input type="submit" value="가입하기">
</form>

<c:if test="${not empty errorMessage}">
    <p class="error">${errorMessage}</p>
</c:if>

<br>
<a href="/login.do">이미 계정이 있으신가요? 로그인하기</a>
</body>
</html>
