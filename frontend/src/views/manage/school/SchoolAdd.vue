<template>
  <a-modal v-model="show" title="新增学校" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='学校名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入学校名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='省份' v-bind="formItemLayout">
            <a-input v-decorator="[
            'province',
            { rules: [{ required: true, message: '请输入省份!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='城市' v-bind="formItemLayout">
            <a-input v-decorator="[
            'city',
            { rules: [{ required: true, message: '请输入城市!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='地址' v-bind="formItemLayout">
            <a-input v-decorator="[
            'address',
            { rules: [{ required: true, message: '请输入地址!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='水平层次' v-bind="formItemLayout">
            <a-input v-decorator="[
            'level',
            { rules: [{ required: true, message: '请输入水平层次!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='办学类别' v-bind="formItemLayout">
            <a-input v-decorator="[
            'type',
            { rules: [{ required: true, message: '请输入办学类别!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='办学类型' v-bind="formItemLayout">
            <a-input v-decorator="[
            'schoolType',
            { rules: [{ required: true, message: '请输入办学类型!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='归属' v-bind="formItemLayout">
            <a-input v-decorator="[
            'manage',
            { rules: [{ required: true, message: '请输入是归属!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='开设专业链接' v-bind="formItemLayout">
            <a-input v-decorator="[
            'linkUrl',
            { rules: [{ required: true, message: '请输入是开设专业链接!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24"></a-col>
        <a-col :span="8">
          <a-form-item label='是否985' v-bind="formItemLayout">
            <a-radio-group default-value="否" button-style="solid" v-decorator="[
              'nineFlag',
              { rules: [{ required: true, message: '请输入是否985!' }] }
              ]">
              <a-radio-button value="是">
                是
              </a-radio-button>
              <a-radio-button value="否">
                否
              </a-radio-button>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='是否211' v-bind="formItemLayout">
            <a-radio-group default-value="否" button-style="solid" v-decorator="[
              'twoFlag',
              { rules: [{ required: true, message: '请输入是否211!' }] }
              ]">
              <a-radio-button value="是">
                是
              </a-radio-button>
              <a-radio-button value="否">
                否
              </a-radio-button>
            </a-radio-group>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item label='是否双一流' v-bind="formItemLayout">
            <a-radio-group default-value="否" button-style="solid" v-decorator="[
              'twoFlag',
              { rules: [{ required: true, message: '请输入是否双一流!' }] }
              ]">
              <a-radio-button value="是">
                是
              </a-radio-button>
              <a-radio-button value="否">
                否
              </a-radio-button>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
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
  name: 'schoolAdd',
  props: {
    schoolAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentschool: state => state.account.school
    }),
    show: {
      get: function () {
        return this.schoolAddVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
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
        images.push(image.response)
      })
      this.form.validateFields((err, values) => {
        values.images = images.length > 0 ? images.join(',') : null
        // values.birthday = moment(values.birthday).format('YYYY-MM-DD')
        if (!err) {
          this.loading = true
          this.$post('/cos/sys-school', {
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
