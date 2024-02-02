<script setup>
import { useReportStore } from "@/stores/report";
import { storeToRefs } from "pinia";
import { ref, onMounted } from "vue";
import AppPagination from "@/components/layout/AppPagination.vue";
import ReportCountBar from "@/components/admin/report/ReportCountBar.vue";
const reportStore = useReportStore();
const page = ref(1);
const { total_page_cnt, reports } = storeToRefs(reportStore);
const titles = ref([]);

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

onMounted(async () => {
  await search(); // 컴포넌트가 마운트될 때 초기 total_page_cnt를 가져옴
  if (reports.value[0] != null) {
    titles.value = Object.keys(reports.value[0]);
    console.log("titles : ", titles.value);
  }
});
</script>

<template>
  <div>
    <report-count-bar></report-count-bar>
    <div>page : {{ page }}</div>
    <div>total_page_count : {{ total_page_cnt }}</div>
    <div class="table-container">
      <table class="custom-table">
        <thead>
          <th>NO</th>
          <th>발생시간</th>
          <th>신고코드</th>
          <th>주차자리</th>
          <th>처리상태</th>
          <th>비고</th>
        </thead>
        <tbody>
          <tr v-for="(item, index) in reports" :key="index">
            <td><input type="checkbox" /></td>
            <td>{{ item.report_time.replace("T", " ") }}</td>
            <td>{{ item.error_code }}</td>
            <td>{{ item.full_name }}</td>
            <td>
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
            <td>{{ item.note }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <app-pagination
      :current-page="basicPaginationProps.currentPage"
      :total-pages="basicPaginationProps.totalPages"
      :page-size="basicPaginationProps.pageSize"
      @page-change="(pageNumber) => changePage(pageNumber)"
    ></app-pagination>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.table-container {
  border-radius: 10px;
  overflow: hidden;
  border: 1px solid rgb(234, 236, 240);
}

.custom-table {
  width: 100%;
  border-radius: 10px;
  overflow: hidden;
  border: 1px solid rgb(234, 236, 240);
  border-collapse: collapse;
}

.custom-table th,
.custom-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
  text-align: center;
}

.custom-table th {
  background-color: #f9fafb;
}

button {
  height: 50px;
  width: 100px;
}

li {
  list-style: none;
  list-style-type: none;
}

.status-style {
  color: white;
  font-size: smaller;
  padding: 4px 15px;
  border-radius: 30px 30px 30px 30px;
  display: inline-block;
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
