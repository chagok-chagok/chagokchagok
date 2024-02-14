<script setup>
import { ref, onMounted } from "vue";
import { RouterLink, RouterView } from "vue-router";
import NavbarVue from "@/components/admin/navbar.vue";
import AppCommonBar from "@/components/admin/AppCommonBar.vue";
import AppFloatingAlert from "@/components/admin/AppFloatingAlert.vue";
import { notificationStore } from "@/stores/alert.js";
import { useParkingSectionStore } from "@/stores/parkingSectionStore";
const store = notificationStore();
const parkingSectionStore = useParkingSectionStore();
const { VITE_VUE_SPRING_URL } = import.meta.env;
//SSE 알림이 발생할때마다 공통바 업데이트
const adminUrl = `${VITE_VUE_SPRING_URL}sse/admin`;
const sseEvent = new EventSource(adminUrl);
onMounted(() => {
  //연결 리스너
  sseEvent.addEventListener("open", function (e) {
    //캐치할 에러코드를 써줌
    console.log(e.data);
    console.log("연결됐어~~~~~~~");
    store.updateSSEStatus("connected");
  });
  //에러 리스너
  sseEvent.addEventListener("error", function (e) {
    console.log(e);
    console.log("응 안됐어~~~~~~~");
    store.updateSSEStatus("disconnected");
  });
  //자동신고시스템 - 플로팅알림
  sseEvent.addEventListener("SENSOR_REPORT", function (e) {
    const data = JSON.parse(e.data);
    store.sendNotification(data);
  });
  //공통바 업데이트
  sseEvent.addEventListener("REALTIME_COMMON", function (e) {
    store.updateBar();
    store.updateVisitChart();
    parkingSectionStore.update();
  });
});
</script>
<template>
  <main>
    <div class="container">
      <section class="nav"><NavbarVue /></section>
      <section class="under">
        <div class="content">
          <RouterView />
        </div>
        <div class="common-bar"><AppCommonBar /></div>
      </section>
      <div class="alert">
        <AppFloatingAlert />
      </div>
    </div>
  </main>
</template>
<style scoped>
main {
  margin: 0;
  padding: 0;
  background-color: #f2f2f7;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
/* 흰색 박스 */
.container {
  width: 71vw;
  height: 90vh;
  background-color: #ffffff;
  border-radius: 20px;
  /* box-shadow: 0 0 20px rgba(0, 0, 0, 0.2); */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
  overflow: hidden;
  /* padding: 20px; */
  position: relative;
}
.nav {
  border-bottom: 1px solid #f2f2f7;
  height: 8vh;
  display: flex;
}
/* 아랫쪽 섹션 : 내용블록 + 공통바 */
.under {
  display: flex;
  /* background-color: antiquewhite; */
  width: 100%;
  height: 91%;
  /* border: 2px solid violet; */
}
/* 탭마다 다른 컨텐츠가 들어가는 블록 */
.content {
  /* background-color: antiquewhite; */
  width: 76%;
  height: 100%;
  padding: 1.5vh 1.5vw;
}
/* 공통바 */
.common-bar {
  width: 24%;
  height: 100%;
  background-color: #f2f2f7;
}
/* 플로팅알림 */
.alert {
  position: absolute; /* 절대적 위치 지정 */
  bottom: 120px; /* 원하는 값으로 조절 */
  right: 320px; /* 원하는 값으로 조절 */
}
</style>
