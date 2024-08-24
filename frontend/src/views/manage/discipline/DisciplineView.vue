<template>
  <a-modal v-model="show" title="专业详情" @cancel="onClose" :width="850">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="disciplineData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">专业信息</span></a-col>
        <a-col :span="8"><b>专业名称：</b>
          {{ disciplineData.name }}
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ disciplineData.createDate }}
        </a-col>
        <a-col :span="8"><b>类型：</b>
          <a-tag v-if="disciplineData.type == 1">专业类型</a-tag>
          <a-tag v-if="disciplineData.type == 2">专业名称</a-tag>
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">就业方向</span></a-col>
        <a-col :span="24">
          {{ disciplineData.employment ? disciplineData.employment : '暂无信息'}}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">备注</span></a-col>
        <a-col :span="24">
          {{ disciplineData.remark ? disciplineData.remark : '暂无信息'}}
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
  name: 'disciplineView',
  props: {
    disciplineShow: {
      type: Boolean,
      default: false
    },
    disciplineData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.disciplineShow
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
    disciplineShow: function (value) {
      if (value) {
        this.imagesInit(this.disciplineData.images)
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
