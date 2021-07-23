<template>
  <div class="kt-grid kt-grid--ver kt-grid--root">
    <div
      class="kt-grid__item kt-grid__item--fluid kt-grid kt-error-v6"
      :style="{
        backgroundImage: `url(${backgroundImage})`
      }"
    >
      <div class="kt-error_container">
        <div class="kt-error_subtitle kt-font-light">
          <h1>503...</h1>
        </div>
        <p class="kt-error_description kt-font-light">
          Service unavailable ...<br />
          Web Server currently unable to handle the HTTP request
        </p>
        <div class="btn-go-wrapper">
          <b-button variant="dark"
                    class="btn-go-lastpage"
                    @click="$router.go(-1)">返回上一页</b-button>
          <b-button variant="success"
                    class="btn-go-home"
                    @click="$router.push('/')">进入首页</b-button>
        </div>
      </div>

    </div>
  </div>
</template>

<style lang="scss">
@import "@/assets/sass/pages/error/error-6.scss";

.btn-go-wrapper {
  margin-left: 0;
}
.btn-go-lastpage,
.btn-go-home {
  margin: 0 35px 0 30px;
  font-size: 16px;
}
</style>

<script>
export default {
  name: "Error-6",
  mounted() {},
  computed: {
    backgroundImage() {
      return process.env.BASE_URL + "assets/media/error/bg6.jpg";
    }
  }
};
</script>
