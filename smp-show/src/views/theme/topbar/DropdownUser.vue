<template>
  <div>
    <!--begin: Head -->
    <div
      class="kt-user-card kt-user-card--skin-dark kt-notification-item-padding-x"
      :style="{ backgroundImage: `url(${backgroundImage})` }">
      <div class="kt-user-card__avatar">
        <img
          class="kt-hidden"
          alt="Pic"
          src="@/assets/media/users/300_25.jpg"/>
        <!--use below badge element instead the user avatar to display username's first letter(remove kt-hidden class to display it) -->
        <span
          class="kt-badge kt-badge--lg kt-badge--rounded kt-badge--bold kt-font-success">{{showUserNameFirstCharacter}}</span>
      </div>
      <div class="kt-user-card__name">{{showUserName}}</div>
      <!--<div class="kt-user-card__badge">
        <span class="btn btn-success btn-sm btn-bold btn-font-md">23 messages</span>
      </div>-->
    </div>
    <!--end: Head -->
    <!--begin: Navigation -->
    <div class="kt-notification">
      <a href="#" class="kt-notification__item"  v-if="hasPerms('sys:user:view')">
        <div class="kt-notification__item-icon">
          <i class="flaticon2-calendar-3 kt-font-success"></i>
        </div>
        <div class="kt-notification__item-details" @click="jumpUserProfile()">
          <!--<router-link to="/sys/user/profile">-->
            <div class="kt-notification__item-title kt-font-bold">
              {{$t('GUIDE.PERSONAL.MY_PROFILE')}}
            </div>
            <div class="kt-notification__item-time">
              {{$t('GUIDE.PERSONAL.ACCOUNT_INFO')}}
            </div>
          <!--</router-link>-->
        </div>
      </a>
      <div class="kt-notification__custom kt-space-between">
        <a
          href="#"
          v-on:click="onLogout()"
          class="btn btn-label btn-label-brand btn-sm btn-bold"
          >{{$t('AUTH.LOGOUT.BUTTON')}}</a>
      </div>
    </div>
    <!--end: Navigation -->
  </div>
</template>

<script>
import { LOGOUT } from "@/store/auth.module";
import Cookies from "js-cookie";
import { hasPermission } from '@/utils/permission/index.js';

export default {
  name: "KTDropdownUser",
  methods: {
    /** 权限控制相关-页面按钮权限 **/
    hasPerms: function (perms) {
      // 根据权限标识和外部指示状态进行权限判断
      // ('&'表示运算符按位与，都为1才为1否则为0。也类似于且，都真才为真)
      return hasPermission(perms) & !this.disabled
    },

    // 退出登录
    onLogout() {
      this.logoutApi();
    },
    logoutApi() {
      this.deleteCookie("username");
      this.deleteSessionStorage("user");
      this.$store
        .dispatch(LOGOUT)
        .then( () => this.$router.push("/login")).catch(()=>{});
    },
    // 删除cookie
    deleteCookie: function(name) {
      Cookies.remove(name)
    },
    // 删除sessionStorage
    deleteSessionStorage: function(name) {
      sessionStorage.removeItem(name)
    },
    /*onLogout() {
      this.$store
        .dispatch(LOGOUT)
        .then(() => this.$router.push({ name: "login" }));
    }*/
    jumpUserProfile: function () {
      this.$router.push({ path: '/sys/user/profile' });
    }
  },
  computed: {
    backgroundImage() {
      return process.env.BASE_URL + "assets/media/misc/bg-1.jpg";
    },
    showUserName() {
      return Cookies.get('username');
    },
    showUserNameFirstCharacter() {
      let username = Cookies.get('username');
      return username.charAt(0).toUpperCase();
    }
  }
};
</script>
