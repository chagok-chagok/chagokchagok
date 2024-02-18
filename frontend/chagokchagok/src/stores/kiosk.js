import { ref } from "vue";
import { defineStore } from "pinia";
import { instance } from "@/utils/mainAxios";
import { useRouter } from "vue-router";
import printJS from "print-js";

export const useKioskStore = defineStore("kioskStore", () => {
  const router = useRouter();
  const carNumber = ref("");
  const isDisabled = ref(false);
  const allocated_location = ref("");
  const item = ref([]);
  const updateCurrentTime = () => {
    const now = new Date();
  };

  const getCurrentTime = () => {
    const now = new Date();
    return now.toTimeString().substring(0, 5);
  };

  const allocation = async () => {
    console.log("allocation 슈웃");
    instance
      .post("park/allocation", {
        car_no: carNumber.value,
        is_disabled: isDisabled.value,
      })
      .then((response) => {
        // print 코드 작성 필요
        item.value = [
          {
            allocated_location: "A2",
            car_no: "07서0484",
            entry_time: new Date(),
          },
        ];
        allocated_location.value = response.data.allocated_location;

        console.log("allocation : ", allocated_location.value);
        print();
        router.push({ name: "allocation" });
      })
      .catch((error) => {
        console.log(error);
        // allocatedLocation.value = "";
        router.push({ name: "no-place" });
      });
  };

  const print = () => {
    printJS({
      printable: item.value,
      properties: [
        { field: "allocated_location", displayName: "배정 자리" },
        { field: "car_no", displayName: "차량 번호" },
        { field: "entry_time", displayName: "입차 시간" },
      ],
      type: "json",
    });
  };

  const selectParking = async (disalbed) => {
    isDisabled.value = disalbed;
    if (!carNumber.value) {
      router.push({ name: "recognition-error" });
      return; // 함수 실행 중단
    }
    console.log("selectParking들어옴!!!");
    if (isDisabled.value) {
      console.log(carNumber.value);
      console.log(isDisabled.value);
      instance
        .post(
          "park/allocation",
          {
            car_no: carNumber.value,
            is_disabled: isDisabled.value,
          },
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        )
        .then((response) => {
          allocated_location.value = response.data.allocated_location;
        })
        .catch((error) => {
          //   allocatedLocation.value = "";
          router.push({ name: "no-place" });
        });
    } else {
      router.push({ name: "recommendation" });
    }
  };
  return {
    updateCurrentTime,
    allocation,
    selectParking,
    getCurrentTime,
    // currentTime,
    carNumber,
    isDisabled,
    allocated_location,
  };
});
