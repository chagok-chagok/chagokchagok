<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useAdminStore } from "@/stores/admin";

const adminStore = useAdminStore();

const { isLogin } = storeToRefs(adminStore);
const { adminLogin, getUserInfo } = adminStore;

const loginAdmin = ref({
  id: "",
  pass: "",
});

const login = async () => {
  await adminLogin(loginAdmin.value);
  let token = sessionStorage.getItem("accessToken");
  if (isLogin) {
    getUserInfo(token);
  }
  router.push("/admin/dashboard");
};
</script>

<template>
  <mark class="orange">로그인</mark>
  <form>
    <div>
      <input type="checkbox" />
      <label for="saveid"> 아이디저장 </label>
    </div>
    <div>
      <label for="userid">아이디 : </label>
      <input type="text" v-model="loginAdmin.id" placeholder="아이디..." />
    </div>
    <div>
      <label for="userpwd">비밀번호 : </label>
      <input
        type="password"
        v-model="loginAdmin.pass"
        @keyup.enter="login"
        placeholder="비밀번호..."
      />
    </div>
    <div class="col-auto text-center">
      <button type="button" @click="login">로그인</button>
      <button type="button">회원가입</button>
    </div>
  </form>
</template>

<style scoped></style>
