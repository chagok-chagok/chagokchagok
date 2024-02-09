<script setup>
import { useParkingSectionStore } from "@/stores/parkingSectionStore";
import { storeToRefs } from "pinia";
import IconExit from "../icons/IconExit.vue";

const parkingSectionStore = useParkingSectionStore();
const {
  modalPosition,
  isModalOpen,
  carInfo,
  isUnlockBarModalOpen,
  isExchangeModalOpen,
} = storeToRefs(parkingSectionStore);
const closeModal = () => {
  parkingSectionStore.closeTooltip();
};

const makeTimeFormat = (dateObj) => {
  const parsedDate = new Date(dateObj);

  if (!(parsedDate instanceof Date) || isNaN(parsedDate.getTime())) {
    return "Invalid Date";
  }

  const date = new Date(parsedDate.getTime()).toISOString().split("T")[0];
  const time = parsedDate.toTimeString().split(" ")[0];
  return date + " " + time;
};

const openUnlockBarModal = () => {
  isUnlockBarModalOpen.value = true;
};
const openExchangeLocationModal = async () => {
  await parkingSectionStore.getInParkCarList();
  isExchangeModalOpen.value = true;
};
</script>

<template>
  <div
    v-if="isModalOpen"
    class="modal"
    :style="{
      top: modalPosition.y - 30 + 'px',
      left: modalPosition.x - 10 + 'px',
    }"
  >
    <div class="triangle"></div>

    <div class="modal-content">
      <div class="carNum-div">
        <div class="carNum">[{{ carInfo.carNo }}]</div>
        <div class="exit-button">
          <icon-exit @click="closeModal"></icon-exit>
        </div>
      </div>
      <div class="tooltip-body">
        <div class="entry-time">
          입차 : {{ makeTimeFormat(carInfo.entryTime) }}
        </div>
        <button :class="['tooltip-button', 'blue']" @click="openUnlockBarModal">
          차단바 해제
        </button>
        <button
          :class="['tooltip-button', 'green']"
          @click="openExchangeLocationModal"
        >
          자리 교환하기
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.triangle {
  width: 0;
  height: 0;

  border-bottom: 15px solid transparent;
  border-top: 15px solid transparent;
  border-left: 15px solid transparent;
  border-right: 15px solid rgb(246, 247, 233);
  margin-top: 20px;
  z-index: 2;
}
.modal {
  position: fixed;
  width: 233px;
  height: 152px;
  top: 0;
  left: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: flex-start;
  /* padding: 5px; */
  z-index: 2;
}

.modal-content {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  background-color: rgb(246, 247, 233);
  /* padding: 0 10px; */
  /* border-radius: 8px; */
  /* border: 1px solid black; */
}
.carNum-div {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  /* align-items: baseline; */
}

.carNum {
  font-size: 15px;
  font-weight: 700;
  margin-top: 15px;
  margin-left: 10px;
  margin-bottom: 5px;
}
.exit-button {
  margin: 5px;
}

.exit-button :hover {
  cursor: pointer;
}

.tooltip-body {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  height: 100%;
  width: 90%;
  /* background-color: white; */
  border-top: 1px solid black;
}

.entry-time {
  font-size: 12px;
  overflow: hidden;
  /* white-space: nowrap; */
}
.tooltip-button {
  width: 100%;
  font-size: 10px;
  font-weight: 600;
  padding: 8px 0;
  border-radius: 3px;
  border: none;
}
.blue {
  background-color: #cde5f2;
}
.green {
  background-color: #cef5c4;
}
button :hover {
  cursor: pointer;
}
</style>
