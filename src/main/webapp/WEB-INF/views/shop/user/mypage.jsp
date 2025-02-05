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
</div>