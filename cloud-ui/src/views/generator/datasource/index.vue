<template>
  <div>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList">
      <el-table-column label="ID" prop="id" width="80" />
      <el-table-column label="名称" prop="name" />
      <el-table-column label="连接地址" prop="url" show-overflow-tooltip />
      <el-table-column label="用户名" prop="username" />
      <el-table-column label="数据库类型" prop="dbType" />
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
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="连接地址" prop="url">
          <el-input v-model="form.url" placeholder="jdbc:mysql://localhost:3306/db" />
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="数据库类型">
          <el-select v-model="form.dbType">
            <el-option label="MySQL" value="mysql" />
            <el-option label="PostgreSQL" value="postgresql" />
          </el-select>
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
import { listDatasource, addDatasource, updateDatasource, deleteDatasource } from '@/api/generator/datasource'

const loading = ref(false)
const dataList = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref<FormInstance>()

const queryParams = reactive({ pageNum: 1, pageSize: 10 })
const form = reactive({ id: undefined, name: '', url: '', username: '', password: '', dbType: 'mysql', status: 0 })
const rules = { name: [{ required: true, message: '请输入名称', trigger: 'blur' }], url: [{ required: true, message: '请输入连接地址', trigger: 'blur' }], username: [{ required: true, message: '请输入用户名', trigger: 'blur' }], password: [{ required: true, message: '请输入密码', trigger: 'blur' }] }

const getList = async () => { loading.value = true; const res: any = await listDatasource(queryParams); dataList.value = res.data.rows; total.value = res.data.total; loading.value = false }
const handleAdd = () => { Object.assign(form, { id: undefined, name: '', url: '', username: '', password: '', dbType: 'mysql', status: 0 }); dialogTitle.value = '新增数据源'; dialogVisible.value = true }
const handleUpdate = (row: any) => { Object.assign(form, row); dialogTitle.value = '修改数据源'; dialogVisible.value = true }
const handleDelete = (row: any) => { ElMessageBox.confirm('确认删除?', '警告').then(async () => { await deleteDatasource([row.id]); ElMessage.success('删除成功'); getList() }) }
const submitForm = async () => { await formRef.value?.validate(); form.id ? await updateDatasource(form) : await addDatasource(form); ElMessage.success('操作成功'); dialogVisible.value = false; getList() }

onMounted(() => getList())
</script>

<style scoped>.mb8 { margin-bottom: 8px; }</style>
