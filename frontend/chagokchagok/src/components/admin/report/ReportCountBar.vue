<script setup>
import { ref } from "vue";
import ReportCountCard from "../../layout/ReportCountCard.vue";
import { useReportStore } from "@/stores/report";
import { storeToRefs } from "pinia";

const reportStore = useReportStore();
const { today_cnt, unsolved_cnt, hotline_cnt, sensor_cnt, autosystem_cnt } =
  storeToRefs(reportStore);

const cards = ref([
  {
    imageSrc: "https://placekitten.com/300/200",
    title: "Today",
    number: today_cnt,
  },
  {
    imageSrc: "https://placekitten.com/300/201",
    title: "Unsolved",
    number: unsolved_cnt,
  },
  {
    imageSrc: "https://placekitten.com/300/202",
    title: "HotLine",
    number: hotline_cnt,
  },
  {
    imageSrc: "https://placekitten.com/300/202",
    title: "Auto System",
    number: autosystem_cnt,
  },
]);
</script>

<template>
  <div class="card-container">
    <report-count-card
      v-for="(card, index) in cards"
      :key="index"
      :imageSrc="card.imageSrc"
      :title="card.title"
      :number="card.number"
    />
  </div>
</template>

<style scoped>
.card-container {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}
</style>
