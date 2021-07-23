<template>
  <div class="page-container">
    <h1>主机监控组件</h1>
  </div>
</template>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';

  export default {
    name: "MainFrame",
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "系统监控", route: "mainframe" },
        { title: "主机监控"}
      ]);
    }
  }
</script>

<style scoped>

</style>
