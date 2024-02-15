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
        if (response.data.status === httpStatusCode.CREATED) {
          let { data } = response;
          let accessToken = data["accessToken"];
          let refreshToken = data["refreshToken"];
          getAdminInfo(accessToken);
          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;
          sessionStorage.setItem("accessToken", accessToken);
          sessionStorage.setItem("refreshToken", refreshToken);
        } else {
          isLogin.value = false;
          isLoginError.value = true;
          isValidToken.value = false;
        }
      },
      (error) => {}
    );
  };

  const getAdminInfo = (token) => {
    let decodeToken = jwtDecode(token);
    sessionStorage.setItem("id", decodeToken.id);
  };

  const tokenRegenerate = async () => {
    await tokenRegeneration(
      JSON.stringify(userInfo.value),
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let accessToken = response.data["accessToken"];
          sessionStorage.setItem("accessToken", accessToken);
          isValidToken.value = true;
        }
      },
      async (error) => {
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await logout(
            userInfo.value.userid,
            (response) => {
              alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
              isLogin.value = false;
              userInfo.value = null;
              isValidToken.value = false;
              router.push({ name: "admin-login" });
            },
            (error) => {
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
        return true;
      },
      (error) => {
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
        }
      },
      (error) => {}
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
