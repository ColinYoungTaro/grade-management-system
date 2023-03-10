import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/home.vue'
import About from '../views/about.vue'
import AdminSchoolUser from '../views/admin/admin-schooluser.vue'
import AdminStudent from '../views/admin/admin-student.vue'
import AdminTeacher from '../views/admin/admin-teacher.vue'
import AdminManager from '../views/admin/admin-manager.vue'
import AdminCourse from '../views/admin/admin-course.vue'
import AdminCourseSchedule from '../views/admin/admin-courseschedule.vue'
import AdminClass from '../views/teacher/admin/admin-class.vue'
import ManagerAdminAppeals from '../views/admin/admin-appeals.vue'
import AnalyzeScore from '../views/student/score/analyze-score.vue'
import StudentQueryWholeScore from '../views/student/score/query-whole-score.vue'
import StudentScoreAppeal from '../views/student/score/apply-appeal-score.vue'
import StudentInformation from '../views/student/student-information.vue'
import SelectMajorCourse from '../views/student/course/select-major-course.vue'
import SelectGeneralCourse from '../views/student/course/select-general-course.vue'
import SelectALLCourse from '../views/student/course/select-all-course.vue'
import TeacherInformation from '../views/teacher/teacher-information.vue'
import TeacherAdminApply from '../views/teacher/admin/admin-apply.vue'

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
    path: '/admin/course',
    name: 'AdminCourse',
    component: AdminCourse,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/admin/course_schedule',
    name: 'AdminCourseSchedule',
    component: AdminCourseSchedule,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/admin/class',
    name: 'AdminClass',
    component: AdminClass,
    meta: {
      loginRequire: true
    }
  },{
    path: '/admin/appeals',
    name: 'ManagerAdminAppeals',
    component: ManagerAdminAppeals,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/student/analysis/score',
    name: 'AnalyzeScore',
    component: AnalyzeScore,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/student/information',
    name: 'StudentInformation',
    component: StudentInformation,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/select/all/course',
    name: 'SelectALLCourse',
    component: SelectALLCourse,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/select/major/course',
    name: 'SelectMajorCourse',
    component: SelectMajorCourse,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/select/general/course',
    name: 'SelectGeneralCourse',
    component: SelectGeneralCourse,
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
  {
    path: '/student/score/appeal',
    name: 'StudentScoreAppeal',
    component: StudentScoreAppeal,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/teacher/information',
    name: 'TeacherInformation',
    component: TeacherInformation,
    meta: {
      loginRequire: true
    }
  },  {
    path: '/teacher/admin/apply',
    name: 'TeacherAdminApply',
    component: TeacherAdminApply,
    meta: {
      loginRequire: true
    }
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

//??????????????????
router.beforeEach((to, from, next) => {
  // ????????????meta.loginRequire?????????????????????
  if (to.matched.some(function (item) {
    console.log(item, "???????????????????????????", item.meta.loginRequire);
    return item.meta.loginRequire
  })) {
    const loginUser = store.state.user;
    if (Tool.isEmpty(loginUser)) {
      console.log("??????????????????");
      next('/');
    } else {
      next();
    }
  } else {
    next();
  }
});


export default router
