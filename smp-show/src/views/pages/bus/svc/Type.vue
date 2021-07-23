<template>
  <!-- 工具栏 -->
  <div>
    <div class="toolbar">
      <div class="">
        <el-form :inline="true" :model="filters" :size="size" ref="filters">
          <el-form-item prop="name">
            <el-input v-model="filters.name" placeholder="服务类型名称"></el-input>
          </el-form-item>
          <el-form-item prop="subjectName">
            <el-input v-model="filters.subjectName" placeholder="服务主题名称"></el-input>
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-search" :label="$t('ACTION.SEARCH')" type="primary"
                        @click="findPage({ pageRequest: {pageNum: 1, pageSize: pageRequest.pageSize} })"
                        perms="svc:type:view"
            />
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-plus" :label="$t('ACTION.ADD')" type="primary"
                        @click="handleAdd"
                        perms="svc:type:add"
            />
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-undo" :label="$t('ACTION.RESET')"
                        @click="resetForm('filters')"
                        perms="svc:type:view"
            />
          </el-form-item>
        </el-form>
      </div>
      <div class="">
        <el-form :inline="true" :size="size">
          <el-form-item>
            <el-button-group>
              <el-tooltip :content="$t('ACTION.REFRESH')" placement="top">
                <!--<k-t-button icon="fa fa-spinner" label="刷新" @click="findSubjectPage(null)"/>-->
                <el-button icon="fa fa-refresh"
                           @click="findPage({ pageRequest: {pageNum: 1, pageSize: pageRequest.pageSize} })"
                           :disabled="!hasPerms('svc:type:view')"
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
          :data="pageResult" :columns="columns"
          @findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete"
          permsDelete="svc:type:delete" permsEdit="svc:type:edit" permsPagination="svc:type:view">
        </k-t-table>
      </div>
    </div>

    <!-- 新增编辑界面 -->
    <el-dialog :title="operation?$t('ACTION.ADD') : $t('ACTION.EDIT')" width="40%"
               :visible.sync="dialogVisible" :close-on-click-modal="false" @close="handleCloseDialog()">
      <el-form :model="dataForm" label-width="80px" :rules="dataFormRules" ref="dataForm" :size="size" label-position="right">
        <el-form-item label="TYPEID" prop="typeId" v-if="false">
          <el-input v-model="dataForm.typeId" :disabled="true" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型名称" prop="name">
          <el-input v-model="dataForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属主题" prop="busSvcSubjectDO.subjectId">
          <el-select v-model="dataForm.busSvcSubjectDO.subjectId" placeholder="请选择">
            <el-option
              v-for="item in subjectSelectOptions"
              :key="item.subjectId"
              :label="item.name"
              :value="item.subjectId">
            </el-option>
          </el-select>
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
    name: "Type",
    components: {
      KTTable,
      KTButton
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "服务管理", route: "type" },
        { title: "服务类型"}
      ]);

      this.findSvcSubject();
    },
    data() {
      return {
        size: 'small',
        // 筛选条件表单
        filters: {
          name: '', // 服务类型名称过滤
          subjectName: '' // 服务主题名称过滤
        },
        columns: [
          /*{prop:"typeId", label:"编号", minWidth:50},*/
          {prop:"name", label:"类型名称", minWidth:120},
          {prop:"busSvcSubjectDO.name", label:"所属主题名称", minWidth:120},
          /*{prop:"delFlag", label:"状态", minWidth:50},*/
          {prop:"createBy", label:"创建人", minWidth:120},
          {prop:"createTime", label:"创建时间", minWidth:120, formatter:this.dateFormat},
          {prop:"lastUpdateBy", label:"更新人", minWidth:120},
          {prop:"lastUpdateTime", label:"更新时间", minWidth:120, formatter:this.dateFormat}
        ],
        pageRequest: {pageNum: 1, pageSize: 10},
        pageResult: {},

        operation: false, // true:新增, false:编辑
        dialogVisible: false, // 新增编辑界面是否显示
        editLoading: false,
        dataFormRules: {
          name: [
            { required: true, message: '请输入类型名称', trigger: 'blur' },
            { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
          ],
          'busSvcSubjectDO.subjectId': [
            { required: true, message: '请选择所属主题', trigger: 'change' }
          ]
        },

        // 新增编辑界面数据
        dataForm: {
          typeId: 0,
          name: '',
          delFlag: 1,
          busSvcSubjectDO: {
            subjectId: 0,
            name: ''
          }
        },

        loading: false,
        // 服务主题数据
        subjectSelectOptions: []
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
          {name: 'subjectName', value: this.filters.subjectName}
        ]
        this.$api.svcType.findSvcTypePage(this.pageRequest).then((res) => {
          this.pageResult = res.data.data;
          //console.log(res.data.data)
        }).then(data!=null?data.callback:'').catch((error) => {
          console.log(error);
        })
      },
      // 批量删除(子组件表格中发射触发该事件)
      handleDelete: function (data) {
        let delRows = data.rows;
        // let params = []
        /*for (let i=0; i<delRows.length; i++) {
          params.push('typeId', delRows[i].typeId);
        }*/

        //let idArray = (ids+'').split(',')
        /*for(var i=0; i<idArray.length; i++) {
          params.push({'typeId':idArray[i]})
        }*/
        this.$api.svcType.batchDelete(delRows).then(data!=null?data.callback:'').catch((error) => {
          console.log(error);
        })
      },
      // 显示新增界面
      handleAdd: function () {
        this.dialogVisible = true;
        this.operation = true;
        this.dataForm = {
          typeId: 0,
          name: '',
          delFlag: 1,
          busSvcSubjectDO: {
            name: ''
          }
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
              //console.log(params)
              this.$api.svcType.save(params).then((res) => {
                this.editLoading = false;
                if (res.data.code == 200) {
                  this.$message({ message: res.data.message, type: 'success', duration: 2000 });
                  this.dialogVisible = false;
                  this.$refs['dataForm'].resetFields();
                } else {
                  this.$message({ message: '操作失败，'+res.data.message, type: 'warning', duration: 3000 })
                }
                this.findPage(null);
              })
            }).catch(()=>{});
          }
        })
      },
      // 获取服务主题数据
      findSvcSubject: function () {
        this.$api.svcSubject.findSvcSubject().then((res) => {
          this.loading = true;
          this.subjectSelectOptions = res.data.data;
          this.loading = false;
          //console.log(res.data.data)
        }).catch((error) => {
          console.log(error);
        })
      },
      // 重置表单
      resetForm(formName) {
        //console.log(this.$refs)
        //console.log(this.$refs[formName])
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
        //this.$refs.dataForm.resetFields();
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
