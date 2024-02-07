<script setup>
import AppSection from "@/components/layout/AppSection.vue";
import AppSectionMiddleVue from "@/components/layout/AppSectionMiddle.vue";
import { computed, ref } from "vue";
import searchComp from "@/components/admin/Search.vue";
import axios from "axios";
// import { useParkingSectionStore } from "@/stores/parkingSectionStore";

const carInfo = ref(null);
const sectionName = ref("");
// const queryType = ref("CAR_NUMBER");
// const queryValue = ref("");
const isTooltipOpen = ref(false);
// const parkingSectionStore = useParkingSectionStore();
const fetchCarInfo = async (message) => {
  console.log("asd", message);
  try {
    const response = await axios.post("http://localhost:8080/admin/caloc", {
      area: message,
    });
    carInfo.value = response.data;
    isTooltipOpen.value = true;
    console.log(carInfo.value);
  } catch (error) {
    console.error("차량 정보를 가져오는데 에러가 발생했습니다:", error);
    // 여기에 에러 처리 로직을 추가합니다.
  }
};
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
  <VDropdown placement="right-start" :distance="6">
    <template #popper>
      <div class="dropdown-content" v-if="isTooltipOpen">
        <p>[{{ carInfo.carNo }}]</p>
        <p>입차시간:{{ carInfo.entryTime }}</p>
        <router-link to="/barrier-release" class="dropdown-button"
          >차단바 해제</router-link
        >
        <router-link to="/spot-swap" class="dropdown-button"
          >자리 교환하기</router-link
        >
      </div>
    </template>
  </VDropdown>
  <div>
    <div class="parking-title-div">
      <div class="parking-logo">P</div>
      <h3 class="page-title">차량 배정 현황</h3>
    </div>
    <div>
      <span
        >각 자리 클릭 시 차단바 제어, 자리 변경 등의 동작이 가능합니다.</span
      >
      <searchComp />
      <!-- <form @submit.prevent="searchQuery">
        <select name="search" id="search" v-model="queryType">
          <option value="CAR_NUMBER">차 번호</option>
          <option value="SPOT_NUMBER">주차 위치</option>
        </select>
        <input type="text" v-model="queryValue" />
      </form> -->
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
        @location-select="fetchCarInfo"
      ></app-section>
      <app-section-middle-vue
        :parkings="parkingMiddleInfoLU"
        @location-select="fetchCarInfo"
      ></app-section-middle-vue>
      <app-section-middle-vue
        :parkings="parkingMiddleInfoRU"
        @location-select="fetchCarInfo"
      ></app-section-middle-vue>
      <app-section
        :parkings="parkingRightUp"
        :is-left="false"
        @location-select="fetchCarInfo"
      ></app-section>
    </div>
    <div class="section-container">
      <app-section
        :parkings="parkingLeftDown"
        :is-left="true"
        @location-select="fetchCarInfo"
      ></app-section>
      <app-section-middle-vue
        :parkings="parkingMiddleInfoLD"
        @location-select="fetchCarInfo"
      ></app-section-middle-vue>
      <app-section-middle-vue
        :parkings="parkingMiddleInfoRD"
        @location-select="fetchCarInfo"
      ></app-section-middle-vue>
      <app-section
        :parkings="parkingRightDown"
        :is-left="false"
        @location-select="fetchCarInfo"
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
