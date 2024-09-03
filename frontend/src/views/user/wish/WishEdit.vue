<template>
  <a-modal v-model="show" title="修改志愿" @cancel="onClose" :width="600">
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
          <a-form-item label='选择学校' v-bind="formItemLayout">
            <a-select
              v-decorator="[
              'schoolId',
              { rules: [{ required: true, message: '请输入学校!' }] }
              ]"
              @change="handleChange"
              show-search
              placeholder="请选择学校..."
              style="width: 100%"
              :default-active-first-option="false"
              :show-arrow="false"
              :filter-option="false"
              :not-found-content="null"
              @search="handleSearch">
              <a-select-option v-for="d in schoolList" :value="d.id" :key="d.id">
                {{ d.name }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='选择专业' v-bind="formItemLayout">
            <a-select
              :disabled="!schoolFlag"
              v-decorator="[
              'disciplineId',
              { rules: [{ required: true, message: '请输入专业!' }] }
              ]"
              placeholder="请选择专业..."
              style="width: 100%">
              <a-select-option v-for="d in disciplineList" :value="d.disciplineId" :key="d.disciplineId">
                {{ d.disciplineName }}
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
  name: 'wishEdit',
  props: {
    wishEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentwish: state => state.account.wish
    }),
    show: {
      get: function () {
        return this.wishEditVisiable
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
      schoolList: [],
      schoolFlag: null,
      disciplineList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  methods: {
    handleSearch (value) {
      this.schoolList = []
      if (value !== '' && value !== null) {
        this.$get(`/cos/sys-school/listLikeByKey/${value}`).then((r) => {
          this.schoolList = r.data.data
        })
      }
    },
    handleChange (value) {
      if (value) {
        this.selectDiscipline(value)
      }
    },
    selectDiscipline (schoolId) {
      this.$get(`/cos/school-discipline-bind/selectBindBySchool`, {schoolId}).then((r) => {
        this.disciplineList = r.data.data
      })
    },
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
    setFormValues ({...wish}) {
      this.rowId = wish.id
      let fields = ['indexNo', 'schoolId', 'disciplineId']
      let obj = {}
      Object.keys(wish).forEach((key) => {
        if (key === 'schoolId' && wish[key] != null) {
          this.schoolFlag = wish[key]
          this.selectDiscipline(wish[key])
        }
        if (key === 'schoolName' && wish[key] != null) {
          this.handleSearch(wish[key])
        }
        // if (key === 'disciplineId' && wish[key] != null) {
        //   wish[key] = wish[key].toString()
        // }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = wish[key]
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
          this.$put('/cos/user-wish-info', {
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
