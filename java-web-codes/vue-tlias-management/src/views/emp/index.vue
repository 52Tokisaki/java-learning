<script setup>
import { ref, watch } from "vue";
import { getEmpList } from "@/api/emp";

const searchEmp = ref({
  name: '',
  gender: '',
  date: [],
  begin: '',
  end: ''
});

const search = async () => {
  // 处理查询逻辑
  console.log('Search:', searchEmp.value);
  const result = await getEmpList(searchEmp.value);
  console.log('Result:', result);
};

const clear = () => {
  // 清空表单
  searchEmp.value = {
    name: '',
    gender: '',
    date: []
  };
  search();
};

watch(() => searchEmp.value.date, (newVal, oldValue) => {
  if (newVal.length === 2) {
    // 两个日期都选择了
    searchEmp.value.begin = newVal[0];
    searchEmp.value.end = newVal[1];
  } else {
    searchEmp.value.begin = '';
    searchEmp.value.end = '';
  }
});
</script>

<template>
  <h1>员工管理</h1> <br>
  <el-form :inline="true" :model="searchEmp">
    <el-form-item label="姓名">
      <el-input v-model="searchEmp.name" placeholder="请输入员工姓名"></el-input>
    </el-form-item>

    <el-form-item label="性别">
      <el-select v-model="searchEmp.gender" placeholder="请选择">
        <el-option label="男" value="1"></el-option>
        <el-option label="女" value="2"></el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="入职日期">
      <el-date-picker
        v-model="searchEmp.date"
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
</template>

<style scoped>

</style>