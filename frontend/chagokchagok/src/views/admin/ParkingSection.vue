<script setup>
import AppSection from "@/components/layout/AppSection.vue";
import AppSectionMiddleVue from "@/components/layout/AppSectionMiddle.vue";
import { ref, onMounted } from "vue";
import { useParkingSectionStore } from "@/stores/parkingSectionStore";
import { storeToRefs } from "pinia";
import Tooltip2 from "@/components/admin/Tooltip2.vue";
import ModalBar from "@/components/admin/modal/ModalBar.vue";
import ModalChange from "@/components/admin/modal/ModalChange.vue";
import AppPageHeader from "@/components/admin/AppPageHeader.vue";

const parkingSectionStore = useParkingSectionStore();
const {
  parks,
  occupied,
  carInfo,
  targetLocation,
  isUnlockBarModalOpen,
  isExchangeModalOpen,
} = storeToRefs(parkingSectionStore);

const queryType = ref("CAR_NUMBER");
const queryValue = ref("");

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

const searchQuery = async () => {
  await parkingSectionStore.searchLocation(queryType, queryValue);
};

const showTooltip = async (message) => {
  await parkingSectionStore.getCarInfo(message);
};

onMounted(() => {
  parkingSectionStore.getParkList();
});
</script>

<template>
  <!-- <div>주차장 정보 : {{ parks }}</div> -->
  <!-- <div>주차 자리 정보 : {{ occupied }}</div> -->
  <modal-bar v-if="isUnlockBarModalOpen"></modal-bar>
  <modal-change v-if="isExchangeModalOpen"></modal-change>
  <Tooltip2></Tooltip2>

  <AppPageHeader icon="square-parking" pageTitle="차량 배정 현황" />
  <div class="describtion-bar">
    <span>각 자리 클릭 시 차단바 제어, 자리 변경 등의 동작이 가능합니다.</span>
    <div id="app">
      <div class="search-bar">
        <form @submit.prevent="searchQuery">
          <select name="search" id="search" v-model="queryType">
            <option value="CAR_NUMBER">차 번호</option>
            <option value="SPOT_NUMBER">주차 위치</option>
          </select>
          <input type="text" v-model="queryValue" />
        </form>
      </div>
    </div>
  </div>
  <div class="content">
    <div class="all-park-container">
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
      <div class="section-container">
        <app-section
          :parkings="parkingLeftUp"
          :is-left="true"
          @location-select="showTooltip"
        ></app-section>
        <app-section-middle-vue
          :parkings="parkingMiddleInfoLU"
          @location-select="showTooltip"
        ></app-section-middle-vue>
        <app-section-middle-vue
          :parkings="parkingMiddleInfoRU"
          @location-select="showTooltip"
        ></app-section-middle-vue>
        <app-section
          :parkings="parkingRightUp"
          :is-left="false"
          @location-select="showTooltip"
        ></app-section>
      </div>
      <div class="section-container" style="margin-bottom: 2%">
        <app-section
          :parkings="parkingLeftDown"
          :is-left="true"
          @location-select="showTooltip"
        ></app-section>
        <app-section-middle-vue
          :parkings="parkingMiddleInfoLD"
          @location-select="showTooltip"
        ></app-section-middle-vue>
        <app-section-middle-vue
          :parkings="parkingMiddleInfoRD"
          @location-select="showTooltip"
        ></app-section-middle-vue>
        <app-section
          :parkings="parkingRightDown"
          :is-left="false"
          @location-select="showTooltip"
        ></app-section>
      </div>
      <div class="color-info-div-container">
        <div class="color-info-div">
          <div :class="['color-info', 'yes-car']"></div>
          <span>주차됨</span>
        </div>
        <div class="color-info-div">
          <div :class="['color-info', 'no-car']"></div>
          <span>빈자리</span>
        </div>
      </div>
      <div class="exit-div">
        <div class="pink-circle"></div>
        <button class="exit-button">Exit</button>
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
.content {
  height: 78%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.parking-title-div {
  display: flex;
  flex-direction: row;
  justify-content: baseline;
  align-items: center;
}
.page-title {
  margin-left: 20px;
  font-size: 3vh;
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
.area-div {
  width: 100%;
  height: 10%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: flex-end;
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
  font-family: "Poppins", sans-serif;
  font-weight: 400;
}
.end-width {
  /* height: 5px; */
  width: 90px;
}
.middle-width {
  /* height: 5px; */
  width: 180px;
}

.section-container {
  display: flex;
  flex-direction: row;
  margin-bottom: 4%;
  justify-content: space-between;
  height: 35%;
  width: 100%;
}

.color-info-div-container {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  /* margin-right: 6.8%; */
  height: 7%;
  width: 100%;
}
.color-info-div {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-left: 15px;
}
.color-info-div span {
  margin-left: 5px;
  font-size: 14px;
  font-family: "Poppins", sans-serif;
  font-weight: 600;
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

.yes-car {
  background-color: #84adff;
  border: none;
  border-radius: 7px;
}
.no-car {
  background-color: #e1e1e1;
  border: none;
  border-radius: 7px;
}

.exit-div {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
  height: 13%;
  width: 100%;
  /* margin: 20px 8.5% 0 0; */
}

.pink-circle {
  width: 12.48px;
  height: 12.48px;
  border: 4px solid #ff5a5f;
  border-radius: 50%;
  margin-right: 5px;
}
.exit-button {
  width: 80px;
  height: 30px;
  background-color: #ffefef;
  border: 1px solid transparent;
  border-radius: 3px;
  color: #ff5a5f;
  font-family: "Poppins", sans-serif;
}

.top-bar {
  margin: 5px 0% 0% 2%;
}

.describtion-bar {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 1px 0 0 45px;
  font-family: "NanumBarunGothic", sans-serif;
}

.describtion-bar > #app {
  margin: 1% 0 0 0;
}

.descript {
  font-size: 2vh;
}

#app {
  max-width: 640px;
  margin: 0 auto;
  height: auto;
}

.search-bar {
  display: flex;
  align-items: center;
  position: relative;
}

.search-bar select {
  position: absolute;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 8px;
  z-index: 1;
  outline: none;
  font-family: "Roboto", sans-serif;
}

.search-bar input[type="text"] {
  padding: 6px;
  padding-left: 95px;
  border: 1px solid #ccc;
  border-color: #eff0f6;
  border-radius: 8px;
  width: 100%;
  background-color: #f7f7f7;
  opacity: 0.8;
  outline: none;
  font-family: "Roboto", sans-serif;
}

.all-park-container {
  width: 83%;
  height: 100%;
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
}
</style>
