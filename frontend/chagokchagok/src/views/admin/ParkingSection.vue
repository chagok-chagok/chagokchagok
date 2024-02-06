<script setup>
import { RouterLink, RouterView } from "vue-router";
import navbarVue from "@/components/admin/navbar.vue";
import AppPageHeader from "@/components/admin/AppPageHeader.vue";
import { ref, onMounted } from "vue";
</script>

<template>
  <main>
    <div class="contents">
      <div class="parking-img ml-30">
        <img />
        <p>차량 배정 현황</p>
      </div>
      <div class="parking-desc ml-60">
        <span
          >각 자리 클릭 시 차단바 제어, 자리변경 등의 동작이 가능합니다.</span
        >
      </div>
      <div class="parking-sect ml-60 mb-60 mt-10">
        <div class="parking-sect-info">
          <select class="parking-select">
            <option>차 번호</option>
            <option>구역 번호</option>
          </select>
          <input class="parking-select-input" type="text" value="30가 3425" />
        </div>
      </div>
      <div class="parking-area ml-100">
        <table class="table">
          <tbody>
            <tr>
              <td><a href="#" class="btn rounded blue">A01</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">A07</a></td>
              <td><a href="#" class="btn rounded gray">B01</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">B09</a></td>
              <td><a href="#" class="btn rounded blue">C01</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">C09</a></td>
            </tr>
            <tr>
              <td><a href="#" class="btn rounded blue">A02</a></td>
              <td></td>
              <td><a href="#" class="btn rounded blue">A08</a></td>
              <td><a href="#" class="btn rounded blue">B02</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">B10</a></td>
              <td><a href="#" class="btn rounded blue">C02</a></td>
              <td></td>
              <td><a href="#" class="btn rounded blue">C10</a></td>
            </tr>
            <tr>
              <td rowspan="2">
                <div class="disability-person">
                  <a href="#">A3</a>
                  <img src="@/assets/disabled_icon.png" alt="장애인 이미지" />
                </div>
              </td>
              <td></td>
              <td><a href="#" class="btn rounded gray">A09</a></td>
              <td><a href="#" class="btn rounded gray">B03</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">B11</a></td>
              <td><a href="#" class="btn rounded gray">C03</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">C11</a></td>
            </tr>
            <tr>
              <td></td>
              <td><a href="#" class="btn rounded blue">A10</a></td>
              <td><a href="#" class="btn rounded green">B04</a></td>
              <td></td>
              <td><a href="#" class="btn rounded blue">B12</a></td>
              <td><a href="#" class="btn rounded blue">C04</a></td>
              <td></td>
              <td><a href="#" class="btn rounded blue">C12</a></td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <td rowspan="2">
                <div class="disability-person">
                  <a href="#">A04</a>
                  <img src="@/assets/disabled_icon.png" alt="장애인 이미지" />
                </div>
                s
              </td>
              <td></td>
              <td><a href="#" class="btn rounded gray">A11</a></td>
              <td><a href="#" class="btn rounded blue">B05</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">B13</a></td>
              <td><a href="#" class="btn rounded blue">C05</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">C13</a></td>
            </tr>
            <tr>
              <td></td>
              <td><a href="#" class="btn rounded gray">A12</a></td>
              <td><a href="#" class="btn rounded gray">B06</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">B14</a></td>
              <td><a href="#" class="btn rounded gray">C06</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">C14</a></td>
            </tr>
            <tr>
              <td><a href="#" class="btn rounded gray">A05</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">A13</a></td>
              <td><a href="#" class="btn rounded gray">B07</a></td>
              <td></td>
              <td><a href="#" class="btn rounded blue">B15</a></td>
              <td><a href="#" class="btn rounded blue">C07</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">C15</a></td>
            </tr>
            <tr>
              <td><a href="#" class="btn rounded gray">A06</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">A14</a></td>
              <td><a href="#" class="btn rounded gray">B08</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">B16</a></td>
              <td><a href="#" class="btn rounded gray">C08</a></td>
              <td></td>
              <td><a href="#" class="btn rounded gray">C16</a></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </main>
</template>

<style scoped>
main {
  margin: 0;
  padding: 0;
  background-color: #f2f2f7;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.ml-30 {
  margin-left: 30px;
}
.ml-60 {
  margin-left: 60px;
}
.ml-100 {
  margin-left: 100px;
}

.mb-30 {
  margin-bottom: 30px;
}

.mt-10 {
  margin-top: 10px;
}

.mb-40 {
  margin-bottom: 40px;
}

.mb-60 {
  margin-bottom: 60px;
}

.parking-sect-info {
  width: 100%;
  height: 90%;
}

.parking-select {
  position: absolute;
  z-index: 2;
  margin: 6px;
}

.parking-select-input {
  position: absolute;
  width: 30%;
  height: 2%;
  text-align: center;
  border-radius: 8px;
}

.table {
  border-collapse: separate;
  border-spacing: 10px 10px;
}

.rounded {
  border-radius: 10px;
}

/* default button style */
.btn {
  position: relative;
  border: 0;
  padding: 15px 25px;
  display: inline-block;
  text-align: center;
  color: black;
}

/* color classes for .btn */
.btn.blue {
  background-color: #84adff;
  box-shadow: 0px 4px #6e9cf8;
}
.btn.green {
  background-color: #84ff9f;
  box-shadow: 0px 4px 0px #6ffc8e;
}
.btn.gray {
  background-color: #e1e1e1;
  box-shadow: 0px 4px 0px #bcbcbc;
}

.disability-person {
  display: flex;
  flex-direction: column;
  position: absolute;
  /* height: 86px; */
  border-radius: 10px;
  border: azure;
  background: #84adff;
  width: 77px;
  box-shadow: 0px 3px;
  text-align: center;
  font-size: 20px;
}

.blue {
  background-color: #84adff;
  box-shadow: 0px 4px #6e9cf8;
}
.green {
  background-color: #84ff9f;
  box-shadow: 0px 4px 0px #6ffc8e;
}
.gray {
  background-color: #e1e1e1;
  box-shadow: 0px 4px 0px #bcbcbc;
}
</style>
