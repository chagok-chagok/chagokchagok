<script setup>
import axios from "axios";
import { ref } from "vue";

const carInfo = ref(null);
const sectionName = ref("A2");
const isTooltipOpen = ref(false);

const fetchCarInfo = async () => {
  try {
    const response = await axios.post("http://localhost:8080/admin/caloc", {
      area: sectionName.value,
    });
    carInfo.value = response.data;
    isTooltipOpen.value = true;
    console.log(carInfo.value);
  } catch (error) {
    console.error("차량 정보를 가져오는데 에러가 발생했습니다:", error);
    // 여기에 에러 처리 로직을 추가합니다.
  }
};
</script>

<template>
  <VDropdown placement="right-start" :distance="6">
    <button @click="fetchCarInfo">A2</button>
    <template #popper>
      <div class="dropdown-content" v-if="isTooltipOpen">
        <p>[{{ carInfo.carNo }}]</p>
        <p>입차시간:{{ carInfo.entryTime }}</p>
        <router-link to="/barrier-release" class="dropdown-button"
          >차단바 해제</router-link
        >
        <router-link to="/spot-swap" class="dropdown-button"
          >자리 교환하기</router-link
        >
      </div>
    </template>
  </VDropdown>
</template>

<style lang="scss" scoped>
.dropdown-content {
  display: flex;
  flex-direction: column;
  background: white; // 배경색을 흰색으로 설정
  border-radius: 8px; // 둥근 모서리
  border: 1px solid #e2e2e2; // 테두리 색상
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); // 그림자 효과
  padding: 16px; // 내부 여백
  font-size: 14px; // 폰트 크기
}
.dropdown-content p {
  margin: 4px 0; // 문단 사이의 여백
}

.dropdown-button {
  margin-top: 8px;
  padding: 8px 16px; // 버튼 내부 여백 (상하 좌우)
  color: black;
  text-align: center;
  text-decoration: none;
  border-radius: 4px; // 버튼 둥근 모서리
  cursor: pointer;
  border: none; // 테두리 제거
  font-weight: bold; // 글자 굵기
}
.dropdown-button:first-of-type {
  background-color: #cde5f2; // 첫 번째 버튼의 배경색
}

.dropdown-button:last-of-type {
  background-color: #cef5c4; // 마지막 버튼의 배경색
}

.dropdown-button:hover {
  opacity: 0.85; // 호버 효과
}
</style>
