/**
 * ***************************************************************
 * 文件名称：geoAnalysis.js
 * 摘   要：地形分析交互
 * 作   者：yanran
 * 创建时间：2019-07-04
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：
 * ****************************************************************
 */

jQuery(document).ready(function () {
	geoAnalysisBtn();
});

function geoAnalysisBtn() {
	$('#geoAnalysisBtn').click(function () {
		if($('#geoAnalysisPanel').is(':visible')){
			$('#geoAnalysisPanel').hide();

			deleteLegendImg();//清除地图所有标记
			geoRoam();//进入漫游模式
		}else{
			$('#geoAnalysisPanel').show();
		}
	});

	$('#roamBtn').click(function () {
		geoRoam();
	});
	$('#distanceBtn').click(function () {
		geoAnalysis('topoanalysis_dis_tool');
	});
	$('#elevationBtn').click(function () {
		geoAnalysis('topoanalysis_elev_tool');
	});
	$('#lineSightBtn').click(function () {
		geoAnalysis('topoanalysis_linesight_tool');
	});
	$('#viewSightBtn').click(function () {
		geoAnalysis('topoanalysis_areasight_tool');
	});
	$('#slopeBtn').click(function () {
		geoAnalysis('topoanalysis_slope_tool');
	});
	$('#aspectBtn').click(function () {
		geoAnalysis('topoanalysis_aspect_tool');
	});
	$('#profileBtn').click(function () {
		geoAnalysis('topoanalysis_profile_tool');
	});
	// $('#navigateBtn').click(function () {
	// 	geoAnalysis('topoanalysis_yynavigate_tool');
	// });
}

function geoAnalysis(name) {
	deleteLegendImg();
	if (G_map != null) {
		G_map.clearOverlays();
		lnsightcnt = 0;
		twopoisightparam = null;
		G_map.getToolManager().selectTool(name);
	}
}

function geoRoam() {
	G_map.getToolManager().selectTool(terramap.maps.TOOL_DRAGPAN);
}


function addFlag(poi) {
    var icon = new terramap.maps.Icon();
    icon.image = geoAnalysis_imgs_url + 'flag.gif';
    icon.iconSize = new terramap.Size(12, 18);
    icon.iconAnchor = new terramap.Point(0, 18);
    var mark = new terramap.maps.overlay.Marker(poi, icon, null);
    mark.disableDraggable();
    return mark;
}


function deleteLegendImg() {
    if (G_map != null) {
        G_map.clearOverlays();
    }
    var chartgraph = document.getElementById('chartGraph');
    chartgraph.style.display = 'none';
    //在调用时让其出现。。在这里让其消失
}

var myChart;
var needRefresh = false;
var domMain = document.getElementById('chartGraph');
function focusGraphic() {
    if (needRefresh) {
        myChart.showLoading();
        setTimeout(refresh, 1000);
    }
}




//显示请稍后提示的函数执行类。
//这个类还包括针对异步请求的另一种使用方式。
//但目前只用到了同步请求方式。
function standbyFunction() {

	// 提示的文字
	this.text = '正在执行中，请稍候……';
	// 延时多少称执行函数，这个延时主要是保证同步请求不会把DOM操作锁死，默认400。调试时可适当设大以体现效果。
	this.delay = 400;
	// 超时时间 用于劝能执行错误无法继续时，适时取消页面上的正在执行状态，重新激活页面操作。
	this.timeout = 12000;
	// 页面中显示的请稍候元素总成。
	this.$html = null;
	// 定义需要执行的函数
	this.fn = function () {
	}
	// 函数执行完的回调。
	this.callback = function () {
	}

	this.add = function () {
		var html = '<div><div></div><p></p></div>';
		this.$html = $(html)
		var $div = this.$html.children('div');
		var $p = this.$html.children('p');
		var $band = $div.add($p);
		$band.css('position', 'absolute');
		$div.css('top', '0');
		$div.css('right', '0');
		$div.css('bottom', '0');
		$div.css('left', '0');
		$div.css('z-index', '600');
		$div.css('background', '#999');
		$div.css('opacity', '0.3');
		$p.css('z-index', '650');
		$p.css('padding', '8px 26px 8px 40px');
		$p.css('font-size', '12px');
		$p.css('line-height', '16px');
		$p.css('border', '1px solid #ccc');
		$p.css('background', '#fff url(' + geoAnalysis_imgs_url +'loading.gif) no-repeat 11px 7px');
		//$p.css('box-shadow', '1px 1px 6px #333');
		$p.html(this.text);

		$('body').prepend(this.$html);

		var divHeight = $div.height();
		var divWidth = $div.width();
		var pHeight = $p.height();
		var pWidth = $p.width();

		var pTop = parseInt((divHeight - pHeight) / 2.1);
		var pLeft = parseInt((divWidth - pWidth) / 2);

		//$p.css('top', pTop + 'px');
		$p.css('top', '200px');
		$p.css('left', pLeft + 'px');

	};

	this.remove = function () {
		this.$html.remove();
	};

	this.excute = function () {
		var o = this;
		o.add();
		var timer = setTimeout(function () {
			alert('功能执行超时，请重试');
			o.remove();
		}, o.timeout);
		setTimeout(function () {
			o.fn();
			o.remove();
			o.callback();
			clearTimeout(timer);
		}, o.delay);
	};
};