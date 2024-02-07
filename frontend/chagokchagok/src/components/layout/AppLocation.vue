<script setup>
import { mdiWheelchair } from "@mdi/js";
import MdiIconButton from "../icons/MdiIconButton.vue";
const props = defineProps({
  location: Object, // name: String, isDisabled: Boolean
});

const emit = defineEmits(["click-location"]);
const clickLocation = (locationName) => {
  console.log("locationName : ", locationName);
  emit("click-location", locationName);
};
</script>

<template>
  <div
    v-if="location.isDisabled"
    :class="['location', 'disabled']"
    @click="clickLocation(props.location.name)"
  >
    <div class="car">
      <mdi-icon-button :path="mdiWheelchair" :size="50"></mdi-icon-button>
      <p>{{ location.name }}</p>
    </div>
  </div>
  <div
    v-else
    :class="['location', 'not-disabled']"
    @click="clickLocation(props.location.name)"
  >
    <div class="car">
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
</style>
