// router
import Vue from 'vue'
import VueRouter from 'vue-router'
import BaseLayout from '../views/theme/Base'


/*
 * 0. 如果使用模块化编程，要调用Vue.use(VueRouter)
 */
Vue.use(VueRouter)

/*
 * 1. 定义(路由)组件
 *    可以从其他文件import进来。
 * 2. 定义路由
 *    每个路由应该映射一个组件。其中‘component’可以是通过Vue.extend()创建的组件构造器，或者只是一个组件配置对象。
 *    嵌套路由?
 * @type {({path: string, component: {components: {HelloWorld: *}, name: string}, name: string}|{path: string, component: (function(): *), name: string})[]}
 */
const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard',
    component: BaseLayout,
    children: [
      {
        path: "dashboard",
        name: "dashboard",
        component: () => import("@/views/pages/Dashboard.vue")
      },
      {
        path: '/open',
        name: 'open',
        component: () => import('../views/pages/bus/open/OpenPage.vue'),
        children: [
          {
            path: 'interface',
            name: 'open-interface',
            component: () => import('../views/pages/bus/open/ApiInterface.vue')
          }
        ]
      },
      {
        path: '/test',
        name: 'test',
        component: () => import('../views/test/TestPage.vue'),
        children: [
          {
            path: 't1',
            name: 'test-t1',
            component: () => import('../views/test/HomeTest.vue')
          }
        ]
      },
      {
        path: '/sys',
        name: 'sys',
        component: () => import("@/views/pages/sys/SystemManage.vue"),
        children: [
          {
            path: 'user/profile',
            name: 'sys-user-profile',
            component: () => import('../views/pages/sys/UserProfile')
          }
        ]
      },
      {
        path: '/bus',
        name: 'bus',
        component: () => import('../views/pages/bus/svc/ServiceManage.vue'),
        children: [
          {
            path: 'svc/view-all',
            name: 'bus-svc-view-all',
            component: () => import('../views/pages/bus/svc/ViewAll.vue')
          }
        ]
      }
    ]
  },
  {
    path: '/foreign',
    name: 'foreign',
    component: () => import('../views/pages/bus/foreign/ForeignPage.vue'),
    children: [
      {
        path: 'catalog',
        name: 'foreign-catalog',
        component: () => import('../views/pages/bus/foreign/ServiceCatalog.vue')
      }
    ]
  },
  {
    name: "error",
    path: "/error",
    component: () => import("@/views/pages/error/Error"),
    children: [
      {
        name: "error-400",
        path: "400",
        component: () => import("../views/pages/error/Error-400.vue")
      },
      {
        name: "error-401",
        path: "401",
        component: () => import("../views/pages/error/Error-401.vue")
      },
      {
        name: "error-403",
        path: "403",
        component: () => import("../views/pages/error/Error-403.vue")
      },
      {
        name: "error-404",
        path: "404",
        component: () => import("../views/pages/error/Error-404.vue")
      },
      {
        name: "error-405",
        path: "405",
        component: () => import("../views/pages/error/Error-405.vue")
      },
      {
        name: "error-500",
        path: "500",
        component: () => import("../views/pages/error/Error-500.vue")
      },
      {
        name: "error-503",
        path: "503",
        component: () => import("../views/pages/error/Error-503.vue")
      }
    ]
  },
  {
    path: "/",
    component: () => import("@/views/pages/auth/Auth"),
    children: [
      {
        name: "login",
        path: "/login",
        component: () => import("@/views/pages/auth/Login")
      },
      {
        name: "register",
        path: "/register",
        component: () => import("@/views/pages/auth/Register")
      }
    ]
  }
]

/*
 * 3. 创建router实例，然后传‘routes’ 配置。还可以传别的参数，不过先这么简单吧
 * @type {VueRouter}
 */
const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes // 缩写，相当于routes: routes
})

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

export default router
