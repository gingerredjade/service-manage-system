"use strict";

/**
 * 加载数据事件
 */
$("button#load-data").click(function () {
    var $this = $(this);

    // 1-- 获取指定目录下的数据
    var url = "http://localhost:8888/catalog/loadData";
    $.ajax({
        type:"GET",
        url:url,
        cache:false,
        success:function(returnStr){
            var datainfo = returnStr.data;

            // 2-- 树形展示数据目录信息
            KTTreeview.init(datainfo);
        },
        error:function(){
            alert("加载数据失败...");
        }
    });
});


var KTTreeview = function () {

    var demo4 = function(datainfo) {
        $("#kt_tree_4").jstree({
            "core" : {
                "themes" : {
                    "responsive": false
                }, 
                // so that create works
                "check_callback" : true,
                'data': datainfo,
                'href': datainfo.path
            },
            "types" : {
                "default" : {
                    "icon" : "fa fa-folder kt-font-brand"
                },
                "file" : {
                    "icon" : "fa fa-file  kt-font-brand"
                }
            },
            "state" : { "key" : "demo2" },
            "plugins" : [ "contextmenu", "state", "types", "search" ],
            "contextmenu": {
                "items": {
                    "create":null,
                    "rename":null,
                    "remove":null,
                    "ccp":null,
                    "浏览":{
                        "label":"浏览",
                        "action":function(data){
                            var inst = jQuery.jstree.reference(data.reference),
                                obj = inst.get_node(data.reference);
                            console.log(obj);

                            var dPath = obj.original.path;

                            var url = "http://localhost:8888/data/view?filePath="+dPath;
                            window.open(encodeURI(url),
                                'target', '');
                        }
                    },
                    "下载":{
                        "label":"下载",
                        "action":function(data){
                            var inst = jQuery.jstree.reference(data.reference),
                                obj = inst.get_node(data.reference);
                            // TODO..  增加下载处理。。。

                            /*if(confirm("确定要下载？")){
                                jQuery.get("/accountmanage/deleteMenu?id="+obj.id,function(dat){
                                    if(dat == 1){
                                        alert("下载成功！");
                                        jQuery("#"+treeid).jstree("refresh");
                                    }else{
                                        alert("下载失败！");
                                    }
                                });
                            }*/

                        }
                    }
                }
            }
        });
    }


    return {
        //main function to initiate the module
        init: function (datainfo) {
            demo4(datainfo);
        }
    };
}();


jQuery(document).ready(function() {
    //KTTreeview.init(datainfo);
});
