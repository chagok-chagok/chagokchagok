import { createRouter, createWebHistory } from "vue-router";
import DashBoardView from "../views/admin/DashBoard.vue";
import AdminView from "../views/Admin.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "dashtest",
      component: DashBoardView,
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
      ],
    },
  ],
});

export default router;
