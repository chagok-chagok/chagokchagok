<template>
  <div class="screen-container">
    <div class="time-display">{{ currentTime }}</div>
    <div class="status-message">만차입니다.</div>
    <img
      class="parking-image"
      src="@/assets/주차안내 이미지 3.png"
      alt="만차안내"
    />
    <div class="additional-message">자리가 생기면 안내해 드리겠습니다.</div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import router from "@/router";

// 현재 시간을 가져오는 함수
function getCurrentTime() {
  const now = new Date();
  return now.toTimeString().substring(0, 5);
}

// 현재 시간을 HH:MM 형식으로 반환하는 함수
const currentTime = ref(getCurrentTime());
const kioskUrl = "http://localhost:8080/api/sse/kiosk";

// 인터벌을 설정하여 현재 시간을 매초마다 업데이트
onMounted(() => {
  const sseEvent = new EventSource(kioskUrl);

  //연결 리스너
  sseEvent.addEventListener("open", function (e) {
    //캐치할 에러코드를 써줌
    console.log("open");
    console.log(e.data);
  });

  //에러 리스너
  sseEvent.addEventListener("error", function (e) {
    console.log("error");
    console.log(e);
  });

  // SENSOR_REPORT 상태코드 리스너 << 이런식으로 등록하면 됨
  sseEvent.addEventListener("CONGESTION_CLEAR", function (e) {
    //캐치할 상태코드를 써줌
    console.log("catch");
    console.log(e.data);
    router.push("/fourth");
  });

  setInterval(() => {
    currentTime.value = getCurrentTime();
  }, 10000000);
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
  justify-content: center;
  align-items: center;
}

.time-display {
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 2em;
  color: #333;
}

.status-message {
  color: #000;
  font-size: 2em;
  text-align: center;
  margin-bottom: 20px;
}

.parking-image {
  max-width: 50%;
  margin: 20px 0;
}

.additional-message {
  font-size: 1.5em;
  text-align: center;
  color: #000;
  margin-bottom: 20px;
}
</style>
