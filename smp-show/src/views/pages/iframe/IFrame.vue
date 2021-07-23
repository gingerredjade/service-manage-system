<template>
  <div class="iframe-container">
    <iframe :src="src" scrolling="auto" frameborder="0" class="frame"
            :onload="onloaded()"
            id="externalIframe" name="externalIframe"
    >
    </iframe>
  </div>
</template>

<script>
export default {
  data() {
    return {
      src: "",
      loading: null
    }
  },
  methods: {
    // 获取路径
    resetSrc: function(url) {
      this.src = url
      this.load()
    },
    load: function() {
      let that = this;
      that.loading = that.$loading({
        lock: true,
        text: "loading...",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.5)",
        fullscreen: false,
        target: document.querySelector("#kt_content")
      });

      let ifr = document.getElementById('externalIframe');
      //that.setIframeHeight(ifr);
    },
    onloaded: function() {
      if(this.loading) {
        this.loading.close()
      }
    },
    setIframeHeight: function (iframe) {
      if (iframe) {
        let win = iframe.contentWindow || iframe.contentDocument || iframe.document,
          doc = win.document,
          html = doc.documentElement,
          body = doc.body;

        let height = this.calcPageHeight(doc);
        iframe.style.height = 1386+"px";
      }
    },
    calcPageHeight (doc) {
      let cHeight = Math.max(doc.body.clientHeight, doc.documentElement.clientHeight);
      let sHeight = Math.max(doc.body.scrollHeight, doc.documentElement.scrollHeight);
      let oHeight = Math.max(doc.body.offsetHeight, doc.documentElement.offsetHeight);
      let height = Math.max(cHeight, sHeight, oHeight);
      return height;
    }
  },
  mounted() {
    this.resetSrc(this.$store.state.iframe.iframeUrl);
  },
  watch: {
    /** 监听当前路由 */
    $route: {
      handler: function(val, oldVal) {
        // 如果是跳转到嵌套页面，切换iframe的url
        this.resetSrc(this.$store.state.iframe.iframeUrl);
      },
      immediate: true
    }
  }
}
</script>

<style lang="scss">
  .iframe-container {
    height: 100%;
    .frame {
      width: 100%;
      height: 100%;
    }
  }
</style>
