import { defineStore } from "pinia";
import { ref } from "vue";

export const useTokenStore = defineStore("tokenStore", () => {
  const isTokenRefreshing = ref(false);

  return {
    isTokenRefreshing,
  };
});
