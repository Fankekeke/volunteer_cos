<template>
  <a-row :gutter="8" style="width: 100%">
    <a-col :span="8">
      <div style="background:#ECECEC; padding:30px;margin-top: 30px">
        <a-card :bordered="false">
          <b style="font-size: 15px">我的信息</b>
        </a-card>
        <a-card :bordered="false">
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
              <a-col :span="24">
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
          <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit" style="margin-top: 20px">
            修改
          </a-button>
        </a-card>
      </div>
    </a-col>
    <a-col :span="16">
      <div style="background:#ECECEC; padding:30px;margin-top: 30px">
        <a-card :bordered="false">
          <a-spin :spinning="dataLoading">
            <a-calendar>
              <ul slot="dateCellRender" slot-scope="value" class="events">
                <li v-for="item in getListData(value)" :key="item.content">
                  <a-badge :status="item.type" :text="item.content" />
                </li>
              </ul>
            </a-calendar>
          </a-spin>
        </a-card>
      </div>
    </a-col>
  </a-row>
</template>

<script>
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'User',
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  data () {
    return {
      rowId: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      courseInfo: [],
      dataLoading: false,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  mounted () {
    this.dataInit()
  },
  methods: {
    moment,
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
    isDuringDate (beginDateStr, endDateStr, curDataStr) {
      let curDate = new Date(curDataStr)
      let beginDate = new Date(beginDateStr)
      let endDate = new Date(endDateStr)
      if (curDate >= beginDate && curDate <= endDate) {
        return true
      }
      return false
    },
    getListData (value) {
      let listData = []
      this.courseInfo.forEach(item => {
        if ((moment(value).format('YYYY-MM-DD')) === (moment(item.registerDate).format('YYYY-MM-DD'))) {
          listData.push({type: 'success', content: '✔'})
        }
      })
      return listData || []
    },
    dataInit () {
      this.dataLoading = true
      this.$get(`/cos/sys-school/selectSchoolBulletin/${this.currentUser.userId}`).then((r) => {
        this.rowId = r.data.school.id
        this.setFormValues(r.data.school)
        this.courseInfo = r.data.bulletin
        this.dataLoading = false
      })
    },
    setFormValues ({...user}) {
      this.rowId = user.id
      let fields = ['name', 'province', 'city', 'address', 'level', 'type', 'schoolType', 'nineFlag', 'twoFlag', 'doubleFirstClass', 'manage', 'linkUrl']
      let obj = {}
      Object.keys(user).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(user['images'])
        }
        // if (key === 'birthday' && user[key] != null) {
        //   user[key] = moment(user[key])
        // }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = user[key]
        }
      })
      this.form.setFieldsValue(obj)
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
        values.images = images.length > 0 ? images.join(',') : null
        if (!err) {
          this.loading = true
          this.$put('/cos/user-info', {
            ...values
          }).then((r) => {
            this.$message.success('修改信息成功')
            this.loading = false
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
