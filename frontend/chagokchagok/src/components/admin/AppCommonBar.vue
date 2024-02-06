<script setup>
import { RouterLink, RouterView } from "vue-router";
import { ref, onMounted } from "vue";
import { Chart as ChartJS } from "chart.js/auto";
import { notificationStore } from "@/stores/alert.js";
import { storeToRefs } from "pinia";
const store = notificationStore();
const { total_cnt, current_cnt, parks, processing_details } =
  storeToRefs(store);
const doughnutChart = ref(null);

onMounted(async () => {
  console.log("==========");
  await store.updateBar();
  console.log("alert.js store 업데이트 끝 : ", current_cnt.value);
  drawDoughtnut();
});

function addColor() {
  const percentage = (current_cnt.value / total_cnt.value) * 100;

  if (percentage < 40) {
    return "green-text";
  } else if (percentage < 70) {
    return "orange-text";
  } else {
    return "red-text";
  }
}

function drawDoughtnut() {
  const ctx = doughnutChart.value.getContext("2d");
  console.log(total_cnt.value - current_cnt.value, current_cnt.value);
  new ChartJS(ctx, {
    type: "doughnut",
    data: {
      datasets: [
        {
          data: [current_cnt.value, total_cnt.value - current_cnt.value],
          backgroundColor: ["#7459d9", "#efeff0"],
        },
      ],
    },
    options: {
      responsive: false,
      cutout: "75%",
      radius: "100%",
      plugins: {
        legend: {
          display: false,
        },
        tooltip: {
          //enabled: false, // 튤팁 활성화 (기본값 true)
        },
      },
      elements: {
        arc: {
          spacing: 0,
          borderRadius: 5,
        },
      },
    },
  });
}
</script>
<template>
  <div class="bar-content">
    <div>
      <h3>Parking Rate</h3>
      <div class="common-content">
        <div class="rate-box">
          <div class="circle">
            <canvas ref="doughnutChart" style="height: 70px"> </canvas>
          </div>
          <div id="chart-percent">{{ (current_cnt / total_cnt) * 100 }}%</div>

          <h2>
            <span :class="addColor()">{{ current_cnt }}</span> /
            {{ total_cnt }}
          </h2>
        </div>
      </div>
    </div>
    <div>
      <h3 class="second-h3">처리내역</h3>
      <div class="common-content">
        <div class="white-box">
          <div v-for="item in processing_details" :key="item.id">
            <div class="processing-box" v-if="item.code === 'SENSOR_ERROR'">
              <img
                src="/public/icon/hotline.png"
                alt="Sensor Error Icon"
                class="processing-icon"
              />
              <div class="processing-text">
                <div>{{ item.time }}</div>
                <div>[{{ item.location }}] 차단바 해제</div>
              </div>
            </div>
            <div class="processing-box" v-if="item.code === 'AUTO_REPORT'">
              <img
                src="/public/icon/sensor.png"
                alt="Hotline Icon"
                class="processing-icon"
              />
              <div class="processing-text">
                <div>{{ item.time }}</div>
                <div>[{{ item.location }}] 자동신고 가동</div>
              </div>
            </div>
            <div class="processing-box" v-if="item.code === 'HUMAN_ERROR'">
              <img
                src="/public/icon/hotline.png"
                alt="Hotline Icon"
                class="processing-icon"
              />
              <div class="processing-text">
                <div>{{ item.time }}</div>
                <div>[{{ item.location }}] 자리교환</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.green-text {
  color: green;
}

.orange-text {
  color: orange;
}

.red-text {
  color: red;
}

h3 {
  padding: 0;
  margin: 0 0 20px 0;
  font-size: 1rem; /* 모든 h3에 대한 기본 폰트 크기 설정 */
}

.second-h3 {
  margin: 0 0 10px 0;
}
.bar-content {
  /* border: 10px solid black; */
  padding: 3vh 1vw;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  /* align-items: center; */
  gap: 7vh;
}
.common-content {
  width: 100%;
}

.rate-box {
  width: 100%;
  height: 60px; /* 적절한 픽셀 값으로 조정 */
  background-color: rgb(255, 255, 255);
  border-radius: 5px;
  position: relative;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.circle {
  width: 85px; /* 적절한 픽셀 값으로 조정 */
  height: 85px; /* 적절한 픽셀 값으로 조정 */
  border-radius: 100%;
  background-color: rgb(255, 255, 255);
  position: absolute;
  left: 25px;
  top: 50%;
  transform: translateY(-50%);
}
canvas {
  position: absolute;
  left: -27px; /* 적절한 픽셀 값으로 조정 */
  top: 42px; /* 적절한 픽셀 값으로 조정 */
  transform: translateY(-50%);
}
.rate-box h2 {
  position: absolute;
  left: 135px; /* 적절한 픽셀 값으로 조정 */
  top: 30px; /* 적절한 픽셀 값으로 조정 */
  transform: translateY(-50%);
}

.white-box {
  width: 100%;
  background-color: white;
  border-radius: 25px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 10px;
  display: flex;
  flex-direction: column;
  gap: 13px;
}
ul,
ol {
  list-style: none;
}

.processing-box {
  display: flex;
  gap: 20px;
}
.processing-icon {
  width: 2.5vw;
  height: 2.5vw;
}

.processing-text > div:first-child {
  color: rgb(216, 216, 216);
  font-size: 0.9rem;
}
.processing-text > div:nth-child(2) {
  color: rgb(46, 46, 46);
  font-size: 0.9rem;
  font-weight: bold;
}

/* .processing-text {
  border-bottom: 1px solid rgb(224, 224, 224);
} */

#chart-percent {
  position: absolute;
  left: 55px;
  top: 18px;
  font-size: 1rem;
  font-weight: bold;
}
</style>
