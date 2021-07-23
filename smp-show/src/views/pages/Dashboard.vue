<template>
  <div class="board-content">
    <div class="toolbar">
      <div class="">
      </div>
      <div class="">
        <el-form :inline="true" :size="size">
          <el-form-item>
            <el-button-group>
              <el-tooltip :content="$t('ACTION.REFRESH')" placement="top">
                <el-button icon="fa fa-refresh" @click="loadCountData()"/>
              </el-tooltip>
            </el-button-group>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <!-- 导航菜单测试 menu-tree为导航菜单树组件，动态加载菜单 -->
    <!--<el-menu ref="navmenu" default-active="1" :class="collapse?'menu-bar-collapse-width':'menu-bar-width'"
             :collapse="collapse" :collapse-transition="false" :unique-opened="true  "
             @open="handleopen" @close="handleclose" @select="handleselect">
      <menu-tree v-for="item in navTree" :key="item.id" :menu="item"></menu-tree>
    </el-menu>-->

    <div class="row box-card-row-wrapper">
      <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="text-num-label">{{$t('STATISTIC.LABEL.USER_TOTAL')}}</span>
            <el-tag size="mini" effect="plain" type="success" style="float: right; padding: 0 5px">{{$t('STATISTIC.LABEL.TOTAL')}}</el-tag>
          </div>
          <div class="number-wrapper">
            <span class="box-card-icon">
              <img
                svg-inline
                class="card-svg-icon--brand kt-svg-icon kt-svg-icon--brand"
                src="@/assets/media/icons/svg/Home/Library.svg"
                alt=""
              />
            </span>
            <div class="text item text-num">
              {{ userGrossNum }}
            </div>
          </div>
          <div class="circle-wrapper">
            <div class="circle-button-wrapper">
              <figure class="circle"></figure>
            </div>
            <div class="operation-btn" @click="jumpViewUser()">
              <a href="#" class="btn btn-sm btn-label-brand btn-bold">
                {{$t('STATISTIC.BUTTON.TO_VIEW_USER')}}
              </a>
            </div>
          </div>
        </el-card>
      </div>

      <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="text-num-label">{{$t('STATISTIC.LABEL.SVC_UNAUDITED')}}</span>
            <el-tag size="mini" effect="plain" type="success" style="float: right; padding: 0 5px">{{$t('STATISTIC.LABEL.TOTAL')}}</el-tag>
          </div>
          <div class="number-wrapper">
            <span class="box-card-icon">
              <img
                svg-inline
                class="card-svg-icon--brand kt-svg-icon kt-svg-icon--warning"
                src="@/assets/media/icons/svg/Communication/Urgent-mail.svg"
                alt=""
              />
            </span>
            <div class="text item text-num">
              {{ svcUnAuditedNum }}
            </div>
          </div>
          <div class="circle-wrapper">
            <div class="circle-button-wrapper">
              <figure class="circle"></figure>
            </div>
            <div class="operation-btn" @click="jumpSvcAudit()">
              <a href="#" class="btn btn-sm btn-label-danger btn-bold">
                {{$t('STATISTIC.BUTTON.TO_AUDIT')}}
              </a>
            </div>
          </div>
        </el-card>
      </div>

      <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="text-num-label">{{$t('STATISTIC.LABEL.SVC_UNRELEASED')}}</span>
            <el-tag size="mini" effect="plain" type="success" style="float: right; padding: 0 5px">{{$t('STATISTIC.LABEL.TOTAL')}}</el-tag>
          </div>
          <div class="number-wrapper">
            <span class="box-card-icon">
             <img
               svg-inline
               class="card-svg-icon--brand kt-svg-icon kt-svg-icon--warning"
               src="@/assets/media/icons/svg/Communication/Urgent-mail.svg"
               alt=""
             />
            </span>
            <div class="text item text-num">
              {{ svcUnReleasedNum }}
            </div>
          </div>
          <div class="circle-wrapper">
            <div class="circle-button-wrapper">
              <figure class="circle"></figure>
            </div>
            <div class="operation-btn" @click="jumpSvcRelease()">
              <a href="#" class="btn btn-sm btn-label-success btn-bold">
                {{$t('STATISTIC.BUTTON.TO_RELEASE')}}
              </a>
            </div>
          </div>
        </el-card>
      </div>
    </div>

    <div class="row box-card-row-wrapper">
      <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="text-num-label">{{$t('STATISTIC.LABEL.SVC_TOTAL')}}</span>
            <el-tag size="mini" effect="plain" type="success" style="float: right; padding: 0 5px">{{$t('STATISTIC.LABEL.TOTAL')}}</el-tag>
          </div>
          <div class="number-wrapper">
            <span class="box-card-icon">
              <img
                svg-inline
                class="card-svg-icon--brand kt-svg-icon kt-svg-icon--brand"
                src="@/assets/media/icons/svg/Home/Library.svg"
                alt=""
              />
            </span>
            <div class="text item text-num">
              {{ svcGrossNum }}
            </div>
          </div>
          <div class="circle-wrapper">
            <div class="circle-button-wrapper">
              <figure class="circle"></figure>
            </div>
            <div class="operation-btn" @click="jumpSvcView()">
              <a href="#" class="btn btn-sm btn-label-brand btn-bold">
                {{$t('STATISTIC.BUTTON.TO_VIEW_SVC')}}
              </a>
            </div>
          </div>

        </el-card>
      </div>

      <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="text-num-label">{{$t('STATISTIC.LABEL.SVC_AUDITED')}}</span>
            <el-tag size="mini" effect="plain" type="success" style="float: right; padding: 0 5px">{{$t('STATISTIC.LABEL.TOTAL')}}</el-tag>
          </div>
          <div>
            <span class="box-card-icon">
              <img
                svg-inline
                class="card-svg-icon--brand kt-svg-icon kt-svg-icon--success"
                src="@/assets/media/icons/svg/Design/Layers.svg"
                alt=""
              />
            </span>
            <div class="text item text-num">
              {{ svcAuditedPassNum }}
            </div>
          </div>
          <div class="circle-wrapper">
            <div class="circle-button-wrapper">
              <figure class="circle"></figure>
            </div>
            <div class="operation-btn" @click="jumpSvcAudit()">
              <a href="#" class="btn btn-sm btn-label-danger btn-bold">
                {{$t('STATISTIC.BUTTON.TO_AUDIT')}}
              </a>
            </div>
          </div>
        </el-card>
      </div>

      <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span class="text-num-label">{{$t('STATISTIC.LABEL.SVC_RELEASED')}}</span>
            <el-tag size="mini" effect="plain" type="success" style="float: right; padding: 0 5px">{{$t('STATISTIC.LABEL.TOTAL')}}</el-tag>
          </div>
          <div>
            <span class="box-card-icon">
              <img
                svg-inline
                class="card-svg-icon--brand kt-svg-icon kt-svg-icon--success"
                src="@/assets/media/icons/svg/Design/Layers.svg"
                alt=""
              />
            </span>
            <div class="text item text-num">
              {{ svcReleasedNum }}
            </div>
          </div>
          <div class="circle-wrapper">
            <div class="circle-button-wrapper">
              <figure class="circle"></figure>
            </div>
            <div class="operation-btn" @click="jumpSvcRelease()">
              <a href="#" class="btn btn-sm btn-label-success btn-bold">
                {{$t('STATISTIC.BUTTON.TO_RELEASE')}}
              </a>
            </div>
          </div>
        </el-card>
      </div>

    </div>

  </div>
</template>

<script>
import { SET_BREADCRUMB } from "@/store/breadcrumbs.module";

import { mapState } from 'vuex';
import MenuTree from "@/components/MenuTree";

/**
 * Sample widgets data source
 */


export default {
  name: "dashboard",
  components: {
    MenuTree
  },
  computed: {
    ...mapState({
      navTree: state=>state.menu.navTree,
      collapse: state=>state.app.collapse
    }),

  },
  data() {
    return {
      size: 'small',

      userGrossNum: 0,
      svcGrossNum: 0,
      svcReleasedNum: 0,
      svcUnReleasedNum: 0,
      svcAuditedPassNum: 0,
      svcUnAuditedNum: 0
    };
  },
  mounted() {
    this.$store.dispatch(SET_BREADCRUMB, [{ title: "首页" }]);

    this.loadCountData();
  },
  methods: {
    handleopen() {
      console.log('handleopen')
    },
    handleclose() {
      console.log('handleclose')
    },
    handleselect(a, b) {
      console.log('handleselect')
    },

    /**
     * Set current active on click
     * @param event
     */
    loadCountData: function () {
      this.$api.user.countUserGross().then( (res) => {
        this.userGrossNum = res.data.data;
      });

      this.$api.stat.countBySvcState().then( (res) => {
        this.svcGrossNum = res.data.data.grossNumber;
        this.svcReleasedNum = res.data.data.releasedNumber;
        this.svcUnReleasedNum = res.data.data.unReleasedNumber;
        this.svcAuditedPassNum = res.data.data.auditedPassNumber;
        this.svcUnAuditedNum = res.data.data.unAuditedNumber;
      });
    },

    jumpSvcView: function () {
      this.$router.push({ path: '/bus/svc/view' });
    },
    jumpSvcAudit: function () {
      this.$router.push({ path: '/bus/svc/audit' });
    },
    jumpSvcRelease: function () {
      this.$router.push({ path: '/bus/svc/release' });
    },
    jumpViewUser: function () {
      this.$router.push({ path: '/sys/user' });
    }
  }
};
</script>


<style scoped>
  .box-card-row-wrapper {
    margin-bottom: 20px;
  }
  .text-num-label {
    font-size: 1.2rem;
    font-weight: 500;
    color: #595d6e;
  }
  .text-num {
    font-size: 36px;
    line-height: 36px;
    padding: 5px 0 10px;
    overflow: hidden;
    white-space: nowrap;
    word-break: break-all;
    text-overflow: ellipsis;
    color: #5d78ff !important;
  }
  .box-card-icon {
    float: right;
    width: 38px;
    height: 38px;
  }
  .card-svg-icon--brand {
    width: 38px;
    height: 38px;
  }


  div.board-content {
    /*width: 100%;
    height: 100%;
    background: transparent url("../../assets/media/gis/map9.jpg") no-repeat fixed center;*/
  }

  /* 圆元素 */
  /* 主伪元素绘制,伪元素旋转，与主元素合并为一个圆环 */
  .circle,
  .circle::before,
  .circle::after {
    width: 2.2em;
    height: 2.2em;
    box-sizing: border-box;
    border-top: 0.3em solid currentColor;
    border-radius: 50%;
    position: relative;
    /*margin: auto;*/
  }
  .circle::before,
  .circle::after {
    content: '';
    position: absolute;
    top: -0.2em;
  }
  .circle {
    color: hotpink;
  }
  .circle::before {
    color: dodgerblue;
    transform: rotate(120deg);
  }
  .circle::after {
    color: gold;
    transform: rotate(240deg);
  }

  .circle-button-wrapper,
  .operation-btn {
    display: inline-block;
  }
  .circle-button-wrapper {
    float: right;
  }


  .number-wrapper {
    /*border-bottom: 1px dashed #EBEEF5;*/
  }
  .circle-wrapper {
    padding-top: 20px;
  }

</style>


