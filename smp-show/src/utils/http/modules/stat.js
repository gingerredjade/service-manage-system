import axios from '../axios'

/*
 * 服务统计模块
 */

// 根据服务主题统计
export const countBySubject = () => {
  return axios({
    url: '/stat/subject',
    method: 'get'
  })
}

// 根据服务类型统计
export const countByType = () => {
  return axios({
    url: '/stat/type',
    method: 'get'
  })
}

// 根据机构统计
export const countByDept = () => {
  return axios({
    url: '/stat/dept',
    method: 'get'
  })
}

// 根据适用终端类型统计
export const countByTerminal = () => {
  return axios({
    url: '/stat/terminal',
    method: 'get'
  })
}

// 根据GIS/非GIS服务统计
export const countByGisOrNo = () => {
  return axios({
    url: '/stat/gis',
    method: 'get'
  })
}

// 针对服务的发布状态、审核状态统计对应服务数量
export const countBySvcState = () => {
  return axios({
    url: '/stat/countByState',
    method: 'get'
  })
}
