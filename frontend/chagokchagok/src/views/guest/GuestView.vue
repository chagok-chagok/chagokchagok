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
  { name: "A1", isDisabled: false },
  { name: "A2", isDisabled: false },
  { name: "A3", isDisabled: true },
]);
const parkingMiddleInfoLU = ref([
  { name: "A7", isDisabled: false },
  { name: "A8", isDisabled: false },
  { name: "A9", isDisabled: false },
  { name: "A10", isDisabled: false },
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
  { name: "A4", isDisabled: true },
  { name: "A5", isDisabled: false },
  { name: "A6", isDisabled: false },
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

<template>
  <div>
    <div class="head-div">
      <div class="parking-title-div">
        <div class="parking-logo">P</div>
        <h3 class="page-title">차량 배정 현황</h3>
      </div>
      <question-bubble></question-bubble>
    </div>
    <div class="entry-div">
      <button class="entry-button">Entry</button>
    </div>
    <div class="area-div">
      <div></div>
      <div class="end-width"></div>
      <div>
        <div class="area">A</div>
      </div>
      <div class="middle-width"></div>
      <div class="area"><div class="area">B</div></div>
      <div class="middle-width"></div>
      <div><div class="area">C</div></div>
      <div class="end-width"></div>
      <div></div>
    </div>
    <div class="section-container">
      <app-section-guest
        :parkings="parkingLeftUp"
        :is-left="true"
      ></app-section-guest>
      <app-section-middle-guest-vue
        :parkings="parkingMiddleInfoLU"
      ></app-section-middle-guest-vue>
      <app-section-middle-guest-vue
        :parkings="parkingMiddleInfoRU"
      ></app-section-middle-guest-vue>
      <app-section-guest
        :parkings="parkingRightUp"
        :is-left="false"
      ></app-section-guest>
    </div>
    <div class="section-container">
      <app-section-guest
        :parkings="parkingLeftDown"
        :is-left="true"
      ></app-section-guest>
      <app-section-middle-guest-vue
        :parkings="parkingMiddleInfoLD"
      ></app-section-middle-guest-vue>
      <app-section-middle-guest-vue
        :parkings="parkingMiddleInfoRD"
      ></app-section-middle-guest-vue>
      <app-section-guest
        :parkings="parkingRightDown"
        :is-left="false"
      ></app-section-guest>
    </div>

    <div class="exit-div">
      <!-- <div class="pink-circle"></div> -->
      <button class="exit-button">Exit</button>
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
.head-div {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: flex-start;
}
.parking-title-div {
  display: flex;
  flex-direction: row;
  justify-content: baseline;
  align-items: center;
  margin-top: 50px;
}
.page-title {
  margin-left: 10px;
}
.parking-logo {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 25px;
  height: 25px;
  border: 3.5px solid black;
  border-radius: 3px;
  font-weight: bolder;
}

.entry-div {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
}
.entry-button {
  width: 117px;
  height: 37px;
  background-color: #eff5ff;
  border: 1px solid transparent;
  border-radius: 3px;
  color: #5f93fb;
}
.area-div {
  height: 50px;
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
  align-items: flex-end;
  padding: 0 20px; /* 구역 표시 정렬 안맞으면 패딩값 조정 */
}

.area {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  width: 20px;
  height: 20px;
  background-color: #eff5ff;
  color: #5f93fb;
  border-radius: 5px;
}
.end-width {
  width: 90px;
}
.middle-width {
  width: 180px;
}

.section-container {
  display: flex;
  flex-direction: row;
  margin-bottom: 20px;
  justify-content: space-evenly;
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

.exit-div {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;

  margin: 20px 50px 0 0;
}

.exit-button {
  width: 117px;
  height: 37px;
  background-color: #ffefef;
  border: 1px solid transparent;
  border-radius: 3px;
  color: #ff5a5f;
}
</style>
