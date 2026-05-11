<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="菜单名称" prop="menuName">
        <el-input v-model="queryParams.menuName" placeholder="请输入菜单名称" clearable style="width: 200px" />
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
        <el-button type="primary" plain icon="Plus" @click="handleAdd()">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="Sort" @click="toggleExpandAll">展开/折叠</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" />
    </el-row>

    <!-- 数据表格 -->
    <el-table v-if="refreshTable" v-loading="loading" :data="menuList" row-key="id" :default-expand-all="isExpandAll"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
      <el-table-column label="菜单名称" prop="menuName" width="200" />
      <el-table-column label="图标" align="center" prop="icon" width="80">
        <template #default="scope">
          <el-icon v-if="scope.row.icon"><component :is="scope.row.icon" /></el-icon>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort" width="80" />
      <el-table-column label="权限标识" align="center" prop="perms" />
      <el-table-column label="组件路径" align="center" prop="component" />
      <el-table-column label="状态" align="center" width="80">
        <template #default="scope">
          <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'">
            {{ scope.row.status === 0 ? '正常' : '停用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button link type="primary" icon="Plus" @click="handleAdd(scope.row)">新增</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加/修改对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="menuRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="上级菜单" prop="parentId">
              <el-tree-select v-model="form.parentId" :data="menuOptions"
                :props="{ value: 'id', label: 'menuName', children: 'children' }" value-key="id"
                placeholder="选择上级菜单" check-strictly clearable style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单类型" prop="menuType">
              <el-radio-group v-model="form.menuType">
                <el-radio value="M">目录</el-radio>
                <el-radio value="C">菜单</el-radio>
                <el-radio value="F">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="menuName">
              <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="sort">
              <el-input-number v-model="form.sort" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType !== 'F'">
            <el-form-item label="路由地址" prop="path">
              <el-input v-model="form.path" placeholder="请输入路由地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType === 'C'">
            <el-form-item label="组件路径" prop="component">
              <el-input v-model="form.component" placeholder="请输入组件路径" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType !== 'M'">
            <el-form-item label="权限标识" prop="perms">
              <el-input v-model="form.perms" placeholder="请输入权限标识" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单图标" prop="icon">
              <el-input v-model="form.icon" placeholder="请输入菜单图标" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio :value="0">正常</el-radio>
                <el-radio :value="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
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
import { ref, onMounted, nextTick } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import type { FormInstance } from 'element-plus'
import { listMenu, treeMenu, detailMenu, addMenu, updateMenu, deleteMenu } from '@/api/system/menu'

const loading = ref(false)
const menuList = ref([])
const menuOptions = ref([])
const showSearch = ref(true)
const open = ref(false)
const title = ref('')
const isExpandAll = ref(true)
const refreshTable = ref(true)
const queryRef = ref<FormInstance>()
const menuRef = ref<FormInstance>()

const queryParams = ref({
  menuName: '',
  status: undefined
})

const form = ref<any>({})

const rules = {
  menuName: [{ required: true, message: '菜单名称不能为空', trigger: 'blur' }],
  menuType: [{ required: true, message: '菜单类型不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '排序不能为空', trigger: 'blur' }]
}

onMounted(() => {
  getList()
})

const getList = async () => {
  loading.value = true
  try {
    const res: any = await treeMenu()
    menuList.value = res.data
  } finally {
    loading.value = false
  }
}

const getTreeSelect = async () => {
  const res: any = await treeMenu()
  menuOptions.value = [{ id: 0, menuName: '主类', children: res.data }]
}

const handleQuery = () => {
  getList()
}

const resetQuery = () => {
  queryRef.value?.resetFields()
  handleQuery()
}

const toggleExpandAll = () => {
  refreshTable.value = false
  isExpandAll.value = !isExpandAll.value
  nextTick(() => {
    refreshTable.value = true
  })
}

const handleAdd = (row?: any) => {
  reset()
  getTreeSelect()
  if (row && row.id) {
    form.value.parentId = row.id
  }
  open.value = true
  title.value = '添加菜单'
}

const handleUpdate = async (row: any) => {
  reset()
  getTreeSelect()
  const res: any = await detailMenu({ id: row.id })
  form.value = res.data
  open.value = true
  title.value = '修改菜单'
}

const submitForm = () => {
  menuRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        if (form.value.id) {
          await updateMenu(form.value)
        } else {
          await addMenu(form.value)
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

const handleDelete = async (row: any) => {
  await ElMessageBox.confirm('是否确认删除选中的菜单？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await deleteMenu(row.id)
  ElMessage.success('删除成功')
  getList()
}

const reset = () => {
  form.value = {
    id: undefined,
    parentId: 0,
    menuName: '',
    menuType: 'M',
    sort: 0,
    path: '',
    component: '',
    perms: '',
    icon: '',
    status: 0
  }
}

const cancel = () => {
  open.value = false
  reset()
}
</script>
