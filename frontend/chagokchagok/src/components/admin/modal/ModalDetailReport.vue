<script setup>
import { defineEmits, defineProps } from "vue";
import { useReportStore } from "@/stores/report";

const reportStore = useReportStore();
const props = defineProps({
  errorCode: Array,
  status: Array,
  report: Object,
});
const emit = defineEmits(["close"]);

const closeModal = () => {
  emit("close");
};

const modifyReport = () => {
  reportStore.modifyReport(props.report);
  emit("close");
};
</script>

<template>
  <div class="modal-overlay" @click="closeModal">
    <div class="modal-container" @click.stop>
      <div>{{ report }}</div>

      <div class="modal-header">
        <span class="modal-title">Detailed Report</span>
        <button class="close-button" @click="closeModal">&times;</button>
      </div>
      <div class="modal-body">
        <form class="report-form">
          <label for="issue-type">신고코드*</label>
          <input type="text" v-model="report['error_code']" readonly />
          <!-- <select
            id="issue-type"
            name="issue-type"
            v-model="report['error_code']"
          >
            <option v-for="code in errorCode" :key="code" :value="code">
              {{ code }}
            </option>
          </select> -->

          <div class="datetime-fields">
            <div class="datetime-group">
              <label for="start-date">발생시간*</label>
              <input
                readonly
                type="datetime-local"
                id="start-date"
                name="start-date"
                v-model="report['report_time']"
              />
            </div>
            <div class="datetime-group">
              <label for="end-date">완료시간</label>
              <input
                type="datetime-local"
                id="end-date"
                name="end-date"
                v-model="report['done_time']"
              />
            </div>
          </div>

          <div class="form-row">
            <div class="form-group half-width">
              <label for="issue-location">신고된 자리</label>
              <input type="text" v-model="report['full_name']" />
            </div>

            <div class="form-group half-width">
              <label for="issue-status">처리상태*</label>
              <select
                id="issue-status"
                name="issue-status"
                v-model="report['status']"
              >
                <option
                  v-for="option in status"
                  :key="option"
                  :value="option.value"
                >
                  {{ option.show }}
                </option>
              </select>
            </div>
          </div>

          <label for="notes">비고</label>
          <textarea
            id="notes"
            name="notes"
            placeholder="기타 진단상황을 작성해주세요."
            v-model="report['note']"
          ></textarea>
        </form>
      </div>
      <div class="modal-footer">
        <button class="modal-footer-button cancel-button" @click="closeModal">
          Cancel
        </button>
        <button
          class="modal-footer-button confirm-button"
          @click="modifyReport"
        >
          Confirm
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-container {
  background-color: #fff;
  width: 600px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  z-index: 1001;
}

.modal-header {
  padding: 16px;
  background-color: #f0f0f0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-title {
  font-size: 1.25rem;
  font-weight: bold;
}

.close-button {
  font-size: 1.25rem;
  border: none;
  background: none;
  cursor: pointer;
}

.modal-body {
  padding: 20px;
}

.report-form label {
  display: block;
  margin-bottom: 5px;
}

.report-form select,
.report-form input,
.report-form textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.datetime-fields {
  display: flex;
  justify-content: space-between;
}

.datetime-group {
  width: 48%;
}

.form-row {
  display: flex;
  justify-content: space-between;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.half-width {
  width: 48%;
}

/* 텍스트에리어와 셀렉트 박스 스타일은 동일하게 적용됩니다. */

.modal-footer {
  display: flex;
  justify-content: space-between; /* 버튼들을 양 끝으로 배치 */
  padding: 16px;
  background-color: #f0f0f0;
}

.modal-footer-button {
  flex-basis: 48%; /* 버튼의 너비를 조정 */
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.cancel-button {
  background-color: #ccc;
}

.confirm-button {
  background-color: #007bff;
  color: white;
}
</style>
