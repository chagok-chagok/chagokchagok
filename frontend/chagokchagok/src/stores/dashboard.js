import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";
import moment from "moment";

export const dashBoardStore = defineStore("dashBoardStore", () => {
  //대쉬보드의 데이터를 로드
  const getDashBoard = async () => {
    const resp = await axios.get("http://localhost:8080/admin/dashboard");
    return resp.data;
  };

  return {
    getDashBoard,
  };
});
