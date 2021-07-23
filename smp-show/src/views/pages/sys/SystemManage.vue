<template>
  <!-- 使用transition元素把需要被动画控制的元素，包裹起来 -->
  <transition name="fade-in-up">
    <router-view/>
  </transition>
</template>

<script>
export default {
  components: {}
};
</script>
