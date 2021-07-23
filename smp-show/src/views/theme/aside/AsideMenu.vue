<template>
  <ul class="kt-menu__nav">
    <template v-for="(menu, i) in menuItems">
      <KTMenuItem v-if="!menu.section" :menu="menu" :key="i"></KTMenuItem>
      <KTMenuSection v-if="menu.section" :menu="menu" :key="i"></KTMenuSection>
    </template>
  </ul>
</template>

<script>
import { mapState } from 'vuex';
import KTMenuItem from "@/views/theme/aside/MenuItem.vue";
import KTMenuSection from "@/views/theme/aside/MenuSection.vue";
import menuConfig from "@/common/config/menu.config.json";

export default {
  name: "KTAsideMenu",
  components: {
    KTMenuItem,
    KTMenuSection
  },
  computed: {
    ...mapState({
      navTree: state=>state.menu.navTree,
      // navRoute: state=>state.menu.navRoute,
      navRoute: function (state) {
        let resData = JSON.stringify(state.menu);
        let resDataObj = JSON.parse(resData)
        return resDataObj.navRoute;
      },
      menuItems: function (state) {
        /** 最初写法，直接读取固定配置，未与后台权限动态关联 */
        // return menuConfig.aside.items;

        /** 改写该方法，适配菜单权限（不支持的菜单不予显示） **/
        // JSON.parse(JSON.stringify(obj))可以得到深拷贝的原始数据对象.
        let resData = JSON.stringify(state.menu);
        let resDataObj = JSON.parse(resData)
        console.log('拥有的权限菜单')
        console.log(resDataObj.navRoutePath)

        //let menuConfigItems = menuConfig.aside.items; //不能动态变化，使用vuex方式替换
        let menuConfigItems = resDataObj.menuConfigAsideItems;
        console.log('[侧边菜单]配置菜单中的内容')
        console.log(menuConfigItems)

        let menuItemsToShow = [];
        for (let i=0; i<menuConfigItems.length; i++) {
          let menu = menuConfigItems[i];
          if (menu.page === 'dashboard') {
            menuItemsToShow.push(menu);
          }
          if (menu.section) {
            menuItemsToShow.push(menu);
          }
          if (menu.submenu) {
            let submenuCut = [];
            this.buildSubmenu(menu, resDataObj.navRoutePath, submenuCut, resDataObj.navRoute);
            menu.submenu = submenuCut;
            menuItemsToShow.push(menu);
          }
        }
        console.log("[侧边菜单]将要显示的item")
        console.log(menuItemsToShow)
        return menuItemsToShow;
      }
    }),
  },
  methods: {
    buildSubmenu: function (menu, ownRoutePaths = [], submenuItemsToShow = [], ownRoutes = []) {
      let submenu = menu.submenu;
      for (let i=0; i<submenu.length; i++) {
        let curSubmenu = submenu[i];
        let curExternalFlag = submenu[i].external;
        let curSubmenuPage = submenu[i].page;
        if (curSubmenuPage) {
          if (ownRoutePaths.indexOf(curSubmenuPage) > -1) {
            // 后台返回的权限菜单里含此菜单，予展示
            submenuItemsToShow.push(curSubmenu);
          }
        } else if (curExternalFlag) {
          // 根据嵌套外部标记查询库里对应的外部url并赋予至菜单的page属性
          let eUrl = this.searchExternalUrl(curExternalFlag, ownRoutes);
          if (eUrl) {
            submenu[i].page = eUrl;
            submenuItemsToShow.push(submenu[i]);
          }
        } else {
          /** 此方法适用两级菜单 */
          //this.buildSubmenu(curSubmenu, ownRoutePaths, submenuItemsToShow, ownRoutes);

          /** 此方法适用三级菜单 */
          let submenus = this.buildChildren(curSubmenu, ownRoutePaths, ownRoutes);
          curSubmenu.submenu = submenus;
          submenuItemsToShow.push(curSubmenu);
        }
      }
    },
    buildChildren: function (menu, ownRoutePaths = [], ownRoutes = []) {
      let children = [];
      let submenu = menu.submenu;
      for (let i=0; i<submenu.length; i++) {
        let curSubmenu = submenu[i];
        let curSubmenuPage = submenu[i].page;
        if (curSubmenuPage) {
          if (curSubmenuPage === 'foreign/catalog'
            || curSubmenuPage === 'bus/svc/view-all') {
            children.push(curSubmenu);
          } else if (ownRoutePaths.indexOf(curSubmenuPage) > -1) {
            // 后台返回的权限菜单里含此菜单，予展示
            children.push(curSubmenu);
          }
        } else {
          this.buildChildren(curSubmenu, ownRoutePaths, ownRoutes);
        }
      }
      return children;
    },
    searchExternalUrl: function (externalFlag, ownRoutes = []) {
      let url = "";
      for (let i = 0; i < ownRoutes.length; i++) {
        let route = ownRoutes[i];
        let external = route.meta.external;
        if (external === externalFlag) {
          url = route.path;
        }
      }
      return url;
    }
  },
  watch:{
    /** 监视menuItems值的变化 */
    menuItems(val, newval) {
      // console.log('[侧边菜单]原始值')
      // console.log(val)
      // console.log('[侧边菜单]变化后的值')
      // console.log(newval)
    },
  }
};
</script>
