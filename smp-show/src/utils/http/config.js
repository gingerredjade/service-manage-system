import { baseUrl } from '@/utils/global'

export default {
  method: 'get',
  // 基础url前缀
  //baseUrl: baseUrl,
  //baseUrl: API_URL,
  // 请求头信息
  headers: {
    'Content-Type': 'application/json;charset=UTF-8'
  },
  // 参数
  data: {},
  // 设置超时时间（毫秒计）
  //timeout: 10000,
  // 携带凭证
  withCredentials: false,   // 允许跨域携带cookie,实现session可以传递
  // 返回数据类型
  responseType: 'json'
}

/*
* axios的withCredentials文档描述是“表示跨域请求时是否需要使用凭证”，
*
* */
