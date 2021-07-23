
/**
 * 服务主题画像
 *  **/
export const subjectOption = {
  title: {
    text: '服务主题画像',
    subtext: '主题对应的服务数量',
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  legend: {
    left: 'center',
    top: 'bottom',
    data:  ["test1", "test2"]
  },
  toolbox: {
    show: true,
    feature: {
      mark: {show: true},
      dataView: {show: true, readOnly: false},
      magicType: {
        show: true,
        type: ['pie', 'funnel']
      },
      restore: {show: true},
      saveAsImage: {show: true}
    }
  },
  series: [
    {
      name: '服务数量',
      type: 'pie',
      radius: [30, 110],
      center: ['50%', '50%'],
      roseType: 'area',
      data:  [{"name":"test1","value":19},{"name":"test2","value":8}]
    }
  ]
}

/**
 * 服务类型画像
 * **/
export const typeOption = {
  title: {
    text: '服务类型画像',
      subtext: '类型对应的服务数量',
      left: 'center'
  },
  tooltip: {
    trigger: 'item',
      formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
      x: 'right',
      y: 'center',
      data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
  },
  toolbox: {
    show: true,
      feature: {
      mark: {show: true},
      dataView: {show: true, readOnly: false},
      magicType: {
        show: true,
          type: ['pie', 'funnel']
      },
      restore: {show: true},
      saveAsImage: {show: true}
    }
  },
  series: [
    {
      name: '服务数量',
      type: 'pie',
      radius: '55%',
      center: ['50%', '60%'],
      data: [
        {value: 335, name: '直接访问'},
        {value: 310, name: '邮件营销'},
        {value: 234, name: '联盟广告'},
        {value: 135, name: '视频广告'},
        {value: 1548, name: '搜索引擎'}
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}

/**
 * 地理信息/非 画像
 * **/
export const gisOption = {
  title: {
    text: '地理信息 | 非地理信息 画像',
      subtext: '对应的服务数量',
      left: 'center'
  },
  tooltip: {
    trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
  },
  toolbox: {
    show: true,
      feature: {
      mark: {show: true},
      dataView: {show: true, readOnly: false},
      magicType: {
        show: true,
          type: ['pie', 'funnel']
      },
      restore: {show: true},
      saveAsImage: {show: true}
    }
  },
  legend: {
    orient: 'vertical',
      x: 'right',
      y: 'center',
      data: ['地理信息服务', '非地理信息服务']
  },
  series: [
    {
      name: '服务数量',
      type: 'pie',
      radius: ['50%', '70%'],
      avoidLabelOverlap: false,
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: '30',
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
        {value: 335, name: '地理信息服务'},
        {value: 310, name: '非地理信息服务'}
      ]
    }
  ]
}

/**
 * 服务->适用终端 画像
 * **/
export const terminalOption = {
  title: {
    text: '服务终端画像',
      subtext: '服务适用的终端类型统计',
      left: 'center'
  },
  tooltip: {
    trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
  },
  toolbox: {
    show: true,
      feature: {
      mark: {show: true},
      dataView: {show: true, readOnly: false},
      magicType: {
        show: true,
          type: ['pie', 'funnel']
      },
      restore: {show: true},
      saveAsImage: {show: true}
    }
  },
  legend: {
    orient: 'vertical',
      x: 'right',
      y: 'center',
      data: ['通用', 'Web端', '桌面端']
  },
  series: [
    {
      name: '服务数量',
      type: 'pie',
      radius: ['50%', '70%'],
      avoidLabelOverlap: false,
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: '30',
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
        {value: 335, name: '通用'},
        {value: 335, name: 'Web端'},
        {value: 310, name: '桌面端'}
      ]
    }
  ]
}

/**
 * 服务->机构 画像
 * **/
export const deptOption = {
  title: {
    text: '服务机构画像',
      subtext: '机构对应的服务数量',
      left: 'center'
  },
  color: ['#3398DB'],
    tooltip: {
    trigger: 'axis',
      axisPointer: {          // 坐标轴指示器，坐标轴触发有效
      type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    }
  },
  grid: {
    left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
  },
  xAxis: [
    {
      type: 'category',
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      axisTick: {
        alignWithLabel: true
      }
    }
  ],
    yAxis: [
    {
      type: 'value'
    }
  ],
    toolbox: {
    show: true,
      feature: {
      mark: {show: true},
      dataView: {show: true, readOnly: false},
      magicType: {
        show: true,
          type: ['pie', 'funnel']
      },
      restore: {show: true},
      saveAsImage: {show: true}
    }
  },
  series: [
    {
      name: '服务数量',
      type: 'bar',
      barWidth: '60%',
      data: [10, 52, 200, 334, 390, 330, 220]
    }
  ]
}

export default {
  subjectOption,
  typeOption,
  gisOption,
  terminalOption,
  deptOption,
}
