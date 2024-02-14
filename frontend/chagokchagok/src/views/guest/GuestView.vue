<script setup>
import AppSectionGuest from "@/components/layout/AppSectionGuest.vue";
import AppSectionMiddleGuestVue from "@/components/layout/AppSectionMiddleGuest.vue";
import QuestionBubble from "@/components/icons/QuestionBubble.vue";

import { ref } from "vue";
import { useGuestStore } from "@/stores/guest";
import { storeToRefs } from "pinia";
const guestStore = useGuestStore();
guestStore.getParkList();
const { occupied, parks } = storeToRefs(guestStore);

const search = async () => {
  await guestStore.getParkList();
  console.log("parks : ", parks.value);
  console.log("occupied : ", occupied.value);
};

search();
// name: String, isDisabled: Boolean
// 위쪽
const parkingLeftUp = ref([
  { name: "A1", isDisabled: true },
  { name: "A2", isDisabled: false },
  { name: "A3", isDisabled: false },
]);
const parkingMiddleInfoLU = ref([
  { name: "A8", isDisabled: false },
  { name: "A9", isDisabled: false },
  { name: "A10", isDisabled: false },
  { name: "A11", isDisabled: false },
  { name: "B1", isDisabled: false },
  { name: "B2", isDisabled: false },
  { name: "B3", isDisabled: false },
  { name: "B4", isDisabled: false },
]);
const parkingMiddleInfoRU = ref([
  { name: "B9", isDisabled: false },
  { name: "B10", isDisabled: false },
  { name: "B11", isDisabled: false },
  { name: "B12", isDisabled: false },
  { name: "C1", isDisabled: false },
  { name: "C2", isDisabled: false },
  { name: "C3", isDisabled: false },
  { name: "C4", isDisabled: false },
]);
const parkingRightUp = ref([
  { name: "C4", isDisabled: false },
  { name: "C5", isDisabled: false },
  { name: "C6", isDisabled: false },
  { name: "C7", isDisabled: false },
]);

// 아래쪽
const parkingLeftDown = ref([
  { name: "A4", isDisabled: false },
  { name: "A5", isDisabled: false },
  { name: "A6", isDisabled: false },
  { name: "A7", isDisabled: false },
]);
const parkingMiddleInfoLD = ref([
  { name: "A11", isDisabled: false },
  { name: "A12", isDisabled: false },
  { name: "A13", isDisabled: false },
  { name: "A14", isDisabled: false },
  { name: "B5", isDisabled: false },
  { name: "B6", isDisabled: false },
  { name: "B7", isDisabled: false },
  { name: "B8", isDisabled: false },
]);
const parkingMiddleInfoRD = ref([
  { name: "B13", isDisabled: false },
  { name: "B14", isDisabled: false },
  { name: "B15", isDisabled: false },
  { name: "B16", isDisabled: false },
  { name: "C5", isDisabled: false },
  { name: "C6", isDisabled: false },
  { name: "C7", isDisabled: false },
  { name: "C8", isDisabled: false },
]);
const parkingRightDown = ref([
  { name: "C13", isDisabled: false },
  { name: "C14", isDisabled: false },
  { name: "C15", isDisabled: false },
  { name: "C16", isDisabled: false },
]);
</script>

<template class="test">
  <div class="all-container">
    <div class="head-div">
      <div class="parking-title-div">
        <div class="parking-logo">P</div>
        <span class="page-title">차량 배정 현황</span>
      </div>
      <question-bubble></question-bubble>
    </div>
    <div class="content-body">
      <div class="entry-div">
        <button :class="['entry', 'button']">Entry</button>
      </div>

      <div class="area-div">
        <div class="end-width"></div>
        <div>
          <div class="area">A</div>
        </div>
        <div class="middle-width"></div>
        <div class="area"><div class="area">B</div></div>
        <div class="middle-width"></div>
        <div><div class="area">C</div></div>
        <div class="end-width"></div>
      </div>

      <div class="parking-div">
        <div class="section-container">
          <app-section-guest
            :parkings="parkingLeftUp"
            :is-left="true"
            :class="['end-width', 'flex-row-center']"
          ></app-section-guest>
          <app-section-middle-guest-vue
            :parkings="parkingMiddleInfoLU"
            :class="['middle-width', 'flex-row-center']"
          ></app-section-middle-guest-vue>
          <app-section-middle-guest-vue
            :parkings="parkingMiddleInfoRU"
            :class="['middle-width', 'flex-row-center']"
          ></app-section-middle-guest-vue>
          <app-section-guest
            :parkings="parkingRightUp"
            :is-left="false"
            :class="['end-width', 'flex-row-center']"
          ></app-section-guest>
        </div>
        <div class="section-container">
          <app-section-guest
            :parkings="parkingLeftDown"
            :is-left="true"
            :class="['end-width', 'flex-row-center']"
          ></app-section-guest>
          <app-section-middle-guest-vue
            :parkings="parkingMiddleInfoLD"
            :class="['middle-width', 'flex-row-center']"
          ></app-section-middle-guest-vue>
          <app-section-middle-guest-vue
            :parkings="parkingMiddleInfoRD"
            :class="['middle-width', 'flex-row-center']"
          ></app-section-middle-guest-vue>
          <app-section-guest
            :parkings="parkingRightDown"
            :is-left="false"
            :class="['end-width', 'flex-row-center']"
          ></app-section-guest>
        </div>
      </div>

      <div class="exit-div">
        <!-- <div class="pink-circle"></div> -->
        <button :class="['exit', 'button']">Exit</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  border-collapse: collapse;
}
.test {
  display: flex;
  justify-content: center;
  align-items: center;
}
.all-container {
  width: 100vw;
  max-width: 1920px;
  height: 100vh;
  max-height: 800px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: white;
}
.head-div {
  width: 70%;
  max-width: 1240px;
  height: 15%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
.parking-title-div {
  display: flex;
  flex-direction: row;
  justify-content: baseline;
  align-items: center;
  margin-left: 2%;
}
.page-title {
  margin-left: 10px;
  font-size: 24px;
  font-weight: bold;
}
.parking-logo {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 32px;
  height: 32px;
  border: 3.5px solid black;
  border-radius: 3px;
  font-weight: bolder;
}

.content-body {
  height: 85%;
  width: 65%;
}
.entry-div {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  /* margin-bottom: 3%; */
  height: 7%;
}
.exit-div {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
  margin-top: 3%;
  height: 7%;
}
.entry {
  background-color: #eff5ff;
  color: #5f93fb;
}
.exit {
  background-color: #ffefef;
  color: #ff5a5f;
}

.button {
  width: 13%;
  height: 100%;
  border: 1px solid transparent;
  border-radius: 3px;
  font-size: 1.5rem;
  font-weight: 600;
}

.area-div {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: flex-end;
  height: 6%;
  /* padding: 0 px;  */
  /* 구역 표시 정렬 안맞으면 패딩값 조정 */
}

.area {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 3.5vh;
  max-width: 50px;
  height: 3.5vh;
  max-height: 50px;
  background-color: #eff5ff;
  color: #5f93fb;
  border-radius: 5px;
  font-size: 1.3rem;
  font-weight: 500;
}
.end-width {
  width: 12%;
  height: 80%;
}
.middle-width {
  width: 24%;
  height: 80%;
}
.flex-row-center {
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.parking-div {
  width: 100%;
  height: 74%;
}
.section-container {
  display: flex;
  flex-direction: row;
  /* margin-bottom: 20px; */
  height: 50%;
  width: 100%;
  justify-content: space-between;
  align-items: center;
}

.color-info {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 30px;
  height: 15px;
  border-radius: 5px;
}
</style>
