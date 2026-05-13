<template>
  <div>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="Sort" @click="toggleExpand">展开/折叠</el-button>
      </el-col>
    </el-row>

    <el-table v-if="refreshTable" v-loading="loading" :data="dataList" row-key="id" :default-expand-all="isExpand"
      :tree-props="{ children: 'children' }">
      <el-table-column label="菜单名称" prop="menuName" />
      <el-table-column label="图标" prop="icon" width="80">
        <template #default="{ row }">
          <el-icon v-if="row.icon"><component :is="row.icon" /></el-icon>
        </template>
      </el-table-column>
      <el-table-column label="排序" prop="sort" width="80" />
      <el-table-column label="权限标识" prop="perms" />
      <el-table-column label="组件路径" prop="component" />
      <el-table-column label="类型" prop="menuType" width="80">
        <template #default="{ row }">
          <el-tag v-if="row.menuType === 'M'">目录</el-tag>
          <el-tag type="success" v-else-if="row.menuType === 'C'">菜单</el-tag>
          <el-tag type="warning" v-else-if="row.menuType === 'F'">按钮</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="status" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 0 ? 'success' : 'danger'">{{ row.status === 0 ? '正常' : '停用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(row)">修改</el-button>
          <el-button link type="primary" icon="Plus" @click="handleAdd(row)">新增</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="上级菜单">
          <el-tree-select v-model="form.parentId" :data="menuOptions" :props="{ label: 'menuName', value: 'id', children: 'children' }"
            check-strictly placeholder="选择上级菜单" clearable />
        </el-form-item>
        <el-form-item label="菜单类型" prop="menuType">
          <el-radio-group v-model="form.menuType">
            <el-radio value="M">目录</el-radio>
            <el-radio value="C">菜单</el-radio>
            <el-radio value="F">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="form.menuName" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
        <el-form-item v-if="form.menuType !== 'F'" label="路由地址" prop="path">
          <el-input v-model="form.path" />
        </el-form-item>
        <el-form-item v-if="form.menuType === 'C'" label="组件路径" prop="component">
          <el-input v-model="form.component" />
        </el-form-item>
        <el-form-item v-if="form.menuType !== 'M'" label="权限标识">
          <el-input v-model="form.perms" />
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
import { ref, reactive, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance } from 'element-plus'
import { menuTree, addMenu, updateMenu, deleteMenu } from '@/api/system/menu'

const loading = ref(false)
const dataList = ref([])
const menuOptions = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref<FormInstance>()
const isExpand = ref(true)
const refreshTable = ref(true)

const form = reactive({ id: undefined, parentId: 0, menuName: '', menuType: 'M', sort: 0, path: '', component: '', perms: '', status: 0 })
const rules = { menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }], menuType: [{ required: true, message: '请选择菜单类型', trigger: 'change' }] }

const getList = async () => {
  loading.value = true
  const res: any = await menuTree()
  dataList.value = res.data
  menuOptions.value = [{ id: 0, menuName: '主类目', children: res.data }]
  loading.value = false
}

const toggleExpand = () => {
  refreshTable.value = false
  isExpand.value = !isExpand.value
  nextTick(() => { refreshTable.value = true })
}

const handleAdd = (row?: any) => {
  Object.assign(form, { id: undefined, parentId: row?.id || 0, menuName: '', menuType: 'C', sort: 0, path: '', component: '', perms: '', status: 0 })
  dialogTitle.value = '新增菜单'
  dialogVisible.value = true
}

const handleUpdate = (row: any) => {
  Object.assign(form, row)
  dialogTitle.value = '修改菜单'
  dialogVisible.value = true
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm('确认删除?', '警告').then(async () => {
    await deleteMenu(row.id)
    ElMessage.success('删除成功')
    getList()
  })
}

const submitForm = async () => {
  await formRef.value?.validate()
  form.id ? await updateMenu(form) : await addMenu(form)
  ElMessage.success('操作成功')
  dialogVisible.value = false
  getList()
}

onMounted(() => getList())
</script>

<style scoped>.mb8 { margin-bottom: 8px; }</style>
