<template>
  <div class="iframe-container">
    <div class="open-operation">
      <!--<b-button block variant="primary"
                class="open-button"
                @click="open">
        转至接口页
      </b-button>-->
    </div>
    <iframe :src="src" scrolling="auto" frameborder="0" class="frame"
            :onload="onloaded()"
            id="interfaceIframe" name="interfaceIframe">
    </iframe>
  </div>
</template>


<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';

  export default {
    name: "ApiInterface",
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "开放平台", route: "apis" },
        { title: "接口文档"}
      ]);

      //this.resetSrc(API_URL + "/swagger-ui.html");
      this.resetSrc("/swagger-ui.html");
    },
    data() {
      return {
        src: "",
        loading: null
      }
    },
    methods: {
      // 获取路径
      resetSrc: function (url) {
        this.src = url;
        this.load();
      },
      load: function () {
        let that = this;
        that.loading = that.$loading({
          lock: true,
          text: "loading...",
          spinner: "el-icon-loading",
          background: "rgba(0, 0, 0, 0.5)",
          fullscreen: false,
          target: document.querySelector("#kt_content")
        });

        let ifr = document.getElementById('interfaceIframe');
        that.setIframeHeight(ifr);
      },
      onloaded: function () {
        if (this.loading) {
          this.loading.close();
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
      },

      open: function () {
        // let path = API_URL + "/swagger-ui.html";
        let path = "/swagger-ui.html";
        window.location.href = path;
        //window.open(path, '_blank');
      }

    }
  }

</script>

<style lang="scss">
  .open-operation {
    margin: 0 0 10px 0;
    .open-button {
      font-size: large;
      font-weight: bold;
    }
  }

 .iframe-container {
  height: 100%;
  .frame {
    width: 100%;
    height: 100%;
  }
}
</style>
