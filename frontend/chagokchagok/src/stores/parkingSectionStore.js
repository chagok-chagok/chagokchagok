import { ref } from "vue";
import { defineStore } from "pinia";
import { localAxios } from "@/utils/mainAxios";

const local = localAxios();
const instance = localAxios();

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

  const update = async () => {
    getParkList();
  };

  const getParkList = async () => {
    const { data } = await local.get("/guest");
    parks.value = data.parks;
    occupied.value = parks.value
      .filter((park) => park["park_status"])
      .map((park) => park["park_spot"]);
  };

  const getCarInfo = async (area) => {
    instance.defaults.headers["Authorization"] =
      sessionStorage.getItem("accessToken");
    const { data } = await instance.post("/admin/caloc", { area: area });
    carInfo.value = data;
  };

  const searchLocation = async (queryType, queryValue) => {
    try {
      instance.defaults.headers["Authorization"] =
        sessionStorage.getItem("accessToken");
      const { data } = await instance.post("/admin/search", {
        type: queryType.value,
        value: queryValue.value.trim(),
      });
      targetLocation.value = data;
    } catch (error) {
      targetLocation.value = {};
    }
  };

  const getInParkCarList = async () => {
    instance.defaults.headers["Authorization"] =
      sessionStorage.getItem("accessToken");
    const { data } = await instance.get("/admin/cars");
    inParkCarList.value = data.cars;
  };

  const exchangeLocation = async (carNum) => {
    instance.defaults.headers["Authorization"] =
      sessionStorage.getItem("accessToken");
    await instance.put("/admin/exchange", {
      car_num: carNum.trim(),
      original_location: originalLocation.value,
    });
  };

  const unlockBar = async (carNum) => {
    instance.defaults.headers["Authorization"] =
      sessionStorage.getItem("accessToken");
    await instance.put("/admin/bar", {
      car_no: carNum.trim(),
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
    update,
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
