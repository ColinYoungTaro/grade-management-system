import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/home.vue'
import About from '../views/about.vue'
import AdminUser from '../views/admin/admin-user.vue'
import AdminStudent from '../views/admin/admin-student.vue'
import AdminTeacher from '../views/admin/admin-teacher.vue'
import AdminManager from '../views/admin/admin-manager.vue'

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
    path: '/admin/user',
    name: 'AdminUser',
    component: AdminUser
  },
  {
    path: '/admin/student',
    name: 'AdminStudent',
    component: AdminStudent
  },
  {
    path: '/admin/teacher',
    name: 'AdminTeacher',
    component: AdminTeacher
  },
  {
    path: '/admin/manager',
    name: 'AdminManager',
    component: AdminManager
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
