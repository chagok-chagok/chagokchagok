<script setup>
import AppLocation from "./AppLocation.vue";

const props = defineProps({
  parkings: Array, // name: String, isDisabled: Boolean
  isLeft: Boolean,
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
      <tr
        v-for="location in parkings"
        class="section"
        :class="{
          left: props.isLeft,
          right: !props.isLeft,
          disabled: location.isDisabled,
          'not-disabled': !location.isDisabled,
        }"
      >
        <app-location
          :location="location"
          class="location"
          @click-location="locationSelect"
        ></app-location>
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
.section-container {
  width: 10vh;
  height: 100%;
}
.section {
  border-top: 2px dashed rgb(164, 164, 164);
  border-bottom: 2px dashed rgb(164, 164, 164);
  font-family: 'Poppins', sans-serif;
  font-weight: 600;
}

.not-disabled {
  height: 25%;
}
.disabled {
  height: 50%;
}
.left {
  border-left: 2px dashed rgb(164, 164, 164);
}
.right {
  border-right: 2px dashed rgb(164, 164, 164);
}
.location {
  height: 100%;
  padding: 3px;
}
</style>
