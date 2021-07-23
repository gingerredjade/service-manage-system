<template>
  <div class="page-container">
    <h1>在线用户组件</h1>
  </div>
</template>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';

  export default {
    name: "OnlineUsers",
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "系统监控", route: "onlineuser" },
        { title: "在线用户"}
      ]);
    }
  }
</script>

<style scoped>

</style>
