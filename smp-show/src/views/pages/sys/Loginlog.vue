<template>
  <div class="">
    <!-- 工具栏 -->
    <div class="toolbar">
      <div class="">
        <el-form :inline="true" :model="filters" :size="size" ref="filters">
          <el-form-item prop="name">
            <el-input v-model="filters.name" placeholder="用户名称"></el-input>
          </el-form-item>
          <el-form-item prop="status">
            <el-select v-model="filters.status" placeholder="请选择状态">
              <el-option
                v-for="item in statusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-search" :label="$t('ACTION.SEARCH')" type="primary"
                        @click="findPage({ pageRequest: {pageNum: 1, pageSize: pageRequest.pageSize} })"
                        perms="sys:loginlog:view"
            />
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-undo" :label="$t('ACTION.RESET')"
                        @click="resetForm('filters')"
                        perms="sys:loginlog:view"
            />
          </el-form-item>
        </el-form>
      </div>
      <div class="">
        <el-form :inline="true" :size="size">
          <el-form-item>
            <el-button-group>
              <el-tooltip :content="$t('ACTION.REFRESH')" placement="top">
                <el-button icon="fa fa-refresh"
                           @click="findPage({ pageRequest: {pageNum: 1, pageSize: pageRequest.pageSize} })"
                           :disabled="!hasPerms('sys:loginlog:view')"
                />
              </el-tooltip>
            </el-button-group>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <!--表格内容栏-->
    <div class="kt-portlet">
      <div class="kt-portlet__body kt-portlet__body--fit">
        <!--表格内容栏-->
        <el-table :data="pageResult.list" :highlight-current-row="true" @selection-change="selectionChange"
                  v-loading="loading" :element-loading-text="$t('ACTION.LOADING')" border stripe
                  show-overflow-tooltip="true" max-height="740" :size="size" align="left" style="width:100%;" >
          <el-table-column type="selection" width="40" header-align="center" align="center"></el-table-column>

          <el-table-column
            fixed="left" v-if="true" width="180px"
            prop="id" header-align="center" align="center" label="ID" sortable show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="userName" header-align="center" align="center" width="180px" label="用户名" sortable show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="status" header-align="center" align="center" width="180" label="状态" sortable>
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status === 'login'" size="mini" effect="plain" type="info">退出登录</el-tag>
              <el-tag v-else-if="scope.row.status === 'online'" size="mini" effect="plain" type="success">在线</el-tag>
              <el-tag v-else size="mini" effect="plain" type="warning">未知</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="ip" header-align="center" align="center" width="220" label="IP" sortable :show-overflow-tooltip="true">
          </el-table-column>
          <el-table-column
            prop="createTime" header-align="center" align="center" width="200" label="创建时间" :formatter="dateFormat" sortable :show-overflow-tooltip="true">
          </el-table-column>
          <el-table-column
            prop="lastUpdateTime" header-align="center" align="center" label="更新时间" :formatter="dateFormat" sortable show-overflow-tooltip>
          </el-table-column>
          <!--<el-table-column
            prop="createBy" header-align="center" align="center" label="创建人">
          </el-table-column>
          <el-table-column
            prop="lastUpdateBy" header-align="center" align="center" label="更新人">
          </el-table-column>-->

          <el-table-column :label="$t('ACTION.OPERATION','ch')" width="120" fixed="right" v-if="showOperation" header-align="center" align="center">
            <template slot-scope="scope">
              <k-t-button icon="fa fa-trash" :label="$t('ACTION.DELETE')" perms="sys:log:delete" :size="size" type="danger" @click="handleDelete(scope.$index, scope.row)" />
            </template>
          </el-table-column>
        </el-table>

        <!--分页栏-->
        <div style="padding: 10px;">
          <k-t-button :label="$t('ACTION.BATCHDELETE')" :size="size" type="danger"
                      @click="handleBatchDelete()"
                      :disabled="this.selections.length===0" style="float:left;"
                      perms="sys:loginlog:delete"
          />
          <el-pagination layout="total, sizes, prev, pager, next, jumper"
                         @size-change="handleSizeChange"
                         @current-change="refreshPageRequest"
                         :current-page="pageRequest.pageNum"
                         :page-size="pageRequest.pageSize"
                         :total="pageResult.totalRow"
                         style="float:right;"
                         :page-sizes="[1, 3, 5, 10, 20, 30]"
                         :disabled="!hasPerms('sys:loginlog:view')"
          >
          </el-pagination>
        </div>


      </div>
    </div>
  </div>
</template>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';
  import KTTable from '@/components/KTTable';
  import KTButton from '@/components/KTButton';
  import { format } from "@/utils/datetime";
  import { hasPermission } from '@/utils/permission/index.js';

  export default {
    name: "Loginlog",
    components: {
      KTTable,
      KTButton
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "系统监控", route: "loginlog" },
        { title: "登录日志"}
      ]);

      // 初始化登录日志列数据
      this.findPage(null);
    },
    data() {
      return {
        size: 'small',
        loading: false,
        filters: {
          name: '', // 用户名称过滤
          status: '' // 用户状态过滤
        },

        pageRequest: {pageNum: 1, pageSize: 10},
        pageResult: {},

        showOperation: true,

        // 列表选中列
        selections: [],

        // 状态选项
        statusOptions: [
          {
            value: 'online',
            label: '在线'
          }, {
            value: 'login',
            label: '退出登录'
          }
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

      /** 操作日志相关 **/
      // 获取分页数据
      findPage: function (data) {
        if(data !== null && typeof data !== "undefined") {
          this.pageRequest = data.pageRequest
        }
        this.pageRequest.params = [
          {name: 'userName', value: this.filters.name},
          {name: 'status', value: this.filters.status}
        ]
        this.$api.loginlog.findPage(this.pageRequest).then( (res) => {
          console.log(res.data.data)
          // 组织操作日志数据
          this.pageResult = res.data.data
        }).then(data != null ? data.callback : '').catch( () => {})
      },
      // 单条删除
      handleDelete: function (row) {
        this.delete([row])
      },
      // 批量删除
      handleBatchDelete: function () {
        console.log(this.selections)
        if (this.selections.length > 0) {
          this.delete(this.selections);
        } else {
          this.$message({ message: '请选择要删除的数据', type: 'error', duration: 2000 })
        }
      },
      // 删除操作
      delete: function (rows) {
        this.$confirm('确认删除选中记录吗？', '提示', {
          type: 'warning',
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(() => {
          this.loading = true;
          console.log(rows);
          this.$api.loginlog.batchDelete(rows).then( (res) => {
            if (res.data.code == 200) {
              this.$message({ message: '删除成功, ' + res.data.message, type: 'success', duration: 2000 })
              this.findPage(null);
            } else {
              this.$message({ message: '操作失败, ' + res.data.message, type: 'error', duration: 2000 })
            }
            this.loading = false;
          })
        }).catch(() => {})
      },


      /** 通用 **/
      // 时间格式化
      dateFormat: function (row, column, cellValue, index) {
        return format(row[column.property])
      },
      // 重置表单
      resetForm(formName) {
        this.$nextTick(()=>{
          this.$refs[formName].resetFields();
        })
      },

      /** 表格分页、批量删除相关 **/
      // 换页刷新
      refreshPageRequest: function (pageNum) {
        this.pageRequest.pageNum = pageNum
        this.findPage()
      },
      // 每页条目数调整刷新
      handleSizeChange(val) {
        //console.log(`每页 ${val} 条`);
        let params = Object.assign({}, this.pageRequest);
        params.pageSize = val;
        this.pageRequest.pageSize = val;
        this.findPage({"pageRequest": params});
      },
      // 选择切换
      selectionChange: function (selections) {
        this.selections = selections;
      }

    }
  }
</script>

<style scoped>

</style>
