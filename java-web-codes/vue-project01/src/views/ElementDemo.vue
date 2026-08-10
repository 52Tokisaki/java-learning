<script setup>

import {ref} from "vue";

const tableData = [
  {date: '2016-05-03', name: 'Tom', address: 'No. 189, Grove St, Los Angeles'},
  {date: '2016-05-02', name: 'Tom', address: 'No. 189, Grove St, Los Angeles'},
  {date: '2016-05-04', name: 'Tom', address: 'No. 189, Grove St, Los Angeles'},
  {date: '2016-05-01', name: 'Tom', address: 'No. 189, Grove St, Los Angeles'}
];

const currentPage4 = ref(1);
const pageSize4 = ref('large');

const handleSizeChange = (val) => {
  console.log(`${val} items per page`);
};

const handleCurrentChange = (val) => {
  console.log(`current page: ${val}`);
};

const dialogTableVisible = ref(false);
</script>

<template>
  <el-row class="mb-4">
    <el-button>Default</el-button>
    <el-button type="primary">Primary</el-button>
    <el-button type="success">Success</el-button>
    <el-button type="info">Info</el-button>
    <el-button type="warning">Warning</el-button>
    <el-button type="danger">Danger</el-button>
  </el-row>

  <br>

  <!-- Table表格 -->
  <el-table :data="tableData" border class="mb-4" style="width: 100%">
    <el-table-column prop="date" label="Date" width="180" />
    <el-table-column prop="name" label="Name" width="180" />
    <el-table-column prop="address" label="Address" />
  </el-table>

  <el-pagination
      v-model:current-page="currentPage4"
      v-model:page-size="pageSize4"
      :page-sizes="[100, 200, 300, 400]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      class="mb-4"
  />

  <el-button @click="dialogTableVisible = true">
    打开对话框
  </el-button>

  <el-dialog v-model="dialogTableVisible" title="Shipping address">
    <el-table :data="tableData">
      <el-table-column property="date" label="Date" width="150" />
      <el-table-column property="name" label="Name" width="200" />
      <el-table-column property="address" label="Address" />
    </el-table>
  </el-dialog>
</template>

<style scoped>
.mb-4 {
  margin-bottom: 4px;
}
</style>