/**
 * ***************************************************************
 * 文件名称：topoAnalysis.js
 * 摘   要：地形分析服务调用
 * 作   者：Mr_wang
 * 创建时间：2016-12-28
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：2016-12-28 Mr_wang 创建
 * 			2019-07-05 yanran 重构
 * ****************************************************************
 */

var topo_cb_fun = null;
//距离
var topoanalysis_dis_tool = 'topoanalysis_dis_tool';
//面积
var topoanalysis_area_tool = 'topoanalysis_area_tool';
//高程
var topoanalysis_elev_tool = 'topoanalysis_elev_tool';
//挖方填方
var topoanalysis_volumn_tool = 'topoanalysis_volumn_tool';
//两点通视
var topoanalysis_linesight_tool = 'topoanalysis_linesight_tool';
//区域通视
var topoanalysis_areasight_tool = 'topoanalysis_areasight_tool';
//坡度分析
var topoanalysis_slope_tool = 'topoanalysis_slope_tool';
//坡向分析Aspect
var topoanalysis_aspect_tool = 'topoanalysis_aspect_tool';
//断面分析
var topoanalysis_profile_tool = 'topoanalysis_profile_tool';
//越野分析
var topoanalysis_yynavigate_tool = 'topoanalysis_yynavigate_tool';
//拓扑距离分析
var topoanalysis_dis_cmd = 'topoanalysis_dis_cmd';
//面积
var topoanalysis_area_cmd = 'topoanalysis_area_cmd';
//高程
var topoanalysis_elev_cmd = 'topoanalysis_elev_cmd';
//挖方填方
var topoanalysis_volumn_cmd = 'topoanalysis_volumn_cmd';
//两点通视
var topoanalysis_linesight_cmd = 'topoanalysis_linesight_cmd';
//区域通视
var topoanalysis_areasight_cmd = 'topoanalysis_areasight_cmd';
//坡度分析
var topoanalysis_slope_cmd = 'topoanalysis_slope_cmd';
//坡向分析Aspect
var topoanalysis_aspect_cmd = 'topoanalysis_aspect_cmd';
//断面分析
var topoanalysis_profile_cmd = 'topoanalysis_profile_cmd';
//数据显示or隐藏
var bFinishedAnalysis = false;
//越野分析
var topoanalysis_yynavigate_cmd = 'topoanalysis_yynavigate_cmd';

//图片地址
var geoAnalysis_imgs_url = 'assets/modules/geoAnalysis/imgs/';


function topoanaly_init(G_map, evtMg) {
    if (G_map != null) {
        var action = new TopoAnalyToolAction();
        var callbacks = {
            'down': action._onMouseDown,
            'move': action._onMouseMove,
            'up': action._onMouseUp,
            'dblclick': action._onDoubleClick
        };
        var _eventListener = new terramap.maps.listener.MouseEventListener(action, callbacks);
        action.setEventListener(_eventListener);
        G_map.addAction(action);
        registeTopoAnalysisToolCmd(G_map, evtMg);
    }
}


function registeTopoAnalysisToolCmd(G_map, evtMg) {
    if (G_map) {
        if (topo_cb_fun == null) {
            var _ob = {
                map: G_map,
                eMg: evtMg
            }
            topo_cb_fun = (function (ob) {
                return function (commandName, param) {
                    _topo_handleSel(commandName, param, ob);
                }
            })(_ob);
        }
        var cm = G_map.getCmdManager();
        var cmds = ['topoanalysis_dis_cmd', 'topoanalysis_area_cmd', 'topoanalysis_elev_cmd',
            'topoanalysis_volumn_cmd', 'topoanalysis_linesight_cmd', 'topoanalysis_areasight_cmd',
            'topoanalysis_slope_cmd', 'topoanalysis_aspect_cmd', 'topoanalysis_profile_cmd','topoanalysis_yynavigate_cmd'];
        for (var i = 0; i < cmds.length; i++) {
            cm.register(cmds[i], this, topo_cb_fun);
        }
    }
}


function unregisterTopoAnalysisToolCmd(G_map) {
    if (G_map) {
        var cm = G_map.getCmdManager();
        var cmds = ['topoanalysis_dis_cmd', 'topoanalysis_area_cmd', 'topoanalysis_elev_cmd',
            'topoanalysis_volumn_cmd', 'topoanalysis_linesight_cmd', 'topoanalysis_areasight_cmd',
            'topoanalysis_slope_cmd', 'topoanalysis_aspect_cmd', 'topoanalysis_profile_cmd','topoanalysis_yynavigate_cmd'];
        for (var i = 0; i < cmds.length; i++) {
            cm.unregister(terramap.maps.CMD_IDENTIFY, this, topo_cb_fun);
        }
    }
}


var TopoAnalyToolAction = terramap.declare(
    terramap.maps.Action,
    {
        // 关联的工具名
        ASSOCIATED_TOOLS: [
            topoanalysis_dis_tool,
            topoanalysis_area_tool,
            topoanalysis_elev_tool,
            topoanalysis_volumn_tool,
            topoanalysis_linesight_tool,
            topoanalysis_areasight_tool,
            topoanalysis_slope_tool,
            topoanalysis_aspect_tool,
            topoanalysis_profile_tool,
            topoanalysis_yynavigate_tool
        ],

        /**
         * 构造TopoAnalyToolAction对象。
         */
        initialize: function (options) {
            terramap.maps.Action.prototype.initialize.apply(this, arguments);
            terramap.extend(this, options);

            this._toolHandlers = {};

            //距离量算
            this._toolHandlers[topoanalysis_dis_tool] =
                new terramap.maps.selection.Polyline(this, this._handlePolylineQuery);

            //面积量算
            this._toolHandlers[topoanalysis_area_tool] =
                new terramap.maps.selection.Polygon(this, this._handlePolygonQuery);

            //高程量算
            this._toolHandlers[topoanalysis_elev_tool] =
                new terramap.maps.selection.Polygon(this, this._handlePolygonQuery);

            //挖方填方
            this._toolHandlers[topoanalysis_volumn_tool] =
                new terramap.maps.selection.Polygon(this, this._handlePolygonQuery);

            //两点通视，特例

            //区域通视
            this._toolHandlers[topoanalysis_areasight_tool] =
                new terramap.maps.selection.Circle(this, this._handlePolygonQuery);

            //坡向
            this._toolHandlers[topoanalysis_slope_tool] =
                new terramap.maps.selection.Polygon(this, this._handlePolygonQuery);
            //坡度
            this._toolHandlers[topoanalysis_aspect_tool] =
                new terramap.maps.selection.Polygon(this, this._handlePolygonQuery);
            //断面，特例
            //this._toolHandlers[topoanalysis_profile_tool] =
            //new terramap.maps.selection.Polygon(this, this._handlePolygonQuery);
        },

        setMap: function (G_map) {
            terramap.maps.Action.prototype.setMap.apply(this, arguments);

            for (var name in this._toolHandlers) {
                var h = this._toolHandlers[name];
                if (h && h.setMap) {
                    h.setMap(G_map);
                }
            }
        },

        _handleMouseEvent: function (evt, eventName) {
            if (this.map) {
                var tm = this.map.getToolManager();
                var tool = tm.getSelectedTool();
                var h = this._toolHandlers[tool];
                if (h && h[eventName]) {
                    var fn = h[eventName];
                    fn.call(h, evt);
                }
            }
        },

        /**
         * Parameters:
         * evt - {Event}
         */
        _onMouseDown: function (evt) {
            var toolname = this.map.getToolManager().getSelectedTool();
            if (toolname == topoanalysis_linesight_tool) {
                var pixel = new terramap.Point(evt.xy.x, evt.xy.y);
                var poi = this.map.fromPixelToGeoCoord(pixel);
                var marker = addFlag(poi);
                this.map.addOverlay(marker);
                this.map.executeCmd(topoanalysis_linesight_cmd, poi);
            } else if (toolname == topoanalysis_profile_tool) {
                //断面分析
                var pixel = new terramap.Point(evt.xy.x, evt.xy.y);
                var poi = this.map.fromPixelToGeoCoord(pixel);
                var marker = addFlag(poi);
                this.map.addOverlay(marker);
                this.map.executeCmd(topoanalysis_profile_cmd, poi);
            } else if(toolname == topoanalysis_yynavigate_tool ){
                var pixel = new terramap.Point(evt.xy.x, evt.xy.y);
                var poi = this.map.fromPixelToGeoCoord(pixel);
                var marker = addFlag(poi);
                this.map.addOverlay(marker);
                this.map.executeCmd(topoanalysis_yynavigate_cmd, poi);
            }else {
                if (bFinishedAnalysis) {
                    deleteLegendImg();
                    bFinishedAnalysis = false;
                }
                this._handleMouseEvent(evt, 'onMouseDown');
            }
        },

        /**
         * Method: onMouseMove
         */
        _onMouseMove: function (evt) {
            this._handleMouseEvent(evt, 'onMouseMove');
        },

        /**
         * Method: onMouseUp
         */
        _onMouseUp: function (evt) {
            this._handleMouseEvent(evt, 'onMouseUp');
        },

        // 鼠标双击事件。
        _onDoubleClick: function (evt) {
            this._handleMouseEvent(evt, 'onDoubleClick');
            terramap.event.stop(evt);
        },

        // 处理区域通视 查询
        // @param rect {terramap.GeoBounds} 外接矩形。
        _handleCircleQuery: function (rect) {
            //this.map.executeCmd(topoanalysis_areasight_cmd, rect);

        },

        // 处理多边形查询
        // @param geocoords Array{terramap.GeoPoint}，顶点串
        _handlePolygonQuery: function (geocoords) {

            var toolname = this.map.getToolManager().getSelectedTool();
            //面积
            if (toolname == topoanalysis_area_tool)
                this.map.executeCmd(topoanalysis_area_cmd, geocoords);
            //高程量算
            if (toolname == topoanalysis_elev_tool)
                this.map.executeCmd(topoanalysis_elev_cmd, geocoords);

            //挖方填方
            if (toolname == topoanalysis_volumn_tool)
                this.map.executeCmd(topoanalysis_volumn_cmd, geocoords);

            //区域通视
            if (toolname == topoanalysis_areasight_tool)
                this.map.executeCmd(topoanalysis_areasight_cmd, geocoords);

            //坡向
            if (toolname == topoanalysis_slope_tool)
                this.map.executeCmd(topoanalysis_slope_cmd, geocoords);

            //坡度
            if (toolname == topoanalysis_aspect_tool)
                this.map.executeCmd(topoanalysis_aspect_cmd, geocoords);

            //断面
            if (toolname == topoanalysis_profile_tool)
                this.map.executeCmd(topoanalysis_profile_cmd, geocoords);

        },

        // 处理距离量算
        // @param geocoords Array{terramap.GeoPoint}，顶点串
        _handlePolylineQuery: function (geocoords) {
            this.map.executeCmd(topoanalysis_dis_cmd, geocoords);

        },

        deactivate: function () {
            var ret = terramap.maps.Action.prototype.deactivate.apply(this, arguments);

            for (var name in this._toolHandlers) {
                var h = this._toolHandlers[name];
                if (h && h.deactivate) {
                    h.deactivate();
                }
            }

            return ret;
        },

        // 析构函数
        destroy: function () {
            terramap.maps.Action.prototype.destroy.apply(this, arguments);

            for (var name in this._toolHandlers) {
                var h = this._toolHandlers[name];
                if (h && h.destroy) {
                    h.destroy();
                }
            }
            this._toolHandlers = null;
        }
    });

function twoPoiSightParam() {

    // 输入参数，观察点坐标、目标点坐标
    this.anaShape = null;
    //两点通视参数
    this.anaParam = null;

    this.req = null;
}




var lnsightcnt = 0;
var twopoisightparam = null;
var yynavigaterequest = null;
var profilecnt = 0;
var profile_param = null;
function _topo_handleSel(commandName, param, ob) {
    //var G_map = ob.map;
    if (G_map == null)
        return;
    //拓扑分析
    var search;
    if (terramap.runningInDataCenter) {
        var params = {
            'alias': mapTopoSvcName, 'version': mapTopoSvcVersion,
            'svcurl': terramap.serviceBaseURL,
            'svctype': gis3.svcs.gs.common.constants.ServiceTypes.TOPOGANALYSE_SERVICE
        };
        var svcUrl = getSvcReqUrl(params, true);
        search = terramap.getService('mmss.services.gs.tas.ITopographicAnalyse', svcUrl);
    } else {
    	terramap.serviceBaseURL = G_mswss_maps_services;
        search = terramap.getService('mmss.services.gs.tas.ITopographicAnalyse');
    }
    // 用户设定查询的参数
    var req = new mmss.services.gs.tas.TopogAnalyseRequest();
    req.props.GIS3=G_DEMIsGis3Server;
    var emg = ob.eMg;
    var nResolution = G_map.getCellSize().height;
    var serAlias = G_DEMServerName;
    switch (commandName) {
        case topoanalysis_yynavigate_cmd ://越野分析
        	 var p = new gis3.svcs.gs.common.Point();
             p.x = param.x;
             p.y = param.y;
             if (lnsightcnt == 1) {
                lnsightcnt = 0;
                if (yynavigaterequest != null) {
                    yynavigaterequest.endPoint = p;
                }
             } else {
                yynavigaterequest = new mmss.services.gs.yynavigate.YYNavigateRequest();
                 yynavigaterequest.startPoint = p;
                yynavigaterequest.srsCode = param.sref;
                yynavigaterequest.serviceAlias = 'global_dem1016';
                if (emg != null) {
                    // 用户设定请求过虑器
                    emg.fireEvent(EventManager.prototype.EVENTTYPE[11], [yynavigaterequest, commandName]);
                }
                lnsightcnt++;
                return;
             }
            var out1 = {};
            var fn = new standbyFunction();
            fn.text = '正在进行越野分析，请稍候……';
            fn.timeout = 30000;
            fn.fn = function () {
                if (G_map) {
                    G_map.clearOverlays();
                } else {
                    return;
                }
                var IYYnavigateSvc = new mmss.services.gs.yynavigate.IYYnavigateSvc();
                IYYnavigateSvc.doNavigate(yynavigaterequest, out1);
                var sref = G_map.getSpatialReference();
                var rst = out1.value;
                var pCount = rst.pCount;
                var pointSeq = rst.pointSeq;
                if(pCount == 0){
                    var writePoint = G_map.getCenter();
                    var textshow = new terramap.maps.overlay.TextLabel(writePoint,new terramap.Size(200,200),'两点间没有符合条件的道路','black');
                    G_map.addOverlay(textshow);
                }else{
                    var geoPointSeq = new Array();
                    for (var i = 0; i < pCount; i++) {
                        geoPointSeq[i] = new terramap.GeoPoint(pointSeq[2 * i], pointSeq[2 * i + 1], sref);
                    }
                    var polyline = new terramap.maps.overlay.Polyline(geoPointSeq, '#ff1827', 5, 0.8);
                    G_map.addOverlay(polyline);
                }
            }
            fn.excute();
            geoRoam();
            // $('#yyNavigate').css('background-image','url(modules/geoanalysis/img/yynavigate.png)');
            // $('#yyNavigate p').css('background','#018287');
            break;
        case topoanalysis_linesight_cmd ://两点通视
            var p = new gis3.svcs.gs.common.Point();
            p.x = param.x;
            p.y = param.y;

            if (lnsightcnt == 1) {
                lnsightcnt = 0;
                if (twopoisightparam != null) {
                    twopoisightparam.anaShape.pointList.push(p);
                }
            } else {
                twopoisightparam = new twoPoiSightParam();
                twopoisightparam.req = req;
                twopoisightparam.anaShape = new gis3.svcs.gs.common.GeoShape();
                twopoisightparam.anaShape.pointList = new Array();
                //gis3.svcs.gs.common.GeoShape 的pointList为全局静态变量，造成点坐标一直增加
                twopoisightparam.anaShape.geoType = gis3.svcs.gs.common.constants.GeoType.GIS_POLYLINE;
                twopoisightparam.anaShape.pointList.push(p);
                twopoisightparam.anaShape.srsCode = param.sref;
                if (emg != null) {
                    // 用户设定请求过虑器
                    emg.fireEvent(EventManager.prototype.EVENTTYPE[11], [twopoisightparam, commandName]);
                }
                lnsightcnt++;
                return;
            }
            var out1 = {};

            twopoisightparam.req.props.GIS3 = G_DEMIsGis3Server;
            twopoisightparam.req.serviceAlias = serAlias;
            twopoisightparam.req.serviceVersion = G_DEMServerVersion;
            twopoisightparam.anaParam = new mmss.services.gs.tas.P2PSightParam();
            twopoisightparam.req.picWidth = 256;
            twopoisightparam.req.picHeight = 256;
            twopoisightparam.req.dResolutionH = nResolution;
            twopoisightparam.req.dResolutionW = nResolution;
            twopoisightparam.anaParam.observerHeight = 100;
            twopoisightparam.req.bDrawPic = true;
            //两点通视
            var fn = new standbyFunction();
            fn.text = '正在进行两点通视分析，请稍候……';
            fn.timeout = 30000;
            fn.fn = function () {
                 if (G_map) {
                    G_map.clearOverlays();
                 } else {
                    return;
                 }
                var polyline = new terramap.maps.overlay.Polyline(twopoisightparam.anaShape.pointList,'#ff1827',5,1);
                G_map.addOverlay(polyline);
                var posObserve = new terramap.GeoPoint(twopoisightparam.anaShape.pointList[0].x,twopoisightparam.anaShape.pointList[0].y,(twopoisightparam.anaShape.pointList[0].sref||twopoisightparam.anaShape.srsCode));
                posObserve = posObserve.convertTo(G_map.getSpatialReference());
                var iconObserve = new terramap.maps.Icon();
                iconObserve.image = geoAnalysis_imgs_url + 'people.png';
                iconObserve.iconSize = new terramap.Size(36,36);
                iconObserve.iconAnchor = new terramap.Point(18,18);//18.36
                var people = new terramap.maps.overlay.Marker(posObserve, iconObserve/*icon*/, '');
                G_map.addOverlay(people);
                search.getP2PSight(twopoisightparam.req, twopoisightparam.anaShape, twopoisightparam.anaParam, out1);
                var rst = out1.value;
                if(!rst.visible){
                    var posObstacle = new terramap.GeoPoint(rst.obstaclePoint.x,rst.obstaclePoint.y,(twopoisightparam.anaShape.pointList[0].sref||twopoisightparam.anaShape.srsCode));
                    posObstacle = posObstacle.convertTo(G_map.getSpatialReference());
                    var iconObstacle = new terramap.maps.Icon();
                    iconObstacle.image = geoAnalysis_imgs_url + 'high.png';
                    iconObstacle.iconSize = new terramap.Size(36,36);
                    iconObstacle.iconAnchor = new terramap.Point(10,36);
                    var obstacle = new terramap.maps.overlay.Marker(posObstacle, iconObstacle/*icon*/, '');
                    G_map.addOverlay(obstacle);
                    var obstacleInfo = new terramap.maps.overlay.TextLabel(posObstacle,
                        new terramap.Size(120,20) ,
                        (rst.obstaclePoint.z).toFixed(2) + '米');
                    G_map.addOverlay(obstacleInfo);
                }else{
                    var polylinetrue = new terramap.maps.overlay.Polyline(twopoisightparam.anaShape.pointList,'#00ff00',5,1);
                    G_map.addOverlay(polylinetrue);
                }
            }
            fn.excute();
            geoRoam();
	    	// $('#lineSight').css('background-image','url(modules/geoanalysis/img/linesight.png)');
            // $('#lineSight p').css('background','#018287');
            break;

        case topoanalysis_dis_cmd :
            var geoshape = new gis3.svcs.gs.common.GeoShape();
            //由于GeoShape的pointList是全局的变量，需要强制清空
            geoshape.pointList = [];
            geoshape.geoType = gis3.svcs.gs.common.constants.GeoType.GIS_POLYLINE;
            for (var i = 0; i < param.length; i++) {
                var poi = param[i];
                geoshape.srsCode = poi.sref;//poi.sref
                var p = new gis3.svcs.gs.common.Point();
                p.x = poi.x;
                p.y = poi.y;
                geoshape.pointList.push(p);
            }
//            var dis_req = {req: {}};
//            dis_req.req.dResolutionH = nResolution;
//            dis_req.req.dResolutionW = nResolution;
//            dis_req.req.serviceAlias = serAlias;
//            dis_req.req.serviceVersion = G_DEMServerVersion;
//            dis_req.req.bDrawPic = true;
            if (emg != null) {
                // 用户设定请求过虑器
                emg.fireEvent(EventManager.prototype.EVENTTYPE[11], [dis_req, commandName]);
            }
            var out1 = {};
            var dis_request = req;
            dis_request.props.GIS3 = G_DEMIsGis3Server;
            dis_request.dResolutionH = nResolution;
            dis_request.dResolutionW = nResolution;
            dis_request.serviceAlias = serAlias;
            dis_request.serviceVersion = G_DEMServerVersion;
            dis_request.bDrawPic = true;
            //距离量算
            var fn = new standbyFunction();
            fn.text = '正在进行距离量算，请稍候……';
            fn.timeout = 30000;
            fn.fn = function () {
                bFinishedAnalysis = true;
                search.getDistance(dis_request, geoshape, out1);
                var polyline = new terramap.maps.overlay.Polyline(param,'#ff1827',5,0.8);
                G_map.addOverlay(polyline);
                var x = param[param.length-1].x;
                var y = param[param.length-1].y;
                var sref = param[param.length-1].sref;
                var lastpoint =new terramap.GeoPoint(x,y,sref);
                var value = out1.value;
                var textshow = new terramap.maps.overlay.TextLabel(lastpoint,new terramap.Size(120,50),(value.sphereDis / 1000).toFixed(2)+'公里','#ff1827');
                G_map.addOverlay(textshow);
                //'&nbsp;地表距离是 : ' + (value.surfaceDis/1000).toFixed(2) + '公里；</br>' +
                //var text = '球面距离是 : </br>' +'&nbsp&nbsp&nbsp&nbsp'+(value.sphereDis / 1000).toFixed(2) + '公里' + '';
                //'&nbsp;投影距离是 : ' + (value.projectionDis/1000).toFixed(2) + '公里。' ;
            }
            fn.excute();
            geoRoam();
            // $('#distance').css('background-image','url(modules/geoanalysis/img/distance.png)');
            // $('#distance p').css('background','#018287');
            break;

        case topoanalysis_area_cmd :
            var geoshape = new gis3.svcs.gs.common.GeoShape();
            //由于GeoShape的pointList是全局的变量，需要强制清空
            geoshape.pointList = [];
            geoshape.geoType = gis3.svcs.gs.common.constants.GeoType.GIS_POLYGON;
            for (var i = 0; i < param.length; i++) {
                var poi = param[i];
                geoshape.srsCode = poi.sref;//poi.sref
                var p = new gis3.svcs.gs.common.Point();
                p.x = poi.x;
                p.y = poi.y;
                geoshape.pointList.push(p);
            }
            if (geoshape.pointList.length < 3) {
                alert('输入点的数量小于3。');
                return;
            }
            var area_req = {};
            if (emg != null) {
                // 用户设定请求过虑器
                emg.fireEvent(EventManager.prototype.EVENTTYPE[11], [area_req, commandName]);
            }
            var out1 = {};
            //area_req.req.props.GIS3=G_DEMIsGis3Server;
            area_req.req.serviceAlias = serAlias;
            area_req.req.serviceVersion = G_DEMServerVersion;
            area_req.req.dResolutionH = nResolution;
            area_req.req.dResolutionW = nResolution;
            //面积量算
            var fn = new standbyFunction();
            fn.text = '正在进行面积量算，请稍候……';
            fn.timeout = 30000;
            fn.fn = function () {
                search.getArea(area_req.req, geoshape, out1);
                var value = out1.value;
                var text = '地表面积是 : ' + (value.surfaceArea / 1000000).toFixed(2) + '平方公里';
            }
            fn.excute();
            geoRoam();
            break;

        //区域高程量算
        case topoanalysis_elev_cmd :
            var geoshape = new gis3.svcs.gs.common.GeoShape();
            //由于GeoShape的pointList是全局的变量，需要强制清空
            geoshape.pointList = [];
            geoshape.geoType = gis3.svcs.gs.common.constants.GeoType.GIS_POLYGON;
            for (var i = 0; i < param.length; i++) {
                var poi = param[i];
                geoshape.srsCode =poi.sref ;//poi.sref
                var p = new gis3.svcs.gs.common.Point();
                p.x = poi.x;
                p.y = poi.y;
                geoshape.pointList.push(p);
            }
            if (geoshape.pointList.length < 3) {
                alert('输入点的数量小于3。');
                return;
            }
//            var elev_req = {req: {}};
//          elev_req.req.serviceAlias = serAlias;
//          elev_req.req.serviceVersion = G_DEMServerVersion;
//          elev_req.req.bDrawPic = true;
//          elev_req.req.dResolutionH = nResolution;
//          elev_req.req.dResolutionW = nResolution;
            
            if (emg != null) {
                // 用户设定请求过虑器
                emg.fireEvent(EventManager.prototype.EVENTTYPE[11], [elev_req, commandName]);
            }
            var out1 = {};
            
            var elev_request = req;
            elev_request.props.GIS3 = G_DEMIsGis3Server;
            elev_request.serviceAlias = serAlias;
            elev_request.serviceVersion = G_DEMServerVersion;
            elev_request.bDrawPic = true;
            elev_request.dResolutionH = nResolution;
            elev_request.dResolutionW = nResolution;
            
            //高程量算
            var fn = new standbyFunction();
            fn.text = '正在进行高程量算，请稍候……';
            fn.timeout = 30000;
            fn.fn = function () {
                bFinishedAnalysis = true;

                search.getElevation(elev_request, geoshape, out1);
                //param
                var polygon = new terramap.maps.overlay.Polygon(param,'#fff0f0',2,0.6,'#abcdef',0.8);
                G_map.addOverlay(polygon);
                var value = out1.value;
                var sref =  G_map.getSpatialReference();
                var _max = value.maxElevPoint;
                var _min = value.minElevPoint;
                var posgeomax = new terramap.GeoPoint(_max.x, _max.y, sref);
                var posgeomin = new terramap.GeoPoint(_min.x, _min.y, sref);
                var icon = new terramap.maps.Icon();
                icon.image = geoAnalysis_imgs_url + 'high.png';
                icon.iconSize = new terramap.Size(36, 36);
                icon.iconAnchor = new terramap.Point(18, 36);
                var maxP = new terramap.maps.overlay.Marker(posgeomax, icon/*icon*/, '');
                var icon1 = new terramap.maps.Icon();
                icon1.image = geoAnalysis_imgs_url + 'low.png';
                icon1.iconSize = new terramap.Size(36, 36);
                icon1.iconAnchor = new terramap.Point(18, 36);
                var minP = new terramap.maps.overlay.Marker(posgeomin, icon1/*icon*/, '');
                G_map.addOverlay(maxP);
                G_map.addOverlay(minP);
                //geocoord, size,text, fontColor,backgoundColor, opacity, options
                var maxLb = new terramap.maps.overlay.TextLabel(posgeomax,
                    new terramap.Size(120,20) ,
                    (value.maxElevation).toFixed(2) + '米');
                G_map.addOverlay(maxLb);

                var minLb = new terramap.maps.overlay.TextLabel(posgeomin,
                    new terramap.Size(120,20) ,
                    (value.minElevation).toFixed(2) + '米');
                G_map.addOverlay(minLb);
            }
            fn.excute();
            geoRoam();
            // $('#elev').css('background-image','url(assets/image/geoAnalysis/elev.png)');
            // $('#elev p').css('background','#018287');
            break;

        //填挖方量算
        case topoanalysis_volumn_cmd :
            var geoshape = new gis3.svcs.gs.common.GeoShape();
            //由于GeoShape的pointList是全局的变量，需要强制清空
            geoshape.pointList = [];
            geoshape.geoType = gis3.svcs.gs.common.constants.GeoType.GIS_POLYGON;
            for (var i = 0; i < param.length; i++) {
                var poi = param[i];
                geoshape.srsCode = poi.sref;//poi.sref
                var p = new gis3.svcs.gs.common.Point();
                p.x = poi.x;
                p.y = poi.y;
                geoshape.pointList.push(p);
            }
            if (geoshape.pointList.length < 3) {
                alert('输入点的数量小于3。');
                return;
            }
            var vol_param = {};
            vol_param.req = req;
            vol_param.anaShape = geoshape;
            vol_param.anaParam = new gis3.svcs.gs.tas.VolumeParam();
            //vol_param.anaParam.refHeigh
            if (emg != null) {
                // 用户设定请求过虑器
                emg.fireEvent(EventManager.prototype.EVENTTYPE[11], [vol_param, commandName]);
            }
            var out1 = {};
            vol_param.req.serviceAlias = serAlias;
            vol_param.req.serviceVersion = G_DEMServerVersion;
            vol_param.req.props.GIS3=G_DEMIsGis3Server;
            
            //填挖方量算
            var fn = new standbyFunction();
            fn.text = '正在进行挖填方量算，请稍候……';
            fn.timeout = 30000;
            fn.fn = function () {
                search.getVolume(vol_param.req, vol_param.anaShape, vol_param.anaParam, out1);
                var value = out1.value;
                var text = '&nbsp;挖方体积是 : ' + (value.digVolume).toFixed(2) + '立方米；</br>' +
                    '&nbsp;填方体积是 : ' + (value.fillVolume).toFixed(2) + '立方米。';

                var param = new terramap.widget.MessageBoxParam();
                param.height = 130;
                var mbox = new terramap.widget.MessageBox(param);
                mbox.show($('#wrapper')[0], text);
            }
            fn.excute();
            geoRoam();
            break;

        //区域通视
        case topoanalysis_areasight_cmd :
            var geoshape = new gis3.svcs.gs.tas.RingShape();
            geoshape.centerPoint = param.getCenter();
            geoshape.radius = param.width() / 2;
            geoshape.srsCode =param.sref ;//param.sref
            var view_param = {};
            view_param.req = req;
            view_param.anaShape = geoshape;
            view_param.anaParam = new gis3.svcs.gs.tas.ViewSightParam();
            if (emg != null) {
                // 用户设定请求过虑器
                emg.fireEvent(EventManager.prototype.EVENTTYPE[11], [view_param, commandName]);
            }
            var out1 = {};
            view_param.anaParam.observerHeight = 50;
            view_param.req.picWidth = 256;
            view_param.req.picHeight = 256;
            view_param.req.serviceAlias = serAlias;
            view_param.req.serviceVersion = G_DEMServerVersion;
            view_param.req.bDrawPic = true;
            view_param.req.dResolutionH = nResolution;
            view_param.req.dResolutionW = nResolution;
            view_param.req.props.GIS3=G_DEMIsGis3Server;
            //区域通视
            var fn = new standbyFunction();
            fn.text = '正在进行区域通视分析，请稍候……';
            fn.timeout = 30000;
            fn.fn = function () {
                bFinishedAnalysis = true;

                search.getViewSight(view_param.req, view_param.anaShape, view_param.anaParam, out1);
                if (G_map) {
                    G_map.clearOverlays();
                } else {
                    return;
                }
                var rst = out1.value;
                if (rst.picUrl != null) {
                    var a = new terramap.GeoPoint(param.left, param.top, G_map.getSpatialReference());
                    var b = new terramap.GeoPoint(param.right, param.bottom, G_map.getSpatialReference());
                    var topooverlay = new TopoImageOverlay([a, b], rst.picUrl);
                    G_map.addOverlay(topooverlay);
                   /*区域通视图例
                    var legend = new TopoLegendImageOverlay([a, b], rst.legendUrl);
                    G_map.addOverlay(legend);*/
                }
                var chartgraph = document.getElementById('chartGraph');
                chartgraph.style.display = 'inline';
                refreshAreaSight();
            }
            fn.excute();
            geoRoam();
            // $('#areaSight').css('background-image','url(modules/geoanalysis/img/areasight.png)');
            // $('#areaSight p').css('background','#018287');
            break;

        //坡度分析
        case topoanalysis_slope_cmd :
            var geoshape = new gis3.svcs.gs.common.GeoShape();
            //由于GeoShape的pointList是全局的变量，需要强制清空
            geoshape.pointList = [];
            geoshape.geoType = gis3.svcs.gs.common.constants.GeoType.GIS_POLYGON;
            for (var i = 0; i < param.length; i++) {
                var poi = param[i];
                geoshape.srsCode =poi.sref;//poi.sref
                var p = new gis3.svcs.gs.common.Point();
                p.x = poi.x;
                p.y = poi.y;
                geoshape.pointList.push(p);
            }
            if (geoshape.pointList.length < 3) {
                alert('输入点的数量小于3。');
                return;
            }
            var slope_param = {};
            slope_param.req = req;
            slope_param.anaShape = geoshape;
            slope_param.anaParam = new gis3.svcs.gs.tas.SlopeParam();
            slope_param.anaParam.clrLow = 0xFF0000;
            slope_param.anaParam.clrHigh = 0x00FF00;
            if (emg != null) {
                // 用户设定请求过虑器
                emg.fireEvent(EventManager.prototype.EVENTTYPE[11], [slope_param, commandName]);
            }
            var out1 = {};
            slope_param.req.serviceAlias = serAlias;
            slope_param.req.serviceVersion = G_DEMServerVersion;
            slope_param.req.picWidth = 256;
            slope_param.req.picHeight = 256;
            slope_param.req.bDrawPic = true;
            slope_param.req.dResolutionH = nResolution;
            slope_param.req.dResolutionW = nResolution;
            slope_param.req.props.GIS3=G_DEMIsGis3Server;
            //坡度分析
            // alert( slope_param.req.dResolutionH);
            var fn = new standbyFunction();
            fn.text = '正在进行坡度分析，请稍候……';
            fn.timeout = 30000;
            fn.fn = function () {
                bFinishedAnalysis = true;

                search.getSlope(slope_param.req, slope_param.anaShape, slope_param.anaParam, out1);
                if (G_map) {
                    G_map.clearOverlays();
                } else {
                    return;
                }
                var rst = out1.value;
                //rst.url = '/gis3wss/gridmap/images/theme2/nomap256x256.png';
                if (rst.picUrl != null) {
                    var poilist = geoshape.pointList;
                    var minx = Number.MAX_VALUE;
                    var miny = Number.MAX_VALUE;
                    var maxx = Number.MIN_VALUE;
                    var maxy = Number.MIN_VALUE;
                    for (var m = 0; m < poilist.length; m++) {
                        var poi = poilist[m];
                        var minx = Math.min(poi.x, minx);
                        var miny = Math.min(poi.y, miny);
                        var maxx = Math.max(poi.x, maxx);
                        var maxy = Math.max(poi.y, maxy);
                    }
                    var a = new terramap.GeoPoint(minx, maxy, G_map.getSpatialReference());
                    var b = new terramap.GeoPoint(maxx, miny, G_map.getSpatialReference());
                    var topooverlay = new TopoImageOverlay([a, b], rst.picUrl);
                    G_map.addOverlay(topooverlay);
                }
                var chartgraph = document.getElementById('chartGraph');
                chartgraph.style.display = 'inline';
                refreshSlope();
            }
            fn.excute();
            geoRoam();
            // $('#slope').css('background-image','url(modules/geoanalysis/img/slope.png)');
            // $('#slope p').css('background','#018287');
            break;

        //坡向分析
        case topoanalysis_aspect_cmd:
            var geoshape = new gis3.svcs.gs.common.GeoShape();
            //由于GeoShape的pointList是全局的变量，需要强制清空
            geoshape.pointList = [];
            geoshape.geoType = gis3.svcs.gs.common.constants.GeoType.GIS_POLYGON;
            for (var i = 0; i < param.length; i++) {
                var poi = param[i];
                geoshape.srsCode =poi.sref;//poi.sref
                var p = new gis3.svcs.gs.common.Point();
                p.x = poi.x;
                p.y = poi.y;
                geoshape.pointList.push(p);
            }
            if (geoshape.pointList.length < 3) {
                alert('输入点的数量小于3。');
                return;
            }
            var aspect_param = {};
            aspect_param.req = req;
            aspect_param.anaShape = geoshape;
            aspect_param.anaParam = new gis3.svcs.gs.tas.AspectParam();
            if (emg != null) {
                // 用户设定请求过虑器
                emg.fireEvent(EventManager.prototype.EVENTTYPE[11], [aspect_param, commandName]);
            }
            var out1 = {};
            aspect_param.req.serviceAlias = serAlias;
            aspect_param.req.serviceVersion = G_DEMServerVersion;
            aspect_param.req.picWidth = 256;
            aspect_param.req.picHeight = 256;
            aspect_param.req.bDrawPic = true;
            aspect_param.req.dResolutionH = nResolution;
            aspect_param.req.dResolutionW = nResolution;
            aspect_param.req.props.GIS3=G_DEMIsGis3Server;
            //坡向分析
            var fn = new standbyFunction();
            fn.text = '正在进行坡向分析，请稍候……';
            fn.timeout = 30000;
            fn.fn = function () {
                bFinishedAnalysis = true;

                search.getAspect(aspect_param.req, aspect_param.anaShape, aspect_param.anaParam, out1);
                if (G_map) {
                    G_map.clearOverlays();
                } else {
                    return;
                }
                var rst = out1.value;
                if (rst.picUrl != null) {
                    var poilist = geoshape.pointList;
                    var minx = Number.MAX_VALUE;
                    var miny = Number.MAX_VALUE;
                    var maxx = Number.MIN_VALUE;
                    var maxy = Number.MIN_VALUE;
                    for (var m = 0; m < poilist.length; m++) {
                        var poi = poilist[m];
                        var minx = Math.min(poi.x, minx);
                        var miny = Math.min(poi.y, miny);
                        var maxx = Math.max(poi.x, maxx);
                        var maxy = Math.max(poi.y, maxy);
                    }
                    var a = new terramap.GeoPoint(minx, maxy, G_map.getSpatialReference());
                    var b = new terramap.GeoPoint(maxx, miny, G_map.getSpatialReference());
                    var topooverlay = new TopoImageOverlay([a, b], rst.picUrl);
                    G_map.addOverlay(topooverlay);
                    /*图例去除
                    var legend = new TopoLegendImageOverlay([a, b], rst.legendUrl);
                    G_map.addOverlay(legend);*/
                }
                var chartgraph = document.getElementById('chartGraph');
                chartgraph.style.display = 'inline';
                refreshAspect();
            }
            fn.excute();
            geoRoam();
	    	// $('#aspect').css('background-image','url(modules/geoanalysis/img/aspect.png)');
            // $('#aspect p').css('background','#018287');
            break;

        //断面分析
        case topoanalysis_profile_cmd :
            var geoshape = new gis3.svcs.gs.common.GeoShape();
            if (profilecnt == 1) {
                profilecnt = 0;
                if (profile_param != null) {
                    profile_param.anaShape.pointList.push(param);
                }
            } else {
                profile_param = {};
                profile_param.req = req;
                profile_param.anaShape = new gis3.svcs.gs.common.GeoShape();
                profile_param.anaShape.pointList = new Array();
                //gis3.svcs.gs.common. 的pointList为全局静态变量，造成点坐标一直增加
                profile_param.anaShape.geoType = gis3.svcs.gs.common.constants.GeoType.GIS_POLYLINE;
                profile_param.anaShape.pointList.push(param);
                profile_param.anaShape.srsCode =param.sref;//param.sref
                if (emg != null) {
                    // 用户设定请求过虑器
                    emg.fireEvent(EventManager.prototype.EVENTTYPE[11], [profile_param, commandName]);
                }
                profilecnt++;
                return;
            }

            var out1 = {};
            profile_param.req.props.GIS3=G_DEMIsGis3Server;
            profile_param.req.serviceAlias = serAlias;
            profile_param.req.serviceVersion = G_DEMServerVersion;
            profile_param.req.picWidth = 256;
            profile_param.req.picHeight = 256;
            profile_param.req.bDrawPic = true;
            profile_param.req.dResolutionH = nResolution;
            profile_param.req.dResolutionW = nResolution;
            profile_param.anaParm = new mmss.services.gs.tas.ProfileParamForPoints();
            profile_param.anaParm.pointNum = 100;

            //断面分析
            var fn = new standbyFunction();
            fn.text = '正在进行断面分析，请稍候……';
            fn.timeout = 30000;
            fn.fn = function () {
                bFinishedAnalysis = true;
                search.getProfileForPoints(profile_param.req, profile_param.anaShape, profile_param.anaParm,out1);
                if (G_map) {
                    G_map.clearOverlays();
                } else {
                    return;
                }
                var polyline = new terramap.maps.overlay.Polyline(profile_param.anaShape.pointList,'#ff1827',5,1);
                var a = new terramap.GeoPoint();
                G_map.addOverlay(polyline);
                var rst = out1.value.pointSeq;
                var longitude = [];
                var latitude = [];
                var tall = [];
                for (var i = 0;i < rst.length; i++){
                    var a = new terramap.GeoPoint(rst[i].x,rst[i].y,G_map.getSpatialReference());
                    var longandLat = a.convertToLonLat();
                    console.log(longandLat);
                    longitude[i] = (longandLat.lon).toFixed(2);
                    latitude[i] = (longandLat.lat).toFixed(2);
                    tall[i] = rst[i].z;
                }
                var chartgraph = document.getElementById('chartGraph');
                chartgraph.style.display = 'inline';
                refreshProfile(longitude,latitude,tall);
            }
            fn.excute();
            geoRoam();
	    	// $('#profile').css('background-image','url(modules/geoanalysis/img/profile.png)');
            // $('#profile p').css('background','#018287');
            break;
    }
}