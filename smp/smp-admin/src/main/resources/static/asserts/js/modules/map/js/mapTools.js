/**
 * ***************************************************************
 * 文件名称：mapTool.js
 * 摘   要：距离测量和面积测量
 * 作   者：yanran
 * 创建时间：2016-12-17
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：2016-12-17 yanran 创建
 * 			2019-07-03 yanran 重构
 * ****************************************************************
 */

jQuery(document).ready(function () {
	mapTools();
	mapToolBtnStyle();
});

function mapTools() {
	$('#mapToolsBtn').click(function () {
		if($('#mapToolsPanel').is(':visible')){
			$('#mapToolsPanel').hide();

			G_map.getToolManager().selectTool(terramap.maps.TOOL_DRAGPAN);//进入按钮模式
		}else{
			$('#mapToolsPanel').show();
		}		
	});

	$('#mapToolsPanel #measureDistanceBtn').click(function () {
		measureDistance();
	});

	$('#mapToolsPanel #measureAreaBtn').click(function () {
		measureArea();
	});
}

function mapToolBtnStyle(){
	$('.tool-btns > div > a').click(function () {
		if($(this).next('div').is(':visible')){
			$('.tool-btns > div > div > a').each(function(){
				$(this).removeClass('active');
			});
			$(this).addClass('active');
		}else{
			$(this).removeClass('active');
		}		
	});


	$('.tool-btns > div > div > a').click(function () {
		$(this).addClass('active').siblings().removeClass('active');
	});
}


function measureDistance() {
	G_map.getToolManager().selectTool(terramap.maps.TOOL_MEASUREDISTANCE);
	G_map.getCmdManager().register(terramap.maps.CMD_DISTANCE_MEASURED, this, measureDistance);
	function measureDistance(name, args) {
		//TODO 用自己的弹窗
		//alert(args.resultText);
		//args.handled = true;

		G_map.getCmdManager().unregister(terramap.maps.CMD_DISTANCE_MEASURED, this, measureDistance);
		//清除标记并回复移动状态
		//G_map.clearOverlays();
		G_map.getToolManager().selectTool(terramap.maps.TOOL_DRAGPAN);
		$('#measureDistanceBtn').removeClass('active');
	}
}


function measureArea() {
	G_map.getToolManager().selectTool(terramap.maps.TOOL_MEASUREAREA);
	G_map.getCmdManager().register(terramap.maps.CMD_AREA_MEASURED, this, measureArea);
	function measureArea(name, args) {
		//TODO 用自己的弹窗
		//alert(args.resultText);
		//args.handled = true;

		G_map.getCmdManager().unregister(terramap.maps.CMD_AREA_MEASURED, this, measureArea);
		//清除标记并回复移动状态
		//G_map.clearOverlays();
		G_map.getToolManager().selectTool(terramap.maps.TOOL_DRAGPAN);
		$('#measureAreaBtn').removeClass('active');
	}
}