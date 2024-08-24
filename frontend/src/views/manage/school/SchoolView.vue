<template>
  <a-modal v-model="show" title="学校详情" @cancel="onClose" :width="850">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="schoolData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">学校信息</span></a-col>
        <a-col :span="8"><b>学校名称：</b>
          {{ schoolData.name }}
        </a-col>
        <a-col :span="8"><b>省份：</b>
          {{ schoolData.province }}
        </a-col>
        <a-col :span="8"><b>城市：</b>
          {{ schoolData.city }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>地址：</b>
          {{ schoolData.address }}
        </a-col>
        <a-col :span="8"><b>水平层次：</b>
          {{ schoolData.level }}
        </a-col>
        <a-col :span="8"><b>办学类别：</b>
          {{ schoolData.type }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>办学类型：</b>
          {{ schoolData.schoolType }}
        </a-col>
        <a-col :span="8"><b>是否为985：</b>
          {{ schoolData.nineFlag }}
        </a-col>
        <a-col :span="8"><b>是否为211：</b>
          {{ schoolData.twoFlag }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>是否为双一流：</b>
          {{ schoolData.doubleFirstClass }}
        </a-col>
        <a-col :span="8"><b>归属：</b>
          {{ schoolData.manage }}
        </a-col>
        <br/>
        <br/>
        <a-col :span="24"><b>开设专业链接：</b>
          {{ schoolData.linkUrl }}
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
  name: 'schoolView',
  props: {
    schoolShow: {
      type: Boolean,
      default: false
    },
    schoolData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.schoolShow
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
    schoolShow: function (value) {
      if (value) {
        this.imagesInit(this.schoolData.images)
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
