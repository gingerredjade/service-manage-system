<template>
  <div>
    <!-- 工具栏 -->
    <div class="toolbar">
      <div class="">
        <el-form :inline="true" :model="filters" :size="size" ref="filters">
          <el-form-item prop="name">
            <el-input v-model="filters.name" placeholder="一级机构名称"></el-input>
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-search" :label="$t('ACTION.SEARCH')" type="primary"
                        @click="findTreeData(null)"
                        perms="sys:dept:view"
            />
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-plus" :label="$t('ACTION.ADD')" type="primary"
                        @click="handleAdd"
                        perms="sys:dept:add"
            />
          </el-form-item>
          <el-form-item>
            <k-t-button icon="fa fa-undo" :label="$t('ACTION.RESET')"
                        @click="resetForm('filters')"
                        perms="sys:dept:view"
            />
          </el-form-item>
        </el-form>
      </div>
      <div class="">
        <el-form :inline="true" :size="size">
          <el-form-item>
            <el-button-group>
              <el-tooltip :content="$t('ACTION.REFRESH')" placement="top">
                <el-button icon="fa fa-refresh" @click="findTreeData(null)"
                           :disabled="!hasPerms('sys:dept:view')"
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
            fixed="left" v-if="false"
            prop="id" header-align="center" width="180" label="ID" show-overflow-tooltip>
          </el-table-column><!-- prop="id"-->
          <table-tree-column
            fixed="left"
            prop="name" header-align="center" width="200" treeKey="id" label="名称" show-overflow-tooltip>
          </table-tree-column>
          <el-table-column
            header-align="center" align="center" width="300" label="上级机构">
            <template slot-scope="scope">{{scope.row.parentName==null?'顶级机构':scope.row.parentName}}</template>
            <!-- prop="parentName" -->
          </el-table-column>
          <el-table-column
            prop="address" header-align="center" width="500" label="机构地址" show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="website" header-align="center" width="250" label="机构网站" show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="contact" header-align="center" align="center" width="150" label="联系人" show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="phoneNumber" header-align="center" align="center" width="150" label="联系电话" show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="email" header-align="center" align="center" width="200" label="联系人邮箱" show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="orderNum" header-align="center" align="center" label="排序">
          </el-table-column>
          <el-table-column
            prop="createBy" header-align="center" align="center" label="创建人">
          </el-table-column>
          <el-table-column
            prop="createTime" header-align="center" align="center" width="150" label="创建时间" :formatter="dateFormat">
          </el-table-column>
          <el-table-column
            prop="lastUpdateBy" header-align="center" align="center" label="更新人">
          </el-table-column>
          <el-table-column
            prop="lastUpdateTime" header-align="center" align="center" width="150" label="更新时间" :formatter="dateFormat">
          </el-table-column>
          <el-table-column
            fixed="right" header-align="center" align="center" width="185" :label="$t('ACTION.OPERATION')">
            <template slot-scope="scope">
              <k-t-button icon="fa fa-edit" :label="$t('ACTION.EDIT')" perms="sys:dept:edit" @click="handleEdit(scope.$index, scope.row)"/>
              <k-t-button icon="fa fa-trash" :label="$t('ACTION.DELETE')" perms="sys:dept:delete" type="danger" @click="handleDelete(scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 新增修改界面 -->
    <el-dialog :title="operation ? $t('ACTION.ADD') : $t('ACTION.EDIT')" width="40%"
               :visible.sync="dialogVisible" :close-on-click-modal="false" @close="handleCloseDialog()">
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="submitForm()"
               label-width="80px" :size="size" style="text-align:left;">
        <el-form-item label="名称" prop="name">
          <el-input v-model="dataForm.name" placeholder="名称"></el-input>
        </el-form-item>

        <el-form-item label="上级机构" prop="parentName" v-if="dataForm.parentName==null">
          <el-input v-if="dataForm.parentName==null" :readonly="true" :disabled="true" placeholder="顶级机构"></el-input>
        </el-form-item>
        <el-form-item label="上级机构" prop="parentName" v-else
                      :rules="{ required: true, message: '上级机构不能为空', trigger: 'blur' }">
          <popup-tree-input
                            :data="popupTreeData" :props="popupTreeProps" :prop="dataForm.parentName==null?'顶级菜单':dataForm.parentName"
                            :nodeKey="''+dataForm.parentId" :currentChangeHandle="handleTreeSelectChange">
          </popup-tree-input>
        </el-form-item>

        <!--<el-form-item v-if="dataForm.type !== 2" label="排序编号" prop="orderNum">
          <el-input-number v-model="dataForm.orderNum" controls-position="right" :min="0" label="排序编号"></el-input-number>
        </el-form-item>-->
        <el-form-item label="地址" prop="address">
          <el-input v-model="dataForm.address" placeholder="机构地址"></el-input>
        </el-form-item>
        <el-form-item label="网站" prop="website">
          <el-input v-model="dataForm.website" placeholder="机构网站"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="contact">
          <el-input v-model="dataForm.contact" placeholder="联系人"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phoneNumber">
          <el-input v-model="dataForm.phoneNumber" placeholder="联系电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="dataForm.email" placeholder="联系人邮箱"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button :size="size"  @click="handleCloseDialog()">{{$t('ACTION.CANCEL')}}</el-button>
        <el-button :size="size"  type="primary" @click="submitForm()" >{{$t('ACTION.SUBMIT')}}</el-button>
      </span>
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
  import { hasPermission } from '@/utils/permission/index.js';

  export default {
    name: "Dept",
    components: {
      KTTable,
      KTButton,
      TableTreeColumn,
      PopupTreeInput
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "权限管理", route: "dept" },
        { title: "机构管理"}
      ]);

      this.findTreeData();
    },
    data() {
      return {
        size: 'small',
        loading: false,

        // 筛选条件表单
        filters: {
          name: '' // 机构名称过滤
        },
        tableTreeData: [],
        popupTreeData: [],
        popupTreeProps: {
          label: "name",
          children: "children"
        },

        operation: false, // true:新增, false:编辑
        dialogVisible: false, // 新增编辑界面是否显示
        editLoading: false,

        dataForm: {
          id: 0,
          name: '',
          parentId: 0,
          parentName: '',
          orderNum: 0,
          address: '',
          website: '',
          contact: '',
          phoneNumber: '',
          email: ''
        },
        dataRule: {
          name: [
            { required: true, message: '机构名称不能为空', trigger: 'blur' }
          ]
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

      // 获取机构数据
      findTreeData: function() {
        this.loading = true;
        let params = { 'name': this.filters.name }
        this.$api.dept.findDeptTree(params).then((res) => {
          this.tableTreeData = res.data.data;
          this.popupTreeData = this.getParentMenuTree(res.data.data);
          this.loading = false;
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
        this.dataForm.parentId = data.id;
        this.dataForm.parentName = data.name;
      },
      // 显示新增界面
      handleAdd: function () {
        this.dialogVisible = true;
        this.operation = true;
        this.dataForm = {
          id: 0,
          name: '',
          parentId: 0,
          parentName: '',
          orderNum: 0,
          address: '',
          website: '',
          contact: '',
          phoneNumber: '',
          email: ''
        }
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.dialogVisible = true;
        this.operation = false;
        Object.assign(this.dataForm, row);
      },
      // 批量删除(子组件表格中发射触发该事件)
      handleDelete: function (row) {
        this.$confirm('确认删除选中记录吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( () => {
          console.log(row);
          /*let params = this.getDeleteIds([], row);*/
          let params = [{id: row.id, name: row.name, parentId: row.parentId, children: row.children}];
          this.$api.dept.batchDelete(params).then( res => {
            if (res.data.code == 200) {
              this.$message({ message: '删除成功, ' + res.data.message, type: 'success', duration: 2000 })
              this.findTreeData();
            } else {
              this.$message({ message: '操作失败, ' + res.data.message, type: 'error', duration: 2000 })
            }
          })
        }).catch(() => {});
      },
      // 获取删除的包含子机构的id列表(包含id、name、parentId)
      /*getDeleteIds (ids, row) {
        ids.push({id:row.id, name: row.name, parentId: row.parentId, children: row.children});
        if (row.children != null) {
          for (let i=0, len=row.children.length; i<len; i++) {
            this.getDeleteIds(ids, row.children[i]);
          }
        }
        return ids;
      },*/
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
              this.$api.dept.save(params).then((res) => {
                this.editLoading = false;
                if (res.data.code == 200) {
                  this.$message({ message: '操作成功', type: 'success', duration: 2000 });
                  this.dialogVisible = false;
                  this.$refs['dataForm'].resetFields();
                } else {
                  this.$message({ message: '操作失败'+res.data.message, type: 'error', duration: 2000 })
                }
                this.findTreeData();
              })
            }).catch(()=>{});
          }
        })
      },
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
