// stores/parkingStore.js
import { defineStore } from "pinia";

export const useParkingStore = defineStore("parking", {
  state: () => ({
    allocated_location: "",
    car_no: "",
    entry_time: "",
  }),
  actions: {
    setAllocatedLocation(location) {
      this.allocated_location = location;
    },
  },
});
