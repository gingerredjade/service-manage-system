import axios from '../axios'

/*
 * 服务类型模块
 */

// 保存
export const save = (data) => {
  return axios({
    url: '/type/create',
    method: 'post',
    data
  })
}


// 通过主题编号查询服务类型
export const findSvcType = (params) => {
  return axios({
    url: '/type/listBySubjectId',
    method: 'get',
    params
  })
}

// 分页查询服务类型
export const findSvcTypePage = (data) => {
  return axios({
    url: '/type/list',
    method: 'post',
    data
  })
}


// 删除
export const batchDelete = (data) => {
  return axios({
    url: '/type/delete',
    method: 'post',
    data
  })
}


