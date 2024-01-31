<template>
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

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import axios from "axios";
import router from "@/router";
import { useParkingStore } from "@/stores/parkingStore";

const currentTime = ref(getCurrentTime());
const allocatedLocation = ref("");
const parkingStore = useParkingStore();

onMounted(() => {
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
  axios
    .post(
      "http://localhost:8080/park/allocation",
      {
        car_no: "10가1234",
        is_disabled: isDisabled,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
    .then((response) => {
      console.log(response.data.allocatedLocation);
      // allocatedLocation.value = response.data.allocatedLocation;
      parkingStore.allocatedLocation = response.data.allocatedLocation;
      router.push("/fourth");
    })
    .catch((error) => {
      console.error("자리 없음:", error);
      allocatedLocation.value = "";
      router.push("/second");
    });
}
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
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 2em;
  color: #333;
  z-index: 10;
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
  padding: 15px;
  border-radius: 10px; /* 'px' 단위가 빠져 있어 추가했습니다 */
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.1s ease-in-out;
  flex: 1; /* 버튼이 동일한 비율로 크기를 조정하도록 flex 속성을 추가합니다 */
  margin: 0 10px; /* 버튼 사이의 간격을 추가합니다 */
  font-size: 18px;
  max-width: 266px; /* 버튼의 최대 너비를 설정합니다 */
  height: 561px; /* 버튼의 높이를 유지합니다 */
}

/* 이제 버튼들이 가운데에 정렬될 것입니다. */

/* Additional hover effect */
.parking-option:hover {
  transform: scale(1.02);
}

/* Active/click effect */
.parking-option:active {
  transform: scale(0.98);
}
</style>
