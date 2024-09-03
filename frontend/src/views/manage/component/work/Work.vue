<template>
  <a-card :bordered="false" class="card-area">
    <a-row :gutter="30">
      <a-col :span="6" style="margin-top: 30px">
        <p style="font-size: 30px;font-family: SimHei;font-weight: 500">欢迎使用高考志愿填报系统</p>
      </a-col>
      <a-col :span="24" style="margin-top: 25px;text-align: center">
        <img alt="example" style="height: 500px;" src="/static/img/manage.png"/>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'Work',
  data () {
    return {
      form: this.$form.createForm(this),
      formItemLayout,
      visible: false,
      statusList: [],
      vehicleList: [],
      newsContent: '',
      newsPage: 0,
      loading: false,
      userInfo: null,
      memberInfo: null,
      spaceInfo: null,
      newsList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  mounted () {
  },
  methods: {
    newsNext () {
      if (this.newsPage + 1 === this.newsList.length) {
        this.newsPage = 0
      } else {
        this.newsPage += 1
      }
      this.newsContent = `《${this.newsList[this.newsPage].title}》 ${this.newsList[this.newsPage].content.slice(0, 65)}`
    },
    handleCancel (e) {
      console.log('Clicked cancel button')
      this.visible = false
    },
    selectBulletinDetail () {
      this.$get(`/cos/user-info/selectBulletinDetail/${this.currentUser.userId}`).then((r) => {
        this.userInfo = r.data.user
        this.newsList = r.data.bulletin
        if (this.newsList.length !== 0) {
          this.newsContent = `《${this.newsList[0].title}》 ${this.newsList[0].content.slice(0, 65)}`
        }
      })
    }
  }
}
</script>

<style scoped>
>>> .ant-card-meta-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-meta-description {
  font-size: 12px;
  font-family: SimHei;
}
>>> .ant-divider-with-text-left {
  margin: 0;
}

>>> .ant-card-head-title {
  font-size: 13px;
  font-family: SimHei;
}
>>> .ant-card-extra {
  font-size: 13px;
  font-family: SimHei;
}
.ant-carousel >>> .slick-slide {
  text-align: center;
  height: 250px;
  line-height: 250px;
  overflow: hidden;
}

</style>
