<template>
  <a-modal v-model="show" title="志愿申请详情" @cancel="onClose" :width="850">
    <template slot="footer">
      <a-button key="pass" @click="onAudit" v-if="applyData.status == 1" type="primary">
        审核通过
      </a-button>
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="applyData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">志愿申请信息</span></a-col>
        <a-col :span="8"><b>学生姓名：</b>
          {{ applyData.userName }}
        </a-col>
        <a-col :span="8"><b>志愿申请编号：</b>
          {{ applyData.code }}
        </a-col>
        <a-col :span="8"><b>身份证号码：</b>
          {{ applyData.idCard }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>类型：</b>
          <a-tag v-if="applyData.type == 1">文科</a-tag>
          <a-tag v-if="applyData.type == 2">理科</a-tag>
        </a-col>
        <a-col :span="8"><b>出生日期：</b>
          {{ applyData.birthday }}
        </a-col>
        <a-col :span="8"><b>性别：</b>
          <a-tag v-if="applyData.sex == 1" color="blue">男</a-tag>
          <a-tag v-if="applyData.sex == 2" color="pink">女</a-tag>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>学校名称：</b>
          {{ applyData.schoolName }}
        </a-col>
        <a-col :span="8"><b>审核状态：</b>
          <a-tag v-if="applyData.status == 1">发送申请</a-tag>
          <a-tag v-if="applyData.status == 2" color="red">学校确认</a-tag>
          <a-tag v-if="applyData.status == 3" color="green">用户确认</a-tag>
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ applyData.createDate ? applyData.createDate : '- -'}}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>所在省份：</b>
          {{ applyData.province ? applyData.province : '- -'}}
        </a-col>
        <a-col :span="8"><b>城市：</b>
          {{ applyData.city ? applyData.city : '- -'}}
        </a-col>
        <a-col :span="8"><b>主管部门：</b>
          {{ applyData.manage ? applyData.manage : '- -'}}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>层级：</b>
          {{ applyData.level ? applyData.level : '- -'}}
        </a-col>
        <a-col :span="8"><b>学生确认时间：</b>
          {{ applyData.userConfirmDate ? applyData.userConfirmDate : '- -'}}
        </a-col>
        <a-col :span="8"><b>学校确认时间：</b>
          {{ applyData.schoolConfirmDate ? applyData.schoolConfirmDate : '- -'}}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">备注</span></a-col>
        <a-col :span="24">
          {{ applyData.remark ? applyData.remark : '- -'}}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">学生头像</span></a-col>
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
      </a-row>
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
  name: 'applyView',
  props: {
    applyShow: {
      type: Boolean,
      default: false
    },
    applyData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.applyShow
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
    applyShow: function (value) {
      if (value) {
        this.imagesInit(this.applyData.images)
      }
    }
  },
  methods: {
    onAudit () {
      let params = { id: this.applyData.id, status: 2 }
      this.$put('/cos/apply-bill-info', params).then((r) => {
        this.$emit('success')
      })
    },
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
