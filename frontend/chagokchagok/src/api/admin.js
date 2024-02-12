import { localAxios } from "@/utils/mainAxios";

const local = localAxios();

async function adminConfirm(param, success, fail) {
  console.log("param", param);
  await local.post("/admin/login", param).then(success).catch(fail);
  console.log("userConfirm ok");
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["authorization"] =
    sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/admin/refresh`, user).then(success).catch(fail);
}

async function logout(success, fail) {
  console.log("=============테스트===============");
  console.log(sessionStorage.getItem("accessToken"));
  console.log("=============테스트===============");
  local.defaults.headers["Authorization"] =
    sessionStorage.getItem("accessToken"); //axios header에 refresh-token 셋팅1
  console.log(local.defaults.headers["Authorization"]);
  await local.get(`/admin/logout`).then(success).catch(fail);
  console.log("복귀완료!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
}

async function changePW(newPassword, success, fail) {
  console.log("newPassword", newPassword);
  local.defaults.headers["Authorization"] =
    sessionStorage.getItem("accessToken");
  console.log("비밀번호 바꾸러가자");
  console.log(newPassword);
  await local
    .post(`/admin/changePassword`, newPassword)
    .then(success)
    .catch(fail);
}

export { adminConfirm, tokenRegeneration, logout, changePW };
