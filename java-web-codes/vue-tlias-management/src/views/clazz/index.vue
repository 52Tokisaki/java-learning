<script setup>


import { onMounted, ref, watch } from "vue";
import { getClazzList } from "@/api/clazz";

const searchClazz = ref({
  name: '',
  begin: '',
  end: '',
  date: [],
  page: 1,
  pageSize: 10
});

watch(() => searchClazz.value.date, (newVal, oldValue) => {
  if (newVal.length === 2) {
    searchClazz.value.begin = newVal[0];
    searchClazz.value.end = newVal[1];
  } else {
    searchClazz.value.begin = '';
    searchClazz.value.end = '';
  }
});

// 分页配置
const pageSize = ref(10);
const currentPage = ref(1);
const total = ref(0);

// 分页处理
const handleSizeChange = (val) => {
  searchClazz.value.pageSize = val;
  search();
};
const handleCurrentChange = (val) => {
  searchClazz.value.page = val;
  search();
};

const clazzList = ref([]);

const search = async () => {
  const result = await getClazzList(searchClazz.value);
  if (result.code) {
    clazzList.value = result.data.rows
    total.value = result.data.total;
    console.log(clazzList.value)
  }
};

const clear = () => {
  searchClazz.value = {
    name: '',
    begin: '',
    end: '',
    date: [],
    page: 1,
    pageSize: 10
  };
  search();
};

const handleEdit = (id) => {

};

const handleDelete = (id) => {

};

onMounted(() => {
  search();
});

</script>

<template>
  <h1>班级管理</h1>

  <el-form :inline="true" :model="searchClazz">
    <el-form-item label="班级鸣潮">
      <el-input v-model="searchClazz.name" placeholder="请输入员工姓名"></el-input>
    </el-form-item>

    <el-form-item label="入职日期">
      <el-date-picker
        v-model="searchClazz.date"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="YYYY-MM-DD"
      ></el-date-picker>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="search">查询</el-button>
      <el-button @click="clear">清空</el-button>
    </el-form-item>
  </el-form>

  <!-- 表格 -->
  <el-table :data="clazzList" border style="width: 100%" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center"></el-table-column>
    <el-table-column prop="name" label="班级名称" width="120" align="center"></el-table-column>
    <el-table-column prop="room" label="班级教室" width="120" align="center"></el-table-column>
    <el-table-column prop="masterName" label="班主任" width="120" align="center"></el-table-column>
    <el-table-column prop="beginDate" label="开课时间" width="210" align="center"></el-table-column>
    <el-table-column prop="endDate" label="结课时间" width="210" align="center"></el-table-column>
    <el-table-column prop="status" label="状态" width="210" align="center"></el-table-column>
    <el-table-column prop="updateTime" label="最后修改时间" width="210" align="center"></el-table-column>
    <el-table-column label="操作" fixed="right" align="center">
      <template #default="scope">
        <el-button size="small" type="primary" @click="handleEdit(scope.row.id)">编辑</el-button>
        <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!-- 分页 -->
  <el-pagination
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
    v-model:current-page="currentPage"
    v-model:page-size="pageSize"
    :page-sizes="[10, 20, 30, 40]"
    layout="total, sizes, prev, pager, next, jumper"
    :total="total"
  >
  </el-pagination>
</template>

<style scoped>

</style>