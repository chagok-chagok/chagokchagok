// stores/parkingStore.js
import { defineStore } from "pinia";

export const useParkingStore = defineStore("parking", {
  state: () => ({
    allocated_location: "",
  }),
  actions: {
    setAllocatedLocation(location) {
      this.allocated_location = location;
    },
  },
});
