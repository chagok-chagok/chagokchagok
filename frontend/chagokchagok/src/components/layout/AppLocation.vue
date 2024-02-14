<script setup>
import { mdiWheelchair } from "@mdi/js";
import MdiIconButton from "../icons/MdiIconButton.vue";
import { useParkingSectionStore } from "@/stores/parkingSectionStore";
import { storeToRefs } from "pinia";
import { ref } from "vue";
const parkingSectionStore = useParkingSectionStore();
const {
  occupied,
  targetLocation,
  modalPosition,
  isModalOpen,
  originalLocation,
} = storeToRefs(parkingSectionStore);

const props = defineProps({
  location: Object, // name: String, isDisabled: Boolean
});

const emit = defineEmits(["click-location"]);
const clickLocation = (locationName) => {
  // console.log("locationName : ", locationName);
  if (occupied.value.includes(locationName)) {
    originalLocation.value = locationName;
    targetLocation.value = "";
    modalPosition.value.x = event.clientX;
    modalPosition.value.y = event.clientY;
    // isModalOpen.value = true;
    parkingSectionStore.openTooltip();
    console.log("x : ", modalPosition.value.x, "y : ", modalPosition.value.y);
    emit("click-location", locationName);
  }
};
</script>

<template>
  <div
    v-if="location.isDisabled"
    :class="{
      location: true,
      disabled: true,
    }"
    @click="clickLocation(props.location.name)"
  >
    <div
      :class="{
        car: true,
        'yes-car': occupied.includes(location.name),
        'no-car': !occupied.includes(location.name),
        active: targetLocation.area == location.name,
      }"
    >
      <mdi-icon-button :path="mdiWheelchair" :size="33"></mdi-icon-button>
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
    <div
      :class="{
        car: true,
        'yes-car': occupied.includes(location.name),
        'no-car': !occupied.includes(location.name),
        active: targetLocation.area == location.name,
      }"
    >
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
  /* height: 100%; */
  /* background-color: cadetblue; */
}
.disabled {
  /* height: 100%; */
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

.yes-car {
  background-color: #84adff;
  border: none;
  border-radius: 7px;
  cursor: pointer;
}
.no-car {
  background-color: #e1e1e1;
  border: none;
  border-radius: 7px;
}
.active {
  background-color: #84ff9f;
  border: none;
  border-radius: 7px;
}
</style>
