<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import axios from "axios";
import router from "@/router";
import { useParkingStore } from "@/stores/parkingStore";

const { VITE_VUE_SPRING_URL } = import.meta.env;
const kioskUrl = `${VITE_VUE_SPRING_URL}sse/kiosk`; //kiosk의 sse접속 url입니다.
const currentTime = ref(getCurrentTime());
const allocatedLocation = ref("");
const parkingStore = useParkingStore();
const carNumber = ref("");

onMounted(() => {
  const sseEvent = new EventSource(kioskUrl);

  //연결 리스너
  sseEvent.addEventListener("open", function (e) {
    //캐치할 에러코드를 써줌
    console.log("carNumber : ", e.data);
  });

  //에러 리스너
  sseEvent.addEventListener("error", function (e) {
    console.log(e);
  });

  // 자리 배정 이벤트
  sseEvent.addEventListener("VALID_CAR_NUM", function (e) {
    // 차 번호 입력받았으니 장애 여부 입력받고 자리 할당 api 호출
    console.log(e.data);
    carNumber.value = e.data;
  });
  // 정규식 틀렸을 경우
  sseEvent.addEventListener("INVALID_CAR_NUM", function (e) {
    // 잘못찍힘 화면으로 보냄
    console.log(e.data);
    router.push({ name: "recognition-error" });
  });
  const interval = setInterval(() => {
    currentTime.value = getCurrentTime();
  }, 10000);

  onUnmounted(() => {
    clearInterval(interval);
  });
});

function getCurrentTime() {
  const now = new Date();
  return now.toTimeString().substring(0, 5);
}

function selectParking() {
  // 여기서는 isDisabled 파라미터를 사용하지 않고 직접 allocation 페이지로 이동
  router.push({ name: "allocation" });
}
</script>

<template>
  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
  <link
    href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@700&display=swap"
    rel="stylesheet"
  />
  <div class="screen-container">
    <div class="time-display">{{ currentTime }}</div>
    <div class="message"><strong>선호하시는 자리를 선택해주세요.</strong></div>
    <div class="button-container">
      <button class="parking-option" @click.prevent="selectParking()">
        <div><strong>가까운 자리</strong></div>
      </button>
      <button class="parking-option" @click.prevent="selectParking()">
        <div><strong>여유로운 자리</strong></div>
      </button>
    </div>
  </div>
</template>

<style scoped>
@font-face {
  font-family: "MICEGothic Bold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-01@1.0/MICEGothic Bold.woff2")
    format("woff2");
  font-weight: 700;
  font-style: normal;
}
@font-face {
  font-family: "Pretendard-Regular";
  src: url("https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff")
    format("woff");
  font-weight: 400;
  font-style: normal;
}
@font-face {
  font-family: "NanumBarunGothic";
  font-style: normal;
  font-weight: 400;
  src: url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot");
  src: url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot?#iefix")
      format("embedded-opentype"),
    url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.woff")
      format("woff"),
    url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.ttf")
      format("truetype");
}

.screen-container {
  position: relative;
  font-family: "MICEGothic Bold";
  background: rgb(85, 153, 255);
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
  justify-content: center; /* Centers content vertically */
}
.screen-container::before {
  content: ""; /* 가상 요소에는 내용이 필요하지 않으므로 비워둡니다. */
  position: absolute; /* 상대 위치인 .screen-container에 대해 절대 위치를 설정합니다. */
  top: 0; /* 상단에서 0px 위치 */
  left: 0; /* 왼쪽에서 0px 위치 */
  width: 100%; /* 부모 요소의 전체 너비를 차지하도록 설정합니다. */
  height: 80px; /* 색상 바의 높이를 설정합니다. 이것은 시간 표시를 포함하기에 충분한 높이여야 합니다. */
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

.message {
  color: #000;
  font-size: 2.6em;
  text-align: center;
}

.button-container {
  display: flex;
  flex-direction: column;
  align-items: center; /* 버튼을 수직으로 가운데 정렬합니다 */
  gap: 40px; /* 버튼 사이의 간격을 조정합니다 */
  margin-top: 150px;
}

.parking-option {
  width: 80%; /* 버튼 너비를 컨테이너의 80%로 설정합니다. */
  height: 150px; /* 버튼 높이를 60px로 설정합니다. */
  margin-bottom: 20px; /* 버튼 사이의 마진을 20px로 설정합니다. */
  padding: 20px; /* 버튼 내부의 패딩을 20px로 설정합니다. */
  border-radius: 10px; /* 버튼의 둥근 모서리 반경을 10px로 설정합니다. */
  background-color: #fff; /* 버튼 배경색을 흰색으로 설정합니다. */
  font-family: "NanumBarunGothic", sans-serif; /* 버튼 폰트를 설정합니다. */
  font-size: 3.3em; /* 버튼 텍스트 크기를 24px로 설정합니다. */
  color: #333; /* 버튼 텍스트 색상을 설정합니다. */
  border: none; /* 버튼 테두리를 제거합니다. */
  cursor: pointer; /* 마우스 커서를 포인터로 설정합니다. */
  transition: transform 0.1s ease-in-out; /* 마우스 호버 효과를 위한 전환 설정 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
}

.parking-option:hover {
  transform: scale(1.02);
}
</style>
