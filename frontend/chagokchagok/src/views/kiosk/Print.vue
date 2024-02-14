<script setup>
import printJs from "print-js";
import { ref } from "vue";

const items = ref([
  {
    date: "2019-01-01",
    name: "홍길동",
    item: "회비",
    group: "1기",
    amt: "30,000",
    remark: "2019 1월 회비",
  },
  {
    date: "2019-01-01",
    name: "김영일",
    item: "회비",
    gorup: "3기",
    amt: "30,000",
    remark: "2019 1월 회비",
  },
  {
    date: "2019-01-02",
    name: "전도일",
    item: "회비",
    gorup: "5기",
    amt: "30,000",
    remark: "2019 1월 회비",
  },
  {
    date: "2019-02-03",
    name: "박상도",
    item: "회비",
    gorup: "6기",
    amt: "30,000",
    remark: "2019 1월 회비",
  },
]);

const print = () => {
  printJs({
    printable: items.value,
    properties: [
      { field: "date", displayName: "날짜" },
      { field: "item", displayName: "항목" },
      { field: "name", displayName: "이름" },
      { field: "group", displayName: "기수" },
      { field: "amt", displayName: "금액" },
      { field: "remark", displayName: "비고" },
    ],
    type: "json",
  });
};
</script>

<template>
  <button @click="print">print</button>
</template>
