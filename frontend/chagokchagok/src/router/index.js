import { createRouter, createWebHistory } from "vue-router";
import DashBoardView from "../views/admin/DashBoard.vue";
import AdminView from "../views/Admin.vue";
import HomeView from "../views/HomeView.vue";
import FirstScreen from "../components/kiosk/FirstScreen.vue";
import SecondScreen from "../components/kiosk/SecondScreen.vue";
import ThirdScreen from "../components/kiosk/ThirdScreen.vue";
import FourthScreen from "../components/kiosk/FourthScreen.vue";
import ParkingSection from "../views/admin/ParkingSection.vue";
import Search from "../components/admin/Search.vue";
import Tooltip from "../components/admin/Tooltip.vue";

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
    { path: "/first", name: "fisrt", component: FirstScreen },
    { path: "/second", name: "second", component: SecondScreen },
    { path: "/third", name: "third", component: ThirdScreen },
    { path: "/fourth", name: "fourth", component: FourthScreen },
    { path: "/section", name: "section", component: ParkingSection },
    {
      path: "/guest",
      name: "guest",
      component: () => import("@/views/guest/GuestView.vue"),
    },
  ],
});

export default router;
