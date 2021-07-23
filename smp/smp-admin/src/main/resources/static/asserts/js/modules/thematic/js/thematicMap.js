/**
 * ***************************************************************
 * 文件名称：loadMap.js
 * 摘   要：地图加载
 * 作   者：yanran
 * 创建时间：2019-07-01
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：
 * ****************************************************************
 */

var G_thematicMap = null;
jQuery(document).ready(function () {
	//loadMap();
});


function loadMap(svcname, center, nowCenter, nowLevel) {

	if (typeof svcname != 'string') {
		svcname = G_thematicMapName;
	}
	
	var options = {
		async: true,
		timeout: 3000,
		onMapCreationCompleted: dosuccess,
		onMapCreationError: dofail,
		html5TiledEnabled: false,
		versionLevelEnabled: true, //状态栏显示级别，不显示版权信息
		bSmallModel: true,
		mswssVer: 1.0
	};

	terramap.initialize();

	// 指定使用 theme2 样式
	terramap.maps.setThemeId('theme2');
	terramap.setLocaleType('zh_CN_utf8');


	//同步加载地图
	/*G_thematicMap = terramap.maps.createMap('/maps/b/'+svcname+'?version=1000', 'thematicMap', {debug: true, html5TiledEnabled : true});*/

	//异步加载地图	
	terramap.serviceBaseURL = G_mswss_maps_services;
	terramap.tileServerList = [G_mswss];

	terramap.maps.createMap('/maps/b/' + svcname + '?version=1000', 'thematicMap', options);

	function dosuccess(obj, options) {
		G_thematicMap = obj;
		terramap.initialize();

		if (nowCenter == null) {
			nowCenter = new terramap.GeoPoint(G_centerX * 3600000, G_centerY * 3600000, G_sref);
			nowLevel = G_level;
		} else {
			if (G_thematicMap.getSpatialReference() != nowCenter.sref) {
				nowCenter = nowCenter.convertTo(G_thematicMap.getSpatialReference());
			}
		}

		G_thematicMap.setCenter(nowCenter, nowLevel);
		// 选择默认工具
		G_thematicMap.getToolManager().selectTool(null, true);

		if (G_thematicMap.getControlBarByName(terramap.maps.MAINTOOLBAR)) {
			// 之前已经加入了
			return;
		}

		G_thematicMap.addControlBar(terramap.maps.POSITIONBAR, 10, 200, { defaultPos: true });//状态栏：鼠标坐标	

		if (center) {
			var _pos = new terramap.GeoPoint(center[0], center[1], center[2]);
			G_thematicMap.setCenter(_pos, center[3]);
		}
	}

	function dofail(text, options) {
		alert(text);
	}
}
