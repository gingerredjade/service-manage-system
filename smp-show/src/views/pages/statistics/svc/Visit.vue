<template>
  <div class="page-container">
    <h1>服务访问量统计组件</h1>
  </div>
</template>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';

  export default {
    name: "Visit",
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "统计分析", route: "visit" },
        { title: "服务访问量统计"}
      ]);
    }
  }
</script>

<style scoped>

</style>
