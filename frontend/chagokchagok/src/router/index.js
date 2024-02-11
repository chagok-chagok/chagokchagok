import { createRouter, createWebHistory } from "vue-router";
import DashBoardView from "../views/admin/DashBoard.vue";
import AdminView from "../views/Admin.vue";
import HomeView from "../views/HomeView.vue";
import ChoiceScreen from "@/views/kiosk/ChoiceScreen.vue";
import NoplaceScreen from "@/views/kiosk/NoplaceScreen.vue";
import RecognitionErrorScreen from "@/views/kiosk/RecognitionErrorScreen.vue";
import AllocationScreen from "@/views/kiosk/AllocationScreen.vue";
import ParkingSection from "../views/admin/ParkingSection.vue";
import Tooltip from "@/components/admin/Tooltip2.vue";

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
          path: "section",
          name: "parking-section",
          component: ParkingSection,
        },
        {
          path: "report",
          name: "report-list",
          component: () => import("@/components/admin/report/ReportList.vue"),
        },
      ],
    },

    {
      path: "/choice",
      name: "choice",
      component: ChoiceScreen,
    },
    {
      path: "/no-place",
      name: "no-place",
      component: NoplaceScreen,
    },
    {
      path: "/recognition-error",
      name: "recognition-error",
      component: RecognitionErrorScreen,
    },
    {
      path: "/allocation",
      name: "allocation",
      component: AllocationScreen,
    },

    {
      path: "/guest",
      name: "guest",
      component: () => import("@/views/guest/GuestView.vue"),
    },
  ],
});

export default router;
