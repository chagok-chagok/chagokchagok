<!-- mdi icon format -->
<!-- size : 아이콘 사이즈 -->
<!-- path : https://pictogrammers.com/library/mdi에서 찾을 icon 검색 후 @mdi/js에서 import -->
<!-- fill = none으로 -->
<script setup>
defineProps(["path", "fill", "size"]);
</script>

<template>
  <svg
    :width="size"
    :height="size"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <path :d="path" :fill="fill" />
  </svg>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
</style>
