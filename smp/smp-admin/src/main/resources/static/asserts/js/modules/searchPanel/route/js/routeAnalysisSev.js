/**
 * Created by ShiYu on 2016/8/18.
 */

var netAnalysisReq = null;

var route_imgs_url = 'assets/modules/searchPanel/route/imgs/';

function sortNaviInfo(routeinfo) {
    var i, j;
    var route0, route1, routeName, routemeter, routeAngel;
    var routeNameArray = [];
    var routemeterArray = [];
    var routeAngelArray = [];
    var routeArray = [];
    var routeinfo0 = routeinfo.split("。");
    routeArray.push(routeinfo0[0] + '。');

    for (i = 1; i < routeinfo0.length - 2; i++) {
        route0 = routeinfo0[i].split("，");
        route1 = route0[0].split("行驶");
        if (i == 1) {
            routeName = route1[0];
            routemeter = parseInt(route1[1]);
            routeAngel = route0[1];
        }
        if (i > 1 && (routeName == route1[0])) {
            routemeter += parseInt(route1[1]);
            routeAngel = route0[1];
        }
        if (i > 1 && (routeName != route1[0])) {
            routeNameArray.push(routeName);
            routemeterArray.push(routemeter);
            routeAngelArray.push(routeAngel);
            routeName = route1[0];
            routemeter = parseInt(route1[1]);
            routeAngel = route0[1];
        }
        if (i == (routeinfo0.length - 3)) {
            routeNameArray.push(routeName);
            routemeterArray.push(routemeter);
            routeAngelArray.push(routeAngel);
        }
    }
    var kmeter;
    for (j = 0; j < routeNameArray.length; j++) {
        if (routemeterArray[j] >= 1000) {
            kmeter = (routemeterArray[j] / 1000) + "公里,";
        } else {
            kmeter = routemeterArray[j] + "米,";
        }
        routeArray.push(routeNameArray[j] + "行驶" + kmeter + routeAngelArray[j] + "。")
    }
    routeArray.push(routeinfo0[routeinfo0.length - 2] + '。');
    //console.log(routeNameArray);
    //console.log(routemeterArray);
    // console.log(routeArray);
    return routeArray;

}

//showRouteMessage(routeinfo);

var objRouteFavorite = {
    startPoint: {
        naviPointType: RouteMarker.START_POINT,
        title: "",
        x: 0,
        y: 0,
        sref: ""
    },
    endPoint: {
        naviPointType: RouteMarker.END_POINT,
        title: "",
        x: 0,
        y: 0,
        sref: ""
    },
    passPoint: {
        naviPointType: RouteMarker.PASS_POINT,
        title: "",
        x: 0,
        y: 0,
        sref: ""
    },
    avoidPoint: {
        naviPointType: RouteMarker.END_POINT,
        title: "",
        x: 0,
        y: 0,
        sref: ""
    },
    passPoint1: {
        naviPointType: RouteMarker.PASS_POINT1,
        title: "",
        x: 0,
        y: 0,
        sref: ""
    },
    avoidPoint1: {
        naviPointType: RouteMarker.AVOID_POINT1,
        title: "",
        x: 0,
        y: 0,
        sref: ""
    }
};

var objRoutePointMarker = {
    startPoint: [],
    endPoint: [],
    passPoint: [],
    avoidPoint: [],
    startMarker: [],
    endMarker: [],
    passMarker: [],
    avoidMarker: [],
    passPoint1: [],
    avoidPoint1: [],
    passMarker1: [],
    avoidMarker1: []
};

var objRst = {
    navigatePoint: null,
    navigateInfoSeq: null,
    navigatePointMaker: null,
    interval: null

}

var naviMode = 1;
var naviSearchState = -1;
var selectRouteMark = [];
var objRoutepolyline = [];

function getNaviRst(startPoint, endPoint, passPoint, avoidPoint, passPoint1, avoidPoint1, naviMode) {
    var serviceUrl = terramap.serviceBaseURL;
    terramap.serviceBaseURL = __serviceURL.route;
    var svc = terramap.getService("mmss.services.gs.navigate.INavigateSvc");
    var req = new mmss.services.gs.navigate.NavigateRequest();
    var start = new Date();
    var startLLpy = startPoint.convertToLonLat();
    var startLL = gcj02towgs84(startLLpy.lon, startLLpy.lat);
    var endLLpy = endPoint.convertToLonLat();
    var endLL = gcj02towgs84(endLLpy.lon, endLLpy.lat);

    var startPos = {};
    var endPos = {};
    var passPos = {};
    var passPos1 = {};
    var avoidPos = {};
    var avoidPos1 = {};

    if (isJP) {
        startPos.x = startLL[0] * 3600000;
        startPos.y = startLL[1] * 3600000;
        endPos.x = endLL[0] * 3600000;
        endPos.y = endLL[1] * 3600000;
    } else {
        startPos.x = startLLpy.lon * 3600000;
        startPos.y = startLLpy.lat * 3600000;
        endPos.x = endLLpy.lon * 3600000;
        endPos.y = endLLpy.lat * 3600000;
    }

    req.serviceAlias = G_routeAnalysisSvcName //"naviModel";//"a";
    req.serviceVersion = 1000;
    req.naviMode = naviMode;
    req.withNaviArray = true;
    req.srsCode = tempSpatialReference;
    //req.pathPassParams = true;
    req.startPoint = new gis3.svcs.gs.common.Point();
    req.startPoint.x = parseInt(startPos.x);
    req.startPoint.y = parseInt(startPos.y);
    req.endPoint = new gis3.svcs.gs.common.Point();
    req.endPoint.x = parseInt(endPos.x);
    req.endPoint.y = parseInt(endPos.y);
    req.passPointList = new Array();
    req.avoidPointList = new Array();
    if (passPoint != null) {
        var passLLpy = passPoint.convertToLonLat();
        var passLL = gcj02towgs84(passLLpy.lon, passLLpy.lat);
        var _passPoint = new gis3.svcs.gs.common.Point();
        if (isJP) {
            _passPoint.x = parseInt(passLL[0] * 3600000);
            _passPoint.y = parseInt(passLL[1] * 3600000);
        } else {
            _passPoint.x = parseInt(passLLpy.lon * 3600000);
            _passPoint.y = parseInt(passLLpy.lat * 3600000);

        }
        req.passPointList[0] = _passPoint;
    }

    if (avoidPoint != null) {
        var avoidLLpy = avoidPoint.convertToLonLat();
        var avoidLL = gcj02towgs84(avoidLLpy.lon, avoidLLpy.lat);
        var _avoidPoint = new gis3.svcs.gs.common.Point();

        if (isJP) {
            _avoidPoint.x = parseInt(avoidLL[0] * 3600000);
            _avoidPoint.y = parseInt(avoidLL[1] * 3600000);
        } else {
            _avoidPoint.x = parseInt(avoidLLpy.lon * 3600000);
            _avoidPoint.y = parseInt(avoidLLpy.lat * 3600000);

        }


        //avoidPoint.x = 41869198;
        //avoidPoint.y = 14381003;
        req.avoidPointList[0] = _avoidPoint;
        //req.avoidPointList = avoidPointList;
    }

    if (passPoint1 != null) {
        var passLLpy = passPoint1.convertToLonLat();
        var passLL = gcj02towgs84(passLLpy.lon, passLLpy.lat);
        var _passPoint = new gis3.svcs.gs.common.Point();

        if (isJP) {
            _passPoint.x = parseInt(passLL[0] * 3600000);
            _passPoint.y = parseInt(passLL[1] * 3600000);
        } else {
            _passPoint.x = parseInt(passLLpy.lon * 3600000);
            _passPoint.y = parseInt(passLLpy.lat * 3600000);

        }


        //passPoint.x = 41913322;
        //passPoint.y = 14388450;
        req.passPointList[1] = _passPoint;
        //req.passPointList = passPointList;
    }

    if (avoidPoint1 != null) {
        var avoidLLpy = avoidPoint1.convertToLonLat();
        var avoidLL = gcj02towgs84(avoidLLpy.lon, avoidLLpy.lat);
        var _avoidPoint = new gis3.svcs.gs.common.Point();

        if (isJP) {
            _avoidPoint.x = parseInt(avoidLL[0] * 3600000);
            _avoidPoint.y = parseInt(avoidLL[1] * 3600000);
        } else {
            _avoidPoint.x = parseInt(avoidLLpy.lon * 3600000);
            _avoidPoint.y = parseInt(avoidLLpy.lat * 3600000);

        }

        req.avoidPointList[1] = _avoidPoint;
    }

    var out = {};
    //1表示资源类型，-1表示所有
    svc.doNavigate(req, out);
    var rst = out.value.pathPointSeq;
    terramap.serviceBaseURL = serviceUrl;
    // console.log(rst);
    routeReturnMessage(out.value.IsGetId);
    // console.log(out.value.IsGetId);

    showRouteMessage(out.value.navigateString);
    // console.log(out.value.navigateString);
    // console.log(out.value.navigateInfoSeq);
    var end = new Date();
    // console.log("search useTime:" + (end.getTime() - start.getTime()) / 1000.0 + "s :");
    objRst.navigatePoint = out.value.pathPointSeq;
    objRst.navigateInfoSeq = out.value.navigateInfoSeq;
    return rst;
};

function cancelSelectTool() {
    $(".route-pushpin").css("color", "#ccc");
    $("#outerDiv").css("cursor", "default");

    G_map.getToolManager().selectTool(null, true);

}

function routeReturnMessage(msg) {
    switch (msg) {
        case "GetRoadInfoByStartPoint failed!":
            alert("起点周边无合适道路，请重新选择起点。");
            $("#route-start-input").attr("naviState", "false");
            naviSearchState = -1;
            break;
        case "GetRoadInfoByPoint failed!":
            alert("终点周边无合适道路，请重新选择终点。");
            $("#route-end-input").attr("naviState", "false");
            naviSearchState = -1;
            break;
        case "GetRoadInfoByAvoidPoint failed!":
            alert("无合适路径，请重新选点。");
            $("#route-avoid-input,#route-avoid-input1").attr("naviState", "false");
            naviSearchState = -1;
            break;
        case "GetRoadInfoByPassPoint failed!":
            alert("必经点周边无合适道路，请重新选择必经点。");
            $("#route-pass-input,#route-pass-input1").attr("naviState", "false");
            naviSearchState = -1;
            break;
        case "GetRoadInfoByEndPoint Success!":
            break;
        default:
            break;
    }
};


function showRouteMessage(msg) {
    //msg = msg.replace(/。/gm,"。\n");
    //var naviInfoArray = sortNaviInfo(msg);
    $("#routeMessage").empty();
    if (msg) {
        var naviInfoArray = msg.split("。");
        for (var i = 0; i < naviInfoArray.length - 1; i++) {
            var iconClass = matchRouteIcon(naviInfoArray[i]);
            var items = '<div class="naviInfoString">' +
                '<div class="naviInfoString00">' + naviInfoArray[i] + '</div>' +
                '<div class="naviInfoString01 ' + iconClass + '"></div>' +
                '</div>'
            $("#routeMessage").append(items);
        }
    }
};


function matchRouteIcon(info) {
    if (info.indexOf("直行") > 0) {
        return "naviInfoIconStright";
    } else if (info.indexOf("左转") > 0) {
        return "naviInfoIconTL";
    } else if (info.indexOf("右转") > 0) {
        return "naviInfoIconTR";
    } else if (info.indexOf("稍向左转") > 0) {
        return "naviInfoIconLTL";
    } else if (info.indexOf("稍向右转") > 0) {
        return "naviInfoIconLTR";
    } else if (info.indexOf("掉头") > 0) {
        return "naviInfoIconTAL";
    } else {
        return "";
    }
}

var navigateIndex = 0;
//remove in route.js by fenglei 2017.05.02
/*$(".startNavigate").live("click", function () {
    $(this).children("span").text("停止导航");
    G_map.setZoomLevel(13);
    var tttt = window.setInterval(simulateNavigate, 500);//毫秒
    objRst.interval = tttt;
    $("#naviButton").removeClass("startNavigate");
    $("#naviButton").addClass("stopNavigate");

});

$(".stopNavigate").live("click", function () {
    $(this).children("span").text("模拟导航");
    $("#naviButton").removeClass("stopNavigate");
    $("#naviButton").addClass("startNavigate");
    window.clearInterval(objRst.interval);
});*/


function simulateNavigate() {
    if (objRst.navigatePointMaker != null) {
        G_map.removeOverlay(objRst.navigatePointMaker);
        objRst.navigatePointMaker = null;
    }

    var navigateInfoSeq = objRst.navigateInfoSeq[navigateIndex];
    var currentRoadName = navigateInfoSeq.currentRoadName;
    var info = navigateInfoSeq.info;
    var nextRoadName = navigateInfoSeq.nextRoadName;
    var nextdistance = navigateInfoSeq.nextdistance;
    var totalDistance = navigateInfoSeq.totalDistance;

    var geoX = objRst.navigatePoint[navigateIndex].x;
    var geoY = objRst.navigatePoint[navigateIndex].y;

    if (isJP) {
        var _posPY = new terramap.GeoPoint(geoX, geoY, tempSpatialReference);
        var LL = _posPY.convertToLonLat();
        var LLjp = wgs84togcj02(LL.lon, LL.lat);
        var _pos = new terramap.GeoPoint(LLjp[0] * 3600000, LLjp[1] * 3600000, tempSpatialReference);
        var geocoord = _pos.convertTo(G_map.getSpatialReference());
    } else {
        var _pos = new terramap.GeoPoint(geoX, geoY, tempSpatialReference);
        var geocoord = _pos.convertTo(G_map.getSpatialReference());
    }
    var title = '<div style="width:160px; height:38px;overflow: hidden" >' + currentRoadName + '。</div>';//距离终点还有' + parseInt(totalDistance) + '公里
    var content = title;

    var icon = new terramap.maps.Icon();
    icon.image = route_imgs_url + "car.png";
    icon.iconSize = new terramap.Size(36, 36);
    icon.iconAnchor = new terramap.Point(18, 36);
    //icon.imageMap = [3,9,9,3,16,3,20,8,20,16,15,22,12,30,9,22,3,16,3,9];
    var tolay = new terramap.maps.overlay.Marker(geocoord, icon, content);
    tolay.disableDraggable();
    objRst.navigatePointMaker = tolay;

    //tolay.closeInfoWindow();
    //tolay.showInfoWindow(null,null,false)
    G_map.addOverlay(tolay);
    G_map.panTo(geocoord);
    //G_map.showInfoWindow(geocoord,title,true);

    var items = '<span>当前道路名称：' + currentRoadName + '</span><br>' +
        //'<span>下一段道路名称：' + nextRoadName + '</span><br>' +
        '<span>导航信息：' + info + '</span><br>' +
        '<span>距离终点还有：' + parseFloat(totalDistance).toFixed(3) + '公里</span><br>'
    // $("#routeInfoPanel").css("display", "block");
    // $("#routeInfoPanel").empty();
    // $("#routeInfoPanel").append(items);


    // console.log((objRst.navigatePoint.length - 1) + "==" + navigateIndex);

    if (navigateIndex == (objRst.navigatePoint.length - 1)) {
        window.clearInterval(objRst.interval);
        $(".stopNavigate span").text("模拟导航");
        $("#naviButton").removeClass("stopNavigate");
        $("#naviButton").addClass("startNavigate");

        navigateIndex = 0;
    }
    ;

    if (navigateIndex < (objRst.navigatePoint.length - 1)) {
        navigateIndex = navigateIndex + 2;
    }
    ;
    if (navigateIndex > (objRst.navigatePoint.length - 1)) {
        navigateIndex = (objRst.navigatePoint.length - 1);
    }
    //else if (navigateIndex == (objRst.navigatePoint.length-1)){
    //	window.clearInterval(objRst.interval);
    //	$(".stopNavigate").text("模拟导航");
    //	navigateIndex=0;
    //};

};


//基于网络分析服务进行的查询功能
var cbfun = null;
//网络分析，地图定位途径点工具的名称
var netanalysis_poi_tool = "netanalysis_poi_tool";
var add_netanalysis_poi_cmd = "add_netanalysis_poi_cmd";
//对网络进行的一些初始化工作
function netanalysis_init(g_map, evtMg) {
    if (g_map != null) {
        var action = new NetAnalysisToolAction();
        var callbacks = {
            "down": action._onMouseDown,
        };

        var _eventListener = new terramap.maps.listener.MouseEventListener(action, callbacks);
        action.setEventListener(_eventListener);
        g_map.addAction(action);
        registerNetAnalysisToolCmd(g_map, evtMg);
    }
}

function registerNetAnalysisToolCmd(gmap, evtMg) {
    if (gmap) {
        if (cbfun == null) {
            var _ob = {
                map: gmap,
                eMg: evtMg
            }
            cbfun = (function (ob) {
                return function (commandName, param) {
                    _net_handleSel(commandName, param, ob);
                }
            })(_ob);
        }
        var cm = gmap.getCmdManager();
        cm.register(add_netanalysis_poi_cmd, this, cbfun);
    }
}


function _net_handleSel(commandName, param, ob) {

    var gmap = ob.map;
    if (gmap == null)
        return;
    if (netAnalysisReq == null) {
        netAnalysisReq = new gis3.svcs.gs.nas.NetworkAnalyseRequest();
    }
    var p = new gis3.svcs.gs.common.Point(param.x, param.y);
    netAnalysisReq.paramNodeList.push(p);

}

function doNetAnalysis(emg) {
    if (netAnalysisReq == null) {
        return;
    }
    netAnalysis = terramap.getService("gis3.svcs.gs.nas.INetworkAnalyse");

    if (emg != null) {
        // 用户设定请求过虑器
        emg.fireEvent(EventManager.prototype.EVENTTYPE[9], [netAnalysisReq]);
    }
    var out = {};
    netAnalysis.kShortestPath(netAnalysisReq, out);
    var analysis_value = out.value;
    if (emg != null) {
        emg.fireEvent(EventManager.prototype.EVENTTYPE[10], [analysis_value]);
    }
}

//网络分析工具的action
var NetAnalysisToolAction = terramap.declare(
    terramap.maps.Action,
    {
        //网络分析在地图上标注符号的工具
        ASSOCIATED_TOOLS: [
            netanalysis_poi_tool
        ],

        /**
         * 构造NetAnalysisToolAction对象。
         */
        initialize: function (options) {
            terramap.maps.Action.prototype.initialize.apply(this, arguments);
            terramap.extend(this, options);
        },

        setMap: function (map) {
            terramap.maps.Action.prototype.setMap.apply(this, arguments);
        },

        /**
         * Parameters:
         * evt - {Event}
         */
        _onMouseDown: function (evt) {
            //add marker;
            if (this.map != null) {
                var pixel = new terramap.Point(evt.xy.x, evt.xy.y + 16);
                var poi = this.map.fromPixelToGeoCoord(pixel);
                var marker = addRouteFlag(poi);
                this.map.addOverlay(marker);
                this.map.executeCmd(add_netanalysis_poi_cmd, poi);
                G_map.getToolManager().selectTool(null, true);
                $("#outerDiv").css("cursor", "default");
                document.removeEventListener("mousedown", cancelSelectTool);
                $("#outerDiv").attr("oncontextmenu", "return(true)");
                console.log("removeEventListener cancelSelectTool");
            }
        },

        //deactivate: function(){
        //    console.log("123123");
        //G_map.getToolManager().selectTool(null, true);
        //}
    });

function addRouteFlag(poi) {
    var icon = new terramap.maps.Icon();
    //icon.image = route_imgs_url + "startpoint.png";
    icon.iconSize = new terramap.Size(36, 36);
    icon.iconAnchor = new terramap.Point(18, 36);
    //icon.imageMap = [3,9,9,3,16,3,20,8,20,16,15,22,12,30,9,22,3,16,3,9];
    var content = '';


    switch (RouteMarkerType) {
        case RouteMarker.START_POINT:
            if (objRoutePointMarker.startPoint.length >= 1) {
                G_map.removeOverlay(objRoutePointMarker.startMarker[0]);
                objRoutePointMarker.startPoint = [];
                objRoutePointMarker.startMarker = [];
            }
            icon.image = route_imgs_url + "startpoint.png";
            content = '起点';
            content = '<div style="width:160px; height:38px;overflow: hidden" >' + content + '</div>';
            break;
        case RouteMarker.END_POINT:
            if (objRoutePointMarker.endPoint.length >= 1) {
                G_map.removeOverlay(objRoutePointMarker.endMarker[0]);
                objRoutePointMarker.endPoint = [];
                objRoutePointMarker.endMarker = [];
            }
            icon.image = route_imgs_url + "endpoint.png";
            content = '终点';
            content = '<div style="width:160px; height:38px;overflow: hidden" >' + content + '</div>';
            break;
        case RouteMarker.PASS_POINT:
            if (objRoutePointMarker.passPoint.length >= 1) {
                G_map.removeOverlay(objRoutePointMarker.passMarker[0]);
                objRoutePointMarker.passPoint = [];
                objRoutePointMarker.passMarker = [];
            }
            icon.image = route_imgs_url + "mustpoint.png";
            content = '必经点';
            content = '<div style="width:160px; height:38px;overflow: hidden" >' + content + '</div>';
            break;
        case RouteMarker.AVOID_POINT:
            if (objRoutePointMarker.avoidPoint.length >= 1) {
                G_map.removeOverlay(objRoutePointMarker.avoidMarker[0]);
                objRoutePointMarker.avoidPoint = [];
                objRoutePointMarker.avoidMarker = [];
            }
            icon.image = route_imgs_url + "avoidpoint.png";
            content = '回避点';
            content = '<div style="width:160px; height:38px;overflow: hidden" >' + content + '</div>';
            break;
        case RouteMarker.PASS_POINT1:
            if (objRoutePointMarker.passPoint1.length >= 1) {
                G_map.removeOverlay(objRoutePointMarker.passMarker1[0]);
                objRoutePointMarker.passPoint1 = [];
                objRoutePointMarker.passMarker1 = [];
            }
            icon.image = route_imgs_url + "mustpoint.png";
            content = '必经点2';
            content = '<div style="width:160px; height:38px;overflow: hidden" >' + content + '</div>';
            break;
        case RouteMarker.AVOID_POINT1:
            if (objRoutePointMarker.avoidPoint1.length >= 1) {
                G_map.removeOverlay(objRoutePointMarker.avoidMarker1[0]);
                objRoutePointMarker.avoidPoint1 = [];
                objRoutePointMarker.avoidMarker1 = [];
            }
            icon.image = route_imgs_url + "avoidpoint.png";
            content = '回避点2';
            content = '<div style="width:160px; height:38px;overflow: hidden" >' + content + '</div>';
            break;
        default:
            break;
    }

    var mark = new terramap.maps.overlay.Marker(poi, icon, content);
    mark.disableDraggable();
    //G_map.showInfoWindow(poi,content,true);

    switch (RouteMarkerType) {
        case RouteMarker.START_POINT:
            objRoutePointMarker.startMarker.push(mark);
            objRoutePointMarker.startPoint.push(poi);
            $(".route-start-input").val("起点(地图选点)");
            $("#route-start-pushpin").css("color", "#ccc");
            $(".route-start-input").attr("naviState", "false");
            naviSearchState = -1;
            //$(".route-start-input").val("x:"+ poi.x +" y:"+ poi.y);
            break;
        case RouteMarker.END_POINT:
            objRoutePointMarker.endMarker.push(mark);
            objRoutePointMarker.endPoint.push(poi);
            $(".route-end-input").val("终点(地图选点)");
            $(".route-end-input").attr("naviState", "false");
            naviSearchState = -1;
            $("#route-end-pushpin").css("color", "#ccc");
            //$(".route-end-input").val("x:"+ poi.x +" y:"+ poi.y);
            break;
        case RouteMarker.PASS_POINT:
            objRoutePointMarker.passMarker.push(mark);
            objRoutePointMarker.passPoint.push(poi);
            $("#route-pass-input").val("必经点(地图选点)");
            $("#route-pass-input").attr("naviState", "false");
            naviSearchState = -1;
            $("#route-pass-pushpin").css("color", "#ccc");
            //$(".route-pass-input").val("x:"+ poi.x +" y:"+ poi.y);
            break;
        case RouteMarker.AVOID_POINT:
            objRoutePointMarker.avoidMarker.push(mark);
            objRoutePointMarker.avoidPoint.push(poi);
            $("#route-avoid-input").val("回避点(地图选点)");
            $("#route-avoid-input").attr("naviState", "false");
            naviSearchState = -1;
            $("#route-avoid-pushpin").css("color", "#ccc");
            //$(".route-avoid-input").val("x:"+ poi.x +" y:"+ poi.y);
            break;
        case RouteMarker.PASS_POINT1:
            objRoutePointMarker.passMarker1.push(mark);
            objRoutePointMarker.passPoint1.push(poi);
            $("#route-pass-input1").val("必经点2(地图选点)");
            $("#route-pass-input1").attr("naviState", "false");
            naviSearchState = -1;
            $("#route-pass-pushpin1").css("color", "#ccc");
            //$(".route-pass-input").val("x:"+ poi.x +" y:"+ poi.y);
            break;
        case RouteMarker.AVOID_POINT1:
            objRoutePointMarker.avoidMarker1.push(mark);
            objRoutePointMarker.avoidPoint1.push(poi);
            $("#route-avoid-input1").val("回避点2(地图选点)");
            $("#route-avoid-input1").attr("naviState", "false");
            naviSearchState = -1;
            $("#route-avoid-pushpin1").css("color", "#ccc");
            //$(".route-avoid-input").val("x:"+ poi.x +" y:"+ poi.y);
            break;
        default:
            break;
    }
    return mark;
}

function navSearch(searchInfo) {

    terramap.setServiceCallAckEncoding("utf-8");
    terramap.setServiceCallReqEncoding("utf-8");

    var serviceUrl = terramap.serviceBaseURL;
    terramap.serviceBaseURL = __serviceURL.search;

    var proxy = terramap.getService("mmss.services.gs.search.ISearchService");

    var start = new Date();
    var __options = {};
    __options.timeout = 300000;
    var req = new mmss.services.gs.geoquery.QueryRequest();
    var resultFilter = new mmss.services.gs.geoquery.ResultFilter();
    var spatialFilter = new mmss.services.gs.geoquery.SpatialFilter();
    resultFilter.__proto__.loadMulti = true;
    resultFilter.fieldNames = ["name", "desc", "regioncode", "zipcode", "phone", "addr"];
    req.resultFilter = resultFilter;
    req.spatialFilter = spatialFilter;
    resultFilter.featureNum = 3;//返回结果个数

    req.searchInfo = searchInfo;

    var result = new mmss.services.gs.geoquery.QueryResult();
    proxy.search(req, result, __options);

    var end = new Date();
    console.log("search useTime:" + (end.getTime() - start.getTime()) / 1000.0 + "s :");
    terramap.serviceBaseURL = serviceUrl;

    return result;
};


function showRouteSearchResultList(searchInfo, naviPointType) {
    $.fn = newJquery.fn;
    var result = navSearch(searchInfo);
    //var minList=(pageNum-1)*listnum;
    //var maxList=pageNum*listnum;
    var geoDataList = result.value.datas.geoDataList;
    if (geoDataList.length > 0) {
        $('#routeSearchList').empty();
        selectRouteMark = [];
        //$('#sole-input').blur();
        for (var i = 0; i < geoDataList.length; i++) {
            //if (i>=minList&&i<maxList){
            $('#routeSearchList').searchItems({
                'type': "dq",
                'icon': "fa-map-marker",
                'content': geoDataList[i].attrData[0],//11
                'geoType': geoDataList[i].geoType,
                'pointList': geoDataList[i].geoData[0].pointList,
                'index': i,
                'phoneNumber': geoDataList[i].attrData[4],
                'address': geoDataList[i].attrData[5],//12
                'className': 'selectAsPoint',
                'naviPointType': naviPointType
            });
            if (geoDataList[i].geoType == 1) {
                addRouteMaker(geoDataList[i].geoData[0].pointList, naviPointType);
            } else if (geoDataList[i].geoType == 2) {
                addMakerPolyline(geoDataList[i].geoData[0].pointList, i % listnum);
            } else if (geoDataList[i].geoType == 4) {
                addMakerPolygon(geoDataList[i].geoData[0].pointList, i % listnum);
            }
            ;
            //}
        }

        $("#card-route1").css("display", "block");
        //$("#cards-level0>li").eq(1).css("display","block");
        //$("#cards-level2").css("display","block");
    } else {
        //alert("没有查询到结果！");
        $("#card-1-0").css("display", "block");
        $("#card-route1").css("display", "none");
    }
};

//remove in route.js by fenglei 2017.05.02
/*$(".selectAsPoint").live("click", function () {
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

});*/


function selectNaviPoint(objInfo) {
    if (isJP == true) {
        var _pos = new terramap.GeoPoint(objInfo.x, objInfo.y, tempSpatialReference);
        var LL = _pos.convertToLonLat();
        var LLjp = wgs84togcj02(LL.lon, LL.lat);
        var geocoord = new terramap.GeoPoint(LLjp[0] * 3600000, LLjp[1] * 3600000, tempSpatialReference);
        var poi = geocoord.convertTo(G_map.getSpatialReference());
    } else {
        var _pos = new terramap.GeoPoint(objInfo.x, objInfo.y, tempSpatialReference);
        var poi = _pos.convertTo(G_map.getSpatialReference());
    }

    var title = objInfo.title;
    var icon = new terramap.maps.Icon();
    //icon.image = route_imgs_url + startpoint.png";
    icon.iconSize = new terramap.Size(36, 36);
    icon.iconAnchor = new terramap.Point(18, 36);
    //icon.imageMap = [3,9,9,3,16,3,20,8,20,16,15,22,12,30,9,22,3,16,3,9];
    var content = '';
    var pointType = parseInt(objInfo.naviPointType);


    switch (pointType) {
        case RouteMarker.START_POINT:
            if (objRoutePointMarker.startPoint.length >= 1) {
                G_map.removeOverlay(objRoutePointMarker.startMarker[0]);
                objRoutePointMarker.startPoint = [];
                objRoutePointMarker.startMarker = [];
            }
            icon.image = route_imgs_url + "startpoint.png";
            break;
        case RouteMarker.END_POINT:
            if (objRoutePointMarker.endPoint.length >= 1) {
                G_map.removeOverlay(objRoutePointMarker.endMarker[0]);
                objRoutePointMarker.endPoint = [];
                objRoutePointMarker.endMarker = [];
            }
            icon.image = route_imgs_url + "endpoint.png";
            break;
        case RouteMarker.PASS_POINT:
            if (objRoutePointMarker.passPoint.length >= 1) {
                G_map.removeOverlay(objRoutePointMarker.passMarker[0]);
                objRoutePointMarker.passPoint = [];
                objRoutePointMarker.passMarker = [];
            }
            icon.image = route_imgs_url + "mustpoint.png";
            break;
        case RouteMarker.AVOID_POINT:
            if (objRoutePointMarker.avoidPoint.length == 1) {
                G_map.removeOverlay(objRoutePointMarker.avoidMarker[0]);
                objRoutePointMarker.avoidPoint = [];
                objRoutePointMarker.avoidMarker = [];
            }
            icon.image = route_imgs_url + "avoidpoint.png";
            break;
        case RouteMarker.PASS_POINT1:
            if (objRoutePointMarker.passPoint1.length >= 1) {
                G_map.removeOverlay(objRoutePointMarker.passMarker1[0]);
                objRoutePointMarker.passPoint1 = [];
                objRoutePointMarker.passMarker1 = [];
            }
            icon.image = route_imgs_url + "mustpoint.png";
            break;
        case RouteMarker.AVOID_POINT1:
            if (objRoutePointMarker.avoidPoint.length == 1) {
                G_map.removeOverlay(objRoutePointMarker.avoidMarker1[0]);
                objRoutePointMarker.avoidPoint1 = [];
                objRoutePointMarker.avoidMarker1 = [];
            }
            icon.image = route_imgs_url + "avoidpoint.png";
            break;
        default:
            break;
    }

    var mark = new terramap.maps.overlay.Marker(poi, icon, content);

    mark.disableDraggable();
    G_map.addOverlay(mark);

    switch (pointType) {
        case RouteMarker.START_POINT:
            objRoutePointMarker.startMarker.push(mark);
            objRoutePointMarker.startPoint.push(poi);
            $(".route-start-input").attr("naviState", "true");
            $(".route-start-input").val(title);
            break;
        case RouteMarker.END_POINT:
            objRoutePointMarker.endMarker.push(mark);
            objRoutePointMarker.endPoint.push(poi);
            $(".route-end-input").attr("naviState", "true");
            $(".route-end-input").val(title);
            break;
        case RouteMarker.PASS_POINT:
            objRoutePointMarker.passMarker.push(mark);
            objRoutePointMarker.passPoint.push(poi);
            $("#route-pass-input").attr("naviState", "true");
            $("#route-pass-input").val(title);
            break;
        case RouteMarker.AVOID_POINT:
            objRoutePointMarker.avoidMarker.push(mark);
            objRoutePointMarker.avoidPoint.push(poi);
            $("#route-avoid-input").attr("naviState", "true");
            $("#route-avoid-input").val(title);
            break;
        case RouteMarker.PASS_POINT1:
            objRoutePointMarker.passMarker1.push(mark);
            objRoutePointMarker.passPoint1.push(poi);
            $("#route-pass-input1").attr("naviState", "true");
            $("#route-pass-input1").val(title);
            break;
        case RouteMarker.AVOID_POINT1:
            objRoutePointMarker.avoidMarker1.push(mark);
            objRoutePointMarker.avoidPoint1.push(poi);
            $("#route-avoid-input1").attr("naviState", "true");
            $("#route-avoid-input1").val(title);
            break;
        default:
            break;
    }
}

function doNaviSearch() {
    var route_start_input = $("#route-start-input").val();
    var route_end_input = $("#route-end-input").val();
    var route_pass_input = $("#route-pass-input").val();
    var route_avoid_input = $("#route-avoid-input").val();
    var route_pass_input1 = $("#route-pass-input1").val();
    var route_avoid_input1 = $("#route-avoid-input1").val();

    var naviState_start = $("#route-start-input").attr("naviState");
    var naviState_end = $("#route-end-input").attr("naviState");
    var naviState_pass = $("#route-pass-input").attr("naviState");
    var naviState_avoid = $("#route-avoid-input").attr("naviState");
    var naviState_pass1 = $("#route-pass-input1").attr("naviState");
    var naviState_avoid1 = $("#route-avoid-input1").attr("naviState");

    if (route_start_input != '起点(地图选点)' && route_start_input != '终点(地图选点)' && naviState_start != "true") {
        $("#routeSearchTitle").text("请选择准确的起点");
        showRouteSearchResultList(route_start_input, RouteMarker.START_POINT);
        return 0;
    } else if (route_end_input != '终点(地图选点)' && route_end_input != '起点(地图选点)' && naviState_end != "true") {
        $("#routeSearchTitle").text("请选择准确的终点");
        showRouteSearchResultList(route_end_input, RouteMarker.END_POINT);
        return 0;
    } else if (route_pass_input != '请输入必经点...' && route_pass_input != '必经点(地图选点)' && route_pass_input != null && naviState_pass != "true") {
        $("#routeSearchTitle").text("请选择准确的必经点");
        showRouteSearchResultList(route_pass_input, RouteMarker.PASS_POINT);
        return 0;
    } else if (route_avoid_input != '请输入回避点...' && route_avoid_input != '回避点(地图选点)' && route_avoid_input != null && naviState_avoid != "true") {
        $("#routeSearchTitle").text("请选择准确的回避点");
        showRouteSearchResultList(route_avoid_input, RouteMarker.AVOID_POINT);
        return 0;
    } else if (route_pass_input1 != '请输入必经点...' && route_pass_input1 != '必经点2(地图选点)' && route_pass_input1 != null && naviState_pass1 != "true") {
        $("#routeSearchTitle").text("请选择准确的必经点");
        showRouteSearchResultList(route_pass_input1, RouteMarker.PASS_POINT1);
        return 0;
    } else if (route_avoid_input1 != '请输入回避点...' && route_avoid_input1 != '回避点2(地图选点)' && route_avoid_input1 != null && naviState_avoid1 != "true") {
        $("#routeSearchTitle").text("请选择准确的回避点");
        showRouteSearchResultList(route_avoid_input1, RouteMarker.AVOID_POINT1);
        return 0;
    } else {
        naviSearchState = 1;
        naviGo();
        //$("#search-route-button").trigger("click");
        return 1;
    }
};

function addRouteMaker(point, naviPointType) {

    if (isJP == true) {
        var _pos = new terramap.GeoPoint(point[0].x * 3600000, point[0].y * 3600000, tempSpatialReference);
        var LL = _pos.convertToLonLat();
        var LLjp = wgs84togcj02(LL.lon, LL.lat);
        var geocoord = new terramap.GeoPoint(LLjp[0] * 3600000, LLjp[1] * 3600000, tempSpatialReference);
        var poi = geocoord.convertTo(G_map.getSpatialReference());
    } else {
        var _pos = new terramap.GeoPoint(point[0].x * 3600000, point[0].y * 3600000, tempSpatialReference);
        var poi = _pos.convertTo(G_map.getSpatialReference());
    }

    var icon = new terramap.maps.Icon();

    switch (naviPointType) {
        case RouteMarker.START_POINT:
            icon.image = route_imgs_url + "startpoint.png";
            break;
        case RouteMarker.END_POINT:
            icon.image = route_imgs_url + "endpoint.png";
            break;
        case RouteMarker.PASS_POINT:
            icon.image = route_imgs_url + "mustpoint.png";
            break;
        case RouteMarker.AVOID_POINT:
            icon.image = route_imgs_url + "avoidpoint.png";
            break;
        case RouteMarker.PASS_POINT1:
            icon.image = route_imgs_url + "mustpoint.png";
            break;
        case RouteMarker.AVOID_POINT1:
            icon.image = route_imgs_url + "avoidpoint.png";
            break;
        default:
            break;
    }
    icon.iconSize = new terramap.Size(36, 36);
    icon.iconAnchor = new terramap.Point(18, 36);
    //icon.imageMap = [3,9,9,3,16,3,20,8,20,16,15,22,12,30,9,22,3,16,3,9];
    var content = '';
    var mark = new terramap.maps.overlay.Marker(poi, icon, content);
    selectRouteMark.push(mark);
    mark.disableDraggable();
    G_map.addOverlay(mark);
}

function naviGo() {
    var route_start_input = $("#route-start-input").val();
    var route_end_input = $("#route-end-input").val();
    var route_pass_input = $("#route-pass-input").val();
    var route_avoid_input = $("#route-avoid-input").val();
    var route_pass_input1 = $("#route-pass-input1").val();
    var route_avoid_input1 = $("#route-avoid-input1").val();


    if (route_start_input != '起点(地图选点)' && route_start_input != '终点(地图选点)') {
        setRouteHistoryCookies(route_start_input, "routeStartHistory");
    }
    if (route_end_input != '终点(地图选点)' && route_end_input != '起点(地图选点)') {
        setRouteHistoryCookies(route_end_input, "routeEndHistory");
    }

    if (route_pass_input != '请输入必经点...' && route_pass_input != '必经点(地图选点)' && route_pass_input != null) {
        setRouteHistoryCookies(route_pass_input, "routePassHistory");

    }
    if (route_avoid_input != '请输入回避点...' && route_avoid_input != '回避点(地图选点)' && route_avoid_input != null) {
        setRouteHistoryCookies(route_avoid_input, "routeAvoidHistory");
    }

    if (route_pass_input1 != '请输入必经点...' && route_pass_input1 != '必经点2(地图选点)' && route_pass_input1 != null) {
        setRouteHistoryCookies(route_pass_input1, "routePassHistory1");

    }
    if (route_avoid_input1 != '请输入回避点...' && route_avoid_input1 != '回避点2(地图选点)' && route_avoid_input1 != null) {
        setRouteHistoryCookies(route_avoid_input1, "routeAvoidHistory1");
    }

    console.log('startPoint:' + objRoutePointMarker.startPoint + '\n');
    console.log('endPoint:' + objRoutePointMarker.endPoint + '\n');
    console.log('passPoint:' + objRoutePointMarker.passPoint + '\n');
    console.log('avoidPoint:' + objRoutePointMarker.avoidPoint + '\n');
    console.log('passPoint1:' + objRoutePointMarker.passPoint1 + '\n');
    console.log('avoidPoint1:' + objRoutePointMarker.avoidPoint1 + '\n');
    console.log('naviMode:' + naviMode + '\n');
    var naviPoint = getNaviRst(
        objRoutePointMarker.startPoint[0], objRoutePointMarker.endPoint[0],
        objRoutePointMarker.passPoint[0], objRoutePointMarker.avoidPoint[0],
        objRoutePointMarker.passPoint1[0], objRoutePointMarker.avoidPoint1[0],
        parseInt(naviMode));

    var out = [];
    if (naviPoint.length > 0) {
        $("#card-route1").css("display", "none");
        $("#card-route0,#route-favorite").css("display", "block");
        for (var i = 0; i < naviPoint.length; i++) {
            if (isJP) {
                var _pos = new terramap.GeoPoint(naviPoint[i].x, naviPoint[i].y, tempSpatialReference);
                var LL = _pos.convertToLonLat();
                var LLjp = wgs84togcj02(LL.lon, LL.lat);
                var geocoord = new terramap.GeoPoint(LLjp[0] * 3600000, LLjp[1] * 3600000, tempSpatialReference);
            } else {
                var geocoord = new terramap.GeoPoint(naviPoint[i].x, naviPoint[i].y, tempSpatialReference);
            };
            var gp = geocoord.convertTo(G_map.getSpatialReference());
            out.push(gp);
            //var label = new terramap.maps.overlay.TextLabel(out[0], new terramap.Size(200, 50), path.getAttribute("name"), "#RRGGBB");
            //G_map.addOverlay(label);
        }
        var polyline = new terramap.maps.overlay.Polyline(out, "#00ff00", 3, 1);//#06a009
        G_map.removeOverlay(objRoutepolyline[0]);
        objRoutepolyline = [];
        G_map.addOverlay(polyline);
        objRoutepolyline.push(polyline);

        //储存路线规划信息
        saveToFavorite("startPoint", route_start_input, objRoutePointMarker.startPoint[0], RouteMarker.START_POINT);
        saveToFavorite("endPoint", route_end_input, objRoutePointMarker.endPoint[0], RouteMarker.END_POINT);
        saveToFavorite("avoidPoint", route_avoid_input, objRoutePointMarker.avoidPoint[0], RouteMarker.AVOID_POINT);
        saveToFavorite("avoidPoint1", route_avoid_input1, objRoutePointMarker.avoidPoint1[0], RouteMarker.AVOID_POINT1);
        saveToFavorite("passPoint", route_pass_input, objRoutePointMarker.passPoint[0], RouteMarker.PASS_POINT);
        saveToFavorite("passPoint1", route_pass_input1, objRoutePointMarker.passPoint1[0], RouteMarker.PASS_POINT1);
    }
    ;
    //var geocoord0 = new terramap.GeoPoint(116.39*3600000, 39.91*3600000, tempSpatialReference);
    //var _pos = geocoord0.convertTo(G_map.getSpatialReference());
    //G_map.setCenter(_pos, 12);


    //var geocoord0 = new terramap.GeoPoint(naviPoint[0].x, naviPoint[0].y, _sref);
    //var tolay0 = new terramap.maps.overlay.Marker(geocoord0, null,naviInfo[route_start_point].Name);
    //tolay0.disableDraggable();
    //G_map.addOverlay(tolay0);
    //var geocoord1 = new terramap.GeoPoint(naviPoint[naviPoint.length-1].x, naviPoint[naviPoint.length-1].y,_sref);
    //var tolay1 = new terramap.maps.overlay.Marker(geocoord1, null,naviInfo[route_end_point].Name);
    //tolay1.disableDraggable();
    //G_map.addOverlay(tolay1);
    //G_map.setCenter(tolay0,8);
}

//cookie 搜索关键词历史记录
var setRouteHistoryCookies = function (kwd, cookieName) {
    var history = [];
    var cookies = decodeURI(getCookie(cookieName))
    if (cookies == "") {
        history.unshift(kwd);
    } else {
        history = cookies.split(",");
    }
    for (var i = 0; i < history.length; i++) {
        if (history[i] == kwd) {
            history.splice(i, 1);
            break;
        }
    }
    history.unshift(kwd);
    if (history.length > 8) {
        history.pop();
    }
    setCookie(cookieName, encodeURI(history.join()), 30);
    //console.log(history);
    //console.log(history.join());
}