"use strict";

var KTTreeview = function () {

    var demo4 = function(datainfo) {
        $("#kt_tree_4").jstree({
            "core" : {
                "themes" : {
                    "responsive": false
                }, 
                // so that create works
                "check_callback" : true,
                'data': datainfo
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
            "plugins" : [ "contextmenu", "state", "types" ]
        }).on('select_node.jstree', function (event, data) {
            console.log(data.node);
            console.log(data.node.original.path);
        });
    }


    return {
        //main function to initiate the module
        init: function (datainfo) {
            demo4(datainfo);
        }
    };
}();

/**
 * 遍历JSON对象删除其中的id键值
 */
/*var delIdInJson = function(datainfo) {
    var dataJsonStr = JSON.stringify(datainfo);
    for (var i in datainfo) {
        alert(eval(datainfo[i]).children)
        alert(JSON.stringify(eval(datainfo[i]).children))
        alert(eval(datainfo[i]).children != null)
        if (eval(datainfo[i]).children != null) {
            var child = eval(datainfo[i]).children;
            alert(JSON.stringify(child));
            delIdInJson(eval(child));
            alert(JSON.stringify(child))
        } else {
            delete eval(datainfo[i]).id;
        }
    }
    alert(JSON.stringify(datainfo));
};*/



jQuery(document).ready(function() {

    var datainfo = [
        {
            "path":"D:\\testData2",
            //"a_attr": {"path":"D:\\testData2"},
            "children": [
                {
                    "path":"D:\\testData2\\航海书表",
                    //"a_attr": {"path":"D:\\testData2\\航海书表"},
                    "children": [
                        {
                            "path":"D:\\testData2\\航海书表\\test001",
                            //"a_attr": {"path":"D:\\testData2\\航海书表\\test001"},
                            "children": [
                                {
                                    "path":"D:\\testData2\\航海书表\\test001\\2020-01-01",
                                    //"a_attr": {"path":"D:\\testData2\\航海书表\\test001\\2020-01-01"},
                                    "children": [
                                        {
                                            "path":"D:\\testData2\\航海书表\\test001\\2020-01-01\\TheNewStack_GuideToCloudNativeDevOps.pdf",
                                            //"a_attr": {"path":"D:\\testData2\\航海书表\\test001\\2020-01-01\\TheNewStack_GuideToCloudNativeDevOps.pdf"},
                                            "children": [],
                                            "text": "TheNewStack_GuideToCloudNativeDevOps.pdf",
                                            "parentId": "0",
                                            "tags": []
                                        }
                                    ],
                                    "text": "2020-01-01",
                                    "parentId": "0",
                                    "tags": []
                                }
                            ],
                            "text": "test001",
                            "parentId": "0",
                            "tags": []
                        }
                    ],
                    "text": "航海书表",
                    "parentId": "0",
                    "tags": []
                }
            ],
            "text": "testData2",
            "parentId": "0",
            "tags": []
        }
    ];

    var datainfo1 = [
        {
            "path": "D:\\testData",
            "children": [
                {
                    "path": "D:\\testData\\海图数据",
                    "children": [
                        {
                            "path": "D:\\testData\\海图数据\\航海图1",
                            "children": [
                                {
                                    "path": "D:\\testData\\海图数据\\航海图1\\2020-01-01",
                                    "children": [
                                        {
                                            "path": "D:\\testData\\海图数据\\航海图1\\2020-01-01\\NGD-TestChart.rar",
                                            "children": [],
                                            "text": "NGD-TestChart.rar",
                                            "parentId": "0",
                                            "tags": []
                                        }
                                    ],
                                    "text": "2020-01-01",
                                    "parentId": "0",
                                    "tags": []
                                },
                                {
                                    "path": "D:\\testData\\海图数据\\航海图1\\2020-02-10",
                                    "children": [
                                        {
                                            "path": "D:\\testData\\海图数据\\航海图1\\2020-02-10\\NGD-TestChart.rar",
                                            "children": [],
                                            "text": "NGD-TestChart.rar",
                                            "parentId": "1",
                                            "tags": []
                                        }
                                    ],
                                    "text": "2020-02-10",
                                    "parentId": "0",
                                    "tags": []
                                }
                            ],
                            "text": "航海图1",
                            "parentId": "0",
                            "tags": []
                        }
                    ],
                    "text": "海图数据",
                    "parentId": "0",
                    "tags": []
                }
            ],
            "text": "testData",
            "parentId": "0",
            "tags": []
        }
    ];

    // 1-- 获取数据目录信息
    var datainfotest = [{
        "text": "Parent Node",
        "children": [{
            "text": "Initially selected",
            "state": {
                "selected": true
            }
        }, {
            "text": "Custom Icon",
            "icon": "fa fa-warning kt-font-danger"
        }, {
            "text": "Initially open",
            "icon" : "fa fa-folder kt-font-success",
            "state": {
                "opened": true
            },
            "children": [
                {"text": "Another node", "icon" : "fa fa-file kt-font-waring"}
            ]
        }, {
            "text": "Another Custom Icon",
            "icon": "fa fa-warning kt-font-waring"
        }, {
            "text": "Disabled Node",
            "icon": "fa fa-check kt-font-success",
            "state": {
                "disabled": true
            }
        }, {
            "text": "Sub Nodes",
            "icon": "fa fa-folder kt-font-danger",
            "children": [
                {"text": "Item 1", "icon" : "fa fa-file kt-font-waring"},
                {"text": "Item 2", "icon" : "fa fa-file kt-font-success"},
                {"text": "Item 3", "icon" : "fa fa-file kt-font-default"},
                {"text": "Item 4", "icon" : "fa fa-file kt-font-danger"},
                {"text": "Item 5", "icon" : "fa fa-file kt-font-info"}
            ]
        }]
    },
        "Another Node"
    ];

    // 2-- 树形展示数据目录信息
    KTTreeview.init(datainfo);
});