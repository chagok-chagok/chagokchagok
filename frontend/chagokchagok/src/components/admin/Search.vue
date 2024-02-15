<script setup>
import { ref } from "vue";
import { useParkingSectionStore } from "@/stores/parkingSectionStore";
import { instance } from "@/utils/mainAxios";
const { VITE_VUE_SPRING_URL } = import.meta.env;

const selectedOption = ref("vehicle");
const searchQuery = ref("");
const searchResult = ref(null);
const parkingSectionStore = useParkingSectionStore();

const search = async () => {
  try {
    instance.defaults.headers["Authorization"] =
      sessionStorage.getItem("accessToken");
    const response = await instance.post(`${VITE_VUE_SPRING_URL}admin/search`, {
      type: selectedOption.value,
      value: searchQuery.value,
    });
    searchResult.value = response.data;
    parkingSectionStore.searchRES = response.data;
  } catch (error) {}
};
</script>

<template>
  <div id="app">
    <div class="search-bar">
      <select v-model="selectedOption">
        <option value="CAR_NUMBER">차 번호</option>
        <option value="SPOT_NUMBER">구역 번호</option>
      </select>
      <input type="text" v-model="searchQuery" @keyup.enter="search" />
    </div>
    <div v-if="searchResult">
      <p>구역번호: {{ searchResult.area }}</p>
      <p>차량번호: {{ searchResult.car_no }}</p>
      <p>입차시간: {{ searchResult.entryTime }}</p>
    </div>
  </div>
</template>

<style>
.search-bar {
  display: flex;
  align-items: center;
  position: relative;
}

.search-bar select {
  position: absolute;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 8px;
  z-index: 1;
}

.search-bar input[type="text"] {
  padding: 6px;
  padding-left: 95px;
  border: 1px solid #ccc;
  border-color: #eff0f6;
  border-radius: 8px;
  width: 38%;
  background-color: #f7f7f7;
  opacity: 0.8;
}
</style>

<style scoped>
#app {
  max-width: 640px;
  margin: 0 auto;
  height: auto;
}
</style>
