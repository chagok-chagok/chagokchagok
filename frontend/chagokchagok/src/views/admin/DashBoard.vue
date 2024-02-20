<script setup>
import { RouterLink, RouterView } from "vue-router";
import AppPageHeader from "@/components/admin/AppPageHeader.vue";
import { ref, onMounted, watchEffect } from "vue";
import moment from "moment";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js/auto"; // Chart.js를 가져옴
import { notificationStore } from "@/stores/alert.js";
import { storeToRefs } from "pinia";
const store = notificationStore();
const {
  percent,
  currentPercent,
  labels,
  today_visits,
  pre_visits,
  sensorErrorCnt,
  humanErrortCnt,
  autoReportCnt,
  totalReportCnt,
  unresolvedCnt,
  unresolvedReport,
} = storeToRefs(store);

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Tooltip
);

const lineChart = ref(null);
const doughnutChart = ref(null);

//update를 위한 변수
let lineChartRef = null;
let doughnutChartRef = null;

onMounted(async () => {
  await store.updateVisitChart();
  //라인차트
  drawLine();
  //도넛차트
  drawDoughtnut();
  //프로그래스바
  startAnimation();
  //실시간 변화시 차트 업데이트
  watchEffect(() => {
    //라인차트 업데이트
    lineChartRef.data.datasets[0].data = today_visits.value;
    lineChartRef.data.datasets[1].data = pre_visits.value;
    lineChartRef.update();
    //도넛차트 업데이트
    doughnutChartRef.data.datasets[0].data = [
      autoReportCnt.value,
      humanErrortCnt.value,
      sensorErrorCnt.value,
    ];
    doughnutChartRef.update();
  });
});

function drawDoughtnut() {
  const ctx = doughnutChart.value.getContext("2d");

  doughnutChartRef = new ChartJS(ctx, {
    type: "doughnut",
    data: {
      labels: ["AUTO-REPORT", "HUMAN_ERROR", "SEONSOR_ERROR"],
      datasets: [
        {
          data: [
            autoReportCnt.value,
            humanErrortCnt.value,
            sensorErrorCnt.value,
          ],
          backgroundColor: ["#B9ABEB", "#E3DEF7", "#7459D9"],
        },
      ],
    },
    options: {
      cutout: "75%",
      radius: "100%",
      plugins: {
        legend: {
          display: false,
        },
      },
      elements: {
        arc: {
          // spacing: 1,
          borderRadius: 15,
        },
      },
    },
  });
}

function drawLine() {
  const ctx = lineChart.value.getContext("2d");
  let gradientFill = ctx.createLinearGradient(0, 0, 0, 200);
  gradientFill.addColorStop(0, "rgb(63,102,181,0.3)");
  gradientFill.addColorStop(1, "rgba(0, 0, 0, 0)");

  const data = {
    labels: labels.value,

    datasets: [
      {
        label: "Today",
        data: today_visits.value,
        borderColor: "#3F66B5",
        tension: 0.5,
        pointRadius: 0,
        pointBorderWidth: 5,
        pointHoverRadius: 5,
        pointHoverBorderWidth: 1,
        pointBackgroundColor: "#3F66B5",
        fill: true,
        backgroundColor: gradientFill,
        borderWidth: 2.5,
      },
      {
        label: "Previous Day",
        data: pre_visits.value,
        fill: false,
        borderColor: "#ECE9F1",
        tension: 0.5,
        pointRadius: 0,
        borderWidth: 2.5, // 선의 두께 설정
      },
    ],
  };

  lineChartRef = new ChartJS(ctx, {
    type: "line",
    data: data,
    options: {
      interaction: {
        intersect: false,
      },
      maintainAspectRatio: false,
      plugins: {
        tooltip: {
          enabled: true, // 튤팁 활성화 (기본값 true)
          backgroundColor: "#000", // 튤팁 색상
          padding: 10, // 튤팁 패딩
        },
        legend: {
          display: false, // 범례 활성화 (기본값 true)
        },
      },
      scales: {
        y: {
          ticks: {
            color: "#b6baca",
            stepSize: 10,
          },
          grid: {
            drawTicks: false,
            color: "#ECE9F1",
            lineWidth: 1,
          },
          border: {
            display: false,
          },
        },
        x: {
          ticks: {
            color: "#b6baca",
            callback: (value, index) => {
              return index % 3 === 0 ? value : "";
            },
          },
          grid: {
            display: false,
          },
          border: {
            display: false,
          },
        },
      },
    },
  });
}

function startAnimation() {
  const framesPerSecond = 60;
  const totalFrames = 0.2 * framesPerSecond; // Animation duration in seconds

  const interval = setInterval(() => {
    if (currentPercent.value < percent.value) {
      currentPercent.value += (1 / totalFrames) * percent.value;
    } else {
      clearInterval(interval);
    }
  }, 1000 / framesPerSecond);
}
</script>

<template>
  <div class="dashboard-main">
    <AppPageHeader icon="chart-line" pageTitle="실시간 방문차량 대수" />
    <section class="park-info">
      <div id="chart-info">
        <h4 id="chart_left" class="title">Total Visits</h4>
        <div id="chart_right">
          <div class="today-line"></div>
          <p>ToDay</p>
          <div class="horizontal-line"></div>
          <p>Previous Day</p>
        </div>
      </div>
      <div id="total-visit-chart">
        <canvas ref="lineChart"> </canvas>
      </div>
    </section>
    <section class="report-info">
      <div id="report-info-rate">
        <h4 class="title">Report Rate</h4>
        <div class="report-rate">
          <div class="report-chart">
            <h3 id="report-chart-total">
              {{ Number(totalReportCnt).toLocaleString() }}
            </h3>
            <span>reports</span>
            <div id="doughnut_chart">
              <canvas ref="doughnutChart"> </canvas>
            </div>
          </div>
          <div class="report-data">
            <div class="report-data-card">
              <div class="horizontal-line" id="auto-line"></div>
              <div>
                <p class="semi_big">AUTO-REPORT</p>
                <p class="bold content">
                  {{ Number(autoReportCnt).toLocaleString() }} cases
                </p>
              </div>
            </div>
            <div class="report-data-card">
              <div class="horizontal-line" id="human-line"></div>
              <div>
                <p class="semi_big">HUMAN-ERROR</p>
                <p class="bold content">
                  {{ Number(humanErrortCnt).toLocaleString() }} cases
                </p>
              </div>
            </div>
            <div class="report-data-card">
              <div class="horizontal-line" id="sensor-line"></div>
              <div>
                <p class="semi_big">SENSOR-ERROR</p>
                <p class="bold content">
                  {{ Number(sensorErrorCnt).toLocaleString() }} cases
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="report-info-unresolved">
        <h5 class="title">Unresolved</h5>
        <div class="unresolved">
          <p class="bold content">
            최근한달간 <span class="red">{{ unresolvedCnt }}</span
            >건의 미해결 신고건이 있습니다.
          </p>

          <div class="unresolved-chart">
            <div class="flex content bold">
              <p>{{ unresolvedCnt }}/{{ totalReportCnt }} Total</p>
            </div>
            <div class="progress-container">
              <div
                class="progress-bar"
                :style="{ width: currentPercent + '%' }"
              ></div>
            </div>
          </div>
          <div class="unresolved-table">
            <table>
              <tr>
                <th>No</th>
                <th>Time</th>
                <th>Code</th>
                <th>비고</th>
              </tr>
              <tr v-for="item in unresolvedReport" :key="item.no">
                <td>{{ item.no }}</td>
                <td>{{ moment(item.time).format("YYYY-MM-DD HH:mm:ss") }}</td>
                <td>{{ item.code }}</td>
                <td>{{ item.note || "-" }}</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
h1,
h2,
h3,
h4,
h5,
h6 {
  margin: 2px;
  padding: 0;
  font-size: 1em;
  /* 기타 초기화할 스타일 속성들 */
}
.title {
  font-size: 0.9rem;
  margin-bottom: 3px;
}
.bold {
  font-weight: bold;
}

p {
  margin: 0px;
  padding: 0;
  font-size: 1em;
  /* 기타 초기화할 스타일 속성들 */
}

.today-line {
  background-color: #3f66b5;

  width: 60px;
  height: 3px;
  /* border: 1px solid black; */
  border-radius: 40px;
  margin-top: 12px;
}
.dashboard-main {
  display: flex;
  flex-direction: column;
  /* background-color: rgb(156, 151, 211); */
  /* border: 1px solid black; */
  width: 100%;
  height: 100%;
  gap: 0%;
}

/* 윗쪽 선차트 섹션 */
.park-info {
  display: flex;
  flex-direction: column;
  /* background-color: azure; */
  height: 50%;
  margin-top: 10px;
}

#total-visit-chart {
  width: 100%;
  height: 100%;
  /* background-color: rgb(255, 255, 255); */
}

/* 아랫쪽 섹션 */
.report-info {
  display: flex;
  /* background-color: aliceblue; */
  width: 100%;
  height: 50%;
  gap: 10px;
  margin-top: 30px;
}

/* Report Rate 섹션 - 도넛차트 */
#report-info-rate {
  width: 45%;
  /* background-color: rgb(139, 159, 177); */
  height: 100%;
}
#report-info-unresolved {
  width: 55%;
  /* background-color: rgb(122, 140, 155); */
  height: 100%;
}

.report-rate {
  display: flex;
  gap: 5%;
  height: 80%;
  width: 100%;
}

.report-chart {
  /* background-color: aliceblue; */
  width: 45%;
  position: relative;
}

.report-data {
  padding-top: 4%;
  /* background-color: aliceblue; */
  width: 45%;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.unresolved {
  display: flex;
  flex-direction: column;
  /* background-color: rgb(179, 179, 179); */
  height: 80%;
  gap: 3%;
}

.unresolved-chart {
  /* background-color: aliceblue; */
  width: 90%;
  margin: 0% 0%;
}

.unresolved-table {
  /* background-color: aliceblue; */
  height: 45%;
  width: 90%;
  font-size: 0.7rem;
}

.horizontal-line {
  width: 60px;
  height: 3px;
  /* border: 1px solid black; */
  border-radius: 40px;
  background-color: #edeaf2;
  margin-top: 12px;
}

#chart-info {
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between; /* 양쪽 끝으로 정렬 */
}

#chart_right {
  display: flex;
  flex-direction: row;
  gap: 20px;
  font-size: 0.8rem;
}

#report-chart-total {
  position: absolute;
  top: 38%;
  left: 49%;
  transform: translate(-50%, -50%);
}
.report-chart span {
  font-weight: lighter;
  color: #d9d9d9;
  font-size: 20px;
  position: absolute;
  top: 55%;
  left: 49%;
  transform: translate(-50%, -50%);
}

#doughnut_chart {
  position: absolute;
  width: 100%;
  top: 45%;
  left: 50%;
  transform: translate(-50%, -50%);
}

#report-chart-total {
  font-size: 4vh;
}

table {
  width: 100%;
  border-collapse: collapse;
  /* font-size: 14px; */
}
th,
td {
  border: 1px solid rgb(241, 241, 241);
  padding: 8px;
  text-align: left;
  text-align: center; /* 헤더 중앙 정렬 추가 */
}

th {
  background-color: #f9f9fc;
}

#sensor-line {
  width: 30px;
  border: 2px solid #cfdaff;
  border-radius: 40px;
  background-color: #cfdaff;
}

#human-line {
  width: 30px;
  border: 2px solid #99ade2;
  border-radius: 40px;
  background-color: #99ade2;
}

#auto-line {
  width: 30px;
  border: 2px solid #0007b6;
  border-radius: 50px;
  background-color: #0007b6;
}

.report-data-card {
  display: flex;
  gap: 10px;
}
.progress-container {
  width: 100%;
  height: 12px;
  background-color: #e0eaff;
  border-radius: 20px;
  overflow: hidden;
  margin-bottom: 10px;
}

.progress-bar {
  height: 100%;
  background-color: #255bc6;
  transition: width 0.3s ease;
  border-radius: 20px;
}

.red {
  color: rgb(206, 0, 0);
}

.flex {
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
}

.flex p {
  color: #7c7c7c;
}

.content {
  font-size: 0.9rem;
}

.semi_big {
  font-size: 0.9rem;
}
</style>
