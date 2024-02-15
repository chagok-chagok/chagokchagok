<script setup>
import printJs from "print-js";
import { ref } from "vue";
import { useParkingStore } from "@/stores/parkingStore";
const parkingStore = useParkingStore();
const item = ref([]); // print-js에서 json 출력하려면 객체의 배열 형태만 가능함.

// 이부분을 parkStore의 변수들로 대체하면 됨.
item.value = [
  {
    allocated_location: "A1",
    car_no: "77칠7777",
    entry_time: new Date(),
  },
];

// print함수 => RecommendationScreen에서 호출하면 될듯함
// 스타일 변경해야 함
const print = () => {
  printJs({
    printable: item.value,
    properties: [
      { field: "allocated_location", displayName: "배정 자리" },
      { field: "car_no", displayName: "차량 번호" },
      { field: "entry_time", displayName: "입차 시간" },
    ],
    type: "json",
  });
};

// print();
</script>

<template>
  <button @click="print">print</button>
</template>
