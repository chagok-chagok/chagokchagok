import { createRouter, createWebHistory } from "vue-router";
import FirstScreen from "../components/FirstScreen.vue";
import SecondScreen from "../components/SecondScreen.vue";
import ThirdScreen from "../components/ThirdScreen.vue";
import FourthScreen from "../components/FourthScreen.vue";

const routes = [
  { path: "/", component: FirstScreen },
  { path: "/second", component: SecondScreen },
  { path: "/third", component: ThirdScreen },
  { path: "/fourth", component: FourthScreen },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
