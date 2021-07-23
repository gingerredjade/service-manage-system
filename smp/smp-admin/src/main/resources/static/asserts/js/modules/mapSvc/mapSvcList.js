/**
 * ***************************************************************
 * 文件名称：mapSvcList.js
 * 摘   要：地图服务列表
 * 作   者：yanran
 * 创建时间：2019-07-02
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：
 * ****************************************************************
 */

jQuery(document).ready(function () {
	mapSvcList();
	getWMTSSvc();
    getWCSSvc();
	mapSvcBtnControl();
	buildTree();
	// changeSvcMap();
});

// 获取服务列表请求的参数
var mapSvcListjson = {
	'c': 'mmss.services.gs.catalog.ICatalog',
	'm': 'searchCatalog',
	't': '',
	'f': 'json',
	'q': '{"searchRequest":{"catalogType":"CATALOG_TYPE_SERVICE","attrSearchInfos":[{"fieldName":"TYPE","matchValue":"gsmaptile","attrSearchType":"ATTRSEARCH_TKYPE_MATCH","attrSearchMatchType":"ATTRSEARCH_MATCH_MUST","gteValue":"","gtValue":"","lteValue":"","ltValue":"","inValues":[]}],"searchInfo":"","featureNum":100,"beginIndex":0}}'
};

var treeJson = [];
// 获取服务列表
function mapSvcList() {
	$.ajax({
		type: 'GET',
		cache: false,
		async:false,
		url: G_mswss_maps_services,
		dataType: 'json',
		data: mapSvcListjson,
		success: function (res) {
			$('#mapSvcPanel ul').empty();
			var iconName = '';
			var itemLi = '';
			const catalog = res.catalog;
			treeJson = [
				{ "id" : "mapServiceTree", "parent" : "#", "text" : "地图服务", 'state' : {
						'opened' : true
					}},
				{ "id" : "OGCServiceTree", "parent" : "#", "text" : "OGC服务" },
				{ "id" : "WMTSServiceTree", "parent" : "OGCServiceTree", "text" : "WMTS服务"},
				{ "id" : "RESTServiceTree", "parent" : "OGCServiceTree", "text" : "REST服务"},
				{ "id" : "WMSServiceTree", "parent" : "OGCServiceTree", "text" : "WMS服务"},
				{ "id" : "WCSServiceTree", "parent" : "OGCServiceTree", "text" : "WCS服务"},
			];
			for (var i in catalog) {
				var element = catalog[i];
				var alias = element.alias;
				var version = element.version;
				var type = element.type;
				switch (type) {
					case 'gsmaptile':
						iconName = 'fa fa-globe';
						break;

					case 'gsvector':
						iconName = 'fa fa-image';
						break;

					default:
						iconName = 'fa fa-globe';
						break;
				}
				var node = {
					"id" : alias, "parent" : "mapServiceTree", "text" : alias+version, 'icon':iconName,'state' : {
						'selected' : false
					}
				};
				treeJson.push(node);
			}
		},
		error: function (xhr, ajaxOptions, thrownError) {
			console.log('获取地图服务列表失败');
		}
	});

}

function buildTree() {
    buildFromLocalStorage();
	$('#mapService').on('changed.jstree',function (e,data) {
			if (G_map) {
				G_map.destroy();
			}
			var i, j, r = [];
			for(i = 0, j = data.selected.length; i < j; i++) {
				r.push(data.instance.get_node(data.selected[i]).id);
                r.push(data.instance.get_node(data.selected[i]).parent);
			};
            if(r[1]=='mapServiceTree'){
                var mapName = r[0];
                loadMap(mapName, null, null, null);
            }else {
                var mapid = r[0];
                loadMapById(mapid);
            }
		}).jstree(
            {'plugins':["wholerow"],
			'core' : {
				'data' : treeJson,
                'themes':{
                    'ellipsis':true
                }

			},
		});
}

function loadMapById(treeid){
        var svc =　JSON.parse(localStorage.getItem(treeid));
        if (typeof (svc.svcname)=='undefined'){
           alert('未从缓存中查找到服务信息');
        }
        var ogctype = svc.svctype,svcname=svc.svcname,url=svc.svcurl,tilematrixset=svc.svctilematrix,sref=svc.sref,version=svc.version;
        loadOGCMap(ogctype,svcname,url,tilematrixset,sref,version);
}

// 地图服务列表的显隐
function mapSvcBtnControl() {
	$('#mapSvcBtn').click(function () {
		$('#mapSvcPanel').toggle();
	});
}

//xiabin 注册服务列表
var wmtsInfo = null;
function getWMTSSvc(){
	$('#wmtslayername').empty();
	$('#wmtstilematrix').empty();
	$('#wmtsurl').blur(function(){
		wmtsInfo=null;
		var url = $('#wmtsurl').val();
		var charset='utf-8';
		try{
			wmtsInfo = new terramap.maps.layer.wmts.ServiceMapInfo(url, {"charset" : charset});
		}catch (e){
			$('#wmtslayername').empty();
			$('#wmtstilematrix').empty();
		}
		buildSvcWMTSLayer();
		buildSvcTileMatrix();
		changeLayerevt();
	});
}

function buildSvcWMTSLayer(){
	$('#wmtslayername').empty();
	console.log(wmtsInfo.layerInfos);
	$.each(wmtsInfo.layerInfos,function(i,layer){
		$('#wmtslayername').append("<option value='"+layer.Identifier+"' id>"+layer.Title+"</option>");
	});
}
function buildSvcTileMatrix(){
	$('#wmtstilematrix').empty();
	$.each(wmtsInfo.layerInfos,function(i,layer){
		if(layer.Identifier == $('#wmtslayername').val()){
			$.each(layer.TileMatrixSetLink,function(i,tilematrixset){
				$('#wmtstilematrix').append("<option value='"+tilematrixset.TileMatrixSet+"'>"+tilematrixset.TileMatrixSet+"</option>");
			});
		}
	});
}
function changeLayerevt(){
	$('#wmtslayername').change(function(){
		buildSvcTileMatrix();
	});
}

var wcsInfo=null;

function getWCSSvc(){
    $('#wcslayer').empty();
    $('#wcsurl').blur(function(){
        wcsInfo=null;
        var url = $('#wcsurl').val();
        var charset='utf-8';
        $('#wcsversion').parent().parent().css('display','block');
        try{
            wcsInfo = new terramap.maps.layer.wcs.WCSMapInfo(url, {"charset" : charset, "version" :  $('#wcsversion').val()});
        }catch (e){
            $('#wcslayer').empty();
            $('#wcsversion').parent().parent().css('display','none');
        }
        buildSvcWCSLayer();
    });
    $('#wcsversion').blur(function(){
        wcsInfo=null;
        var url = $('#wcsurl').val();
        var charset='utf-8';
        try{
            wcsInfo = new terramap.maps.layer.wcs.WCSMapInfo(url, {"charset" : charset, "version" :  $('#wcsversion').val()});
        }catch (e){
            $('#wcslayer').empty();
        }
        buildSvcWCSLayer();
    });

}

function buildSvcWCSLayer(){
    $('#wcslayer').empty();
    console.log(wcsInfo.layerInfos);
    $.each(wcsInfo.layerInfos,function(i,layer){
        $('#wcslayer').append("<option value='"+layer.title+"' id>"+layer.title+"</option>");
    });
}

addSVCToLocalStorage();
function addSVCToLocalStorage(){
	$('#svcsave').click(function(){
		if($('#responsive li.active').val()===1){
            var ogctype = 'wmts',svcname=$('#wmtslayername').val(),url=$('#wmtsurl').val(),tilematrixset=$('#wmtstilematrix').val(),sref=null,version=null;
			loadOGCMap(ogctype,svcname,url,tilematrixset,sref,version);
            saveOGCSvcList(ogctype,svcname,url,tilematrixset,sref,version);
		}
		if($('#responsive li.active').val()===2){
            var ogctype='xyz',svcname='xyzmap',url=$('#xyzurl').val(),tilematrixset=null,sref=$('#xyzsref').val(),version=null;
            loadOGCMap(ogctype,svcname,url,tilematrixset,sref,version);
            saveOGCSvcList(ogctype,svcname,url,tilematrixset,sref,version);
		}
        if($('#responsive li.active').val()===3){
            var ogctype='wcs',svcname=$('#wcslayer').val(),url=$('#wcsurl').val() ,tilematrixset=null,sref=$('#wcssref').val(),version=$('#wcsversion').val();
            loadOGCMap(ogctype,svcname,url,tilematrixset,sref,version);
            saveOGCSvcList(ogctype,svcname,url,tilematrixset,sref,version);
        }
        if($('#responsive li.active').val()===4){
            var ogctype='wms',svcname='',url=$('#wmsurl').val(),tilematrixset=null,sref=$('#wmssref').val(),version=null;
            var ss = new terramap.maps.layer.wms.WMSMapInfo(url,{"charset" : "utf-8"});
            svcname = ss.getServiceInfo().LayerInfo.ProviderName;
            loadOGCMap(ogctype,svcname,url,tilematrixset,sref,version);
            saveOGCSvcList(ogctype,svcname,url,tilematrixset,sref,version);
        }
		$('#mapSvcPanel').toggle();
		$(".btn.default").trigger("click");
	});
}

function saveOGCSvcList(ogctype,svcname,url,tilematrixset,sref,version){
    var data = {
        'svctype' : ogctype,
        'svcname' : svcname,
        'svcurl' : url,
        'svctilematrix' : tilematrixset,
        'sref':sref,
        'version':version
    };
    var Svcdata = JSON.stringify(data);
    var item = new Date();
    localStorage.setItem(item.valueOf(),Svcdata);
    $('#mapService').jstree('destroy');
    treeJson=[];
    mapSvcList();
    buildTree();
}

function buildFromLocalStorage(){
    for(var i=0;i<window.localStorage.length;i++){
        var svc =　JSON.parse(localStorage.getItem(localStorage.key(i)));
        if (typeof (svc.svcname)=='undefined'){
            continue;
        }
        var iconName = 'fa fa-globe';
        var ogctype = svc.svctype;
        var parent = '';
        var alias = localStorage.key(i);
        var text = '';
        if(ogctype == 'wmts'){
            parent = 'WMTSServiceTree';
            text = svc.svctilematrix;
        }
        if(ogctype == 'xyz'){
            parent='RESTServiceTree';
            text = svc.svcurl;
        }
        if(ogctype == 'wcs'){
            parent='WCSServiceTree';
            text = svc.svcname;
        }
        if(ogctype == 'wms'){
            parent='WMSServiceTree';
            text = svc.svcname;
        }
        var node = {
            "id" : alias, "parent" :parent, "text" : text+"<br/>", 'icon':iconName,'state' : {
                'selected' : false
            }
        };
        treeJson.push(node);
    }
}