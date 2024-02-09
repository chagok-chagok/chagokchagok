import { ref } from "vue";
import { defineStore } from "pinia";
import { instance } from "@/utils/mainAxios";

export const useParkingSectionStore = defineStore("parkingSection", () => {
  /* =========== 차량 현황 받아오는 요청 ============ */
  const parks = ref([]); // 주차장 현재 정보, {park_spot:String, park_status=Boolean}
  const occupied = ref([]); // 현재 차가 있는 주차장
  const carInfo = ref({});
  const targetLocation = ref({});
  const modalPosition = ref({ x: 0, y: 0 });
  const inParkCarList = ref([]);
  const originalLocation = ref("");
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

  const getInParkCarList = async () => {
    const { data } = await instance.get("/admin/cars");
    inParkCarList.value = data.cars;
  };

  const exchangeLocation = async (carNum) => {
    await instance.put("/admin/exchange", {
      car_num: carNum,
      original_location: originalLocation.value,
    });
  };

  const unlockBar = async (carNum) => {
    await instance.put("/admin/bar", {
      car_no: carNum,
      park_full_name: originalLocation.value,
    });
  };

  const openTooltip = () => {
    isModalOpen.value = true;
  };
  const closeTooltip = () => {
    isModalOpen.value = false;
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
    originalLocation,
    inParkCarList,
    getParkList,
    getCarInfo,
    searchLocation,
    getInParkCarList,
    exchangeLocation,
    unlockBar,
    openTooltip,
    closeTooltip,
  };
});
