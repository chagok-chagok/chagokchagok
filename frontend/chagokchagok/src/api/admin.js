import { instance } from "@/utils/mainAxios";

const local = instance;

async function adminConfirm(param, success, fail) {
  await local.post("/admin/login", param).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["authorization"] =
    sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/admin/refresh`, user).then(success).catch(fail);
}

async function logout(success, fail) {
  local.defaults.headers["Authorization"] =
    sessionStorage.getItem("accessToken"); //axios header에 refresh-token 셋팅1
  await local.get(`/admin/logout`).then(success).catch(fail);
}

async function changePW(newPassword, success, fail) {
  local.defaults.headers["Authorization"] =
    sessionStorage.getItem("accessToken");
  await local
    .post(`/admin/changePassword`, newPassword)
    .then(success)
    .catch(fail);
}

export { adminConfirm, tokenRegeneration, logout, changePW };
