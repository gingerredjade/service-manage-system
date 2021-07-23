import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

// 引入子模块-业务相关
import app from './modules/app'
import tab from './modules/tab'
import iframe from './modules/iframe'
import user from './modules/user'
import menu from './modules/menu'

// 引入子模块-layout相关
import config from './config.module'
import htmlClass from './htmlclass.module'
import breadcrumbs from "./breadcrumbs.module";
import auth from "./auth.module";

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    app: app,
    tab: tab,
    iframe: iframe,
    user: user,
    menu: menu,
    config: config,
    htmlClass: htmlClass,
    breadcrumbs: breadcrumbs,
    auth: auth
  }
})
