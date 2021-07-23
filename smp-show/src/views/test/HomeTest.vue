<template>
  <div class="page">
    <h2>Home Test Page</h2>
    <el-button type="primary" @click="getAbout()">获取About信息</el-button>
    <!--<el-button type="primary" @click="getMenu()">获取菜单信息</el-button>-->
  </div>
</template>

<script>
// import mock from '@/mock/index.js'

export default {
  name: 'HomeTest',
  methods: {
      getAbout() {
        // 由于main.js里全局定义的axios,此处直接使用$axios即可.
        // 由于main.js里定义了每个请求前缀,此处的/即为/open/.
        // 经过vue.config.js配置的代理设置,会自动转为http://localhost:8081,从而解决跨域问题
        /*this.$axios({
          url: '/register/list',
          method: 'get'
        }).then(function(res){
          if (res.status === 200) {
            console.log(res);
          }
        }).catch(err => {
          alert('请求失败:' + err.toString())
        })*/

        // let url = API_URL + "/register/list"
        let url = "/register/list"
        // this.$axios.get('http://localhost:8888/register/list').then(function(res){
        this.$axios.get(url).then(function(res){
          // if (res.status === 200) {
            console.log(res);
          // }
        }).catch(err => {
          alert('请求失败:' + err.toString())
        })
      }/*,
      getMenu() {
        axios.get('http://localhost:8080/menu').then(res => { alert(JSON.stringify(res.data)) })
      }*/
  }
}
</script>

<style scoped>

</style>
