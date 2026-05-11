<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch">
      <el-form-item label="字典名称" prop="dictName">
        <el-input v-model="queryParams.dictName" placeholder="请输入字典名称" clearable style="width: 200px" />
      </el-form-item>
      <el-form-item label="字典类型" prop="dictType">
        <el-input v-model="queryParams.dictType" placeholder="请输入字典类型" clearable style="width: 200px" />
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
    <el-table v-loading="loading" :data="dictTypeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="字典ID" align="center" prop="id" width="100" />
      <el-table-column label="字典名称" align="center" prop="dictName" />
      <el-table-column label="字典类型" align="center" prop="dictType">
        <template #default="scope">
          <el-link type="primary" @click="handleDictData(scope.row)">{{ scope.row.dictType }}</el-link>
        </template>
      </el-table-column>
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
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
          <el-button link type="primary" icon="View" @click="handleDictData(scope.row)">数据</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加/修改对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="dictTypeRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="字典名称" prop="dictName">
          <el-input v-model="form.dictName" placeholder="请输入字典名称" />
        </el-form-item>
        <el-form-item label="字典类型" prop="dictType">
          <el-input v-model="form.dictType" placeholder="请输入字典类型" />
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

    <!-- 字典数据对话框 -->
    <el-dialog :title="dictDataTitle" v-model="dictDataOpen" width="800px" append-to-body>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="primary" plain icon="Plus" @click="handleAddDictData">新增</el-button>
        </el-col>
      </el-row>

      <el-table v-loading="dictDataLoading" :data="dictDataList">
        <el-table-column label="字典标签" align="center" prop="dictLabel" />
        <el-table-column label="字典值" align="center" prop="dictValue" />
        <el-table-column label="排序" align="center" prop="sort" width="80" />
        <el-table-column label="状态" align="center" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'">
              {{ scope.row.status === 0 ? '正常' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
          <template #default="scope">
            <el-button link type="primary" icon="Edit" @click="handleUpdateDictData(scope.row)">修改</el-button>
            <el-button link type="primary" icon="Delete" @click="handleDeleteDictData(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!-- 字典数据添加/修改对话框 -->
    <el-dialog :title="dictDataFormTitle" v-model="dictDataFormOpen" width="500px" append-to-body>
      <el-form ref="dictDataRef" :model="dictDataForm" :rules="dictDataRules" label-width="100px">
        <el-form-item label="字典标签" prop="dictLabel">
          <el-input v-model="dictDataForm.dictLabel" placeholder="请输入字典标签" />
        </el-form-item>
        <el-form-item label="字典值" prop="dictValue">
          <el-input v-model="dictDataForm.dictValue" placeholder="请输入字典值" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="dictDataForm.sort" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="dictDataForm.status">
            <el-radio :value="0">正常</el-radio>
            <el-radio :value="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitDictDataForm">确 定</el-button>
          <el-button @click="cancelDictData">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import type { FormInstance } from 'element-plus'
import {
  listDictType, detailDictType, addDictType, updateDictType, deleteDictType,
  listDictData, detailDictData, addDictData, updateDictData, deleteDictData
} from '@/api/system/dict'

const loading = ref(false)
const dictTypeList = ref([])
const total = ref(0)
const showSearch = ref(true)
const open = ref(false)
const title = ref('')
const ids = ref<number[]>([])
const single = ref(true)
const multiple = ref(true)
const queryRef = ref<FormInstance>()
const dictTypeRef = ref<FormInstance>()

const queryParams = ref({
  pageNum: 1,
  pageSize: 10,
  dictName: '',
  dictType: '',
  status: undefined
})

const form = ref<any>({})

const rules = {
  dictName: [{ required: true, message: '字典名称不能为空', trigger: 'blur' }],
  dictType: [{ required: true, message: '字典类型不能为空', trigger: 'blur' }]
}

// 字典数据相关
const dictDataLoading = ref(false)
const dictDataList = ref([])
const dictDataOpen = ref(false)
const dictDataTitle = ref('')
const currentDictType = ref('')
const dictDataFormOpen = ref(false)
const dictDataFormTitle = ref('')
const dictDataRef = ref<FormInstance>()
const dictDataForm = ref<any>({})

const dictDataRules = {
  dictLabel: [{ required: true, message: '字典标签不能为空', trigger: 'blur' }],
  dictValue: [{ required: true, message: '字典值不能为空', trigger: 'blur' }]
}

onMounted(() => {
  getList()
})

const getList = async () => {
  loading.value = true
  try {
    const res: any = await listDictType(queryParams.value)
    dictTypeList.value = res.rows
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
  title.value = '添加字典类型'
}

const handleUpdate = async (row: any) => {
  reset()
  const res: any = await detailDictType({ id: row.id })
  form.value = res.data
  open.value = true
  title.value = '修改字典类型'
}

const submitForm = () => {
  dictTypeRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        if (form.value.id) {
          await updateDictType(form.value)
        } else {
          await addDictType(form.value)
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
  await ElMessageBox.confirm('是否确认删除选中的字典类型？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await deleteDictType(deleteIds)
  ElMessage.success('删除成功')
  getList()
}

const reset = () => {
  form.value = {
    id: undefined,
    dictName: '',
    dictType: '',
    status: 0
  }
}

const cancel = () => {
  open.value = false
  reset()
}

// 字典数据相关方法
const handleDictData = async (row: any) => {
  currentDictType.value = row.dictType
  dictDataTitle.value = `字典数据 - ${row.dictName}`
  dictDataOpen.value = true
  await getDictDataList()
}

const getDictDataList = async () => {
  dictDataLoading.value = true
  try {
    const res: any = await listDictData({ dictType: currentDictType.value })
    dictDataList.value = res.rows
  } finally {
    dictDataLoading.value = false
  }
}

const handleAddDictData = () => {
  resetDictDataForm()
  dictDataForm.value.dictType = currentDictType.value
  dictDataFormOpen.value = true
  dictDataFormTitle.value = '添加字典数据'
}

const handleUpdateDictData = async (row: any) => {
  resetDictDataForm()
  const res: any = await detailDictData({ id: row.id })
  dictDataForm.value = res.data
  dictDataFormOpen.value = true
  dictDataFormTitle.value = '修改字典数据'
}

const submitDictDataForm = () => {
  dictDataRef.value?.validate(async (valid) => {
    if (valid) {
      try {
        if (dictDataForm.value.id) {
          await updateDictData(dictDataForm.value)
        } else {
          await addDictData(dictDataForm.value)
        }
        ElMessage.success('操作成功')
        dictDataFormOpen.value = false
        getDictDataList()
      } catch (error) {
        console.error(error)
      }
    }
  })
}

const handleDeleteDictData = async (row: any) => {
  await ElMessageBox.confirm('是否确认删除选中的字典数据？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
  await deleteDictData([row.id])
  ElMessage.success('删除成功')
  getDictDataList()
}

const resetDictDataForm = () => {
  dictDataForm.value = {
    id: undefined,
    dictType: '',
    dictLabel: '',
    dictValue: '',
    sort: 0,
    status: 0
  }
}

const cancelDictData = () => {
  dictDataFormOpen.value = false
  resetDictDataForm()
}
</script>
