<template>
  <!-- 使用transition元素把需要被动画控制的元素，包裹起来。用v-for渲染的需要使用transition-group包裹 -->
  <transition name="fade-in-up">
    <router-view></router-view>
  </transition>
</template>

<script>
  export default {
    name: "ServiceManage"
  }
</script>

<style scoped>

</style>
