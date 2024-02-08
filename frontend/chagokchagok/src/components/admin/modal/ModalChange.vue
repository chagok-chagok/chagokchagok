<template>
  <div class="modal-overlay" @click="closeModal">
    <div class="modal-window" @click.stop>
      <div class="modal-header">
        <button class="close-button" @click="closeModal">&times;</button>
      </div>
      <div class="modal-content">
        <!-- <div class="modal-icon">
          <img src="document-icon.png" alt="Document Icon" />
        </div> -->
        <div class="modal-title">
          <h3>[B12]와 교환할 정보를 입력해주세요.</h3>
          <p>
            잘못 주차한 차량의 차번호를 입력해주세요.<br />시스템에서 자동으로
            해당 위치를 찾아 변경합니다.
          </p>
        </div>
        <div class="modal-form-row">
          <div class="modal-form-group">
            <select id="number-choice" name="number-choice">
              <option value="car-number">차 번호</option>
              <option value="seat-number">자리 번호</option>
            </select>
          </div>

          <div class="modal-form-group">
            <input
              type="text"
              id="input-number"
              name="input-number"
              placeholder="번호를 입력하세요"
            />
          </div>

          <div class="modal-form-action">
            <button class="confirm-button">교체</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useParkingSectionStore } from "@/stores/parkingSectionStore";
import { storeToRefs } from "pinia";
const parkingSectionStore = useParkingSectionStore();
const { isExchangeModalOpen } = storeToRefs(parkingSectionStore);
const closeModal = () => {
  isExchangeModalOpen.value = false;
};
</script>

<style scoped>
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
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  width: 400px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 200;
}

.modal-header {
  display: flex;
  justify-content: flex-end;
}

.close-button {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
}

.modal-content {
  text-align: center;
}

.modal-icon img {
  width: 60px;
  height: 60px;
}

.modal-title h3 {
  color: #333;
  font-size: 1.2rem;
}

.modal-title p {
  color: #666;
  font-size: 0.9rem;
  margin: 10px 0 20px;
}

.modal-form-row {
  display: flex;
  justify-content: space-between;
  align-items: end;
  gap: 10px;
}

.modal-form-group {
  flex: 1;
}

.modal-form-group label {
  display: block;
  margin-bottom: 5px;
  font-size: 0.9rem;
}

.modal-form-group select,
.modal-form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

.modal-form-action {
  /* Adjust the width as necessary or keep it flexible */
  margin-left: 10px;
  align-self: flex-end;
}

.confirm-button {
  background-color: #007bff;
  border: none;
  padding: 10px 20px;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}
</style>
