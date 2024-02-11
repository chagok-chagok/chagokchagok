import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { adminConfirm, tokenRegeneration, logout, changePW } from "@/api/admin";
import { httpStatusCode } from "@/utils/http-status";

export const useAdminStore = defineStore("adminStore", () => {
  const router = useRouter();

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const adminInfo = ref(null);
  const isValidToken = ref(false);

  const adminLogin = async (loginAdmin) => {
    await adminConfirm(
      loginAdmin,
      (response) => {
        // console.log(response.data);
        // console.log(response.data.status);
        // console.log(httpStatusCode.CREATED);
        if (response.data.status === httpStatusCode.CREATED) {
          let { data } = response;
          console.log("data", data);
          let accessToken = data["accessToken"];
          let refreshToken = data["refreshToken"];
          getAdminInfo(accessToken);
          // console.log("accessToken", accessToken);
          // console.log("refreshToken", refreshToken);
          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;
          sessionStorage.setItem("accessToken", accessToken);
          sessionStorage.setItem("refreshToken", refreshToken);
          // adminInfo.value = data["id"];
          // console.log("sessiontStorage에 담았다", isLogin.value);
        } else {
          console.log("로그인 실패했다");
          isLogin.value = false;
          isLoginError.value = true;
          isValidToken.value = false;
        }
      },
      (error) => {
        console.error(error);
      }
    );
  };

  const getAdminInfo = (token) => {
    let decodeToken = jwtDecode(token);
    console.log("2. decodeToken", decodeToken.id);
    sessionStorage.setItem("id", decodeToken.id);
  };

  const tokenRegenerate = async () => {
    console.log(
      "토큰 재발급 >> 기존 토큰 정보 : {}",
      sessionStorage.getItem("accessToken")
    );
    await tokenRegeneration(
      JSON.stringify(userInfo.value),
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let accessToken = response.data["accessToken"];
          console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
          sessionStorage.setItem("accessToken", accessToken);
          isValidToken.value = true;
        }
      },
      async (error) => {
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          console.log("갱신 실패");
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await logout(
            userInfo.value.userid,
            (response) => {
              if (response.status === httpStatusCode.OK) {
                console.log("리프레시 토큰 제거 성공");
              } else {
                console.log("리프레시 토큰 제거 실패");
              }
              alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
              isLogin.value = false;
              userInfo.value = null;
              isValidToken.value = false;
              router.push({ name: "admin-login" });
            },
            (error) => {
              console.error(error);
              isLogin.value = false;
              userInfo.value = null;
            }
          );
        }
      }
    );
  };

  const changePassword = async (newPass) => {
    await changePW(
      {
        newPassword: newPass,
      },
      () => {
        console.log("변경성공");
        return true;
      },
      (error) => {
        console.log(error);
        console.log("실패ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ");
        return false;
      }
    );
  };

  const adminLogout = async () => {
    await logout(
      (response) => {
        if (response.status === httpStatusCode.OK) {
          sessionStorage.removeItem("accessToken");
          sessionStorage.removeItem("refreshToken");
          sessionStorage.removeItem("id");
          isLogin.value = false;
          isValidToken.value = false;
        } else {
          console.error("유저 정보 없음!!!!");
        }
        console.log("성공인듯?????????????????????");
      },
      (error) => {
        console.log(error);
        console.log("실패ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ");
      }
    );
  };

  return {
    isLogin,
    isLoginError,
    adminInfo,
    isValidToken,
    getAdminInfo,
    adminLogin,
    tokenRegenerate,
    adminLogout,
    changePassword,
  };
});
