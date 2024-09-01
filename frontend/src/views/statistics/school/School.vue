<template>
  <div style="width: 100%">
    <a-row style="margin-top: 25px">
      <a-col :span="24">
        <a-radio-group button-style="solid" v-model="checkFlag" style="width: 100%">
          <a-radio-button value="1" style="width: 25%;text-align: center">
            按地区分类
          </a-radio-button>
          <a-radio-button value="2" style="width: 25%;text-align: center">
            按主管部门分类
          </a-radio-button>
          <a-radio-button value="3" style="width: 25%;text-align: center">
            按层次分类
          </a-radio-button>
        </a-radio-group>
      </a-col>
      <a-col :span="24" style="margin-top: 25px">
        <div style="background:#ECECEC; padding:30px">
          <a-skeleton :loading="loading" active :paragraph="{ rows: 10 }"/>
          <a-alert v-if="!loading" message="此数据根据学校信息进行统计" type="info" close-text="Close Now" style="margin-bottom: 15px"/>
          <a-row :gutter="15" v-if="!loading">
            <a-col :span="4" v-for="(item, index) in dataList" style="margin-bottom: 15px" :key="index">
              <a-card :bordered="false" hoverable>
                <span slot="title">
                  <a-badge status="processing"/>
                  {{ item.name }}
                </span>
                <a-row>
                  <a-col :span="12" style="text-align: center;font-size: 18px;font-family: SimHei;">
                    <a-icon type="arrow-up" style="margin-right: 5px"/>{{ item.data === undefined ? 0 : item.data.length }}
                    <span style="font-size: 12px">家</span>
                  </a-col>
                  <!--                  <a-col :span="12" style="text-align: center;font-size: 18px;font-family: SimHei;">-->
                  <!--                    <a-icon type="arrow-up" style="color: red;margin-right: 5px"/>{{ item.rent === undefined ? 0 : item.rent.length }}-->
                  <!--                    <span style="font-size: 10px">套</span>-->
                  <!--                  </a-col>-->
                  <!--                  <a-col :span="12" style="text-align: center;font-size: 18px;font-family: SimHei;">-->
                  <!--                    <a-icon type="arrow-down" style="color: green;margin-right: 5px"/>{{ item.rent === undefined ? 0 : item.rent.filter(e => e.flag == 3).length }}-->
                  <!--                    <span style="font-size: 10px">套</span>-->
                  <!--                  </a-col>-->
                </a-row>
              </a-card>
            </a-col>
          </a-row>
        </div>
        <!--          <a-col :span="24">-->
        <!--            <apexchart type="radar" height="350" :options="chartOptions" :series="series"></apexchart>-->
        <!--          </a-col>-->
      </a-col>
    </a-row>
  </div>
</template>

<script>
export default {
  name: 'House',
  data () {
    return {
      dataList: [],
      loading: false,
      checkFlag: '1',
      series: [{
        name: 'Series 1',
        data: [80, 50, 30, 40, 100, 20]
      }],
      chartOptions: {
        chart: {
          height: 350,
          type: 'radar'
        },
        title: {
          text: 'Basic Radar Chart'
        },
        xaxis: {
          categories: ['January', 'February', 'March', 'April', 'May', 'June']
        }
      }
    }
  },
  watch: {
    checkFlag: function (value) {
      this.selectSchoolRate(value)
    }
  },
  mounted () {
    this.selectSchoolRate(1)
  },
  methods: {
    selectSchoolRate (type) {
      this.loading = true
      this.$get(`/cos/score-line-info/selectSchoolRate/type/${type}`).then((r) => {
        this.dataList = r.data.data
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>
>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-alert-message {
  font-size: 14px;
  font-family: SimHei;
}
</style>
