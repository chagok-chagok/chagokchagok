import "./assets/main.css";
import "./assets/font.css"; // 전역 스타일 파일을 가져옴

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount("#app");
