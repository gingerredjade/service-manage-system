<template>
  <div class="kt-grid kt-grid--ver kt-grid--root">
    <div
      class="kt-grid__item kt-grid__item--fluid kt-grid kt-error-v1"
      :style="{
        backgroundImage: `url(${backgroundImage})`
      }"
    >
      <div class="kt-error-v1__container">
        <!--<h1 class="kt-error-v1__number">404</h1>-->
        <p class="kt-error-v1__desc" style="margin-top: 10px; color: black;">
          We couldn't find it ...
          The page you're looking for doesn't exist.
        </p>
      </div>
      <div class="btn-go-wrapper">
        <b-button variant="warning"
                  class="btn-go-lastpage"
                  @click="$router.go(-1)">返回上一页</b-button>
        <b-button variant="primary"
                  class="btn-go-home"
                  @click="$router.push('/')">进入首页</b-button>
      </div>
    </div>
  </div>
</template>

<style lang="scss">
  @import "@/assets/sass/pages/error/error-1.scss";

  .btn-go-wrapper {
    margin-left: 0;
  }
  .btn-go-lastpage,
  .btn-go-home {
    margin: 0 5px 0 30px;
    font-size: 16px;
  }
</style>


<script>
  export default {
    name: "Error-1",
    mounted() {},
    computed: {
      backgroundImage() {
        return process.env.BASE_URL + "assets/media/error/bg2.jpg";
      }
    }
  };
</script>
