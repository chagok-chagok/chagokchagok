<script setup>
import { storeToRefs } from "pinia";
import { notificationStore } from "@/stores/alert.js";
const store = notificationStore();
const { showNotification, location, time, closeNotification } =
  storeToRefs(store);

const beforeEnter = (el) => {
  el.style.opacity = "0";
  el.style.transform = "translateY(-20px)";
};

const enter = (el, done) => {
  el.offsetHeight; // 강제 리플로우를 유발하여 애니메이션 트리거
  el.style.transition = "opacity 0.5s, transform 0.5s";
  el.style.opacity = "1";
  el.style.transform = "translateY(0)";
  done();
};

const leave = (el, done) => {
  el.style.transition = "opacity 0.5s, transform 0.5s";
  el.style.opacity = "0";
  el.style.transform = "translateY(-20px)";
  done();
};
</script>

<template>
  <div>
    <transition
      name="notification-fade"
      appear
      @before-enter="beforeEnter"
      @enter="enter"
      @leave="leave"
    >
      <div key="notification" v-if="showNotification" class="notification">
        <!-- 상단 파란색 네모 -->
        <div class="notification-header">
          <span
            ><span
              ><font-awesome-icon
                :icon="['fas', 'square']"
                style="color: #3a57e8"
            /></span>
            &nbsp;자동신고시스템</span
          >
          <span class="close-button" @click="store.closeNotification"
            ><span
              ><span class="time"
                >{{ time[3] }}:{{ time[4] }}:{{
                  time[5]
                }}&nbsp;&nbsp;&nbsp;</span
              > </span
            ><font-awesome-icon
              :icon="['fas', 'xmark']"
              size="xl"
              style="color: #4c5365"
          /></span>
        </div>
        <!-- 하단 내용 -->
        <div class="notification-content">
          <p>출차중인 [{{ location }}] 자리에서 차량이 인식됨.</p>
        </div>
      </div>
    </transition>
    <!-- 나머지 내용 -->
  </div>
</template>

<style scoped>
.notification-fade-enter-active,
.notification-fade-leave-active {
  transition: opacity 0.5s, transform 0.5s;
}
.notification-fade-enter,
.notification-fade-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}
.notification {
  position: fixed;
  background-color: #fff;
  /* border: 2px solid #ccc; */
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column; /* 상하로 배치 */
  width: 300px; /* 가로 길이 조절 */
  border-radius: 5px;
  overflow: hidden;
}

.notification-header {
  background-color: #ffffff;
  color: #505050; /* 글자색 */
  font-weight: bold;
  font-size: 0.9rem;
  padding: 6px 10px;
  display: flex;
  justify-content: space-between; /* 간격 벌리기 */
}

.close-button {
  cursor: pointer;
}

.notification-content {
  background-color: #dedfdf; /* 진회색 배경색 */
  padding: 10px;
}

.notification-content p {
  margin: 0; /* 기본 마진 제거 */
  color: #6f6f6f; /* 글자색 */
  font-size: 0.8rem;
}

.time {
  color: #cdcdcd;
  font-weight: lighter;
  font-size: 0.9rem;
}
</style>
