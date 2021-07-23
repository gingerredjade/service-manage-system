import axios from '../axios'

/*
 * 用户管理模块
 */

// 保存
export const save = (data) => {
  return axios({
    url: '/user/create',
    method: 'post',
    data
  })
}
// 删除
export const batchDelete = (data) => {
  return axios({
    url: '/user/delete',
    method: 'post',
    data
  })
}
// 分页查询
export const findPage = (data) => {
  return axios({
    url: '/user/list',
    method: 'post',
    data
  })
}
// 导出Excel用户信息
export const exportUserExcelFile = (data) => {
  return axios({
    url: '/user/exportUserExcelFile',
    method: 'post',
    data
  })
}
// 查找用户的菜单权限标识集合
export const findPermissions = (params) => {
  return axios({
    url: '/user/findPermissions',
    method: 'get',
    params
  })
}
// 根据用户名查找
export const findByName = (params) => {
  return axios({
    url: '/user/findByUsername',
    method: 'get',
    params
  })
}
// 更新用户密码
export const updatePassword = (params) => {
  return axios({
    url: '/user/updatePassword',
    method: 'get',
    params
  })
}
// 更新用户状态
export const updateStatus = (toStatus, params) => {
  return axios({
    url: '/user/status/'+toStatus,
    method: 'get',
    params
  })
}

// 统计用户总量
export const countUserGross = () => {
  return axios({
    url: '/user/countUserGross',
    method: 'get'
  })
}

