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
    router.push({ name: "third" });
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

function selectParking(isDisabled) {
  console.log("지금 전송할 차번호는 ", carNumber.value);
  axios
    .post(
      "http://localhost:8080/api/park/allocation",
      {
        car_no: carNumber.value,
        is_disabled: isDisabled,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
    .then((response) => {
      console.log(response);
      console.log(response.data.allocated_location);
      parkingStore.allocatedLocation = response.data.allocated_location;
      router.push({ name: "fourth" });
    })
    .catch((error) => {
      console.error("자리 없음:", error);
      allocatedLocation.value = "";
      router.push({ name: "second" });
    });
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
    <div class="message">장애 차량 여부를 선택해주세요.</div>
    <div class="button-container">
      <button class="parking-option" @click.prevent="selectParking(false)">
        <img src="@/assets/주차안내 이미지 1.png" alt="일반 주차" />
        <div>일반 주차구역을 <br />배정해드립니다.</div>
      </button>
      <button class="parking-option" @click.prevent="selectParking(true)">
        <img src="@/assets/주차안내 이미지 2.png" alt="장애인 주차" />
        <div>장애인 주차구역을 <br />우선 배정해드립니다.</div>
      </button>
    </div>
  </div>
</template>

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

.message {
  color: #000;
  font-size: 2em;
  text-align: center;
  margin-bottom: 20px; /* Space between message and buttons */
}

.button-container {
  display: flex;
  flex-direction: row;
  justify-content: center; /* 버튼을 가운데 정렬합니다 */
  align-items: center; /* 버튼을 수직으로 가운데 정렬합니다 */
  gap: 20px; /* 버튼 사이의 간격을 조정합니다 */
  padding: 0 20px; /* 좌우 패딩을 유지합니다 */
}

.parking-option {
  border: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 50px;
  border-radius: 10px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.1s ease-in-out;
  flex: 1; /* 버튼이 동일한 비율로 크기를 조정하도록 flex 속성을 추가합니다 */
  margin: 0 10px; /* 버튼 사이의 간격을 추가합니다 */
  font-size: 15px;
  max-width: 266px; /* 버튼의 최대 너비를 설정합니다 */
  height: 561px; /* 버튼의 높이를 유지합니다 */
}
.parking-option > :first-child {
  margin-bottom: 20px; /* 첫 번째 요소와 두 번째 요소 사이의 간격을 늘립니다 */
}

.parking-option > :last-child {
  margin-top: 20px; /* 마지막 요소 위에 간격을 늘립니다 */
}
/* Additional hover effect */
.parking-option:hover {
  transform: scale(1.02);
}

/* Active/click effect */
.parking-option:active {
  transform: scale(0.98);
}
</style>
