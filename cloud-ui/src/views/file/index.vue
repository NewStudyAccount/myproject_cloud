<template>
  <div>
    <el-form :model="queryParams" :inline="true">
      <el-form-item label="文件名">
        <el-input v-model="queryParams.fileName" placeholder="请输入" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-upload action="/api/file/upload" :headers="uploadHeaders" :on-success="handleUploadSuccess" :show-file-list="false">
          <el-button type="primary" plain icon="Upload">上传文件</el-button>
        </el-upload>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dataList">
      <el-table-column label="ID" prop="id" width="80" />
      <el-table-column label="文件名" prop="fileName" />
      <el-table-column label="文件类型" prop="fileType" width="120" />
      <el-table-column label="文件大小" prop="fileSize" width="120">
        <template #default="{ row }">
          {{ formatSize(row.fileSize) }}
        </template>
      </el-table-column>
      <el-table-column label="存储类型" prop="storageType" width="100" />
      <el-table-column label="预览" width="100">
        <template #default="{ row }">
          <el-link type="primary" :href="row.fileUrl" target="_blank">查看</el-link>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="180" />
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button link type="primary" icon="Delete" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination v-show="total > 0" :total="total" v-model:current-page="queryParams.pageNum"
      v-model:page-size="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listFile, deleteFile } from '@/api/file/file'

const loading = ref(false)
const dataList = ref([])
const total = ref(0)

const queryParams = reactive({ pageNum: 1, pageSize: 10, fileName: '' })

const uploadHeaders = computed(() => ({
  Authorization: `Bearer ${localStorage.getItem('token')}`,
}))

const getList = async () => {
  loading.value = true
  const res: any = await listFile(queryParams)
  dataList.value = res.data.rows
  total.value = res.data.total
  loading.value = false
}

const handleQuery = () => { queryParams.pageNum = 1; getList() }
const resetQuery = () => { queryParams.fileName = ''; handleQuery() }

const handleUploadSuccess = (res: any) => {
  ElMessage.success('上传成功')
  getList()
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm('确认删除?', '警告').then(async () => {
    await deleteFile([row.id])
    ElMessage.success('删除成功')
    getList()
  })
}

const formatSize = (size: number) => {
  if (size < 1024) return size + ' B'
  if (size < 1024 * 1024) return (size / 1024).toFixed(1) + ' KB'
  if (size < 1024 * 1024 * 1024) return (size / 1024 / 1024).toFixed(1) + ' MB'
  return (size / 1024 / 1024 / 1024).toFixed(1) + ' GB'
}

onMounted(() => getList())
</script>

<style scoped>.mb8 { margin-bottom: 8px; }</style>
