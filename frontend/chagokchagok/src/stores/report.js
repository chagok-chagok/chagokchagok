import { ref } from "vue";
import { defineStore } from "pinia";
import { instance } from "@/utils/mainAxios";

export const useReportStore = defineStore("report", () => {
  const reports = ref([]);
  const today_cnt = ref(0);
  const total_page_cnt = ref(0);
  const unsolved_cnt = ref(0);
  const hotline_cnt = ref(0);
  const sensor_cnt = ref(0);
  const autosystem_cnt = ref(0);

  /* ============= 신고 리스트 받아오는 요청 ============= */
  const getReportList = async (page) => {
    const { data } = await instance.get("admin/report", { params: { page } });
    reports.value = data.board;
    today_cnt.value = data.today_cnt;
    unsolved_cnt.value = data.unsolved_cnt;
    hotline_cnt.value = data.hotline_cnt;
    sensor_cnt.value = data.sensor_cnt;
    autosystem_cnt.value = data.autosystem_cnt;
    total_page_cnt.value = data.total_page_cnt;
  };

  const modifyReport = async (report) => {
    await instance.put("admin/report", report);
  };

  return {
    reports,
    today_cnt,
    total_page_cnt,
    unsolved_cnt,
    hotline_cnt,
    sensor_cnt,
    autosystem_cnt,
    getReportList,
    modifyReport,
  };
});
