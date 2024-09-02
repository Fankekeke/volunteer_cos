<template>
  <a-modal v-model="show" title="专业绑定详情" @cancel="onClose" :width="850">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="bindData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">专业绑定信息</span></a-col>
        <a-col :span="8"><b>企业名称：</b>
          {{ bindData.name }}
        </a-col>
        <a-col :span="8"><b>专业绑定编号：</b>
          {{ bindData.code }}
        </a-col>
        <a-col :span="8"><b>联系方式：</b>
          {{ bindData.phone }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>类型：</b>
          <a-tag v-if="bindData.type == 1">经销商</a-tag>
          <a-tag v-if="bindData.type == 2">批发商</a-tag>
          <a-tag v-if="bindData.type == 3">散客</a-tag>
          <a-tag v-if="bindData.type == 4">代理商</a-tag>
        </a-col>
        <a-col :span="8"><b>联系人：</b>
          {{ bindData.contact }}
        </a-col>
        <a-col :span="8"><b>性别：</b>
          <a-tag v-if="bindData.sex == 1" color="blue">男</a-tag>
          <a-tag v-if="bindData.sex == 2" color="pink">女</a-tag>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>邮箱地址：</b>
          {{ bindData.email }}
        </a-col>
        <a-col :span="8"><b>审核状态：</b>
          <a-tag v-if="bindData.status == 0">未审核</a-tag>
          <a-tag v-if="bindData.status == 1" color="red">审核驳回</a-tag>
          <a-tag v-if="bindData.status == 2" color="green">已审核</a-tag>
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ bindData.createDate ? bindData.createDate : '- -'}}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>审核时间：</b>
          {{ bindData.auditDate ? bindData.auditDate : '- -'}}
        </a-col>
        <a-col :span="8"><b>消费金额：</b>
          {{ bindData.price ? bindData.price : '- -'}}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">备注</span></a-col>
        <a-col :span="24">
          {{ bindData.remark ? bindData.remark : '- -'}}
        </a-col>
      </a-row>
      <br/>
      <a-col :span="24">
        <a-upload
          name="avatar"
          action="http://127.0.0.1:9527/file/fileUpload/"
          list-type="picture-card"
          :file-list="fileList"
          @preview="handlePreview"
          @change="picHandleChange">
        </a-upload>
        <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
          <img alt="example" style="width: 100%" :src="previewImage" />
        </a-modal>
      </a-col>
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
  name: 'bindView',
  props: {
    bindShow: {
      type: Boolean,
      default: false
    },
    bindData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.bindShow
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
    bindShow: function (value) {
      if (value) {
        this.imagesInit(this.bindData.images)
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
