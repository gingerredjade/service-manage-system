/**
 * ***************************************************************
 * 文件名称：config.js
 * 摘   要：配置文件
 * 作   者：yanran
 * 创建时间：2019-06-30
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：
 * ****************************************************************
 */

//URL config
var G_mswss = 'http://192.168.55.168:8088/mswss';
var G_terramap = G_mswss + '/maps/api?f=js&v=1&key=1234';
var G_mswss_maps_services = G_mswss + '/maps/services';


//mapName config
var G_vectorMapName = 'worldVec';//矢量地图名称，默认
var G_DEMMapName = 'demchina_MGIS102089';//晕渲地图名称
var G_photoMapName = 'worldVecRas';//影像地图名称

var G_3dMapName = 'worldVecRas';//三维地图名称

var G_thematicMapName = 'black-blue2';//专题地图名称


//mapService config
var G_DEMServerName = 'demchina';//晕渲图地形分析服务名称
var G_DEMServerVersion = 1000;//晕渲图地形分析服务版本号
var G_DEMIsGis3Server = "false";//晕渲图地形分析是否用GIS3服务

var G_routeAnalysisSvcName = 'navi_china';//导航服务名称

var G_SceneSvcName = 'shijing_beijing'; //实景服务


//map center and serf and level config
var G_centerX = 102;
var G_centerY = 35.75;
var G_level = 3;
var G_sref = 'MGIS:4490';
var G_DEMMaxLevel = 7;