<script setup>
const props = defineProps(["icon", "pageTitle"]);
</script>

<template>
  <div class="header">
    <font-awesome-icon class="fa-2x" :icon="icon" style="color: #11263c" />
    <span class="pageTitle">{{ pageTitle }}</span>
  </div>
</template>

<style scoped>
.header {
  display: flex;
  align-items: center;
  padding: 10px;
  gap: 15px;
}

.icon {
  margin-right: 10px;
  width: 30px;
  height: auto;
}

.pageTitle {
  font-size: 18px;
  font-weight: bold;
}
</style>
