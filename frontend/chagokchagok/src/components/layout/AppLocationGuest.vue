<script setup>
import { useGuestStore } from "@/stores/guest";
import { storeToRefs } from "pinia";
import CarGuest from "../icons/CarGuest.vue";
const guestStore = useGuestStore();
const { occupied } = storeToRefs(guestStore);

const props = defineProps({
  parkSpot: String,
});
</script>

<template>
  <div v-if="occupied.includes(parkSpot)" :class="['location']">
    <car-guest size="41"></car-guest>
  </div>
  <div v-else :class="['location']"><div class="empty"></div></div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  border-collapse: collapse;
}
.location {
  display: flex;
  justify-content: center;
  align-items: center;
}
.empty {
  height: 100%;
  width: 100%;
  background-color: #eff5ff;
  /* border: 1px solid black; */
  border-radius: 10px;
}
</style>
