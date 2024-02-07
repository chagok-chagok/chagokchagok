<script setup>
import { mdiWheelchair } from "@mdi/js";
import MdiIconButton from "../icons/MdiIconButton.vue";
import { useParkingSectionStore } from "@/stores/parkingSectionStore";
import { storeToRefs } from "pinia";
import { ref } from "vue";

const parkingSectionStore = useParkingSectionStore();
const { searchRES } = storeToRefs(parkingSectionStore);
const parkingarea = ref("");
parkingarea.value = searchRES.value.area;

const props = defineProps({
  location: Object, // name: String, isDisabled: Boolean
});

const emit = defineEmits(["click-location"]);
const clickLocation = (locationName) => {
  console.log("locationName : ", locationName);
  emit("click-location", locationName);
};
</script>
s

<template>
  <div
    v-if="location.isDisabled"
    :class="{
      location: true,
      disabled: true,
    }"
    @click="clickLocation(props.location.name)"
  >
    <div :class="{ car: true, active: searchRES.area == location }">
      <mdi-icon-button :path="mdiWheelchair" :size="50"></mdi-icon-button>
      <p>{{ location.name }}</p>
    </div>
  </div>
  <div
    v-else
    :class="{
      location: true,
      'not-disabled': true,
    }"
    @click="clickLocation(props.location.name)"
  >
    <div :class="{ car: true, active: searchRES.area == location.name }">
      <p>{{ location.name }}</p>
    </div>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  border-collapse: collapse;
}
.location {
  height: 100%;
  /* background-color: cadetblue; */
}
.disabled {
  height: 100%;
  /* background-color: yellow; */
}
.car {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  border: 1px solid black;
  border-radius: 10px;
}

.active {
  background-color: #84ff9f;
}
</style>
