<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>마이페이지</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h1>마이페이지</h1>

<c:if test="${not empty sessionScope.user}">
    <table>
        <tr>
            <th>사용자 ID</th>
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
            <th>가입일</th>
            <td>${user.createdAt}</td>
        </tr>
    </table>

    <br>
    <a href="/edit_profile.do">프로필 수정</a>
</c:if>

<c:if test="${empty user}">
    <p>사용자 정보를 불러올 수 없습니다. 다시 로그인해주세요.</p>
    <a href="/login.do">로그인 페이지로 이동</a>
</c:if>

<br>
<a href="/index.do">메인 페이지로 돌아가기</a>
</body>
</html>
