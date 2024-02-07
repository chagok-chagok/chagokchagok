import { defineStore } from "pinia";
import { instance } from "@/utils/mainAxios";
import { ref } from "vue";

export const useGuestStore = defineStore("guest", () => {
  /* =========== 차량 현황 받아오는 요청 ============ */
  const parks = ref([]);
  const occupied = ref([]);
  const total_cnt = ref(0);
  const current_cnt = ref(0);
  const getParkList = async () => {
    const { data } = await instance.get("/guest");
    parks.value = data.parks;
    total_cnt.value = data.total_cnt;
    current_cnt.value = data.curr_cnt;
    occupied.value = parks.value
      .filter((park) => park["park_status"])
      .map((park) => park["park_spot"]);
  };
  return {
    parks,
    total_cnt,
    current_cnt,
    occupied,
    getParkList,
  };
});
