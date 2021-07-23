<template>
  <div class="">
    <!-- 工具栏 -->
    <div class="toolbar">
      <div class="">
        <el-form :inline="true" :model="filters" :size="size" ref="filters">
          <el-form-item prop="name">
            <el-input v-model="filters.name" placeholder="角色名"></el-input>
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-search" :label="$t('ACTION.SEARCH')" type="primary"
                        @click="findPage({ pageRequest: {pageNum: 1, pageSize: pageRequest.pageSize} })"
                        perms="sys:role:view"
            />
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-plus" :label="$t('ACTION.ADD')" type="primary"
                        @click="handleAdd"
                        perms="sys:role:add"
            />
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-undo" :label="$t('ACTION.RESET')"
                        @click="resetForm('filters')"
                        perms="sys:role:view"
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
                           :disabled="!hasPerms('sys:role:view')"
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
          :showBatchDelete="false" @handleCurrentChange="handleRoleSelectChange"
          @findPage="findPage" @handleEdit="handleEdit" @handleDelete="handleDelete"
          permsDelete="sys:role:delete" permsEdit="sys:role:edit"
          permsPagination="sys:role:view">
        </k-t-table>
      </div>
    </div>

    <!--新增编辑界面-->
    <el-dialog :title="operation?$t('ACTION.ADD') : $t('ACTION.EDIT')" width="40%"
               :visible.sync="dialogVisible" :close-on-click-modal="false" @close="handleCloseDialog()">
      <el-form :model="dataForm" :rules="dataFormRules" ref="dataForm"
               :size="size" label-width="80px" label-position="right">
        <el-form-item label="ID" prop="id" v-if="false">
          <el-input v-model="dataForm.id" :disabled="true" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色名" prop="name">
          <el-input v-model="dataForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="dataForm.remark" auto-complete="off" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :size="size" @click.native="handleCloseDialog()">{{$t('ACTION.CANCEL')}}</el-button>
        <el-button :size="size" type="primary" @click.native="submitForm" :loading="editLoading">{{$t('ACTION.SUBMIT')}}</el-button>
      </div>
    </el-dialog>

    <!--角色菜单，表格树内容栏-->
    <div class="menu-container">
      <h1 class="menu-header">
        <img class="menu-header-title" src="@/assets/media/logos/color-line.png" alt="logo" />
        <span><b>角色菜单授权</b></span>
        <span class="role-menu-tips">授权时应至少赋予一个菜单</span>
      </h1>
      <div class="menu-tool">
        <div class="menu-tool-select-all">
          <el-checkbox v-model="checkAll" @change="handleCheckAll" :disabled="this.selectRole.id == null"><b>{{$t('ACTION.CHECK_ALL')}}</b></el-checkbox>
        </div><!--:disabled="this.selectRole.id == null"-->
        <div class="menu-tool-operation">
          <k-t-button icon="fa fa-undo" type="primary" :label="$t('ACTION.RESET')"
                      @click="resetSelection" :disabled="this.selectRole.id == null"
                      perms="sys:role:edit"
          ></k-t-button>
          <k-t-button icon="fa fa-arrow-right" type="primary" :label="$t('ACTION.SUBMIT')" :loading="authLoading"
                      @click="submitAuthForm" :disabled="this.selectRole.id == null"
                      perms="sys:role:edit"
          ></k-t-button>
        </div>
      </div>
      <el-tree :data="menuData" size="mini" show-checkbox node-key="id" :props="defaultProps"
               style="width: 100%;padding-top: 20px; padding-bottom: 20px;" ref="menuTree" :render-content="renderContent"
               v-loading="menuLoading" element-loading-text="拼命加载中" :check-strictly="true"
               @check-change="handleMenuCheckChange">
      </el-tree>
    </div>


  </div>
</template>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';
  import KTTable from '@/components/KTTable';
  import KTButton from '@/components/KTButton';
  import { format } from "@/utils/datetime";
  import TableTreeColumn from '@/components/TableTreeColumn';
  import { hasPermission } from '@/utils/permission/index.js';

  export default {
    name: "Role",
    components: {
      KTTable,
      KTButton,
      TableTreeColumn
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "权限管理", route: "role" },
        { title: "角色管理"}
      ]);
    },
    data() {
      return {
        size: 'small',
        loading: false,

        // 筛选条件表单
        filters: {
          name: '' // 角色名称过滤
        },
        columns: [
          /*{prop:"id", label:"ID", minWidth:50},*/
          {prop:"name", label:"角色名", minWidth:120},
          {prop:"remark", label:"备注", minWidth:120},
          {prop:"createBy", label:"创建人", minWidth:120},
          {prop:"createTime", label:"创建时间", minWidth:120, formatter: this.dateFormat},
          {prop:"lastUpdateBy", label:"更新人", minWidth:120},
          {prop:"lastUpdateTime", label:"更新时间", minWidth:120, formatter: this.dateFormat}
        ],
        pageRequest: {pageNum: 1, pageSize: 10},
        pageResult: {},

        operation: false, // true:新增, false:编辑
        dialogVisible: false, // 新增编辑界面是否显示
        editLoading: false,
        dataFormRules: {
          name: [
            { required: true, message: '请输入角色名称', trigger: 'blur'},
            { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur'}
          ]
        },

        // 新增编辑界面数据
        dataForm: {
          id: 0,
          name: '',
          remark: ''
        },

        selectRole: {}, // 当前被选角色
        menuData: [], // 菜单树数据
        menuSelections: [],
        menuLoading: false, // 菜单树加载时使用
        authLoading: false, // 提交角色菜单授权按钮时使用
        checkAll: false, // 角色菜单全选按钮控制时使用
        currentRoleMenus: [], // 当前角色对应的角色菜单数据
        defaultProps: {
          children: 'children',
          label: 'name'
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

      /** 角色相关 **/
      // 获取分页数据
      findPage: function (data) {
        if (data != null) {
          this.pageRequest = data.pageRequest;
        }
        this.pageRequest.params = [{name: 'name', value: this.filters.name}];
        this.$api.role.findPage(this.pageRequest).then( (res) => {
          // 组织角色数据
          this.pageResult = res.data.data;
          // 获取菜单树数据
          this.findMenuTreeData();
        }).then(data != null ? data.callback : '').catch(()=>{})
      },
      // 批量删除
      handleDelete: function (data) {
        let delRows = data.rows;
        this.$api.role.batchDelete(delRows).then(data.callback).catch(()=>{})
      },
      // 显示新增界面
      handleAdd: function () {
        this.dialogVisible = true;
        this.operation = true;
        this.dataForm = {
          id: 0,
          name: '',
          remark: ''
        }
      },
      // 显示编辑界面
      handleEdit: function (params) {
        this.dialogVisible = true;
        this.operation = false;
        this.dataForm = Object.assign({}, params.row);
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
              this.$api.role.save(params).then((res) => {
                this.editLoading = false;
                if (res.data.code == 200) {
                  this.$message({
                    message: '操作成功',
                    type: 'success',
                    duration: 2000
                  });
                  this.dialogVisible = false;
                  this.$refs['dataForm'].resetFields();
                } else {
                  this.$message({
                    message: '操作失败, ' + res.data.message,
                    type: 'error',
                    duration: 2000
                  })
                }
                this.findPage(null);
              }).catch(()=>{})
            }).catch(()=>{});
          }
        })
      },
      /** 菜单相关 **/
      // 获取菜单数据
      findMenuTreeData: function () {
        this.menuLoading = true;
        this.$api.menu.findMenuTree().then((res) => {
          this.menuData = res.data.data;
          this.menuLoading = false;
        })
      },
      // 角色选择改变监听的事件
      handleRoleSelectChange(val) {
        if (val == null || val.val == null) {
          return
        }
        this.selectRole = val.val;
        let params = {'roleId':val.val.id};
        this.$api.role.findRoleMenus(params).then((res) => {
          this.currentRoleMenus = res.data.data;
          this.$refs.menuTree.setCheckedNodes(res.data.data);
        }).catch(()=>{})
      },
      // 树节点选择监听的事件
      handleMenuCheckChange(data, check, subCheck) {
        if (check) {
          // 节点选中时同步选中父节点
          let parentId = data.parentId;
          this.$refs.menuTree.setChecked(parentId, true, false);
        } else {
          // 节点取消选中时同步取消选中子节点
          if (data.children != null) {
            data.children.forEach(element => {
              this.$refs.menuTree.setChecked(element.id, false, false);
            });
          }
        }
      },
      // 重置选择
      resetSelection() {
        this.checkAll = false;
        // 设置菜单树选中节点均为空
        this.$refs.menuTree.setCheckedNodes(this.currentRoleMenus);
      },
      // 全选操作
      handleCheckAll() {
        if (this.checkAll) {
          let allMenus = [];
          this.checkAllMenu(this.menuData, allMenus);
          this.$refs.menuTree.setCheckedNodes(allMenus);
        } else {
          this.$refs.menuTree.setCheckedNodes([]);
        }
      },
      // 递归全选
      checkAllMenu(menuData, allMenus) {
        menuData.forEach(menu => {
          allMenus.push(menu);
          if (menu.children) {
            this.checkAllMenu(menu.children, allMenus);
          }
        });
      },
      // 角色菜单授权提交
      submitAuthForm() {
        let roleId = this.selectRole.id;
        if ('admin' == this.selectRole.name) {
          this.$message({ message: '超级管理员拥有所有菜单权限，禁止修改！', type: 'error', duration: 2000 });
          return;
        }
        this.authLoading = true;
        let checkedNodes = this.$refs.menuTree.getCheckedNodes(false, true);
        let roleMenus = [];
        for (let i=0, len=checkedNodes.length; i<len; i++) {
          let roleMenu = { roleId: roleId, menuId: checkedNodes[i].id };
          roleMenus.push(roleMenu);
        }
        this.$api.role.saveRoleMenus(roleMenus).then((res) => {
          if (res.data.code == 200) {
            this.$message({ message: '操作成功, ' + res.data.message, type: 'success', duration: 2000 })
          } else {
            this.$message({ message: '操作失败, ' + res.data.message, type: 'error', duration: 2000 })
          }
          this.authLoading = false;
        }).catch(()=>{})
      },
      // 镭射内容
      renderContent(h, { node, data, store }) {
        return (
          <div class="column-container">
            <span style="text-align: center; margin-right: 80px;">{data.name}</span>
            <span style="text-align: center; margin-right: 80px;">
              <el-tag type={data.type === 0?'':data.type === 1?'success':'info'} size="small">
                {data.type === 0?'目录':data.type === 1?'菜单':'按钮'}
              </el-tag>
            </span>
            <span style="text-align: center; margin-right: 80px;"><i class={data.icon}></i></span>
            <span style="text-align: center; margin-right: 80px;">{data.parentName?data.parentName:'顶级菜单'}</span>
            <span style="text-align: center; margin-right: 80px;">{data.url?data.url:'\t'}</span>
          </div>
        )
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

  /** 菜单展示相关 **/
  div.menu-container {
    margin-top: 30px;
  }
  h1.menu-header {
    padding-bottom: 5px;
    text-align: left;
    font-size: 16px;
    color: #333;/* #5d78ff !important    */
  }
  div.menu-tool {
    display: flex;
    display: -webkit-flex;
    align-items: center;
    margin: 0 0 5px 0;
  }
  div.menu-tool > div:last-child {
    margin-left: auto;
  }
  div.menu-tool div.menu-tool-select-all {

  }
  div.menu-tool div.menu-tool-operation {

  }
  h1.menu-header > img.menu-header-title {
    display: inline-block;
    width: 1.6em;
    height: auto;
    vertical-align: middle;
    margin-right: 10px;
  }
  h1.menu-header > span {
    font-size: 1.1em;
    font-weight: normal;
  }
  h1.menu-header > span.role-menu-tips {
    font-size: 0.8em;
    color: #444444;
    font-weight: 400;
    margin-left: 10px;
  }
</style>
