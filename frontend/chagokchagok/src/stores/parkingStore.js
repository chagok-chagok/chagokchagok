// stores/parkingStore.js
import { defineStore } from "pinia";

export const useParkingStore = defineStore("parking", {
  state: () => ({
    allocatedLocation: "",
  }),
  actions: {
    setAllocatedLocation(location) {
      this.allocatedLocation = location;
    },
  },
});
