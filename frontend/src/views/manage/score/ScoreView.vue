<template>
  <a-modal v-model="show" title="分数线详情" @cancel="onClose" :width="850">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="scoreData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">分数线信息</span></a-col>
        <a-col :span="8"><b>学校名称：</b>
          {{ scoreData.schoolName }}
        </a-col>
        <a-col :span="8"><b>学校省份：</b>
          {{ scoreData.province }}
        </a-col>
        <a-col :span="8"><b>城市：</b>
          {{ scoreData.city }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>归属：</b>
          {{ scoreData.manage }}
        </a-col>
        <a-col :span="8"><b>水平层次：</b>
          {{ scoreData.level }}
        </a-col>
        <a-col :span="8"><b>专业名称：</b>
          {{ scoreData.disciplineName }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>类型：</b>
          <a-tag v-if="scoreData.type == 1" color="blue">文科</a-tag>
          <a-tag v-if="scoreData.type == 2" color="pink">理科</a-tag>
        </a-col>
        <a-col :span="8"><b>招生分数：</b>
          {{ scoreData.score }}
        </a-col>
        <a-col :span="8"><b>所属年份：</b>
          {{ scoreData.year }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>招生人数：</b>
          {{ scoreData.admissions }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">就业方向</span></a-col>
        <a-col :span="24">
          {{ scoreData.employment ? scoreData.employment : '暂无数据'}}
        </a-col>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'scoreView',
  props: {
    scoreShow: {
      type: Boolean,
      default: false
    },
    scoreData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.scoreShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      durgList: []
    }
  },
  watch: {
    scoreShow: function (value) {
      if (value) {
        this.imagesInit(this.scoreData.images)
      }
    }
  },
  methods: {
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
