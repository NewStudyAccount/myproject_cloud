<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>用户数量</template>
          <div class="stat-value">1,024</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>角色数量</template>
          <div class="stat-value">12</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>菜单数量</template>
          <div class="stat-value">64</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>文件数量</template>
          <div class="stat-value">512</div>
        </el-card>
      </el-col>
    </el-row>
    <el-card class="mt-20">
      <template #header>欢迎使用 Project Cloud 微服务脚手架</template>
      <p>技术栈：Spring Boot 3.5 + Spring Cloud 2025 + Vue3 + Element Plus</p>
    </el-card>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #409eff;
  text-align: center;
}
.mt-20 {
  margin-top: 20px;
}
</style>
