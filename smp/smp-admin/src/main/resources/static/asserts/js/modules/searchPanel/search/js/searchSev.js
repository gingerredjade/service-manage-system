/**
 * Created by ShiYu on 2016/9/26.
 */

var searchResultTemp;
var collectTemp;
var collectListTemp = [];
var listnum;
var contentH;
var geojsonhead = '{"type": "FeatureCollection","features": [';
var geojsonlast = ']}';
var geojsonpoints = '';

if (screen_height < 768)  {listnum = 3;contentH=320};
if (screen_height >= 768 && screen_height < 800)  {listnum = 3;contentH=370};
if (screen_height >= 800 && screen_height < 960)  {listnum = 3;contentH=390};
if (screen_height >= 960 && screen_height < 1024)  {listnum = 4;contentH=550};
if (screen_height >= 1024 && screen_height < 1050) {listnum = 5;contentH=600};
if (screen_height >= 1050) {listnum = 6;contentH=850};
var listnumRoute = listnum/2;
$("#search-item-content .tab-content").css("height", contentH+"px");

function searchByProperty(index, id) {

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
    var attrsearchinfo = new mmss.services.gs.geoquery.AttrSearchInfo();
    var searchcatalrequest0 = new mmss.services.gs.catalog.SearchCatalogRequest();
    req.attrSearchInfos = [];
    attrsearchinfo.fieldName = "GIS__F_ID";
    attrsearchinfo.matchValue = id;
    console.log(id);
    attrsearchinfo.attrSearchMatchType = "ATTRSEARCH_MATCH_MUST";
    attrsearchinfo.attrSearchType = "ATTRSEARCH_TKYPE_MATCH";
    req.attrSearchInfos.push(attrsearchinfo);
    resultFilter.__proto__.loadMulti = true;
    resultFilter.fieldNames = ["name", "desc", "regioncode", "zipcode", "phone", "addr"];
    req.resultFilter = resultFilter;
    req.spatialFilter = spatialFilter;
    //resultFilter.featureNum = 20;

    //req.searchInfo = searchInfo;

    var result = new mmss.services.gs.geoquery.QueryResult();
    proxy.search(req, result, __options);

    var end = new Date();
    console.log("search useTime:" + (end.getTime() - start.getTime()) / 1000.0 + "s :");
    terramap.serviceBaseURL = serviceUrl;
    console.log(result);
    collectListTemp.push(result);
    // DynamicPaginationFav(Math.ceil(collectListTemp.length / listnum), "poi");
};

// function showCollectList(pageNum) {
//     var old = $.fn;
//     $.fn = newJquery.fn;
//     //var result = collectListTemp;
//     var minList = (pageNum - 1) * listnum;
//     var maxList = pageNum * listnum;
//     //var geoDataList = result.value.datas.geoDataList;
//     if (collectListTemp.length > 0) {
//         $('#collect-list').empty();
//         geojsonpoints="";
//         for (var i = 0; i < collectListTemp.length; i++) {
//             if (i >= minList && i < maxList) {
//                 $('#collect-list').searchItems({
//                     'type': "dq",
//                     'icon': "fa-map-marker",
//                     'content': collectListTemp[i].value.datas.geoDataList[0].attrData[0],
//                     'geoType': collectListTemp[i].value.datas.geoDataList[0].geoType,
//                     'pointList': collectListTemp[i].value.datas.geoDataList[0].geoData[0].pointList,
//                     'index': i,
//                     'phoneNumber': collectListTemp[i].value.datas.geoDataList[0].attrData[4],
//                     'address': collectListTemp[i].value.datas.geoDataList[0].attrData[5],
//                     'featureID': collectListTemp[i].value.datas.geoDataList[0].featureID,
//                     'className': 'showPonitCollect'
//                 });
//                 if (collectListTemp[i].value.datas.geoDataList[0].geoType == 1) {
//                     addMakerPonit(collectListTemp[i].value.datas.geoDataList[0].attrData[0], collectListTemp[i].value.datas.geoDataList[0].geoData[0].pointList, i % listnum);
//                 } else if (collectListTemp[i].value.datas.geoDataList[0].geoType == 2) {
//                     addMakerPolyline(collectListTemp[i].value.datas.geoDataList[0].geoData[0].pointList, i % listnum);
//                 } else if (collectListTemp[i].value.datas.geoDataList[0].geoType == 4) {
//                     addMakerPolygon(collectListTemp[i].value.datas.geoDataList[0].geoData[0].pointList, i % listnum);
//                 }
//             }
//         }
//         //FOR 3D
//         //if(G_3dMapView){
//         //    geojsonpoints = geojsonhead+geojsonpoints.replace(",","")+geojsonlast;
//         //    geojsonpoints = $.parseJSON(geojsonpoints);
//         //    var dataSource = Cesium.GeoJsonDataSource.load(geojsonpoints);
//         //    G_3dMapView.dataSources.add(dataSource);
//         //}
//         //END 3D
//     } else {
//         //$('#collect-list').text("没有收藏点");
//     }
//     $.fn = old;
// };

function searchServer(searchInfo, searchType) {
    if (searchType == "all") searchType = "";

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
    req.attrSearchInfos = [];
    req.layerID = searchType;
    resultFilter.__proto__.loadMulti = true;
    resultFilter.fieldNames =
        [
            //共有字段
            "name",
            //地名特有字段
            "desc", "regioncode", "zipcode", "phone", "addr",
            //兵要数据特有字段
            "typename","flm","qym","sxm","jyjcdlxxbm",//编码
            "szgjmc","szdmc","dlfw","zbtd","jianshu","cjsj","cjdw",//名称
            "gd_layerID"//数据类型
        ];
    req.resultFilter = resultFilter;
    req.spatialFilter = spatialFilter;
    //resultFilter.featureNum = 20;

    req.searchInfo = searchInfo;

    var result = new mmss.services.gs.geoquery.QueryResult();
    proxy.search(req, result, __options);

    var end = new Date();
    console.log("search useTime:" + (end.getTime() - start.getTime()) / 1000.0 + "s :");
    terramap.serviceBaseURL = serviceUrl;
    searchResultTemp = result;
    console.log(result);
    showSearchResultList(1);
    DynamicPagination(Math.ceil(result.value.datas.geoDataList.length / listnum));
    if (result.value.total > 0) {
        return 1;
    } else {
        //attendionTip("没有搜索到相关结果,搜索其他关键词试试");
        return 0;
    }

};

function showSearchResultList(pageNum) {
    var old = $.fn;
    $.fn = newJquery.fn;
    var result = searchResultTemp;
    var minList = (pageNum - 1) * listnum;
    var maxList = pageNum * listnum;
    var geoDataList = result.value.datas.geoDataList;
    if (geoDataList.length > 0) {
        $('#search-list').empty();
        geojsonpoints='';
        $('#sole-input').blur();
        for (var i = 0; i < geoDataList.length; i++) {
            if (i >= minList && i < maxList) {
                $('#search-list').searchItems({
                    'type': "dq",
                    'icon': "fa-map-marker",
                    'content': geoDataList[i].attrData[0],
                    'geoType': geoDataList[i].geoType,
                    'pointList': geoDataList[i].geoData[0].pointList,
                    'index': i,
                    'phoneNumber': geoDataList[i].attrData[4],
                    'address': geoDataList[i].attrData[5],
                    'featureID': geoDataList[i].featureID
                });
                if (geoDataList[i].geoType == 1) {
                    addMakerPonit(geoDataList[i].attrData[0], geoDataList[i].geoData[0].pointList, i % listnum);
                } else if (geoDataList[i].geoType == 2) {
                    addMakerPolyline(geoDataList[i].geoData[0].pointList, i % listnum);
                } else if (geoDataList[i].geoType == 4) {
                    addMakerPolygon(geoDataList[i].geoData[0].pointList, i % listnum);
                }
                ;
            }
        }
        //FOR 3D
        //if(G_3dMapView){
        //    geojsonpoints = geojsonhead+geojsonpoints.replace(",","")+geojsonlast;
        //    geojsonpoints = $.parseJSON(geojsonpoints);
        //    var dataSource = Cesium.GeoJsonDataSource.load(geojsonpoints);
        //    G_3dMapView.dataSources.add(dataSource);
        //};
        //END 3D
        $("#search-list").parent().css("display", "block");
        //$("#cards-level0>li").eq(1).css("display","block");
        $("#cards-level2").css("display", "block");
    } else {
        //alert("没有查询到结果！");
        $("#card-1-0").css("display", "block");
    }
    $.fn = old;
};

function addMakerPonit(name, point, index) {
    var x, y;
    if (isJP) {
        var LLjp = wgs84togcj02(point[0].x, point[0].y);
        x = LLjp[0];
        y = LLjp[1];
    } else {
        x = point[0].x;
        y = point[0].y;
    }

    var featurepoint = '{"type": "Feature","geometry": {"type": "Point","coordinates": ['+x+','+y+']},' +
        '"properties": {"title": "'+(parseInt(index)+1)+'","marker-symbol": "'+(parseInt(index)+1)+'","marker-color": "#D13C3C"}}';
    geojsonpoints = geojsonpoints+","+featurepoint;
    $(document).addLayerMaker({
        'index': index,
        'content': name,
        'x': x,
        'y': y
    });
};

var objOverlay = {
    point: [],
    polyline: [],
    polygon: []
};

function addMakerPolyline(plist, index) {
    addMakerPonit(name, plist, index);
    var pout = [];
    for (var i = 0; i < plist.length; i++) {
        if (isJP) {
            var _pos = new terramap.GeoPoint(plist[i].x * 3600000, plist[i].y * 3600000, tempSpatialReference);
            var LL = _pos.convertToLonLat();
            var LLjp = wgs84togcj02(LL.lon, LL.lat);
            var geocoord = new terramap.GeoPoint(LLjp[0] * 3600000, LLjp[1] * 3600000, tempSpatialReference);
        } else {
            var geocoord = new terramap.GeoPoint(plist[i].x * 3600000, plist[i].y * 3600000, tempSpatialReference);
        }
        ;
        var gp = geocoord.convertTo(G_map.getSpatialReference());
        pout.push(gp);
    }
    var polyline = new terramap.maps.overlay.Polyline(pout, "#00ff00", 3, 1);
    G_map.addOverlay(polyline);
    polyline.events = new terramap.Events(polyline, polyline.div, null, true);
    polyline.events.register("mouseover", polyline, function () {
        polyline.div.style.cursor = "pointer";
        console.log("hotAreaEvents mouseover!!!!")
    });
    polyline.events.register("mousedown", polyline, function () {
        $("#search-list .showDetail").eq(index).trigger("click");
        console.log("hotAreaEvents mousedown!!!!")
    });
};

function addMakerPolygon(plist, index) {
    //addMakerPonit(name,plist,index);
    var pout = [];
    for (var i = 0; i < plist.length; i++) {
        if (isJP) {
            var _pos = new terramap.GeoPoint(plist[i].x * 3600000, plist[i].y * 3600000, tempSpatialReference);
            var LL = _pos.convertToLonLat();
            var LLjp = wgs84togcj02(LL.lon, LL.lat);
            var geocoord = new terramap.GeoPoint(LLjp[0] * 3600000, LLjp[1] * 3600000, tempSpatialReference);
        } else {
            var geocoord = new terramap.GeoPoint(plist[i].x * 3600000, plist[i].y * 3600000, tempSpatialReference);
        }
        ;
        var gp = geocoord.convertTo(G_map.getSpatialReference());
        pout.push(gp);
    }
    //for (var i = 0; i < plist.length; i++) {
    //    var m_poi = new terramap.GeoPoint(plist[i].x * 3600000, plist[i].y * 3600000, tempSpatialReference);
    //    m_poi = m_poi.convertTo(G_map.getSpatialReference());
    //    pout.push(m_poi);
    //}
    var polygon = new terramap.maps.overlay.Polygon(pout, "#ff0000", 1, 5, "#00ffff", 1);
    G_map.addOverlay(polygon);
    //objOverlay.polygon[index] = polygon;
    polygon.events = new terramap.Events(polygon, polygon.div, null, true);
    polygon.events.register("mouseover", polygon, function () {
        polygon.div.style.cursor = "pointer";
        console.log("hotAreaEvents mouseover!!!!")
    });
    polygon.events.register("mousedown", polygon, function () {
        $("#search-list .showDetail").eq(index).trigger("click");
        console.log("hotAreaEvents mousedown!!!!")
    });
};


var highLightFlag = -1;

function selectMakerHighLight(index) {
    if (index < 0) return;
    highLightFlag = index;
    var geoDataList = searchResultTemp.value.datas.geoDataList[index];
    var pointList = geoDataList.geoData[0].pointList;
    var geoType = geoDataList.geoType;
    var pout = [];
    for (var i = 0; i < pointList.length; i++) {
        if (isJP) {
            var _pos = new terramap.GeoPoint(pointList[i].x * 3600000, pointList[i].y * 3600000, tempSpatialReference);
            var LL = _pos.convertToLonLat();
            var LLjp = wgs84togcj02(LL.lon, LL.lat);
            var geocoord = new terramap.GeoPoint(LLjp[0] * 3600000, LLjp[1] * 3600000, tempSpatialReference);
        } else {
            var geocoord = new terramap.GeoPoint(pointList[i].x * 3600000, pointList[i].y * 3600000, tempSpatialReference);
        }
        ;
        var gp = geocoord.convertTo(G_map.getSpatialReference());
        pout.push(gp);
    }
    var p;
    switch (geoType) {
        case 1 :
            break;
        case 2 :
            p = new terramap.maps.overlay.Polyline(pout, "#E84848", 3, 1);
            G_map.addOverlay(p);
            objOverlay.polyline[index] = p;
            break;
        case 4 :
            p = new terramap.maps.overlay.Polygon(pout, "#E84848", 1, 5, "#E84848", 0.8);
            G_map.addOverlay(p);
            objOverlay.polygon[index] = p;
            break;
        default :
            break;
    }
    ;
};

function cancelMakerHighLight(index) {
    if (index < 0) return;
    var geoDataList = searchResultTemp.value.datas.geoDataList[index];
    var geoType = geoDataList.geoType;
    switch (geoType) {
        case 1 :
            break;
        case 2 :
            G_map.removeOverlay(objOverlay.polyline[index]);
            break;
        case 4 :
            G_map.removeOverlay(objOverlay.polygon[index]);
            break;
        default :
            break;
    }
    ;

    //if(index<0) return;
    //var geoDataList = searchResultTemp.value.datas.geoDataList[index];
    //var pointList = geoDataList.geoData[0].pointList;
    //var geoType = geoDataList.geoType;
    //var pout = [];
    //for (var i =0; i<pointList.length; i++) {
    //	var m_poi = new terramap.GeoPoint(pointList[i].x*3600000, pointList[i].y*3600000, "MGIS:4214");
    //	m_poi = m_poi.convertTo(G_map.getSpatialReference());
    //	pout.push(m_poi);
    //};
    //var p;
    //switch (geoType){
    //	case 1 : break;
    //	case 2 :
    //		p = new terramap.maps.overlay.Polyline(pout,  "#00ff00", 3, 1);
    //		G_map.removeOverlay(objOverlay.polyline[index]);
    //		G_map.addOverlay(p);
    //		break;
    //	case 4 :
    //		p = new terramap.maps.overlay.Polygon(pout, "#ff0000", 1, 5, "#00ffff",1);
    //		G_map.removeOverlay(objOverlay.polygon[index]);
    //		G_map.addOverlay(p);
    //		break;
    //	default : break;
    //};
};