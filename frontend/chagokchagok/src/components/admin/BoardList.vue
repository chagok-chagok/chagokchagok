<script setup>
import { useBoardStore } from "@/stores/board";
import { storeToRefs } from "pinia";
import { computed, ref } from "vue";

const boardStore = useBoardStore();
const page = ref(0);
boardStore.getReportList(page.value);

const total_page_count = computed(
  () => storeToRefs(boardStore).total_page_count
);
const board = computed(() => boardStore.reports);

const plus = () => {
  page.value = page.value + 1;
};
const minus = () => {
  page.value = page.value - 1;
};
</script>

<template>
  <div>
    <div>page : {{ page }}</div>
    <div>total_page_count : {{ total_page_count }}</div>
    <div>
      <ul>
        <li v-for="item in board">
          {{ item }}
        </li>
      </ul>
    </div>
    <div>
      <button @click="minus">page down</button
      ><button @click="plus">page up</button>
    </div>
  </div>
</template>

<style scoped>
button {
  height: 50px;
  width: 100px;
}
</style>
