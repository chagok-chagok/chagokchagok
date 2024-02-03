import { createRouter, createWebHistory } from "vue-router";
import DashBoardView from "../views/admin/DashBoard.vue";
import AdminView from "../views/Admin.vue";
import HomeView from "../views/HomeView.vue";

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
          component: DashBoardView
        },
        {
          path: "report",
          name: "report-list",
          component: () => import("@/components/admin/report/ReportList.vue"),
        },
      ],
    },
  ],
});

export default router;
