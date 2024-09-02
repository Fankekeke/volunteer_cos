<template>
  <a-modal v-model="show" title="修改分数线" @cancel="onClose" :width="900">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        修改
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
        <a-col :span="8"><b>所属年份：</b>
          {{ scoreData.year }}
        </a-col>
      </a-row>
      <br/>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">修改招生信息</span></a-col>
        <a-form :form="form" layout="vertical">
          <a-row :gutter="20">
            <a-col :span="12">
              <a-form-item label='招生人数' v-bind="formItemLayout">
                <a-input-number :max="100" :step="1" style="width: 100%" v-decorator="[
                'admissions',
                { rules: [{ required: true, message: '请输入招生人数!' }] }
                ]"/>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label='招生分数' v-bind="formItemLayout">
                <a-input-number :max="750" :step="1" style="width: 100%" v-decorator="[
                'score',
                { rules: [{ required: true, message: '请输入招生分数!' }] }
                ]"/>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </a-row>
    </div>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
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
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'scoreEdit',
  props: {
    scoreEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentscore: state => state.account.score
    }),
    show: {
      get: function () {
        return this.scoreEditVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      scoreData: null,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  methods: {
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    setFormValues ({...score}) {
      this.scoreData = score
      this.rowId = score.id
      let fields = ['admissions', 'score']
      let obj = {}
      Object.keys(score).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(score['images'])
        }
        // if (key === 'birthday' && score[key] != null) {
        //   score[key] = moment(score[key])
        // }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = score[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        if (image.response !== undefined) {
          images.push(image.response)
        } else {
          images.push(image.name)
        }
      })
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        if (!err) {
          this.loading = true
          this.$put('/cos/score-line-info', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
