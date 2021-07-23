import axios from '../axios'

/*
 * 服务主题模块
 */

// 保存
export const save = (data) => {
  return axios({
    url: '/subject/create',
    method: 'post',
    data
  })
}

// 查询服务主题
export const findSvcSubject = () => {
  return axios({
    url: '/subject/listAll',
    method: 'get'
  })
}


// 分页查询服务主题
export const findSvcSubjectPage = (data) => {
  return axios({
    url: '/subject/list',
    method: 'post',
    data
  })
}

// 删除
export const batchDelete = (data) => {
  return axios({
    url: '/subject/delete',
    method: 'post',
    data
  })
}


