/**
 * ***************************************************************
 * 文件名称：echartsAspect.js
 * 摘   要：坡向分析echarts图例
 * 作   者：Mr_wang
 * 创建时间：2017-01-03
 * 版权所有：华北计算技术研究所(NCI) 地理信息与图形图像技术研究中心
 * 开发记录：2017-01-03 Mr_wang 创建
 * 			2019-07-05 yanran 重构
 * ****************************************************************
 */

function refreshAspect(isBtnRefresh) {

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
                { start: -1, end: -1, label: '-1--1(平地)', color: '#b0b0b0' },
                { start: 0, end: 23, label: '0-23(正北)', color: '#ff0000' },
                { start: 23, end: 68, label: '23-68(东北)', color: '#ffa600' },
                { start: 68, end: 113, label: '68-113（正东）', color: '#ffff00' },
                { start: 113, end: 158, label: '113-158（东南）', color: '#00ff00' },
                { start: 158, end: 203, label: '158-203（正南）', color: '#00ffff' },
                { start: 203, end: 248, label: '203-248（西南）', color: '#00a6ff' },
                { start: 248, end: 293, label: '248-293（正西）', color: '#0000ff' },
                { start: 293, end: 338, label: '293-338(西北)', color: '#ff00ff' },
                { start: 338, end: 360, label: '338-360(正北)', color: '#ff0000' },
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