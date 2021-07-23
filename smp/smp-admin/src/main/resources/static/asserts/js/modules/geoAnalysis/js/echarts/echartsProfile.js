/**
 * ***************************************************************
 * 文件名称：echartsProfile.js
 * 摘   要：断面分析echarts图例
 * 作   者：Mr_wang
 * 创建时间：2017-01-03
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：2017-01-03 Mr_wang 创建
 * 			2019-07-05 yanran 重构
 * ****************************************************************
 */

function refreshProfile(longitude, latitude, tall, isBtnRefresh) {
    if (isBtnRefresh) {
        needRefresh = true;
        focusGraphic();
        return;
    }

    needRefresh = false;
    if (myChart && myChart.dispose) {
        myChart.dispose();
    }
    myChart = echarts.init(domMain, 'macarons');
    window.onresize = myChart.resize;
    var option = {
        title: {
            text: '断面分析折线图',
        },
        tooltip: {//鼠标浮动文字
            trigger: 'axis',
            formatter: function (params, ticket, callback) {
                // alert(params[0]
                console.log(params);
                var longsub = longitude[0] - longitude[longitude.length - 1];
                var latsub = latitude[0] - latitude[latitude.length - 1];
                var posfirst = [];
                posfirst[0] = longitude[0];
                posfirst[1] = latitude[0];
                var k = latsub / longsub;
                var b = latitude[0] - k * longitude[0];
                var longnow;
                var latnow;
                if (Math.abs(longsub) > Math.abs(latsub)) {
                    longnow = params[0].name;
                    latnow = (k * longnow + b).toFixed(2);
                } else {
                    latnow = params[0].name;
                    longnow = ((latnow - b) / k).toFixed(2);
                }
                var res = '经度:' + longnow + '<br>' +
                    '纬度:' + latnow + '<br>' +
                    '高度:' + params[0].data;
                setTimeout(function () {
                    callback(ticket, res);
                }, 1000);
                return 'loading';

            },
        },
        toolbox: {
            show: true,
            feature: {
                mark: {
                    show: true,
                },
                dataView: { show: true, readOnly: false },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        },
        dataZoom: {
            show: true,
            realtime: true,
            backgroundColor: 'rgba(221,160,221,0.5)',
            dataBackgroundColor: 'rgba(138,43,226,0.5)',
            fillerColor: 'rgba(38,143,26,0.6)',
            handleColor: 'rgba(128,43,16,0.8)',
        },
        legend: {
            show: false,
            data: ['series1']
        },
        grid: {
            y2: 80
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                splitNumber: 5,
                data: function () {
                    var list = [];
                    var n = 0;
                    var longsub = longitude[0] - longitude[longitude.length - 1];
                    var latsub = latitude[0] - latitude[latitude.length - 1];
                    list = (Math.abs(longsub) > Math.abs(latsub)) ? longitude : latitude;
                    return list;
                }()
            },
        ],
        yAxis: [
            {
                type: 'value',

            }
        ],
        series: [
            {
                name: 'series1',
                type: 'line',
                showAllSymbol: true,
                symbolSize: function (value) {
                    return 2;
                },
                data: function () {
                    var list = [];
                    var n = 0;
                    while (n < tall.length) {
                        list.push(tall[n]);
                        n++;
                    }
                    return list;
                }()
            },
        ]
    };
    /*var ecConfig = require('config');
    function eConsole(param) {
        alert('bangbangbang');
        console.log(param);
    }
    myChart.on(ecConfig.EVENT.CLICK, eConsole);*/
    /*myChart.on(ecConfig.EVENT.DBLCLICK, eConsole);
     myChart.on(ecConfig.EVENT.HOVER, eConsole);*/
    myChart.setOption(option, true);

}
