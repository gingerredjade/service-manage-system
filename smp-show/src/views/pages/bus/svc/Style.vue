<template>
  <!-- 工具栏 -->
  <div>
    <div class="toolbar">
      <div class="">
        <el-form :inline="true" :model="filters" :size="size" ref="filters">
          <el-form-item prop="name">
            <el-input v-model="filters.name" placeholder="风格名称"></el-input>
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-search" :label="$t('ACTION.SEARCH')" type="primary"
                        @click="findPage({ pageRequest: {pageNum: 1, pageSize: pageRequest.pageSize} })"
                        perms="svc:style:view"

            />
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-plus" :label="$t('ACTION.ADD')" type="primary"
                        @click="handleAdd"
                        perms="svc:style:add"
            />
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-undo" :label="$t('ACTION.RESET')"
                        @click="resetForm('filters')"
                        perms="svc:style:view"
            />
          </el-form-item>
        </el-form>
      </div>
      <div class="">
        <el-form :inline="true" :size="size">
          <el-form-item>
            <el-button-group>
              <el-tooltip :content="$t('ACTION.RESET')" placement="top">
                <!--<k-t-button icon="fa fa-spinner" label="刷新" @click="findSubjectPage(null)"/>-->
                <el-button icon="fa fa-refresh"
                           @click="findPage({ pageRequest: {pageNum: 1, pageSize: pageRequest.pageSize} })"
                           :disabled="!hasPerms('svc:style:view')"
                />
              </el-tooltip>
            </el-button-group>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="kt-portlet">
      <div class="kt-portlet__body kt-portlet__body--fit">
        <!--表格内容栏-->
        <k-t-table
          :highlightCurrentRow="true" :stripe="false"
          :data="pageResult" :columns="filterColumns"
          @findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete"
          permsDelete="svc:style:delete" permsEdit="svc:style:edit" permsPagination="svc:style:view">
        </k-t-table>
      </div>
    </div>

    <!-- 新增编辑界面 -->
    <el-dialog :title="operation ? $t('ACTION.ADD') : $t('ACTION.EDIT')" width="40%"
               :visible.sync="dialogVisible" :close-on-click-modal="false" @close="handleCloseDialog()">
      <el-form :model="dataForm" label-width="80px" :rules="dataFormRules" ref="dataForm" :size="size" label-position="right">
        <el-form-item label="SUBJECTID" prop="subjectId" v-if="false">
          <el-input v-model="dataForm.subjectId" :disabled="true" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="风格名称" prop="name">
          <el-input v-model="dataForm.name" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :size="size" @click.native="handleCloseDialog()">{{$t('ACTION.CANCEL')}}</el-button>
        <el-button :size="size" type="primary" @click.native="submitForm" :loading="editLoading">{{$t('ACTION.SUBMIT')}}</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';
  import KTTable from '@/components/KTTable';
  import KTButton from '@/components/KTButton';
  import { format } from "@/utils/datetime";
  import { hasPermission } from '@/utils/permission/index.js';

  export default {
    name: "Style",
    components: {
      KTTable,
      KTButton
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "服务管理", route: "style" },
        { title: "服务风格"}
      ]);

      this.initColumns();
    },
    data() {
      return {
        size: 'small',
        // 筛选条件表单
        filters: {
          name: '' // 服务风格名称
        },
        columns: [],
        filterColumns: [],

        pageRequest: {pageNum: 1, pageSize: 10},
        pageResult: {},

        operation: false, // true:新增, false:编辑
        dialogVisible: false, // 新增编辑界面是否显示
        editLoading: false,
        dataFormRules: {
          name: [
            { required: true, message: '请输入风格名称', trigger: 'blur'},
            { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur'}
          ]
        },

        // 新增编辑界面数据
        dataForm: {
          styleId: 0,
          name: '',
          delFlag: 1
        }
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
        this.pageRequest.params = [{name: 'name', value: this.filters.name}]
        this.$api.svcStyle.findSvcStylePage(this.pageRequest).then((res) => {
          this.pageResult = res.data.data;
        }).then(data!=null?data.callback:'').catch((error) => {
          console.log(error);
        })
      },
      // 批量删除
      handleDelete: function (data) {
        let delRows = data.rows;
        this.$api.svcStyle.batchDelete(delRows).then(data!=null?data.callback:'').catch(err => {})
      },
      // 显示新增界面
      handleAdd: function () {
        this.dialogVisible = true;
        this.operation = true;
        this.dataForm = {
          styleId: 0,
          name: '',
          delFlag: 1
        }
      },
      // 显示编辑界面
      handleEdit: function (params) {
        this.dialogVisible = true;
        this.operation = false;
        this.dataForm = Object.assign({}, params.row);
      },
      // 时间格式化
      dateFormat: function (row, column, cellValue, index) {
        return format(row[column.property])
      },
      // 初始化表格列显示
      initColumns: function (data) {
        this.columns = [
          /*{prop:"styleId", label:"编号", minWidth:50},*/
          {prop:"name", label:"风格名称", minWidth:120},
          /*{prop:"delFlag", label:"状态", minWidth:50},*/
          {prop:"createBy", label:"创建人", minWidth:120},
          {prop:"createTime", label:"创建时间", minWidth:120, formatter:this.dateFormat},
          {prop:"lastUpdateBy", label:"更新人", minWidth:120},
          {prop:"lastUpdateTime", label:"更新时间", minWidth:120, formatter:this.dateFormat}
        ]
        //this.filterColumns = JSON.parse(JSON.stringify(this.columns));
        this.filterColumns = this.columns;
      },
      // 编辑-提交
      submitForm: function () {
        this.$refs.dataForm.validate( (valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then( () => {
              this.editLoading = true;
              let params = Object.assign({}, this.dataForm);
              this.$api.svcStyle.save(params).then((res) => {
                this.editLoading = false;
                if (res.data.code == 200) {
                  this.$message({ message: res.data.message, type: 'success', duration: 2000 });
                  this.dialogVisible = false;
                  this.$refs['dataForm'].resetFields();
                } else {
                  this.$message({ message: '操作失败，'+res.data.message, type: 'warning', duration: 2000 })
                }
                this.findPage(null);
              })
            }).catch(()=>{});
          }
        })
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
</style>
