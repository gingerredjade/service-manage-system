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

var G_map = null;
jQuery(document).ready(function () {
	loadMap();
});


function loadMap(svcname, center, nowCenter, nowLevel) {

	if (typeof svcname != 'string') {
		svcname = G_vectorMapName;
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
	/*G_map = terramap.maps.createMap('/maps/b/'+svcname+'?version=1000', 'outerDiv', {debug: true, html5TiledEnabled : true});*/

	//异步加载地图	
	terramap.serviceBaseURL = G_mswss_maps_services;
	terramap.tileServerList = [G_mswss];

	terramap.maps.createMap('/maps/b/' + svcname + '?version=1000', 'outerDiv', options);

	function dosuccess(obj, options) {
		G_map = obj;
		terramap.initialize();

		if (nowCenter == null) {
			nowCenter = new terramap.GeoPoint(G_centerX * 3600000, G_centerY * 3600000, G_sref);
			nowLevel = G_level;
		} else {
			if (G_map.getSpatialReference() != nowCenter.sref) {
				nowCenter = nowCenter.convertTo(G_map.getSpatialReference());
			}
		}

		//路径规划初始化
		netanalysis_init(G_map);

		G_map.setCenter(nowCenter, nowLevel);
		// 选择默认工具
		G_map.getToolManager().selectTool(null, true);

		if (G_map.getControlBarByName(terramap.maps.MAINTOOLBAR)) {
			// 之前已经加入了
			return;
		}

		G_map.addControlBar(terramap.maps.POSITIONBAR, 10, 200, { defaultPos: true });//状态栏：鼠标坐标	

		if (center) {
			var _pos = new terramap.GeoPoint(center[0], center[1], center[2]);
			G_map.setCenter(_pos, center[3]);
		}

		if (svcname == G_DEMMapName) {//地形分析初始化
			var evtMg;
			topoanaly_init(G_map, evtMg); 
		}
	}

	function dofail(text, options) {
		alert(text);
	}
}


function loadOGCMap(ogctype,svcname,url ,tilematrixset,sref,version) {
	if (G_map) {
		G_map.destroy();
	}
	//ogctype,svcname,url,tilematrixset
	if(ogctype=='wmts'){
		var source_param = {
			url : url,
			TileMatrixSet: tilematrixset,
			//TileMatrixSet: "GoogleMapsCompatible_World",
			charset : "utf-8"
		};
		var options = {
			debug : true,
			ogctype : "wmts",
			source_cls : terramap.source.WMTSource,
			source_param : source_param
		};
		G_map = terramap.maps.createMap(svcname, "outerDiv", options);
	}
	//goctype,svcname,url,sref
	if(ogctype=='xyz'){
		var source_param = {
			url : url,
			sref : sref
		};
		var options = {
			ogctype : "xyz",
			source_cls : terramap.source.XYZSource,
			source_param : source_param
		};
		G_map = terramap.maps.createMap(svcname, "outerDiv", options);
	}
	//ogctype,version,url,sref
	if(ogctype=='wcs'){
		//http://192.168.56.179:8090/iserver/services/data-world/wcs112
		var source_param = {
			url : url,
			sref : sref,
			version : version,
			charset : "utf-8"
		};
		var options = {
			ogctype : "wcs",
			source_cls : terramap.source.WCSource,
			source_param : source_param
		};
		G_map = terramap.maps.createMap(svcname, "outerDiv", options);
	}

	if(ogctype=='wms'){
		var source_param = {
			url : url,
			charset : "utf-8",
			sref : sref
		};
		var options = {
			debug : true,
			ogctype : "wms",
			source_cls : terramap.source.WMSSource,
			source_param : source_param
		};
		G_map = terramap.maps.createMap(svcname, "outerDiv", options);
	}
	try{
		G_map.showFullExtent();
	}catch (e){
		alert("地图初始化失败，请确认添加的地图源服务");
		return false;
	}

	G_map.getToolManager().selectTool(null, true);

	//路径规划初始化
	netanalysis_init(G_map);

	if (G_map.getControlBarByName(terramap.maps.MAINTOOLBAR)) {
		// 之前已经加入了
		return;
	}

	G_map.addControlBar(terramap.maps.POSITIONBAR, 10, 200, { defaultPos: true });//状态栏：鼠标坐标

	if (svcname == G_DEMMapName) {//地形分析初始化
		var evtMg;
		topoanaly_init(G_map, evtMg);
	}

}