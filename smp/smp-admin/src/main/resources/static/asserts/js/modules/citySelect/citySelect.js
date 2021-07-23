/**
 * ***************************************************************
 * 文件名称：citySelect.js
 * 摘   要：城市选择
 * 作   者：xiabin
 * 创建时间：2018-07-04
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：
 * ****************************************************************
 */

jQuery(document).ready(function () {
    citySelector.cityInit("citySelectorBtn");
});


var citySelector = {};
citySelector.provinces = new Array();
citySelector.all = new Array();
citySelector.hotcity = new Array();
citySelector.other = new Array();
citySelector.municipality = new Array();
citySelector.city = new Array();

citySelector._py = new Array();
citySelector._name = new Array();

citySelector.pycode = new Array("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
citySelector.div_id = "";
citySelector.inputvl = "";


citySelector.cityInit = function (_divid) {
    //$("#"+_divid).append('<a class="place">中国</a>');
    $('#citySelectorBtn').click(function () {
        //初始化ui
        //_closePanelContent();
        if (document.getElementById("placeBoard") == null) {
            citySelector.div_id = _divid;
            //0.构建基本城市定位器二级菜单
            $(".page-container #citySelectorBtn").after('<div id="placeBoard" class="place-board clearfix">' + '<span class="cityarrow"></span>' +
                '<div id="hotCity">' +
                '</div>' +
                '<div id="searchCity" class="col-md-8 input-group">' +
                '<input id="cityInput" type="text" placeholder="请输入城市拼音缩写" class="form-control ui-autocomplete-input font-gray"/>' +
                '<button id="cityInputBtn" type="submit" class="btn lightblue col-md-2 margin-bottom-10 common-btn"><i class="fa fa-search"></i></button></div>' +
                '<div id="cityMain" class="portlet-body">' +
                '<ul class="nav nav-tabs">' +
                '<li class="active">' +
                '<a data-toggle="tab" href="#tabProvince">按省份</a>' +
                '</li>' +
                '<li class="">' +
                '<a data-toggle="tab" href="#tabCity">按城市</a>' +
                '</li>' +
                '</ul>' +
                '<div class="tab-content city-tab-h">' +
                '<div id="tabProvince" class="tab-pane fade active in">' +
                '<div id="tabProvinceList" data-spy="scroll" data-offset="0" class="scrollspy-example">' +
                '</div>' +
                '</div>' +
                '<div id="tabCity" class="tab-pane fade">' +
                '<div id="tabCityPoint" class="collapse navbar-collapse bs-js-navbar-scrollspy">' +
                '</div>' +
                '<div id="tabCityList" data-spy="scroll" data-target="#navbar-example2" data-offset="0" class="scrollspy-example">' +
                '</div>' + '</div>' + '</div>' + '</div>' + '</div>');

            //1.请求城市定位数据
            sendAllCitiesAjax();
            //2.构建热门城市
            build_HotCitys();
            //3.构建省市tab页
            build_Tab1();
            //4.构建拼音tab页
            build_Tab2();
            onc();
        };
        $('#placeBoard').toggle();

        //输入框检测
        // $("#cityInput").focus(function () {
        //     var $this = $(this);
        //     // '请搜索...'为搜索框默认值
        //     ($this.val() != '') ? $this.val('') : null;
        // }).blur(function () {
        //     var $this = $(this);
        //     // '请搜索...'为搜索框默认值
        //     ($this.val() == '') ? $this.val('请输入城市拼音缩写') : null;
        // });
    });
    // $('.place3D').click(function(){
    //     $(".place").trigger("click");
    // });

};


//定位地图
function pointmap(x, y, level) {
    try {
        var height = 0.746 * Math.pow(2, (28 - level));
        G_3dMapView.camera.flyTo({
            destination: Cesium.Cartesian3.fromDegrees(Number(x), Number(y), height)
        });
    }
    catch (e) {
    }
    finally {
        var _sref = G_map.getSpatialReference();
        var pos = new terramap.GeoPoint(x * 3600000, y * 3600000, G_sref);
        //var _pos = new terramap.GeoPoint(418973643, 143719299, _sref);
        pos = pos.convertTo(_sref);
        G_map.setCenter(pos, level);
        $('#placeBoard').toggle();
    }
};

function build_HotCitys() {
    var hotcitys = "<P>热门城市：";
    $.each(citySelector.hotcity,
        function (i, city) {
            hotcitys += '<a class="js-city-name" x="' + city.x + '" y="' + city.y + '" level="' + city.level + '" href="javascript:;"  >' + city.name + '&nbsp</a>';
        });
    hotcitys += "</p>";
    $("#hotCity").append(hotcitys);
};

function build_Tab1() {
    var allProvince = "";
    $.each(citySelector.city, function (i, city) {
        var city_Province = '<a class="js-city-name" x="' + city.x + '" y="' + city.y + '" level="' + city.level + '" href="javascript:;"  >' + city.name + '&nbsp</a>';
        allProvince += city_Province;
    });
    allProvince = '<p class="city-p-height">' + '直辖市、特别行政区：' + allProvince + '</p>';
    $.each(citySelector.provinces,
        function (i, province) {
            var temp_city = "";
            var citys = province.citys;
            $.each(citys, function (i, city) {
                var city_Province = '<a class="js-city-name" x="' + city.x + '" y="' + city.y + '" level="' + city.level + '" href="javascript:;"  >' + city.name + '&nbsp</a>';
                temp_city = temp_city + city_Province;
            });
            allProvince += '<p class="city-p-height">' + province.name + '：' + temp_city + '</p>';
        });
    $("#tabProvinceList").append(allProvince);
};

function build_Tab2() {
    var temp_tabCityPoint = "";
    var all = citySelector.all;
    var temp_tabCityList = "";
    $.each(citySelector.pycode, function (i, pycode) {
        if (pycode != "I" && pycode != "O" && pycode != "U" && pycode != "V") {
            temp_tabCityPoint += '<a href="#city_' + pycode + '">' + pycode + '&nbsp' + ' </a>'
        }
    });
    $.each(citySelector.all, function (i, py_citys) {
        if (py_citys.py != "I" && py_citys.py != "O" && py_citys.py != "U" && py_citys.py != "V") {
            temp_tabCityList += getCitysbyPY(py_citys);
        }
    });
    $("#tabCityPoint").append(temp_tabCityPoint);
    $("#tabCityList").append(temp_tabCityList);
};

function getCitysbyPY(py_citys) {
    var city_Province = "<p id=city_" + py_citys.py + " class='city-p-height'>" + py_citys.py + " :  ";
    $.each(py_citys.citys,
        function (i, city) {
            city_Province += '<a class="js-city-name" x="' + city.x + '" y="' + city.y + '" level="' + city.level + '" href="javascript:;"  >' + city.name + '&nbsp</a>';;
        });
    city_Province += "</p>";
    return city_Province;
};

//获取所有城市数据
function sendAllCitiesAjax() {
    citySelector.provinces = G_cityJson.province;
    citySelector.all = G_cityJson.all;
    citySelector.hotcity = G_cityJson.hotcity;
    citySelector.other = G_cityJson.other;
    citySelector.municipality = G_cityJson.municipality;
    citySelector.city = G_cityJson.city;
};

function onc() {
    $("#hotCity").on("click", ".js-city-name", function (e) {
        e.stopPropagation();
        var new_palce = $(this).html();
        var level = $(this).attr("level");
        var x = $(this).attr("x");
        var y = $(this).attr("y");
        pointmap(x, y, level);
        document.getElementById(citySelector.div_id).getElementsByTagName("A")[0].innerHTML = new_palce;

    });
    $("#tabProvinceList").on("click", ".js-city-name", function (e) {
        e.stopPropagation();
        var new_palce = $(this).html();
        var level = $(this).attr("level");
        var x = $(this).attr("x");
        var y = $(this).attr("y");
        pointmap(x, y, level);
        document.getElementById(citySelector.div_id).getElementsByTagName("A")[0].innerHTML = new_palce;
    });
    $("#tabCityList").on("click", ".js-city-name", function (e) {
        e.stopPropagation();
        var new_palce = $(this).html();
        var level = $(this).attr("level");
        var x = $(this).attr("x");
        var y = $(this).attr("y");
        pointmap(x, y, level);
        document.getElementById(citySelector.div_id).getElementsByTagName("A")[0].innerHTML = new_palce;
    });
};