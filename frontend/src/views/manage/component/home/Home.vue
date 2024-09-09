<template>
  <div>
    <a-row style="margin-top: 15px">
      <a-col :span="24">
        <div style="background: #ECECEC; padding: 30px;" v-if="user.roleId == 74 || user.roleId == 76">
          <a-row :gutter="16">
            <a-col :span="6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月申请数量</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.monthOrderNum }}
                    <span style="font-size: 20px;margin-top: 3px">单</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月审核通过</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.passMonthNum }}
                    <span style="font-size: 20px;margin-top: 3px">单</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本年申请数量</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.yearOrderNum }}
                    <span style="font-size: 20px;margin-top: 3px">单</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本年审核通过</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.passYearNum }}
                    <span style="font-size: 20px;margin-top: 3px">单</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
          </a-row>
        </div>
      </a-col>
    </a-row>
    <a-row style="margin-top: 15px" v-if="user.roleId == 74 || user.roleId == 76" :gutter="25">
      <a-col :span="12">
        <div hoverable :bordered="false" style="width: 100%">
          <a-skeleton active v-if="loading" />
          <apexchart v-if="!loading" type="bar" height="300" :options="chartOptions1" :series="series1"></apexchart>
        </div>
      </a-col>
      <a-col :span="12">
        <div style="width: 100%">
          <a-skeleton active v-if="loading" />
          <apexchart  v-if="!loading" type="line" height="300" :options="chartOptions" :series="series"></apexchart>
        </div>
      </a-col>
    </a-row>
    <a-row style="margin-top: 15px">
      <a-col :span="24">
        <a-card hoverable :loading="loading" :bordered="false" title="公告信息" style="margin-top: 15px">
          <div style="padding: 0 22px">
            <a-list item-layout="vertical" :pagination="pagination" :data-source="bulletinList">
              <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
                <template slot="actions">
              <span key="message">
                <a-icon type="message" style="margin-right: 8px" />
                {{ item.date }}
              </span>
                </template>
                <a-list-item-meta :description="item.content" style="font-size: 14px">
                  <a slot="title">{{ item.title }}</a>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </div>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import {mapState} from 'vuex'
export default {
  name: 'Home',
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    })
  },
  data () {
    return {
      pagination: {
        onChange: page => {
          console.log(page)
        },
        pageSize: 2
      },
      bulletinList: [],
      titleData: {
        monthOrderNum: 0,
        passMonthNum: 0,
        yearOrderNum: 0,
        passYearNum: 0,
        monthPutNum: 0,
        monthPutPrice: 0,
        yearPutNum: 0,
        yearPutPrice: 0
      },
      loading: false,
      series: [{
        name: '申请单',
        data: []
      }],
      chartOptions: {
        chart: {
          type: 'line',
          height: 300
        },
        xaxis: {
          categories: []
        },
        stroke: {
          curve: 'stepline'
        },
        dataLabels: {
          enabled: false
        },
        title: {
          text: '近十天内出库收益统计',
          align: 'left'
        },
        markers: {
          hover: {
            sizeOffset: 4
          }
        }
      },
      series1: [{
        name: '申请单',
        data: []
      }],
      chartOptions1: {
        chart: {
          type: 'bar',
          height: 300
        },
        title: {
          text: '近十天申请',
          align: 'left'
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%'
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: []
        },
        yaxis: {
          title: {
            text: ''
          }
        },
        fill: {
          opacity: 1
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + ' 单'
            }
          }
        }
      },
      series2: [{
        name: '申请单',
        data: []
      }],
      chartOptions2: {
        chart: {
          type: 'line',
          height: 300
        },
        xaxis: {
          categories: []
        },
        stroke: {
          curve: 'stepline'
        },
        dataLabels: {
          enabled: false
        },
        title: {
          text: '近十天申请通过',
          align: 'left'
        },
        markers: {
          hover: {
            sizeOffset: 4
          }
        }
      },
      series3: [{
        name: '申请单',
        data: []
      }],
      chartOptions3: {
        chart: {
          type: 'bar',
          height: 300
        },
        title: {
          text: '近十天内入库收益统计',
          align: 'left'
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%'
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: []
        },
        yaxis: {
          title: {
            text: ''
          }
        },
        fill: {
          opacity: 1
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + ' 单'
            }
          }
        }
      }
    }
  },
  mounted () {
    this.loading = true
    this.selectHomeData()
    setTimeout(() => {
      this.loading = false
    }, 200)
  },
  methods: {
    selectHomeData () {
      this.$get('/cos/sys-school/homeData').then((r) => {
        let titleData = { userNum: r.data.userNum, schoolNum: r.data.schoolNum, disciplineNum: r.data.disciplineNum, billNum: r.data.billNum }
        this.$emit('setTitle', titleData)
        this.titleData.monthOutNum = r.data.monthOutNum
        this.titleData.passMonthNum = r.data.passMonthNum
        this.titleData.yearOutNum = r.data.yearOutNum
        this.titleData.passYearNum = r.data.passYearNum

        this.titleData.monthPutNum = r.data.monthPutNum
        this.titleData.monthPutPrice = r.data.monthPutPrice
        this.titleData.yearPutNum = r.data.yearPutNum
        this.titleData.yearPutPrice = r.data.yearPutPrice
        this.bulletinList = r.data.bulletin
        let values = []
        // if (r.data.orderNumWithinDays !== null && r.data.orderNumWithinDays.length !== 0) {
        //   if (this.chartOptions1.xaxis.categories.length === 0) {
        //     this.chartOptions1.xaxis.categories = r.data.orderNumWithinDays.map(obj => { return obj.days })
        //   }
        //   let itemData = { name: '出库统计', data: r.data.orderNumWithinDays.map(obj => { return obj.count }) }
        //   values.push(itemData)
        //   this.series1 = values
        // }
        this.series1[0].data = r.data.orderNumDayList.map(obj => { return obj.count })
        this.chartOptions1.xaxis.categories = r.data.orderNumDayList.map(obj => { return obj.days })

        this.series[0].data = r.data.priceDayList.map(obj => { return obj.count })
        this.chartOptions.xaxis.categories = r.data.priceDayList.map(obj => { return obj.days })

        // if (r.data.putNumWithinDays !== null && r.data.putNumWithinDays.length !== 0) {
        //   if (this.chartOptions2.xaxis.categories.length === 0) {
        //     this.chartOptions2.xaxis.categories = r.data.putNumWithinDays.map(obj => { return obj.days })
        //   }
        //   let itemData = { name: '出库统计', data: r.data.putNumWithinDays.map(obj => { return obj.count }) }
        //   values.push(itemData)
        //   this.series2 = values
        // }
      })
    }
  }
}
</script>

<style scoped>

</style>
