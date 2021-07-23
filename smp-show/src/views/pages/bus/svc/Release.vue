<template>
  <div class="">
    <div class="hint-info">
      <b-alert show variant="primary" dismissible
               style="background-color: rgb(209, 236, 241);
               border-color: rgb(190, 229, 235);
               color: rgb(12, 84, 96);
               padding: 6px 14px; margin: 0 auto;">
        <div>
          <i class="fa fa-info-circle"></i>
          <span class="hint-description"
                style="font-family: 微软雅黑; font-size: 12px;">
            允许对<span style="color: #dc3545; font-weight: bold;">审核通过</span>的服务进行<span style="color: #007bff; font-weight: bold;">发布</span>
          </span>
        </div>
      </b-alert>
    </div>

    <!-- 工具栏 -->
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
                                perms="svc:release:view"
                    />
                  </el-form-item>
                </div>
                <div>
                  <el-form-item>
                    <k-t-button icon="fa fa-undo" :label="$t('ACTION.RESET')"
                                @click="resetForm('filters')"
                                perms="svc:release:view"
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
                <el-form-item prop="releaseState">
                  <el-select v-model="filters.releaseState" placeholder="选择发布状态">
                    <el-option
                      v-for="item in releaseStateOptions"
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
      <div class="">
        <el-form :inline="true" :size="size">
          <el-form-item>
            <el-button-group>
              <el-tooltip :content="$t('ACTION.REFRESH')" placement="top">
                <el-button icon="fa fa-refresh"
                           @click="findPage({ pageRequest: {pageNum: 1, pageSize: pageRequest.pageSize} })"
                           :disabled="!hasPerms('svc:release:view')"
                />
              </el-tooltip>
            </el-button-group>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <div class="kt-portlet">
      <div class="kt-portlet__body kt-portlet__body--fit">
        <!--卡片内容栏-->
        <div class="service-card-content">
          <el-table :data="pageResult.list" style="width: 100%" size="small" border
                    v-loading="loading" :element-loading-text="$t('ACTION.LOADING')" :highlight-current-row="true">
            <!-- 展开行内容 -->
            <el-table-column fixed="left" type="expand">
              <template slot-scope="props"><!--type="border-card"  value="baseInfo" -->
                <div>
                  <div class="row">
                    <div class="col-xl-12 text-ellipsis" :title="props.row.svcAlias">
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
                      <span class="kt-font-info">{{ props.row.layerCoordinateSystem==null||props.row.layerCoordinateSystem===''?'无':props.row.layerCoordinateSystem }}</span>
                    </div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerProjectionType">
                      <span>图层投影类型：</span>
                      <span class="kt-font-info">{{ props.row.layerProjectionType==null||props.row.layerProjectionType===''?'无':props.row.layerProjectionType }}</span>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerCoverage">
                      <span>图层覆盖范围：</span>
                      <span class="kt-font-info">{{ props.row.layerCoverage==null||props.row.layerCoverage===''?'无':props.row.layerCoverage }}</span>
                    </div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerServiceArea">
                      <span>图层数据范围：</span>
                      <span class="kt-font-info">{{ props.row.layerServiceArea==null||props.row.layerServiceArea===''?'无':props.row.layerServiceArea }}</span>
                    </div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerUpdateCycle">
                      <span>图层更新周期：</span>
                      <span class="kt-font-info">{{ props.row.layerUpdateCycle==null||props.row.layerUpdateCycle===''?'无':props.row.layerUpdateCycle }}</span>
                    </div>
                    <div class="col-xl-3 text-ellipsis" :title="props.row.layerUpdateTime">
                      <span>图层最新更新时间：</span>
                      <span class="kt-font-info">{{ props.row.layerUpdateTime==null||props.row.layerUpdateTime===''?'无':props.row.layerUpdateTime }}</span>
                    </div>
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
            <el-table-column label="服务名称" prop="svcName" header-align="center" align="center" width="200"></el-table-column>
            <!--<el-table-column label="服务别名" prop="svcAlias" header-align="center" align="center" width="180"></el-table-column>
            <el-table-column label="服务版本" prop="svcVersion" header-align="center" align="center" width="180"></el-table-column>-->
            <el-table-column label="服务类型" prop="svcVersion" header-align="center" align="center" width="180">
              <template slot-scope="scope">
                {{ scope.row.svcTypeDO.name }}
              </template>
            </el-table-column>
            <el-table-column label="服务主题" prop="svcVersion" header-align="center" align="center" width="180">
              <template slot-scope="scope">
                {{ scope.row.svcTypeDO.busSvcSubjectDO.name }}
              </template>
            </el-table-column>
            <el-table-column label="机构名称" prop="svcVersion" header-align="center" align="center" width="180">
              <template slot-scope="scope">
                {{ scope.row.sysDeptDO.name }}
              </template>
            </el-table-column>
            <el-table-column label="适用终端类型" prop="terminal" header-align="center" align="center" width="100px">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.terminal === 1" size="mini" effect="plain" type="success">通用</el-tag>
                <el-tag v-else-if="scope.row.terminal === 2" size="mini" effect="plain" type="">Web端</el-tag>
                <el-tag v-else-if="scope.row.terminal === 3" size="mini" effect="plain" type="info">桌面端</el-tag>
                <el-tag v-else size="mini" effect="plain" type="danger">未知</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="地理信息服务" prop="isGisSvc" header-align="center" align="center" width="100px">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.isGisSvc === 1" size="mini" effect="plain" type="">是</el-tag>
                <el-tag v-else size="mini" effect="plain" type="info">否</el-tag>
              </template>
            </el-table-column>
            <!--<el-table-column label="审核状态" prop="auditState" header-align="center" align="center" width="100px">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.auditState === 0" size="mini" effect="plain" type="">未审核</el-tag>
                <el-tag v-else-if="scope.row.auditState === 1" size="mini" effect="plain" type="success">审核通过</el-tag>
                <el-tag v-else-if="scope.row.auditState === 2" size="mini" effect="plain" type="danger">审核拒绝</el-tag>
                <el-tag v-else size="mini" effect="dark" type="info">未知</el-tag>
              </template>
            </el-table-column>-->
            <el-table-column label="发布状态" prop="releaseState" header-align="center" align="center">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.releaseState === 0" size="mini" effect="plain" type="">未发布</el-tag>
                <el-tag v-else-if="scope.row.releaseState === 1" size="mini" effect="plain" type="success">已发布</el-tag>
                <el-tag v-else size="mini" effect="plain" type="info">未知</el-tag>
              </template>
            </el-table-column>

            <el-table-column
              fixed="right" header-align="center" align="center" width="255" :label="$t('ACTION.OPERATION')">
              <template slot-scope="scope">
                <k-t-button  v-if="scope.row.releaseState === 0" icon="fa fa-edit" :label="$t('ACTION.RELEASE')" type="success" perms="svc:release:release" @click="handleRelease(scope.$index, scope.row)"/>
                <k-t-button  v-else-if="scope.row.releaseState === 1" icon="fa fa-edit" :label="$t('ACTION.RELEASE_UNDO')" type="warning" perms="svc:release:release" @click="handleRelease(scope.$index, scope.row)"/>
                <k-t-button icon="fa fa-trash" :label="$t('ACTION.DELETE')" perms="svc:release:delete" type="danger" @click="handleDelete(scope.row)"/>
              </template>
            </el-table-column>
          </el-table>
        </div>

      </div>
    </div>

    <div class="">
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
          :disabled="!hasPerms('svc:release:view')"
        >
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';
  import { format } from "@/utils/datetime";
  import KTButton from "@/components/KTButton.vue";
  import { hasPermission } from '@/utils/permission/index.js';

  export default {
    name: "Release",
    components: {
      KTButton
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "服务管理·服务审核发布", route: "release" },
        { title: "服务发布"}
      ]);

      // Load page content
      this.findPage(null);
    },
    data() {
      return {
        size: 'small',
        loading: false,
        filters: {
          name: '', // 服务名称
          alias: '', // 服务别名
          svcSubjectName: '', // 服务主题名称
          svcTypeName: '', // 服务类型名称
          svcKeyword: '', // 服务关键字
          deptName: '', // 机构名称
          version: '', // 服务版本
          terminal: '', // 适用终端类型
          releaseState: '' // 发布状态
        },
        pageRequest: {pageNum: 1, pageSize: 10},
        pageResult: {},
        editLoading: false,

        //thumbnailBaseURI: API_URL,

        // tab页相关
        tabActiveName: 'baseInfo',
        tabPosition: 'top',

        dataFormRules: {},

        // 终端类型
        terminalOptions: [
          { text: '通用', value: 1 },
          { text: 'Web端', value: 2 },
          { text: '桌面端', value: 3 }
        ],
        // 发布状态
        releaseStateOptions: [
          { text: '未发布', value: 0 },
          { text: '已发布', value: 1 }
        ]
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
          {name: 'releaseState', value: this.filters.releaseState},
          {name: 'auditState', value: 1}
        ]
        this.$api.svc.findSvcPage(this.pageRequest).then((res) => {
          this.pageResult = res.data.data;
        }).then(data!=null?data.callback:'')
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
        }).catch((error) => {
          console.log(error);
        })
      },
      // 审核操作
      handleRelease: function (index, row) {
        let toOperationStr = '';
        let toStatus = '';
        let toStatusNum = 1;
        if (row.releaseState === 0) {
          toOperationStr = '发布';
          toStatus = 'RELEASED';
          toStatusNum = 1;
        } else if (row.releaseState === 1) {
          toOperationStr = '撤销发布';
          toStatus = 'UNRELEASED';
          toStatusNum = 0;
        }
        this.$confirm('确定'+toOperationStr+'该服务吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( () => {
          let params = {ids: row.svcId};
          this.$api.svc.updateReleaseStatus(toStatus, params).then( (res) => {
            if (res.data.code == 200) {
              this.$message({
                message: '操作成功', type: 'success', duration: 2000
              });
              // 更新状态显示
              row.releaseState = toStatusNum;
            } else {
              this.$message({
                message: '操作失败, ' + res.data.message, type: 'error', duration: 2000
              })
            }
          }).catch((error) => {
            console.log(error);
          })
        }).catch( () => {} )
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
      },
      // 清除验证
      clearVail(formName) {
        this.$nextTick(()=>{
          this.$refs[formName].clearValidate();
        })
      },
      // 关闭dialog事件
      handleCloseDialog: function() {
        this.dialogVisible = false;
        this.clearVail('dataForm');
      },

      /** Tab页相关 **/
      handleTabClick(tab, event) {
        console.log(tab, event);
      },

      /** 卡片分页、批量删除相关 **/
      // 换页刷新
      refreshPageRequest: function (pageNum) {
        this.pageRequest.pageNum = pageNum
        this.findPage(null)
      },
      // 每页条目数调整刷新
      handleSizeChange(val) {
        //console.log(`每页 ${val} 条`);
        let params = Object.assign({}, this.pageRequest);
        params.pageSize = val;
        this.pageRequest.pageSize = val;
        this.findPage({"pageRequest": params});
      }

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

  /** 服务信息相关 **/
  .service-table-expand {
    font-size: 0;
  }
  .service-table-expand label {
    width: 90px;
    color: #99a9bf;
    line-height: normal;
    border: 1px solid green;
  }
  .service-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    /*width: 50%;*/
    line-height: normal;
    border: 1px solid red;
    font-size: 12px;
  }
</style>
