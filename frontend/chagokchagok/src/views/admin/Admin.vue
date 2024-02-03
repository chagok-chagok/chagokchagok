<script setup>
import { RouterLink, RouterView } from "vue-router";
import navbarVue from "@/components/admin/navbar.vue";
</script>

<template>
  <main>
    <div class="container">
      <section class="on"><navbarVue /></section>

      <section class="under">
        <div class="content">
          <RouterView />
        </div>
        <div class="common-bar">공통바</div>
      </section>
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

/* 흰색 박스 */
.container {
  width: 1050px;
  height: 650px;
  background-color: #ffffff;
  border-radius: 20px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);

  overflow: hidden;
  /* padding: 20px; */
}

/* 윗쪽 섹션 : 네브바 */
.on {
  border-bottom: 2px solid rgb(237, 239, 243);
}
/* 아랫쪽 섹션 : 내용블록 + 공통바 */
.under {
  padding: 0px 20px;
  display: flex;
  /* background-color: antiquewhite; */
  width: 100%;
  height: 90%;
}

/* 탭마다 다른 컨텐츠가 들어가는 블록 */
.content {
  /* background-color: antiquewhite; */
  width: 79%;
  height: 100%;
}

/* 공통바 */
.common-bar {
  width: 21%;
  height: 100%;
}
</style>
