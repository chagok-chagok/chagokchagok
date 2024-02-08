<template>
  <div class="screen-container">
    <div class="time-display">{{ currentTime }}</div>
    <div class="assignment-message">자리가 배정되었습니다.</div>
    <br />
    <div class="assignment-message">주차확인증을 뽑아주세요.</div>
    <div class="ticket-container">
      <img
        src="@/assets/주차확인증.png"
        alt="주차확인증"
        class="ticket-image"
      />
      <!-- 할당된 주차 구역 번호를 직접 참조 -->
      <div class="ticket">{{ parkingStore.allocatedLocation }}</div>
    </div>
    <div class="welcome-message">환영합니다.</div>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, ref } from "vue";
import router from "@/router";
import { useParkingStore } from "@/stores/parkingStore";

const currentTime = ref("");
const parkingStore = useParkingStore(); // Pinia store 사용
// const allocatedLocation =parkingStore.allocatedLocation; // 할당된 주차 구역 번호

// 현재 시간을 24시간 형식으로 업데이트하는 함수
function updateCurrentTime() {
  const now = new Date();
  currentTime.value = now.toTimeString().substring(0, 5);
}

let intervalId = null; // 인터벌 ID를 저장할 변수

// 마운트될 때와 매초마다 현재 시간을 업데이트
onMounted(() => {
  updateCurrentTime();
  intervalId = setInterval(updateCurrentTime, 1000); // 인터벌 ID 저장
  setTimeout(() => {
    router.push({ name: "choice" }); // 일정 시간 후 홈으로 리디렉션
  }, 10000); // 시간을 1000000에서 10000(예: 10초)으로 조정하여 실제 사용 시나리오에 더 적합하게 조정
});

// 컴포넌트가 언마운트될 때 인터벌 클리어
onUnmounted(() => {
  clearInterval(intervalId);
});
</script>

<style scoped>
.screen-container {
  position: relative;
  font-family: "Arial", sans-serif;
  background-color: #f0f7ff;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 630px;
  height: 1120px;
  margin: 20px auto;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: center; /* Centers content vertically */
}
.screen-container::before {
  content: ""; /* 가상 요소에는 내용이 필요하지 않으므로 비워둡니다. */
  position: absolute; /* 상대 위치인 .screen-container에 대해 절대 위치를 설정합니다. */
  top: 0; /* 상단에서 0px 위치 */
  left: 0; /* 왼쪽에서 0px 위치 */
  width: 100%; /* 부모 요소의 전체 너비를 차지하도록 설정합니다. */
  height: 60px; /* 색상 바의 높이를 설정합니다. 이것은 시간 표시를 포함하기에 충분한 높이여야 합니다. */
  background: linear-gradient(
    to right,
    rgba(255, 255, 255, 0.2) 0%,
    rgba(255, 255, 255, 0.2) 100%
  );
  border-top-left-radius: 10px; /* 왼쪽 상단 모서리 둥글게 처리 */
  border-top-right-radius: 10px; /* 오른쪽 상단 모서리 둥글게 처리 */
  z-index: 1; /* 다른 요소들보다 앞에 표시되도록 z-index 값을 설정합니다. */
}

.time-display {
  position: absolute;
  top: 10px; /* 상단 바의 더 가까운 위치로 조정합니다. */
  right: 10px; /* 오른쪽 가장자리와의 거리를 줄입니다. */
  font-size: 2em;
  color: #333;
  z-index: 2; /* .screen-container::before 요소 위에 표시되도록 z-index 값을 더 높게 설정합니다. */
}

.assignment-message,
.welcome-message {
  font-size: 2rem;
  margin-bottom: 1rem;
  color: #000; /* 이미지에 맞춰서 색상 변경 */
  text-align: center;
}

.ticket-container {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative; /* 티켓 번호를 위해 relative 추가 */
}

.ticket {
  position: absolute; /* 티켓 이미지 위에 오도록 절대 위치 사용 */
  font-size: 3rem;
  color: #000; /* 검은색으로 변경 */
}

.welcome-message {
  color: #000; /* 환영 메시지 색상 유지 */
  margin-top: 1rem; /* 여백 조정 */
}
</style>
