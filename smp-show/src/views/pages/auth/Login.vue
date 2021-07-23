<template>
  <div>
    <!--begin::Head-->
    <div class="kt-login__head">
      <span class="kt-login__signup-label">{{
        $t("AUTH.GENERAL.NO_ACCOUNT")
      }}</span>&nbsp;&nbsp;
      <router-link
        class="kt-link kt-login__signup-link"
        :to="{ name: 'register' }">
        {{ $t("AUTH.GENERAL.SIGNUP_BUTTON") }}
      </router-link>
    </div>
    <!--end::Head-->

    <!--begin::Body-->
    <div class="kt-login__body">
      <!--begin::Signin-->
      <div class="kt-login__form">
        <div class="kt-login__title">
          <h3>{{ $t('AUTH.LOGIN.BUTTON') }}</h3>
        </div>

        <!--begin::Form-->
        <b-form class="kt-form" @submit.stop.prevent="onSubmit">
          <div role="alert" class="alert alert-info">
            <div class="alert-text" v-if="this.$i18n.locale === 'ch'">
              使用 用户名 <strong>admin</strong> 和 密码
              <strong>admin</strong> 继续.
            </div>
            <div class="alert-text" v-else-if="this.$i18n.locale === 'en'">
              Use username <strong>admin</strong> and password
              <strong>admin</strong> to continue.
            </div>
            <div class="alert-text" v-else>
              使用 用户名 <strong>admin</strong> 和 密码
              <strong>admin</strong> 继续.
            </div>
          </div>

          <!--<div
            role="alert"
            v-bind:class="{ show: errors.length }"
            class="alert fade alert-danger">
            <div class="alert-text" v-for="(error, i) in errors" :key="i">
              {{ error }}
            </div>
          </div>-->

          <b-form-group
            id="input-group-account"
            :label="$t('AUTH.INPUT.USERNAME')"
            label-for="input-account" style="margin-bottom: 1.25rem;">
            <b-form-input
              id="input-account"
              name="input-account"
              v-model="$v.form.account.$model"
              :state="validateState('account')"
              aria-describedby="input-account-live-feedback"
              ref="input-account"
            ></b-form-input>

            <b-form-invalid-feedback id="input-account-live-feedback">
              <!--Account is required and a valid account.-->
              {{$t('AUTH.VALIDATION.INVALID', { name: this.$v.form.account.$model } )  }}
            </b-form-invalid-feedback>
          </b-form-group>

          <b-form-group
            id="input-group-password"
            :label="$t('AUTH.INPUT.PASSWORD')"
            label-for="input-password" style="margin-bottom: 1.25rem;">
            <b-form-input
              type="password"
              id="input-password"
              name="input-password"
              v-model="$v.form.password.$model"
              :state="validateState('password')"
              aria-describedby="input-password-live-feedback"></b-form-input>

            <b-form-invalid-feedback id="input-password-live-feedback">
              <!--Password is required.-->
              {{$t('AUTH.VALIDATION.INVALID', { name: this.$v.form.password.$model } )  }}
            </b-form-invalid-feedback>
          </b-form-group>

          <!--<b-form-group
            id="input-group-captcha"
            label="验证码"
            label-for="input-captcha" style="margin-bottom: 1.25rem;">
            <b-row>
              <b-col cols="6">
                <b-form-input
                  id="input-captcha"
                  name="input-captcha"
                  v-model="$v.form.captcha.$model"
                  :state="validateState('captcha')"
                  aria-describedby="input-captcha-live-feedback" placeholder="单击图片刷新"></b-form-input>

                <b-form-invalid-feedback id="input-captcha-live-feedback">
                  Captcha is required.
                </b-form-invalid-feedback>
              </b-col>
              <b-col cols="6">
                <b-img :src="form.src" alt="验证码" fluid
                       style="margin-top: 1.25rem;"
                       @click="refreshCaptcha"></b-img>
              </b-col>
            </b-row>
          </b-form-group>-->

          <!--begin::Action-->
          <div class="kt-login__actions">
            <b-button
              type="submit"
              id="kt_submit"
              class="btn btn-primary btn-elevate kt-login__btn-primary">
              {{ $t("AUTH.LOGIN.BUTTON") }}
            </b-button>
          </div>
          <!--end::Action-->
        </b-form>
        <!--end::Form-->

      </div>
      <!--end::Signin-->
    </div>
    <!--end::Body-->
  </div>
</template>

<style lang="scss" scoped>
.kt-spinner.kt-spinner--right:before {
  right: 8px;
}


</style>

<script>
import { mapState } from "vuex";
import { LOGIN, LOGOUT, SET_AUTH } from "@/store/auth.module";

import Cookies from "js-cookie";

import { validationMixin } from "vuelidate";
import { email, minLength, required } from "vuelidate/lib/validators";


export default {
  mixins: [validationMixin],
  name: "login",
  data() {
    return {
      loading: false,

      // Remove this dummy login info
      form: {
        account: "admin",
        password: "admin",
        captcha: '',
        src: ''
      }
    };
  },
  validations: {
    form: {
      account: {
        required,
        minLength: minLength(3)
      },
      password: {
        required,
        minLength: minLength(3)
      }/*,
      captcha: {
        required
      }*/
    }
  },
  methods: {
    validateState(name) {
      const { $dirty, $error } = this.$v.form[name];
      return $dirty ? !$error : null;
    },
    resetForm() {
      this.form = {
        account: null,
        password: null,
        captcha: '',
        src: ''
      };

      this.$nextTick(() => {
        this.$v.$reset();
      });
    },

    // 刷新验证码
    refreshCaptcha: function () {
      // this.form.src = API_URL + "/captcha.jpg?t=" + new Date().getTime();
      this.form.src = "/captcha.jpg?t=" + new Date().getTime();
    },

    onSubmit() {
      // 事后根据后台情况修改登录事件的具体处理,使用mango-ui的验证方式
      this.$v.form.$touch();
      if (this.$v.form.$anyError) {
        return;
      }

      const account = this.$v.form.account.$model;
      const password = this.$v.form.password.$model;
      //const captcha = this.$v.form.captcha.$model; // 或使用this.form.captcha;

      let userInfo = {
        account: account,
        password: password
        /*,captcha: captcha*/
      }

      // clear existing errors
      this.$store.dispatch(LOGOUT);

      // set spinner to submit button (旋转亮片)
      const submitButton = document.getElementById("kt_submit");
      submitButton.classList.add(
        "kt-spinner",
        "kt-spinner--light",
        "kt-spinner--right"
      );

      /**
       * Cookie：记录服务器和客户端会话状态的机制。
       *    服务器与浏览器间进行会话跟踪（知道谁在访问我）而维护的一个状态。
       *    这个状态用于告知服务端前后两个请求是否来自同一浏览器。
       * token：访问资源接口（API）时所需要的资源凭证
       * */
      // send login request (replace dummy delay)
      this.$api.login.login(userInfo).then( (res) => {
        if (res.data.code == 200) {
          //Cookies.set('token', res.data.data.token, 20); // 放置token到Cookie
          sessionStorage.setItem('user', userInfo.account); // 保存用户到本地会话(生命周期仅当前标签页)

          Cookies.set('username', account);
          this.$store.commit('menuRouteLoaded', false) // 要求重新加载导航菜单
          this.$store.dispatch(LOGIN, res.data.data)
            .then( () => {
              this.$router.push('/'); // 登录成功，跳转到主页
            });
        } else {
          this.$message({
            message: res.data.message,
            type: 'error'
          })
        }

        submitButton.classList.remove(
          "kt-spinner",
          "kt-spinner--light",
          "kt-spinner--right"
        );
      }).catch( (res) => {
        this.$message({
          message: res.message,
          type: 'error'
        })
      })
    }

  },
  mounted () {
    this.refreshCaptcha();
    //console.log(this.form.src)
  },
  computed: {
    ...mapState({
      errors: state => state.auth.errors
    }),
    backgroundImage() {
      return process.env.BASE_URL + "assets/media/bg/bg-4.jpg";
    }
  }
};
</script>
