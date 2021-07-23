/**
 * 邮箱
 * @param {*} s
 */
export function isEmail (s) {
  return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
}

/**
 * 手机号码
 * @param {*} s
 */
export function isMobile (s) {
  return /^1[0-9]{10}$/.test(s)
}

/**
 * 电话号码
 * @param {*} s
 */
export function isPhone (s) {
  return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
}

/**
 * URL地址
 * @param {*} s
 */
export function isURL (s) {
  return /^http[s]?:\/\/.*/.test(s)
}



/** 服务注册表单相关校验 **/
/**
 * 服务别名
 *    仅限数字、英文字符、下划线
 * @param {*} s
 */
export function isServiceAlias (s) {
  // 由数字、26个英文字母或者下划线组成的字符串,且不能以下划线开头和结尾,长度2-50
  //return /^(?!_)(?!.*?_$)\w+$/.test(s)
  return /^(?!_)(?!.*?_$)\w{2,50}$/.test(s)
}

/**
 * 服务名称
 * @param {*} s
 */
export function isServiceName(s) {
  // 只含有汉字、数字、字母、下划线不能以下划线开头和结尾,长度2-30
  //return /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$/.test(s)
  return /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{2,30}$/.test(s)
}

/**
 * 服务版本
 * @param {*} s
 */
export function isServiceVersion(s) {
  // 数字、字母、下划线、横线、点、加号,长度1-20
  return /^([a-z_A-Z-.+0-9]){1,20}$/.test(s)
}

/**
 * 服务描述
 * @param {*} s
 */
export function isServiceDesc(s) {
  // 长度1-150
  return /^.{1,150}$/.test(s)
}

/**
 * 服务关键字
 * @param {*} s
 */
export function isServiceKeyword(s) {
  // 汉字、数字、字母、下划线、横线、点、加号、逗号,长度1-50
  return /^([a-z_A-Z-.+,，0-9\u4e00-\u9fa5]){1,50}$/.test(s)
}

/**
 * 经纬度
 * @param {*} s
 */
// 经度,整数部分为0-180、小数部分为0到6位
export function isLongitude(s) {
  let longreg = /^(\-|\+)?(((\d|[1-9]\d|1[0-7]\d|0{1,3})\.\d{0,6})|(\d|[1-9]\d|1[0-7]\d|0{1,3})|180\.0{0,6}|180)$/;
  return longreg.test(s)
}
// 纬度,整数部分为0-90、小数部分为0到6位
export function isLatitude(s) {
  let latreg = /^(\-|\+)?([0-8]?\d{1}\.\d{0,6}|90\.0{0,6}|[0-8]?\d{1}|90)$/;
  return latreg.test(s)
}




/** 正则数据存储域 **/
const RegularExpression = function () {
  const regularExpression = {
    "usernameRe" : /[^\w\u4e00-\u9fa5]/g, // 含有非法字符
    "re_number" : /[^\d]/g, // 全局匹配非数字的字符
    "re_letter" : /[^a-zA-Z]/g, // 全局匹配非字母的字符
    "emailReg" : /^[A-Za-z\d]+[A-Za-z\d\-_\.]*@([A-Za-z\d]+[A-Za-z\d\-]*\.)+[A-Za-z]{2,4}$/, // 邮箱验证
    "postcodeReg" : /^[1-9]\d{5}$/, // 邮编验证
    "ipReg" : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/, // IPV4地址验证
    "chinese_ideographReg" : /^[\u4E00-\u9FA5]+5/, // 汉字验证
    "integerReg" : /^-?\d+$/, // 整数验证
    "positive_integerReg" : /^[1-9]+\d*$/, // 正整数验证
    "negative_integerReg" : /^-{1}\d+$/, // 负整数验证
    "unnegative_integerReg" : /^\d+$/, // 非负整数验证
    "characterReg" : /^[a-zA-Z]+$/, // 英文字母验证
    "mobilephoneReg" : /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/, // 手机号码验证
    "phoneReg" : /^\d{3}\-\d{8}$|^\d{4}\-\d{7}$/, // 电话号码验证
    "dateReg" : /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/, // 日期（YYYY-MM-DD）验证
    "usernameReg" : /^[a-zA-Z]\w{3,16}$/, // 常用用户名验证(字母开头，允许4-16字节，允许字母数字下划线中文)
    "passwdReg" : /^[a-zA-Z][_a-zA-Z0-9]{3,10}$/, // 常用密码验证(以字母开头，长度4-10之间，只能包含字母、数字和下划线)
    "HexReg" : /^#?([a-f0-9]{6}|[a-f0-9]{3})$/, // 十六进制值
    "URLReg" : /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/ // URL
  };
  return {
    getRegularExpress : function () {
      return regularExpression;
    }
  }

}





