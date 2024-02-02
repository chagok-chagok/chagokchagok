import "./assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";
import VueSSE from "vue-sse";

const app = createApp(App);

app.use(createPinia());
app.use(router);

// VueSSE를 Vue 3 방식으로 사용
app.use(VueSSE);

app.mount("#app");
