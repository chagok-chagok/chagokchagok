<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { useKioskStore } from "@/stores/kiosk";
// import { instance } from "@/utils/mainAxios";
// import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
// const local = instance;
const kioskStore = useKioskStore();
const {
  currentTime,
  carNumber,
  isDisabled,
  updateCurrentTime,
  allocation,
  selectParking,
  getCurrentTime,
} = storeToRefs(kioskStore);
// const currentTime = ref(getCurrentTime());

// // 현재 시간을 매초마다 업데이트하는 인터벌 설정
// onMounted(() => {
//   const interval = setInterval(() => {
//     currentTime.value = getCurrentTime();
//   }, 1000);

//   // 컴포넌트가 언마운트되면 인터벌 클리어
//   onUnmounted(() => {
//     clearInterval(interval);
//   });
// });

// // 현재 시간을 HH:MM 형식으로 반환하는 함수
// function getCurrentTime() {
//   const now = new Date();
//   return now.toTimeString().substring(0, 5);
// }
</script>

<template>
  <div class="screen-container">
    <div class="time-display">{{ currentTime }}</div>
    <div class="status-message">차량 번호 인식 오류</div>
    <div class="parking-image-container">
      <img
        src="@/assets/주차안내 이미지 4.png"
        alt="인식오류"
        class="parking-image"
      />
    </div>
    <div class="additional-message">
      후진 후 앞으로 천천히 <br />움직여 주세요.
    </div>
  </div>
</template>

<style scoped>
.screen-container {
  /* font-size: 1.4em; */
  position: relative;
  font-family: "MICEGothic Bold";
  background: radial-gradient(
    circle,
    rgba(85, 153, 255, 0.5186449579831933) 0%,
    rgba(255, 255, 255, 1) 100%
  );
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 630px;
  height: 1120px;
  margin: 20px auto;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
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
  font-family: "Pretendard-Regular";
  position: absolute;
  top: 10px; /* 상단 바의 더 가까운 위치로 조정합니다. */
  right: 10px; /* 오른쪽 가장자리와의 거리를 줄입니다. */
  padding-right: 2%;
  padding-top: 1%;
  font-size: 3em;
  color: #333;
  z-index: 2; /* .screen-container::before 요소 위에 표시되도록 z-index 값을 더 높게 설정합니다. */
}

.status-message {
  color: #000000;
  font-size: 2.6em;
  text-align: center;
  margin-bottom: 50px; /* 텍스트 아래 마진 설정 */
}

.parking-image {
  max-width: 100%; /* 이미지가 컨테이너를 넘지 않도록 설정 */
  height: 400px; /* 이미지 높이를 자동으로 설정하여 비율 유지 */
}

.additional-message {
  font-family: "NanumBarunGothic", sans-serif;
  font-size: 2.5em;
  text-align: center;
  color: #000;
  margin-top: 20px; /* 이미지 위에 적용될 마진 */
  margin-bottom: 50px; /* 추가 메시지 아래 마진 설정 */
}
.parking-image-container {
  background-color: #ffffff; /* 흰색 배경 설정 */
  width: 630px; /* 최대 가로 크기 설정 */
  height: 400px;
  display: flex; /* Flexbox를 사용하여 내용물을 중앙에 배치 */
  justify-content: center; /* 가로 방향 중앙 정렬 */
  align-items: center; /* 세로 방향 중앙 정렬 */
  padding: 20px; /* 이미지 주변에 패딩 추가 */
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
  margin: 20px 0; /* 위아래 마진 추가 */
}
</style>
