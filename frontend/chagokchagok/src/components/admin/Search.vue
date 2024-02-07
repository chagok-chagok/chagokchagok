<script setup>
import { ref } from "vue";
import axios from "axios";
import { useParkingSectionStore } from "@/stores/parkingSectionStore";

const selectedOption = ref("vehicle");
const searchQuery = ref("");
const searchResult = ref(null);
const parkingSectionStore = useParkingSectionStore();

const search = async () => {
  try {
    const response = await axios.post("http://localhost:8080/admin/search", {
      type: selectedOption.value,
      value: searchQuery.value,
    });
    searchResult.value = response.data;
    parkingSectionStore.searchRES = response.data;
  } catch (error) {
    console.error("Search error:", error);
    // 에러 처리 로직을 추가합니다.
  }
};
</script>

<template>
  <div id="app">
    <div class="search-bar">
      <select v-model="selectedOption">
        <option value="CAR_NUMBER">차량번호</option>
        <option value="SPOT_NUMBER">구역번호</option>
      </select>
      <input
        type="text"
        v-model="searchQuery"
        placeholder="검색"
        @keyup.enter="search"
      />
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
  gap: 10px;
}

.search-bar select {
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-bar input[type="text"] {
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
}
</style>
