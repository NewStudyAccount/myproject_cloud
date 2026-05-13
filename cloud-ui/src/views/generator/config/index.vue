<template>
  <div>
    <el-form :model="queryParams" :inline="true">
      <el-form-item label="表名">
        <el-input v-model="queryParams.tableName" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList">
      <el-table-column label="ID" prop="id" width="80" />
      <el-table-column label="表名" prop="tableName" />
      <el-table-column label="表描述" prop="tableComment" />
      <el-table-column label="模块名" prop="moduleName" />
      <el-table-column label="功能名" prop="functionName" />
      <el-table-column label="作者" prop="author" />
      <el-table-column label="操作" width="250">
        <template #default="{ row }">
          <el-button link type="primary" icon="View" @click="handlePreview(row)">预览</el-button>
          <el-button link type="primary" icon="Download" @click="handleDownload(row)">下载</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(row)">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-show="total > 0" :total="total" v-model:current-page="queryParams.pageNum"
      v-model:page-size="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="表名" prop="tableName">
          <el-input v-model="form.tableName" />
        </el-form-item>
        <el-form-item label="表描述" prop="tableComment">
          <el-input v-model="form.tableComment" />
        </el-form-item>
        <el-form-item label="模块名" prop="moduleName">
          <el-input v-model="form.moduleName" />
        </el-form-item>
        <el-form-item label="功能名" prop="functionName">
          <el-input v-model="form.functionName" />
        </el-form-item>
        <el-form-item label="包名" prop="packageName">
          <el-input v-model="form.packageName" />
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="form.author" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </template>
    </el-dialog>

    <el-dialog title="代码预览" v-model="previewVisible" width="80%">
      <el-tabs v-model="previewTab">
        <el-tab-pane v-for="(_, name) in previewData" :key="name" :label="name as string" :name="name" />
      </el-tabs>
      <pre><code>{{ previewData[previewTab] }}</code></pre>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance } from 'element-plus'
import { listGenConfig, addGenConfig, updateGenConfig, deleteGenConfig, previewCode } from '@/api/generator/config'

const loading = ref(false)
const dataList = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref<FormInstance>()
const previewVisible = ref(false)
const previewData = ref<Record<string, string>>({})
const previewTab = ref('')

const queryParams = reactive({ pageNum: 1, pageSize: 10, tableName: '' })
const form = reactive({ id: undefined, tableName: '', tableComment: '', moduleName: '', functionName: '', packageName: 'com.project.cloud', author: '' })
const rules = { tableName: [{ required: true, message: '请输入表名', trigger: 'blur' }] }

const getList = async () => { loading.value = true; const res: any = await listGenConfig(queryParams); dataList.value = res.data.rows; total.value = res.data.total; loading.value = false }
const handleQuery = () => { queryParams.pageNum = 1; getList() }
const resetQuery = () => { queryParams.tableName = ''; handleQuery() }
const handleAdd = () => { Object.assign(form, { id: undefined, tableName: '', tableComment: '', moduleName: '', functionName: '', packageName: 'com.project.cloud', author: '' }); dialogTitle.value = '新增配置'; dialogVisible.value = true }
const handleUpdate = (row: any) => { Object.assign(form, row); dialogTitle.value = '修改配置'; dialogVisible.value = true }
const handleDelete = (row: any) => { ElMessageBox.confirm('确认删除?', '警告').then(async () => { await deleteGenConfig([row.id]); ElMessage.success('删除成功'); getList() }) }

const handlePreview = async (row: any) => {
  const res: any = await previewCode(row.id)
  previewData.value = res.data
  const keys = Object.keys(res.data)
  previewTab.value = keys[0] || ''
  previewVisible.value = true
}

const handleDownload = async (row: any) => {
  const res: any = await previewCode(row.id)
  ElMessage.success('下载功能需要后端支持')
}

const submitForm = async () => { await formRef.value?.validate(); form.id ? await updateGenConfig(form) : await addGenConfig(form); ElMessage.success('操作成功'); dialogVisible.value = false; getList() }

onMounted(() => getList())
</script>

<style scoped>
.mb8 { margin-bottom: 8px; }
pre { background: #f5f5f5; padding: 16px; border-radius: 4px; max-height: 500px; overflow: auto; }
</style>
