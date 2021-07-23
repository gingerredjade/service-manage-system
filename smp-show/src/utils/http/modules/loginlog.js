import axios from '../axios'

/*
 * 登录日志模块
 */

// 删除
export const batchDelete = (data) => {
  return axios({
    url: '/loginlog/delete',
    method: 'post',
    data
  })
}
// 分页查询
export const findPage = (data) => {
  return axios({
    url: '/loginlog/list',
    method: 'post',
    data
  })
}
