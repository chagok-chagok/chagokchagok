<script setup>
import { ref, onMounted, onUnmounted } from "vue";
// import router from "vue-router";
import { useParkingStore } from "@/stores/parkingStore";
import { instance } from "@/utils/mainAxios";
import { useRouter } from "vue-router";
import printJs from "print-js";

const local = instance;

const { VITE_VUE_SPRING_URL } = import.meta.env;
const kioskUrl = `${VITE_VUE_SPRING_URL}sse/kiosk`; //kiosk의 sse접속 url입니다.
const currentTime = ref(getCurrentTime());
const allocatedLocation = ref("");
const parkingStore = useParkingStore();
const carNumber = ref("");
const router = useRouter();
const printObj = ref([]);

router.beforeEach((to, from, next) => {
  if (from.name === "allocation" && to.name === "choice-screen") {
    carNumber.value = "";
  }
  next();
});

onMounted(() => {
  const sseEvent = new EventSource(kioskUrl);

  //연결 리스너
  sseEvent.addEventListener("open", function (e) {
    //캐치할 에러코드를 써줌
    console.log("연결되었습니다.", e.data);
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

function selectParking(isDisabled) {
  if (!carNumber.value) {
    console.error("차번호 인식 실패");
    router.push({ name: "recognition-error" });
    return; // 함수 실행 중단
  }

  console.log("지금 전송할 차번호는 ", carNumber.value);
  local.defaults.headers["Authorization"] =
    sessionStorage.getItem("accessToken");
  if (isDisabled) {
    router.push({ name: "allocation" });
  } else {
    router.push({ name: "recommendation" });
  }
  local
    .post(
      `${VITE_VUE_SPRING_URL}park/allocation`,
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
      // parkingStore에 저장할 정보 추가(프린터용)
      parkingStore.allocated_location = response.data.allocated_location;
      parkingStore.car_no = response.data.car_no;
      parkingStore.entry_time = response.data.entry_time;
      printObj.value.push({
        allocated_location: parkingStore.allocated_location,
        car_no: parkingStore.car_no,
        entry_time: parkingStore.entry_time,
      });

      // 프린트 기능
      // print();
    })
    .catch((error) => {
      console.error("자리 없음:", error);
      allocatedLocation.value = "";
      router.push({ name: "no-place" });
    });
}

const print = () => {
  printJs({
    printable: printObj.value,
    properties: [
      { field: "allocated_location", displayName: "배정 자리" },
      { field: "car_no", displayName: "차량 번호" },
      { field: "entry_time", displayName: "입차 시간" },
    ],
    type: "json",
  });
};
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
    <div v-if="carNumber" class="number">[{{ carNumber }}]님 환영합니다.</div>
    <div class="message"><strong>장애 차량 여부를 선택해주세요.</strong></div>
    <div class="button-container">
      <button class="parking-option" @click.prevent="selectParking(false)">
        <img src="@/assets/주차안내 이미지 1.png" alt="일반 주차" />
        <div>일반 주차구역을 <br />배정해드립니다.</div>
      </button>
      <button class="parking-option" @click.prevent="selectParking(true)">
        <img src="@/assets/주차안내 이미지 2.png" alt="장애인 주차" />
        <div style="padding-bottom: 11%">
          장애인 주차구역을 <br />우선 배정해드립니다.
        </div>
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
  background: rgb(85, 153, 255);
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
  margin-bottom: 100px; /* Space between message and buttons */
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
  font-family: "NanumBarunGothic", sans-serif;
  border: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  border-radius: 10px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.1s ease-in-out;
  flex: 1;
  margin: 0 13px;
  font-size: 25px;
  max-width: 266px;
  height: 600px;
}

.parking-option > img {
  margin-bottom: 40%;
  scale: 1;
}

.parking-option > :first-child {
  /* margin-bottom: -100px; 첫 번째 요소와 두 번째 요소 사이의 간격을 늘립니다 */
}
s .parking-option > :last-child {
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

.number {
  font-size: 2.6em;
  text-align: center;
  margin-bottom: 40px;
}
</style>
