/**
 * Created by shiyu on 16-1-18.
 */
var RouteMarkerType = -1;
var RouteMarker = {
    START_POINT: 0,
    END_POINT: 1,
    PASS_POINT: 2,
    AVOID_POINT: 3,
    PASS_POINT1: 4,
    AVOID_POINT1: 5
}
var collectRouteTemp = [];
var collectRouteIDTemp = [];

var oldJquery = $;
$ = newJquery;
naviMode = 1;

$("#route-searchbox-content .route-tabs .tab-item").click(function () {
    naviMode = $(this).attr("value");
    $("#route-searchbox-content .route-tabs>div>span").removeClass("naviModelFont");
    $(this).children("span").addClass("naviModelFont");
    var leftval = (parseInt(naviMode) - 1) * 33 + 15.3;
    $(".arrow-wrap").css("left", leftval + "%");

})

//remove from routeAnalysisSev.js by fenglei 2017.05.02
$(document).on("click", ".startNavigate", function () {
    $(this).children("span").text("停止导航");
    G_map.setZoomLevel(13);
    var tttt = window.setInterval(simulateNavigate, 500);//毫秒
    objRst.interval = tttt;
    $("#naviButton").removeClass("startNavigate");
    $("#naviButton").addClass("stopNavigate");

});

$(document).on("click", ".stopNavigate", function () {
    $(this).children("span").text("模拟导航");
    $("#naviButton").removeClass("stopNavigate");
    $("#naviButton").addClass("startNavigate");
    window.clearInterval(objRst.interval);
});

$(document).on("click", ".selectAsPoint", function () {
    var objInfo = {};
    var xPoints = $(this).attr("x").split(',');
    var yPoints = $(this).attr("y").split(',');
    objInfo.x = xPoints[0] * 3600000;
    objInfo.y = yPoints[0] * 3600000;
    objInfo.index = $(this).attr("index").split(',');
    objInfo.naviPointType = $(this).attr("naviPointType").split(',');
    objInfo.title = $(this).find(".sl-main").text();
    for (var i = 0; i < selectRouteMark.length; i++) {
        G_map.removeOverlay(selectRouteMark[i]);
    }
    var _Level = G_map.getZoomLevel();//当前的级别
    var _pos = new terramap.GeoPoint(objInfo.x, objInfo.y, tempSpatialReference);
    var poi = _pos.convertTo(G_map.getSpatialReference());
    //G_map.setCenter(poi, _Level);
    G_map.panTo(poi);
    G_map.setZoomLevel(12);
    selectNaviPoint(objInfo);
    setTimeout("doNaviSearch()", 300);

});
//end remove

function clearRouteModel() {

    $("#card-1-0").css("display", "none");
    $("#cards-level3").css("display", "none");
    $(".stopNavigate span").text("模拟导航");
    $("#naviButton").removeClass("stopNavigate");
    $("#naviButton").addClass("startNavigate");
    $('.routebox-inputs .route-pushpin').css("color", "#ccc");
    window.clearInterval(objRst.interval);
    $("#route-searchbox-content .fa-angle-double-up").trigger("click");
    navigateIndex = 0;

    objRoutePointMarker.startPoint = [];
    objRoutePointMarker.endPoint = [];
    objRoutePointMarker.passPoint = [];
    objRoutePointMarker.avoidPoint = [];
    objRoutePointMarker.startMarker = [];
    objRoutePointMarker.endMarker = [];
    objRoutePointMarker.passMarker = [];
    objRoutePointMarker.avoidMarker = [];
    objRoutePointMarker.passPoint1 = [];
    objRoutePointMarker.avoidPoint1 = [];
    objRoutePointMarker.passMarker1 = [];
    objRoutePointMarker.avoidMarker1 = [];

    objRst.navigatePoint = null;
    objRst.navigateInfoSeq = null;
    objRst.navigatePointMaker = null;
    objRst.interval = null;

    $("#routebox .input-route-clear,#route-searchbox-content .input-route-clear").trigger("click");
    // $("#routeInfoPanel").css("display", "none");


};


$(".cancel-button").click(function () {
    $("#search-route-button").css("display", "none");
    $("#search-button").css("display", "block");
    $("#route-searchbox-content").css("display", "none");
    $("#sole-searchbox-content").css("display", "block");
    $("#cards-level0>li").css("display", "none");
    $("#cards-level1>li").css("display", "none");
    $("#cards-level2").css("display", "none");
    $("#searchbox .bootstrap-select").css("display", "block");
    $("#card-route1").css("display", "none");
    G_map.clearOverlays();

    RouteMarkerType = -1;
    clearRouteModel();
    G_map.getToolManager().selectTool(null, true);
});

function enterRouteModel() {
    $("#cards-level3").css("display", "none");
    $("#search-route-button").css("display", "block");

    $("#search-button").css("display", "none");
    $("#route-searchbox-content").css("display", "block");

    $("#sole-searchbox-content").css("display", "none");
    $("#cards-level0>li").css("display", "none");
    $("#cards-level1>li").css("display", "none");
    $("#cards-level2").css("display", "none");

    $("#searchbox .bs-select").css("display", "none");

    $(".history-route").parent().css("display", "block");

    $("#searchbox").css("background", "rgba(0,0,0,0)");

    addRouteHistory();
    G_map.clearOverlays();
};

$(".route-button").click(function () {
    $("#outerDiv").trigger("mousemove");
    enterRouteModel();
});



$(".route-pushpin").click(function () {
    document.addEventListener("mousedown", cancelSelectTool);
    console.log("addEventListener cancelSelectTool");
    $("#outerDiv").css("cursor", "url('" + route_imgs_url + "pushpin.png'),default");
    $("#outerDiv").attr("oncontextmenu", "return(false)");
})

$("#route-start-pushpin").click(function () {
    $('.routebox-inputs .route-pushpin').css("color", "#ccc");
    G_map.getToolManager().selectTool(null, true);
    $(this).css("color", "#35aa47");

    RouteMarkerType = RouteMarker.START_POINT;
    G_map.getToolManager().selectTool(netanalysis_poi_tool, true);
});
$("#route-end-pushpin").click(function () {
    $('.routebox-inputs .route-pushpin').css("color", "#ccc");
    G_map.getToolManager().selectTool(null, true);
    $(this).css("color", "#35aa47");
    RouteMarkerType = RouteMarker.END_POINT;
    G_map.getToolManager().selectTool(netanalysis_poi_tool, true);
});
$("#route-pass-pushpin").click(function () {
    $('.routebox-inputs .route-pushpin').css("color", "#ccc");
    G_map.getToolManager().selectTool(null, true);
    $(this).css("color", "#35aa47");
    RouteMarkerType = RouteMarker.PASS_POINT;
    G_map.getToolManager().selectTool(netanalysis_poi_tool, true);
});
$("#route-avoid-pushpin").click(function () {
    $('.routebox-inputs .route-pushpin').css("color", "#ccc");
    G_map.getToolManager().selectTool(null, true);
    $(this).css("color", "#35aa47");
    RouteMarkerType = RouteMarker.AVOID_POINT;
    G_map.getToolManager().selectTool(netanalysis_poi_tool, true);
});

$("#route-pass-pushpin1").click(function () {
    $('.routebox-inputs .route-pushpin').css("color", "#ccc");
    G_map.getToolManager().selectTool(null, true);
    $(this).css("color", "#35aa47");
    RouteMarkerType = RouteMarker.PASS_POINT1;
    G_map.getToolManager().selectTool(netanalysis_poi_tool, true);
});
$("#route-avoid-pushpin1").click(function () {
    $('.routebox-inputs .route-pushpin').css("color", "#ccc");
    G_map.getToolManager().selectTool(null, true);
    $(this).css("color", "#35aa47");
    RouteMarkerType = RouteMarker.AVOID_POINT1;
    G_map.getToolManager().selectTool(netanalysis_poi_tool, true);
});

var routeModel = ""

$("#route-go").bind("click", function () {
    enterRouteModel();
    var endTitle = $(".search-item-title").attr("title");
    var x = $(".search-item-title").attr("x");
    var y = $(".search-item-title").attr("y");
    $(".route-end-input").val(endTitle);

    var _pos = new terramap.GeoPoint(x, y, tempSpatialReference);
    if (isJP) {
        var LL = _pos.convertToLonLat();
        var LLjp = wgs84togcj02(LL.lon, LL.lat);
        var geocoord0 = new terramap.GeoPoint(LLjp[0] * 3600000, LLjp[1] * 3600000, tempSpatialReference);
        var geocoord = geocoord0.convertTo(G_map.getSpatialReference());
    } else {
        var geocoord = _pos.convertTo(G_map.getSpatialReference());
    }

    var icon = new terramap.maps.Icon();
    icon.iconSize = new terramap.Size(36, 36);
    icon.iconAnchor = new terramap.Point(18, 36);
    icon.image = route_imgs_url + "endpoint.png";

    var mark = new terramap.maps.overlay.Marker(geocoord, icon, endTitle);
    if (objRoutePointMarker.endPoint.length >= 1) {
        G_map.removeOverlay(objRoutePointMarker.endMarker[0]);
        objRoutePointMarker.endPoint = [];
        objRoutePointMarker.endMarker = [];
    }
    objRoutePointMarker.endMarker.push(mark);
    objRoutePointMarker.endPoint.push(geocoord);
    mark.disableDraggable();
    G_map.addOverlay(mark);
    routeModel = "search";
    $("#route-end-input").attr("naviState", "true");

});

//切换路径规划起始点终点
$(".routebox-revert").click(function () {
    var route_start = $(".route-start-input").val();
    var route_end = $(".route-end-input").val();
    if (route_start == "请输入起点...") {
        route_start = "请输入终点...";
    }
    if (route_end == "请输入终点...") {
        route_end = "请输入起点...";
    }
    $(".route-start-input").val(route_end);
    $(".route-end-input").val(route_start);

    var tempPoint;
    var tempMarker;
    var tempGeoCoord;


    tempPoint = objRoutePointMarker.startPoint;
    objRoutePointMarker.startPoint = objRoutePointMarker.endPoint;
    objRoutePointMarker.endPoint = tempPoint;

    G_map.removeOverlay(objRoutePointMarker.startMarker[0]);
    G_map.removeOverlay(objRoutePointMarker.endMarker[0]);

    var startGeoCoord = objRoutePointMarker.startMarker[0].getGeoCoord();
    var endGeoCoord = objRoutePointMarker.endMarker[0].getGeoCoord();
    tempGeoCoord = startGeoCoord;
    startGeoCoord = endGeoCoord;
    endGeoCoord = tempGeoCoord;

    objRoutePointMarker.startMarker[0].setGeoCoord(startGeoCoord);
    objRoutePointMarker.endMarker[0].setGeoCoord(endGeoCoord);

    G_map.addOverlay(objRoutePointMarker.startMarker[0]);
    G_map.addOverlay(objRoutePointMarker.endMarker[0]);

});

function addRouteHistory() {
    var cookies = decodeURI(getCookie("routeHistory")).split(",");
    if (cookies[0] == "") return;
    var $ul = $(".history-route>ul").eq(0);
    $ul.empty();

    var cookies_length = cookies.length;
    if (cookies_length > 16) cookies_length = 16;
    for (var i = 0; i < cookies_length; i++) {
        var cookie = cookies[i].split("--")
        $ul.append('<li start=' + cookie[0] + ' end=' + cookie[1] + '><span class="delRouteHistory fa fa-times-circle" historyIndex= ' + i + '></span><i class="his-route-icon">' + cookie[0] + '--' + cookie[1] + '</i></li>');
    }

};

$(document).on("click", ".delRouteHistory", function () {
    var historyIndex = $(this).attr("historyIndex");
    $(this).parent("li").remove();
    var cookies = decodeURI(getCookie("routeHistory")).split(",");
    cookies.splice(Number(historyIndex), 1)
    setCookie("routeHistory", encodeURI(cookies.join()), 30);
    addRouteHistory();
});

function checkRouteInput() {
    var route_start = $('#route-start-input');
    var route_end = $('#route-end-input');
    var isNull = /^[\s' ']*$/;
    if (route_start.val() == '请输入起点...' || route_start.val().length <= 0 || isNull.test(route_start.val())) {
        route_start.focus();
        window.alert("请输入起点，起点不能为空");
        return false;
    } else if (route_end.val() == '请输入终点...' || route_end.val().length <= 0 || isNull.test(route_end.val())) {
        route_end.focus();
        window.alert("请输入终点，终点不能为空");
        return false;
    } else {
        return true;
    }
};

$(document).on("click", "#route-searchbox-content .fa-angle-double-down", function () {
    $("#card-0-1").css("display", "block");
    $(this).removeClass("fa-angle-double-down");
    $(this).addClass("fa-angle-double-up");

});

$(document).on("click", "#route-searchbox-content .fa-angle-double-up", function () {
    $("#card-0-1").css("display", "none");
    $(this).removeClass("fa-angle-double-up");
    $(this).addClass("fa-angle-double-down");

    if (objRoutePointMarker.passPoint.length >= 1) {
        G_map.removeOverlay(objRoutePointMarker.passMarker[0]);
        objRoutePointMarker.passPoint = [];
        objRoutePointMarker.passMarker = [];
    }
    if (objRoutePointMarker.avoidPoint.length >= 1) {
        G_map.removeOverlay(objRoutePointMarker.avoidMarker[0]);
        objRoutePointMarker.avoidPoint = [];
        objRoutePointMarker.avoidMarker = [];
    }
    if (objRoutePointMarker.passPoint1.length >= 1) {
        G_map.removeOverlay(objRoutePointMarker.passMarker1[0]);
        objRoutePointMarker.passPoint1 = [];
        objRoutePointMarker.passMarker1 = [];
    }
    if (objRoutePointMarker.avoidPoint1.length >= 1) {
        G_map.removeOverlay(objRoutePointMarker.avoidMarker1[0]);
        objRoutePointMarker.avoidPoint1 = [];
        objRoutePointMarker.avoidMarker1 = [];
    }
    $(".route-pass-input").val('请输入必经点...');
    $(".route-avoid-input").val('请输入回避点...');

});

$(document).on("click", ".fa-plus", function () {
    $("#route-pass-input1").parent().css("display", "block");
    $("#route-avoid-input1").parent().css("display", "block");
    $(this).removeClass("fa-plus");
    $(this).addClass("fa-minus");
});

$(document).on("click", ".fa-minus", function () {
    $("#route-pass-input1").parent().css("display", "none");
    $("#route-avoid-input1").parent().css("display", "none");
    $(this).removeClass("fa-minus");
    $(this).addClass("fa-plus");

    if (objRoutePointMarker.passPoint1.length >= 1) {
        G_map.removeOverlay(objRoutePointMarker.passMarker1[0]);
        objRoutePointMarker.passPoint1 = [];
        objRoutePointMarker.passMarker1 = [];
    }
    if (objRoutePointMarker.avoidPoint1.length >= 1) {
        G_map.removeOverlay(objRoutePointMarker.avoidMarker1[0]);
        objRoutePointMarker.avoidPoint1 = [];
        objRoutePointMarker.avoidMarker1 = [];
    }

    $("#route-pass-input1").val('请输入必经点...');
    $("#route-avoid-input1").val('请输入回避点...');
});


//$("#route-avoid-plus").live("click",function(){
//	$("#route-avoid-input1").parent().css("display", "block");
//});

$("#route-end-input").change(function () {
    if ($("#search-route-button").css("display") == "block") {
        routeModel = "route";
    }
});

var naviInfo = {}

//$.fn.extend({
//	changeNaviSearchState:function(){
//		$(this).attr("naviState","false");
//		naviSearchState = -1;
//	}
//});

$(".routebox-inputs input").bind("input propertychange change", function () {
    $(this).attr("naviState", "false");
    naviSearchState = -1;
    console.log("input propertychange change");
});

//'出发'按钮事件
$("#search-route-button").click(function () {
    $("#route-favorite-button").attr("collect", "false").children(".fa").css("color", "white");
    $("#card-1-0").css("display", "none");
    $("#routeSearchList").empty();
    navigateIndex = 0;
    naviSearchState = -1;
    // $("#routeInfoPanel").css("display", "none");
    G_map.removeOverlay(objRst.navigatePointMaker);
    $(".stopNavigate span").text("模拟导航");
    $("#naviButton").removeClass("stopNavigate");
    $("#naviButton").addClass("startNavigate");
    window.clearInterval(objRst.interval);
    var check = checkRouteInput();
    if (!check) {
        return false;
    }
    else {
        if (naviSearchState == -1) {
            $("#card-route0,#route-favorite").css("display", "none");
            naviSearchState = doNaviSearch();
        }
        if (naviSearchState == 0) {
            return;
        }

        //naviGo();


    }
});

//路径历史
$(document).on("click", ".his-route-icon", function () {
    var $this = $(this).parent();
    var start = $this.attr("start");
    var end = $this.attr("end");
    if (start == "" || end == "") {
        return;
    }
    else {
        $(".route-start-input").val(start);
        $(".route-end-input").val(end);
    }
});


//路径规划起点输入框输入提示
$(".route-start-input")
    .focus(function () {
        var $this = $(this);
        ($this.val() === '请输入起点...') ? $this.val('') : null;
    })
    .blur(function () {
        var $this = $(this);
        ($this.val() === '') ? $this.val('请输入起点...') : null;
    });
//路径规划终点输入框输入提示
$(".route-end-input")
    .focus(function () {
        var $this = $(this);
        ($this.val() === '请输入终点...') ? $this.val('') : null;
    })
    .blur(function () {
        var $this = $(this);
        ($this.val() === '') ? $this.val('请输入终点...') : null;
    });

//路径规划必经点输入框输入提示
$(".route-pass-input")
    .focus(function () {
        var $this = $(this);
        ($this.val() === '请输入必经点...') ? $this.val('') : null;
    })
    .blur(function () {
        var $this = $(this);
        ($this.val() === '') ? $this.val('请输入必经点...') : null;
    });
//路径规划回避点输入框输入提示
$(".route-avoid-input")
    .focus(function () {
        var $this = $(this);
        ($this.val() === '请输入回避点...') ? $this.val('') : null;
    })
    .blur(function () {
        var $this = $(this);
        ($this.val() === '') ? $this.val('请输入回避点...') : null;
    });

var focusInputFlag = "";
$(".route-start-input").bind("focus", function () {
    focusInputFlag = "start";
    var cookieStart = decodeURI(getCookie("routeStartHistory"));
    var itemsHistory = cookieStart.split(",");
    if ($("#sole-input").val() == "请输入关键词..." || $("#sole-input").val() == "") {
        publicData.item = itemsHistory;
    }
});

$(".route-end-input").bind("focus", function () {
    focusInputFlag = "end";
    var cookieEnd = decodeURI(getCookie("routeEndHistory"));
    var itemsHistory = cookieEnd.split(",");
    if ($("#sole-input").val() == "请输入关键词..." || $("#sole-input").val() == "") {
        publicData.item = itemsHistory;
    }
});

$("#route-pass-input").bind("focus", function () {
    focusInputFlag = "pass";
    var cookieStart = decodeURI(getCookie("routePassHistory"));
    var itemsHistory = cookieStart.split(",");
    if ($("#sole-input").val() == "请输入关键词..." || $("#sole-input").val() == "") {
        publicData.item = itemsHistory;
    }
});

$("#route-avoid-input").bind("focus", function () {
    focusInputFlag = "avoid";
    var cookieEnd = decodeURI(getCookie("routeAvoidHistory"));
    var itemsHistory = cookieEnd.split(",");
    if ($("#sole-input").val() == "请输入关键词..." || $("#sole-input").val() == "") {
        publicData.item = itemsHistory;
    }
});

$("#route-pass-input1").bind("focus", function () {
    focusInputFlag = "pass1";
    var cookieStart = decodeURI(getCookie("routePassHistory"));
    var itemsHistory = cookieStart.split(",");
    if ($("#sole-input").val() == "请输入关键词..." || $("#sole-input").val() == "") {
        publicData.item = itemsHistory;
    }
});

$("#route-avoid-input1").bind("focus", function () {
    focusInputFlag = "avoid1";
    var cookieEnd = decodeURI(getCookie("routeAvoidHistory"));
    var itemsHistory = cookieEnd.split(",");
    if ($("#sole-input").val() == "请输入关键词..." || $("#sole-input").val() == "") {
        publicData.item = itemsHistory;
    }
});

$(".route-start-input").bind("input", function () {
    searchAll($(this).val());
});

$(".route-end-input").bind("input", function () {
    searchAll($(this).val());
});

$("#route-pass-input").bind("input", function () {
    searchAll($(this).val());
});

$("#route-avoid-input").bind("input", function () {
    searchAll($(this).val());
});

$("#route-pass-input1").bind("input", function () {
    searchAll($(this).val());
});

$("#route-avoid-input1").bind("input", function () {
    searchAll($(this).val());
});


//$ = newJquery;
$(".route-start-input,.route-end-input,#route-pass-input,#route-pass-input1,#route-avoid-input,#route-avoid-input1").autocomplete({
    source: function (request, response) {
        searchComplete(request, response, publicData.item, publicData.accentItems);
    },
    open: function (event) {
        var itemsHeight = 31;
        var itemsLength = publicData.item.length;
        if (itemsLength == 1) itemsHeight = 31;
        if (itemsLength == 2) itemsHeight = 28;
        if (itemsLength > 2 && itemsLength <= 5) itemsHeight = 27;
        if (itemsLength > 5 && itemsLength <= 8) itemsHeight = 26;
        if (itemsLength > 8) {
            itemsLength = 8;
            itemsHeight = 26;
        }
        ;
        var $ul = $(this).autocomplete("widget");
        $ul.addClass("searchAutocomplete");
        $ul.css("overflow-y", "auto");
        $ul.css("height", itemsLength * itemsHeight + "px");

    },
    select: function () {
        $(this).change();
    },
    minLength: 0


}).focus(function (event) {
    $(this).autocomplete("search", "");
}).autocomplete("close");

//$ = oldJquery;
$(document).on("click", "#route-favorite-button", function () {
    if (loginCheck()) {
        return;
    }
    var collect = $(this).attr("collect");
    if (collect == "false") {
        var strRouteFavorite = JSON.stringify(objRouteFavorite);
        //var bbb = JSON.parse(aaa);
        strRouteFavorite = encodeURI(strRouteFavorite);
        var datajson = {
            username: usercookieid,
            collectdesc: strRouteFavorite
        }
        var sqlid = doCollect("route", datajson);
        $(this).attr("collectid", sqlid);
        console.log(sqlid);
        console.log(strRouteFavorite);
        $(this).attr("collect", "true").children(".fa").css("color", "#FFF906");//#EFD515
    } else if (collect == "true") {
        var collectid = $(this).attr("collectid");
        doDelCollect(collectid);
        $(this).attr("collect", "false").children(".fa").css("color", "white");//#00aba9
    }
});

$(document).on("click", "#routePanel", function () {
    if (loginCheck()) {
        return;
    }
    closeLeftPanelContent();
    $('#collect-list').empty();
    G_map.showInfoWindow(null, null, false);
    G_map.clearOverlays();
    var strjson = getCollectList("route");
    //strjson = decodeURI(strjson);
    console.log(strjson);
    //var strjson='{"startPoint":{"naviPointType":0,"title":"鸟巢","x":12957347.783700086,"y":4865104.6226403415,"sref":"EPSG:3857"},"endPoint":{"naviPointType":1,"title":"太极大厦","x":12954718.684955837,"y":4864268.011970583,"sref":"EPSG:3857"},"passPoint":{"name":null,"x":null,"y":null,"sref":null},"avoidPoint":{"name":null,"x":null,"y":null,"sref":null},"passPoint1":{"name":null,"x":null,"y":null,"sref":null},"avoidPoint1":{"name":null,"x":null,"y":null,"sref":null}}'
    //var strjson = '{"startPoint":{"name":"金郁金香花店","x":12924931.195060916,"y":4856527.893898474,"sref":"EPSG:3857"},"endPoint":{"name":"中行ＡＴＭ","x":12938649.280696416,"y":4896912.077022231,"sref":"EPSG:3857"},"passPoint":{"name":null,"x":null,"y":null,"sref":null},"avoidPoint":{"name":null,"x":null,"y":null,"sref":null},"passPoint1":{"name":null,"x":null,"y":null,"sref":null},"avoidPoint1":{"name":null,"x":null,"y":null,"sref":null}}';
    if (strjson.length == 0) {
        // attendionTip("没有收藏的路径");
        //alert("123");
    } else {
        $("#collect-title").text("路径收藏");
        // $("#cards-level3").css("display", "block");
        $("#collect-list").empty();
        collectRouteTemp = [];
        for (var i = 0; i < strjson.length; i++) {
            var objson = JSON.parse(decodeURI(strjson[i].collectdesc));
            console.log(objson);
            collectRouteTemp.push(objson);
            collectRouteIDTemp.push(strjson[i].id);
        }
        restoreRouteList(1);

        // DynamicPaginationFav(Math.ceil(collectRouteTemp.length / listnumRoute), "route");
    }
})


$(".favItem").live("mouseover", function () {
    $(this).find(".sl-main").eq(0).css("color", "#37940A");
    $(this).find(".sl-main").eq(1).css("color", "#BD2819");
    $(this).find(".sl-sub").eq(0).css("color", "#F09301");
    $(this).find(".sl-sub").eq(1).css("color", "#F09301");
    $(this).find(".sl-sub").eq(2).css("color", "#0D558D");
    $(this).find(".sl-sub").eq(3).css("color", "#0D558D");
})

$(".favItem").live("mouseout", function () {
    $(this).find(".sl-main").css("color", "#616666");
    $(this).find(".sl-sub").css("color", "#616666");
})

$(document).on("click", ".sl-icon3", function () {
    var sqlid = $(this).prev().attr("sqlid");
    doDelCollect(sqlid);
    console.log(sqlid);
    $(this).parents(".mix_all").css("display", "none");
    //$("#routePanel").trigger("click");
})


$(document).on("click", ".showRouteCollect", function () {
    enterRouteModel();
    var index = $(this).attr("index");
    console.log(collectRouteTemp[index]);
    var pointArr = [];
    pointArr.push(collectRouteTemp[index].startPoint);
    pointArr.push(collectRouteTemp[index].endPoint);
    pointArr.push(collectRouteTemp[index].passPoint);
    pointArr.push(collectRouteTemp[index].passPoint1);
    pointArr.push(collectRouteTemp[index].avoidPoint);
    pointArr.push(collectRouteTemp[index].avoidPoint1);

    if (collectRouteTemp[index].passPoint.x != null || collectRouteTemp[index].avoidPoint.x != null) {
        $("#route-searchbox-content .fa-angle-double-down").trigger("click");
    } else {
        $("#route-searchbox-content .fa-angle-double-up").trigger("click");
    }
    if (collectRouteTemp[index].passPoint1.x != null || collectRouteTemp[index].avoidPoint1.x != null) {
        $("#route-searchbox-content .fa-angle-double-down").trigger("click");
        $("#cards-level0 .fa-plus").trigger("click");
    } else {
        $("#cards-level0 .fa-minus").trigger("click");
    }


    var temp = tempSpatialReference;
    isJP = false;
    tempSpatialReference = collectRouteTemp[index].startPoint.sref;
    for (var i = 0; i < pointArr.length; i++) {
        if (pointArr[i].x == null) {
            continue;
        } else {
            selectNaviPoint(pointArr[i]);
        }
    }
    $("#cards-level3").css("display", "none");
    setTimeout("naviGo()", 300);
    G_map.setZoomLevel(14);
    var x = collectRouteTemp[index].startPoint.x;
    var y = collectRouteTemp[index].startPoint.y;
    var sref = collectRouteTemp[index].startPoint.sref;
    var geocoord = new terramap.GeoPoint(x, y, sref);
    var _sref = G_map.getSpatialReference();
    var _pos = geocoord.convertTo(_sref);
    G_map.panTo(_pos);
    isJP = true;
    tempSpatialReference = temp;
})

//function restoreRoute(objson){
//	$(".route-input").attr("naviState", "true");
//	$("#route-start-input").val(objson.startPoint.name);
//	$("#route-end-input").val(objson.endPoint.name);
//	$("#route-pass-input").val(objson.passPoint.name);
//	$("#route-avoid-input").val(objson.avoidPoint.name);
//	$("#route-pass-input1").val(objson.passPoint1.name);
//	$("#route-avoid-input1").val(objson.avoidPoint1.name);
//
//
//}

$("#outerDiv").on('mousemove', function (e) {
    $(".route-end-input,.route-start-input,.route-pass-input,.route-avoid-input").autocomplete("close").blur();
});

function searchAll(kwd) {

    terramap.setServiceCallAckEncoding("utf-8");
    terramap.setServiceCallReqEncoding("utf-8");
    var serviceUrl = terramap.serviceBaseURL;
    terramap.serviceBaseURL = __serviceURL.route;
    //1--prepare params
    var suggests = [];
    var text = kwd;

    //2---get proxy
    var proxy = terramap.getService("mmss.services.gs.search.ISearchService");

    //3---run service buildIndexThrougConn
    var start = new Date();
    var __options = {
        async: true,
        timeout: 300000,
        handleServiceCallResult: doSearchSuccess,
        handleServiceCallError: doSearchFailed,
    }
    proxy.getSuggestion(text, suggests, __options);
    terramap.serviceBaseURL = serviceUrl;
    var end = new Date();
    // console.log("getSuggestion useTime:" + (end.getTime() - start.getTime()) / 1000.0 + "s :");
};

var doSearchSuccess = function (num, info) {
    if (info.length > 0) {
        // console.log(info.length);
        var itemsWords = [];
        for (var i = 0; i < info.length; i++) {
            itemsWords[i] = info[i].text;
        }
    }
    ;
    publicData.item = itemsWords;
    publicData.itemsRelatedWord = itemsWords;
    //var $uls = $(".route-start-input").autocomplete ("widget");
    //var $ule = $(".route-end-input").autocomplete ("widget");

    switch (focusInputFlag) {
        case "start":
            $(".route-start-input").autocomplete("search", "");
            break;
        case "end":
            $(".route-end-input").autocomplete("search", "");
            break;
        case "pass":
            $("#route-pass-input").autocomplete("search", "");
            break;
        case "avoid":
            $("#route-avoid-input").autocomplete("search", "");
            break;
        case "pass1":
            $("#route-pass-input1").autocomplete("search", "");
            break;
        case "avoid1":
            $("#route-avoid-input1").autocomplete("search", "");
            break;
    }

    //$("#sole-input").blur().focus();
}

var doSearchFailed = function () {
    console.log("searchSever error");
}

    //$ = oldJquery