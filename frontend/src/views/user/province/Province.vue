<template>
  <div style="width: 100%">
    <a-row style="margin-top: 25px">
      <a-col :span="24">
        <a-radio-group button-style="solid" v-model="checkFlag" style="width: 100%">
          <a-radio-button :value="item" style="text-align: center" v-for="(item, index) in provinceList" :key="index">
            {{ item }}
          </a-radio-button>
        </a-radio-group>
      </a-col>
<!--      <a-col :span="12">-->
<!--        <div>-->
<!--          <a-input-search-->
<!--            style="margin-top: 30px"-->
<!--            placeholder="学校搜索"-->
<!--            enter-button="搜索"-->
<!--            @search="onSearch"-->
<!--          />-->
<!--        </div>-->
<!--      </a-col>-->
      <a-col :span="24" style="margin-top: 25px">
        <div style="background:#ECECEC; padding:30px">
          <a-skeleton :loading="loading" active :paragraph="{ rows: 10 }"/>
          <a-alert v-if="!loading" message="此数据根据学校信息进行统计" type="info" close-text="Close Now" style="margin-bottom: 15px"/>
          <a-row :gutter="15" v-if="!loading">
            <a-col :span="6" v-for="(item, index) in currentDataList" style="margin-bottom: 15px" :key="index">
              <a-card :bordered="false" hoverable :description="item.address">
                <span slot="title">
                  <a-badge status="processing"/>
                  {{ item.schoolName }} <span style="color: #fa541c">{{ item.disciplineName }}</span>
                </span>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;font-family: SimHei">
                    <div>{{ item.province }} - {{ item.city }} </div>
                    <div style="margin-top: 10px">{{ item.manage }} {{ item.level }}</div>
                    <div style="margin-top: 10px">
                      <a-row :gutter="8">
                        <a-col :span="8">
                          <a-tag size="mini" v-if="item.type == 1" color="pink">文科</a-tag>
                          <a-tag size="mini" v-if="item.type == 2" color="blue">理科</a-tag>
                        </a-col>
                        <a-col :span="8">
                          <a-icon type="hourglass" />分数线：{{ item.score }}
                        </a-col>
                        <a-col :span="8">
                          <a-icon type="team" />招生人数：{{ item.admissions }}
                        </a-col>
                        <a-col :span="24"><a-button type="primary" style="width: 100%;margin-top: 15px" @click="apply(item)">志愿申请</a-button></a-col>
                      </a-row>
                    </div>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
          </a-row>
          <a-pagination show-quick-jumper :defaultCurrent="page.current" :total="page.total" :defaultPageSize="page.size" showLessItems @change="pageChange"/>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'House',
  data () {
    return {
      currentTab: '',
      page: {
        current: 1,
        total: 0,
        size: 36
      },
      dataList: [],
      currentDataList: [],
      provinceList: [],
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
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  watch: {
    checkFlag: function (value) {
      this.currentTab = value
      this.tabChange(value)
    }
  },
  mounted () {
    this.selectSchoolRate()
    // this.queryProvinces()
  },
  methods: {
    apply (row) {
      let params = {schoolId: row.schoolId, userId: this.currentUser.userId}
      this.$post('/cos/apply-bill-info/addApplyBill', params).then((r) => {
        this.$message.success('申请成功')
      })
    },
    onSearch (value) {
      this.page.size = 36
      this.page.current = 1
      this.selectSchoolRate(this.currentTab, value)
    },
    selectSchoolRate (type, schoolName) {
      this.loading = true
      let params = {}
      if (schoolName) {
        params.schoolName = schoolName
      }
      params.userId = this.currentUser.userId
      this.$get(`/cos/score-line-info/selectRecommendProvice`, params).then((r) => {
        let data = r.data.data
        this.dataList = data
        if (this.dataList) {
          this.provinceList = data.map(item => item.name)
          this.currentDataList = data[0].list.slice((this.page.current - 1) * this.page.size, this.page.current * this.page.size)
          const pagination = {...this.pagination}
          pagination.total = this.currentDataList.total
          this.dataList = this.currentDataList.records
          this.page = pagination
        }
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    },
    tabChange (checkFlag) {
      this.page.current = 1
      this.page.size = 36
      if (this.dataList) {
        let result = this.dataList.filter(item => item.name === checkFlag)
        if (result) {
          this.currentDataList = result[0].list.slice((this.page.current - 1) * this.page.size, this.page.current * this.page.size)
        }
      }
    },
    queryProvinces () {
      this.$get('/cos/sys-school/queryProvince').then((r) => {
        // 获取所有省份
        this.provinceList = r.data.data
      })
    },
    pageChange (page, pageSize) {
      this.page.size = pageSize
      this.page.current = page
      if (this.dataList) {
        let result = this.dataList.filter(item => item.name === this.currentTab)
        if (result) {
          this.currentDataList = result[0].list.slice((this.page.current - 1) * this.page.size, this.page.current * this.page.size)
        }
      }
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      // 如果分页信息为空，则设置为默认值
      params.size = this.page.size
      params.current = this.page.current
      this.$get('/cos/score-line-info/page', {
        ...params
      }).then((r) => {
        let data = r.data.data
        const pagination = {...this.pagination}
        pagination.total = data.total
        this.dataList = data.records
        this.page = pagination
        console.log(this.page)
        // 数据加载完毕，关闭loading
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
