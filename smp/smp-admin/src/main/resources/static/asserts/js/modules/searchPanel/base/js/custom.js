/**
 * Created by shiyu on 16-3-11.
 */


//下拉框组件
var handleBootstrapSelect = function() {
	$('.bs-select').selectpicker({
		iconBase: 'fa',
		tickIcon: 'fa-check'
	});
}
handleBootstrapSelect();

//获取当前浏览器信息
function myBrowser(){
	var userAgent = navigator.userAgent;
	var isOpera = userAgent.indexOf("Opera")>-1;
	var isFirefox = userAgent.indexOf("Firefox")>-1;
	var isChrome = userAgent.indexOf("Chrome")>-1;
	var isSafari = userAgent.indexOf("Safari")>-1;
	var isMSIE = userAgent.indexOf("compatible")>-1 && userAgent.indexOf("MSIE")>-1 && !isOpera;

	if(isOpera) return "Opera";
	if(isFirefox) return "Firefox";
	if(isChrome) return "Chrome";
	if(isSafari) return "Safari";
	if(isMSIE) return "MSIE";
};

var myBrowser = myBrowser();
// console.log("myBrowser is "+ myBrowser);

// var usercookieid = getCookie('userId');
// console.log("usercookieid:"+usercookieid);

//var __a = '终点(地图选点)';
//var __ab = encodeURI(__a);
//var __abc = decodeURI(__ab);
//console.log(__a);
//console.log(__ab);
//console.log(__abc);

// function loginCheck(){
// 	var usercookieid = getCookie('userId');
// 	console.log("usercookieid:"+usercookieid);
// 	if(usercookieid==null || usercookieid=="null") {
// 		attendionTip("请先登录。");
// 		return 1;
// 	}else {
// 		return 0;
// 	}
// }

var tempSpatialReference = G_sref;
var isJP = true;
var __serviceURL={
	search:G_mswss_maps_services,
	route:G_mswss_maps_services
	// pics:G_base_serviceURL_pics,
	// video_:G_base_serviceURL_video_,
	// _video:G_base_serviceURL_video
}

//获取用户收藏列表
// if(usercookieid!=null && usercookieid!="null"){
// 	var pointList = getCollectList("point");
// 	collectTemp = pointList;
// 	console.log(collectTemp);
// }

//清除输入框文字
$(".input-clear").click(function(){
	$(this).prev("input").val('');
});

$(".input-route-clear").click(function(){
	$(this).prev().prev("input").val('');
	var _id = $(this).attr("id");
	switch (_id) {
		case "route-start-clear":
			if (objRoutePointMarker.startPoint.length >= 1){
				objRoutePointMarker.startMarker[0].closeInfoWindow();
				G_map.removeOverlay(objRoutePointMarker.startMarker[0]);
				objRoutePointMarker.startPoint = [];
				objRoutePointMarker.startMarker = [];
			}
			break;
		case "route-end-clear":
			if (objRoutePointMarker.endPoint.length >= 1){
				objRoutePointMarker.endMarker[0].closeInfoWindow();
				G_map.removeOverlay(objRoutePointMarker.endMarker[0]);
				objRoutePointMarker.endPoint = [];
				objRoutePointMarker.endMarker = [];
			}
			break;
		case "route-pass-clear":
			if (objRoutePointMarker.passPoint.length >= 1){
				objRoutePointMarker.passMarker[0].closeInfoWindow();
				G_map.removeOverlay(objRoutePointMarker.passMarker[0]);
				objRoutePointMarker.passPoint = [];
				objRoutePointMarker.passMarker = [];
			}
			break;
		case "route-avoid-clear":
			if (objRoutePointMarker.avoidPoint.length >= 1){
				objRoutePointMarker.avoidMarker[0].closeInfoWindow();
				G_map.removeOverlay(objRoutePointMarker.avoidMarker[0]);
				objRoutePointMarker.avoidPoint = [];
				objRoutePointMarker.avoidMarker = [];
			}
			break;
		case "route-pass-clear1":
			if (objRoutePointMarker.passPoint1.length >= 1){
				objRoutePointMarker.passMarker1[0].closeInfoWindow();
				G_map.removeOverlay(objRoutePointMarker.passMarker1[0]);
				objRoutePointMarker.passPoint1 = [];
				objRoutePointMarker.passMarker1 = [];
			}
			break;
		case "route-avoid-clear1":
			if (objRoutePointMarker.avoidPoint1.length >= 1){
				objRoutePointMarker.avoidMarker1[0].closeInfoWindow();
				G_map.removeOverlay(objRoutePointMarker.avoidMarker1[0]);
				objRoutePointMarker.avoidPoint1 = [];
				objRoutePointMarker.avoidMarker1 = [];
			}
			break;
		default:
			break;
	}
});

var menuFadeIn = function(){
	return $(this).addClass("menuFadeIn").removeClass("menuFadeOut");
};

function saveToFavorite(keyName,title,point,RouteMarker){
	if(point==null){
		objRouteFavorite[keyName] = {naviPointType:RouteMarker,title:null,x:null,y:null,sref:null}
		return objRouteFavorite;
	}else{
		objRouteFavorite[keyName].naviPointType=RouteMarker;
		objRouteFavorite[keyName].title=title;
		objRouteFavorite[keyName].x=point.x;
		objRouteFavorite[keyName].y=point.y;
		objRouteFavorite[keyName].sref=point.sref;
		return objRouteFavorite;
	}
}
//收藏
// function doCollect(type,datajson){
// 	var _url = "";
// 	var sqlid;
// 	if(type == "point"){
// 		_url = '../Collect/addpoint';
// 	}else if(type == "route"){
// 		_url = '../Collect/addroute';
// 		//_url = 'http://192.168.55.190:8080/gisPortal/Collect/addroute';
// 	}
// 	$.ajax({
// 		url:_url,
// 		method:'get',
// 		data:datajson,
// 		dataType:"json",
// 		async:false,
// 		//contentType:"application/x-www-from-urlencoded;charset=UTF-8",
// 		success:function(data){
// 			console.log("id: "+data);
// 			attendionTip("收藏成功");
// 			console.log("收藏成功");
// 			sqlid = data;
// 		},
// 		error: function()
// 		{
// 			attendionTip("收藏失败");
// 			console.log("收藏失败");
// 		}

// 	});
// 	return sqlid;
// }

//获取收藏列表
// function getCollectList(type){
// 	var _url = "";
// 	var datalist=1;
// 	if(type == "point"){
// 		_url = '../Collect/getpoint';
// 	}else if(type == "route"){
// 		_url = '../Collect/getroute';
// 		//_url = 'http://192.168.55.190:8080/gisPortal/Collect/getroute';
// 	}
// 	var datajson = {
// 		username:getCookie('userId')
// 	};
// 	$.ajax({
// 		url:_url,
// 		method:'get',
// 		data:datajson,
// 		dataType:"json",
// 		async:false,
// 		success:function(data){
// 			console.log(data);
// 			console.log("获取收藏列表成功");
// 			datalist =  data;
// 		},
// 		error: function()
// 		{
// 			console.log("获取收藏列表失败");
// 		}
// 	});
// 	return datalist;
// }
//取消收藏
// function doDelCollect(id){
// 	var idjson = {
// 		id:id,
// 	}
// 	$.ajax({
// 		url:'../Collect/delcollect',// /Collect/getroute  /Collect/delcollect
// 		method:'get',
// 		data:idjson,
// 		dataType:"json",
// 		success:function(data){
// 			console.log("id: "+data);
// 			attendionTip("取消收藏成功");
// 			console.log("取消收藏成功");
// 		},
// 		error: function()
// 		{
// 			attendionTip("取消收藏失败");
// 			console.log("取消收藏失败");
// 		}

// 	});
// }

//autocomplete
var normalize = function( term , cols) {
	var ret = "";
	ret = cols[ term] || term;
	return ret;
};

function searchComplete (request, response, ornames, slcols) {
	// $.ui = newJquery.ui;
	if(typeof ornames == 'undefined'){
		ornames = '';
	}
	var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term) , "i" );
	if (!request.term || $.trim(request.term).length == 0) {
		matcher = null;
	} else if (!request.term || $.trim(request.term).length == 1) {
		matcher = new RegExp("^" + $.ui.autocomplete.escapeRegex(request.term) , "i" );
	}
	response( $.grep( ornames, function( value ) {
		value = value.label || value.value || value;
		if (matcher == null) {
			return value;
		} else {
			return matcher.test( value ) || matcher.test( normalize( value, slcols ) );
		}
	}) );
};

//关闭LeftPanel
function closeLeftPanelContent(){
	$("#cards-level3").css("display","none");
	$("#cards-level0>li").css("display","none");
	$("#cards-level1>li").css("display","none");
	$("#cards-level2").css("display","none");
	$(".cancel-button").trigger("click");
};

//切换底图
var maptypeicon = ['allType','placenameType','byType','wenxianType','biaozhuType']

function typeIcon(){
	for(var i=0;i<maptypeicon.length;i++){
		$(".dropdown-menu .inner li").eq(i).children("a").prepend('<div class="typeicon '+ maptypeicon[i] +'"></div>');
	}
}

// Cookie
function setCookie(name,value,day)
{
	var date = new Date();
	date.setDate(date.getDate()+day);
	document.cookie=name+'='+value+';expires='+date;
};

function getCookie(name)
{
	var arr = document.cookie.split('; ');
	var i = 0;
	for(i=0;i<arr.length;i++)
	{
		var arr2 = arr[i].split('=');
		if(arr2[0] == name)
		{
			return arr2[1];
		}
	}
	return '';
}



function removeCookie(name)
{
	setCookie(name,'1','-100');
}

//css的淡入淡出
function menuFadeIn(id){
	id.addClass("menuFadeIn").removeClass("menuFadeOut");
}

function menuFadeOut(id){
	id.addClass("menuFadeOut").removeClass("menuFadeIn");
}

// function attendionTip(content){
// 	$("#attendionTip span").text(content);
// 	$("#attendionTip").fadeIn(800).delay(1500).fadeOut(800);
// }



//获取屏幕信息
var screen_height = window.screen.height;
// console.log("屏幕分辨率的高："+window.screen.height+"\n");
/*console.log("屏幕分辨率的宽："+window.screen.width+"\n");
console.log("屏幕可用工作区的高："+window.screen.availHeight+"\n");
console.log("屏幕可用工作区的宽："+window.screen.availWidth+"\n");
console.log("屏幕可见区域的高："+document.body.offsetHeight+"\n");
console.log("屏幕可见区域的宽："+document.body.offsetWidth+"\n");
console.log("网页可见区域的高："+document.body.clientHeight+"\n");
console.log("网页可见区域的宽："+document.body.clientWidth+"\n");
console.log("网页可见区域的高："+document.body.scrollHeight+"\n");
console.log("网页可见区域的宽："+document.body.scrollWidth+"\n");*/


