<template>
  <div class="test">
    <perfect-scrollbar class="service-catalog-container-scrollbar">
      <div class="service-catalog-container" id="service-catalog-container">
        <div v-model="serviceTotalCount" class="service-count"><span>服务数量：</span>{{serviceTotalCount}}</div>
        <el-input placeholder="输入服务名检索"
                  type="text"
                  prefix-icon="el-icon-search"
                  size="small"
                  @input="searchFun()"
                  v-model="searchVal"
                  class="service-catalog-search"
                  style="" >
        </el-input>
        <el-menu
          default-active="1"
          class="service-catalog-content"
          @open="handleOpen"
          @close="handleClose"
          background-color="#1e1e2d"
          text-color="#fff"
          active-text-color="#409EFF">
          <!--style="overflow-y: scroll;"  #ffd04b-->
          <div v-if="serviceCatalogData.length === 0" class="search-no-data">未检索到相关服务</div>
          <template v-for="(item, key) in serviceCatalogData">
            <el-submenu v-if="item.subject"
                        :key="key" :index="item.subject.name" class="service-catalog-content-subject">
              <template slot="title">
                <i class="el-icon-s-flag"></i>
                <span :title="item.subject.name">{{ item.subject.name }}</span>
                <div class="kind-number-wrapper">
                  <span class="kind-number">
                    {{ calcServiceNumBySubject(item.types) }}
                  </span>
                </div>
              </template>
              <el-submenu v-for="(val, index) in item.types"
                          :index="val.type.name" :key="index" class="service-catalog-content-type">
                <template slot="title">
                  <i class="el-icon-document"></i>
                  <span :title="val.type.name">{{ val.type.name }}</span>
                  <div class="kind-number-wrapper">
                    <span class="kind-number">{{ val.svcInfo.length }}</span>
                  </div>
                </template>
                <el-menu-item v-for="(svcVal, svcIndex) in val.svcInfo"
                              :key="svcIndex" :index="svcVal.svcName"
                              v-if="val.svcInfo && val.svcInfo.length > 0"
                              @click="handleClick(svcVal, svcIndex)" class="service-catalog-content-svcinfo">
                  <template slot="title">
                    <i class="el-icon-paperclip"></i>
                    <span slot="title" :title="svcVal.svcName">{{ svcVal.svcName }}</span>
                  </template>
                </el-menu-item>
              </el-submenu>
            </el-submenu>
          </template>
        </el-menu>
      </div>
    </perfect-scrollbar>

    <!--<div class="service-catalog-container-scrollbar">
      <div class="service-catalog-container" id="service-catalog-container">
        <el-input placeholder="输入服务名检索"
                  type="text"
                  prefix-icon="el-icon-search"
                  size="small"
                  @input="searchFun(serviceCatalogData)"
                  v-model="searchVal"
                  class="service-catalog-search"
                  style="" >
        </el-input>&lt;!&ndash;  menu-bar-width  &ndash;&gt;
        <el-menu
          default-active="1"
          class="service-catalog-content"
          @open="handleOpen"
          @close="handleClose"
          background-color="#1e1e2d"
          text-color="#fff"
          active-text-color="#409EFF">
          &lt;!&ndash; style="overflow-y: scroll;"  #ffd04b &ndash;&gt;
          <div v-if="serviceCatalogData.length === 0" class="search-no-data">未检索到相关服务</div>
          <template v-for="(item, key) in serviceCatalogData">
            <el-submenu v-if="item.subject"
                        :key="key" :index="item.subject.name" class="service-catalog-content-subject">
              <template slot="title">
                <i class="el-icon-s-flag"></i>
                <span>{{ item.subject.name }}</span>
              </template>
              <el-submenu v-for="(val, index) in item.types"
                          :index="val.type.name" :key="index" class="service-catalog-content-type">
                <template slot="title">
                  <i class="el-icon-document"></i>
                  <span>{{ val.type.name }}</span>
                </template>
                <el-menu-item v-for="(svcVal, svcIndex) in val.svcInfo"
                              :key="svcIndex" :index="svcVal.svcName"
                              v-if="val.svcInfo && val.svcInfo.length > 0"
                              @click="handleClick(svcVal, svcIndex)" class="service-catalog-content-svcinfo">
                  <template slot="title">
                    <i class="el-icon-paperclip"></i>
                    <span slot="title">{{ svcVal.svcName }}</span>
                  </template>
                </el-menu-item>
              </el-submenu>
            </el-submenu>
          </template>
        </el-menu>
      </div>
    </div>-->

  </div>

</template>

<script>
  import $axios from 'axios'

  export default {
    name: "ServiceCatalog",
    components: {

    },
    mounted () {
      this.findCatalogAll();
    },
    data() {
      return {
        loading: false,
        serviceCatalogDataPremier: [],
        serviceCatalogData: [],
        //API_URL: API_URL,

        searchVal: '',
        serviceTotalCount: 0
      }
    },
    methods: {
      handleOpen(key, keyPath) {
        //console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        //console.log(key, keyPath);
      },
      handleSelect(a, b) {
        console.log('handleSelect');
      },
      handleClick(val, index) {
        console.log(val);
        alert(JSON.stringify(val));
      },

      /** 服务目录相关 **/
      // 获取全部服务目录信息
      findCatalogAll: function () {
        let that = this;
        that.loading = true;
        let params = {}
        // this.$axios.get(API_URL+'/service/catalog/all', {
        this.$axios.get('/service/catalog/all', {
          params: params
        }).then( function (res) {
          //console.log(res.data.data);
          that.serviceCatalogDataPremier = res.data.data;
          that.serviceCatalogData = JSON.parse(JSON.stringify(res.data.data));

          // 计算检索出的服务数量
          if (res.data.data != null && res.data.data.length !== 0) {
            let count = that.calcServiceTotalCount(res.data.data);
            that.serviceTotalCount = count;
          }

          that.loading = false;
        }).catch( () => {} );
      },

      // 实时检索事件
      searchFun: function () {
        this.serviceCatalogData = JSON.parse(JSON.stringify(this.serviceCatalogDataPremier));
        if (this.searchVal === '') {
          this.findCatalogAll();
        } else {
          /* 快速检索 */
          // 结果-服务目录
          let serviceCatalogResultList = [];
          this.serviceCatalogData.forEach(serviceCatalogItem => {
            // 结果-服务目录/类型数组
            let typeResultList = [];
            serviceCatalogItem.types.forEach(typeItem => {
              // 结果-服务目录/类型数组/服务信息
              let svcInfoResultList = [];
              typeItem.svcInfo.forEach(svcItem => {
                if (svcItem.svcName.toString().indexOf(this.searchVal) >= 0) {
                  svcInfoResultList.push(svcItem);
                }
              });
              if (svcInfoResultList.length > 0) {
                typeItem.svcInfo = svcInfoResultList;
                typeResultList.push(typeItem);
              }
            });
            serviceCatalogItem.types = typeResultList;
            if (serviceCatalogItem.types.length > 0) {
              serviceCatalogResultList.push(serviceCatalogItem);
            }
          });
          this.serviceCatalogData = serviceCatalogResultList;
        }

        // 计算检索出的服务数量
        this.serviceTotalCount = this.calcServiceTotalCount(this.serviceCatalogData);
      },

      // 计算服务数量
      calcServiceTotalCount: function (data) {
        let number = 0;
        data.forEach(serviceCatalogItem => {
          serviceCatalogItem.types.forEach(typeItem => {
            number += typeItem.svcInfo.length;
          });
        });
        return number;
      },


      // 根据服务主题统计其中包含的服务数量
      calcServiceNumBySubject: function (data) {
        let number = 0;
        data.forEach(serviceTypeItem => {
          number += serviceTypeItem.svcInfo.length;
        });
        return number;
      }


    }

  }
</script>

<style lang="scss">
// 服务检索结果空提示
.search-no-data {
  color: #fff;
  font-size: 14px;
  text-align: center;
  padding-top: 2px;
}

// 服务内容展示相关
.service-catalog-container-scrollbar {
  /*max-height: 90vh;*/
  position: absolute;
  width: 300px;
  height: 100%;
  top: 10px;
  left: 10px;
  background: #1e1e2d; /* rgba(0,19,57,200) */
}

div.service-catalog-container {
  position: relative;
  height: 90%;
  top: 10px;
  /*left: 10px;*/
  background: #1e1e2d;
  padding: 0 10px 0 10px;

    .service-catalog-search {
      width: 100%;
      /*border: 1px solid #DCDFE6;*/
    }

    .el-input__inner {
      border-radius: 4px;
      background: transparent;
      color: #fff;
      border: 1px solid #333;
    }
    .el-input__inner:hover,
    .el-input__inner:focus {
      border-color:#409EFF;
      outline:0;
    }

    .el-menu {
      /*position: absolute;
      top: 0;
      bottom: 0;*/
      text-align: left;
      height: 100%;
      background: #1e1e2d;
      border-right: none;
    }

  .service-catalog-content {
    width: inherit;
    /*overflow-y: scroll;
    overflow-x: scroll;*/
    overflow: auto;
  }
  .service-catalog-content-subject {
  }
  .service-catalog-content-type {
  }
  .service-catalog-content-svcinfo {
    height: 30px;
    line-height: 30px;
    padding-left: 40px !important;
  }
  .el-submenu__title {
    height: 34px;
    line-height: 34px;
  }
  .service-catalog-content-subject > .el-submenu__title  {
    padding-left: 10px !important;
  }
  .service-catalog-content-type > .el-submenu__title {
    padding-left: 20px !important;
  }
  div.service-catalog-container .el-submenu__title:hover {
    background: #0debaa;
  }
  .el-submenu__title:focus,
  .el-submenu__title:hover,
  .service-catalog-content-svcinfo:focus,
  .service-catalog-content-svcinfo:hover {
    > i {
     color: #0f9af0;
    }
  }

  .service-count {
    font-size: 14px;
    color: #ffffff;
    font-weight: 400;
    text-transform: initial;
  }


  // 滚动条相关
  /*定义滚动条宽高及背景，宽高分别对应横竖滚动条的尺寸*/
  ::-webkit-scrollbar {
    width: 4px; /*对垂直滚动条有效*/
    height: 4px;  /*对水平滚动条有效*/
    background-color: transparent; /*#fff*/
  }

  /*定义滚动条的轨道颜色、内阴影及圆角*/
  ::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
    background-color: transparent; /*#ececec   #fff*/
    border-radius: 10px;
  }

  /*定义滑块颜色、内阴影及圆角*/
  ::-webkit-scrollbar-thumb {
    border-radius: 10px;
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
    background-color: rgba(64, 158, 255, .6); /*rgba(0, 0, 0, .1);   #409eff*/
  }

  /* 光标移到滚动滑块上样式颜色变深 */
  ::-webkit-scrollbar-thumb:hover {
    background-color: rgba(64, 158, 255, .9); /*rgba(0, 0, 0, .2);*/
  }

  /*定义两端按钮的样式*/
  ::-webkit-scrollbar-button {
    background-color: cyan;
  }

  /*定义右下角汇合处的样式*/
  ::-webkit-scrollbar-corner {
    background: khaki;
  }


  // 类别含有的数量相关
  .kind-number-wrapper {
    height: 20px;
    min-width: 20px;
    width: auto;
    background-color: #009688 !important;
    border-color: #009688 !important;  /*#ff9800  #009688 #3f51b5*/
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    border-radius: 50%;
    display: inline-flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    justify-content: center;
    line-height: normal;
    position: relative;
    text-align: center;
    vertical-align: middle;
    overflow: hidden;
  }
  .kind-number {
    font-size: 1.0rem !important;
    font-weight: 400;
    line-height: 1.5rem;
    letter-spacing: normal !important;
    font-family: "Roboto", sans-serif !important;
    color: #FFFFFF !important;
    caret-color: #FFFFFF !important;
  }



}
</style>
