import { createRouter, createWebHistory } from "vue-router";
import FirstScreen from "../components/kiosk/FirstScreen.vue";
import SecondScreen from "../components/kiosk/SecondScreen.vue";
import ThirdScreen from "../components/kiosk/ThirdScreen.vue";
import FourthScreen from "../components/kiosk/FourthScreen.vue";
import ParkingSection from "../views/admin/ParkingSection.vue";

const routes = [
  { path: "/", component: FirstScreen },
  { path: "/second", component: SecondScreen },
  { path: "/third", component: ThirdScreen },
  { path: "/fourth", component: FourthScreen },
  { path: "/section", component: ParkingSection },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
