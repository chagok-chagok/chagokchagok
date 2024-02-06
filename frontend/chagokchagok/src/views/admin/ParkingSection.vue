<script setup>
import AppSection from "@/components/layout/AppSection.vue";
import AppSectionMiddleVue from "@/components/layout/AppSectionMiddle.vue";

import { ref, watch } from "vue";

const queryType = ref("CAR_NUMBER");
const queryValue = ref("");

// name: String, isDisabled: Boolean
// 위쪽
const parkingLeftUp = ref([
  { name: "A01", isDisabled: false },
  { name: "A02", isDisabled: false },
  { name: "A03", isDisabled: true },
]);
const parkingMiddleInfoLU = ref([
  { name: "A07", isDisabled: false },
  { name: "A08", isDisabled: false },
  { name: "A09", isDisabled: false },
  { name: "A10", isDisabled: false },
  { name: "B01", isDisabled: false },
  { name: "B02", isDisabled: false },
  { name: "B03", isDisabled: false },
  { name: "B04", isDisabled: false },
]);
const parkingMiddleInfoRU = ref([
  { name: "B09", isDisabled: false },
  { name: "B10", isDisabled: false },
  { name: "B11", isDisabled: false },
  { name: "B12", isDisabled: false },
  { name: "C01", isDisabled: false },
  { name: "C02", isDisabled: false },
  { name: "C03", isDisabled: false },
  { name: "C04", isDisabled: false },
]);
const parkingRightUp = ref([
  { name: "C04", isDisabled: false },
  { name: "C05", isDisabled: false },
  { name: "C06", isDisabled: false },
  { name: "C07", isDisabled: false },
]);

// 아래쪽
const parkingLeftDown = ref([
  { name: "A04", isDisabled: true },
  { name: "A05", isDisabled: false },
  { name: "A06", isDisabled: false },
]);
const parkingMiddleInfoLD = ref([
  { name: "A11", isDisabled: false },
  { name: "A12", isDisabled: false },
  { name: "A13", isDisabled: false },
  { name: "A14", isDisabled: false },
  { name: "B05", isDisabled: false },
  { name: "B06", isDisabled: false },
  { name: "B07", isDisabled: false },
  { name: "B08", isDisabled: false },
]);
const parkingMiddleInfoRD = ref([
  { name: "B13", isDisabled: false },
  { name: "B14", isDisabled: false },
  { name: "B15", isDisabled: false },
  { name: "B16", isDisabled: false },
  { name: "C05", isDisabled: false },
  { name: "C06", isDisabled: false },
  { name: "C07", isDisabled: false },
  { name: "C08", isDisabled: false },
]);
const parkingRightDown = ref([
  { name: "C13", isDisabled: false },
  { name: "C14", isDisabled: false },
  { name: "C15", isDisabled: false },
  { name: "C16", isDisabled: false },
]);

const selectLocation = (message) => {
  console.log("ParkingSection emit 도달 : ", message);
};

const searchQuery = () => {
  console.log("검색조건 : ", queryType.value, "검색어 : ", queryValue.value);
};
</script>

<template>
  <div>
    <div class="parking-title-div">
      <div class="parking-logo">P</div>
      <h3 class="page-title">차량 배정 현황</h3>
    </div>
    <div>
      <span
        >각 자리 클릭 시 차단바 제어, 자리 변경 등의 동작이 가능합니다.</span
      >
      <form @submit.prevent="searchQuery">
        <select name="search" id="search" v-model="queryType">
          <option value="CAR_NUMBER">차 번호</option>
          <option value="SPOT_NUMBER">주차 위치</option>
        </select>
        <input type="text" v-model="queryValue" />
      </form>
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
      <app-section
        :parkings="parkingLeftUp"
        :is-left="true"
        @location-select="selectLocation"
      ></app-section>
      <app-section-middle-vue
        :parkings="parkingMiddleInfoLU"
        @location-select="selectLocation"
      ></app-section-middle-vue>
      <app-section-middle-vue
        :parkings="parkingMiddleInfoRU"
        @location-select="selectLocation"
      ></app-section-middle-vue>
      <app-section
        :parkings="parkingRightUp"
        :is-left="false"
        @location-select="selectLocation"
      ></app-section>
    </div>
    <div class="section-container">
      <app-section
        :parkings="parkingLeftDown"
        :is-left="true"
        @location-select="selectLocation"
      ></app-section>
      <app-section-middle-vue
        :parkings="parkingMiddleInfoLD"
        @location-select="selectLocation"
      ></app-section-middle-vue>
      <app-section-middle-vue
        :parkings="parkingMiddleInfoRD"
        @location-select="selectLocation"
      ></app-section-middle-vue>
      <app-section
        :parkings="parkingRightDown"
        :is-left="false"
        @location-select="selectLocation"
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
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  border-collapse: collapse;
}
.parking-title-div {
  display: flex;
  flex-direction: row;
  justify-content: baseline;
  align-items: center;
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

.color-info-div-container {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  margin-right: 30px;
}
.color-info-div {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-right: 15px;
}
.color-info-div span {
  margin-left: 5px;
  font-size: 14px;
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
}
.no-car {
  background-color: #e1e1e1;
}

.exit-div {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;

  margin: 20px 50px 0 0;
}

.pink-circle {
  width: 12.48px;
  height: 12.48px;
  border: 4px solid #ff5a5f;
  border-radius: 50%;
  margin-right: 5px;
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
