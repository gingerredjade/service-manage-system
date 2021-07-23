<!-- 公开浏览：只能浏览已发布的服务 -->
<template>
  <div>
    <div class="hint-info"><!-- background-color: #d1ecf1; border-color: #bee5eb; color: #0c5460; -->
      <b-alert show variant="primary" dismissible
               style="background-color: rgb(209, 236, 241);
               border-color: rgb(190, 229, 235);
               color: rgb(12, 84, 96);
               padding: 6px 14px; margin: 0 auto;">
        <div>
          <i class="fa fa-info-circle"></i>
          <span class="hint-description"
                style="font-family: 微软雅黑; font-size: 12px;">
            允许浏览<span style="color: #007bff; font-weight: bold;">已发布服务</span>，
            新服务注册后须进行<span style="color: #dc3545 !important; font-weight: bold;">审核</span>
            &nbsp;<i class="fa fa-arrow-right" aria-hidden="true"></i>&nbsp;
            <span style="color: #dc3545 !important; font-weight: bold;">发布</span>流程。
          </span>
        </div>
      </b-alert>
    </div>

    <div class="kt-portlet">

      <div class="kt-portlet__body kt-portlet__body&#45;&#45;fit">

        <div class="toolbar">
          <div class="">
            <el-form :inline="true" :model="filters" :size="size" ref="filters">
              <div class="toolbar-content">
                <div class="toolbar-content-button">
                  <div class="">
                    <div>
                      <el-form-item>
                        <k-t-button icon="fa fa-search" :label="$t('ACTION.SEARCH')" type="primary"
                                    @click="findPage({ pageRequest: {pageNum: 1, pageSize: pageRequest.pageSize} })"
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
                  <el-tooltip :content="$t('ACTION.RESET')" placement="top">
                    <!--<k-t-button icon="fa fa-spinner" label="刷新" @click="findSubjectPage(null)"/>-->
                    <el-button icon="fa fa-refresh"
                               @click="findPage({ pageRequest: {pageNum: 1, pageSize: pageRequest.pageSize} })"
                               :disabled="!hasPerms('svc:view:view')"
                    />
                  </el-tooltip>
                </el-button-group>
              </el-form-item>
            </el-form>
          </div>
        </div>


        <!--卡片内容栏-->
        <div class="service-card-content">
          <el-table :data="pageResult.list" style="width: 100%" size="small" border
                    v-loading="loading" :element-loading-text="$t('ACTION.LOADING')" :highlight-current-row="true">
            <!-- 展开行内容 -->
            <el-table-column fixed="left" type="expand">
              <template slot-scope="props"><!--type="border-card"  value="baseInfo" -->
                <div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.svcStyleDO.name">
                      <span>服务风格：</span>
                      <span class="kt-font-info">{{ props.row.svcStyleDO.name }}</span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.svcAlias">
                      <span>服务别名：</span>
                      <span class="kt-font-info">{{ props.row.svcAlias }}</span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.svcVersion">
                      <span>服务版本：</span>
                      <span class="kt-font-info">{{ props.row.svcVersion }}</span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.svcKeyword">
                      <span>服务关键字：</span>
                      <span class="kt-font-info">{{ props.row.svcKeyword==null||props.row.svcKeyword===''?'无':props.row.svcKeyword }}</span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.homePageUrl">
                      <span>服务主页地址：</span>
                      <span class="kt-font-info">
                          <a target="_blank" :href="props.row.homePageUrl">{{ props.row.homePageUrl==null||props.row.homePageUrl===''?'无':props.row.homePageUrl }}</a>
                      </span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.interfaceSiteUrl">
                      <span>服务接口地址：</span>
                      <span class="kt-font-info">
                          <a target="_blank" :href="props.row.interfaceSiteUrl">{{ props.row.interfaceSiteUrl==null||props.row.interfaceSiteUrl===''?'无':props.row.interfaceSiteUrl }}</a>
                      </span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.previewUrl">
                      <span>服务预览地址：</span>
                      <span class="kt-font-info">
                          <a target="_blank" :href="props.row.previewUrl">{{ props.row.previewUrl==null||props.row.previewUrl===''?'无':props.row.previewUrl }}</a>
                      </span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.svcDesc">
                      <span>服务描述：</span>
                      <span class="kt-font-info">{{ props.row.svcDesc==null||props.row.svcDesc===''?'无':props.row.svcDesc }}</span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.routingName">
                      <span>服务路由派发名：</span>
                      <span class="kt-font-info">
                        {{ props.row.routingName==null||props.row.routingName===''?'无':props.row.routingName }}
                      </span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis">
                      <span>发布状态：</span>
                      <el-tag v-if="props.row.releaseState === 0" size="mini" effect="plain" type="">未发布</el-tag>
                      <el-tag v-else-if="props.row.releaseState === 1" size="mini" effect="plain" type="success">已发布</el-tag>
                      <el-tag v-else size="mini" effect="plain" type="info">未知</el-tag>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis">
                      <span>审核状态：</span>
                      <el-tag v-if="props.row.auditState === 0" size="mini" effect="plain" type="">未审核</el-tag>
                      <el-tag v-else-if="props.row.auditState === 1" size="mini" effect="plain" type="success">审核通过</el-tag>
                      <el-tag v-else-if="props.row.auditState === 2" size="mini" effect="plain" type="danger">审核拒绝</el-tag>
                      <el-tag v-else size="mini" effect="plain" type="info">未知</el-tag>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.auditOpinion">
                      <span>审核意见：</span>
                      <span class="kt-font-info">{{props.row.auditOpinion==null||props.row.auditOpinion===''?'无':props.row.auditOpinion}}</span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-3 text-ellipsis" :title="props.row.createBy"><span>创建人：</span><span class="kt-font-info">{{ props.row.createBy==null||props.row.createBy===''?'无':props.row.createBy }}</span></div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.createTime"><span>创建时间：</span><span class="kt-font-info">{{ props.row.createTime==null||props.row.createTime===''?'无':dateFormat(props.row.createTime) }}</span></div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.lastUpdateBy"><span>更新人：</span><span class="kt-font-info">{{ props.row.lastUpdateBy==null||props.row.lastUpdateBy===''?'无':props.row.lastUpdateBy }}</span></div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.lastUpdateTime"><span>更新时间：</span><span class="kt-font-info">{{ props.row.lastUpdateTime==null||props.row.lastUpdateTime===''?'无':dateFormat(props.row.lastUpdateTime) }}</span></div>
                  </div>
                </div>
                <div><!-- v-if="props.row.isGisSvc===1" -->
                  <el-divider content-position="left">服务扩展信息</el-divider><!-- 地理信息 -->
                  <div class="row">
                    <div class="col-xl-3 text-ellipsis" :title="props.row.svcCoverage"><span>覆盖范围：</span><span class="kt-font-info">{{ props.row.svcCoverage==null||props.row.svcCoverage===''?'无':props.row.svcCoverage }}</span></div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.svcCoordinateSystem"><span>坐标系：</span><span class="kt-font-info">{{ props.row.svcCoordinateSystem==null||props.row.svcCoordinateSystem===''?'无':props.row.svcCoordinateSystem }}</span></div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.svcProjectionType"><span>投影类型：</span><span class="kt-font-info">{{ props.row.svcProjectionType==null||props.row.svcProjectionType===''?'无':props.row.svcProjectionType }}</span></div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.svcUpdateCycle"><span>更新周期：</span><span class="kt-font-info">{{ props.row.svcUpdateCycle==null||props.row.svcUpdateCycle===''?'无':props.row.svcUpdateCycle }}</span></div>
                  </div>
                  <div class="row">
                    <div class="col-xl-3 text-ellipsis" :title="props.row.svcServiceArea"><span>服务范围：</span>
                      <span class="kt-font-info">{{ props.row.svcServiceArea==null||props.row.svcServiceArea===''?'无':props.row.svcServiceArea }}</span>
                    </div>
                  </div>
                </div>
                <div><!-- v-if="props.row.isGisSvc===1" -->
                  <el-divider content-position="left">服务图层信息</el-divider><!-- 地理信息 -->
                  <div class="row">
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerName">
                      <span>图层名称：</span>
                      <span class="kt-font-info">{{ props.row.layerName==null||props.row.layerName===''?'无':props.row.layerName }}</span>
                    </div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerKeyword">
                      <span>图层关键字：</span>
                      <span class="kt-font-info">{{ props.row.layerKeyword==null||props.row.layerKeyword===''?'无':props.row.layerKeyword }}</span>
                    </div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerCoordinateSystem">
                      <span>图层坐标系：</span>
                      <span class="kt-font-info">{{ props.row.layerCoordinateSystem==null||props.row.layerCoordinateSystem===''?'无':props.row.layerCoordinateSystem }}</span></div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerProjectionType">
                      <span>图层投影类型：</span>
                      <span class="kt-font-info">{{ props.row.layerProjectionType==null||props.row.layerProjectionType===''?'无':props.row.layerProjectionType }}</span></div>
                  </div>
                  <div class="row">
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerCoverage">
                      <span>图层覆盖范围：</span>
                      <span class="kt-font-info">{{ props.row.layerCoverage==null||props.row.layerCoverage===''?'无':props.row.layerCoverage }}</span></div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerServiceArea">
                      <span>图层数据范围：</span>
                      <span class="kt-font-info">{{ props.row.layerServiceArea==null||props.row.layerServiceArea===''?'无':props.row.layerServiceArea }}</span>
                    </div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerUpdateCycle">
                      <span>图层更新周期：</span>
                      <span class="kt-font-info">{{ props.row.layerUpdateCycle==null||props.row.layerUpdateCycle===''?'无':props.row.layerUpdateCycle}}</span>
                    </div>
                    <!--<div class="col-xl-3 text-ellipsis" :title="props.row.layerUpdateTime">
                      <span>图层最新更新时间：</span>
                      <span class="kt-font-info">{{ props.row.layerUpdateTime==null||props.row.layerUpdateTime===''?'无':props.row.layerUpdateTime }}</span>
                    </div>-->
                  </div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.layerDesc">
                      <span>图层简介：</span>
                      <span class="kt-font-info">{{ props.row.layerDesc==null||props.row.layerDesc===''?'无':props.row.layerDesc }}</span>
                    </div>
                  </div>
                </div>

              </template>
            </el-table-column>
            <!-- 非展开行内容 -->
            <el-table-column label="服务名称" prop="svcName" header-align="center" align="center" width="210"></el-table-column>
            <!--<el-table-column label="服务别名" prop="svcAlias" header-align="center" align="center" width="180"></el-table-column>
            <el-table-column label="服务版本" prop="svcVersion" header-align="center" align="center" width="180"></el-table-column>-->
            <el-table-column label="服务类型" prop="svcVersion" header-align="center" align="center" width="210">
              <template slot-scope="scope">
                {{ scope.row.svcTypeDO.name }}
              </template>
            </el-table-column>
            <el-table-column label="服务主题" prop="svcVersion" header-align="center" align="center" width="210">
              <template slot-scope="scope">
                {{ scope.row.svcTypeDO.busSvcSubjectDO.name }}
              </template>
            </el-table-column>
            <el-table-column label="机构名称" prop="svcVersion" header-align="center" align="center" width="210">
              <template slot-scope="scope">
                {{ scope.row.sysDeptDO.name }}
              </template>
            </el-table-column>
            <el-table-column label="适用终端类型" prop="terminal" header-align="center" align="center" width="100">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.terminal === 1" size="mini" effect="plain" type="success">通用</el-tag>
                <el-tag v-else-if="scope.row.terminal === 2" size="mini" effect="plain" type="">Web端</el-tag>
                <el-tag v-else-if="scope.row.terminal === 3" size="mini" effect="plain" type="info">桌面端</el-tag>
                <el-tag v-else size="mini" effect="plain" type="danger">未知</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="地理信息服务" prop="isGisSvc" header-align="center" align="center">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.isGisSvc === 1" size="mini" effect="plain" type="">是</el-tag>
                <el-tag v-else size="mini" effect="plain" type="info">否</el-tag>
              </template>
            </el-table-column>

            <el-table-column
              fixed="right" header-align="center" align="center" width="185" :label="$t('ACTION.OPERATION')">
              <template slot-scope="scope">
                <k-t-button icon="fa fa-edit" :label="$t('ACTION.EDIT')" perms="svc:view:edit" @click="handleEdit(scope.$index, scope.row)"/>
                <k-t-button icon="fa fa-trash" :label="$t('ACTION.DELETE')" perms="svc:view:delete" type="danger" @click="handleDelete(scope.row)"/>
              </template>
            </el-table-column>
          </el-table>
        </div>

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
          :page-sizes="[1, 3, 5, 8, 10, 20, 30]"
          :page-size="pageRequest.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pageResult.totalRow"
          style="float:right;"
          :disabled="!hasPerms('svc:view:view')"
        >
        </el-pagination>
      </div>
    </div>

    <!-- begin::修改界面 :append-to-body=true -->
    <el-drawer :visible.sync="drawerVisible"
               :append-to-body="true"
               custom-class="service-edit-drawer"
               direction="rtl"
               size="80%"
               ref="drawer" @close="handleCloseDrawer()">
      <div
        class="kt-grid  kt-wizard-v2 kt-wizard-v2--white"
        id="kt_wizard_v2"
        data-ktwizard-state="step-first">
        <div class="kt-grid__item kt-wizard-v2__aside">
          <!--begin: Form Wizard Nav -->
          <div class="kt-wizard-v2__nav">
            <div
              class="kt-wizard-v2__nav-items kt-wizard-v2__nav-items--clickable">
              <!--doc: Replace A tag with SPAN tag to disable the step link click -->
              <div
                class="kt-wizard-v2__nav-item"
                data-ktwizard-type="step"
                data-ktwizard-state="current">
                <div class="kt-wizard-v2__nav-body">
                  <div class="kt-wizard-v2__nav-icon">
                    <i class="flaticon-globe"></i>
                  </div>
                  <div class="kt-wizard-v2__nav-label">
                    <div class="kt-wizard-v2__nav-label-title">
                      服务基本信息
                    </div>
                    <div class="kt-wizard-v2__nav-label-desc">
                      编辑你的服务基本信息，该类均需填写
                    </div>
                  </div>
                </div>
              </div>
              <div class="kt-wizard-v2__nav-item" data-ktwizard-type="step">
                <div class="kt-wizard-v2__nav-body">
                  <div class="kt-wizard-v2__nav-icon">
                    <i class="flaticon-bus-stop"></i>
                  </div>
                  <div class="kt-wizard-v2__nav-label">
                    <div class="kt-wizard-v2__nav-label-title">
                      服务扩展信息
                    </div>
                    <div class="kt-wizard-v2__nav-label-desc">
                      个性化服务信息，地理信息服务扩展信息
                    </div>
                  </div>
                </div>
              </div>
              <div
                class="kt-wizard-v2__nav-item"
                href="#"
                data-ktwizard-type="step">
                <div class="kt-wizard-v2__nav-body">
                  <div class="kt-wizard-v2__nav-icon">
                    <i class="flaticon-responsive"></i>
                  </div>
                  <div class="kt-wizard-v2__nav-label">
                    <div class="kt-wizard-v2__nav-label-title">
                      服务对应图层信息
                    </div>
                    <div class="kt-wizard-v2__nav-label-desc">
                      个性化服务信息，地理信息服务图层信息
                    </div>
                  </div>
                </div>
              </div>
              <div class="kt-wizard-v2__nav-item" data-ktwizard-type="step">
                <div class="kt-wizard-v2__nav-body">
                  <div class="kt-wizard-v2__nav-icon">
                    <i class="flaticon-confetti"></i>
                  </div>
                  <div class="kt-wizard-v2__nav-label">
                    <div class="kt-wizard-v2__nav-label-title">
                      完成!
                    </div>
                    <div class="kt-wizard-v2__nav-label-desc">
                      检验并提交
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!--end: Form Wizard Nav -->
        </div>
        <div class="kt-grid__item kt-grid__item--fluid kt-wizard-v2__wrapper">
          <!--begin: Form Wizard Form-->
          <form class="kt-form" id="svcDataForm" :model="svcDataForm" ref="svcDataForm">
            <!--begin: Form Wizard Step 1-->
            <div
              class="kt-wizard-v2__content"
              data-ktwizard-type="step-content"
              data-ktwizard-state="current">
              <div class="kt-heading kt-heading--md">编辑服务基本信息</div>
              <div class="kt-form__section kt-form__section--first">
                <div class="kt-wizard-v2__form">
                  <!--<div class="form-group">
                    <el-upload
                      class="upload-demo"
                      action="#"
                      :on-preview="handlePreview"
                      :on-remove="handleRemove"
                      :before-remove="beforeRemove"
                      multiple
                      :limit="1"
                      :on-exceed="handleExceed"
                      :file-list="thumbnailFileList"
                      accept="image/*"
                      :on-change="handleChange"
                      :auto-upload="false"
                      ref="upload">
                      <el-button size="small" type="primary">点击上传缩略图</el-button><span class="must-fill-in">*</span>
                      <div slot="tip" class="el-upload__tip">只能上传图片文件</div>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible">
                      <img width="100%" :src="dialogImageUrl" alt="">
                    </el-dialog>
                  </div>-->
                  <div class="form-group">
                    <el-switch
                      style="display: block"
                      v-model="svcDataForm.isGisSvc"
                      active-text="地理信息服务"
                      inactive-text="非地理信息服务"
                      :active-value="1"
                      :inactive-value="0"
                    >
                    </el-switch>
                  </div>
                  <div class="form-group">
                    <label>服务适用终端类型<span class="must-fill-in">*</span></label>
                    <b-form-radio-group
                      v-model="svcDataForm.terminal"
                      :options="terminalOptions"
                      name="radio-inline"
                    ></b-form-radio-group>
                  </div>
                  <div class="form-group">
                    <label>服务风格<span class="must-fill-in">*</span></label>
                    <select name="svcStyle" class="form-control"
                            v-model="svcDataForm.svcStyleId"
                            id="svcStyle"
                            @blur="formCheck().svcStyleVerify()"><!--  -->
                      <option disabled value="">请选择</option>
                      <option v-for="(item, index) in svcStyleSelectData" :value="item.styleId">{{item.name}}</option>
                    </select>
                  </div>
                  <div class="form-group">
                    <label>服务所属机构<span class="must-fill-in">*</span></label>
                    <popup-tree-input
                      :data="popupTreeData" :props="popupTreeProps"
                      :prop="deptDataForm.parentName==null?'顶级菜单':deptDataForm.parentName"
                      :nodeKey="''+deptDataForm.parentId"
                      :currentChangeHandle="handleTreeSelectChange"
                      :id="'deptName'"
                      :blur="formCheck().svcDeptVerify"
                    >
                    </popup-tree-input>
                  </div>
                  <div class="row">
                    <div class="col-xl-6">
                      <div class="form-group">
                        <label>服务主题<span class="must-fill-in">*</span></label>
                        <select name="svcSubject" class="form-control"
                                v-model="svcDataForm.svcSubjectId"
                                @change="getSvcTypeData($event)"
                                id="svcSubject"
                                @blur="formCheck().svcSubjectVerify()">
                          <option disabled value="">请选择</option>
                          <option v-for="(item, index) in svcSubjectSelectData" :value="item.subjectId">{{item.name}}</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-xl-6">
                      <div class="form-group">
                        <label>服务类型<span class="must-fill-in">*</span></label>
                        <select name="subtype" class="form-control"
                                v-model="svcDataForm.svcTypeId"
                                id="svcType"
                                @blur="formCheck().svcTypeVerify()">
                          <option disabled value="">请选择</option>
                          <option v-for="(item, index) in svcTypeSelectData" :value="item.typeId">{{item.name}}</option>
                        </select>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-6">
                      <div class="form-group">
                        <label>服务名称<span class="must-fill-in">*</span></label>
                        <input
                          type="text"
                          class="form-control"
                          name="fname"
                          placeholder=""
                          value=""
                          v-model="svcDataForm.svcName" id="svcName" @blur="formCheck().svcNameVerify()"/>
                        <span class="form-text text-muted">{{ VMsgBox.SvcVMsgBox.svcNameTip }}</span>
                      </div>
                    </div>
                    <div class="col-xl-6">
                      <div class="form-group">
                        <label>服务别名<span class="must-fill-in">*</span></label>
                        <input
                          type="text"
                          class="form-control"
                          name="fname"
                          placeholder=""
                          value=""
                          v-model="svcDataForm.svcAlias" id="svcAlias"
                          @blur="formCheck().svcAliasVerify()"/>
                        <span class="form-text text-muted">{{ VMsgBox.SvcVMsgBox.svcAliasTip }}</span>
                      </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <label>服务版本<span class="must-fill-in">*</span></label>
                    <input
                      type="text"
                      class="form-control"
                      name="version"
                      placeholder=""
                      value="1000"
                      v-model="svcDataForm.svcVersion"
                      id="svcVersion"
                      @blur="formCheck().svcVersionVerify()"/>
                    <span class="form-text text-muted">{{ VMsgBox.SvcVMsgBox.svcVersionTip }}</span>
                  </div>
                  <div class="form-group">
                    <label>服务描述/简介<span class="must-fill-in">*</span></label>
                    <b-container fluid>
                      <b-row>
                        <b-col sm="12">
                          <b-form-textarea
                            id="svcDesc"
                            placeholder=""
                            rows="3"
                            max-rows="8"
                            v-model="svcDataForm.svcDesc"
                            @blur="formCheck().svcDescVerify()"
                          ></b-form-textarea>
                        </b-col>
                      </b-row>
                    </b-container>
                    <span class="form-text text-muted">{{ VMsgBox.SvcVMsgBox.svcDescTip }}</span>
                  </div>
                  <div class="form-group">
                    <label>关键字<span class="must-fill-in">*</span></label>
                    <input
                      type="text"
                      class="form-control"
                      name="KeyWord"
                      placeholder="keyword1,keyword2..."
                      value=""
                      v-model="svcDataForm.svcKeyword" id="svcKeyword"
                      @blur="formCheck().svcKeywordVerify()"/>
                    <span class="form-text text-muted">{{ VMsgBox.SvcVMsgBox.svcKeywordTip }}</span>
                  </div>
                  <div class="form-group">
                    <label>服务主页URL</label>
                    <input
                      type="text"
                      class="form-control"
                      name="homeUrl"
                      placeholder="http://..."
                      value=""
                      v-model="svcDataForm.homePageUrl"/>
                    <span class="form-text text-muted">请输入服务的主页URL.</span>
                  </div>
                  <div class="form-group">
                    <label>服务接口网站URL</label>
                    <input
                      type="text"
                      class="form-control"
                      name="interfaceUrl"
                      placeholder="http://..."
                      value=""
                      v-model="svcDataForm.interfaceSiteUrl"/>
                    <span class="form-text text-muted">请输入服务的接口网站URL.如,swagger-ui接口网站.</span>
                  </div>
                  <div class="form-group">
                    <label>服务预览URL</label>
                    <input
                      type="text"
                      class="form-control"
                      name="previewUrl"
                      placeholder="http://..."
                      value=""
                      v-model="svcDataForm.previewUrl" />
                    <span class="form-text text-muted">请输入服务预览URL.</span>
                  </div>
                  <div class="row">
                    <div class="col-xl-12">
                      <div class="form-group">
                        <label>服务路由指派名</label>
                        <div>
                          <input
                            type="text"
                            class="form-control routingName-input"
                            name="routingName"
                            placeholder=""
                            value=""
                            v-model="routingNameFirst"/>
                          <span class="routingName-add-icon" @click="addRoutingNameItem()">
                              <i class="fa fa-plus-circle fa-lg" aria-hidden="true"></i>
                          </span>
                        </div>
                        <!-- 动态添加参数 -->
                        <div class="routingName-add-content" v-for="(item, index) in routingNameParams" :key="index">
                          <input
                            type="text"
                            class="form-control routingName-input"
                            name="routingName"
                            placeholder=""
                            value=""
                            v-model="item.routingName"/>
                          <span class="routingName-add-icon" @click="minusRoutingNameItem(item)">
                              <i class="fa fa-minus-circle fa-lg" aria-hidden="true"></i>
                          </span>
                        </div>

                        <span class="form-text text-muted">请输入服务路由指派名.</span>
                      </div>
                    </div>
                  </div>

                </div>
              </div>
            </div>
            <!--end: Form Wizard Step 1-->

            <!--begin: Form Wizard Step 2-->
            <div
              class="kt-wizard-v2__content"
              data-ktwizard-type="step-content">
              <div class="kt-heading kt-heading--md">编辑服务扩展信息</div><!-- 地理信息 -->
              <div class="kt-form__section kt-form__section--first">
                <div class="kt-wizard-v2__form">
                  <div class="row">
                    <div class="col-xl-4">
                      <div class="form-group">
                        <label>覆盖范围:</label>
                        <select name="svcCoverage" class="form-control"
                                v-model="svcDataForm.svcCoverage" id="svcCoverage"
                                @blur="svcDataForm.isGisSvc==1?formCheck().svcCoverageVerify():''">
                          <option value="GLOBAL">全球</option>
                          <option value="CHINA">中国</option>
                          <option value="PROVINCE">省</option>
                          <option value="CITY">市</option>
                          <option value="DISTRICTANDCOUNTRY">区县</option>
                          <option value="TOWNS">乡镇</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-xl-8">
                      <div class="form-group">
                        <label>服务范围</label>
                        <!--  @blur="svcDataForm.isGisSvc==1?formCheck().svcServiceAreaVerify():''" -->
                        <input
                          type="text"
                          class="form-control"
                          name="serviceScope"
                          placeholder=""
                          value=""
                          v-model="svcDataForm.svcServiceArea" id="svcServiceArea"
                          @blur="formCheck().svcServiceAreaVerify('svcServiceArea')" />
                        <span class="form-text text-muted">
                          <b>左上经度,左上纬度,右下经度,右下纬度</b>。<br />
                          <b>单位为度</b>，中间使用<b>英文逗号分隔</b>。<br />
                          经度：整数部分为0-180、小数部分为0到6位；<br />
                          纬度：整数部分为0-90、小数部分为0到6位。<br />
                          如：<br />若是：110°46′12″,39°4′42″,121°36′17″,24°13′12″，<br />则应输入：<b>110.77,39.078333,121.604722,24.22</b>。
                        </span>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-xl-4">
                      <div class="form-group">
                        <label>坐标系:</label>
                        <select name="spatialReference" class="form-control"
                                v-model="svcDataForm.svcCoordinateSystem" id="svcCoordinateSystem"
                                @blur="svcDataForm.isGisSvc==1?formCheck().svcCoordinateSystemVerify():''">
                          <option value="CGCS2000">CGCS2000</option>
                          <option value="WGS84">WGS84</option>
                          <option value="OTHER">其他</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-xl-4">
                      <div class="form-group">
                        <label>投影类型:</label>
                        <select name="projectionType" class="form-control"
                                v-model="svcDataForm.svcProjectionType" id="svcProjectionType"
                                @blur="svcDataForm.isGisSvc==1?formCheck().svcProjectionTypeVerify():''">
                          <option value="LONLAT">经纬度投影</option>
                          <option value="MERCATOR">球面墨卡托投影</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-xl-4">
                      <div class="form-group">
                        <label>更新周期:</label>
                        <select name="refreshCycle" class="form-control"
                                v-model="svcDataForm.svcUpdateCycle" id="svcUpdateCycle"
                                @blur="svcDataForm.isGisSvc==1?formCheck().svcUpdateCycleVerify():''">
                          <option value="ONEYEARMORE">大于1年</option>
                          <option value="ONEYEAR">1年</option>
                          <option value="TRUETIME">实时</option>
                          <option value="OTHER">其他</option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!--end: Form Wizard Step 2-->

            <!--begin: Form Wizard Step 3-->
            <div
              class="kt-wizard-v2__content"
              data-ktwizard-type="step-content">
              <div class="kt-heading kt-heading--md">编辑服务图层基本信息</div><!-- 地理信息 -->
              <div class="kt-form__section kt-form__section--first">
                <div class="kt-wizard-v2__form">
                  <div class="row">
                    <div class="col-xl-6">
                      <div class="form-group">
                        <label>图层名称</label>
                        <input
                          type="text"
                          class="form-control"
                          name="layerName"
                          placeholder=""
                          value=""
                          v-model="svcDataForm.layerName" id="layerName"/>
                        <span class="form-text text-muted">请输入图层名称.</span>
                      </div>
                    </div>
                    <div class="col-xl-6">
                      <div class="form-group">
                        <label>图层关键字</label>
                        <input
                          type="text"
                          class="form-control"
                          name="KeyWord"
                          placeholder="keyword1,keyword2..."
                          value=""
                          v-model="svcDataForm.layerKeyword" id="layerKeyword"/>
                        <span class="form-text text-muted">请输入图层关键字.</span>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-6">
                      <div class="form-group">
                        <label>图层覆盖范围:</label>
                        <select name="svcCoverage" class="form-control"
                                v-model="svcDataForm.layerCoverage" id="layerCoverage">
                          <option value="GLOBAL">全球</option>
                          <option value="CHINA">中国</option>
                          <option value="PROVINCE">省</option>
                          <option value="CITY">市</option>
                          <option value="DISTRICTANDCOUNTRY">区县</option>
                          <option value="TOWNS">乡镇</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-xl-6">
                      <div class="form-group">
                        <label>图层数据范围</label>
                        <input
                          type="text"
                          class="form-control"
                          name="serviceScope"
                          placeholder=""
                          value=""
                          v-model="svcDataForm.layerServiceArea" id="layerServiceArea"
                          @blur="formCheck().layerServiceAreaVerify('layerServiceArea')"/>
                        <span class="form-text text-muted">
                          <b>左上经度,左上纬度,右下经度,右下纬度</b>。<br />
                          <b>单位为度</b>，中间使用<b>英文逗号分隔</b>。<br />
                          经度：整数部分为0-180、小数部分为0到6位；<br />
                          纬度：整数部分为0-90、小数部分为0到6位。<br />
                          如：<br />若是：110°46′12″,39°4′42″,121°36′17″,24°13′12″，<br />则应输入：<b>110.77,39.078333,121.604722,24.22</b>。
                        </span>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-4">
                      <div class="form-group">
                        <label>图层坐标系:</label>
                        <select name="spatialReference" class="form-control"
                                v-model="svcDataForm.layerCoordinateSystem" id="layerCoordinateSystem">
                          <option value="CGCS2000">CGCS2000</option>
                          <option value="WGS84">WGS84</option>
                          <option value="OTHER">其他</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-xl-4">
                      <div class="form-group">
                        <label>图层投影类型:</label>
                        <select name="projectionType" class="form-control"
                                v-model="svcDataForm.layerProjectionType" id="layerProjectionType">
                          <option value="LONLAT">经纬度投影</option>
                          <option value="MERCATOR">球面墨卡托投影</option>
                        </select>
                      </div>
                    </div>
                    <div class="col-xl-4">
                      <div class="form-group">
                        <label>图层更新周期:</label>
                        <select name="refreshCycle" class="form-control"
                                v-model="svcDataForm.layerUpdateCycle" id="layerUpdateCycle">
                          <option value="ONEYEARMORE">大于1年</option>
                          <option value="ONEYEAR">1年</option>
                          <option value="TRUETIME">实时</option>
                          <option value="OTHER">其他</option>
                        </select>
                      </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <label>图层描述/简介</label>
                    <b-container fluid>
                      <b-row>
                        <b-col sm="12">
                          <b-form-textarea
                            id="textarea-auto-height"
                            placeholder=""
                            rows="3"
                            max-rows="8"
                            v-model="svcDataForm.layerDesc"
                          ></b-form-textarea>
                        </b-col>
                      </b-row>
                    </b-container>
                    <span class="form-text text-muted">请输入图层的简介信息.</span>
                  </div>
                </div>
              </div>
            </div>
            <!--end: Form Wizard Step 3-->

            <!--begin: Form Wizard Step 4-->
            <!-- <div
               class="kt-wizard-v2__content"
               data-ktwizard-type="step-content">
               <div class="kt-heading kt-heading&#45;&#45;md">Setup Your Delivery Location</div>
               <div class="kt-form__section kt-form__section&#45;&#45;first">
                 <div class="kt-wizard-v2__form">
                   <div class="row">
                     <div class="col-xl-6">
                       <div class="form-group">
                         <label>Address Line 1</label>
                         <input
                           type="text"
                           class="form-control"
                           name="locaddress1"
                           placeholder="Address Line 1"
                           value="Address Line 1"/>
                         <span class="form-text text-muted">Please enter your Address.</span>
                       </div>
                     </div>
                     <div class="col-xl-6">
                       <div class="form-group">
                         <label>Address Line 2</label>
                         <input
                           type="text"
                           class="form-control"
                           name="locaddress2"
                           placeholder="Address Line 2"
                           value="Address Line 2"/>
                         <span class="form-text text-muted">Please enter your Address.</span>
                       </div>
                     </div>
                   </div>
                   <div class="row">
                     <div class="col-xl-6">
                       <div class="form-group">
                         <label>Postcode</label>
                         <input
                           type="text"
                           class="form-control"
                           name="locpostcode"
                           placeholder="Postcode"
                           value="3072"/>
                         <span class="form-text text-muted">Please enter your Postcode.</span>
                       </div>
                     </div>
                     <div class="col-xl-6">
                       <div class="form-group">
                         <label>City</label>
                         <input
                           type="text"
                           class="form-control"
                           name="loccity"
                           placeholder="City"
                           value="Preston"/>
                         <span class="form-text text-muted">Please enter your City.</span>
                       </div>
                     </div>
                   </div>
                   <div class="row">
                     <div class="col-xl-6">
                       <div class="form-group">
                         <label>State</label>
                         <input
                           type="text"
                           class="form-control"
                           name="locstate"
                           placeholder="State"
                           value="VIC"/>
                         <span class="form-text text-muted">Please enter your Postcode.</span>
                       </div>
                     </div>
                     <div class="col-xl-6">
                       <div class="form-group">
                         <label>Country:</label>
                         <select name="loccountry" class="form-control">
                           <option value="">Select</option>
                           <option value="AF">Afghanistan</option>
                           <option value="AX">Åland Islands</option>
                         </select>
                       </div>
                     </div>
                   </div>
                 </div>
               </div>
             </div>-->
            <!--end: Form Wizard Step 4-->

            <!--begin: Form Wizard Step 5-->
            <!--<div
              class="kt-wizard-v2__content"
              data-ktwizard-type="step-content">
              <div class="kt-heading kt-heading&#45;&#45;md">Enter your Payment Details</div>
              <div class="kt-form__section kt-form__section&#45;&#45;first">
                <div class="kt-wizard-v2__form">
                  <div class="row">
                    <div class="col-xl-6">
                      <div class="form-group">
                        <label>Name on Card</label>
                        <input
                          type="text"
                          class="form-control"
                          name="ccname"
                          placeholder="Card Name"
                          value="John Wick"/>
                        <span class="form-text text-muted">Please enter your Card Name.</span>
                      </div>
                    </div>
                    <div class="col-xl-6">
                      <div class="form-group">
                        <label>Card Number</label>
                        <input
                          type="text"
                          class="form-control"
                          name="ccnumber"
                          placeholder="Card Number"
                          value="4444 3333 2222 1111"/>
                        <span class="form-text text-muted">Please enter your Address.</span>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-4">
                      <div class="form-group">
                        <label>Card Expiry Month</label>
                        <input
                          type="number"
                          class="form-control"
                          name="ccmonth"
                          placeholder="Card Expiry Month"
                          value="01"/>
                        <span class="form-text text-muted">Please enter your Card Expiry Month.</span>
                      </div>
                    </div>
                    <div class="col-xl-4">
                      <div class="form-group">
                        <label>Card Expiry Year</label>
                        <input
                          type="number"
                          class="form-control"
                          name="ccyear"
                          placeholder="Card Expire Year"
                          value="21"/>
                        <span class="form-text text-muted">Please enter your Card Expiry Year.</span>
                      </div>
                    </div>
                    <div class="col-xl-4">
                      <div class="form-group">
                        <label>Card CVV Number</label>
                        <input
                          type="password"
                          class="form-control"
                          name="cccvv"
                          placeholder="Card CVV Number"
                          value="123"/>
                        <span class="form-text text-muted">Please enter your Card CVV Number.</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>-->
            <!--end: Form Wizard Step 5-->

            <!--begin: Form Wizard Step 6-->
            <!-- <div
               class="kt-wizard-v2__content"
               data-ktwizard-type="step-content">
               <div class="kt-heading kt-heading&#45;&#45;md">Review your Details and Submit</div>
               <div class="kt-form__section kt-form__section&#45;&#45;first">
                 <div class="kt-wizard-v2__review">
                   <div class="kt-wizard-v2__review-item">
                     <div class="kt-wizard-v2__review-title">
                       Account Details
                     </div>
                     <div class="kt-wizard-v2__review-content">
                       John Wick<br />
                       Phone: +61412345678<br />
                       Email: johnwick@reeves.com
                     </div>
                   </div>
                   <div class="kt-wizard-v2__review-item">
                     <div class="kt-wizard-v2__review-title">
                       Support Location Address
                     </div>
                     <div class="kt-wizard-v2__review-content">
                       Address Line 1<br />
                       Address Line 2<br />
                       Melbourne 3000, VIC, Australia
                     </div>
                   </div>
                   <div class="kt-wizard-v2__review-item">
                     <div class="kt-wizard-v2__review-title">
                       Support Channels
                     </div>
                     <div class="kt-wizard-v2__review-content">
                       Overnight Delivery with Regular Packaging<br />
                       Preferred Morning (8:00AM - 11:00AM) Delivery
                     </div>
                   </div>
                   <div class="kt-wizard-v2__review-item">
                     <div class="kt-wizard-v2__review-title">
                       Delivery Address
                     </div>
                     <div class="kt-wizard-v2__review-content">
                       Address Line 1<br />
                       Address Line 2<br />
                       Preston 3072, VIC, Australia
                     </div>
                   </div>
                   <div class="kt-wizard-v2__review-item">
                     <div class="kt-wizard-v2__review-title">
                       Payment Details
                     </div>
                     <div class="kt-wizard-v2__review-content">
                       Card Number: xxxx xxxx xxxx 1111<br />
                       Card Name: John Wick<br />
                       Card Expiry: 01/21
                     </div>
                   </div>
                 </div>
               </div>
             </div>-->
            <!--end: Form Wizard Step 6-->

            <!--begin: Form Actions -->
            <div class="kt-form__actions">
              <button
                class="btn btn-secondary btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u"
                data-ktwizard-type="action-prev">
                上一步
              </button>
              <button
                v-on:click="submit"
                class="btn btn-success btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u"
                data-ktwizard-type="action-submit">
                提交
              </button>
              <button
                class="btn btn-brand btn-md btn-tall btn-wide kt-font-bold kt-font-transform-u"
                data-ktwizard-type="action-next">
                下一步
              </button>
            </div>
            <!--end: Form Actions -->
          </form>
          <!--end: Form Wizard Form-->
        </div>
      </div>
    </el-drawer>

  </div>
</template>

<style lang="scss">
  @import "~@a/custom/styles/_common.css";
  @import "@/assets/sass/pages/wizard/wizard-2.scss";

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

  // 浏览提示信息相关
  .hint-info {
    .alert-dismissible .close {
      padding: 0.45rem 1.25rem;
    }
  }

  // 浏览检索工具栏相关
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

  // el-drawer相关
  .el-drawer.rtl {
    overflow: scroll;
  }
  .el-drawer:focus {
    outline: none;
  }
  .el-drawer__header {
    margin-bottom: 0;
  }

  /* routing name */
  .routingName-input {
    display: inline-block;
    width: 90%;
  }
  .routingName-add-icon {
    display: inline-block;
    height: 30px;
    width: 30px;
    line-height: 1.9;
    margin-left: 10px;
    vertical-align: middle;
    text-align: center;
  }
  .routingName-add-content {
    margin-top: 5px;
  }

  /* must fill in */
  .must-fill-in {
    color: red;
    font-size: 14px;
    vertical-align: middle;
  }

  /* err message */
  .err-message-frame {
    border: 1px solid red;
    box-shadow: 0 0 2px red;
  }
  .err-message {
    color: red !important;
    font-size: 0.9rem;
  }

</style>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';
  import KTPortlet from "@/views/partials/content/Portlet.vue";
  import KTButton from "@/components/KTButton.vue";
  import { format } from "@/utils/datetime";
  import KTUtil from '@/assets/js/util';
  import KTWizard from '../../../../assets/js/wizard';
  import PopupTreeInput from '@/components/PopupTreeInput';
  import $ from 'jquery'
  import { isServiceAlias, isServiceName, isServiceVersion, isServiceKeyword, isLongitude, isLatitude, isServiceDesc } from '../../../../utils/validate'
  import VMsgBox from '../../../../utils/validatemsg';
  import { hasPermission } from '@/utils/permission/index.js';

  export default {
    name: "View",
    components: {
      KTPortlet,
      KTButton,
      PopupTreeInput
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "服务管理", route: "view" },
        { title: "服务浏览"}
      ]);

      // Load page content
      this.findPage(null);

      // Load dept tree data
      this.findTreeData();

      // Load service subject data
      this.findSvcSubject();

      // Load service style data
      this.findSvcStyle();
    },
    data() {
      return {
        size: 'small',
        currentPage: 1,
        loading: false,

        pageRequest: {pageNum: 1, pageSize: 8},
        pageResult: {},

        drawerVisible: false, // 修改界面是否显示
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

        /** 服务修改相关 **/
        tableTreeData: [],
        popupTreeData: [],
        popupTreeProps: {
          label: "name",
          children: "children"
        },
        deptDataForm: {
          id: 0,
          name: '',
          parentId: 0,
          parentName: '',
          orderNum: 0
        },
        svcSubjectSelectData: [],
        svcTypeSelectData: [],
        svcStyleSelectData: [],
        // 修改界面数据
        svcDataForm: {
          deptId: 0,
          svcSubjectId: 0,
          svcTypeId: 0,
          svcStyleId: 0,

          isGisSvc: 1, // GIS服务1,否则0.
          svcName: '',
          svcAlias: '',
          svcVersion: '',
          svcDesc: '',
          svcKeyword: '',
          homePageUrl: '',
          interfaceSiteUrl: '',
          previewUrl: '',
          terminal: 1,
          routingName: '',

          svcCoverage: '',
          svcServiceArea: '',
          svcCoordinateSystem: '',
          svcProjectionType: '',
          svcUpdateCycle: '',

          layerName: '',
          layerDesc: '',
          layerKeyword: '',
          layerCoverage:'',
          layerServiceArea: '',
          layerCoordinateSystem: '',
          layerProjectionType: '',
          layerUpdateCycle: ''
        },
        thumbnailFileList: [],
        dialogImageUrl: '',
        dialogVisible: false,

        // 初始化服务路由动态增加参数
        routingNameFirst: '',
        routingNameParams: []
      }
    },
    methods: {
      /** 权限控制相关-页面按钮权限 **/
      hasPerms: function (perms) {
        // 根据权限标识和外部指示状态进行权限判断
        // ('&'表示运算符按位与，都为1才为1否则为0。也类似于且，都真才为真)
        return hasPermission(perms) & !this.disabled
      },

      /** 服务信息相关 **/
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
          {name: 'terminal', value: this.filters.terminal},
          {name: 'releaseState', value: 1}
        ]
        this.$api.svc.findSvcPage(this.pageRequest).then((res) => {
          this.pageResult = res.data.data;
          console.log(res.data.data)
        }).then(data!=null?data.callback:'').catch((error) => {
          console.log(error);
        })
      },
      // 单条删除
      handleDelete: function (row) {
        this.delete([row])
      },
      // 删除操作
      delete: function (rows) {
        this.$confirm('确认删除选中记录吗？', '提示', {
          type: 'warning'
        }).then(() => {
          this.loading = true;
          this.$api.svc.batchDelete(rows).then( (res) => {
            if (res.data.code == 200) {
              this.$message({ message: '删除成功, ' + res.data.message, type: 'success', duration: 2000 })
              this.findPage(null);
            } else {
              this.$message({ message: '操作失败, ' + res.data.message, type: 'error', duration: 2000 })
            }
            this.loading = false;
          })
        }).catch(() => {
        })
      },
      // 显示修改界面
      handleEdit: function (index, row) {
        this.drawerVisible = true;
        this.$nextTick(() => {
          //console.log($("#kt_wizard_v2"))

          // Initialize form wizard
          const wizard = new KTWizard("kt_wizard_v2", {
            startStep: 1, // initial active step number
            clickableSteps: true // allow step clicking
          });

          // Validation before going to next page
          wizard.on("beforeNext", function(/*wizardObj*/) {
            // validate the form and use below function to stop the wizard's step
            // wizardObj.stop();
          });

          // Change event
          wizard.on("change", function(/*wizardObj*/) {
            setTimeout(function() {
              KTUtil.scrollTop();
            }, 500);
          });
        });

        /*this.svcDataForm = Object.assign({}, row); 空数据会显示null故不适用该方式*/
        this.svcDataForm.svcId = row.svcId;

        this.svcDataForm.svcStyleId = row.svcStyleDO.styleId;

        this.svcDataForm.svcSubjectId = row.svcTypeDO.busSvcSubjectDO.subjectId;

        this.getSvcTypeData(null);
        this.svcDataForm.svcTypeId = row.svcTypeDO.typeId;

        this.deptDataForm.id = row.sysDeptDO.id;
        this.deptDataForm.parentName = row.sysDeptDO.name;
        this.svcDataForm.deptId = row.sysDeptDO.id;

        this.svcDataForm.isGisSvc = row.isGisSvc;
        this.svcDataForm.terminal = row.terminal;
        this.svcDataForm.svcName = row.svcName;
        this.svcDataForm.svcAlias = row.svcAlias;
        this.svcDataForm.svcVersion = row.svcVersion;
        this.svcDataForm.svcDesc = row.svcDesc;
        this.svcDataForm.svcKeyword = row.svcKeyword;

        if (row.homePageUrl !== null) {
          if (row.homePageUrl.trim() !== '') {
            this.svcDataForm.homePageUrl = row.homePageUrl;
          }
        }
        if (row.interfaceSiteUrl !== null) {
          if (row.interfaceSiteUrl.trim() !== '') {
            this.svcDataForm.interfaceSiteUrl = row.interfaceSiteUrl;
          }
        }
        if (row.previewUrl !== null) {
          if (row.previewUrl.trim() !== '') {
            this.svcDataForm.previewUrl = row.previewUrl;
          }
        }

        if (row.routingName !== null) {
          if (row.routingName.trim() !== '') {
            this.svcDataForm.routingName = row.routingName;
          }
        }


        if (row.svcCoverage !== null) {
          if (row.svcCoverage.trim() !== '') {
            this.svcDataForm.svcCoverage = row.svcCoverage;
          }
        }
        if (row.svcServiceArea !== null) {
          if (row.svcServiceArea.trim() !== '') {
            this.svcDataForm.svcServiceArea = row.svcServiceArea;
          }
        }
        if (row.svcCoordinateSystem !== null) {
          if (row.svcCoordinateSystem.trim() !== '') {
            this.svcDataForm.svcCoordinateSystem = row.svcCoordinateSystem;
          }
        }
        if (row.svcProjectionType !== null) {
          if (row.svcProjectionType.trim() !== '') {
            this.svcDataForm.svcProjectionType = row.svcProjectionType;
          }
        }
        if (row.svcUpdateCycle !== null) {
          if (row.svcUpdateCycle.trim() !== '') {
            this.svcDataForm.svcUpdateCycle = row.svcUpdateCycle;
          }
        }


        if (row.layerName !== null) {
          if (row.layerName.trim() !== '') {
            this.svcDataForm.layerName = row.layerName;
          }
        }
        if (row.layerDesc !== null) {
          if (row.layerDesc.trim() !== '') {
            this.svcDataForm.layerDesc = row.layerDesc;
          }
        }
        if (row.layerKeyword !== null) {
          if (row.layerKeyword.trim() !== '') {
            this.svcDataForm.layerKeyword = row.layerKeyword;
          }
        }
        if (row.layerCoverage !== null) {
          if (row.layerCoverage.trim() !== '') {
            this.svcDataForm.layerCoverage = row.layerCoverage;
          }
        }
        if (row.layerServiceArea !== null) {
          if (row.layerServiceArea.trim() !== '') {
            this.svcDataForm.layerServiceArea = row.layerServiceArea;
          }
        }
        if (row.layerCoordinateSystem !== null) {
          if (row.layerCoordinateSystem.trim() !== '') {
            this.svcDataForm.layerCoordinateSystem = row.layerCoordinateSystem;
          }
        }
        if (row.layerProjectionType !== null) {
          if (row.layerProjectionType.trim() !== '') {
            this.svcDataForm.layerProjectionType = row.layerProjectionType;
          }
        }
        if (row.layerUpdateCycle !== null) {
          if (row.layerUpdateCycle.trim() !== '') {
            this.svcDataForm.layerUpdateCycle = row.layerUpdateCycle;
          }
        }

        // 服务路由指派名处理
        let rnArr = this.svcDataForm.routingName.split(',');
        if (rnArr.length === 1) {
          this.routingNameFirst = this.svcDataForm.routingName;
        } else {
          this.routingNameFirst = rnArr[0];
          rnArr.forEach( (item, index, rnArr) => {
            if (index > 0) {
              this.routingNameParams.push({routingName: item});
            }
          });
        }
      },


      /** 通用 **/
      // 时间格式化
      dateFormat: function (data) {
        return format(data);
      },
      // 重置表单
      resetForm(formName) {
        this.$nextTick(()=>{
          this.$refs[formName].resetFields();
        })
        // this.pageRequest.pageNum = 1;
        // this.pageRequest.pageSize = 8;
      },
      // 清除验证
      /*clearVail(formName) {
        this.$nextTick(()=>{
          this.$refs[formName].clearValidate();
        })
      },*/
      // 关闭dialog事件
      handleCloseDrawer: function() {
        this.drawerVisible = false;
        this.clearCon();
      },
      clearCon: function() {
        this.svcDataForm.deptId = 0;
        this.svcDataForm.svcSubjectId = 0;
        this.svcDataForm.svcTypeId = 0;
        this.svcDataForm.svcStyleId = 0;

        this.svcDataForm.isGisSvc = 1;
        this.svcDataForm.svcName = '';
        this.svcDataForm.svcAlias = '';
        this.svcDataForm.svcVersion = '';
        this.svcDataForm.svcDesc = '';
        this.svcDataForm.svcKeyword = '';
        this.svcDataForm.homePageUrl = '';
        this.svcDataForm.interfaceSiteUrl = '';
        this.svcDataForm.previewUrl = '';
        this.svcDataForm.terminal = 1;
        this.svcDataForm.routingName = '';

        this.svcDataForm.svcCoverage = '';
        this.svcDataForm.svcServiceArea = '';
        this.svcDataForm.svcCoordinateSystem = '';
        this.svcDataForm.svcProjectionType = '';
        this.svcDataForm.svcUpdateCycle = '';

        this.svcDataForm.layerName = '';
        this.svcDataForm.layerDesc = '';
        this.svcDataForm.layerKeyword = '';
        this.svcDataForm.layerCoverage = '';
        this.svcDataForm.layerServiceArea = '';
        this.svcDataForm.layerCoordinateSystem = '';
        this.svcDataForm.layerProjectionType = '';
        this.svcDataForm.layerUpdateCycle = '';

        this.routingNameFirst = '';
        this.routingNameParams = [];

        // 移除校验提示信息
        $('#svcDataForm').find('span.err-message').remove();
        $('#svcDataForm').find('.err-message-frame').removeClass('err-message-frame');
      },

      /** 卡片分页、批量删除相关 **/
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

      /** 服务修改相关 **/
      // 上传图片表单涉及到的控制事件
      handleRemove(file, fileList) {
        console.log(file, fileList);
        this.thumbnailFileList = fileList;
        this.$message({
          type: 'info',
          message: '已删除原有图片',
          duration: 1000
        });
      },
      handlePreview(file) {
        console.log(file);
        let url = URL.createObjectURL(file.raw);
        console.log("file url is: "+url);
        this.dialogImageUrl = url;
        this.dialogVisible = true;
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      handleChange(file, fileList) {
        this.thumbnailFileList = fileList;
      },

      // 获取机构数据
      findTreeData: function() {
        this.loading = true;
        this.$api.dept.findDeptTree().then((res) => {
          this.tableTreeData = res.data.data;
          //this.popupTreeData = this.getParentMenuTree(res.data.data);
          this.popupTreeData = res.data.data;
          this.loading = false;
          //console.log(this.tableTreeData);
        }).catch((error) => {
          console.log(error);
        })
      },
      // 获取上级菜单树
      getParentMenuTree: function(tableTreeData) {
        let parent = {
          parentId: 0,
          name: '顶级菜单',
          children: tableTreeData
        };
        return [parent];
      },
      // 机构树项选中事件
      handleTreeSelectChange (data, node) {
        //console.log("current id---"+data.id+", current name---"+data.name);
        this.deptDataForm.parentId = data.id;
        this.deptDataForm.parentName = data.name;
        this.svcDataForm.deptId = data.id;

        // 每次选择机构书之后校验'服务所属机构'
        this.formCheck().svcDeptVerify();
      },
      // 获取服务主题
      findSvcSubject: function() {
        this.loading = true;
        this.$api.svcSubject.findSvcSubject().then((res) => {
          this.svcSubjectSelectData = res.data.data;
          this.loading = false;
        }).catch((error) => {
          console.log(error);
        })
      },
      // 获取服务类型(服务主题选中事件触发，自动更新对应的服务类型选项)
      getSvcTypeData: function(event) {
        let that = this;
        this.loading = true;
        let params = {subjectId: this.svcDataForm.svcSubjectId}; //  {"subjectId": event.target.value};
        this.$api.svcType.findSvcType(params).then((res) => {
          that.svcTypeSelectData = res.data.data;
          that.loading = false;
        }).catch((error) => {
          console.log(error);
        })
      },
      // 获取服务风格
      findSvcStyle: function() {
        this.loading = true;
        this.$api.svcStyle.findSvcStyle().then((res) => {
          this.svcStyleSelectData = res.data.data;
          this.loading = false;
        }).catch((error) => {
          console.log(error);
        })
      },

      // 编辑-提交
      submit: function(e) {
        e.preventDefault();

        // 组织服务路由派发名称组合
        let routingNameAssembly  = '';
        routingNameAssembly += this.routingNameFirst;
        if (this.routingNameParams.length > 0) {
          for (let rn = 0; rn < this.routingNameParams.length; rn++) {
            routingNameAssembly += ',' + this.routingNameParams[rn].routingName;
          }
        }

        // 校验
        if(!this.verify.flag){
          this.$message( {message: this.verify.msg, type: 'error', duration: 2000} );
          return;
        }

        // 缩略图校验-不支持修改缩略图
        /*if (this.thumbnailFileList.length <= 0) {
          this.$message.error("请至少上传一个缩略图文件！");
          return;
        }*/

        // 错误提示校验
        let num = 0;
        $(".err-message-frame").each(function() {
          num = num + 1;
        });
        if (num > 0) {
          this.$message( {message: '请解决错误后提交！', type: 'error', duration: 2000} );
          return;
        }

        // 提交注册
        this.$confirm('确定提交修改吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          confirmButtonClass: '',
          type: 'warning'
        }).then(() => {
          let formData = new FormData()
          for (let i in this.svcDataForm) {
            if (i === 'layerUpdateTime') {
              continue;
            } else if (i === 'routingName') {
              // 服务路由派发名赋值
              formData.append(i, routingNameAssembly);
            } else {
              formData.append(i, this.svcDataForm[i]);
            }
          }

          let config = {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }

          // 缩略图参数-不支持修改缩略图
          /*for (let i=0;i<this.thumbnailFileList.length;i++) {
            formData.append("thumbfile", this.thumbnailFileList[i].raw);
          }*/
          /*formData.forEach((value, key) => {
            console.log("key %s: va
            lue %s", key, value);
          })*/

          this.$api.svc.save(formData,config).then((res) => {
            this.thumbnailFileList = [];
            this.$message({ title: '成功', message: '修改成功', type: 'success', duration: 2000 });
            //console.log(res.data);

            this.findPage(null);
            this.handleCloseDrawer();
          })
        }).catch((error) => {
          console.log(error);
        });
      },

      // 添加表单校验提示
      checkInfoFormatAddErr: function (objId, msg) {
        let $obj = $("#"+objId);
        let str = "<span class='err-message'>"+msg+"</span>";
        $obj.addClass("err-message-frame");
        if ($obj.siblings("span.err-message").length>0) {
          $obj.siblings('span.err-message').remove().end().parent().append(str);
        } else {
          $obj.parent().append(str);
        }
      },
      // 移除表单校验提示
      checkInfoFormatRemoveErr: function(objId) {
        let $obj = $("#"+objId);
        $obj.removeClass("err-message-frame");
        if ($obj.siblings("span.err-message").length>0) {
          $obj.siblings('span.err-message').remove();
        }
      },

      // 服务路由名称新增事件
      addRoutingNameItem: function () {
        this.routingNameParams.push({
          routingName: ""
        });
      },
      // 服务路由名称删除事件
      minusRoutingNameItem: function (item) {
        let index = this.routingNameParams.indexOf(item);
        if (index !== -1) { // 当没有多余路由名时就不再删除
          this.routingNameParams.splice(index, 1);
        }
      },

      // 表单校验
      formCheck: function() {
        let that = this;
        // 服务风格校验
        let svcStyleVerify = function() {
          if (!that.svcDataForm.svcStyleId) {
            that.checkInfoFormatAddErr('svcStyle', VMsgBox.SvcVMsgBox.svcStyleNotNull);
          } else {
            that.checkInfoFormatRemoveErr('svcStyle');
          }
        };

        // 服务所属机构校验
        let svcDeptVerify = function() {
          //console.log(that.svcDataForm.deptId)
          if (!that.svcDataForm.deptId) {
            that.checkInfoFormatAddErr('deptName', VMsgBox.SvcVMsgBox.deptNameNotNull);
          } else {
            that.checkInfoFormatRemoveErr('deptName');
          }
        };

        // 服务主题校验
        let svcSubjectVerify = function() {
          if (!that.svcDataForm.svcSubjectId) {
            that.checkInfoFormatAddErr('svcSubject', VMsgBox.SvcVMsgBox.svcSubjectNotNull);
          } else {
            that.checkInfoFormatRemoveErr('svcSubject');
          }
        }

        // 服务类型校验
        let svcTypeVerify = function() {
          if (!that.svcDataForm.svcTypeId) {
            that.checkInfoFormatAddErr('svcType', VMsgBox.SvcVMsgBox.svcTypeNotNull);
          } else {
            that.checkInfoFormatRemoveErr('svcType');
          }
        }

        // 服务名称校验
        let svcNameVerify = function() {
          if (!that.svcDataForm.svcName) {
            that.checkInfoFormatAddErr('svcName', VMsgBox.SvcVMsgBox.svcNameNotNull);
            return { "flag": false, "msg": VMsgBox.SvcVMsgBox.svcNameNotNull};
          } else if (!isServiceName(that.svcDataForm.svcName)) {
            let errMsg = VMsgBox.SvcVMsgBox.svcNameDemand;
            that.checkInfoFormatAddErr('svcName' ,errMsg);
          } else {
            that.checkInfoFormatRemoveErr('svcName');
          }
        }

        // 服务别名校验
        let svcAliasVerify = function() {
          if (!that.svcDataForm.svcAlias) {
            that.checkInfoFormatAddErr('svcAlias', VMsgBox.SvcVMsgBox.svcAliasNotNull);
          } else if (!isServiceAlias(that.svcDataForm.svcAlias)) {
            let errMsg = VMsgBox.SvcVMsgBox.svcAliasDemand;
            that.checkInfoFormatAddErr('svcAlias' ,errMsg);
          } else {
            that.checkInfoFormatRemoveErr('svcAlias');
          }
        }

        // 服务版本校验
        let svcVersionVerify = function () {
          if (!that.svcDataForm.svcVersion) {
            that.checkInfoFormatAddErr('svcVersion', VMsgBox.SvcVMsgBox.svcVersionNotNull);
          } else if (!isServiceVersion(that.svcDataForm.svcVersion)) {
            let errMsg = VMsgBox.SvcVMsgBox.svcVersionDemand;
            that.checkInfoFormatAddErr('svcVersion' ,errMsg);
          } else {
            that.checkInfoFormatRemoveErr('svcVersion');
          }
        }

        // 服务描述校验
        let svcDescVerify = function() {
          if (!that.svcDataForm.svcDesc) {
            that.checkInfoFormatAddErr('svcDesc', VMsgBox.SvcVMsgBox.svcDescNotNull);
          } else {
            that.checkInfoFormatRemoveErr('svcDesc');
          }
        }

        // 服务关键字不为空
        let svcKeywordVerify = function() {
          if (!that.svcDataForm.svcKeyword) {
            that.checkInfoFormatAddErr('svcKeyword', VMsgBox.SvcVMsgBox.svcKeywordNotNull);
          } else if (!isServiceKeyword(that.svcDataForm.svcKeyword)) {
            let errMsg = VMsgBox.SvcVMsgBox.svcKeywordDemand;
            that.checkInfoFormatAddErr('svcKeyword' ,errMsg);
          } else {
            that.checkInfoFormatRemoveErr('svcKeyword');
          }
        }

        /** 地理信息服务时，校验服务扩展参数 **/
        // 服务覆盖范围校验
        let svcCoverageVerify = function () {
            if (!that.svcDataForm.svcCoverage) {
              that.checkInfoFormatAddErr('svcCoverage', VMsgBox.SvcVMsgBox.svcCoverageNotNull);
            } else {
              that.checkInfoFormatRemoveErr('svcCoverage');
            }
          }
        // 服务服务范围校验
        let svcServiceAreaVerify = function (objId) {
          if (that.svcDataForm.svcServiceArea) {
            let content = that.svcDataForm.svcServiceArea;
            serviceAreaVerifyFormat(objId, content);
          } else {
            that.checkInfoFormatRemoveErr(objId);
          }
        }
        // 图层数据范围校验
        let layerServiceAreaVerify = function (objId) {
          if (that.svcDataForm.layerServiceArea) {
            let content = that.svcDataForm.layerServiceArea;
            serviceAreaVerifyFormat(objId, content);
          } else {
            that.checkInfoFormatRemoveErr(objId);
          }
        }
        // 格式校验
        let serviceAreaVerifyFormat = function (objId, content) {
          let conArray = content.split(',');
          if (conArray.length === 4) {
            let leftTopLon = conArray[0];
            let leftTopLat = conArray[1];
            let rightBottomLon = conArray[2];
            let rightBottomLat = conArray[3];
            if (!isLongitude(leftTopLon)) {
              let errMsg = VMsgBox.SvcVMsgBox.leftTopLonDemand;
              that.checkInfoFormatAddErr(objId, errMsg);
            } else if (!isLongitude(rightBottomLon)) {
              let errMsg = VMsgBox.SvcVMsgBox.rightBottomLonDemand;
              that.checkInfoFormatAddErr(objId, errMsg);
            } else if (!isLatitude(leftTopLat)) {
              let errMsg = VMsgBox.SvcVMsgBox.leftTopLatDemand;
              that.checkInfoFormatAddErr(objId, errMsg);
            } else if (!isLatitude(rightBottomLat)) {
              let errMsg = VMsgBox.SvcVMsgBox.rightBottomLatDemand;
              that.checkInfoFormatAddErr(objId, errMsg);
            } else {
              that.checkInfoFormatRemoveErr(objId);
            }
          } else {
            let errMsg = VMsgBox.SvcVMsgBox.LonLatDemand;
            that.checkInfoFormatAddErr(objId, errMsg);
          }
        }

        // 服务坐标系校验
        let svcCoordinateSystemVerify = function () {
          if (!that.svcDataForm.svcCoordinateSystem) {
            that.checkInfoFormatAddErr('svcCoordinateSystem', VMsgBox.SvcVMsgBox.svcCoordinateSystemNotNull);
          } else {
            that.checkInfoFormatRemoveErr('svcCoordinateSystem');
          }
        };
        // 服务投影类型校验
        let svcProjectionTypeVerify = function () {
          if (!that.svcDataForm.svcProjectionType) {
            that.checkInfoFormatAddErr('svcProjectionType', VMsgBox.SvcVMsgBox.svcProjectionTypeNotNull);
          } else {
            that.checkInfoFormatRemoveErr('svcProjectionType');
          }
        };
        // 服务更新周期校验
        let svcUpdateCycleVerify = function () {
          if (!that.svcDataForm.svcUpdateCycle) {
            that.checkInfoFormatAddErr('svcUpdateCycle', VMsgBox.SvcVMsgBox.svcUpdateCycleNotNull);
          } else {
            that.checkInfoFormatRemoveErr('svcUpdateCycle');
          }
        };

        return {
          // 服务基础信息
          svcStyleVerify: svcStyleVerify,
          svcDeptVerify: svcDeptVerify,
          svcSubjectVerify: svcSubjectVerify,
          svcTypeVerify: svcTypeVerify,
          svcNameVerify: svcNameVerify,
          svcAliasVerify: svcAliasVerify,
          svcVersionVerify: svcVersionVerify,
          svcDescVerify: svcDescVerify,
          svcKeywordVerify: svcKeywordVerify,

          // 服务扩展信息
          svcCoverageVerify: svcCoverageVerify,
          svcServiceAreaVerify: svcServiceAreaVerify,
          svcCoordinateSystemVerify: svcCoordinateSystemVerify,
          svcProjectionTypeVerify: svcProjectionTypeVerify,
          svcUpdateCycleVerify: svcUpdateCycleVerify,

          // 服务对应图层信息
          layerServiceAreaVerify: layerServiceAreaVerify
        }
      },


    },
    computed: {
      VMsgBox() {
        return VMsgBox;
      },
      // 注册表单校验
      verify: function() {
        if (!this.svcDataForm.svcStyleId ||
          !this.svcDataForm.terminal || !this.svcDataForm.deptId ||
          !this.svcDataForm.svcSubjectId || !this.svcDataForm.svcTypeId ||
          !this.svcDataForm.svcName || !this.svcDataForm.svcAlias || !this.svcDataForm.svcVersion ||
          !this.svcDataForm.svcKeyword || !this.svcDataForm.svcDesc) {
          // 服务适用终端类型不为空
          if (!this.svcDataForm.terminal) {
            this.checkInfoFormatAddErr('terminal', VMsgBox.SvcVMsgBox.terminalNotNull);
          }
          // 服务风格不为空
          if (!this.svcDataForm.svcStyleId) {
            console.log(VMsgBox)
            this.checkInfoFormatAddErr('svcStyle', VMsgBox.SvcVMsgBox.svcStyleNotNull);
          }
          // 服务所属机构不为空
          if (!this.svcDataForm.deptId) {
            this.checkInfoFormatAddErr('deptName', VMsgBox.SvcVMsgBox.deptNameNotNull);
          }
          // 服务主题不为空
          if (!this.svcDataForm.svcSubjectId) {
            this.checkInfoFormatAddErr('svcSubject', VMsgBox.SvcVMsgBox.svcSubjectNotNull);
          }
          // 服务类型不为空
          if (!this.svcDataForm.svcTypeId) {
            this.checkInfoFormatAddErr('svcType', VMsgBox.SvcVMsgBox.svcTypeNotNull);
          }
          // 服务名称不为空
          if (!this.svcDataForm.svcName) {
            this.checkInfoFormatAddErr('svcName', VMsgBox.SvcVMsgBox.svcNameNotNull);
          }
          // 服务别名不为空
          if (!this.svcDataForm.svcAlias) {
            this.checkInfoFormatAddErr('svcAlias', VMsgBox.SvcVMsgBox.svcAliasNotNull);
          }
          // 服务版本不为空
          if (!this.svcDataForm.svcVersion) {
            this.checkInfoFormatAddErr('svcVersion', VMsgBox.SvcVMsgBox.svcVersionNotNull);
          }
          // 服务描述不为空
          if (!this.svcDataForm.svcDesc) {
            this.checkInfoFormatAddErr('svcDesc', VMsgBox.SvcVMsgBox.svcDescNotNull);
          }
          // 服务关键字不为空
          if (!this.svcDataForm.svcKeyword) {
            this.checkInfoFormatAddErr('svcKeyword', VMsgBox.SvcVMsgBox.svcKeywordNotNull);
          }
          return { "flag": false, "msg": VMsgBox.SvcVMsgBox.NotNullFlag};
        } else {
          let errCount = 0;
          if (this.svcDataForm.svcName) {
            if (!isServiceName(this.svcDataForm.svcName)) {
              let errMsg = VMsgBox.SvcVMsgBox.svcNameDemand;
              this.checkInfoFormatAddErr('svcName' ,errMsg);
              errCount++;
            }
          }
          if (this.svcDataForm.svcAlias) {
            if (!isServiceAlias(this.svcDataForm.svcAlias)) {
              let errMsg = VMsgBox.SvcVMsgBox.svcAliasDemand;
              this.checkInfoFormatAddErr('svcAlias' ,errMsg);
              errCount++;
            }
          }
          if (this.svcDataForm.svcVersion) {
            if (!isServiceVersion(this.svcDataForm.svcVersion)) {
              let errMsg = VMsgBox.SvcVMsgBox.svcVersionDemand;
              this.checkInfoFormatAddErr('svcVersion' ,errMsg);
              errCount++;
            }
          }
          if (this.svcDataForm.svcDesc) {
            if (!isServiceDesc(this.svcDataForm.svcDesc)) {
              let errMsg = VMsgBox.SvcVMsgBox.svcDescDemand;
              this.checkInfoFormatAddErr('svcDesc' ,errMsg);
              errCount++;
            }
          }
          if (this.svcDataForm.svcKeyword) {
            if (!isServiceKeyword(this.svcDataForm.svcKeyword)) {
              let errMsg = VMsgBox.SvcVMsgBox.svcKeywordDemand;
              this.checkInfoFormatAddErr('svcKeyword' ,errMsg);
              errCount++;
            }
          }

          if (errCount > 0) {
            return {"flag": false, "msg": VMsgBox.SvcVMsgBox.DemandFlag};
          }
        }

        // 是地理信息服务，对相关参数进行校验
        /*if (this.svcDataForm.isGisSvc == 1) {*/
        if (false) {
          if (!this.svcDataForm.svcCoverage || !this.svcDataForm.svcServiceArea ||
            !this.svcDataForm.svcCoordinateSystem || !this.svcDataForm.svcProjectionType ||
            !this.svcDataForm.svcUpdateCycle) {
            if (!this.svcDataForm.svcCoverage) {
              this.checkInfoFormatAddErr('svcCoverage', VMsgBox.SvcVMsgBox.svcCoverageNotNull);
            }
            if (!this.svcDataForm.svcServiceArea) {
              this.checkInfoFormatAddErr('svcServiceArea', VMsgBox.SvcVMsgBox.svcServiceAreaNotNull);
            }
            if (!this.svcDataForm.svcCoordinateSystem) {
              this.checkInfoFormatAddErr('svcCoordinateSystem', VMsgBox.SvcVMsgBox.svcCoordinateSystemNotNull);
            }
            if (!this.svcDataForm.svcProjectionType) {
              this.checkInfoFormatAddErr('svcProjectionType', VMsgBox.SvcVMsgBox.svcProjectionTypeNotNull);
            }
            if (!this.svcDataForm.svcUpdateCycle) {
              this.checkInfoFormatAddErr('svcUpdateCycle', VMsgBox.SvcVMsgBox.svcUpdateCycleNotNull);
            }
            return { "flag": false, "msg": VMsgBox.SvcVMsgBox.DemandFlag};
          } else {
            if (this.svcDataForm.svcServiceArea) {
              let content = this.svcDataForm.svcServiceArea;
              let conArray = content.split(',');
              if (conArray.length === 4) {
                let leftTopLon = conArray[0];
                let leftTopLat = conArray[1];
                let rightBottomLon = conArray[2];
                let rightBottomLat = conArray[3];
                if (!isLongitude(leftTopLon)) {
                  let errMsg = VMsgBox.SvcVMsgBox.leftTopLonDemand;
                  this.checkInfoFormatAddErr('svcServiceArea' ,errMsg);
                  return { "flag": false, "msg": errMsg };
                }
                if (!isLongitude(rightBottomLon)) {
                  let errMsg = VMsgBox.SvcVMsgBox.rightBottomLonDemand;
                  this.checkInfoFormatAddErr('svcServiceArea' ,errMsg);
                  return { "flag": false, "msg": errMsg };
                }
                if (!isLatitude(leftTopLat)) {
                  let errMsg = VMsgBox.SvcVMsgBox.leftTopLatDemand;
                  this.checkInfoFormatAddErr('svcServiceArea' ,errMsg);
                  return { "flag": false, "msg": errMsg };
                }
                if (!isLatitude(rightBottomLat)) {
                  let errMsg = VMsgBox.SvcVMsgBox.rightBottomLatDemand;
                  this.checkInfoFormatAddErr('svcServiceArea' ,errMsg);
                  return { "flag": false, "msg": errMsg };
                }
              } else {
                let errMsg = VMsgBox.SvcVMsgBox.LonLatDemand;
                this.checkInfoFormatAddErr('svcServiceArea' ,errMsg);
                return { "flag": false, "msg": errMsg };
              }
            }
          }
        }
        return {"flag": true, "msg": 'ok'};
      }
    }

  }
</script>
