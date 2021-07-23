import axios from '../axios'

/*
 * 服务管理模块
 */

// 保存
export const save = (data,config) => {
  return axios({
    url: '/service/create',
    method: 'post',
    data
  })
}



// 分页查询服务信息
export const findSvcPage = (data) => {
  return axios({
    url: '/service/list',
    method: 'post',
    data
  })
}

// 删除
export const batchDelete = (data) => {
  return axios({
    url: '/service/delete',
    method: 'post',
    data
  })
}

// 更新服务审核状态
export const updateAuditStatusAndOpinion = (toAuditStatus, params) => {
  return axios({
    url: '/service/status/audit/'+toAuditStatus,
    method: 'get',
    params
  })
}

// 更新服务发布状态
export const updateReleaseStatus = (toReleaseStatus, params) => {
  return axios({
    url: '/service/status/release/'+toReleaseStatus,
    method: 'get',
    params
  })
}

// 获取全部服务目录
export const findCatalogAll = ( params) => {
  return axios({
    url: '/service/catalog/all',
    method: 'get',
    params
  })
}

