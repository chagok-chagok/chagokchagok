// import './assets/main.css'

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";
import VueSSE from "vue-sse";

// fontawesome import
// import { library } from "@fortawesome/fontawesome-svg-core";
// import { fas } from "@fortawesome/free-solid-svg-icons";
// import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
// import { loadFonts } from "./plugins/webfontloader";
// import "@/assets/font.css";
// mdi import
// import mdiVue from "mdi-vue/v3";
// import * as mdijs from "@mdi/js";

// loadFonts();

// library.add(fas);

const app = createApp(App);

// createApp을 통해 생성한Application 인스턴스의 component API 활용
// app.component("font-awesome-icon", FontAwesomeIcon);
app.use(createPinia());
app.use(router);
app.use(VueSSE);

app.mount("#app");
