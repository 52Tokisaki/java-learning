<script setup>

import { onMounted, ref } from "vue";
import { queryStuList } from "@/api/stu";
import { getAllClazz } from "@/api/clazz";

const searchStu = ref({
  name: '',
  degree: null,
  clazzId: null,
  page: 1,
  pageSize: 10
});

const clazzMap = ref([]);
const stuList = ref([]);
// 学历(1:初中,2:高中,3:大专,4:本科,5:硕士,6:博士)
const degreeMap = [
  { value: 1, label: '初中' },
  { value: 2, label: '高中' },
  { value: 3, label: '大专' },
  { value: 4, label: '本科' },
  { value: 5, label: '硕士' },
  { value: 6, label: '博士' },
];

const search = async () => {
  const result = await queryStuList(searchStu.value);
  if (result.code) {
    stuList.value = result.data.rows;
    total.value = result.data.total;
  }
};

// 分页配置
const pageSize = ref(10);
const currentPage = ref(1);
const total = ref(0);

// 分页处理
const handleSizeChange = (val) => {
  searchStu.value.pageSize = val;
  search();
};
const handleCurrentChange = (val) => {
  searchStu.value.page = val;
  search();
};

const getClazzMap = async () => {
  const result = await getAllClazz();
  if (result.code) {
    clazzMap.value = result.data.map(item => ({
      value: item.id,
      label: item.name
    }));
  }
};

onMounted(() => {
  search();
  getClazzMap();
});
</script>

<template>
  <h1>学员管理</h1>
  <el-form :inline="true" :model="searchStu">
    <el-form-item label="姓名">
      <el-input v-model="searchStu.name" placeholder="请输入学生姓名"></el-input>
    </el-form-item>

    <el-form-item label="学位" prop="degree">
      <el-select v-model="searchStu.degree" placeholder="请选择学位">
        <el-option v-for="item in degreeMap" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
    </el-form-item>

    <el-form-item label="班级" prop="clazzId">
      <el-select v-model="searchStu.clazzId" placeholder="请选择班级">
        <el-option v-for="item in clazzMap" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="search">查询</el-button>
      <el-button @click="clear">清空</el-button>
    </el-form-item>
  </el-form>

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