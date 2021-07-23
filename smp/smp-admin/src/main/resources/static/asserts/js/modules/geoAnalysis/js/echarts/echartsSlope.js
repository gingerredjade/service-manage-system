/**
 * ***************************************************************
 * 文件名称：echartsSlope.js
 * 摘   要：坡度分析echarts图例
 * 作   者：Mr_wang
 * 创建时间：2017-01-17
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：2017-01-17 Mr_wang 创建
 * 			2019-07-05 yanran 重构
 * ****************************************************************
 */

function refreshSlope(isBtnRefresh) {

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
        dataRange: {
            //orient: 'vertical',      // 'vertical'
            x: 'left',
            y: 'bottom',
            color: ['red', 'pink'],    //颜色
            splitList: [
                { start: 0, end: 2, label: '0-2', color: '#ff0000' },
                { start: 2, end: 5, label: '2-5', color: '#e71700' },
                { start: 5, end: 8, label: '5-8', color: '#d02e00' },
                { start: 8, end: 10, label: '8-10', color: '#b94500' },
                { start: 10, end: 12, label: '10-12', color: '#a25c00' },
                { start: 12, end: 14, label: '12-14', color: '#8b7300' },
                { start: 14, end: 16, label: '14-16', color: '#738b00' },
                { start: 16, end: 18, label: '16-18', color: '#5ca200' },
                { start: 18, end: 21, label: '18-21', color: '#45b900' },
                { start: 21, end: 25, label: '21-25', color: '#2ed000' },
                { start: 25, end: 30, label: '25-30', color: '#17e700' },
                { start: 30, end: 90, label: '30-90', color: '#00ff00' },
            ]
        },
        series: [
            {
                mapLocation: { x: 'center', y: 'center', width: '0', height: '0' },
                name: '',
                type: 'map',
                data: []
            }
        ]
    };

    myChart.setOption(option, true);

}
