import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";
import moment from "moment";

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

  //플로팅 알림 띄우기
  const sendNotification = async (data) => {
    console.log("플로팅 알림을 띄우자");
    showNotification.value = true;
    location.value = data.park;
    time.value = data.time;
    // setTimeout(() => {
    //   closeNotification();
    // }, 5000); // 5초 후에 알림을 닫음 (조절 가능)
  };

  //플로팅 알림 닫기
  const closeNotification = () => {
    showNotification.value = false;
    location.value = "";
    time.value = "";
  };

  //공통바 업데이트
  const updateBar = async () => {
    const resp = await axios.get("http://localhost:8080/admin/common");
    const commonData = resp.data;
    console.log("alert.js 실행됨 : ", commonData);
    total_cnt.value = commonData.total_cnt;
    current_cnt.value = commonData.curr_cnt;
    parks.value = commonData.parks;
    processing_details.value = commonData.processing_details;
    processing_details.value.forEach((item) => {
      item.time = moment(item.time).format("YYYY-MM-DD HH:mm:ss");
    });

    console.log(total_cnt.value);
    console.log(current_cnt.value);
  };

  return {
    showNotification,
    location,
    time,
    total_cnt,
    current_cnt,
    parks,
    processing_details,
    sendNotification,
    closeNotification,
    updateBar,
  };
});
