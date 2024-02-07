<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useAdminStore } from "@/stores/admin";
import IconBlue from "@/components/icons/IconMainBlue.vue";
import IconWhite from "@/components/icons/IconMainWhtite.vue";

const router = useRouter();
const adminStore = useAdminStore();

const { isLogin } = storeToRefs(adminStore);
const { adminLogin, getUserInfo } = adminStore;

const loginAdmin = ref({
  id: "",
  pass: "",
});

const login = async () => {
  console.log("로그인 시작");
  await adminLogin(loginAdmin.value);
  let token = sessionStorage.getItem("accessToken");
  console.log(token);
  if (token === null) {
    window.alert("아이디 또는 비밀번호가 잘못되었습니다.");
    router.replace("/main/login");
  }
  router.push("/admin/dashboard");
};
</script>

<template>
  <main>
    <div class="container">
      <div id="content">
        <div id="leftContent">
          <div id="background">
            <IconWhite />
          </div>
          <div id="inner-container">
            <div id="contentCenter">
              <span class="service-title">
                <iconBlue />
                <b>차곡차곡 관리 시스템</b>
              </span>
              <div id="test">
                <h1 class="login-title">Sign In</h1>
                <article id="mainContent" class="content-article">
                  <div class="cont_login">
                    <div class="login-main">
                      <form class="login-form">
                        <div class="form-group">
                          <label class="bold" for="id">ID</label>
                          <input
                            type="text"
                            v-model="loginAdmin.id"
                            id="id"
                            class="input"
                            required
                          />
                        </div>
                        <div class="form-group">
                          <label class="bold" for="password">Password</label>
                          <input
                            type="password"
                            v-model="loginAdmin.pass"
                            @keyup.enter="login"
                            id="password"
                            class="input"
                            required
                          />
                          <div class="form-options">
                            <div class="remember-me">
                              <input type="checkbox" id="remember-me" />
                              <label for="remember-me">Remember me?</label>
                            </div>
                          </div>
                        </div>

                        <div id="login-button">
                          <button
                            type="button"
                            class="sign-in-button"
                            @click="login"
                          >
                            Sign in
                          </button>
                        </div>
                      </form>
                    </div>
                  </div>
                </article>
              </div>
            </div>
          </div>
        </div>

        <div id="rightContent">
          <img
            class="rightImage"
            src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_vLFxgT5ryI_kbEtYj_7rtdVFlDknDC4Qgw-BNf1aI-kjrlJx7sC8GsB1FAMSusdJKCo&usqp=CAU"
          />
        </div>
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

/* 흰색 박스 */
.container {
  width: 71vw;
  height: 90vh;
  background-color: #ffffff;
  border-radius: 20px;
  /* box-shadow: 0 0 20px rgba(0, 0, 0, 0.2); */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
  overflow: hidden;
  /* padding: 20px; */
  position: relative;
  display: flex;
  align-items: center;
}
/*성욱 */

.login-title {
  font-size: 50px;
}

.content-article {
  flex: 1 1 auto;
  width: 100%;
  min-height: auto;
  margin: 0;
  padding: 0 16px;
  border: 0;
  font-size: 10px;
  box-sizing: border-box;
}
.content-article .cont_login {
  word-wrap: break-word;
}
#test {
  width: 100%;
}
.login-form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.form-group {
  width: 60%;
  margin-bottom: 20px;
}
.form-group > input {
  width: 100%;
}

.form-options {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  margin-top: 10px;
}

.remember-me input {
  margin-right: 0.5rem;
}

.forgot-password {
  color: #007bff;
  text-decoration: none;
}

.forgot-password:hover {
  text-decoration: underline;
}

#content {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
}

#leftContent {
  width: 50%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

#inner-container {
  display: flex;
  flex-direction: column;
  z-index: 20;
  position: absolute;
  width: 50%;
  height: auto;
}

#rightContent {
  width: 50%;
  height: 100%;
  display: flex;
  align-items: center;
}
.rightImage {
  position: absolute;
  width: 100%;
  height: auto;
}

#login-button {
  display: flex;
  justify-content: center;
  width: 30%;
}

.sign-in-button {
  cursor: pointer;
  width: 200px;
  height: 40px;
  display: block;
  width: 100%;
  padding: 13px 0 13px;
  margin: 10px;
  border-radius: 6px;
  border: solid 1px rgba(0, 0, 0, 0.15);
  background-color: #3a57e8;
  color: #ffffff;
  box-sizing: border-box;
}
#background {
  display: flex;
  justify-content: left;
  width: 100%;
  height: 100%;
  z-index: 10;
  align-items: flex-start;
  position: relative;
  top: 0; /* 페이지 상단에 위치 */
  left: 0;
}
.sign-in-button:hover {
  background-color: #007bff;
}
.bold {
  font-weight: bold;
  font-size: 20px;
}
.form-group > input {
  margin-top: 10px; /* 라벨과 입력란 사이 간격 조절 */
}
h1 {
  text-align: center;
  margin-bottom: 30px;
}
#contentCenter {
  display: flex;
  align-items: center;
}
#test {
  display: flex;
  flex-direction: column;
}

#contentCenter {
  display: flex;
  flex-direction: column;
}

.input {
  height: 25px;
  border: 1px solid #3a57e8;
}
</style>
