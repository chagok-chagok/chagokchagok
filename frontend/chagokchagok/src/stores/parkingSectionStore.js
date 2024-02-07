import { ref } from "vue";
import { defineStore } from "pinia";
import instance from "@/utils/mainAxios";

export const useParkingSectionStore = defineStore("parkingSection", () => {
  /* =========== 차량 현황 받아오는 요청 ============ */
  const parks = ref([]); // 주차장 현재 정보, {park_spot:String, park_status=Boolean}
  const occupied = ref([]); // 현재 차가 있는 주차장
  const carInfo = ref({});
  const targetLocation = ref({});
  const modalPosition = ref({ x: 0, y: 0 });
  const isModalOpen = ref(false);
  const isUnlockBarModalOpen = ref(false);
  const isExchangeModalOpen = ref(false);

  const getParkList = async () => {
    const { data } = await instance.get("/guest");
    parks.value = data.parks;
    occupied.value = parks.value
      .filter((park) => park["park_status"])
      .map((park) => park["park_spot"]);
  };

  const getCarInfo = async (area) => {
    const { data } = await instance.post("/admin/caloc", { area: area });
    carInfo.value = data;
  };

  const searchLocation = async (queryType, queryValue) => {
    const { data } = await instance.post("/admin/search", {
      type: queryType.value,
      value: queryValue.value,
    });
    targetLocation.value = data;
  };
  return {
    parks,
    occupied,
    carInfo,
    targetLocation,
    modalPosition,
    isModalOpen,
    isUnlockBarModalOpen,
    isExchangeModalOpen,
    getParkList,
    getCarInfo,
    searchLocation,
  };
});
