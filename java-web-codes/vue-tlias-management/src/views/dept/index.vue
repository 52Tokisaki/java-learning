<script setup>

import { onMounted, ref } from "vue";
import { getDeptList } from "@/api/dept";

const deptList = ref([]);

const handleEdit = (id) => {
  console.log('修改部门', id);
};

const handleDelete = (id) => {
  console.log('删除部门', id);
};

const getTableData = async () => {
  const result = await getDeptList();
  deptList.value = result.data;
};

onMounted(() => {
  getTableData();
});
</script>

<template>
  <h1>部门管理</h1>
  <div style="margin: 20px 0"><el-button type="primary">新增部门</el-button></div>

  <el-table :data="deptList" border style="width: 100%;">
    <el-table-column type="index" label="序号" width="100" align="center"/>
    <el-table-column prop="name" label="部门名称" width="300" align="center"/>
    <el-table-column prop="updateTime" label="最后修改时间" width="300" align="center"/>
    <el-table-column fixed="right" label="操作" align="center">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row.id)">修改</el-button>
        <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>

</style>
