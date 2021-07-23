/**
 * ***************************************************************
 * 文件名称：echartsAreaSight.js
 * 摘   要：区域通视echarts图例
 * 作   者：Mr_wang
 * 创建时间：2017-01-17
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：2017-01-17 Mr_wang 创建
 * 			2019-07-05 yanran 重构
 * ****************************************************************
 */

function refreshAreaSight(isBtnRefresh) {

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
                { start: -1, end: -1, label: '观察点', color: 'yellow' },
                { start: 0, end: 23, label: '可见处', color: 'green' },
                { start: 23, end: 68, label: '不可见处', color: 'red' },

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