<template>
  <a-card :bordered="false" class="card-area">
    <a-row :gutter="30" v-if="userInfo != null">
      <a-col :span="12" style="margin-top: 65px;text-align: center">
        <img alt="example" style="width: 500px;height: 500px;" src="/static/img/Search_SVG.png"/>
      </a-col>
      <a-col :span="6" style="margin-top: 80px">
        <p style="font-size: 30px;font-family: SimHei;font-weight: 500">欢迎使用高考志愿填报系统</p>
        <a-card :bordered="false">
          <span slot="title">
            <a-icon type="user" style="margin-right: 10px" />
            <span>用户信息</span>
          </span>
          <div>
            <a-avatar :src="'http://127.0.0.1:9527/imagesWeb/' + userInfo.images" shape="square" style="width: 100px;height: 100px;float: left;margin: 10px 0 10px 10px" />
            <div style="float: left;margin-left: 15px;margin-top: 8px">
              <span style="font-size: 20px;font-family: SimHei">{{ userInfo.name }}</span>
              <span style="font-size: 14px;font-family: SimHei">{{ userInfo.code }}</span>
            </div>
            <br/>
            <div style="float: left;margin-left: 15px;margin-top: 8px">
              <span style="font-size: 14px;font-family: SimHei">邮箱：{{ userInfo.email == null ? '- -' : userInfo.email }}</span>
            </div>
            <div style="float: left;margin-left: 15px;margin-top: 8px">
              <span style="font-size: 14px;font-family: SimHei">电话：{{ userInfo.phone == null ? '- -' : userInfo.phone }}</span>
            </div>
          </div>
        </a-card>
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
    this.selectBulletinDetail()
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
