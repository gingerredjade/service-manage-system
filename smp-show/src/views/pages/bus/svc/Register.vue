<template>
  <div>
    <div class="hint-info">
      <!-- background-color: #f0f9eb; color: #67c23a; -->
      <!-- background-color: #d1ecf1; border-color: #bee5eb; color: #0c5460; -->
      <el-alert style="background-color: #d1ecf1; border-color: #bee5eb; color: #0c5460;"
                title="输入指引"
                type="info"
                description="须填写【服务基本信息】。选填【服务扩展信息】、【服务对应图层信息】。
                              注：服务的【适用终端类型、所属机构、主题、类型、名称、版本】均相同的，不允重复注册！"
                show-icon
                :closable="false">
      </el-alert>
    </div>
    <div class="kt-portlet">


      <div class="kt-portlet__body kt-portlet__body--fit">
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
                        填写你的服务基本信息，该类均需填写
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
                <!--<div class="kt-wizard-v2__nav-item" data-ktwizard-type="step">
                  <div class="kt-wizard-v2__nav-body">
                    <div class="kt-wizard-v2__nav-icon">
                      <i class="flaticon-trophy"></i>
                    </div>
                    <div class="kt-wizard-v2__nav-label">
                      <div class="kt-wizard-v2__nav-label-title">
                        服务权限设置
                      </div>
                      <div class="kt-wizard-v2__nav-label-desc">
                        配置该服务的权限相关项
                      </div>
                    </div>
                  </div>
                </div>-->
                <!--<div class="kt-wizard-v2__nav-item" data-ktwizard-type="step">
                  <div class="kt-wizard-v2__nav-body">
                    <div class="kt-wizard-v2__nav-icon">
                      <i class="flaticon-truck"></i>
                    </div>
                    <div class="kt-wizard-v2__nav-label">
                      <div class="kt-wizard-v2__nav-label-title">
                        Make Payment
                      </div>
                      <div class="kt-wizard-v2__nav-label-desc">
                        Use Credit or Debit Cards
                      </div>
                    </div>
                  </div>
                </div>-->
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
                <div class="kt-heading kt-heading--md">填写服务基本信息</div>
                <div class="kt-form__section kt-form__section--first">
                  <div class="kt-wizard-v2__form">
                    <div class="form-group">
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
                        <el-button size="small" type="primary">点击上传缩略图</el-button><!--<span class="must-fill-in">*</span>-->
                        <div slot="tip" class="el-upload__tip">只能上传图片文件</div>
                      </el-upload>
                      <el-dialog :visible.sync="dialogVisible">
                        <img width="100%" :src="dialogImageUrl" alt="">
                      </el-dialog>
                    </div>
                    <div class="form-group">
                      <el-switch
                        style="display: block"
                        v-model="svcDataForm.isGisSvc"
                        active-text="地理信息服务"
                        inactive-text="非地理信息服务"
                        active-value="1"
                        inactive-value="0"
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
                          <label>服务主题<span class="must-fill-in">*</span></label><!--    -->
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
                          <!--<span class="form-text text-muted">支持汉字、数字、字母或下划线，不能以下划线开头和结尾，长度2-30.</span>-->
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
                        v-model="svcDataForm.previewUrl"/>
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
                              v-model="svcDataForm.routingName"/>
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
                <div class="kt-heading kt-heading--md">填写服务扩展信息</div><!-- 地理信息 -->
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
                          <!-- @blur="svcDataForm.isGisSvc==1?formCheck().svcServiceAreaVerify():''" -->
                          <input
                            type="text"
                            class="form-control"
                            name="serviceScope"
                            placeholder=""
                            value=""
                            v-model="svcDataForm.svcServiceArea" id="svcServiceArea"
                            @blur="formCheck().svcServiceAreaVerify('svcServiceArea')"/>
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
                <div class="kt-heading kt-heading--md">填写服务图层基本信息</div><!-- 地理信息 -->
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
      </div>
    </div>
  </div>

</template>

<!-- 注意style要在script之前 -->
<style lang="scss">
  @import "@/assets/sass/pages/wizard/wizard-2.scss";

  /* el popover set fixed height and scrollbar */
  .el-popover {
    height: 300px;
    overflow: auto;
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

  /* registe hint info  */
  div.hint-info {
    margin: 0 0 8px 0;
  }

  .el-alert--info .el-alert__description {
    color: #0c5460;
  }

  /* must fill in */
  .must-fill-in {
    color: red;
    font-size: 14px;
    vertical-align: middle;
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


</style>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';
  import KTUtil from '@/assets/js/util';
  import KTWizard from '../../../../assets/js/wizard';
  import Swal from 'sweetalert2';
  import PopupTreeInput from '@/components/PopupTreeInput';
  import $ from 'jquery'
  import { isServiceAlias, isServiceName, isServiceVersion, isServiceKeyword, isLongitude, isLatitude, isServiceDesc } from '../../../../utils/validate'
  import VMsgBox from '../../../../utils/validatemsg'

  export default {
    name: "Register",
    components: {
      PopupTreeInput
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "服务管理", route: "register" },
        { title: "服务注册"}
      ]);

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

      // Load dept tree data
      this.findTreeData();

      // Load service subject data
      this.findSvcSubject();

      // Load service style data
      this.findSvcStyle();
    },
    data() {
      return {
        show: false,
        loading: false,
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

        // 终端类型
        terminalOptions: [
          { text: '通用', value: 1 },
          { text: 'Web端', value: 2 },
          { text: '桌面端', value: 3 }
        ],

        // 初始化服务路由动态增加参数
        routingNameParams: []
      }
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
            console.log(VMsgBox)
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
          return { "flag":false, "msg": VMsgBox.SvcVMsgBox.NotNullFlag};
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
            return {"flag":false, "msg": VMsgBox.SvcVMsgBox.DemandFlag};
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
            return { "flag":false, "msg": VMsgBox.SvcVMsgBox.NotNullFlag};
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
    },
    methods: {
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
            return { "flag":false, "msg": VMsgBox.SvcVMsgBox.svcNameNotNull};
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
        }).catch(()=>{})
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
        }).catch(()=>{})
      },
      // 获取服务类型(服务主题选中事件触发，自动更新对应的服务类型选项)
      getSvcTypeData: function(event) {
        let that = this;
        this.loading = true;
        let params = {subjectId: event.target.value}; // {"subjectId": this.svcDataForm.svcSubject.subjectId};
        this.$api.svcType.findSvcType(params).then((res) => {
          that.svcTypeSelectData = res.data.data;
          that.loading = false;
        }).catch(()=>{})
      },
      // 获取服务风格
      findSvcStyle: function() {
        this.loading = true;
        this.$api.svcStyle.findSvcStyle().then((res) => {
          this.svcStyleSelectData = res.data.data;
          this.loading = false;
        }).catch(()=>{})
      },
      // 服务注册表单提交
      /*submit: function(e) {
        e.preventDefault();
        this.$confirm('确定提交注册吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          confirmButtonClass: '',
          type: 'warning'
        }).then(() => {
          let params = Object.assign({}, this.svcDataForm);
          params.sysDeptDO.name = this.deptDataForm.parentName;
          params.sysDeptDO.id = this.deptDataForm.parentId;
          console.log(params)
          this.$open.svc.save(params).then((res) => {
              this.$message({message: '注册成功', type: 'success'})
              console.log(res.data);
          })
        }).catch((error) => {
          console.log(error);
        });
      }*/

      submit: function(e) {
        e.preventDefault();

        // 组织服务路由派发名称组合
        let firstRoutingName = this.svcDataForm.routingName;
        let routingNameAssembly  = '';
        routingNameAssembly += firstRoutingName;
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

        // 缩略图校验
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
        this.$confirm('确定提交注册吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          confirmButtonClass: '',
          type: 'warning'
        }).then(() => {
          let formData = new FormData()
          for (let i in this.svcDataForm) {
            if (i === 'routingName') {
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

          // 缩略图参数
          for (let i=0;i<this.thumbnailFileList.length;i++) {
            formData.append("thumbfile", this.thumbnailFileList[i].raw);
          }
          /*formData.forEach((value, key) => {
            console.log("key %s: va
            lue %s", key, value);
          })*/

          // 执行保存
          this.$api.svc.save(formData,config).then((res) => {
            this.thumbnailFileList = [];
            if (res.data.code === 500) {
              this.$message({ title: '失败', message: res.data.message, type: 'warning', duration: 4000 });
            } else {
              this.$message({ title: '成功', message: res.data.message, type: 'success', duration: 2000 });
            }
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
      }


    }

  }
</script>



