<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="queryParams.username" placeholder="请输入用户名" clearable style="width: 200px" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入手机号" clearable style="width: 200px" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 200px">
          <el-option label="正常" :value="1" />
          <el-option label="停用" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete">删除</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户ID" align="center" prop="id" width="100" />
      <el-table-column label="用户名" align="center" prop="username" />
      <el-table-column label="昵称" align="center" prop="nickname" />
      <el-table-column label="手机号" align="center" prop="phone" width="120" />
      <el-table-column label="状态" align="center" width="80">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '正常' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加/修改对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="userRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!form.id">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="1">正常</el-radio>
            <el-radio :value="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import type { FormInstance } from 'element-plus'
import { listUser, detailUser, addUser, updateUser, deleteUser } from '@/api/system/user'

const loading = ref(false)
const userList = ref([])
const total = ref(0)
const showSearch = ref(true)
const open = ref(false)
const title = ref('')
const ids = ref<number[]>([])
const single = ref(true)
const multiple = ref(true)
const queryRef = ref<FormInstance>()
const userRef = ref<FormInstance>()

const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  username: '',
  phone: '',
  status: undefined
})

const form = ref<any>({})

const rules = {
  username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
  nickname: [{ required: true, message: '昵称不能为空', trigger: 'blur' }]
}

onMounted(() => {
  getList()
})

const getList = async () => {
  loading.value = true
  try {
    const res: any = await listUser(queryParams.value)
    userList.value = res.rows
    total.value = res.total
  } finally {
    loading.value = false
  }
}

const handleQuery = () => {
  queryParams.value.pageNum = 1
  getList()
}

const resetQuery = () => {
  queryRef.value?.resetFields()
  handleQuery()
}

const handleSelectionChange = (selection: any[]) => {
  ids.value = selection.map((item: any) => item.id)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

const handleAdd = () => {
  reset()
  open.value = true
  title.value = '添加用户'
}

const handleUpdate = async (row: any) => {
  reset()
  const res: any = await detailUser({ id: row.id })
  form.value = res.data
  open.value = true
  title.value = '修改用户'
}

const submitForm = () => {
  userRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        if (form.value.id) {
          await updateUser(form.value)
        } else {
          await addUser(form.value)
        }
        ElMessage.success('操作成功')
        open.value = false
        getList()
      } catch (error) {
        console.error(error)
      }
    }
  })
}

const handleDelete = async (row?: any) => {
  const deleteIds = row?.id ? [row.id] : ids.value
  await ElMessageBox.confirm('是否确认删除选中的用户？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await deleteUser(deleteIds)
  ElMessage.success('删除成功')
  getList()
}

const reset = () => {
  form.value = {
    id: undefined,
    username: '',
    password: '',
    nickname: '',
    phone: '',
    email: '',
    status: 1
  }
}

const cancel = () => {
  open.value = false
  reset()
}
</script>
