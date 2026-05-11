<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="queryParams.roleName" placeholder="请输入角色名称" clearable style="width: 200px" />
      </el-form-item>
      <el-form-item label="权限字符" prop="roleKey">
        <el-input v-model="queryParams.roleKey" placeholder="请输入权限字符" clearable style="width: 200px" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 200px">
          <el-option label="正常" :value="0" />
          <el-option label="停用" :value="1" />
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
    <el-table v-loading="loading" :data="roleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="角色ID" align="center" prop="id" width="100" />
      <el-table-column label="角色名称" align="center" prop="roleName" />
      <el-table-column label="权限字符" align="center" prop="roleKey" />
      <el-table-column label="排序" align="center" prop="sort" width="80" />
      <el-table-column label="状态" align="center" width="80">
        <template #default="scope">
          <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'">
            {{ scope.row.status === 0 ? '正常' : '停用' }}
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
      <el-form ref="roleRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="权限字符" prop="roleKey">
          <el-input v-model="form.roleKey" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :value="0">正常</el-radio>
            <el-radio :value="1">停用</el-radio>
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
import { listRole, detailRole, addRole, updateRole, deleteRole } from '@/api/system/role'

const loading = ref(false)
const roleList = ref([])
const total = ref(0)
const showSearch = ref(true)
const open = ref(false)
const title = ref('')
const ids = ref<number[]>([])
const single = ref(true)
const multiple = ref(true)
const queryRef = ref<FormInstance>()
const roleRef = ref<FormInstance>()

const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  roleName: '',
  roleKey: '',
  status: undefined
})

const form = ref<any>({})

const rules = {
  roleName: [{ required: true, message: '角色名称不能为空', trigger: 'blur' }],
  roleKey: [{ required: true, message: '权限字符不能为空', trigger: 'blur' }]
}

onMounted(() => {
  getList()
})

const getList = async () => {
  loading.value = true
  try {
    const res: any = await listRole(queryParams.value)
    roleList.value = res.rows
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
  title.value = '添加角色'
}

const handleUpdate = async (row: any) => {
  reset()
  const res: any = await detailRole({ id: row.id })
  form.value = res.data
  open.value = true
  title.value = '修改角色'
}

const submitForm = () => {
  roleRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        if (form.value.id) {
          await updateRole(form.value)
        } else {
          await addRole(form.value)
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
  await ElMessageBox.confirm('是否确认删除选中的角色？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await deleteRole(deleteIds)
  ElMessage.success('删除成功')
  getList()
}

const reset = () => {
  form.value = {
    id: undefined,
    roleName: '',
    roleKey: '',
    sort: 0,
    status: 0
  }
}

const cancel = () => {
  open.value = false
  reset()
}
</script>
