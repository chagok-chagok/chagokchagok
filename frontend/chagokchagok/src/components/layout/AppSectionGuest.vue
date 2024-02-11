<script setup>
import AppLocationGuest from "./AppLocationGuest.vue";
const props = defineProps({
  parkings: Array, // name: String, isDisabled: Boolean
  isLeft: Boolean,
});
const emit = defineEmits("location-select");
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
        <app-location-guest
          class="location"
          :park-spot="location.name"
        ></app-location-guest>
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
  width: 100%;
  height: 100%;
}
.section {
  border-top: 2px dashed black;
  border-bottom: 2px dashed black;
}

.not-disabled {
  height: 25%;
}
.disabled {
  height: 50%;
}
.left {
  border-left: 2px dashed black;
}
.right {
  border-right: 2px dashed black;
}
.location {
  height: 100%;
  padding: 4%;
}
</style>
