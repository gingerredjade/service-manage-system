"use strict";


/**
 * 加载服务列表数据事件
 */
var loadServiceList = function() {

    var $this = $(this);

    // 1-- 获取服务列表数据
    var url = API_URL + "/service/listAll";
    $.ajax({
        type:"GET",
        url:url,
        cache:false,
        success:function(returnStr){
            var datainfo = returnStr.data;
            //alert(datainfo);
            //alert(JSON.stringify(datainfo));

            // 2-- 构造数据目录信息
            var dataToShow = serviceListFunc.serviceInfoStr(datainfo).showStr;

            // 2-- 展示数据目录信息
            $("div#accordion-service-list").empty().append(dataToShow);
        },
        error:function(){
            alert("加载数据失败...");
        }
    });

};




var serviceListFunc = (function(){

    var serviceInfoStr = function(datainfo) {

        var showStr = '';

        for (var i = 0; i < datainfo.length; i++) {
            var dataInfoItem = datainfo[i];

            var showStrItem = '';
            showStrItem += '<div class="card">';
                showStrItem += '<div class="card-header" id="headingTwo'+i+'">';

                    // tile信息
                    showStrItem += '<div class="card-title collapsed" data-toggle="collapse" data-target="#collapseTwo'+i+'" aria-expanded="false" aria-controls="collapseTwo7">';
                        showStrItem += '<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1" class="kt-svg-icon">\n' +
                            '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\n' +
                            '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<polygon points="0 0 24 0 24 24 0 24" />\n' +
                            '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<path d="M12.2928955,6.70710318 C11.9023712,6.31657888 11.9023712,5.68341391 12.2928955,5.29288961 C12.6834198,4.90236532 13.3165848,4.90236532 13.7071091,5.29288961 L19.7071091,11.2928896 C20.085688,11.6714686 20.0989336,12.281055 19.7371564,12.675721 L14.2371564,18.675721 C13.863964,19.08284 13.2313966,19.1103429 12.8242777,18.7371505 C12.4171587,18.3639581 12.3896557,17.7313908 12.7628481,17.3242718 L17.6158645,12.0300721 L12.2928955,6.70710318 Z" fill="#000000" fill-rule="nonzero" />\n' +
                            '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<path d="M3.70710678,15.7071068 C3.31658249,16.0976311 2.68341751,16.0976311 2.29289322,15.7071068 C1.90236893,15.3165825 1.90236893,14.6834175 2.29289322,14.2928932 L8.29289322,8.29289322 C8.67147216,7.91431428 9.28105859,7.90106866 9.67572463,8.26284586 L15.6757246,13.7628459 C16.0828436,14.1360383 16.1103465,14.7686056 15.7371541,15.1757246 C15.3639617,15.5828436 14.7313944,15.6103465 14.3242754,15.2371541 L9.03007575,10.3841378 L3.70710678,15.7071068 Z" fill="#000000" fill-rule="nonzero" opacity="0.3" transform="translate(9.000003, 11.999999) rotate(-270.000000) translate(-9.000003, -11.999999) " />\n' +
                            '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</g>\n' +
                            '\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</svg>';
                        showStrItem += dataInfoItem.svcId;
                        showStrItem += '  丨  ';
                        showStrItem += dataInfoItem.svcName;
                        showStrItem += '  丨  ';
                        showStrItem += dataInfoItem.svcVersion;
                    showStrItem += '</div>';

                    // 点击显示的详细信息
                    showStrItem += '<div id="collapseTwo'+i+'" class="collapse" aria-labelledby="headingTwo'+i+'" data-parent="#accordion-service-list">';
                        showStrItem += '<div class="card-body">';

                            showStrItem +='<div class="row">';
                                showStrItem +='<div class="service-content-item"><label class="service-content-item-label"></label>';
                                var gisSvcStr = '';
                                if(dataInfoItem.isGisSvc===1){
                                  gisSvcStr = '地理信息服务';
                                } else {
                                  gisSvcStr = '非地理信息服务';
                                }
                                showStrItem += gisSvcStr;
                                showStrItem +='</div>';
                            showStrItem +='</div>';

                            // 基础信息
                            showStrItem +='<div class="row">';
                                showStrItem += '<div class="col-lg-12 service-show-group">';

                                    showStrItem +='<div class="service-content-item" style="display: none;"><label class="service-content-item-label"></label>';
                                        showStrItem += dataInfoItem.svcId;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.svcAlias+'"><label class="service-content-item-label">服务别名：</label>';
                                      showStrItem += dataInfoItem.svcAlias;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.svcTypeDO.busSvcSubjectDO.name+'"><label class="service-content-item-label">服务主题：</label>';
                                      var subjectClass = dataInfoItem.svcTypeDO.busSvcSubjectDO.name;
                                      if (!subjectClass) {
                                        subjectClass = '无';
                                      }
                                      showStrItem += subjectClass;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.svcTypeDO.name+'"><label class="service-content-item-label">服务类型：</label>';
                                        showStrItem += dataInfoItem.svcTypeDO.name;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.svcKeyword+'"><label class="service-content-item-label">服务关键字：</label>';
                                        var keyword = dataInfoItem.svcKeyword;
                                        if (!keyword) {
                                          keyword = '无';
                                        }
                                        showStrItem += keyword;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.homePageUrl+'"><label class="service-content-item-label">服务主页地址：</label>';
                                        var homePageUrl = dataInfoItem.homePageUrl;
                                        if (!homePageUrl) {
                                          homePageUrl = "无";
                                        }
                                        showStrItem += homePageUrl;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.interfaceSiteUrl+'"><label class="service-content-item-label">服务接口地址：</label>';
                                        var interfaceSiteUrl = dataInfoItem.interfaceSiteUrl;
                                        if (!interfaceSiteUrl) {
                                          interfaceSiteUrl = "无";
                                        }
                                        showStrItem += interfaceSiteUrl;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.previewUrl+'"><label class="service-content-item-label">服务预览地址：</label>';
                                        var previewUrl = dataInfoItem.previewUrl;
                                        if (!previewUrl) {
                                          previewUrl = "无";
                                        }
                                        showStrItem += previewUrl;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item"><label class="service-content-item-label">服务描述：</label>';
                                        var desc = dataInfoItem.svcDesc;
                                        if (!desc) {
                                          desc = "无";
                                        }
                                        showStrItem += desc;
                                    showStrItem +='</div>';

                                showStrItem += '</div>';

                            showStrItem += '</div>';


                            // 地理信息服务扩展相关信息
                            var gisSvcExtension = '';
                            gisSvcExtension += '<hr />';
                            gisSvcExtension += '<div class="row">';
                                gisSvcExtension += '<div class="col-lg-6 service-show-group">';
                                    gisSvcExtension +='<div class="service-content-item"><label class="service-content-item-label">覆盖范围：</label>';
                                        var coverage = dataInfoItem.svcCoverage;
                                        if (!coverage) {
                                          coverage = '无';
                                        }
                                        gisSvcExtension += coverage;
                                    gisSvcExtension +='</div>';

                                    gisSvcExtension +='<div class="service-content-item"><label class="service-content-item-label">坐标系：</label>';
                                        var coordinateSystem = dataInfoItem.svcCoordinateSystem;
                                        if (!coordinateSystem) {
                                          coordinateSystem = '无';
                                        }
                                        gisSvcExtension += coordinateSystem;
                                    gisSvcExtension +='</div>';

                                    gisSvcExtension +='<div class="service-content-item"><label class="service-content-item-label">投影类型：</label>';
                                        var projection = dataInfoItem.svcProjectionType;
                                        if (!projection) {
                                          projection = '无';
                                        }
                                        gisSvcExtension += projection;
                                    gisSvcExtension +='</div>';
                                gisSvcExtension +='</div>';

                                gisSvcExtension += '<div class="col-lg-6 service-show-group">';
                                    gisSvcExtension +='<div class="service-content-item"><label class="service-content-item-label">更新周期：</label>';
                                        var updateCycle= dataInfoItem.svcUpdateCycle;
                                        if (!updateCycle) {
                                          updateCycle = '无';
                                        }
                                        gisSvcExtension += updateCycle;
                                    gisSvcExtension +='</div>';

                                    gisSvcExtension +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.svcServiceArea+'"><label class="service-content-item-label">服务范围：</label>';
                                        var serviceArea = dataInfoItem.svcServiceArea;
                                        if (!serviceArea) {
                                          serviceArea = '无';
                                        }
                                        gisSvcExtension += serviceArea;
                                    gisSvcExtension +='</div>';


                                gisSvcExtension += '</div>';
                            gisSvcExtension += '</div>';

                            // 地理信息服务图层相关信息
                            gisSvcExtension += '<hr />';
                            gisSvcExtension +='<div class="row">';
                                gisSvcExtension += '<div class="col-lg-6 service-show-group">';
                                    gisSvcExtension +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.layerName+'"><label class="service-content-item-label">图层名称：</label>';
                                        var layerName = dataInfoItem.layerName;
                                        if (!layerName) {
                                            layerName = '无';
                                        }
                                        gisSvcExtension += layerName;
                                    gisSvcExtension +='</div>';

                                    gisSvcExtension +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.layerDesc+'"><label class="service-content-item-label">图层简介：</label>';
                                        var layerDesc = dataInfoItem.layerDesc;
                                        if (!layerDesc) {
                                            layerDesc = '无';
                                        }
                                        gisSvcExtension += layerDesc;
                                    gisSvcExtension +='</div>';

                                    gisSvcExtension +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.layerKeyword+'"><label class="service-content-item-label">图层关键字：</label>';
                                        var layerKeyword = dataInfoItem.layerKeyword;
                                        if (!layerKeyword) {
                                            layerKeyword = '无';
                                        }
                                        gisSvcExtension += layerKeyword;
                                    gisSvcExtension +='</div>';

                                    gisSvcExtension +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.layerCoverage+'"><label class="service-content-item-label">图层覆盖范围：</label>';
                                        var layerCoverage = dataInfoItem.layerCoverage;
                                        if (!layerCoverage) {
                                            layerCoverage = '无';
                                        }
                                        gisSvcExtension += layerCoverage;
                                    gisSvcExtension +='</div>';

                                    gisSvcExtension +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.layerServiceArea+'"><label class="service-content-item-label">图层数据范围：</label>';
                                        var layerServiceArea = dataInfoItem.layerServiceArea;
                                        if (!layerServiceArea) {
                                            layerServiceArea = '无';
                                        }
                                        gisSvcExtension += layerServiceArea;
                                    gisSvcExtension +='</div>';

                                gisSvcExtension += '</div>';
                                gisSvcExtension += '<div class="col-lg-6 service-show-group">';
                                    gisSvcExtension +='<div class="service-content-item"><label class="service-content-item-label">图层坐标系：</label>';
                                        var layerCoordinateSystem = dataInfoItem.layerCoordinateSystem;
                                        if (!layerCoordinateSystem) {
                                            layerCoordinateSystem = '无';
                                        }
                                        gisSvcExtension += layerCoordinateSystem;
                                    gisSvcExtension +='</div>';

                                    gisSvcExtension +='<div class="service-content-item"><label class="service-content-item-label">图层投影类型：</label>';
                                        var layerProjection = dataInfoItem.layerProjectionType;
                                        if (!layerProjection) {
                                            layerProjection = '无';
                                        }
                                        gisSvcExtension += layerProjection;
                                    gisSvcExtension +='</div>';

                                    gisSvcExtension +='<div class="service-content-item"><label class="service-content-item-label">图层更新周期：</label>';
                                        var layerUpdateCycle = dataInfoItem.layerUpdateCycle;
                                        if (!layerUpdateCycle) {
                                            layerUpdateCycle = '无';
                                        }
                                        gisSvcExtension += layerUpdateCycle;
                                    gisSvcExtension +='</div>';

                                    gisSvcExtension +='<div class="service-content-item"><label class="service-content-item-label">图层最新更新时间：</label>';
                                        var layerUpdateTime = dataInfoItem.layerUpdateTime;
                                        if (!layerUpdateTime) {
                                            layerUpdateTime = '无';
                                        }
                                        gisSvcExtension += layerUpdateTime;
                                    gisSvcExtension +='</div>';
                                gisSvcExtension += '</div>';
                            gisSvcExtension +='</div>';


                            // 组织机构相关信息
                            showStrItem += '<hr />';
                            showStrItem +='<div class="row">';
                                showStrItem += '<div class="col-lg-6 service-show-group">';
                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.sysDeptDO.name+'"><label class="service-content-item-label">组织机构名称：</label>';
                                        var deptName = dataInfoItem.sysDeptDO.name;
                                        if(!deptName) {
                                          deptName = '无';
                                        }
                                        showStrItem += deptName;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.sysDeptDO.address+'"><label class="service-content-item-label">组织机构地址：</label>';
                                        var deptAddress = dataInfoItem.sysDeptDO.address;
                                        if(!deptAddress) {
                                          deptAddress = '无';
                                        }
                                        showStrItem += deptAddress;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.sysDeptDO.website+'"><label class="service-content-item-label">组织机构网站：</label>';
                                        var website = dataInfoItem.sysDeptDO.website;
                                        if (!website) {
                                          website = '无';
                                        }
                                        showStrItem += website;
                                    showStrItem +='</div>';
                                showStrItem += '</div>';
                                showStrItem += '<div class="col-lg-6 service-show-group">';
                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.sysDeptDO.phoneNumber+'"><label class="service-content-item-label">组织机构联系人：</label>';
                                        var deptContact = dataInfoItem.sysDeptDO.contact;
                                        if (!deptContact) {
                                          deptContact = '无';
                                        }
                                        showStrItem += deptContact;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.sysDeptDO.phoneNumber+'"><label class="service-content-item-label">组织机构联系电话：</label>';
                                        var deptPN = dataInfoItem.sysDeptDO.phoneNumber;
                                        if (!deptPN) {
                                          deptPN = '无';
                                        }
                                        showStrItem += deptPN;
                                    showStrItem +='</div>';

                                    showStrItem +='<div class="service-content-item text-ellipsis" title="'+dataInfoItem.sysDeptDO.email+'"><label class="service-content-item-label">组织机构联系邮箱：</label>';
                                        var deptEmail = dataInfoItem.sysDeptDO.email;
                                        if (!deptEmail) {
                                          deptEmail = '无';
                                        }
                                        showStrItem += deptEmail;
                                    showStrItem +='</div>';

                                showStrItem += '</div>';
                            showStrItem +='</div>';

                            // 地理信息服务则展示服务扩展及图层信息
                            if(dataInfoItem.isGisSvc===1) {
                              showStrItem += gisSvcExtension;
                            }

                        showStrItem += '</div>';
                    showStrItem += '</div>';
                showStrItem += '</div>';
            showStrItem += '</div>';

            showStr += showStrItem;
        }



        return {
            showStr : showStr
        }

    };


    return {
        serviceInfoStr : serviceInfoStr
    }

})();


/**
 * item点击展开收起控制
 *  Deprecate!
 */
/*$("div#accordion-service-list").delegate("div.card","click", function(){
    $(this).each(function(){
        alert($(this).text())
        var $this = $(this);
        $this.find("div.card-title").removeClass("collapsed");
        $this.siblings("div.card").find("div.card-title").addClass("collapsed");

        $this.find("div#collapseTwo7").addClass("show");
        $this.siblings("div.card").find("div#collapseTwo7").removeClass("show");
    });
});*/


jQuery(document).ready(function() {
    loadServiceList();
});




