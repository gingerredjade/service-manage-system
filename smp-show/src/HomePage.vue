<!-- 搭建框架时,测试用的主页组件 -->
<template>
    <div id="app">
      <router-view/>
    </div>
</template>

<script>
  export default {
    name: 'HomePage'
  }
</script>

<style scoped>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
