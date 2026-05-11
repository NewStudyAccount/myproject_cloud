<template>
  <div :class="{ hidden: hidden }" class="pagination-container">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :background="background"
      :layout="layout"
      :page-sizes="pageSizes"
      :pager-count="pagerCount"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps({
  total: {
    type: Number,
    required: true
  },
  page: {
    type: Number,
    default: 1
  },
  limit: {
    type: Number,
    default: 10
  },
  pageSizes: {
    type: Array,
    default: () => [10, 20, 30, 50]
  },
  pagerCount: {
    type: Number,
    default: 5
  },
  layout: {
    type: String,
    default: 'total, sizes, prev, pager, next, jumper'
  },
  background: {
    type: Boolean,
    default: true
  },
  autoScroll: {
    type: Boolean,
    default: true
  },
  hidden: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:page', 'update:limit', 'pagination'])

const currentPage = computed({
  get() {
    return props.page
  },
  set(val) {
    emit('update:page', val)
  }
})

const pageSize = computed({
  get() {
    return props.limit
  },
  set(val) {
    emit('update:limit', val)
  }
})

const handleSizeChange = (val: number) => {
  if (currentPage.value * val > props.total) {
    currentPage.value = 1
  }
  emit('pagination', { page: currentPage.value, limit: val })
}

const handleCurrentChange = (val: number) => {
  emit('pagination', { page: val, limit: pageSize.value })
}
</script>

<style scoped>
.pagination-container {
  background: #fff;
  padding: 32px 16px;
}

.pagination-container.hidden {
  display: none;
}
</style>
