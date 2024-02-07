<script setup>
import { useGuestStore } from "@/stores/guest";
import { storeToRefs } from "pinia";

const guestStore = useGuestStore();
const { total_cnt, current_cnt } = storeToRefs(guestStore);
</script>

<template>
  <div class="svg-container">
    <svg
      width="215"
      height="212"
      viewBox="0 0 215 212"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <g clip-path="url(#clip0_44_2118)">
        <path
          fill-rule="evenodd"
          clip-rule="evenodd"
          d="M91.2314 162.089C91.7961 162.03 92.3683 162.095 92.909 162.268C123.405 172.017 156.919 160.645 172.81 133.12C191.279 101.131 178.954 59.4402 145.282 39.9998C111.61 20.5594 69.3424 30.7314 50.874 62.7196C38.2718 84.5471 40.0076 110.893 53.0799 131.604C53.5324 132.32 53.7644 133.157 53.7227 134.004L52.3694 161.472C52.2488 163.92 54.3378 165.9 56.7756 165.648L91.2314 162.089Z"
          fill="#E4E4E4"
        />
      </g>
      <defs>
        <clipPath id="clip0_44_2118">
          <rect
            width="160"
            height="152"
            fill="white"
            transform="translate(76) rotate(30)"
          />
        </clipPath>
      </defs>
    </svg>
    <div class="overlay-div">
      <span :class="['text', 'green']">{{ current_cnt }}</span>
      <span class="text">/</span>
      <span class="text">{{ total_cnt }}</span>
    </div>
  </div>
</template>

<style scoped>
.svg-container {
  position: relative;
  display: flex;
  justify-content: center;
  /* height: 100%; */
  width: 30%;
}
.overlay-div {
  position: absolute;
  top: 47%;
  left: 51%;
  transform: translate(-50%, -50%);
  /* background-color: rgba(255, 0, 0, 0.5);
  width: 100px;
  height: 100px; */
  display: flex;
  justify-content: center;
  align-items: center;
}
.text {
  font-size: 30px;
  font-family: fantasy;
  overflow: visible;
  color: black;
}
.green {
  color: #3eb667;
}
</style>
