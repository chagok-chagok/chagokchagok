import { createRouter, createWebHistory } from "vue-router";
import DashBoardView from "../views/admin/DashBoard.vue";
import AdminView from "../views/Admin.vue";
import HomeView from "../views/HomeView.vue";
import FirstScreen from "../components/kiosk/FirstScreen.vue";
import SecondScreen from "../components/kiosk/SecondScreen.vue";
import ThirdScreen from "../components/kiosk/ThirdScreen.vue";
import FourthScreen from "../components/kiosk/FourthScreen.vue";
import ParkingSection from "../views/admin/ParkingSection.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/admin",
      name: "admin",
      component: AdminView,
      children: [
        {
          path: "dashboard",
          name: "admin-dashboard",
          component: DashBoardView,
        },
        {
          path: "report",
          name: "report-list",
          component: () => import("@/components/admin/report/ReportList.vue"),
        },
      ],
    },
    { path: "/", component: FirstScreen },
    { path: "/second", component: SecondScreen },
    { path: "/third", component: ThirdScreen },
    { path: "/fourth", component: FourthScreen },
    { path: "/section", component: ParkingSection },
  ],
});

export default router;
