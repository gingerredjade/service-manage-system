/*!
* validatemsg.js content v1.0
*
* Copyright 2020
*
* Designed and built by JiangHongyu
*/

export const SvcVMsgBox = {
  "NotNullFlag": '请检查参数完整性！',

  "terminalNotNull": '适用终端类型不为空！',
  "svcStyleNotNull": '服务风格不为空！',
  "deptNameNotNull": '所属机构不为空！',
  "svcSubjectNotNull": '服务主题不为空！',
  "svcTypeNotNull": '服务类型不为空！',
  "svcNameNotNull": '服务名称不为空！',
  "svcAliasNotNull": '服务别名不为空！',
  "svcVersionNotNull": '服务版本不为空！',
  "svcDescNotNull": '服务描述不为空！',
  "svcKeywordNotNull": '服务关键字不为空！',

  "svcCoverageNotNull": '覆盖范围不为空！',
  "svcServiceAreaNotNull": '服务范围不为空！',
  "svcCoordinateSystemNotNull": '坐标系不为空！',
  "svcProjectionTypeNotNull": '投影类型不为空！',
  "svcUpdateCycleNotNull": '更新周期不为空！',



  "DemandFlag": '请检查参数正确性！',

  "svcNameDemand": '仅支持使用2~30个汉字、数字、字母、下划线自由组合，不能以下划线开头和结尾。',
  "svcAliasDemand": '仅支持使用2~50个数字、英文字母、下划线自由组合，不能以下划线开头结尾。',
  "svcVersionDemand": '仅支持使用1~20个数字、字母、下划线、横线、点、加号自由组合。',
  "svcDescDemand": '限长度1~150。',
  "svcKeywordDemand": '仅支持使用1~50个汉字、数字、字母、下划线、横线、点、加号、逗号自由组合。',

  "leftTopLonDemand": '左上角经度格式不对，经度：整数部分为0-180、小数部分为0到6位.',
  "rightBottomLonDemand": '右下角经度格式不对，经度：整数部分为0-180、小数部分为0到6位.',
  "leftTopLatDemand": '左上角纬度格式不对，纬度：整数部分为0-90、小数部分为0到6位.',
  "rightBottomLatDemand": '右下角纬度格式不对，纬度：整数部分为0-90、小数部分为0到6位.',
  "LonLatDemand": '经纬度格式不对，经度：整数部分为0-180、小数部分为0到6位；纬度：整数部分为0-90、小数部分为0到6位.请重新检查输入！',


  "svcNameTip": '支持汉字、数字、字母或下划线，不能以下划线开头和结尾，长度2-30.',
  "svcAliasTip": '支持数字、字母或下划线，不能以下划线开头结尾，长度2-50.',
  "svcVersionTip": '支持数字、字母、下划线、横线、点、加号，长度1-20.',
  "svcDescTip": '请输入服务的简介信息，长度限150.',
  "svcKeywordTip": '支持数字、字母、下划线、横线、点、加号，长度1-50.'

}

export default {
  SvcVMsgBox
}

