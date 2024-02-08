import { ref } from "vue";
import { defineStore } from "pinia";

export const useParkingSectionStore = defineStore("parkingSection", () => {
  const searchRES = ref("");
  return {
    searchRES,
  };
});
