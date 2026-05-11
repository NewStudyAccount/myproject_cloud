<template>
  <div class="right-toolbar">
    <el-row :gutter="10">
      <el-col :span="1.5">
        <el-tooltip content="显隐搜索" placement="top">
          <el-button circle icon="Search" @click="toggleSearch" />
        </el-tooltip>
      </el-col>
      <el-col :span="1.5">
        <el-tooltip content="刷新" placement="top">
          <el-button circle icon="Refresh" @click="refresh" />
        </el-tooltip>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
const props = defineProps({
  showSearch: {
    type: Boolean,
    default: true
  }
})

const emit = defineEmits(['update:showSearch', 'queryTable'])

const toggleSearch = () => {
  emit('update:showSearch', !props.showSearch)
}

const refresh = () => {
  emit('queryTable')
}
</script>

<style scoped>
.right-toolbar {
  float: right;
  margin-bottom: 10px;
}
</style>
