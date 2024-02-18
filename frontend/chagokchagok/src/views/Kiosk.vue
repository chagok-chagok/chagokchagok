<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import axios from "axios";
import router from "@/router";
import { useParkingStore } from "@/stores/parkingStore";
import { RouterView } from "vue-router";

import { useKioskStore } from "@/stores/kiosk";

import { storeToRefs } from "pinia";
const kioskStore = useKioskStore();
const {
  currentTime,
  carNumber,
  isDisabled,
  updateCurrentTime,
  allocation,
  selectParking,
} = storeToRefs(kioskStore);

const { VITE_VUE_SPRING_URL } = import.meta.env;

const kioskUrl = `${VITE_VUE_SPRING_URL}sse/kiosk`; //kiosk의 sse접속 url입니다.
const allocatedLocation = ref("");
const parkingStore = useParkingStore();

onMounted(() => {
  const sseEvent = new EventSource(kioskUrl);

  //연결 리스너
  sseEvent.addEventListener("open", function (e) {
    //캐치할 에러코드를 써줌
  });

  //에러 리스너
  sseEvent.addEventListener("error", function (e) {});

  // 자리 배정 이벤트
  sseEvent.addEventListener("VALID_CAR_NUM", function (e) {
    // 차 번호 입력받았으니 장애 여부 입력받고 자리 할당 api 호출
    carNumber.value = e.data;
    parkingStore.car_no = carNumber.value;
  });
  // 정규식 틀렸을 경우
  sseEvent.addEventListener("INVALID_CAR_NUM", function (e) {
    // 잘못찍힘 화면으로 보냄
    router.push({ name: "recognition-error" });
  });
});
</script>

<template>
  <div>
    <RouterView />
  </div>
</template>

<style scoped></style>
