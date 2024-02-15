import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";
import moment from "moment";
import { instance } from "@/utils/mainAxios";

const local = instance;

export const notificationStore = defineStore("notificationStore", () => {
  //플로팅알림 변수
  const showNotification = ref(false);
  const location = ref("");
  const time = ref("");

  //공통바 변수
  const total_cnt = ref();
  const current_cnt = ref();
  const parks = ref([]);
  const processing_details = ref([]);

  //대시보드 차트의 변수
  const percent = ref();
  const currentPercent = ref(0);
  const labels = ref([
    "00",
    "01",
    "02",
    "03",
    "04",
    "05",
    "06",
    "07",
    "08",
    "09",
    "10",
    "11",
    "12",
    "13",
    "14",
    "15",
    "16",
    "17",
    "18",
    "19",
    "20",
    "21",
    "22",
    "23",
  ]);
  const today_visits = ref([]);
  const pre_visits = ref([]);
  const sensorErrorCnt = ref();
  const humanErrortCnt = ref();
  const autoReportCnt = ref();
  const totalReportCnt = ref();
  const unresolvedCnt = ref();
  const unresolvedReport = ref(null);

  //SSE 연결상태를 표시하는 변수
  const sseStatus = ref("disconnected");

  //플로팅 알림 띄우기
  const sendNotification = async (data) => {
    updateVisitChart();
    showNotification.value = true;
    location.value = data.park;
    time.value = data.time;
    setTimeout(() => {
      closeNotification();
    }, 17000);
  };

  //플로팅 알림 닫기
  const closeNotification = () => {
    showNotification.value = false;
    location.value = "";
    time.value = "";
  };

  //공통바 업데이트
  const updateBar = async () => {
    local.defaults.headers["Authorization"] =
      sessionStorage.getItem("accessToken");
    const { data } = await local.get("/admin/common");
    const commonData = data;
    total_cnt.value = commonData.total_cnt;
    current_cnt.value = commonData.curr_cnt;
    parks.value = commonData.parks;
    processing_details.value = commonData.processing_details;
    processing_details.value.forEach((item) => {
      item.time = moment(item.time).format("YYYY-MM-DD HH:mm:ss");
    });
  };

  //대시보드 차트 업데이트
  const updateVisitChart = async () => {
    local.defaults.headers["Authorization"] =
      sessionStorage.getItem("accessToken");
    const { data } = await local.get("/admin/dashboard");
    const dashboardData = data;
    today_visits.value = dashboardData.today_visits;
    pre_visits.value = dashboardData.previous_visits;
    sensorErrorCnt.value = dashboardData.report_rate.sensor;
    humanErrortCnt.value = dashboardData.report_rate.human;
    autoReportCnt.value = dashboardData.report_rate.auto;
    totalReportCnt.value = dashboardData.total_report;
    unresolvedCnt.value = dashboardData.unresolvedDto.cnt;
    unresolvedReport.value = dashboardData.unresolvedDto.report_data;
    percent.value =
      (dashboardData.unresolvedDto.cnt / dashboardData.total_report) * 100;
  };

  //SSE 상태 업데이트
  const updateSSEStatus = (status) => {
    sseStatus.value = status;
  };

  return {
    showNotification,
    location,
    time,
    total_cnt,
    current_cnt,
    parks,
    processing_details,
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
    sendNotification,
    closeNotification,
    updateBar,
    updateVisitChart,
    sseStatus,
    updateSSEStatus,
  };
});
