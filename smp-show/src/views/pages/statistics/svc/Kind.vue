<template>
  <div class="page-container">
    <div class="kt-portlet">
      <div class="kt-portlet__body kt-portlet__body--fit">
        <div class="row" style="margin-top: 15px;">
          <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
            <div id="svcSubjectCharts" ref="svcSubjectCharts" style="height: 400px; padding: 10px;"></div>
          </div>
          <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
            <div id="svcTypeCharts" ref="svcTypeCharts" style="height: 400px; padding: 10px;"></div>
          </div>
        </div>

        <div class="row" style="height: 100%; margin-top: 40px;">
          <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
            <div id="terminalCharts" ref="terminalCharts" style="height: 400px; padding: 10px;"></div>
          </div>
          <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
            <div id="gisSvcYoNCharts" ref="gisSvcYoNCharts" style="height: 400px; padding: 10px;"></div>
          </div>
        </div>

        <div class="row" style="height: 100%; margin-top: 30px;">
          <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
            <div id="deptCharts" ref="deptCharts" style="height: 400px; padding: 10px;"></div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
  import { SET_BREADCRUMB } from '@/store/breadcrumbs.module';
  import echarts from 'echarts';
  import { subjectOption, typeOption, gisOption, terminalOption, deptOption } from '@/utils/echarts/async-chart-options';

  export default {
    name: "Kind",
    components: {
    },
    mounted () {
      this.$store.dispatch(SET_BREADCRUMB, [
        { title: "统计分析", route: "kind" },
        { title: "服务种类统计"}
      ]);

      // 加载统计数据
      this.countBySubject();
      this.countByType();
      this.countByDept();
      this.countByGisOrNo();
      this.countByTerminal();
    },
    data() {
      return {
        size: 'small'
      }
    },
    methods: {
      // 根据服务主题统计
      countBySubject: function () {
        let that = this;

        let subjectCharts = echarts.init(this.$refs.svcSubjectCharts, 'light');
        subjectCharts.setOption(subjectOption);

        this.$api.stat.countBySubject().then( (res) => {
          subjectCharts.hideLoading();
          subjectCharts.setOption({
            series: [{
              data: res.data.data.detail
            }],
            legend: {
              data: res.data.data.keys
            },
            toolbox: {
              show: true,
              feature: {
                myUpdateTool: {
                  show: true,
                  title: '刷新',
                  //icon: 'path://M525.4 721.2H330.9c-9 0-18.5-7.7-18.5-18.1V311c0-9 9.3-18.1 18.5-18.1h336.6c9.3 0 18.5 9.1 18.5 18.1v232.7c0 6 8.8 12.1 15 12.1 6.2 0 15-6 15-12.1V311c0-25.6-25.3-48.9-50.1-48.9h-335c-26.2 0-50.1 23.3-50.1 48.9v389.1c0 36.3 20 51.5 50.1 51.5h197.6c6.2 0 9.3-7.5 9.3-15.1 0-6-6.2-15.3-12.4-15.3zM378.8 580.6c-6.2 0-12.3 8.6-12.3 14.6s6.2 14.6 12.3 14.6h141.4c6.2 0 12.3-5.8 12.3-13.4 0.3-9.5-6.2-15.9-12.3-15.9H378.8z m251.6-91.2c0-6-6.2-14.6-12.3-14.6H375.7c-6.2 0-12.4 8.6-12.4 14.6s6.2 14.6 12.4 14.6h240.8c6.2 0.1 13.9-8.5 13.9-14.6z m-9.2-120.5H378.8c-6.2 0-12.3 8.6-12.3 14.6s6.2 14.6 12.3 14.6h240.8c7.7 0 13.9-8.6 13.9-14.6s-6.2-14.6-12.3-14.6z m119.4 376.6L709 714.1c9.2-12 14.6-27 14.6-43.2 0-39.4-32.1-71.4-71.8-71.4-39.7 0-71.8 32-71.8 71.4s32.1 71.4 71.8 71.4c16.3 0 31.3-5.4 43.4-14.5l31.6 31.5c3.8 3.8 10 3.8 13.8 0 3.8-3.8 3.8-10 0-13.8z m-88.8-23.6c-28.3 0-51.3-22.8-51.3-51s23-51 51.3-51c28.3 0 51.3 22.8 51.3 51s-23 51-51.3 51z',
                  icon: 'path://M8.43296491,7.17429118 L9.40782327,7.85689436 C9.49616631,7.91875282 9.56214077,8.00751728 9.5959027,8.10994332 C9.68235021,8.37220548 9.53982427,8.65489052 9.27756211,8.74133803 L5.89079566,9.85769242 C5.84469033,9.87288977 5.79661753,9.8812917 5.74809064,9.88263369 C5.4720538,9.8902674 5.24209339,9.67268366 5.23445968,9.39664682 L5.13610134,5.83998177 C5.13313425,5.73269078 5.16477113,5.62729274 5.22633424,5.53937151 C5.384723,5.31316892 5.69649589,5.25819495 5.92269848,5.4165837 L6.72910242,5.98123382 C8.16546398,4.72182424 10.0239806,4 12,4 C16.418278,4 20,7.581722 20,12 C20,16.418278 16.418278,20 12,20 C7.581722,20 4,16.418278 4,12 L6,12 C6,15.3137085 8.6862915,18 12,18 C15.3137085,18 18,15.3137085 18,12 C18,8.6862915 15.3137085,6 12,6 C10.6885336,6 9.44767246,6.42282109 8.43296491,7.17429118 Z',
                  onclick: function () {
                    that.countBySubject();
                  }
                }
              }
            }
          });
        });
      },
      // 根据服务类型统计
      countByType: function () {
        let that = this;
        const typeCharts = echarts.init(this.$refs.svcTypeCharts, 'light');
        typeCharts.setOption(typeOption);

        this.$api.stat.countByType().then( (res) => {
          typeCharts.hideLoading();
          typeCharts.setOption({
            series: [{
              data: res.data.data.detail
            }],
            legend: {
              data: res.data.data.keys
            },
            toolbox: {
              show: true,
              feature: {
                myUpdateTool: {
                  show: true,
                  title: '刷新',
                  icon: 'path://M8.43296491,7.17429118 L9.40782327,7.85689436 C9.49616631,7.91875282 9.56214077,8.00751728 9.5959027,8.10994332 C9.68235021,8.37220548 9.53982427,8.65489052 9.27756211,8.74133803 L5.89079566,9.85769242 C5.84469033,9.87288977 5.79661753,9.8812917 5.74809064,9.88263369 C5.4720538,9.8902674 5.24209339,9.67268366 5.23445968,9.39664682 L5.13610134,5.83998177 C5.13313425,5.73269078 5.16477113,5.62729274 5.22633424,5.53937151 C5.384723,5.31316892 5.69649589,5.25819495 5.92269848,5.4165837 L6.72910242,5.98123382 C8.16546398,4.72182424 10.0239806,4 12,4 C16.418278,4 20,7.581722 20,12 C20,16.418278 16.418278,20 12,20 C7.581722,20 4,16.418278 4,12 L6,12 C6,15.3137085 8.6862915,18 12,18 C15.3137085,18 18,15.3137085 18,12 C18,8.6862915 15.3137085,6 12,6 C10.6885336,6 9.44767246,6.42282109 8.43296491,7.17429118 Z',
                  onclick: function () {
                    that.countByType();
                  }
                }
              }
            }
          });
        });
      },
      // 根据机构统计
      countByDept: function () {
        let that = this;

        const deptCharts = echarts.init(this.$refs.deptCharts, 'light');
        deptCharts.setOption(deptOption);

        this.$api.stat.countByDept().then( (res) => {
          deptCharts.hideLoading();
          deptCharts.setOption({
            series: [{
              data: res.data.data.values
            }],
            xAxis: [{
              data: res.data.data.keys
            }],
            toolbox: {
              show: true,
              feature: {
                myUpdateTool: {
                  show: true,
                  title: '刷新',
                  icon: 'path://M8.43296491,7.17429118 L9.40782327,7.85689436 C9.49616631,7.91875282 9.56214077,8.00751728 9.5959027,8.10994332 C9.68235021,8.37220548 9.53982427,8.65489052 9.27756211,8.74133803 L5.89079566,9.85769242 C5.84469033,9.87288977 5.79661753,9.8812917 5.74809064,9.88263369 C5.4720538,9.8902674 5.24209339,9.67268366 5.23445968,9.39664682 L5.13610134,5.83998177 C5.13313425,5.73269078 5.16477113,5.62729274 5.22633424,5.53937151 C5.384723,5.31316892 5.69649589,5.25819495 5.92269848,5.4165837 L6.72910242,5.98123382 C8.16546398,4.72182424 10.0239806,4 12,4 C16.418278,4 20,7.581722 20,12 C20,16.418278 16.418278,20 12,20 C7.581722,20 4,16.418278 4,12 L6,12 C6,15.3137085 8.6862915,18 12,18 C15.3137085,18 18,15.3137085 18,12 C18,8.6862915 15.3137085,6 12,6 C10.6885336,6 9.44767246,6.42282109 8.43296491,7.17429118 Z',
                  onclick: function () {
                    that.countByDept();
                  }
                }
              }
            }
          });
        });
      },
      // 根据适用终端类型统计
      countByTerminal: function () {
        let that = this;

        const terminalCharts = echarts.init(this.$refs.terminalCharts, 'light');
        terminalCharts.setOption(terminalOption);

        this.$api.stat.countByTerminal().then( (res) => {
          terminalCharts.hideLoading();
          terminalCharts.setOption({
            series: [{
              data: res.data.data.detail
            }],
            legend: {
              data: res.data.data.keys
            },
            toolbox: {
              show: true,
              feature: {
                myUpdateTool: {
                  show: true,
                  title: '刷新',
                  icon: 'path://M8.43296491,7.17429118 L9.40782327,7.85689436 C9.49616631,7.91875282 9.56214077,8.00751728 9.5959027,8.10994332 C9.68235021,8.37220548 9.53982427,8.65489052 9.27756211,8.74133803 L5.89079566,9.85769242 C5.84469033,9.87288977 5.79661753,9.8812917 5.74809064,9.88263369 C5.4720538,9.8902674 5.24209339,9.67268366 5.23445968,9.39664682 L5.13610134,5.83998177 C5.13313425,5.73269078 5.16477113,5.62729274 5.22633424,5.53937151 C5.384723,5.31316892 5.69649589,5.25819495 5.92269848,5.4165837 L6.72910242,5.98123382 C8.16546398,4.72182424 10.0239806,4 12,4 C16.418278,4 20,7.581722 20,12 C20,16.418278 16.418278,20 12,20 C7.581722,20 4,16.418278 4,12 L6,12 C6,15.3137085 8.6862915,18 12,18 C15.3137085,18 18,15.3137085 18,12 C18,8.6862915 15.3137085,6 12,6 C10.6885336,6 9.44767246,6.42282109 8.43296491,7.17429118 Z',
                  onclick: function () {
                    that.countByTerminal();
                  }
                }
              }
            }
          });
        });
      },
      // 根据GIS/非GIS服务统计
      countByGisOrNo: function () {
        let that = this;

        const gisCharts = echarts.init(this.$refs.gisSvcYoNCharts, 'light');
        gisCharts.setOption(gisOption);

        this.$api.stat.countByGisOrNo().then( (res) => {
          gisCharts.hideLoading();
          gisCharts.setOption({
            series: [{
              data: res.data.data.detail
            }],
            legend: {
              data: res.data.data.keys
            },
            toolbox: {
              show: true,
              feature: {
                myUpdateTool: {
                  show: true,
                  title: '刷新',
                  icon: 'path://M8.43296491,7.17429118 L9.40782327,7.85689436 C9.49616631,7.91875282 9.56214077,8.00751728 9.5959027,8.10994332 C9.68235021,8.37220548 9.53982427,8.65489052 9.27756211,8.74133803 L5.89079566,9.85769242 C5.84469033,9.87288977 5.79661753,9.8812917 5.74809064,9.88263369 C5.4720538,9.8902674 5.24209339,9.67268366 5.23445968,9.39664682 L5.13610134,5.83998177 C5.13313425,5.73269078 5.16477113,5.62729274 5.22633424,5.53937151 C5.384723,5.31316892 5.69649589,5.25819495 5.92269848,5.4165837 L6.72910242,5.98123382 C8.16546398,4.72182424 10.0239806,4 12,4 C16.418278,4 20,7.581722 20,12 C20,16.418278 16.418278,20 12,20 C7.581722,20 4,16.418278 4,12 L6,12 C6,15.3137085 8.6862915,18 12,18 C15.3137085,18 18,15.3137085 18,12 C18,8.6862915 15.3137085,6 12,6 C10.6885336,6 9.44767246,6.42282109 8.43296491,7.17429118 Z',
                  onclick: function () {
                    that.countByGisOrNo();
                  }
                }
              }
            }
          });
        });
      }

    }

  }
</script>

<style scoped>

</style>
