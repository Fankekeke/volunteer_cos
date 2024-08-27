<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="学校名称"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.schoolName"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="专业名称"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.disciplineName"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="类型"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-select v-model="queryParams.type">
                  <a-select-option value="1">文科</a-select-option>
                  <a-select-option value="2">理科</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="所属年份"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-select v-model="queryParams.year">
                  <a-select-option value="2020">2020</a-select-option>
                  <a-select-option value="2021">2021</a-select-option>
                  <a-select-option value="2022">2022</a-select-option>
                  <a-select-option value="2023">2023</a-select-option>
                  <a-select-option value="2024">2024</a-select-option>
                  <a-select-option value="2025">2025</a-select-option>
                  <a-select-option value="2026">2026</a-select-option>
                  <a-select-option value="2027">2027</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
<!--        <a-button type="primary" ghost @click="add">新增</a-button>-->
        <a-button @click="batchDelete">删除</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="titleShow" slot-scope="text, record">
          <template>
            <a-tooltip>
              <template slot="title">
                {{ record.title }}
              </template>
              {{ record.title.slice(0, 8) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon type="cloud" @click="handlescoreViewOpen(record)" title="详 情" style="margin-right: 10px"></a-icon>
<!--          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改" style="margin-right: 10px"></a-icon>-->
        </template>
      </a-table>
    </div>
    <score-add
      v-if="scoreAdd.visiable"
      @close="handlescoreAddClose"
      @success="handlescoreAddSuccess"
      :scoreAddVisiable="scoreAdd.visiable">
    </score-add>
    <score-edit
      ref="scoreEdit"
      @close="handlescoreEditClose"
      @success="handlescoreEditSuccess"
      :scoreEditVisiable="scoreEdit.visiable">
    </score-edit>
    <score-view
      @close="handlescoreViewClose"
      :scoreShow="scoreView.visiable"
      :scoreData="scoreView.data">
    </score-view>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import {mapState} from 'vuex'
import scoreAdd from './ScoreAdd.vue'
import scoreEdit from './ScoreEdit.vue'
import scoreView from './ScoreView.vue'
import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'score',
  components: {RangeDate, scoreAdd, scoreEdit, scoreView},
  data () {
    return {
      advanced: false,
      scoreAdd: {
        visiable: false
      },
      scoreEdit: {
        visiable: false
      },
      scoreView: {
        visiable: false,
        data: null
      },
      queryParams: {
        year: '2024'
      },
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      scoreList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '学校名称',
        dataIndex: 'schoolName',
        ellipsis: true
      }, {
        title: '学校地址',
        dataIndex: 'address',
        ellipsis: true
      }, {
        title: '专业名称',
        dataIndex: 'disciplineName',
        ellipsis: true
      }, {
        title: '就业方向',
        dataIndex: 'employment',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '暂无信息'
          }
        },
        ellipsis: true
      }, {
        title: '类型',
        dataIndex: 'type',
        customRender: (text, row, index) => {
          switch (text) {
            case '1':
              return <a-tag>文科</a-tag>
            case '2':
              return <a-tag>理科</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '招生分数',
        dataIndex: 'score',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '暂无信息'
          }
        },
        ellipsis: true
      }, {
        title: '所属年份',
        dataIndex: 'year',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '暂无信息'
          }
        },
        ellipsis: true
      }, {
        title: '招生人数',
        dataIndex: 'admissions',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '暂无信息'
          }
        },
        ellipsis: true
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: {customRender: 'operation'}
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    audit (scoreId, flag) {
      this.$post('/cos/score-info/score/audit', {scoreId, flag}).then((r) => {
        this.$message.success('修改成功！')
        this.fetch()
      })
    },
    handlescoreViewOpen (row) {
      this.scoreView.data = row
      this.scoreView.visiable = true
    },
    handlescoreViewClose () {
      this.scoreView.visiable = false
    },
    editStatus (row, status) {
      this.$post('/cos/score-info/account/status', { staffId: row.id, status }).then((r) => {
        this.$message.success('修改成功')
        this.fetch()
      })
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.scoreAdd.visiable = true
    },
    handlescoreAddClose () {
      this.scoreAdd.visiable = false
    },
    handlescoreAddSuccess () {
      this.scoreAdd.visiable = false
      this.$message.success('新增分数线成功')
      this.search()
    },
    edit (record) {
      this.$refs.scoreEdit.setFormValues(record)
      this.scoreEdit.visiable = true
    },
    handlescoreEditClose () {
      this.scoreEdit.visiable = false
    },
    handlescoreEditSuccess () {
      this.scoreEdit.visiable = false
      this.$message.success('修改产品成功')
      this.search()
    },
    handleDeptChange (value) {
      this.queryParams.deptId = value || ''
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let ids = that.selectedRowKeys.join(',')
          that.$delete('/cos/score-line-info/' + ids).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.size = this.paginationInfo.pageSize
        params.current = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.size = this.pagination.defaultPageSize
        params.current = this.pagination.defaultCurrent
      }
      if (params.type === undefined) {
        delete params.type
      }
      this.$get('/cos/score-line-info/page', {
        ...params
      }).then((r) => {
        let data = r.data.data
        const pagination = {...this.pagination}
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  },
  watch: {}
}
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
