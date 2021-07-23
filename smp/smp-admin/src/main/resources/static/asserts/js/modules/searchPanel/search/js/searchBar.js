/**
 * Created by shiyu on 16-1-18.
 */
//
var newJquery;

(function ($) {
    newJquery = $;
})(jQuery);
var publicData = {
    item: "",
    accentItems: "",
    itemsRelatedWord: ""
}


// var audioPlay = function () {
//     $("#audio-img").attr("src", "modules/leftPanel/search/imgs/audio_sound01.gif");
// }

// var audioPause = function () {
//     $("#audio-img").attr("src", "modules/leftPanel/search/imgs/audio_sound0.jpg");
// }

$(document).ready(function () {
    typeIcon();
});



$ = newJquery;
var search_imgs_url = 'assets/modules/searchPanel/search/imgs/';


function searchKey(index) {
    if ($("#card-3").css("display") == "block") {
        $('#search-list a').eq(index).trigger("click");
    }
    ;
};

function keyEnterFun() {
    if ($("#search-button").css("display") == "block") {
        $("#search-button").trigger("click");
    }
    ;
    if ($("#search-route-button").css("display") == "block") {
        $("#search-route-button").trigger("click");
    }
};

function keyBackspaceFun() {
    if ($("#cards-level0 li").eq(0).css("display") == "block") {
        $("#cards-level0 li").eq(0).trigger("click");
    }
    ;
};

function keyPgUpDownFun(direct) {
    if ($("#card-3").css("display") == "block" && direct == "prev") {
        $("#dynamic_pager .prev").trigger("click");
    }
    ;
    if ($("#card-3").css("display") == "block" && direct == "next") {
        $("#dynamic_pager .next").trigger("click");
    }
    ;
};

$(function () {
    //绑定键盘事件 for 火狐
    if (myBrowser == "Firefox") {
        $(document).keydown(function (e) {
            var charCode;
            if (window.event) {
                e = window.event;
                charCode = (e.type == "keypress") ? e.keyCode : 0;
            }
            else {
                charCode = e.which;
            }
            ;
            //console.log(charCode);

            //数字键快捷选取搜索结果
            if (charCode >= 97 && charCode <= 102) {
                searchKey(charCode - 97);
                console.log(charCode - 96);
            }
            ;

            if (charCode >= 49 && charCode <= 54) {
                searchKey(charCode - 49);
                console.log(charCode - 48);
            }
            ;

            switch (charCode) {
                case 13:
                    //$("#search-button").trigger("click");
                    //keyEnterFun();
                    console.log("Enter");
                    break;
                case 33:
                    //var pageNum = $("#dynamic_pager .disabled").eq(0).attr("data-lp");
                    keyPgUpDownFun("prev")
                    console.log("PgUp");
                    break;
                case 34:
                    //var pageNum = $("#dynamic_pager .disabled").eq(0).attr("data-lp");
                    keyPgUpDownFun("next")
                    console.log("PgDn");
                    break;
                //case 8:
                //    keyBackspaceFun();
                //    console.log("Backspace");
                //    break;
                //default:break;
            }
            //if(charCode==33){
            //    var pageNum = $("#dynamic_pager .disabled").eq(0).attr("data-lp");
            //    console.log(pageNum);
            //};
            //if(charCode==34){
            //    var pageNum = $("#dynamic_pager .disabled").eq(0).attr("data-lp");
            //    console.log(pageNum);
            //};
        });
    }
    ;
    if (myBrowser == "Chrome") {
        //绑定键盘事件 for chrome
        //$(document).keydown(function(e){
        //    console.log(e.keyCode);
        //});
        $(document).keydown(function (e) {
            //数字键快捷选取搜索结果
            if (e.keyCode >= 97 && e.keyCode <= 102) {
                searchKey(e.keyCode - 97);
                //console.log(e.keyCode-96);
            }
            ;

            if (e.keyCode >= 49 && e.keyCode <= 54) {
                searchKey(e.keyCode - 49);
                //console.log(e.keyCode-48);
            }
            ;

            switch (e.keyCode) {
                case 13:
                    //keyEnterFun();
                    console.log("Enter");
                    break;
                case 33:
                    //var pageNum = $("#dynamic_pager .disabled").eq(0).attr("data-lp");
                    keyPgUpDownFun("prev")
                    console.log("PgUp");
                    break;
                case 34:
                    //var pageNum = $("#dynamic_pager .disabled").eq(0).attr("data-lp");
                    keyPgUpDownFun("next")
                    console.log("PgDn");
                    break;
                //case 8:
                //    keyBackspaceFun();
                //    console.log("Backspace");
                //    break;
                default:
                    break;
            }
        })
    }
    ;
});

//搜索框检测代码
function checkinput() {
    var search = $('#sole-input');
    var isNull = /^[\s' ']*$/;
    if (search.val() == '请输入关键词...' || search.val().length <= 0 || isNull.test(search.val())) {
        search.focus();
        window.alert("请输入关键词，搜索关键词不能为空");
        return false;
    } else {
        return true;
    }
};

$("#cards-level0 li").eq(0).click(function () {
    $("#cards-level0>li").eq(0).css("display", "none");
    //$("#cards-level0>li").eq(1).css("display","block");
    $("#cards-level1>li").css("display", "none");
    $("#cards-level2").css("display", "block");
    $("#search-list").parent().css("display", "block");
});


//cookie 搜索关键词历史记录
var setSearchHistoryCookies = function (kwd) {
    var history = [];
    var cookies = decodeURI(getCookie("searchHistory"))
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
    setCookie("searchHistory", encodeURI(history.join()), 30);
    //console.log(history);
    //console.log(history.join());
}

function searchType() {
    var typeindex = $("#searchbox .selected").attr("rel");
    var type = ""
    switch (typeindex) {
        case "0":
            type = "";
            break;
        case "1":
            type = "dm";
            break;
        case "2":
            type = "by";
            break;
        case "3":
            type = "wx";
            break;
        case "4":
            type = "bz";
            break;
        default:
            break;
    }
    return type;
}

//搜索按钮事件
$("#search-button").click(function () {
    var check = checkinput();
    if (!check) {
        return false;
    } else {
        $("#cards-level3").css("display", "none");
        $("#search-list").empty();
        //$("#card-0-1 div").empty();
        $("#card-2-1 div").empty();
        $("#cards-level1>li").css("display", "none");
        $("#cards-level0>li").eq(0).css("display", "none");
        $("#cards-level0>li").eq(1).css("display", "none");
        //appendHotWord();
        G_map.clearOverlays();
        //if(G_3dMapView){
        //    G_3dMapView.dataSources.removeAll();
        //}
        var type = searchType();

        var kwd = $("#sole-input").val();
        var bounds = G_map.getFullBounds(); //getBounds();
        //doPlaceNameReq(kwd,bounds);
        var result = searchServer(kwd, type);
        //G_map.showFullExtent();
        setSearchHistoryCookies(kwd);
        appendRelatedWord();
        if (result) {
            var x = $("#search-list>div .showDetail").eq(0).attr("x").split(',');
            var y = $("#search-list>div .showDetail").eq(0).attr("y").split(',');
            var _pos = new terramap.GeoPoint(x[0] * 3600000, y[0] * 3600000, tempSpatialReference);
            var geocoord = _pos.convertTo(G_map.getSpatialReference());
            G_map.setCenter(geocoord, 9);
        }
        //window.open("http://192.168.56.104:9002/pdfjs/web/viewer.html?file="+encodeURIComponent('http://192.168.56.104:9002/pdfviewer/test/JavaScript.pdf?op=OPEN&namenoderpcaddress=192.168.56.104:8020&offset=0'))
    }
    ;
});

// $('#literature-button').click(function () {
//     var urlpdf = G_search_serviceURL_literature_ + encodeURIComponent(G_search_serviceURL_literature);
//     showPdfDiv(urlpdf);
// })

//定位到地图
function dotLocate(_pos) {
    //geoX=x*3600000;
    //geoY=y*3600000;
    var _Level = G_map.getZoomLevel();//当前的级别
    //var _sref = G_map.getSpatialReference();
    //var _pos = new terramap.GeoPoint(geoX, geoY, _sref);//116.3815675,39.9220275
    if (_Level < 10) _Level = 10;//9
    G_map.setCenter(_pos, _Level);
};


//点击搜索列表显示详细信息
$(document).on("click", ".showDetail", function () {

    var xPoints = $(this).attr("x").split(',');
    var yPoints = $(this).attr("y").split(',');
    var x = xPoints[0] * 3600000;
    var y = yPoints[0] * 3600000;
    var index = $(this).attr("index").split(',');
    var rst = searchResultTemp.value.datas.geoDataList[index];
    var type = $(this).attr("geotype").split(',');
    var _sref = G_map.getSpatialReference();
    var geocoord = new terramap.GeoPoint(x, y, tempSpatialReference);
    var _pos = geocoord.convertTo(_sref);
    if (type == "-1") {
        printTool(_pos);
    } else {
        $("#search-item-content ul li").eq(4).css("display", "none");
    }
    ;
    var featureid = $(this).attr("featureid");
    // if (usercookieid != null && usercookieid != "null") {
    //     var pointList = getCollectList("point");
    //     for (var i = 0; i < pointList.length; i++) {
    //         if (pointList[i].collectdesc == featureid) {
    //             $("#search-result-favorite").attr("collect", "true").children(".fa").css("color", "#EFD515");
    //             $("#search-result-favorite").attr("collectid", pointList[i].id);
    //             break;
    //         } else {
    //             $("#search-result-favorite").attr("collect", "false").children(".fa").css("color", "#00aba9");
    //         }
    //     }
    //     $("#search-result-favorite").attr("featureid", featureid)
    // }
    var title = $(this).find(".sl-main").text();
    $(".search-item-title").text(title);
    $(".search-item-title").attr("title", title);
    $(".search-item-title").attr("x", x);
    $(".search-item-title").attr("y", y);
    $("#searchListNo").css('background', 'url("'+ search_imgs_url +'number/red0' + (parseInt(index) % listnum + 1) + '.png")');
    $("#cards-level2").css("display", "none");
    $("#cards-level1>li").css("display", "none");
    //$("#cards-level0>li").eq(1).css("display","none");
    $("#cards-level0>li").eq(0).css("display", "block");
    $("#search-item").parent().css("display", "block");
    dotLocate(_pos);
    //title = '<div style="width:160px; height:38px;overflow: hidden" >' + title + '</div>';
    //G_map.showInfoWindow(_pos,title,true);
    G_map.showInfoWindow(null, null, false);
    // addAudioDetail(1);
    // addVideoDetail(rst);
    addTextDetail(rst);
    // addPicDetail(rst);
    cancelMakerHighLight(highLightFlag);
    selectMakerHighLight(index);
    console.log(searchResultTemp);

});


$(".dropdown-menu .inner li").mouseover(function () {
    var index = $(this).index();
    //alert(index);
    $(this).find(".typeicon").addClass('maptypeicon' + index);
});

$(".dropdown-menu .inner li").mouseout(function () {
    var index = $(this).index();
    $(this).find(".typeicon").removeClass('maptypeicon' + index);
});

$(".searchbox-content .input-clear").click(function () {
    $("#cards-level3").css("display", "none");
    $("#cards-level0>li").css("display", "none");
    $("#cards-level1>li").css("display", "none");
    $("#cards-level2").css("display", "none");
    G_map.clearOverlays();
    //if(G_3dMapView){
    // G_3dMapView.dataSources.removeAll();
    //}
});

//audioPlayer
// $(".audioPanel .fa-times")
//     .click(function () {
//         $(".audioPanel").fadeOut();
//         $("#audioplayer")[0].load();
//     })
//     .mouseover(function () {
//         $(this).css("color", "whitesmoke");
//     })
//     .mouseout(function () {
//         $(this).css("color", "black");
//     });

// $(".videoPanel .fa-times").click(function () {
//     $(".videoPanel").fadeOut();
//     jwplayer("videoForm").remove();
//     //$(".videoPanel video").get(0).pause();
// })
//     .mouseover(function () {
//         $(this).css("color", "whitesmoke");
//     })
//     .mouseout(function () {
//         $(this).css("color", "rgb(159,159,159)");
//     });

//closeLeftPanelContent();

//相关搜索推荐
var appendRelatedWord = function () {
    if(typeof publicData.itemsRelatedWord == 'undefined'){
        publicData.itemsRelatedWord = '';
    }
    for (var i = 0; i < publicData.itemsRelatedWord.length && i < 5; i++) {
        var html = '<a href="#" style="margin-right: 20px;">' + publicData.itemsRelatedWord[i] + '</a>';
        $("#card-2-1 div").append(html);
    }
    ;
};

//推荐词汇搜索功能#card-2-1 a
$("#card-2-1 a").live("click", function () {
    var keyword = $(this).text();
    $("#sole-input").val(keyword);
    $("#search-button").trigger("click");
    //console.log(keyword);
});

$("#sole-input").bind("dblclick", function () {
    console.log("dblclick");
})

$("#sole-input").bind("focus", function () {
    var cookies = decodeURI(getCookie("searchHistory"));
    var itemsHistory = cookies.split(",");
    if ($("#sole-input").val() == "请输入关键词..." || $("#sole-input").val() == "") {
        publicData.item = itemsHistory;
    }
    console.log("focus");
});

$("#sole-input").bind("input", function () {
    searchAll($(this).val());
    console.log("input");
});


//搜索栏提示
$("#sole-input")
    .focus(function () {
        var $this = $(this);
        // '请搜索...'为搜索框默认值
        ($this.val() === '请输入关键词...') ? $this.val('') : null;
    })
    .blur(function () {
        var $this = $(this);
        // '请搜索...'为搜索框默认值
        ($this.val() === '') ? $this.val('请输入关键词...') : null;
    });

$("#sole-input").autocomplete({
    //change : function(event,ui){
    //    console.log("change");
    //    //searchAll($this.val())
    //},
    //delay : 5000,
    source: function (request, response) {
        searchComplete(request, response, publicData.item, publicData.accentItems);
        //console.log("auto");
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

        //var mapstyle = $("html").attr("class").split(" ");
        //if(mapstyle[1] == "vectorMapStyle"){
        //    $ul.css ("background-color", "white");
        //}else if(mapstyle[1] == "photoMapStyle"){
        //    $ul.children(".ui-menu-item").children("a").css ("color", "#ebffff");
        //    $ul.find(".ui-state-focus").css("background", "rgba(58,59,61,1");
        //    $ul.css ("background", "rgba(58,59,61,0.8)");
        //}
        //console.log("mapstyle:"+mapstyle[1]);
        //var $ul = $("#ui-id-1");
        $ul.css("overflow-y", "auto");
        $ul.css("display", "block");
        //$ul.css ("width", "200px");
        $ul.css("height", itemsLength * itemsHeight + "px");//210=8 180=7
        //console.log(itemsLength * itemsHeight + "px");
    },
    select: function (event, ui) {
        $("#sole-input").val(ui.item.value);
        $("#search-button").trigger("click");
    },
    //scroll:true,
    //scrollHeight:120,
    minLength: 0
    //minChars : 0,//触发autocomplete最小字符数
    //autoFill : true,
    //mustMatch : true,
    //width : 220,
    //max : 10

}).focus(function (event) {
    $(this).autocomplete("search", "");
});


function searchAll(kwd) {
    terramap.setServiceCallAckEncoding("utf-8");
    terramap.setServiceCallReqEncoding("utf-8");
    var serviceUrl = terramap.serviceBaseURL;
    terramap.serviceBaseURL = __serviceURL.search;//"http://192.168.56.157:8999/mswss/maps/services"
    //1--prepare params
    var suggests = [];
    var text = kwd;
    var type = searchType();

    //2---get proxy
    var proxy = terramap.getService("mmss.services.gs.search.ISearchService");

    //3---run service buildIndexThrougConn
    var start = new Date();
    var __options = {
        async: true,
        timeout: 300000,
        handleServiceCallResult: doSearchSuccess,
        handleServiceCallError: doSearchFailed
    }
    proxy.getSuggestion(text + "__" + type, suggests, __options);
    terramap.serviceBaseURL = serviceUrl;
    var end = new Date();
    console.log("getSuggestion useTime:" + (end.getTime() - start.getTime()) / 1000.0 + "s :");
};

var doSearchSuccess = function (num, info) {
    if (info.length > 0) {
        console.log(info.length);
        var itemsWords = [];
        for (var i = 0; i < info.length; i++) {
            itemsWords[i] = info[i].text;
            //accentItemsWords[data[i].name]=data[i].pinyin+','+data[i].shortword;
        }
    }
    ;
    publicData.item = itemsWords;
    publicData.itemsRelatedWord = itemsWords;
    $("#sole-input").autocomplete("search", "");
}

var doSearchFailed = function () {
    //alert("searchSever error");
}

// $(document).on("click", "#search-result-favorite", function () {
//     if (loginCheck()) {
//         return;
//     }
//     var collect = $(this).attr("collect");
//     if (collect == "false") {
//         $(this).attr("collect", "true").children(".fa").css("color", "#EFD515");
//         var featureid = $(this).attr("featureid");
//         var datajson = {
//             username: usercookieid,
//             collectdesc: featureid
//         }
//         var sqlid = doCollect("point", datajson);
//         $(this).attr("collectid", sqlid);
//         console.log(sqlid);
//     } else if (collect == "true") {
//         $(this).attr("collect", "false").children(".fa").css("color", "#00aba9");
//         var collectid = $(this).attr("collectid");
//         doDelCollect(collectid);
//     }
// });

// $(document).on("click", "#pointPanel", function () {
//     if (loginCheck()) {
//         return;
//     }
//     closeLeftPanelContent();
//     collectListTemp = [];
//     $('#collect-list').empty();
//     var pointList = getCollectList("point");
//     if (pointList.length == 0) {
//         attendionTip("没有收藏的兴趣点");
//     }
//     else {
//         collectTemp = pointList;
//         G_map.clearOverlays();
//         G_map.showInfoWindow(null, null, false);
//         $("#collect-title").text("兴趣点收藏");
//         for (var i = 0; i < pointList.length; i++) {
//             searchByProperty(i, pointList[i].collectdesc);
//         }
//         showCollectList(1);
//         $("#cards-level3").css("display", "block");
//     }
// })

// $("#cards-level0 li").eq(1).click(function () {
//     $("#cards-level0>li").eq(1).css("display", "none");
//     $("#cards-level1>li").css("display", "none");
//     //$("#pointPanel").trigger("click");

//     $("#cards-level3").css("display", "block");
//     //$("#collect-list").parent().css("display", "block");
// });

// $(document).on("click", ".showPonitCollect", function () {
//     loginCheck();
//     $("#cards-level3").css("display", "none");
//     var xPoints = $(this).attr("x").split(',');
//     var yPoints = $(this).attr("y").split(',');
//     var x = xPoints[0] * 3600000;
//     var y = yPoints[0] * 3600000;
//     var index = $(this).attr("index").split(',');
//     var sqlid = collectTemp[index].id;
//     $("#search-result-favorite").attr("collectid", sqlid).attr("collect", "true").children(".fa").css("color", "#EFD515");
//     var rst = collectListTemp[index].value.datas.geoDataList[0];
//     var type = $(this).attr("geotype").split(',');
//     var _sref = G_map.getSpatialReference();
//     var geocoord = new terramap.GeoPoint(x, y, tempSpatialReference);
//     var _pos = geocoord.convertTo(_sref);
//     if (type == "-1") {
//         printTool(_pos);
//     } else {
//         $("#search-item-content ul li").eq(4).css("display", "none");
//     }
//     ;
//     var featureid = $(this).attr("featureid");
//     $("#search-result-favorite").attr("featureid", featureid)
//     var title = $(this).find(".sl-main").text();
//     $("#searchListNo").css('background', 'url("'+ search_imgs_url +'number/red0' + (parseInt(index) % listnum + 1) + '.png")');
//     $(".search-item-title").text(title);
//     $(".search-item-title").attr("title", title);
//     $(".search-item-title").attr("x", x);
//     $(".search-item-title").attr("y", y);
//     $("#search-item").parent().css("display", "block");
//     $("#cards-level0>li").eq(1).css("display", "block");
//     G_map.setZoomLevel(14);
//     G_map.panTo(_pos);
//     //title = '<div style="width:160px; height:38px;overflow: hidden" >' + title + '</div>';
//     //G_map.showInfoWindow(_pos,title,true);
//     G_map.showInfoWindow(null, null, false);
//     addAudioDetail(1);
//     addVideoDetail(rst);
//     addTextDetail(rst);
//     addPicDetail(rst);
//     cancelMakerHighLight(highLightFlag);
//     selectMakerHighLight(index);
//     console.log(searchResultTemp);

// });


// $("#closeCollect").click(function () {
//     $("#cards-level3").css("display", "none");
// })

