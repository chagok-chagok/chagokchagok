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
    <div class="additional-message">후진 후 앞으로 천천히 움직여 주세요.</div>
  </div>
</template>

<script>
export default {
  name: "ThirdScreen",
  data() {
    return {
      currentTime: this.getCurrentTime(),
    };
  },
  mounted() {
    this.interval = setInterval(() => {
      this.currentTime = this.getCurrentTime();
    }, 1000);
  },
  beforeDestroy() {
    clearInterval(this.interval);
  },
  methods: {
    getCurrentTime() {
      const now = new Date();
      return now.toTimeString().substring(0, 5);
    },
  },
};
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
  color: #d9534f;
  font-size: 2em;
  text-align: center;
  margin-bottom: 20px;
}

.parking-image {
  max-width: 100%; /* 이미지가 컨테이너를 넘지 않도록 설정 */
  height: auto; /* 이미지 높이를 자동으로 설정하여 비율 유지 */
}

.additional-message {
  font-size: 1.5em;
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}
.parking-image-container {
  background-color: #ffffff; /* 흰색 배경 설정 */
  width: 100%; /* 컨테이너의 가로 폭을 화면에 꽉 차도록 설정 */
  display: flex; /* Flexbox를 사용하여 내용물을 중앙에 배치 */
  justify-content: center; /* 가로 방향 중앙 정렬 */
  align-items: center; /* 세로 방향 중앙 정렬 */
  padding: 20px; /* 이미지 주변에 패딩 추가 */
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
  margin: 20px 0; /* 위아래 마진 추가 */
}
</style>
