<script setup>

import MyTable from "@/components/MyTable.vue";
import { User } from '@element-plus/icons-vue';

const getJobName = (jobCode) => {
  const map = {
    1: '班主任',
    2: '教师',
    3: '咨询师'
  };
  return map[jobCode] || '其他';
};

const handleAvatarError = () => {
  // 头像加载失败处理
  return true; // 使用默认图标
};
</script>

<template>
  <MyTable>
    <!-- 性别列自定义 -->
    <template #gender="{ row }">
      <el-tag :type="row.gender === 1 ? 'primary' : 'success'">
        {{ row.gender === 1 ? '男' : '女' }}
      </el-tag>
    </template>

    <!-- 职位列自定义 -->
    <template #job="{ row }">
      <el-tag type="warning">
        {{ getJobName(row.job) }}
      </el-tag>
    </template>

    <!-- 头像列自定义 -->
    <template #image="{ row }">
      <el-avatar
          :size="50"
          :src="row.image"
          @error="handleAvatarError"
      >
        <el-icon><User /></el-icon>
      </el-avatar>
    </template>
  </MyTable>
</template>

<style scoped>

</style>