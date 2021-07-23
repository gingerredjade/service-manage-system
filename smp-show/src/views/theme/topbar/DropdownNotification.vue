<template>
  <div>
    <div
      class="kt-head kt-head--skin-dark kt-head--fit-x kt-head--fit-b"
      :style="{ backgroundImage: `url(${backgroundImage})` }"
    >
      <h3 class="kt-head__title">
        {{ $t('GUIDE.NOTIFICATION') }}
        <!--<span class="ml-2 btn btn-success btn-sm btn-bold btn-font-md">23 new</span>-->
      </h3>
      <ul
        class="nav nav-tabs nav-tabs-line nav-tabs-bold nav-tabs-line-3x nav-tabs-line-success kt-notification-item-padding-x"
        role="tablist"
      >
        <li class="nav-item">
          <a
            v-on:click="setActiveTab"
            data-tab="0"
            class="nav-link active"
            data-toggle="tab"
            href="#"
            role="tab"
            >{{ $t('MENU.USER.INFO') }}</a>
        </li>
        <!--<li class="nav-item">
          <a
            v-on:click="setActiveTab"
            data-tab="1"
            class="nav-link"
            data-toggle="tab"
            href="#"
            role="tab"
            >Events
          </a>
        </li>
        <li class="nav-item">
          <a
            v-on:click="setActiveTab"
            data-tab="2"
            class="nav-link"
            data-toggle="tab"
            href="#"
            role="tab"
            >Logs
          </a>
        </li>-->
      </ul>
    </div>

    <b-tabs class="kt-hide-tabs" v-model="tabIndex">
      <b-tab active>
        <div class="kt-grid-nav kt-grid-nav--skin-light">
          <div class="kt-grid-nav__row">
            <a href="#" class="kt-grid-nav__item" @click="openOnlinePage">
              <span class="kt-grid-nav__icon">
                <img
                  svg-inline
                  src="@/assets/media/icons/svg/Communication/Group.svg"
                  class="kt-svg-icon kt-svg-icon--success kt-svg-icon--lg"
                  alt=""
                />
              </span>
              <span class="kt-grid-nav__title">{{ onlineUser }}</span>
              <span class="kt-grid-nav__desc">{{ $t('MENU.USER.ONLINE_USER') }}</span>
            </a>
            <a href="#" class="kt-grid-nav__item">
              <span class="kt-grid-nav__icon">
                <img
                  svg-inline
                  src="@/assets/media/icons/svg/Communication/Mail-attachment.svg"
                  class="kt-svg-icon kt-svg-icon--success kt-svg-icon--lg"
                  alt=""
                />
              </span>
              <span class="kt-grid-nav__title">{{ accessTimes }}</span>
              <span class="kt-grid-nav__desc">{{ $t('MENU.USER.VISIT_TIMES') }}</span>
            </a>
          </div>
        </div>

      </b-tab>

      <!--<b-tab>
        <perfect-scrollbar
          class="kt-notification kt-margin-t-10 kt-margin-b-10"
          style="max-height: 40vh; position: relative;"
        >
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-psd kt-font-success"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New report has been received
              </div>
              <div class="kt-notification__item-time">23 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon-download-1 kt-font-danger"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                Finance report has been generated
              </div>
              <div class="kt-notification__item-time">25 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-line-chart kt-font-success"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New order has been received
              </div>
              <div class="kt-notification__item-time">2 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-box-1 kt-font-brand"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New customer is registered
              </div>
              <div class="kt-notification__item-time">3 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-chart2 kt-font-danger"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                Application has been approved
              </div>
              <div class="kt-notification__item-time">3 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-image-file kt-font-warning"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New file has been uploaded
              </div>
              <div class="kt-notification__item-time">5 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-bar-chart kt-font-info"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New user feedback received
              </div>
              <div class="kt-notification__item-time">8 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-pie-chart-2 kt-font-success"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                System reboot has been successfully completed
              </div>
              <div class="kt-notification__item-time">12 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-favourite kt-font-brand"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New order has been placed
              </div>
              <div class="kt-notification__item-time">15 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item kt-notification__item&#45;&#45;read">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-safe kt-font-primary"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                Company meeting canceled
              </div>
              <div class="kt-notification__item-time">19 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-psd kt-font-success"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New report has been received
              </div>
              <div class="kt-notification__item-time">23 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon-download-1 kt-font-danger"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                Finance report has been generated
              </div>
              <div class="kt-notification__item-time">25 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon-security kt-font-warning"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New customer comment recieved
              </div>
              <div class="kt-notification__item-time">2 days ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-pie-chart kt-font-success"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New customer is registered
              </div>
              <div class="kt-notification__item-time">3 days ago</div>
            </div>
          </a>
        </perfect-scrollbar>
      </b-tab>-->

      <!--<b-tab>
        <perfect-scrollbar
          class="kt-notification kt-margin-t-10 kt-margin-b-10"
          style="max-height: 40vh; position: relative;"
        >
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-psd kt-font-success"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New report has been received
              </div>
              <div class="kt-notification__item-time">23 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon-download-1 kt-font-danger"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                Finance report has been generated
              </div>
              <div class="kt-notification__item-time">25 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-line-chart kt-font-success"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New order has been received
              </div>
              <div class="kt-notification__item-time">2 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-box-1 kt-font-brand"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New customer is registered
              </div>
              <div class="kt-notification__item-time">3 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-chart2 kt-font-danger"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                Application has been approved
              </div>
              <div class="kt-notification__item-time">3 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-image-file kt-font-warning"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New file has been uploaded
              </div>
              <div class="kt-notification__item-time">5 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-bar-chart kt-font-info"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New user feedback received
              </div>
              <div class="kt-notification__item-time">8 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-pie-chart-2 kt-font-success"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                System reboot has been successfully completed
              </div>
              <div class="kt-notification__item-time">12 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-favourite kt-font-brand"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New order has been placed
              </div>
              <div class="kt-notification__item-time">15 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item kt-notification__item&#45;&#45;read">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-safe kt-font-primary"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                Company meeting canceled
              </div>
              <div class="kt-notification__item-time">19 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-psd kt-font-success"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New report has been received
              </div>
              <div class="kt-notification__item-time">23 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon-download-1 kt-font-danger"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                Finance report has been generated
              </div>
              <div class="kt-notification__item-time">25 hrs ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon-security kt-font-warning"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New customer comment recieved
              </div>
              <div class="kt-notification__item-time">2 days ago</div>
            </div>
          </a>
          <a href="#" class="kt-notification__item">
            <div class="kt-notification__item-icon">
              <i class="flaticon2-pie-chart kt-font-success"></i>
            </div>
            <div class="kt-notification__item-details">
              <div class="kt-notification__item-title">
                New customer is registered
              </div>
              <div class="kt-notification__item-time">3 days ago</div>
            </div>
          </a>
        </perfect-scrollbar>
      </b-tab>-->
    </b-tabs>
  </div>
</template>

<style lang="scss">
/* hide default vue-bootstrap tab links */
.kt-hide-tabs > div:not(.tab-content) {
  display: none;
}
</style>

<script>
export default {
  name: "KTDropdownNotification",
  data() {
    return {
      tabIndex: 0,

      onlineUser: 0,
      accessTimes: 0
    };
  },
  methods: {
    setActiveTab(event) {
      const tab = event.target.closest('[role="tablist"]');
      const links = tab.querySelectorAll(".nav-link");
      // remove active tab links
      for (let i = 0; i < links.length; i++) {
        links[i].classList.remove("active");
      }

      // set clicked tab index to bootstrap tab
      this.tabIndex = parseInt(event.target.getAttribute("data-tab"));

      // set current active tab
      event.target.classList.add("active");
    },
    // 获取在线用户数
    countOnlineUser() {
      let pageRequest = { pageNum: 1, pageSize: 10000000 };
      pageRequest.params = [{name:'status', value:'online'}];
      this.$api.loginlog.findPage(pageRequest).then((res) => {
        this.onlineUser = res.data.data.list.length;
      }).catch( () => {})
    },
    // 获取访问次数
    countAccessTimes() {
      let pageRequest = { pageNum: 1, pageSize: 10000000 };
      pageRequest.params = [{name:'status', value:'login'}];
      this.$api.loginlog.findPage(pageRequest).then((res) => {
        this.accessTimes = res.data.data.list.length + 1;
      }).catch( () => {})
    },
    openOnlinePage() {
      // 通过菜单URL跳转至指定路由
      this.$router.push('/sys/online');
    }
  },
  computed: {
    backgroundImage() {
      return process.env.BASE_URL + "assets/media/misc/bg-1.jpg";
    }
  },
  mounted() {
    this.countOnlineUser();
    this.countAccessTimes();
  }
};
</script>
