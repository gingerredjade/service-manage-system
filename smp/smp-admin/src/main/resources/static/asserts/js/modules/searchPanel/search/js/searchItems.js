/**
 * Created by shiyu on 16-1-18.
 */
(function ($) {
    $.fn.searchItems = function (options) {
        var defaults = {
            'type': 'ztt',
            'date': '20150101',
            'time': '153000',
            'srsCode': "MGIS:4214",
            'icon': 'fa-map-marker',
            'geoType': 1,
            'pointList': [],
            'content': '北京',
            'index': 0,
            'phoneNumber': 0,
            'address': "",
            'className': "showDetail",
            'naviPointType': -1,
            'featureID': 0
        };
        var settings = $.extend({}, defaults, options);
        return this.each(function () {
            var icon = search_imgs_url + 'number/red0'+(settings.index%listnum+1)+'.png';
            var i = 0;
            var xPoints = '';
            var yPoints = '';
            if (settings.phoneNumber == "" || settings.phoneNumber == "null" || settings.phoneNumber == null) settings.phoneNumber = parseInt(100000000 * Math.random());
            if (settings.address == "" || settings.address == "null" || settings.address == null) settings.address = "";
            for (i = 0; i < settings.pointList.length; i++) {
                xPoints += settings.pointList[i].x;
                xPoints += ','
                yPoints += settings.pointList[i].y;
                yPoints += ','
            }
            var items = '<div class="mix category_' + settings.type + ' mix_all" style="display: block; opacity: 1; ">'
            items += '<ul class="sl-menu"><li style="background: transparent">'
            items += '<a href="#" class="' + settings.className + '" index="' + settings.index + '" geoType="' + settings.geoType + '" naviPointType="' + settings.naviPointType +'" featureID="' + settings.featureID + '"  x="' + xPoints + '" y="' + yPoints + '" style="text-decoration:none;display:block;margin:10px">';
            items += '<span class="sl-icon MarginTop25" style="width: 27px;height: 27px;background: url('+ icon +') "></span>';
            items += '<div class="sl-content">'
            items += '<h4 class="sl-main">' + settings.content + '</h4>';
            items += '<h5 class="sl-sub">' + '联系电话：' + settings.phoneNumber + '&nbsp&nbsp' + '<br>' + '地址：' + settings.address + '</h5>';
            items += '</div></a></li></ul></div>'
            $(this).append(items);
        })
    };

})(jQuery);


function restoreRouteList(pageNum) {
    var objson;

    var minList = (pageNum - 1) * listnumRoute;
    var maxList = pageNum * listnumRoute;
    $("#collect-list").empty();
    for(var i=0;i<collectRouteTemp.length;i++){
        objson = collectRouteTemp[i];
        if (i >= minList && i < maxList) {
            var items = '<div class=" mix_all" style="display: block; opacity: 1; ">'
            items += '<ul class="sl-menu"><li class="favItem" style="background: transparent;height:140px;">'
            items += '<a href="#" class="showRouteCollect" sqlid="'+collectRouteIDTemp[i]+'" index="' + i + '" sref="' + objson.startPoint.sref +
                '" startPointx="' + objson.startPoint.x + '" startPointy="' + objson.startPoint.y +
                '" endPointx="' + objson.endPoint.x + '"  endPointy="' + objson.endPoint.y +
                '" passPointx="' + objson.passPoint.x + '"  passPointy="' + objson.passPoint.y +
                '" passPoint1x="' + objson.passPoint1.x + '"  passPoint1y="' + objson.passPoint1.y +
                '" avoidPointx="' + objson.avoidPoint.x + '"  avoidPointy="' + objson.avoidPoint.y +
                '" avoidPoint1x="' + objson.avoidPoint1.x + '"  avoidPoint1y="' + objson.avoidPoint1.y +
                '" style="text-decoration:none;display:block;margin-left:10px;width: 80%">';
            items += '<span class="sl-icon MarginTop25 fa fa-map-marker"></span>';
            items += '<div class="sl-content">'
            items += '<h4 class="sl-main" style="">起点：  ' + objson.startPoint.title + '</h4>';
            items += '<h4 class="sl-main" style="">终点：  ' + objson.endPoint.title + '</h4>';
            items += '<h4 class="sl-sub" style="font-size: 15px;margin-top: 10px;">必经点1：  ' + convertNull(objson.passPoint.title) + '</h4>';
            items += '<h4 class="sl-sub" style="font-size: 15px;">必经点2：  ' + convertNull(objson.passPoint1.title) + '</h4>';
            items += '<h4 class="sl-sub" style="font-size: 15px;">回避点1：  ' + convertNull(objson.avoidPoint.title) + '</h4>';
            items += '<h4 class="sl-sub" style="font-size: 15px;">回避点2：  ' + convertNull(objson.avoidPoint1.title) + '</h4>';
            items += '</div></a>';
            items += '<span title="取消收藏" style="position: absolute;right: 20px;top: 50px;" class="sl-icon3  fa fa-times-circle"></span>';
            items += '</li></ul></div>'
            $("#collect-list").append(items);
        }
    }

}

function convertNull(info){
    if(info==null || info==undefined || info==""){
        return "未设置";
    }else {
        return info;
    }
}