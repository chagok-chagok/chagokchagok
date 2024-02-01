<script setup>
const props = defineProps(["imageSrc", "title", "number"]);
</script>

<template>
  <div class="card">
    <img :src="imageSrc" alt="Card Image" />
    <div class="content">
      <h4>{{ title }}</h4>
      <p>{{ number }}</p>
    </div>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.card {
  display: flex;
  flex-direction: row;
  align-items: center;
  /* border: 1px solid #ccc; */
  border-radius: 8px;
  padding: 16px;
  text-align: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
  width: 250px; /* 가로 크기 수정 */
  height: 117px; /* 세로 크기 수정 */
}

img {
  width: 68px; /* 이미지의 가로 크기 수정 */
  height: 68px; /* 이미지의 세로 크기 수정 */
  border-radius: 4px;
  margin-bottom: 8px;
}

.content {
  width: 102px; /* title과 number가 들어 있는 div의 가로 크기 수정 */
  height: 51px; /* title과 number가 들어 있는 div의 세로 크기 수정 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: baseline;
  margin-left: 16px; /* 내용과 이미지 사이의 간격 조정 */
}

h4 {
  margin: 0;
  color: #8a92a6;
  font-family: "Inter", sans-serif;
}
p {
  margin: 8px 0;
  font-family: "Poppins";
}
</style>
