<!-- 内部浏览：能浏览所有服务 -->
<template>
  <div>
    <div class="toolbar">
      <div class="">
        <el-form :inline="true" :model="filters" :size="size" ref="filters">
          <div class="toolbar-content">
            <div class="toolbar-content-button">
              <div class="">
                <div>
                  <el-form-item>
                    <k-t-button icon="fa fa-search" :label="$t('ACTION.SEARCH')" type="primary"
                                @click="findPage(null)"
                                perms="svc:view:view"
                    />
                  </el-form-item>
                </div>
                <div>
                  <el-form-item>
                    <k-t-button icon="fa fa-undo" :label="$t('ACTION.RESET')"
                                @click="resetForm('filters')"
                                perms="svc:view:view"
                    />
                  </el-form-item>
                </div>
              </div>
            </div>
            <div class="toolbar-content-condition">
              <div class="">
                <el-form-item prop="name">
                  <el-input v-model="filters.name" placeholder="服务名称"></el-input>
                </el-form-item>
                <el-form-item prop="alias">
                  <el-input v-model="filters.alias" placeholder="服务别名"></el-input>
                </el-form-item>
                <el-form-item prop="version">
                  <el-input v-model="filters.version" placeholder="服务版本"></el-input>
                </el-form-item>
                <el-form-item prop="deptName">
                  <el-input v-model="filters.deptName" placeholder="组织机构名称"></el-input>
                </el-form-item>
              </div>
              <div class="">
                <el-form-item prop="svcSubjectName">
                  <el-input v-model="filters.svcSubjectName" placeholder="服务主题名称"></el-input>
                </el-form-item>
                <el-form-item prop="svcTypeName">
                  <el-input v-model="filters.svcTypeName" placeholder="服务类型名称"></el-input>
                </el-form-item>
                <el-form-item prop="svcKeyword">
                  <el-input v-model="filters.svcKeyword" placeholder="服务关键字"></el-input>
                </el-form-item>
                <el-form-item prop="terminal">
                  <el-select v-model="filters.terminal" placeholder="选择终端类型">
                    <el-option
                      v-for="item in terminalOptions"
                      :key="item.value"
                      :label="item.text"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
            </div>

          </div>

        </el-form>
      </div>
      <div class="tool-refresh">
        <el-form :inline="true" :size="size">
          <el-form-item>
            <el-button-group>
              <el-tooltip :content="$t('ACTION.REFRESH')" placement="top">
                <!--<k-t-button icon="fa fa-spinner" label="刷新" @click="findSubjectPage(null)"/>-->
                <el-button icon="fa fa-refresh" @click="findPage(null)" :disabled="!hasPerms('svc:view:view')"/>
              </el-tooltip>
            </el-button-group>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="row">
      <div class="col-xl-12 col-lg-12 order-lg-3 order-xl-1">
        <KTPortlet v-bind:title="'服务信息'">
          <template v-slot:body>
            <div class="kt-svc"><!--后期可尝试增加滚动条-->
              <template v-for="(item, i) in pageResult.list">
                <div class="kt-svc__item" :key="`item-${i}`">
                  <div class="kt-svc__content">
                    <div class="kt-svc__pic">
                      <div v-if="item.isGisSvc===1" style="text-align:center;">地理信息服务</div>
                      <div v-else style="text-align:center;">非地理信息服务</div>
                      <el-image
                        v-if="item.thumbnails==null||item.thumbnails.length===0" :src="require('@/assets/media/products/product27.jpg')"
                        :preview-src-list="[require('@/assets/media/products/product27.jpg')]"
                        alt="默认图">
                      </el-image>
                      <!--<el-image
                        v-else :src="thumbnailBaseURI+item.thumbnails"
                        :preview-src-list="[thumbnailBaseURI+item.thumbnails]"
                        alt="缩略图">
                      </el-image>-->
                      <el-image
                        v-else :src="item.thumbnails"
                        :preview-src-list="[item.thumbnails]"
                        alt="缩略图">
                      </el-image>

                    </div>
                    <div class="kt-svc__content__info-wrapper">
                      <a href="#" class="kt-svc__title">
                        <span style="display: none;">{{ item.svcId }}</span>&nbsp;&nbsp;
                        <span class="">{{ item.svcName }}</span>
                      </a>
                      <p class="kt-svc__desc">{{ item.svcDesc }}</p>
                      <div class="kt-svc__info">
                        <!-- 服务基础信息 -->
                        <div class="row">
                          <div class="col-xl-12 text-ellipsis" :title="item.svcStyleDO.name">
                            <span>服务风格：</span>
                            <span class="kt-font-info">{{ item.svcStyleDO.name }}</span>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-xl-6 text-ellipsis" :title="item.svcAlias">
                            <span>服务别名：</span>
                            <span class="kt-font-info">{{ item.svcAlias==null||item.svcAlias===''?'无':item.svcAlias }}</span>
                          </div>
                          <div class="col-xl-6 text-ellipsis" :title="item.svcVersion">
                            <span>服务版本：</span>
                            <span class="kt-font-info">{{ item.svcVersion===''||item.svcVersion==null?'无':item.svcVersion }}</span>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-xl-6 text-ellipsis" :title="item.svcTypeDO.busSvcSubjectDO.name">
                            <span>服务主题：</span>
                            <span class="kt-font-info">{{ item.svcTypeDO.busSvcSubjectDO.name }}</span>
                          </div>
                          <div class="col-xl-6 text-ellipsis" :title="item.svcTypeDO.name">
                            <span>服务类型：</span>
                            <span class="kt-font-info">{{ item.svcTypeDO.name }}</span>
                          </div>
                        </div>
                        <div class="row">
                          <div style="display: none;">
                            <span>机构编号：</span>
                            <span class="kt-font-info">{{ item.sysDeptDO.id }}</span>
                          </div>
                          <div class="col-xl-12 text-ellipsis" :title="item.sysDeptDO.name">
                            <span>机构名称：</span>
                            <span class="kt-font-info">{{ item.sysDeptDO.name }}</span>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-xl-12 text-ellipsis" :title="item.svcKeyword">
                            <span>服务关键字：</span>
                            <span class="kt-font-info">{{ item.svcKeyword==null||item.svcKeyword===''?'无':item.svcKeyword }}</span>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-xl-12 text-ellipsis" :title="item.homePageUrl">
                            <span>服务主页地址：</span>
                            <span class="kt-font-info">
                              <a target="_blank" :href="item.homePageUrl">{{ item.homePageUrl==null||item.homePageUrl===''?'无':item.homePageUrl }}</a>
                            </span>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-xl-12 text-ellipsis" :title="item.interfaceSiteUrl">
                            <span>服务接口地址：</span>
                            <span class="kt-font-info">
                              <a target="_blank" :href="item.interfaceSiteUrl">{{ item.interfaceSiteUrl==null||item.interfaceSiteUrl===''?'无':item.interfaceSiteUrl }}</a>
                            </span>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-xl-12 text-ellipsis" :title="item.previewUrl">
                            <span>服务预览地址：</span>
                            <span class="kt-font-info">
                              <a target="_blank" :href="item.previewUrl">{{ item.previewUrl==null||item.previewUrl===''?'无':item.previewUrl }}</a>
                            </span>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-xl-12 text-ellipsis" :title="item.routingName">
                            <span>服务路由派发名：</span>
                            <span class="kt-font-info">{{ item.routingName==null||item.routingName===''?'无':item.routingName }}</span>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-xl-12 text-ellipsis" :title="item.releaseState">
                            <span>发布状态：</span>
                            <span class="kt-font-info" v-if="item.releaseState === 0">未发布</span>
                            <span class="kt-font-info" v-else-if="item.releaseState === 1">已发布</span>
                            <span class="kt-font-info" v-else>未知</span>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-xl-12 text-ellipsis" :title="item.auditState">
                            <span>审核状态：</span>
                            <span class="kt-font-info" v-if="item.auditState === 0">未审核</span>
                            <span class="kt-font-info" v-else-if="item.auditState === 1">审核通过</span>
                            <span class="kt-font-info" v-else-if="item.auditState === 2">审核拒绝</span>
                            <span class="kt-font-info" v-else>未知</span>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-xl-12 text-ellipsis" :title="item.auditOpinion">
                            <span>审核意见：</span>
                            <span class="kt-font-info">{{item.auditOpinion==null||item.auditOpinion===''?'无':item.auditOpinion}}</span>
                          </div>
                        </div>


                        <div v-if="item.isGisSvc===1">
                          <!-- 地理信息服务扩展信息 -->
                          <el-divider content-position="left">地理信息服务扩展信息</el-divider>
                          <div class="row">
                            <div class="col-xl-3 text-ellipsis" :title="item.svcCoverage"><span>覆盖范围：</span><span class="kt-font-info">{{ item.svcCoverage }}</span></div>
                            <div class="col-xl-3 text-ellipsis" :title="item.svcCoordinateSystem"><span>坐标系：</span><span class="kt-font-info">{{ item.svcCoordinateSystem }}</span></div>
                            <div class="col-xl-3 text-ellipsis" :title="item.svcProjectionType"><span>投影类型：</span><span class="kt-font-info">{{ item.svcProjectionType }}</span></div>
                            <div class="col-xl-3 text-ellipsis" :title="item.svcUpdateCycle"><span>更新周期：</span><span class="kt-font-info">{{ item.svcUpdateCycle }}</span></div>
                          </div>
                          <div class="row">
                            <div class="col-xl-3 text-ellipsis" :title="item.svcServiceArea"><span>服务范围：</span>
                              <span class="kt-font-info">{{ item.svcServiceArea==null||item.svcServiceArea===''?'无':item.svcServiceArea }}</span></div>
                          </div>

                          <!-- 地理信息服务图层信息 -->
                          <el-divider content-position="left">地理信息服务图层信息</el-divider>
                          <div class="row">
                            <div class="col-xl-3 text-ellipsis" :title="item.layerName">
                              <span>图层名称：</span><span class="kt-font-info">{{ item.layerName==null||item.layerName===''?'无':item.layerName }}</span>
                            </div>
                            <div class="col-xl-3 text-ellipsis" :title="item.layerKeyword">
                              <span>图层关键字：</span><span class="kt-font-info">{{ item.layerKeyword==null||item.layerKeyword===''?'无':item.layerKeyword }}</span>
                            </div>
                            <div class="col-xl-3 text-ellipsis" :title="item.layerCoordinateSystem"><span>图层坐标系：</span><span class="kt-font-info">{{ item.layerCoordinateSystem }}</span></div>
                            <div class="col-xl-3 text-ellipsis" :title="item.layerProjectionType"><span>图层投影类型：</span><span class="kt-font-info">{{ item.layerProjectionType }}</span></div>
                          </div>
                          <div class="row">
                            <div class="col-xl-3 text-ellipsis" :title="item.layerCoverage"><span>图层覆盖范围：</span><span class="kt-font-info">{{ item.layerCoverage }}</span></div>
                            <div class="col-xl-3 text-ellipsis" :title="item.layerServiceArea">
                              <span>图层数据范围：</span><span class="kt-font-info">{{ item.layerServiceArea==null||item.layerServiceArea===''?'无':item.layerServiceArea }}</span>
                            </div>
                            <div class="col-xl-3 text-ellipsis" :title="item.layerUpdateCycle"><span>图层更新周期：</span><span class="kt-font-info">{{ item.layerUpdateCycle}}</span></div>
                            <!--<div class="col-xl-3 text-ellipsis" :title="item.layerUpdateTime">
                              <span>图层最新更新时间：</span><span class="kt-font-info">{{ item.layerUpdateTime==null||item.layerUpdateTime===''?'无':item.layerUpdateTime }}</span>
                            </div>-->
                          </div>
                          <div class="row">
                            <div class="col-xl-12 text-ellipsis" :title="item.layerDesc">
                              <span>图层简介：</span><span class="kt-font-info">{{ item.layerDesc==null||item.layerDesc===''?'无':item.layerDesc }}</span>
                            </div>
                          </div>
                        </div>

                        <!-- 服务扩展信息 -->
                        <!--<div class="row">
                          <div class="col-xl-6">
                            <span>创建人：</span><span class="kt-font-info">{{ item.createBy }}</span>
                          </div>
                          <div class="col-xl-6">
                            <span>创建时间：</span><span class="kt-font-info">{{ item.createTime }}</span>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-xl-6">
                            <span>更新人：</span><span class="kt-font-info">{{ item.lastUpdateBy }}</span>
                          </div>
                          <div class="col-xl-6">
                            <span>更新时间：</span><span class="kt-font-info">{{ item.lastUpdateTime }}</span>
                          </div>
                        </div>-->

                      </div>
                    </div>
                  </div>
                  <!--<div class="kt-svc__content">
                    <div class="kt-kt-svc__stats">
                      <span class="kt-svc__number">19,200</span>
                      <span class="kt-widget5__sales">sales</span>
                    </div>
                    <div class="kt-kt-svc__stats">
                      <span class="kt-svc__number">1046</span>
                      <span class="kt-widget5__votes">votes</span>
                    </div>
                  </div>-->
                </div>
              </template>
            </div>
          </template>
        </KTPortlet>
      </div>
    </div>
    <div class="">
      <!--<div class="col-xl-12 col-lg-12 order-lg-3 order-xl-1">
        <k-t-button
          :size="size"
          type="danger"
          :label="$t('ACTION.BATCHDELETE', 'ch')"
          style="float:left;"
        >
        </k-t-button>
      </div>-->
      <div class="col-xl-12 col-lg-12 order-lg-3 order-xl-1">
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="refreshPageRequest"
          :current-page="pageRequest.pageNum"
          :page-sizes="[1, 3, 5, 10, 20, 30]"
          :page-size="pageRequest.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pageResult.totalRow"
          style="float:right;"
          :disabled="!hasPerms('svc:view:view')"
        >
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<style lang="scss">
  @import "~@a/custom/styles/_common.css";

  // 覆盖element ui divider内置的样式
  .el-divider--horizontal {
    background: none;
    border-color: #e8e8e8;
    border-style: dashed;
    border-width: 1px 0 0;
  }
  .el-divider--horizontal .el-divider__text.is-center {
    font-size: 12px;
    color: #74788d;
  }
  .el-divider--horizontal .el-divider__text.is-left {
    font-size: 12px;
    color: #74788d;
  }

</style>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';
  import KTPortlet from "@/views/partials/content/Portlet.vue";
  import KTButton from "@/components/KTButton.vue";
  import { hasPermission } from '@/utils/permission/index.js';

  export default {
    name: "ViewAll",
    components: {
      KTPortlet,
      KTButton
    },
    data() {
      return {
        size: 'small',
        currentPage: 1,

        pageRequest: {pageNum: 1, pageSize: 5},
        pageResult: {},

        editLoading: false,

        //thumbnailBaseURI: API_URL,

        // 筛选条件表单
        filters: {
          name: '', // 服务名称
          alias: '', // 服务别名
          svcSubjectName: '', // 服务主题名称
          svcTypeName: '', // 服务类型名称
          svcKeyword: '', // 服务关键字
          deptName: '', // 机构名称
          version: '', // 服务版本
          terminal: '' // 适用终端类型
        },

        // 终端类型
        terminalOptions: [
          { text: '通用', value: 1 },
          { text: 'Web端', value: 2 },
          { text: '桌面端', value: 3 }
        ],

      }
    },
    methods: {
      /** 权限控制相关-页面按钮权限 **/
      hasPerms: function (perms) {
        // 根据权限标识和外部指示状态进行权限判断
        // ('&'表示运算符按位与，都为1才为1否则为0。也类似于且，都真才为真)
        return hasPermission(perms) & !this.disabled
      },

      // 获取分页数据
      findPage: function (data) {
        if (data !== null) {
          this.pageRequest = data.pageRequest;
        }
        this.pageRequest.params = [
          {name: 'name', value: this.filters.name},
          {name: 'alias', value: this.filters.alias},
          {name: 'svcSubjectName', value: this.filters.svcSubjectName},
          {name: 'svcTypeName', value: this.filters.svcTypeName},
          {name: 'svcKeyword', value: this.filters.svcKeyword},
          {name: 'deptName', value: this.filters.deptName},
          {name: 'version', value: this.filters.version},
          {name: 'terminal', value: this.filters.terminal}
        ]
        this.$api.svc.findSvcPage(this.pageRequest).then((res) => {
          this.pageResult = res.data.data;
          //console.log(res.data.data)
        }).then(data!=null?data.callback:'').catch(()=>{})
      },
      // 换页刷新
      refreshPageRequest: function(pageNum) {
        this.pageRequest.pageNum = pageNum;
        this.findPage(null);
      },
      // 每页条目数调整刷新
      handleSizeChange(val) {
        //console.log(`每页 ${val} 条`);
        let params = Object.assign({}, this.pageRequest);
        params.pageSize = val;
        this.pageRequest.pageSize = val;
        this.findPage({"pageRequest": params});
      },
      handleCurrentChange(val) {
        //console.log(`当前页: ${val}`);
      },

      // 重置表单
      resetForm(formName) {
        this.$nextTick(()=>{
          this.$refs[formName].resetFields();
        })
      }
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "服务管理", route: "all" },
        { title: "服务浏览全部"}
      ]);

      // Load page content
      this.findPage(null);

    }
  }
</script>

<style scoped>
  div.toolbar {
    display: flex;
    display: -webkit-flex;
    align-items: center;
  }
  div.toolbar > div:last-child {
    margin-left: auto;
  }
  div.toolbar-content {
    display: flex;
  }
</style>
