<template>
  <div class="">
    <!-- 工具栏 -->
    <div class="toolbar">
      <div class="">
        <el-form :inline="true" :model="filters" :size="size" ref="filters">
          <!--<el-form-item prop="name">
            <el-input v-model="filters.name" placeholder="角色名"></el-input>
          </el-form-item>-->
          <el-form-item>
            <k-t-button icon="fa fa-search" :label="$t('ACTION.SEARCH')" type="primary"
                        @click="findTreeData(null)"
                        perms="sys:menu:view"
            />
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-plus" :label="$t('ACTION.ADD')" type="primary"
                        @click="handleAdd"
                        perms="sys:menu:add"
            />
          </el-form-item>
          <!--<el-form-item>
            <k-t-button icon="fa fa-undo" :label="$t('ACTION.RESET')" @click="resetForm('filters')" />
          </el-form-item>-->
        </el-form>
      </div>
      <div class="">
        <el-form :inline="true" :size="size">
          <el-form-item>
            <el-button-group>
              <el-tooltip :content="$t('ACTION.REFRESH')" placement="top">
                <el-button icon="fa fa-refresh" @click="findTreeData(null)"
                           :disabled="!hasPerms('sys:menu:view')"
                />
              </el-tooltip>
            </el-button-group>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <div class="kt-portlet">
      <div class="kt-portlet__body kt-portlet__body--fit">
        <!--表格树内容栏-->
        <el-table :data="tableTreeData" stripe size="mini" style="width: 100%;"
                  v-loading="loading" row-key="id" :element-loading-text="$t('ACTION.LOADING')"
                  border>
          <el-table-column
            fixed="left" v-if="false" width="260px"
            prop="id" header-align="center" label="ID" show-overflow-tooltip>
          </el-table-column><!-- prop="id"-->
          <table-tree-column
            fixed="left"
            prop="name" header-align="center" width="260px" treeKey="id" label="名称" show-overflow-tooltip>
          </table-tree-column>
          <el-table-column
            header-align="center" align="center" label="图标">
            <template slot-scope="scope">
              <i :class="scope.row.icon || ''"></i>
            </template>
          </el-table-column>
          <el-table-column
            prop="type" header-align="center" align="center" label="类型" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-tag v-if="scope.row.type === 0" size="small">目录</el-tag>
              <el-tag v-else-if="scope.row.type === 1" size="small" type="success">菜单</el-tag>
              <el-tag v-else-if="scope.row.type === 2" size="small" type="info">按钮</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="parentName" header-align="center" align="center" width="150" label="上级菜单" show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="url" header-align="center" align="center" width="260" label="菜单URL" :show-overflow-tooltip="true">
          </el-table-column>
          <el-table-column
            prop="perms" header-align="center" align="center" width="260" label="授权标识" :show-overflow-tooltip="true">
          </el-table-column>
          <el-table-column
            fixed="right" header-align="center" align="center" width="185" :label="$t('ACTION.OPERATION')">
            <template slot-scope="scope">
              <k-t-button icon="fa fa-edit" :label="$t('ACTION.EDIT')" perms="sys:dept:edit" @click="handleEdit(scope.row)"/>
              <k-t-button icon="fa fa-trash" :label="$t('ACTION.DELETE')" perms="sys:dept:delete" type="danger" @click="handleDelete(scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!--新增修改界面-->
    <el-dialog :title="!dataForm.id ? $t('ACTION.ADD') : $t('ACTION.EDIT')" width="40%"
               :visible.sync="dialogVisible" :close-on-click-modal="false" @close="handleCloseDialog()">
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="submitForm()"
               :size="size" label-width="80px" label-position="right">
        <el-form-item label="菜单类型" prop="type">
          <el-radio-group v-model="dataForm.type" @change="radioCheck(dataForm.type)">
            <el-radio v-for="(type, index) in menuTypeList" :label="index" :key="index">{{ type }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="menuTypeList[dataForm.type] + '名称'" prop="name">
          <el-input v-model="dataForm.name" auto-complete="off" :placeholder="menuTypeList[dataForm.type] + '名称'"></el-input>
        </el-form-item>

        <el-form-item label="上级菜单" prop="parentName" v-if="dataForm.parentName==null||dataForm.type===0">
          <el-input :readonly="true" :disabled="true" placeholder="顶级菜单"></el-input>
        </el-form-item><!-- dataForm.parentName==='' -->
        <el-form-item label="上级菜单" prop="parentName" v-else
                      :rules="{ required: true, message: '上级菜单不能为空', trigger: 'blur' }">
          <popup-tree-input
            :data="popupTreeData"
            :props="popupTreeProps" :prop="dataForm.parentName==null||dataForm.parentName===''?'顶级菜单':dataForm.parentName"
            :nodeKey="''+dataForm.parentId" :currentChangeHandle="handleTreeSelectChange">
          </popup-tree-input>
        </el-form-item>

        <el-form-item label="授权标识" prop="perms" v-if="dataForm.type === 2">
          <el-input v-model="dataForm.perms" auto-complete="off" placeholder="请输入权限标识，多个以“,”隔开，如： sys:user:add, sys:user:edit, sys:user:delete"></el-input>
        </el-form-item>

        <el-form-item label="菜单路由" prop="url" v-if="dataForm.type === 1">
          <el-row>
            <el-col :span="22">
              <el-input v-model="dataForm.url" placeholder="菜单路由"></el-input>
            </el-col>
            <el-col :span="2" class="icon-list__tips">
              <el-tooltip placement="top" effect="light">
                <div slot="content">
                  <p>URL格式：</p>
                  <p>1.常规业务开发的功能URL，如用户管理，/sys/user。</p>
                  <p>2.嵌套完整外部页面，以http(s)开头的链接，如通过菜单打开百度首页，此处填写http://www.baidu.com，http:// 不可省略。</p>
                  <p>示例：用户管理：/sys/user  嵌套百度：http://www.baidu.com  嵌套网页：http://127.0.0.1:8000</p>
                </div>
                <i class="el-icon-warning">查阅</i>
              </el-tooltip>
            </el-col>
          </el-row>
        </el-form-item>
        <!--<el-form-item label="排序编号" prop="orderNum" v-if="dataForm.type !== 2">
          <el-input v-model="dataForm.orderNum" controls-position="right" :min="0" type="textarea"></el-input>
        </el-form-item>-->
        <!--目录、菜单可以设置图标，按钮不能设置图标-->
        <el-form-item label="菜单图标" prop="icon" v-if="dataForm.type !== 2">
          <el-row>
            <el-col :span="22">
              <el-input v-model="dataForm.icon" v-popover:iconListPopover :readonly="false"
                        placeholder="菜单图标名称（如：fa fa-home fa-lg）" class="icon-list__input"></el-input>
            </el-col>
            <el-col :span="2" class="icon-list__tips">
              <fa-icon-tooltip/>
            </el-col>
          </el-row>
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
  import TableTreeColumn from '@/components/TableTreeColumn';
  import { format } from "@/utils/datetime";
  import PopupTreeInput from '@/components/PopupTreeInputOutBlur';
  import FaIconTooltip from '../../../components/FaIconTooltip/index';
  import { hasPermission } from '@/utils/permission/index.js';


  export default {
    name: "Menu",
    components: {
      FaIconTooltip,
      KTTable,
      KTButton,
      TableTreeColumn,
      PopupTreeInput,
      FaIconTooltip
    },
    data() {
      return {
        size: 'small',
        loading: false,

        // 筛选条件表单
        filters: {
          name: '' // 菜单名称过滤
        },
        tableTreeData: [],

        //operation: false, // true:新增, false:编辑
        dialogVisible: false, // 新增编辑界面是否显示
        editLoading: false,

        menuTypeList: ["目录", "菜单", "按钮"],
        dataForm: {
          id: 0,
          type: 0,
          name: '',
          parentId: 0,
          parentName: '',
          url: '',
          perms: '',
          orderNum: 0,
          icon: ''
        },
        dataRule: {
          name: [
            { required: true, message: '菜单名称不能为空', trigger: 'blur' }
          ]
        },

        popupTreeData: [],
        popupTreeProps: {
          label: "name",
          children: "children"
        },
      }
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "权限管理", route: "menu" },
        { title: "菜单管理"}
      ]);

      this.findTreeData();
    },
    methods: {
      /** 权限控制相关-页面按钮权限 **/
      hasPerms: function (perms) {
        // 根据权限标识和外部指示状态进行权限判断
        // ('&'表示运算符按位与，都为1才为1否则为0。也类似于且，都真才为真)
        return hasPermission(perms) & !this.disabled
      },

      /** 菜单相关 **/
      // 获取菜单数据（不分页）
      findTreeData: function() {
        this.loading = true;
        //let params = { 'name': this.filters.name }
        this.$api.menu.findMenuTree().then(res => {
          this.tableTreeData = res.data.data;
          this.popupTreeData = this.getParentMenuTree(res.data.data);
          this.loading = false;
        }).catch(()=>{});
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
      // 菜单树选中事件
      handleTreeSelectChange: function (data, node) {
        this.dataForm.parentId = data.id;
        this.dataForm.parentName = data.name;
      },
      // 菜单树图标选中事件
      iconActiveHandle(iconName) {
        this.dataForm.icon = iconName;
      },
      // 显示新增界面
      handleAdd: function () {
        this.dialogVisible = true;
        //this.operation = true;
        this.dataForm = {
          id: 0,
          type: 0,
          name: '',
          parentId: 0,
          parentName: '',
          url: '',
          perms: '',
          orderNum: 0,
          icon: ''
        };
      },
      // 显示编辑界面
      handleEdit: function (row) {
        this.dialogVisible = true;
        //this.operation = false;
        Object.assign(this.dataForm, row);
      },
      // 删除
      handleDelete: function (row) {
        this.$confirm('确认删除选中记录吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( () => {
          console.log(row);
          let params = [{id: row.id, name: row.name, parentId: row.parentId, children: row.children}];
          this.$api.menu.batchDelete(params).then( res => {
            if (res.data.code == 200) {
              this.$message({ message: '删除成功, ' + res.data.message, type: 'success', duration: 2000 })
              this.findTreeData();
            } else {
              this.$message({ message: '操作失败, ' + res.data.message, type: 'error', duration: 2000 })
            }
          }).catch(()=>{})
        });
      },
      // 编辑-提交
      submitForm() {
        this.$refs.dataForm.validate( (valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then( () => {
              this.editLoading = true;
              let params = Object.assign({}, this.dataForm);
              this.$api.menu.save(params).then((res) => {
                this.editLoading = false;
                if (res.data.code == 200) {
                  this.$message({ message: '操作成功', type: 'success', duration: 2000 });
                  this.dialogVisible = false;
                  this.$refs['dataForm'].resetFields();
                } else {
                  this.$message({ message: '操作失败'+res.data.message, type: 'error', duration: 2000 })
                }
                this.findTreeData();
              }).catch(()=>{})
            }).catch(()=>{});
          }
        })
      },
      radioCheck(val) {
        this.clearVail('dataForm');
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
</style>
