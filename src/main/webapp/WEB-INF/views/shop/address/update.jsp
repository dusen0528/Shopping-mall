<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-lg">  <!-- 그림자 효과 추가 -->
                <div class="card-header bg-primary text-white">  <!-- 헤더 스타일링 -->
                    <h4 class="mb-0"><i class="bi bi-pin-map"></i> 배송지 수정</h4>
                </div>
                <div class="card-body">
                    <form action="/address/update.do" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="hidden" name="addressId" value="${address.addressId}">

                        <div class="mb-3">
                            <label for="recipientName" class="form-label">받는 사람</label>
                            <input type="text" class="form-control form-control-lg"
                            id="recipientName" name="recipientName"
                            value="${address.recipientName}" required>
                        </div>

                        <div class="mb-3">
                            <label for="recipientPhone" class="form-label">연락처</label>
                            <input type="tel" class="form-control"
                                   id="recipientPhone" name="recipientPhone"
                                   pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
                                   placeholder="010-1234-5678"
                                   value="${address.recipientPhone}" required>
                            <div class="form-text">하이픈(-)을 포함하여 입력해주세요</div>
                        </div>

                        <div class="mb-3">
                            <label for="address" class="form-label">주소</label>
                            <div class="input-group">
                                <input type="text" class="form-control"
                                       id="address" name="address"
                                       value="${address.address}" readonly
                                       aria-describedby="addressSearchBtn">
                                <button class="btn btn-primary" type="button"
                                        id="addressSearchBtn" onclick="searchAddress()">
                                    <i class="bi bi-search"></i> 주소 검색
                                </button>
                            </div>
                        </div>

                        <div class="mb-3 form-check form-switch">
                            <input type="checkbox" class="form-check-input"
                                   id="isDefault" name="isDefault"
                                   value="true" ${address.isDefault() ? 'checked' : ''}>
                            <label class="form-check-label" for="isDefault">
                                기본 배송지로 설정
                            </label>
                        </div>

                        <div class="d-grid gap-2 mt-4">  <!-- 버튼 그룹화 -->
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="bi bi-check-circle"></i> 수정 완료
                            </button>
                            <a href="/mypage.do" class="btn btn-secondary">
                                <i class="bi bi-arrow-left"></i> 취소하기
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function searchAddress() {
        new daum.Postcode({
            oncomplete: function(data) {
                const roadAddr = data.roadAddress;  // 도로명 주소
                document.getElementById('address').value = roadAddr;
            }
        }).open();
    }
</script>
