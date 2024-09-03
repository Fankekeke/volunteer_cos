<template>
  <div style="width: 100%">
    <a-row style="margin-top: 25px">
      <a-col :span="24" style="margin-top: 25px">
        <div style="background:#ECECEC; padding:30px">
          <a-skeleton :loading="loading" active :paragraph="{ rows: 10 }"/>
          <a-alert v-if="!loading" message="此数据根据学校信息进行统计" type="info" close-text="Close Now" style="margin-bottom: 15px"/>
          <a-row :gutter="15" v-if="!loading">
            <a-col :span="6" v-for="(item, index) in dataList" style="margin-bottom: 15px" :key="index">
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
import { mapMutations, mapState } from 'vuex'
export default {
  name: 'House',
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      page: {
        current: 1,
        total: 0,
        size: 36
      },
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
    this.fetch()
  },
  methods: {
    selectSchoolRate (type) {
      this.loading = true
      this.$get(`/cos/score-line-info/selectSchoolRate/type/${type}`).then((r) => {
        this.dataList = r.data.data
        this.loading = false
      })
    },
    pageChange (page, pageSize) {
      this.page.size = pageSize
      this.page.current = page
      this.fetch()
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      // 如果分页信息为空，则设置为默认值
      params.size = this.page.size
      params.current = this.page.current
      params.schoolId = this.currentUser.userId
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
