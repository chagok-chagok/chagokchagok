import { createRouter, createWebHistory } from "vue-router";
import MainView from "../views/Main.vue";
import LoginView from "@/components/main/LoginPage.vue";
import ChangePasswordView from "@/components/main/ChangePassword.vue";
import DashBoardView from "../views/admin/DashBoard.vue";
import AdminView from "../views/Admin.vue";
import HomeView from "../views/HomeView.vue";
import ChoiceScreen from "@/views/kiosk/ChoiceScreen.vue";
import NoplaceScreen from "@/views/kiosk/NoplaceScreen.vue";
import RecognitionErrorScreen from "@/views/kiosk/RecognitionErrorScreen.vue";
import AllocationScreen from "@/views/kiosk/AllocationScreen.vue";
import RecommendationScreen from "@/views/kiosk/RecommendationScreen.vue";
import ParkingSection from "../views/admin/ParkingSection.vue";
import Tooltip from "@/components/admin/Tooltip2.vue";

const requireAuth = () => (from, to, next) => {
  const token = sessionStorage.getItem("accessToken");
  if (token) {
    return next();
  } // isLogin === true면 페이지 이동
  next("/main/login"); // isLogin === false면 다시 로그인 화면으로 이동
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: LoginView,
    },
    {
      path: "/admin",
      name: "admin",
      component: AdminView,
      beforeEnter: requireAuth(),
      children: [
        {
          path: "dashboard",
          name: "admin-dashboard",
          component: DashBoardView,
          beforeEnter: requireAuth(),
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
      path: "/main",
      name: "main",
      component: MainView,
      children: [
        {
          path: "login",
          name: "admin-login",
          component: LoginView,
        },
        {
          path: "changePassword",
          name: "changePassword",
          component: ChangePasswordView,
          beforeEnter: requireAuth(),
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
      path: "/recommendation",
      name: "recommendation",
      component: RecommendationScreen,
    },
    {
      path: "/guest",
      name: "guest",
      component: () => import("@/views/guest/GuestView.vue"),
    },
    {
      path: "/print",
      name: "print",
      component: () => import("@/views/kiosk/Print.vue"),
    },
  ],
});
export default router;
