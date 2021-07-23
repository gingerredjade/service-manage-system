/**
 * ***************************************************************
 * 文件名称：changeMap.js
 * 摘   要：地图切换
 * 作   者：yanran
 * 创建时间：2019-07-02
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：
 * ****************************************************************
 */

jQuery(document).ready(function () {
	changeBasicMap();
});

function changeBasicMap() {
	$('#mapChangeBtns div').click(function () {
		var mapIndex = $(this).index();
		changLocalMap(mapIndex);
	});
}

function changLocalMap(mapIndex) {
	if (G_map) {
		var nowCenter = G_map.getCenter();//当前的中心点
		var nowLevel = G_map.getZoomLevel();//当前的级别
		G_map.destroy();
	}

	var mapName;

	switch (mapIndex) {
		case 0://切换到矢量地图
			mapName = G_vectorMapName;
			break;
		case 1://切换到影像地图
			mapName = G_photoMapName;
			break;
		case 2://切换到晕渲地图
			mapName = G_DEMMapName;
			if (nowLevel > 7) {
				nowLevel = G_DEMMaxLevel;
			}
			break;
		default:
			mapName = G_vectorMapName;
			break;
	}

	try {
		loadMap(mapName, null, nowCenter, nowLevel);
	} catch (Error) {
		alert(Error);
	}

};
