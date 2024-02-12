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
              disabled: parkings[i - 1].isDisabled,
              'not-disabled': !parkings[i - 1].isDisabled,
            }"
            @click-location="locationSelect"
          ></app-location>
        </td>
        <td>
          <app-location
            :location="parkings[i + 3]"
            :class="{
              location: true,
              disabled: parkings[i + 3].isDisabled,
              'not-disabled': !parkings[i + 3].isDisabled,
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
  /* width: 100%; */
  /* height: 100%; */
  width: 20vh;
  height: 100%;
  /* background-color: blueviolet; */
}
.section {
  /* background-color: bisque; */
  border-top: 2px dashed rgb(164, 164, 164);
  border-bottom: 2px dashed rgb(164, 164, 164);
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
  border-right: 2px dashed rgb(164, 164, 164);
}
.location {
  height: 100%;
  padding: 3px;
}

td {
  width: 50%;
}
</style>
