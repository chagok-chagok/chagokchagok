<script setup>
import { defineEmits, defineProps, onMounted, ref } from "vue";
import { useReportStore } from "@/stores/report";
import moment from "moment";
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

const formattedReportTime = ref("");
const formattedDoneTime = ref("");

const updateFormattedDateTime = () => {
  formattedReportTime.value = moment(props.report.report_time).format(
    "YYYY-MM-DD HH:mm:ss"
  );
  formattedDoneTime.value = moment(props.report.done_time).format(
    "YYYY-MM-DD HH:mm:ss"
  );
};

const getOptionStyle = (option) => {
  // 옵션에 따라 색상을 반환
  switch (option.value) {
    case "IN_PROGRESS":
      return { color: "green", fontWeight: "bold" };
    case "COMPLETED":
      return { color: "blue", fontWeight: "bold" };
    case "UNRESOLVED":
      return { color: "red", fontWeight: "bold" };
    default:
      return {}; // 다른 옵션에 대한 기본 스타일
  }
};

onMounted(() => {
  updateFormattedDateTime();
});
</script>

<template>
  <div class="modal-overlay" @click="closeModal">
    <div class="modal-container" @click.stop>
      <!-- 헤더 -->
      <div class="modal-header">
        <button class="close-button" @click="closeModal">
          <font-awesome-icon :icon="['fas', 'xmark']" />
        </button>
        <div class="icon"><img src="/icon/Doorbell.png" /></div>
      </div>
      <!-- 본문 -->
      <div class="modal-body">
        <!-- 머릿말 -->
        <div class="flex-col body-title">
          <span class="modal-title">Detailed Report</span>
          <span class="modal-text"
            >신고의 상세내역을 작성 및 수정할 수 있습니다</span
          >
        </div>
        <!-- 폼 -->
        <form class="report-form">
          <div>
            <label for="issue-type" class="red">신고코드*</label>
            <input type="text" v-model="report['error_code']" readonly />
          </div>

          <div class="datetime-fields">
            <div class="datetime-group">
              <label for="start-date">발생시간*</label>
              <input
                readonly
                type="datetime-local"
                id="start-date"
                name="start-date"
                v-model="formattedReportTime"
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
              <input type="text" readonly v-model="report['full_name']" />
            </div>

            <div class="form-group half-width">
              <label for="issue-status">처리상태*</label>
              <select
                id="issue-status"
                name="issue-status"
                v-model="report['status']"
                :style="getOptionStyle(report['status'])"
              >
                <option
                  v-for="option in status"
                  :key="option"
                  :value="option.value"
                  :style="getOptionStyle(option)"
                >
                  {{ option.show }}
                </option>
              </select>
            </div>
          </div>

          <div>
            <label for="notes">비고</label>
            <div class="flex-row">
              <textarea
                id="notes"
                name="notes"
                placeholder="기타 진단상황을 작성해주세요."
                v-model="report['note']"
              ></textarea>
            </div>
          </div>
        </form>
      </div>
      <!-- 푸터 -->
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
.red {
  color: red;
}
/* 모달 배경 */
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

/* 제일 바깥 모달 */
.modal-container {
  background-color: #fff;
  width: 35vw;
  border-radius: 25px;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  z-index: 1001;
  padding: 5px;
}

/* 모달 헤더 */
.modal-header {
  padding: 13px 13px 0px 13px;
  /* background-color: #f0f0f0; */
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: row-reverse;
  font-size: 4rem;
}
.icon {
  border: 1px solid rgb(194, 194, 194);
  background-color: #ffffff;
  border-radius: 10px;
  padding: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
}
/* 모달 제목 */
.modal-title {
  font-size: 1.25rem;
  font-weight: bold;
  margin-bottom: 10px;
}
.modal-text {
  margin-bottom: 10px;
}
/* 닫기버튼 */
.close-button {
  font-size: 1.25rem;
  border: none;
  background: none;
  cursor: pointer;
}

/* 모달 본문 */
.modal-body {
  padding: 10px 20px;
  /* margin: 10px; */
  display: flex;
  flex-direction: column;
  gap: 2vh;
}

.report-form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

/* 모달 내부 각 요소의 제목 */
.report-form label {
  display: block;
  margin-bottom: 5px;
}

/* 모달 사용자 수정창들 */
.report-form select {
  width: 125px;
  padding: 10px;
  /* margin-bottom: 15px; */
  border: 1px solid #ccc;
  border-radius: 8px;
}
.report-form input {
  width: 205px;
  padding: 10px;
  /* margin-bottom: 15px; */
  border: 1px solid #ccc;
  border-radius: 8px;
}
.flex-col {
  display: flex;
  flex-direction: column;
}
.flex-row {
  display: flex;
  justify-content: center;
  text-align: center;
}
.report-form textarea {
  width: 100%;
  height: 10vh;
  padding: 10px;
  /* margin-bottom: 15px; */
  border: 1px solid #ccc;
  border-radius: 8px;
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
  /* background-color: #f0f0f0; */
  padding: 10px 20px;
  /* margin: 10px; */
}

.modal-footer-button {
  flex-basis: 48%; /* 버튼의 너비를 조정 */
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.cancel-button {
  background-color: #ffffff;
  border: 1px solid #ccc;
  font-weight: bold;
}

.confirm-button {
  background-color: #007bff;
  color: white;
  font-weight: bold;
}

/* Hover 시 색상 변경 */
.cancel-button:hover {
  background-color: #dadada; /* hover 시 배경색 변경 */
  color: #ffffff; /* hover 시 텍스트 색상 변경 */
}
.confirm-button:hover {
  background-color: rgb(2, 2, 196); /* hover 시 배경색 변경 */
  color: #ffffff; /* hover 시 텍스트 색상 변경 */
}
</style>
