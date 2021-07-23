<template>
    <div class="">
      <!-- 工具栏 -->
      <div class="toolbar">
        <div class="">
          <el-form :inline="true" :model="filters" :size="size" ref="filters">
            <el-form-item prop="username">
              <el-input v-model="filters.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="nickName">
              <el-input v-model="filters.nickName" placeholder="昵称"></el-input>
            </el-form-item>
            <el-form-item prop="sex">
              <el-select v-model="filters.sex" placeholder="请选择性别">
                <el-option
                  v-for="item in sexOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="status">
              <el-select v-model="filters.status" placeholder="请选择状态">
                <el-option
                  v-for="item in statusOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <k-t-button icon="fa fa-search" :label="$t('ACTION.SEARCH')" type="primary"
                          @click="findPage({ pageRequest: {pageNum: 1, pageSize: pageRequest.pageSize} })"
                          perms="sys:user:view"
              />
            </el-form-item>
            <el-form-item>
              <k-t-button icon="fa fa-plus" :label="$t('ACTION.ADD')" type="primary"
                          @click="handleAdd"
                          perms="sys:user:add"
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
          <el-table :data="pageResult.list" stripe size="mini" style="width: 100%;"
                    v-loading="loading" row-key="id" :element-loading-text="$t('ACTION.LOADING')"
                    border :highlight-current-row="true" @selection-change="selectionChange">
            <el-table-column type="selection" header-align="center" align="center"></el-table-column>
            <el-table-column
              fixed="left" v-if="false" width="80px"
              prop="id" header-align="center" label="ID" show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              fixed="left"
              prop="username" header-align="center" align="center" width="120px" label="用户名" show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="nickName" header-align="center" align="center" width="120" label="昵称" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="sex" header-align="center" align="center" label="性别">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.sex === 1" size="mini" effect="plain" type="info">男</el-tag>
                <el-tag v-else-if="scope.row.sex === 2" size="mini" effect="plain" type="info">女</el-tag>
              </template>
            </el-table-column>
            <el-table-column
              prop="deptName" header-align="center" align="center" width="180" label="机构" :show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
              prop="roleNames" header-align="center" align="center" width="180" label="角色" :show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
              prop="email" header-align="center" align="center" width="180" label="邮箱" :show-overflow-tooltip="true">
            </el-table-column>
            <el-table-column
              prop="mobile" header-align="center" align="center" width="150" label="手机" :show-overflow-tooltip="true">
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
              fixed="right"
              prop="status" header-align="center" align="center" width="130" label="状态" show-overflow-tooltip>
              <template slot-scope="scope">
                <k-t-button v-if="scope.row.status === 1" icon="fa fa-user-o" :label="$t('STATUS.USER_OK')" type="success"
                            @click="handleUpdate(scope.row)"
                            perms="sys:user:edit"
                />
                <k-t-button v-else-if="scope.row.status === 2" icon="fa fa-lock" :label="$t('STATUS.USER_LOCK')" type="warning"
                            @click="handleUpdate(scope.row)"
                            perms="sys:user:edit"
                />
              </template>
            </el-table-column>
            <el-table-column
              fixed="right" header-align="center" align="center" width="185" :label="$t('ACTION.OPERATION')">
              <template slot-scope="scope">
                <k-t-button icon="fa fa-edit" :label="$t('ACTION.EDIT')"
                            perms="sys:user:edit"
                            @click="handleEdit(scope.$index, scope.row)"
                />
                <k-t-button icon="fa fa-trash" :label="$t('ACTION.DELETE')" type="danger"
                            perms="sys:user:delete"
                            @click="handleDelete(scope.row)"
                />
              </template>
            </el-table-column>
          </el-table>
          <!--分页栏-->
          <div style="padding: 10px;">
            <k-t-button :label="$t('ACTION.BATCHDELETE')" :size="size" type="danger"
                        @click="handleBatchDelete()"
                        :disabled="this.selections.length===0" style="float:left;"
                        perms="sys:user:delete"
            />
            <el-pagination layout="total, sizes, prev, pager, next, jumper"
                           @size-change="handleSizeChange"
                           @current-change="refreshPageRequest"
                           :current-page="pageRequest.pageNum"
                           :page-size="pageRequest.pageSize"
                           :total="pageResult.totalRow"
                           style="float:right;"
                           :page-sizes="[1, 3, 5, 10, 20, 30]"
                           :disabled="!hasPerms('sys:user:view')"
            >
            </el-pagination>
          </div>

        </div>
      </div>

      <!--新增编辑界面-->
      <!--<el-dialog :title="operation?'新增':'编辑'" width="40%"-->
      <el-dialog :title="operation?$t('ACTION.ADD'):$t('ACTION.EDIT')" width="40%"
                 :visible.sync="dialogVisible" :close-on-click-modal="false" @close="handleCloseDialog()">
        <el-form :model="dataForm" :rules="dataFormRules" ref="dataForm"
                 :size="size" label-width="80px" label-position="right">
          <el-form-item label="ID" prop="id" v-if="false">
            <el-input v-model="dataForm.id" :disabled="true" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户名" prop="username">
            <el-input v-model="dataForm.username" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickName">
            <el-input v-model="dataForm.nickName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="dataForm.password" type="password" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-select v-model="dataForm.sex" placeholder="请选择" style="width: 100%;">
              <el-option
                v-for="item in sexOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="机构" prop="deptName">
            <popup-tree-input
              :data="deptData"
              :props="deptTreeProps"
              :prop="dataForm.deptName"
              :nodeKey="''+dataForm.deptId"
              :currentChangeHandle="deptTreeCurrentChangeHandle">
            </popup-tree-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="dataForm.email" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="手机" prop="mobile">
            <el-input v-model="dataForm.mobile" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="userRoles" v-if="!operation">
            <el-select v-model="dataForm.userRoles" multiple placeholder="请选择"
                       style="width: 100%;">
              <el-option v-for="item in roles" :key="item.id"
                         :label="item.remark" :value="item.id" >
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
  import PopupTreeInput from '@/components/PopupTreeInputOutBlur';
  import { hasPermission } from '@/utils/permission/index.js';

  export default {
    name: "User",
    components: {
      KTTable,
      KTButton,
      PopupTreeInput
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "权限管理", route: "user" },
        { title: "用户管理"}
      ]);

      // 加载机构树数据
      this.findDeptTree();
      // 初始化用户列数据
      this.findPage(null);
    },
    data() {
      return {
        size: 'small',
        loading: false,

        // 筛选条件表单
        filters: {
          username: '', // 用户名称过滤
          nickName: '',
          status: '',
          sex: ''
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
            { required: true, message: '请输入用户名', trigger: 'blur'},
            { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur'}
          ]
        },
        // 新增编辑界面数据
        dataForm: {
          id: 0,
          username: '',
          password: '123456',
          sex: '',
          deptId: 1,
          deptName: '',
          email: 'test@qq.com',
          mobile: '13889700023',
          status: '',
          userRoles: []
        },
        // 用户机构数据
        deptData: [],
        deptTreeProps: {
          label: 'name',
          children: 'children'
        },
        // 用户拥有的角色集合
        roles: [],

        // 列表选中列
        selections: [],

        // 性别选项
        sexOptions: [
          {
            value: Number('1'),
            label: '男'
          }, {
            value: Number('2'),
            label: '女'
          }
        ],
        // 状态选项
        statusOptions: [
          {
            value: 1,
            label: '正常'
          }, {
            value: 2,
            label: '冻结'
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

      /** 用户相关 **/
      // 获取分页数据
      findPage: function (data) {
        if (data != null) {
          this.pageRequest = data.pageRequest;
        }
        this.pageRequest.params = [
          {name: 'username', value: this.filters.username},
          {name: 'nickName', value: this.filters.nickName},
          {name: 'status', value: this.filters.status},
          {name: 'sex', value: this.filters.sex}
        ];
        this.$api.user.findPage(this.pageRequest).then( (res) => {
          console.log(res.data.data)
          // 组织用户数据
          this.pageResult = res.data.data;
          // 获取用户角色数据
          this.findUserRoles();
        }).then(data != null ? data.callback : '').catch(()=>{})
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
          this.$api.user.batchDelete(rows).then( (res) => {
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
      // 显示新增界面
      handleAdd: function () {
        this.dialogVisible = true;
        this.operation = true;
        this.dataForm = {
          id: 0,
          username: '',
          password: '',
          sex: '',
          deptId: 1,
          deptName: '',
          email: 'test@qq.com',
          mobile: '13889700023',
          status: 1,
          userRoles: []
        }
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.dialogVisible = true;
        this.operation = false;
        this.dataForm = Object.assign({}, row);
        let userRoles = [];
        for(let i=0,len=row.userRoles.length; i<len; i++) {
          userRoles.push(row.userRoles[i].roleId)
        }
        this.dataForm.userRoles = userRoles;
      },
      // 更新状态：正常(1)/冻结锁定(2)
      handleUpdate: function (row) {
        let toOperationStr = '';
        let toStatus = '';
        let toStatusNum = 1;
        if (row.status === 1) {
          toOperationStr = '冻结';
          toStatus = 'FREEZED';
          toStatusNum = 2;
        } else if (row.status === 2) {
          toOperationStr = '解冻';
          toStatus = 'OK';
          toStatusNum = 1;
        }
        this.$confirm('确定'+toOperationStr+'该账户吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( () => {
          let params = {ids: row.id};
          this.$api.user.updateStatus(toStatus, params).then( (res) => {
            if (res.data.code == 200) {
              this.$message({
                message: '操作成功', type: 'success', duration: 2000
              });
              // 更新状态显示
              row.status = toStatusNum;
            } else {
              this.$message({
                message: '操作失败, ' + res.data.message, type: 'error', duration: 2000
              })
            }
          })
        }).catch( () => {} )
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
              let userRoles = [];
              for(let i=0,len=params.userRoles.length; i<len; i++) {
                let userRole = {
                  userId: params.id,
                  roleId: params.userRoles[i]
                }
                userRoles.push(userRole)
              }
              params.userRoles = userRoles;
              this.$api.user.save(params).then((res) => {
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
              })
            }).catch(()=>{});
          }
        })
      },

      /** 角色相关 **/
      // 加载用户角色信息
      findUserRoles: function () {
        this.$api.role.findAll().then( (res) => {
          // 加载角色集合
          this.roles = res.data.data;
        })
      },

      /** 机构相关 **/
      // 获取机构列表
      findDeptTree: function () {
        this.$api.dept.findDeptTree().then((res) => {
          this.deptData = res.data.data;
        }).catch(()=>{})
      },
      // 菜单树选中
      deptTreeCurrentChangeHandle (data, node) {
        this.dataForm.deptId = data.id;
        this.dataForm.deptName = data.name;
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
  div.toolbar {
    display: flex;
    display: -webkit-flex;
    align-items: center;
  }
  div.toolbar > div:last-child {
    margin-left: auto;
  }
</style>


