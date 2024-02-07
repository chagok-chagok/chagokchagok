<script setup>
import AppLocation from "./AppLocation.vue";

const props = defineProps({
  parkings: Array, // name: String, isDisabled: Boolean
});
const emit = defineEmits("location-select");
const locationSelect = (message) => {
  console.log("AppSection message : ", message);
  emit("location-select", message);
};
</script>

<template>
  <div>
    <table class="section-container">
      <tr v-for="i in 4" class="section">
        <td>
          <app-location
            :location="parkings[i - 1]"
            :class="{
              location: true,
              right: true,
              disabled: location.isDisabled,
              'not-disabled': !location.isDisabled,
            }"
            @click-location="locationSelect"
          ></app-location>
        </td>
        <td>
          <app-location
            :location="parkings[i + 3]"
            :class="{
              location: true,
              disabled: location.isDisabled,
              'not-disabled': !location.isDisabled,
            }"
            @click-location="locationSelect"
          ></app-location>
        </td>
      </tr>
    </table>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  border-collapse: collapse;
}

td {
  width: 50%;
}
.section-container {
  width: 180px;
  height: 200px;
  /* background-color: blueviolet; */
}
.section {
  /* background-color: bisque; */
  border-top: 2px dashed black;
  border-bottom: 2px dashed black;
}

.not-disabled {
  height: 25%;
}
.disabled {
  height: 50%;
}
/* .left {
  border-left: 2px dashed black;
} */
.right {
  border-right: 2px dashed black;
}
.location {
  height: 100%;
  padding: 5px;
}
</style>
