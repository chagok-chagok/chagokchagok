<script setup>
import { useReportStore } from "@/stores/report";
import { storeToRefs } from "pinia";
import { ref, onMounted } from "vue";
import AppPagination from "@/components/layout/AppPagination.vue";
import ReportCountBar from "@/components/admin/report/ReportCountBar.vue";
import { mdiArrowDown } from "@mdi/js";
import { mdiFilterVariant } from "@mdi/js";
import MdiIcon from "@/components/icons/MdiIcon.vue";
import MdiIconButton from "@/components/icons/MdiIconButton.vue";
import ModalDetailReportVue from "@/components/admin/modal/ModalDetailReport.vue";

const reportStore = useReportStore();
const page = ref(1);
const { total_page_cnt, reports } = storeToRefs(reportStore);
const titles = ref([]);
const isModalVisible = ref(false);
const modalReport = ref({});
const errorCode = ref(["HUMAN_ERROR", "SENSOR_ERROR", "AUTO_REPORT"]);
const status = ref([
  { show: "DONE", value: "COMPLETED" },
  { show: "In Progress", value: "IN_PROGRESS" },
  { show: "Not Started", value: "UNRESOLVED" },
]);
reportStore.getReportList(page.value);

const search = async () => {
  // total_page_cnt 비동기로 가져오기
  await reportStore.getReportList(page.value);
  // 가져온 total_page_cnt를 BasicPagination에 전달
  basicPaginationProps.value.totalPages = total_page_cnt.value;
  console.log("reports : ", reports.value);
  console.log(reports.value[0]);
  console.log();
};

const changePage = async (pageNumber) => {
  page.value = pageNumber;
  await search(); // 페이지 변경할 때마다 total_page_cnt를 업데이트
};

const basicPaginationProps = ref({
  currentPage: page,
  totalPages: total_page_cnt.value, // 초기값으로 설정
  pageSize: 5,
});

const getStatus = (status) => {
  switch (status) {
    case "COMPLETED":
      return "DONE";
    case "IN_PROGRESS":
      return "In Progress";
    case "UNRESOLVED":
      return "Not Started";
    default:
      return status;
  }
};

// filter button 클릭 시 이벤트 작성
const filterClick = () => {
  console.log("filter click");
};

const openModal = (report) => {
  console.log("report : ", report);
  modalReport.value = report;
  isModalVisible.value = true;
};

const closeModal = () => {
  reportStore.getReportList(page.value);
  search();
  isModalVisible.value = false;
};

onMounted(async () => {
  await search(); // 컴포넌트가 마운트될 때 초기 total_page_cnt를 가져옴
  if (reports.value[0] != null) {
    titles.value = Object.keys(reports.value[0]);
    console.log("titles : ", titles.value);
  }
});
</script>

<template>
  <div v-if="isModalVisible">
    <modal-detail-report-vue
      @close="closeModal"
      :report="modalReport"
      :error-code="errorCode"
      :status="status"
    ></modal-detail-report-vue>
  </div>
  <div>
    <report-count-bar></report-count-bar>
    <button class="filter-button" @click="filterClick">
      <div class="filter-button-div">
        <mdi-icon-button :path="mdiFilterVariant" size="14"></mdi-icon-button>
        <span class="filter-span">Filters</span>
      </div>
    </button>
    <div class="table-container">
      <table class="custom-table">
        <thead>
          <th class="report-no"><div class="head-div">NO</div></th>
          <th class="report-time">
            <div class="head-div">
              <span>발생시간</span
              ><mdi-icon
                :path="mdiArrowDown"
                size="12"
                fill="#667085"
                class="arrow-margin"
              ></mdi-icon>
            </div>
          </th>
          <th class="error-code">
            <div class="head-div">
              <span>신고코드</span
              ><mdi-icon
                :path="mdiArrowDown"
                size="12"
                fill="#667085"
                class="arrow-margin"
              ></mdi-icon>
            </div>
          </th>

          <th class="park-no">
            <div class="head-div">
              <span>주차자리</span
              ><mdi-icon
                :path="mdiArrowDown"
                size="12"
                fill="#667085"
                class="arrow-margin"
              ></mdi-icon>
            </div>
          </th>

          <th class="report-status">
            <div class="head-div">
              <span>처리상태</span
              ><mdi-icon
                :path="mdiArrowDown"
                size="12"
                fill="#667085"
                class="arrow-margin"
              ></mdi-icon>
            </div>
          </th>
          <th class="note">
            <div class="head-div">
              <span>비고</span
              ><mdi-icon
                :path="mdiArrowDown"
                size="12"
                fill="#667085"
                class="arrow-margin"
              ></mdi-icon>
            </div>
          </th>
          <th class="update-button"><span></span></th>
        </thead>
        <tbody class="report-item">
          <tr
            v-for="(item, index) in reports"
            :key="index"
            @click="openModal(item)"
          >
            <td class="report-no">{{ item.report_id }}</td>
            <td class="report-time">
              {{ item.report_time.replace("T", " ") }}
            </td>
            <td class="error-code">{{ item.error_code }}</td>
            <td class="park-no">{{ item.full_name }}</td>
            <td class="report-status">
              <div
                :class="{
                  'status-style': true,
                  'background-blue': item.status === 'COMPLETED',
                  'background-red': item.status === 'UNRESOLVED',
                  'background-green': item.status === 'IN_PROGRESS',
                }"
              >
                {{ getStatus(item.status) }}
              </div>
            </td>
            <td class="note">
              {{ item.note == null || item.note == "" ? "-" : item.note }}
            </td>
            <td class="update-button">
              <font-awesome-icon icon="pen-to-square" class="modify-icon" />
            </td>
          </tr>
        </tbody>
      </table>
      <app-pagination
        :current-page="basicPaginationProps.currentPage"
        :total-pages="basicPaginationProps.totalPages"
        :page-size="basicPaginationProps.pageSize"
        @page-change="(pageNumber) => changePage(pageNumber)"
      ></app-pagination>
    </div>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

button :hover {
  cursor: pointer;
}
.filter-button {
  background-color: #fff;
  /* background-color: black; */
  border-radius: 7px 7px 7px 7px;
  border: 1px solid #d0d5dd;
  width: 73px;
  height: 30px;
  margin: 10px 0;
  border-collapse: collapse;
}
.filter-button :hover {
  cursor: pointer;
}

.filter-button-div {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  border-radius: 7px 7px 7px 7px;
  border: 1px solid #d0d5dd;
  overflow: hidden;
  width: 100%;
  height: 100%;
}
.filter-span {
  margin-left: 7px;
  font-size: 11px;
}
.table-container {
  border-radius: 10px;
  height: 42.81%;
  overflow: hidden;
  border: 2px solid rgb(234, 236, 240);
  border-collapse: collapse;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
}

.custom-table {
  width: 100%;
  height: 100%;
  border-radius: 10px;
  overflow: hidden;
  border: 1px solid rgb(234, 236, 240);
  border-collapse: collapse;
}

.custom-table th,
.custom-table td {
  border-top: 1px solid #ddd;
  text-align: center;
  font-family: "Poppins";
}
.custom-table th {
  padding: 5px 0;
  /* margin: 50px 0; */
  font-size: 0.6rem;
  font-weight: 400;
  background-color: #f9fafb;
  color: #8a92a6;
}

.custom-table td {
  padding: 15px 0;
  font-size: 0.8rem;
}

.report-item :hover {
  cursor: pointer;
  background-color: #e9f9fa;
}
.report-no {
  width: 6%;
  padding: auto 0;
}

.report-time {
  width: 22%;
  padding: auto 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.error-code {
  width: 15%;
  padding: auto 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.park-no {
  padding: auto 0;
  width: 10%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.report-status {
  width: 12%;
  padding: auto 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.note {
  width: 25%;
  padding: auto 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.update-button {
  width: 10%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.modify-icon {
  color: #667085;
  font-size: 16px;
}
.status-style {
  color: white;
  font-size: smaller;
  font-family: "Open Sans";
  padding: 5px 10px;
  border-radius: 50px 50px 50px 50px;
  display: inline-block;
}

.head-div {
  height: 23px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
.arrow-margin {
  margin-left: 10%;
}
.background-green {
  background-color: #71dd76;
}

.background-blue {
  background-color: #5f93fb;
}

.background-red {
  background-color: #fb5f5f;
}
</style>
