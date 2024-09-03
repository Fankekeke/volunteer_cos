<template>
  <a-modal v-model="show" title="修改志愿专业" @cancel="onClose" :width="400">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        修改
      </a-button>
    </template>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="24">
          <a-form-item label='选择专业' v-bind="formItemLayout">
            <a-select
              v-decorator="[
              'disciplineCode',
              { rules: [{ required: true, message: '请输入专业!' }] }
              ]"
              show-search
              placeholder="请选择专业..."
              style="width: 100%"
              :default-active-first-option="false"
              :show-arrow="false"
              :filter-option="false"
              :not-found-content="null"
              @search="disciplineHandleSearch">
              <a-select-option v-for="d in disciplineList" :value="d.code" :key="d.code">
                {{ d.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='排序' v-bind="formItemLayout">
            <a-input-number style="width: 100%"
                            v-decorator="[
            'indexNo',
            { rules: [{ required: true, message: '请输入排序!' }] }
            ]"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
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
  name: 'wishDisciplineEdit',
  props: {
    wishDisciplineEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentwishDiscipline: state => state.account.wishDiscipline
    }),
    show: {
      get: function () {
        return this.wishDisciplineEditVisiable
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
      fileList: [],
      disciplineList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  methods: {
    disciplineHandleSearch (value) {
      this.disciplineList = []
      if (value !== '' && value !== null) {
        this.$get(`/cos/discipline-info/listLikeByKey/${value}`).then((r) => {
          this.disciplineList = r.data.data
        })
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    setFormValues ({...wishDiscipline}) {
      this.rowId = wishDiscipline.id
      let fields = ['indexNo', 'disciplineId', 'disciplineName']
      let obj = {}
      Object.keys(wishDiscipline).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(wishDiscipline['images'])
        }
        if (key === 'disciplineName' && bind[key] != null) {
          this.disciplineHandleSearch(bind[key])
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = wishDiscipline[key]
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
          this.$put('/cos/user-wish-discipline', {
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
