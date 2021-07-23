export default {
  state: {
    navTree: [], // 导航菜单树
    navRoute: [],
    menuConfigAsideItems: [],
    menuConfigHeaderItems: [],
    navRoutePath: []
  },
  getters: {
    navRoute(state) { // 对应着上面state
      return state.navRoute
    }
  },
  mutations: {
    setNavTree(state, navTree){ // 设置导航菜单树
      state.navTree = navTree;
    },
    setNavRoutes(state, routes) { // 辅助判断菜单是否显示使用
      state.navRoute = routes;
    },
    setNavRoutePaths(state, paths) { // 辅助判断菜单是否显示使用
      state.navRoutePath = paths;
    },
    setMenuConfigAsideItems(state, items) { // 辅助判断侧边菜单是否显示使用
      state.menuConfigAsideItems = items;
    },
    setMenuConfigHeaderItems(state, items) { // 辅助判断顶部菜单是否显示使用
      state.menuConfigHeaderItems = items;
    }
  },
  actions: {

  }
}
