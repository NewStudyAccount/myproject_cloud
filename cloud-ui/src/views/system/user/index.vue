<template>
  <div>
    <el-form :model="queryParams" :inline="true">
      <el-form-item label="用户名">
        <el-input v-model="queryParams.username" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.status" placeholder="请选择" clearable>
          <el-option label="正常" :value="0" />
          <el-option label="停用" :value="1" />
        </el-select>
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
      <el-table-column label="用户名" prop="username" />
      <el-table-column label="昵称" prop="nickname" />
      <el-table-column label="手机号" prop="phone" />
      <el-table-column label="状态" prop="status">
        <template #default="{ row }">
          <el-tag :type="row.status === 0 ? 'success' : 'danger'">
            {{ row.status === 0 ? '正常' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="180" />
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(row)">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      v-show="total > 0"
      :total="total"
      v-model:current-page="queryParams.pageNum"
      v-model:page-size="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" :disabled="!!form.id" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="0">正常</el-radio>
            <el-radio :value="1">停用</el-radio>
          </el-radio-group>
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
import { listUser, addUser, updateUser, deleteUser } from '@/api/system/user'

const loading = ref(false)
const dataList = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref<FormInstance>()

const queryParams = reactive({ pageNum: 1, pageSize: 10, username: '', status: undefined })
const form = reactive({ id: undefined, username: '', nickname: '', phone: '', email: '', status: 0 })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
}

const getList = async () => {
  loading.value = true
  const res: any = await listUser(queryParams)
  dataList.value = res.data.rows
  total.value = res.data.total
  loading.value = false
}

const handleQuery = () => { queryParams.pageNum = 1; getList() }
const resetQuery = () => { queryParams.username = ''; queryParams.status = undefined; handleQuery() }

const handleAdd = () => {
  Object.assign(form, { id: undefined, username: '', nickname: '', phone: '', email: '', status: 0 })
  dialogTitle.value = '新增用户'
  dialogVisible.value = true
}

const handleUpdate = (row: any) => {
  Object.assign(form, row)
  dialogTitle.value = '修改用户'
  dialogVisible.value = true
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm('确认删除该用户?', '警告').then(async () => {
    await deleteUser([row.id])
    ElMessage.success('删除成功')
    getList()
  })
}

const submitForm = async () => {
  await formRef.value?.validate()
  if (form.id) {
    await updateUser(form)
  } else {
    await addUser(form)
  }
  ElMessage.success('操作成功')
  dialogVisible.value = false
  getList()
}

onMounted(() => getList())
</script>

<style scoped>
.mb8 { margin-bottom: 8px; }
</style>
