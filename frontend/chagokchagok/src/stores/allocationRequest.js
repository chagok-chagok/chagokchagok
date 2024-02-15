import { defineStore } from "pinia";
import { instance } from "@/utils/mainAxios";
import { ref } from "vue";

/* 차량 배정 요청용 Request Store */
export const useAllocationRequestStore = defineStore("request", () => {
  const car_no = ref("");
  const isDisabled = ref(false);

  return {
    car_no,
    isDisabled,
  };
});
