<script setup>
import { ref } from "vue";
import ReportCountCard from "../../layout/ReportCountCard.vue";
import { useReportStore } from "@/stores/report";
import { storeToRefs } from "pinia";
import { mdiCalendarCheckOutline } from "@mdi/js";
import { mdiCheckboxMultipleOutline } from "@mdi/js";
import { mdiPhoneCheck } from "@mdi/js";
import { mdiCogClockwise } from "@mdi/js";
import { mdiSignalCellularOutline } from "@mdi/js";
import MdiIcon from "@/components/icons/MdiIcon.vue";

const reportStore = useReportStore();
const { today_cnt, unsolved_cnt, hotline_cnt, sensor_cnt, autosystem_cnt } =
  storeToRefs(reportStore);

const cards = ref([
  {
    path: mdiCalendarCheckOutline,
    title: "Today",
    number: today_cnt,
    ringColor: "#74EC80",
  },
  {
    path: mdiCheckboxMultipleOutline,
    title: "Unsolved",
    number: unsolved_cnt,
    ringColor: "#FF9292",
  },
  {
    path: mdiPhoneCheck,
    title: "HotLine",
    number: hotline_cnt,
    ringColor: "#FF0099",
  },
  {
    path: mdiCogClockwise,
    title: "Auto System",
    number: autosystem_cnt,
    ringColor: "#97A8FF",
  },
]);
</script>

<template>
  <div class="report-title">
    <mdi-icon :path="mdiSignalCellularOutline" size="40"></mdi-icon>
    <h3>신고기록</h3>
  </div>
  <div class="card-container">
    <report-count-card
      v-for="(card, index) in cards"
      :key="index"
      :path="card.path"
      :title="card.title"
      :number="card.number"
      :ringColor="card.ringColor"
    />
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.report-title {
  height: 40px;
  display: flex;
  flex-direction: row;
  justify-content: baseline;
  align-items: center;
  padding-top: 10px;
  margin-top: 10px;
}
.report-title h3 {
  font-family: "Poppins";
}
.card-container {
  display: flex;
  justify-content: space-between;
  /* margin-top: 20px; */
}
</style>
