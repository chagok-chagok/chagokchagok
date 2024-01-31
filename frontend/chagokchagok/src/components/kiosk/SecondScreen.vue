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

<script>
export default {
  name: "SecondScreen",
  data() {
    return {
      currentTime: this.getCurrentTime(),
    };
  },
  mounted() {
    setInterval(() => {
      this.currentTime = this.getCurrentTime();
    }, 1000);
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
