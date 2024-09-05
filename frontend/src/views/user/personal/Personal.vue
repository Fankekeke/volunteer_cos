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
                <a-form-item label='学生姓名' v-bind="formItemLayout">
                  <a-input v-decorator="[
            'name',
            { rules: [{ required: true, message: '请输入学生姓名!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='类型' v-bind="formItemLayout">
                  <a-select v-decorator="[
                  'type',
                  { rules: [{ required: true, message: '请输入类型!' }] }
                  ]">
                    <a-select-option value="1">文科</a-select-option>
                    <a-select-option value="2">理科</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='性别' v-bind="formItemLayout">
                  <a-select v-decorator="[
                  'sex',
                  ]">
                    <a-select-option value="1">男</a-select-option>
                    <a-select-option value="2">女</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='身份证号' v-bind="formItemLayout">
                  <a-input v-decorator="[
            'idCard',
            { rules: [{ required: true, message: '请输入身份证号!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='籍贯' v-bind="formItemLayout">
                  <a-input v-decorator="[
            'nativePlace',
            { rules: [{ required: true, message: '请输入籍贯' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='详细地址' v-bind="formItemLayout">
                  <a-input v-decorator="[
            'address',
            { rules: [{ required: true, message: '请输入详细地址' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='邮箱地址' v-bind="formItemLayout">
                  <a-input v-decorator="[
            'email',
            { rules: [{ required: true, message: '请输入邮箱地址!' }] }
            ]"/>
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label='分数' v-bind="formItemLayout">
                  <a-input-number :max="750" :step="1" :min="0" style="width: 100%" v-decorator="[
                'score',
                { rules: [{ required: true, message: '请输入分数!' }] }
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
              <a-col :span="24">
                <a-form-item label='头像' v-bind="formItemLayout">
                  <a-upload
                    name="avatar"
                    action="http://127.0.0.1:9527/file/fileUpload/"
                    list-type="picture-card"
                    :file-list="fileList"
                    @preview="handlePreview"
                    @change="picHandleChange"
                  >
                    <div v-if="fileList.length < 1">
                      <a-icon type="plus" />
                      <div class="ant-upload-text">
                        Upload
                      </div>
                    </div>
                  </a-upload>
                  <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
                    <img alt="example" style="width: 100%" :src="previewImage" />
                  </a-modal>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
          <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
            修改
          </a-button>
        </a-card>
      </div>
    </a-col>
    <a-col :span="16">
      <div style="background:#ECECEC; padding:30px;margin-top: 30px">
        <a-card :bordered="false">
          <a-spin :spinning="dataLoading">
            <a-card hoverable :loading="loading" :bordered="false" title="公告信息" style="margin-top: 15px">
              <div style="padding: 0 22px">
                <a-list item-layout="vertical" :pagination="pagination" :data-source="courseInfo">
                  <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
                    <template slot="actions">
                      <span key="message">
                        <a-icon type="message" style="margin-right: 8px" />
                        {{ item.date }}
                      </span>
                    </template>
                    <a-list-item-meta :description="item.content" style="font-size: 14px">
                      <a slot="title">{{ item.title }}</a>
                    </a-list-item-meta>
                  </a-list-item>
                </a-list>
              </div>
            </a-card>
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
      pagination: {
        onChange: page => {
          console.log(page)
        },
        pageSize: 5
      },
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
          listData.push({type: 'success', content: "✔"})
        }
      })
      return listData || []
    },
    dataInit () {
      this.dataLoading = true
      this.$get(`/cos/user-info/selectUserBulletin/${this.currentUser.userId}`).then((r) => {
        this.rowId = r.data.user.id
        this.setFormValues(r.data.user)
        this.courseInfo = r.data.bulletin
        this.dataLoading = false
      })
    },
    setFormValues ({...user}) {
      this.rowId = user.id
      let fields = ['name', 'email', 'address', 'type', 'sex', 'idCard', 'remark', 'nativePlace', 'score']
      let obj = {}
      Object.keys(user).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(user['images'])
        }
        if (key === 'birthday' && user[key] != null) {
          user[key] = moment(user[key])
        }
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
