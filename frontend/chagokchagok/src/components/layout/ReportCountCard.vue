<script setup>
import MdiIcon from "@/components/icons/MdiIcon.vue";
const props = defineProps(["path", "title", "number", "ringColor"]);
</script>

<template>
  <div class="card">
    <div class="svg-container">
      <svg
        class="background-svg"
        width="65"
        height="65"
        viewBox="0 0 73 73"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <circle cx="36.5" cy="36.5" r="34" stroke="#E9ECEF" stroke-width="2" />

        <path
          d="M36.5 2.5C44.8437 2.5 52.8962 5.56812 59.1245 11.1203C65.3528 16.6725 69.3221 24.3211 70.2767 32.6101C71.2313 40.899 69.1046 49.2497 64.3014 56.0723C59.4983 62.8949 52.354 67.7132 44.2287 69.6098C36.1033 71.5065 27.5642 70.3492 20.2369 66.3581C12.9095 62.3671 7.30555 55.821 4.49173 47.966C1.6779 40.111 1.85069 31.4956 4.97722 23.7597C8.10374 16.0239 13.9657 9.70775 21.4472 6.01372"
          :stroke="ringColor"
          stroke-width="4"
          stroke-linecap="round"
          fill="none"
        />
      </svg>
      <mdi-icon
        :path="path"
        :size="40"
        fill="#434343"
        class="mdi-icon"
      ></mdi-icon>
    </div>

    <div class="content">
      <h5>{{ title }}</h5>
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
  justify-content: center;
  /* border: 1px solid #ccc; */
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
  width: 23.27%; /* 가로 크기 수정 */
  height: 10.89%; /* 세로 크기 수정 */
}

.svg-container {
  position: relative;
  display: flex;
  height: 100%;
  width: 30%;
}
.background-svg {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.mdi-icon {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.content {
  width: 50%; /* title과 number가 들어 있는 div의 가로 크기 수정 */
  height: 100%; /* title과 number가 들어 있는 div의 세로 크기 수정 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: baseline;
  margin-left: 16px; /* 내용과 이미지 사이의 간격 조정 */
}

h5 {
  margin: 0;
  color: #8a92a6;
  font-family: "Inter", sans-serif;
  text-align: left;
  white-space: nowrap;
}
p {
  margin: 8px 0;
  font-family: "Poppins";
}
</style>
