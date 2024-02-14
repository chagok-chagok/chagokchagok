<script setup>
import { useParkingSectionStore } from "@/stores/parkingSectionStore";
import { storeToRefs } from "pinia";
import { ref } from "vue";
const parkingSectionStore = useParkingSectionStore();
const { isUnlockBarModalOpen, originalLocation, carInfo } =
  storeToRefs(parkingSectionStore);

const targetCar = ref("");
targetCar.value = carInfo.value.carNo;
const closeModal = () => {
  isUnlockBarModalOpen.value = false;
};

const unlockBar = async () => {
  console.log("park_full_name : ", parkingSectionStore.originalLocation);
  console.log("car_no : ", targetCar.value);
  await parkingSectionStore.unlockBar(targetCar.value);
  await parkingSectionStore.getParkList();
  closeModal();
  parkingSectionStore.closeTooltip();
};
</script>

<template>
  <div class="modal-overlay" @click="closeModal">
    <div class="modal-window" @click.stop>
      <div class="modal-header">
        <button class="close-button" @click="closeModal">&times;</button>
      </div>
      <div class="modal-content">
        <div class="image-div">
          <img src="@/assets/unlock_bar.png" alt="" />
        </div>
        <div class="modal-title">
          <div class="title-text-div">
            [{{ originalLocation }}]의 차단바를 해제하시겠습니까?
          </div>
          <div class="description-text-div">
            <p class="description-text">
              [{{ originalLocation }}]에 배정받은 차량 번호를 입력해주세요.
            </p>
            <p class="description-text">
              차량 번호를 확인하고 차단바 해제 버튼을 눌러주세요.
            </p>
          </div>
        </div>
        <div :class="['search-bar-div', 'border-smooth', 'border-color']">
          <div :class="['search-type-div', 'border-smooth', 'border-color']">
            차 번호
          </div>
          <div class="input-div">
            <input
              type="text"
              :class="['input-car-number', 'border-smooth', 'border-color']"
              placeholder="번호를 입력하세요"
              v-model="targetCar"
            />

            <div class="confirm-button-div">
              <button
                :class="['confirm-button', 'border-smooth']"
                @click="unlockBar"
              >
                해제
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 100;
}

.modal-window {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  height: 534px;
  width: 690px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 200;
  font-family: 'DM Sans', sans-serif;
}

.modal-header {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: flex-start;
  width: 100%;
  height: 10%;
}

.close-button {
  background: none;
  border: none;
  font-size: 40px;
  width: 40px;
  height: 40px;
  cursor: pointer;
  color: #170f49;
}

.modal-content {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  height: 90%;
}

.image-div {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 140px;
  height: 140px;
  background-color: #edecff;
  border-radius: 40px;
}

.mordal-title {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.title-text-div {
  text-align: center;
  font-size: 34px;
  font-weight: 600;
  margin: 10px 0;
}
.description-text-div {
  height: 60px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  /* margin-bottom: 30px; */
}
.description-text {
  font-size: 18px;
  color: #6f6c90;
}
.search-bar-div {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 560px;
  height: 78px;
  border: 1px solid black;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
  margin-bottom: 30px;
}
.search-type-div {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 107px;
  border-right: 1px solid black;
  color: #737373;
}
.border-smooth {
  border-radius: 10px;
}
.border-color {
  border-color: #e6e6e6;
}
.input-div {
  display: flex;
  flex-direction: row;
  align-items: center;
  /* height: 100%; */
}
.input-car-number {
  width: 331px;
  height: 100%;
  padding: 16px;
  font-size: 16px;
  border: none;
  border-left: 1px solid #e6e6e6;
  border-right: 1px solid #e6e6e6;
  color: #737373;
  /* outline: 1px solid black; */
  /* margin:px 0; */
}

.confirm-button-div {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 122px;
  height: 100%;
  padding: 12px;
}
.confirm-button {
  border: none;
  background-color: #4a3aff;
  width: 98px;
  height: 55px;
  font-size: 18px;
  font-weight: 600;
  color: white;
  cursor: pointer;
}
</style>
