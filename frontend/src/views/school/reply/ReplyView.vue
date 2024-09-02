<template>
  <a-modal v-model="show" title="评论详情" @cancel="onClose" :width="850">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="replyData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">评论信息</span></a-col>
        <a-col :span="8"><b>学生姓名：</b>
          {{ replyData.userName }}
        </a-col>
        <a-col :span="8"><b>类型：</b>
          {{ replyData.type == 1 ? '文科' : '理科' }}
        </a-col>
        <a-col :span="8"><b>身份号码：</b>
          {{ replyData.idCard == null ? '暂无数据' : replyData.idCard }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>学校姓名：</b>
          {{ replyData.schoolName }}
        </a-col>
        <a-col :span="8"><b>省份：</b>
          {{ replyData.province == null ? '暂无数据' : replyData.province }}
        </a-col>
        <a-col :span="8"><b>城市：</b>
          {{ replyData.city == null ? '暂无数据' : replyData.city }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>归属：</b>
          {{ replyData.manage == null ? '暂无数据' : replyData.manage }}
        </a-col>
        <a-col :span="8"><b>水平层次：</b>
          {{ replyData.level == null ? '暂无数据' : replyData.level }}
        </a-col>
        <a-col :span="8"><b>评论时间：</b>
          {{ replyData.createDate ? replyData.createDate : '- -'}}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">评论内容</span></a-col>
        <a-col :span="24">
          {{ replyData.content ? replyData.content : '- -'}}
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
  name: 'replyView',
  props: {
    replyShow: {
      type: Boolean,
      default: false
    },
    replyData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.replyShow
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
    replyShow: function (value) {
      if (value) {
        this.imagesInit(this.replyData.images)
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
