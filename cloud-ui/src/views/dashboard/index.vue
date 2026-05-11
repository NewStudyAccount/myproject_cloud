<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>用户数量</span>
              <el-icon><User /></el-icon>
            </div>
          </template>
          <div class="card-value">1,234</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>角色数量</span>
              <el-icon><UserFilled /></el-icon>
            </div>
          </template>
          <div class="card-value">56</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>菜单数量</span>
              <el-icon><Menu /></el-icon>
            </div>
          </template>
          <div class="card-value">128</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>在线用户</span>
              <el-icon><Monitor /></el-icon>
            </div>
          </template>
          <div class="card-value">89</div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>系统信息</span>
          </template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="系统名称">Project Cloud</el-descriptions-item>
            <el-descriptions-item label="系统版本">1.0.0</el-descriptions-item>
            <el-descriptions-item label="Spring Boot">3.5.x</el-descriptions-item>
            <el-descriptions-item label="Spring Cloud">2025.0.0</el-descriptions-item>
            <el-descriptions-item label="Java 版本">21</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>快捷操作</span>
          </template>
          <div class="quick-links">
            <el-button type="primary" @click="$router.push('/system/user')">用户管理</el-button>
            <el-button type="success" @click="$router.push('/system/role')">角色管理</el-button>
            <el-button type="warning" @click="$router.push('/system/menu')">菜单管理</el-button>
            <el-button type="info" @click="$router.push('/system/dict')">字典管理</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-value {
  font-size: 32px;
  font-weight: bold;
  color: #409EFF;
  text-align: center;
}

.quick-links {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}
</style>
