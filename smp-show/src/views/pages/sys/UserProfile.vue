<template>
  <div class="page-container">
    <div class="my-profile__header">
      <h3 class="my-profile__title">我的信息</h3>
    </div>
    <div class="my-profile__content">
      <div class="row">
        <div class="col-lg-4">
          <div class="m-portlet m-portlet--full-height">
            <div class="m-portlet__body">
              <div class="m-card-profile">
                <div class="m-card-profile__title m--hide">
                  Your Profile
                </div>
                <div class="m-card-profile__pic">
                  <div class="m-card-profile__pic-wrapper">
                    <img src="@/assets/media/bg/bg-9.jpg" alt="头像" />
                  </div>
                </div>
                <div class="m-card-profile__details">
                  <span class="m-card-profile__name">{{this.userInfo.username}}</span>
                  <a href="" class="m-card-profile__email m-link" disabled="true" style="pointer-events: none;">
                    {{this.userInfo.email}}
                  </a>
                </div>

                <div class="m-portlet__body-separator"></div>

                <!--<div class="m-widget1 m-widget1&#45;&#45;paddingless">
                  <div class="m-widget1__item">
                    <div class="row m-row&#45;&#45;no-padding align-items-center">
                      <div class="col">
                        <h3 class="m-widget1__title">Member Profit</h3>
                        <span class="m-widget1__desc">Awerage Weekly Profit</span>
                      </div>
                      <div class="col m&#45;&#45;align-right">
                        <span class="m-widget1__number m&#45;&#45;font-brand">+$17,800</span>
                      </div>
                    </div>
                  </div>
                  <div class="m-widget1__item">
                    <div class="row m-row&#45;&#45;no-padding align-items-center">
                      <div class="col">
                        <h3 class="m-widget1__title">Orders</h3>
                        <span class="m-widget1__desc">Weekly Customer Orders</span>
                      </div>
                      <div class="col m&#45;&#45;align-right">
                        <span class="m-widget1__number m&#45;&#45;font-danger">+1,800</span>
                      </div>
                    </div>
                  </div>
                  <div class="m-widget1__item">
                    <div class="row m-row&#45;&#45;no-padding align-items-center">
                      <div class="col">
                        <h3 class="m-widget1__title">Issue Reports</h3>
                        <span class="m-widget1__desc">System bugs and issues</span>
                      </div>
                      <div class="col m&#45;&#45;align-right">
                        <span class="m-widget1__number m&#45;&#45;font-success">-27,49%</span>
                      </div>
                    </div>
                  </div>
                </div>-->

              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-8">
          <div class="m-portlet m-portlet--full-height m-portlet--tabs">
            <div class="m-portlet__head">
              <div class="m-portlet__head-tools">
                <ul class="nav nav-tabs m-tabs m-tabs-line m-tabs-line--left m-tabs-line--primary" role="tablist">
                  <li class="nav-item m-tabs__item">
                    <a class="nav-link m-tabs__link active" data-toggle="tab" href="#m_user_profile_tab_1" role="tab">
                      <i class="flaticon-avatar m--hide"></i>
                      用户信息
                    </a>
                  </li>
                  <!--<li class="nav-item m-tabs__item">
                    <a class="nav-link m-tabs__link" data-toggle="tab" href="#m_user_profile_tab_2" role="tab">
                      <i class="flaticon-more m&#45;&#45;hide"></i>
                      其他信息
                    </a>
                  </li>-->
                </ul>
              </div>
            </div>

            <div class="tab-content">
              <div class="tab-pane active" id="m_user_profile_tab_1">
                <form class="m-form m-form--fit m-form--label-align-right" @submit.stop.prevent="onSubmit">
                  <div class="m-portlet__body">
                    <!--<div class="form-group m-form__group m&#45;&#45;margin-top-10 m&#45;&#45;hide">
                      <div class="alert m-alert m-alert&#45;&#45;default" role="alert">
                        The example form below demonstrates common HTML form elements that receive updated styles from Bootstrap with additional classes.
                      </div>
                    </div>-->
                    <div class="form-group m-form__group row">
                      <div class="col-10 ml-auto">
                        <h3 class="m-form__section">1. 基本信息</h3>
                      </div>
                    </div>
                    <div class="form-group m-form__group row">
                      <label class="col-2 col-form-label">昵称</label>
                      <div class="col-7">
                        <input class="form-control m-input" type="text" :value=this.userInfo.nickName>
                      </div>
                    </div>
                    <div class="form-group m-form__group row">
                      <label class="col-2 col-form-label">性别</label>
                      <div class="col-7">
                        <input v-if="this.userInfo.sex === 1" class="form-control m-input" type="text" value="男">
                        <input v-else-if="this.userInfo.sex === 2" class="form-control m-input" type="text" value="女">
                        <input v-else class="form-control m-input" type="text" value="未知">
                      </div>
                    </div>
                    <div class="form-group m-form__group row">
                      <label class="col-2 col-form-label">公司名称</label>
                      <div class="col-7">
                        <input class="form-control m-input" type="text" :value=this.userInfo.deptName>
                        <!--<span class="m-form__help">If you want your invoices addressed to a company. Leave blank to use your full name.</span>-->
                      </div>
                    </div>
                    <div class="form-group m-form__group row">
                      <label class="col-2 col-form-label">角色</label>
                      <div class="col-7">
                        <input class="form-control m-input" type="text" :value=this.userInfo.roleNames>
                      </div>
                    </div>
                    <div class="m-form__seperator m-form__seperator--dashed m-form__seperator--space-2x"></div>
                    <div class="form-group m-form__group row">
                      <div class="col-10 ml-auto">
                        <h3 class="m-form__section">2. 联系方式</h3>
                      </div>
                    </div>
                    <div class="form-group m-form__group row">
                      <label class="col-2 col-form-label">手机号码</label>
                      <div class="col-7">
                        <input class="form-control m-input" type="text" :value=this.userInfo.mobile>
                      </div>
                    </div>
                    <div class="form-group m-form__group row">
                      <label class="col-2 col-form-label">邮箱</label>
                      <div class="col-7">
                        <input class="form-control m-input" type="text" :value=this.userInfo.email>
                      </div>
                    </div>
                    <div class="m-form__seperator m-form__seperator--dashed m-form__seperator--space-2x"></div>
                    <div class="form-group m-form__group row">
                      <div class="col-10 ml-auto">
                        <h3 class="m-form__section">3. 时间信息</h3>
                      </div>
                    </div>
                    <div class="form-group m-form__group row">
                      <label class="col-2 col-form-label">创建时间</label>
                      <div class="col-7">
                        <input class="form-control m-input" type="text" :value=this.dateFormat(this.userInfo.createTime)>
                      </div>
                    </div>
                    <div class="form-group m-form__group row">
                      <label class="col-2 col-form-label">更新时间</label>
                      <div class="col-7">
                        <input class="form-control m-input" type="text" :value=this.dateFormat(this.userInfo.lastUpdateTime)>
                      </div>
                    </div>
                  </div>
                  <div class="m-portlet__foot m-portlet__foot--fit">
                    <div class="m-form__actions">
                      <div class="row">
                        <div class="col-2">
                        </div>
                        <div class="col-7">
                          <button type="submit" class="btn btn-accent m-btn m-btn--air m-btn--custom">
                            {{ $t("ACTION.UPDATE") }}
                          </button>
                          &nbsp;&nbsp;&nbsp;&nbsp;
                          <button type="reset" class="btn btn-secondary m-btn m-btn--air m-btn--custom"
                                  @click="findUserInfo()">{{ $t("ACTION.RESET") }}</button>
                        </div>
                      </div>
                    </div>
                  </div>
                </form>
              </div>
              <!--<div class="tab-pane " id="m_user_profile_tab_2">
                无
              </div>-->
            </div>

          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';
  import { format } from "@/utils/datetime";
  import Cookies from "js-cookie";

  export default {
    name: "UserProfile",
    computed: {

    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "用户管理", route: "profile" },
        { title: "个人信息"}
      ]);

      this.findUserInfo();
    },
    data() {
      return {
        userInfo: {}
      }
    },
    methods: {
      /** 通用 **/
      // 时间格式化
      dateFormat: function (data) {
        return format(data);
      },

      findUserInfo: function () {
        let userName = Cookies.get('username');
        let params = {'userName': userName};
        this.$api.user.findByName(params).then( (res) => {
          // 组织用户数据
          this.userInfo = res.data.data;
        });
      },

      onSubmit: function () {
        // =======该系统不开放用户更新信息=========
        this.$message({
          message: "系统不开放用户更新！联系管理员修改账户！",
          type: 'warn'
        })
        return;

        // =======开放用户更新部分从此开始=====

      }

    }

  }
</script>

<style lang="scss">
    // 通用相关
    .m-portlet {
      -webkit-box-shadow: 0px 1px 15px 1px rgba(69,65,78,0.08);
      box-shadow: 0px 1px 15px 1px rgba(69,65,78,0.08);
      background-color: #fff;
      margin-bottom: 2.2rem;

      .m-portlet__body-separator {
        border-bottom: 1px solid #f4f5f8;
        height: 0;
        overflow: hidden;
        margin: 30px -2.2rem;
      }
    }

    .m-portlet--full-height {
      height: calc(100% - 2.2rem);

      .m-portlet__body {
        height:calc(100% - 2.2rem - 2.2rem);
        padding: 2.2rem 2.2rem;
        color: #575962;
      }
    }
    .m--hide {
      display: none !important;
    }

    // 我的信息相关
    .my-profile__title {
      color: #3f4047;
      display: inline-block;
      vertical-align: middle;
      font-size: 1.55rem;
    }

    // 左侧卡片信息相关
    .m-card-profile {
      padding: 5px 0;
      margin: 0;

      .m-card-profile__title {
        text-align: center;
        font-size: 1.5rem;
        font-weight: 500;
      }

      .m--hide {
        display: none !important;
      }

      .m-card-profile__pic {
        padding: 0 5px 0 0;
        text-align: center;

        .m-card-profile__pic-wrapper {
          border: 2px solid #f4f5f8;
          padding: 8px;
          display: inline-block;
          margin: 2rem auto;
          border-radius: 100%;

          img {
            vertical-align: middle;
            border-style: none;

            text-align: center;
            max-width: 130px !important;
            margin: 0 auto !important;
            border-radius: 100%;
          }
        }
      }

      .m-card-profile__details {
        text-align: center;
        padding: 0 0 0 0;

        .m-card-profile__name {
          display: block;
          padding: 0 0 0 0;
          font-size: 1.5rem;
          font-weight: 500;
          color: #1b1c1e;
        }

        .m-card-profile__email {
          display: inline-block;
          padding: 6px 0 0 0;
          font-size: 1rem;
          color: #7b7e8a;
        }
      }
    }

    // 用户右侧信息-tab工具
    .m-portlet.m-portlet--tabs .m-portlet__head {
      padding: 0 2.2rem;
    }
    .m-portlet .m-portlet__head {
      display: flex;
      -webkit-box-orient: horizontal;
      -webkit-box-direction: normal;
      -ms-flex-direction: row;
      flex-direction: row;
      -webkit-box-align: stretch;
      -ms-flex-align: stretch;
      align-items: stretch;
      -webkit-box-pack: justify;
      -ms-flex-pack: justify;
      justify-content: space-between;
      height: 5.1rem;
      position: relative;

      border-bottom: 1px solid #ebedf2;

      .m-portlet__head-tools {
        display: flex;
        align-content: flex-end;
        padding: 0;
        -webkit-box-align: stretch;
        -ms-flex-align: stretch;
        align-items: stretch;

        .m-tabs-line {
          position: relative;
          margin: 0;
          border-bottom-color: transparent;
          bottom: -1px;
          display: flex;
          -webkit-box-align: stretch;
          -ms-flex-align: stretch;
          align-items: stretch;
          /*border-bottom: 1px solid #ebedf2;*/

          .m-tabs__item {
            display: flex;
            -webkit-box-align: stretch;
            -ms-flex-align: stretch;
            align-items: stretch;
            margin-right: 30px;
            margin-bottom: -1px;
          }
          a.m-tabs__link {
            display: flex;
            -webkit-box-align: center;
            -ms-flex-align: center;
            align-items: center;
            background: transparent;
            color: #7b7e8a;
            border: 0;
            border-bottom: 1px solid transparent;
            padding: 12px 0;
            font-size: 1.1rem;
          }
          a.m-tabs__link.active {
            background: transparent;
            color: #5867dd;
            border-bottom: 1px solid #5867dd;
           /* border-color: #dee2e6 #dee2e6 #fff;*/
          }
        }
        .m-tabs-line.m-tabs-line--left {
          float: left;
        }
      }
    }

    .m-tabs-line:before, .m-tabs-line:after {
      content: " ";
      display: table;
    }

    // 用户右侧信息-内容
    .m-portlet .m-form.m-form--fit>.m-portlet__body {
      padding-left: 0;
      padding-right: 0;
    }
    .m-form.m-form--fit .m-form__content, .m-form.m-form--fit .m-form__heading, .m-form.m-form--fit .m-form__group {
      margin-bottom: 0;
      padding: 15px 30px;
    }
    .m-form .form-control-label, .m-form .col-form-label, .m-form .m-form__group>label {
      text-align: right;
      color: #3f4047;
    }
    .m-form .form-control-label, .m-form label {
      font-weight: 400;
      font-size: 1rem;
    }
    .col-form-label {
      padding-top: calc(.85rem + 1px);
      padding-bottom: calc(.85rem + 1px);
      margin-bottom: 0;
      font-size: inherit;
      line-height: 1.25;
    }

    // 分隔符相关
    .m-form .m-form__seperator {
      display: block;
      height: 0;
    }
    .m-form .m-form__seperator.m-form__seperator--dashed {
      border-top: 1px dashed #ebedf2;
    }
    .m-form .m-form__seperator.m-form__seperator--space-2x {
      margin: 30px 0;
    }

    // 底部操作按钮
    .m-portlet .m-portlet__foot:not(.m-portlet__no-border) {
      border-top: 1px solid #ebedf2;
    }
    .m-portlet .m-portlet__foot.m-portlet__foot--fit {
      padding: 0 !important;
    }
    .m-form .m-form__actions {
      padding: 30px;
    }
    .btn:not(:disabled):not(.disabled) {
      cursor: pointer;
    }
    .btn.btn-accent {
      color: #fff;
      background-color: #00c5dc;
      border-color: #00c5dc;
    }
    .btn-accent:hover{
      color:#fff;
      background-color:#00a3b6;
      border-color:#0097a9;
    }
    .btn-accent:focus,.btn-accent.focus{
      -webkit-box-shadow:0 0 0 .2rem rgba(0,197,220,0.5);
      box-shadow:0 0 0 .2rem rgba(0,197,220,0.5)
    }

    .btn.m-btn--custom {
      padding: .75rem 2rem;
      font-size: 1rem;
      font-weight: 400;
      font-family: Poppins;
    }
    .btn.m-btn--air.btn-default, .btn.m-btn--air.btn-secondary {
      -webkit-box-shadow: 0 3px 20px 0 rgba(115, 76, 234, 0.17) !important;
      box-shadow: 0 3px 20px 0 rgba(115, 76, 234, 0.17) !important;
      border-color: #ffffff !important;
    }
    .btn-accent.m-btn--air {
      -webkit-box-shadow: 0 5px 10px 2px rgba(0,197,220,0.19) !important;
      box-shadow: 0 5px 10px 2px rgba(0,197,220,0.19) !important;
    }




</style>
