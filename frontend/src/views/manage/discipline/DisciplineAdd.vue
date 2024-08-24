<template>
  <a-modal v-model="show" title="新增专业" @cancel="onClose" :width="800">
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
          <a-form-item label='专业名称' v-bind="formItemLayout">
            <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入专业名称!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='专业名称' v-bind="formItemLayout">
            <a-select @change="handleChange" v-decorator="[
                  'type',
                  { rules: [{ required: true, message: '请输入类型!' }] }
                  ]">
              <a-select-option value="1">专业类型</a-select-option>
              <a-select-option value="2">专业名称</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12" v-if="typeValue == 2">
          <a-form-item label='所属上级' v-bind="formItemLayout">
            <a-select v-decorator="[
                  'parentCode',
                  { rules: [{ required: true, message: '请输入所属上级!' }] }
                  ]">
              <a-select-option v-for="(item, index) in disciplineTopList" :key="index" :value="item.code">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='排序' v-bind="formItemLayout">
            <a-input-number style="width: 100%"
                            v-decorator="[
            'indexNo',
            { rules: [{ required: true, message: '请输入排序!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='就业方向' v-bind="formItemLayout">
            <a-textarea :rows="4" v-decorator="[
            'employment',
            { rules: [{ required: true, message: '请输入就业方向!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='备注' v-bind="formItemLayout">
            <a-textarea :rows="4" v-decorator="[
            'remark',
            { rules: [{ required: true, message: '请输入备注!' }] }
            ]"/>
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
  name: 'disciplineAdd',
  props: {
    disciplineAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentdiscipline: state => state.account.discipline
    }),
    show: {
      get: function () {
        return this.disciplineAddVisiable
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
      previewImage: '',
      typeValue: null,
      disciplineTopList: []
    }
  },
  mounted () {
    this.selectDisciplineTopList()
  },
  methods: {
    selectDisciplineTopList () {
      this.$get('/cos/discipline-info/selectDisciplineTopList').then((r) => {
        this.disciplineTopList = r.data.data
      })
    },
    handleChange (value) {
      this.typeValue = value
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
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          if (this.typeValue === '1') {
            values.parentCode = '0'
          }
          this.loading = true
          this.$post('/cos/discipline-info', {
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
