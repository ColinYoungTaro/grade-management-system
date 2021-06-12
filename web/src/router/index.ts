import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/home.vue'
import About from '../views/about.vue'
import AdminSchoolUser from '../views/admin/admin-schooluser.vue'
import AdminStudent from '../views/admin/admin-student.vue'
import AdminTeacher from '../views/admin/admin-teacher.vue'
import AdminManager from '../views/admin/admin-manager.vue'
import QueryStudentScore from '../views/student/score/query-score.vue'
import StudentQueryWholeScore from '../views/student/score/query-whole-score.vue'
import store from "@/store";
import {Tool} from "@/util/tool";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/admin/schooluser',
    name: 'AdminSchoolUser',
    component: AdminSchoolUser,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/admin/student',
    name: 'AdminStudent',
    component: AdminStudent,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/admin/teacher',
    name: 'AdminTeacher',
    component: AdminTeacher,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/admin/manager',
    name: 'AdminManager',
    component: AdminManager,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/query/student/score',
    name: 'QueryStudentScore',
    component: QueryStudentScore,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/student/query/whole/score',
    name: 'StudentQueryWholeScore',
    component: StudentQueryWholeScore,
    meta: {
      loginRequire: true
    }
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

//路由登录拦截
router.beforeEach((to, from, next) => {
  // 要不要对meta.loginRequire属性做监控拦截
  if (to.matched.some(function (item) {
    console.log(item, "是否需要登录校验：", item.meta.loginRequire);
    return item.meta.loginRequire
  })) {
    const loginUser = store.state.user;
    if (Tool.isEmpty(loginUser)) {
      console.log("用户未登录！");
      next('/');
    } else {
      next();
    }
  } else {
    next();
  }
});


export default router
