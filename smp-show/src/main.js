import Vue from 'vue'
import App from './App.vue'
// 注册路由并通过router.beforeEach控制路由
import router from './router'
import store from './store'
import vuetify from './common/plugins/vuetify'
import api from './utils/http'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.min.css'
import Cookies from 'js-cookie'

import httpApi from './utils/http/api'
import { getIFramePath, getIFrameUrl } from '@/utils/iframe'

// 跨域解决1）引入axios(封装版)
import axios from './utils/http/axios.js'

import MockService from "./common/mock/mock.service";
import ApiService from "./common/api.service";
import { VERIFY_AUTH } from "./store/auth.module";

// Global 3rd party plugins
import "bootstrap";
import "popper.js";
import "tooltip.js";
import "perfect-scrollbar";

// Vue 3rd party plugins
import i18n from "./common/plugins/vue-i18n";
import "./common/plugins/bootstrap-vue";
import "./common/plugins/perfect-scrollbar";
import "@babel/polyfill";
import "@mdi/font/css/materialdesignicons.css";

import 'perfect-scrollbar/css/perfect-scrollbar.css';

import JwtService from "@/common/jwt.service";

// import 'default-passive-events'

import menuConfig from "@/common/config/menu.config.json";

// Remove this to disable mock API
MockService.init();
ApiService.init();

Vue.use(ElementUI) // 引入ElementUI
Vue.use(api) // 引入API模块

Vue.config.productionTip = false

// 跨域解决2）把axios挂载到Vue原型中，在Vue中每个组件都可以使用axios发送请求，不需每次都import一下axios了，直接使用$axios即可
Vue.prototype.$axios = axios
// 跨域解决3）使每次请求都会带一个/api前缀(已在封装类中设置)
// axios.defaults.baseURL = '/api'
// console.log("current base api: " + process.env.VUE_APP_BASE_API)


// no redirect whitelist
const whiteList = ['/login', '/register',
  '/error/400', '/error/401', '/error/403', '/error/404', '/error/405',
  '/error/500', '/error/503']

/**
 * Ensure we checked auth before each page load.
 *
 * 路由拦截器-全局前置守卫(路由和请求是两码事儿哦)
 * 使用router.beforeEach注册一个全局前置守卫(全局钩子函数)
 * 当以导航触发时，全局前置守卫按照创建顺序调用。
 * 守卫是异步解析执行，此时导航在所有守卫resolve完之前一直处于等待中。
 * 每个守卫方法接收三个参数：
 *    to: Route:即将要进入的目标路由对象
 *    from: Route:当前导航正要离开的路由
 *    next: Function:一定要调用该方法来resolve这个钩子。执行效果依赖next方法的调用参数
 *
 * 在router/index.js中定义完路由后，我们主要是利用vue-router提供的钩子函数beforeEach()对路由进行判断
 *
 * 详情参见https://router.vuejs.org/zh/guide/advanced/navigation-guards.html
 */
router.beforeEach((to, from, next) => {
  //Promise.all([store.dispatch(VERIFY_AUTH)]).then(next);
  Promise.all([store.dispatch(VERIFY_AUTH)]).then( () => {
    // 登录界面登录成功之后，会把用户信息保存在会话
    // 存在时间为会话生命周期，页面关闭即失效
    //let userName = sessionStorage.getItem('user')
    let userName = Cookies.get('username')

    // determine whether the user has logged in
    const hasToken = JwtService.getToken()

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else if (to.path === '/login') {
      // 如果是访问登录界面，如果用户会话信息存在，代表已登录过，跳转到主页
      if(hasToken && userName) {
        // if is logged in, redirect to the home page
        next({ path: '/' })
      } else {
        next()
      }
    } else {
      if (!hasToken || !userName) {
        // other pages that do not have permission to access are redirected to the login page.
        // 如果访问非登录界面，且用户会话信息不存在，代表未登录，则跳转到登录界面
        if (!hasToken) {
          console.log("检测到token不存在，请登录...");
        }
        if (!userName) {
          console.log("检测到username不存在，请登录...");
        }
        next({ path: '/login' })
        /*next(`/login?redirect=${to.path}`)*/
      } else {
        // 加载动态菜单
        //addDynamicMenu(userName, to, from);

        // 加载动态菜单和路由
        addDynamicMenuAndRoutes(userName, to, from)

        next() // 确保一定要调用next(),否则钩子就不会被resolved
      }
    }
  });

  // Scroll page to top on every route change
  setTimeout(() => {
    window.scrollTo(0, 0);
  }, 100);

});

/*router.beforeEach((to, from, next) => {
  //Promise.all([store.dispatch(VERIFY_AUTH)]).then(next);
  Promise.all([store.dispatch(VERIFY_AUTH)]).then( () => {
    // 登录界面登录成功之后，会把用户信息保存在会话
    // 存在时间为会话生命周期，页面关闭即失效
    //let userName = sessionStorage.getItem('user')
    let userName = Cookies.get('username')

    // determine whether the user has logged in
    const hasToken = JwtService.getToken()

    debugger
    if (hasToken) {
      if (to.path === '/login') {
        // if is logged in, redirect to the home page
        next({ path: '/' })
      } else {
        if (userName)  {
          // 加载动态菜单
          addDynamicMenu(userName, to, from);

          // 加载动态菜单和路由
          //addDynamicMenuAndRoutes(userName, to, from)
        }
        next() // 确保一定要调用next(),否则钩子就不会被resolved
      }
    } else {
      /!* has no token*!/

      if (whiteList.indexOf(to.path) !== -1) {
        // in the free login whitelist, go directly
        next()
      } else {
        // other pages that do not have permission to access are redirected to the login page.
        // 如果访问非登录界面，且用户会话信息不存在，代表未登录，则跳转到登录界面
        next(`/login?redirect=${to.path}`)
      }
    }
    //console.log(store.state.user.perms)
  });

  // Scroll page to top on every route change
  setTimeout(() => {
    window.scrollTo(0, 0);
  }, 100);
});*/

/**
 * 加载动态菜单
 */
function addDynamicMenu(userName, to, from) {
  httpApi.user.findPermissions({'userName':userName}).then(res => {
    // 保存用户权限标识集合(加载权限标识-在导航守卫路由时加载权限标识并保存状态)
    store.commit('setPerms', res.data.data)
  }).catch(function(res) {})
}

/**
 * 加载动态菜单和路由
 */
function addDynamicMenuAndRoutes(userName, to, from) {
  // 处理IFrame嵌套页面
  handleIFrameUrl(to.path)
  if(store.state.app.menuRouteLoaded) {
    console.log('动态菜单和路由已经存在.')
    return
  }
  httpApi.menu.findNavTree({'userName':userName})
    .then(res => {
      // 添加动态路由
      console.log('后台返回的导航菜单信息')
      console.log(res.data.data)
      let dynamicRoutes = addDynamicRoutes(res.data.data)
      // 处理静态组件绑定路由
      router.options.routes[0].children = router.options.routes[0].children.concat(dynamicRoutes)
      router.addRoutes(router.options.routes)
      // 保存加载状态
      store.commit('menuRouteLoaded', true)
      // 保存菜单树
      store.commit('setNavTree', res.data.data)

      // 辅助判断菜单是否有权限是否显示
      store.commit('setNavRoutePaths', handleRoutesList(dynamicRoutes))
      store.commit('setNavRoutes', dynamicRoutes)
      store.commit('setMenuConfigAsideItems', menuConfig.aside.items)
      store.commit('setMenuConfigHeaderItems', menuConfig.header.items)
    }).then(res => {
    httpApi.user.findPermissions({'userName':userName}).then(res => {
      // 保存用户权限标识集合(加载权限标识-在导航守卫路由时加载权限标识并保存状态)
      store.commit('setPerms', res.data.data)
    })
  })
  .catch(function(res) {
  })
}

function handleRoutesList(dynamicRoutes) {
  let routesList = [];
  if (dynamicRoutes.length > 0) {
    for (let i = 0; i < dynamicRoutes.length; i++) {
      routesList.push(dynamicRoutes[i].path);
    }
  }
  return routesList;
}


/**
 * 处理IFrame嵌套页面
 */
function handleIFrameUrl(path) {
  // 嵌套页面，保存iframeUrl到store，供IFrame组件读取展示
  let url = path
  let length = store.state.iframe.iframeUrls.length
  for(let i=0; i<length; i++) {
    let iframe = store.state.iframe.iframeUrls[i]
    if(path != null && path.endsWith(iframe.path)) {
      url = iframe.url
      store.commit('setIFrameUrl', url)
      break
    }
  }
}

/**
 * 添加动态(菜单)路由
 * @param {*} menuList 菜单列表
 * @param {*} routes 递归创建的动态(菜单)路由
 */
function addDynamicRoutes (menuList = [], routes = []) {
  let temp = []
  for (let i = 0; i < menuList.length; i++) {
    if (menuList[i].children && menuList[i].children.length >= 1) {
      temp = temp.concat(menuList[i].children)
    } else if (menuList[i].url && /\S/.test(menuList[i].url)) {
      menuList[i].url = menuList[i].url.replace(/^\//, '')
      // 创建路由配置
      const route = {
        path: menuList[i].url,
        component: null,
        name: menuList[i].name,
        meta: {
          icon: menuList[i].icon,
          index: menuList[i].id,
          external: menuList[i].external, // 外部网页标识
        }
      }
      // 此处的url为后台库里存储的url(若是外部网页则http[s]开头)
      let path = getIFramePath(menuList[i].url)
      if (path) {
        // 如果是嵌套页面, 通过iframe展示
        route['path'] = path
        route['component'] = resolve => require([`@/views/pages/iframe/IFrame`], resolve)
        // 存储嵌套页面路由路径和访问URL
        let url = getIFrameUrl(menuList[i].url)
        let iFrameUrl = {'path': path, 'url': url}
        store.commit('addIFrameUrl', iFrameUrl)
      } else {
        try {
          // 根据菜单URL动态加载vue组件，这里要求vue组件须按照url路径存储
          // 如url="sys/user"，则组件路径应是"@/views/sys/user.vue",否则组件加载不到
          let array = menuList[i].url.split('/')
          let url = ''
          for(let i=0; i<array.length; i++) {
            if ( i===(array.length-1) ){
              url += array[i].substring(0,1).toUpperCase() + array[i].substring(1) + '/'
            } else {
              url += array[i].substring(0,1) + array[i].substring(1) + '/'
            }
          }
          url = url.substring(0, url.length - 1)
          route['component'] = resolve => require([`@/views/pages/${url}`], resolve)
        } catch (e) {}
      }
      routes.push(route)
    }
  }
  if (temp.length >= 1) {
    addDynamicRoutes(temp, routes)
  } else {
    console.log('动态路由加载...')
    console.log(routes)
    console.log('动态路由加载完成.')
  }
  return routes
}

/*
 * 步骤上接/router/index.js中的1.2.3.
 * 4. 创建和挂载根实例
 *    记得要通过router配置参数注入路由，从而让整个应用都有路由功能。
 */
new Vue({
  router,
  store,
  vuetify,
  i18n,
  render: h => h(App)
}).$mount('#app')
// 现在，应用已经启动了！
