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
      <div class="ticket">{{ allocatedLocation }}</div>
    </div>
    <div class="welcome-message">환영합니다.</div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import router from "@/router";
import axios from "axios";

const currentTime = ref("");
const allocatedLocation = ref(""); // 할당된 주차 구역 번호를 저장할 ref 변수

// 주차 구역을 요청하고 할당된 주차 구역 번호를 업데이트하는 함수
// function fetchParkingAllocation() {
//   axios
//     .post(
//       "http://localhost:8080/park/allocation",
//       {
//         car_no: "10가1234",
//         is_disabled: true,
//       },
//       {
//         headers: {
//           "Content-Type": "application/json",
//         },
//       }
//     )
//     .then((response) => {
//       // 서버로부터 할당된 주차 구역 번호를 업데이트
//       allocatedLocation.value = response.data.allocatedLocation;
//     })
//     .catch((error) => {
//       console.error("Error fetching parking allocation:", error);
//       allocatedLocation.value = "";
//       // 에러가 발생했을 때 'Secondscreen.vue'로 라우트 이동
//       router.push("/second");
//     });
// }

// 현재 시간을 24시간 형식으로 업데이트하는 함수
function updateCurrentTime() {
  const now = new Date();
  currentTime.value = now.toTimeString().substring(0, 5);
}

// 마운트될 때와 매초마다 현재 시간을 업데이트하고 주차 구역을 요청
onMounted(() => {
  updateCurrentTime();
  fetchParkingAllocation();
  setInterval(updateCurrentTime, 1000);
  setTimeout(() => {
    router.push("/");
  }, 1000000);
});

// 컴포넌트가 언마운트될 때 인터벌을 클리어
onUnmounted(() => {
  clearInterval(updateCurrentTime);
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

.time-display {
  position: absolute; /* 절대 위치 사용 */
  top: 16px; /* 이미지와 동일한 여백으로 조정 */
  right: 16px;
  font-size: 2rem;
  color: #000; /* 검은색으로 변경 */
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
