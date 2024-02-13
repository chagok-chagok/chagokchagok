import axios from "axios";
import router from "../router";
import { httpStatusCode } from "@/utils/http-status";

const { VITE_VUE_SPRING_URL } = import.meta.env;

// const instance = axios.create({
//   baseURL: VITE_VUE_SPRING_URL,
//   headers: {
//     "Content-Type": "application/json;charset=utf-8",
//   },
// });
/*

//비동기 요청 전 인터셉트
instance.interceptors.request.use(
  (config) => {
    // console.log("config : ", config);
    const authStore = useAuthStore();

    config.headers.Authorization = `Bearer ${authStore.token}`;
    // console.log("요청 intercept", config);
    return config;
  },
  (error) => Promise.reject(error)
);

//비동기 요청 응답 수신 시, 인터셉트
instance.interceptors.response.use(
  (response) => response,
  (error) => {
    // console.log("응답 에러 : ", error);
    //401에러 and 토큰 만료의 경우 ( 네트워크 응답 에러의 경우 response가 존재하지 않으므로 체크 필요 )
    if (
      error.response &&
      error.response.data.status === 401
      //  && error.response.data.message === "Token Expired"
    ) {
      //store의 유저정보 clear
      const authStore = useAuthStore();
      const menuStore = useMenuStore();

      authStore.clearUser();
      menuStore.changeMenuState();
      //로그인 페이지 이동 및 alert
      router.push({ name: "user-login" });
      alert("인증이 만료되었습니다. 다시 로그인 해주세요.");
    }

    return Promise.reject(error);
  }

  */
// );

// function localAxios() {
const instance = axios.create({
  baseURL: VITE_VUE_SPRING_URL,
  // withCredentials: true,
  // headers: {
  //   "Content-Type": "application/json;charset=utf-8",
  // },
});
// Request 발생 시 적용할 내용.
instance.defaults.headers.common["authorization"] = "";
// instance.defaults.headers.post["Content-Type"] = "application/json";
// instance.defaults.headers.put["Content-Type"] = "application/json";

// Request, Response 시 설정한 내용을 적용.
instance.interceptors.request.use(
  (config) => {
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// accessToken의 값이 유효하지 않은 경우,
// refreshToken을 이용해 재발급 처리.
// https://maruzzing.github.io/study/rnative/axios-interceptors%EB%A1%9C-%ED%86%A0%ED%81%B0-%EB%A6%AC%ED%94%84%EB%A0%88%EC%8B%9C-%ED%95%98%EA%B8%B0/

let isTokenRefreshing = false;

instance.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const {
      config,
      response: { status },
    } = error;

    // 페이지가 새로고침되어 저장된 accessToken이 없어진 경우.
    // 토큰 자체가 만료되어 더 이상 진행할 수 없는 경우.
    console.log("테스트 status : " + status);
    console.log(status == httpStatusCode.UNAUTHORIZED);
    console.log("isTokenRefreshing 값1" + isTokenRefreshing);
    if (status == httpStatusCode.UNAUTHORIZED) {
      // 요청 상태 저장
      const originalRequest = config;

      // Token을 재발급하는 동안 다른 요청이 발생하는 경우 대기.
      // 다른 요청을 진행하면, 새로 발급 받은 Token이 유효하지 않게 됨.
      console.log("isTokenRefreshing 값2" + isTokenRefreshing);
      if (!isTokenRefreshing) {
        isTokenRefreshing = true;
        console.log("에러 예상지역");
        instance.defaults.headers["Authorization"] =
          sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
        console.log(sessionStorage.getItem("refreshToken"));
        console.log("왜 안돼............");
        // 에러가 발생했던 컴포넌트의 axios로 이동하고자하는 경우
        // 반드시 return을 붙여주어야한다.
        return await instance.get("/admin/refresh").then((response) => {
          console.log(response);
          const newAccessToken = response.data.authorization;
          console.log(newAccessToken);
          console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!변경전");
          console.log(originalRequest);
          sessionStorage.setItem("accessToken", newAccessToken);
          originalRequest.headers.Authorization = newAccessToken;

          console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!변경후");
          console.log(originalRequest);

          isTokenRefreshing = false;

          // 에러가 발생했던 원래의 요청을 다시 진행.
          return instance(originalRequest);
        });
      } else {
        if (
          originalRequest.headers.Authorization !=
          sessionStorage.getItem("accessToken")
        ) {
          instance.defaults.headers["Authorization"] =
            sessionStorage.getItem("accessToken");
          return instance(originalRequest);
        }
      }
    } else if (status == httpStatusCode.FORBIDDEN) {
      alert(error.response.data.message);
    }

    return Promise.reject(error);
  }
);
//   return instance;
// }

// export { instance, localAxios };
export { instance };
