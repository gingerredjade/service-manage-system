import axios from '../axios'

/*
 * 服务主题模块
 */

// 保存
export const save = (data) => {
  return axios({
    url: '/style/create',
    method: 'post',
    data
  })
}

// 查询服务风格
export const findSvcStyle = () => {
  return axios({
    // url: API_URL + '/style/listAll',
    url: '/style/listAll',
    method: 'get'
  })
}


// 分页查询服务主题
export const findSvcStylePage = (data) => {
  return axios({
    // url: API_URL + '/style/list',
    url: '/style/list',
    method: 'post',
    data
  })
}

// 删除
export const batchDelete = (data) => {
  return axios({
    // url: API_URL + '/style/delete',
    url: '/style/delete',
    method: 'post',
    data
  })
}


