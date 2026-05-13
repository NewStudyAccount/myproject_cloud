<template>
  <div>
    <el-form :model="queryParams" :inline="true">
      <el-form-item label="字典名称">
        <el-input v-model="queryParams.dictName" placeholder="请输入" clearable />
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
      <el-table-column label="字典名称" prop="dictName" />
      <el-table-column label="字典类型" prop="dictType">
        <template #default="{ row }">
          <el-link type="primary" @click="handleDictData(row)">{{ row.dictType }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="status">
        <template #default="{ row }">
          <el-tag :type="row.status === 0 ? 'success' : 'danger'">{{ row.status === 0 ? '正常' : '停用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(row)">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-show="total > 0" :total="total" v-model:current-page="queryParams.pageNum"
      v-model:page-size="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="字典名称" prop="dictName">
          <el-input v-model="form.dictName" />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input v-model="form.dictType" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="0">正常</el-radio>
            <el-radio :value="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance } from 'element-plus'
import { listDictType, addDictType, updateDictType, deleteDictType } from '@/api/system/dict'
import { useRouter } from 'vue-router'

const router = useRouter()
const loading = ref(false)
const dataList = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref<FormInstance>()

const queryParams = reactive({ pageNum: 1, pageSize: 10, dictName: '' })
const form = reactive({ id: undefined, dictName: '', dictType: '', status: 0, remark: '' })
const rules = { dictName: [{ required: true, message: '请输入字典名称', trigger: 'blur' }], dictType: [{ required: true, message: '请输入字典类型', trigger: 'blur' }] }

const getList = async () => {
  loading.value = true
  const res: any = await listDictType(queryParams)
  dataList.value = res.data.rows
  total.value = res.data.total
  loading.value = false
}

const handleQuery = () => { queryParams.pageNum = 1; getList() }
const resetQuery = () => { queryParams.dictName = ''; handleQuery() }
const handleAdd = () => { Object.assign(form, { id: undefined, dictName: '', dictType: '', status: 0, remark: '' }); dialogTitle.value = '新增字典类型'; dialogVisible.value = true }
const handleUpdate = (row: any) => { Object.assign(form, row); dialogTitle.value = '修改字典类型'; dialogVisible.value = true }
const handleDelete = (row: any) => { ElMessageBox.confirm('确认删除?', '警告').then(async () => { await deleteDictType([row.id]); ElMessage.success('删除成功'); getList() }) }
const handleDictData = (row: any) => { ElMessage.info('字典数据: ' + row.dictType) }
const submitForm = async () => { await formRef.value?.validate(); form.id ? await updateDictType(form) : await addDictType(form); ElMessage.success('操作成功'); dialogVisible.value = false; getList() }

onMounted(() => getList())
</script>

<style scoped>.mb8 { margin-bottom: 8px; }</style>
