<template>
  <div class="modal-overlay" @click="closeModal">
    <div class="modal-window" @click.stop>
      <div class="modal-header">
        <button class="close-button" @click="closeModal">&times;</button>
      </div>
      <div class="modal-content">
        <!-- <div class="modal-icon">
          <img src="lock-icon.png" alt="Lock Icon" />
        </div> -->
        <div class="modal-text">
          <p>[B12]의 차단을 해제하시겠습니까?</p>
        </div>
        <div class="modal-actions">
          <button class="confirm-button">해제</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useParkingSectionStore } from "@/stores/parkingSectionStore";
import { storeToRefs } from "pinia";
const parkingSectionStore = useParkingSectionStore();
const { isUnlockBarModalOpen } = storeToRefs(parkingSectionStore);
const closeModal = () => {
  isUnlockBarModalOpen.value = false;
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
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 300px;
  text-align: center;
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
  margin-top: 20px;
}

.modal-icon img {
  width: 50px;
  height: 50px;
}

.modal-text p {
  font-size: 1rem;
  margin: 20px 0;
}

.modal-actions {
  margin-top: 20px;
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
