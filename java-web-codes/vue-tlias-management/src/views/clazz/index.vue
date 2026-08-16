<script setup>


import { onMounted, ref, watch } from "vue";
import { getClazzList, insertClazz } from "@/api/clazz";
import { getAllEmpList } from "@/api/emp";
import { ElMessage } from "element-plus";

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

const getClazzStatus = row => {
  if (new Date(row.beginDate) <= new Date() && new Date(row.endDate) >= new Date()) {
    return '已开班';
  } else if (new Date(row.beginDate) > new Date()) {
    return '未开班';
  } else {
    return '已结课';
  }
};

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

const showDialog = ref(false);
const dialogTitle = ref('');
const clazzForm = ref({ name: '', room: '', beginDate: '', endDate: '', masterId: null, subject: null });
// 班级名称、开课、结课时间、学科必填
const rules = ref({
  name: [
    { required: true, message: '请输入班级名称', trigger: 'blur' },
    { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
  ],
  beginDate: [
    { required: true, message: '请选择开课时间', trigger: 'change' }
  ],
  endDate: [
    { required: true, message: '请选择结课时间', trigger: 'change' }
  ],
  subject: [
    { required: true, message: '请选择学科', trigger: 'change' }
  ]
});
const formRef = ref(null);

const masterMap = ref([]);
// 学科, 1:java, 2:前端, 3:大数据, 4:Python, 5:Go, 6:嵌入式
const subjectMap = ref([ { value: 1, label: 'Java' }, { value: 2, label: '前端' }, { value: 3, label: '大数据' }, { value: 4, label: 'Python' }, { value: 5, label: 'Go' }, { value: 6, label: '嵌入式' } ])
const addClazz = () => {
  showDialog.value = true;
  clazzForm.value = { name: '', room: '', beginDate: '', endDate: '', masterName: '', subject: '' };
  dialogTitle.value = '新增班级';
};

const getMasterList = async () => {
  const result = await getAllEmpList();
  if (result.code) {
    console.log(result)
    masterMap.value = result.data.map(item => ({
      value: item.id,
      label: item.name
    }));
  }
};

const onSubmit = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      let result;
      if (dialogTitle.value === '新增班级') {
        result = await insertClazz(clazzForm.value);
      } else {
        result = await updateClazz(clazzForm.value);
      }
      if (result.code) {
        ElMessage.success('保存成功');
        showDialog.value = false;
        await search();
      } else {
        ElMessage.error('保存失败');
      }
    } else {
      ElMessage.error('请填写必填项');
      return false;
    }
  });
};

onMounted(() => {
  search();
  getMasterList();
});

</script>

<template>
  <h1>班级管理</h1>

  <el-form :inline="true" :model="searchClazz">
    <el-form-item label="班级名称">
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

  <div style="margin: 20px 0">
    <el-button type="primary" @click="addClazz">新增班级</el-button>
  </div>

  <!-- 表格 -->
  <el-table :data="clazzList" border style="width: 100%" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" align="center"></el-table-column>
    <el-table-column prop="name" label="班级名称" width="120" align="center"></el-table-column>
    <el-table-column prop="room" label="班级教室" width="120" align="center"></el-table-column>
    <el-table-column prop="masterName" label="班主任" width="120" align="center"></el-table-column>
    <el-table-column prop="beginDate" label="开课时间" width="210" align="center"></el-table-column>
    <el-table-column prop="endDate" label="结课时间" width="210" align="center"></el-table-column>
    <el-table-column prop="status" label="状态" width="210" align="center">
    <!--  状态映射：状态 （未开班、已开班、已结课） 判断当前时间是否在开课时间和结课时间之间   -->
      <template #default="scope">
        {{ getClazzStatus(scope.row) }}
      </template>
    </el-table-column>
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

  <el-dialog v-model="showDialog" :title="dialogTitle">
    <el-form :model="clazzForm" :rules="rules" ref="formRef" label-width="100px">
      <el-form-item label="班级名称" prop="name">
        <el-input v-model="clazzForm.name" />
      </el-form-item>
      <el-form-item label="班级教室" prop="room">
        <el-input v-model="clazzForm.room" />
      </el-form-item>
      <el-form-item label="开课时间" prop="beginDate">
        <el-date-picker v-model="clazzForm.beginDate" type="date" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item label="结课时间" prop="endDate">
        <el-date-picker v-model="clazzForm.endDate" type="date" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" />
      </el-form-item>
      <el-form-item label="班主任" prop="masterId">
        <el-select v-model="clazzForm.masterId" placeholder="请选择班主任">
          <el-option v-for="item in masterMap" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="学科" prop="subject">
        <el-select v-model="clazzForm.subject" placeholder="请选择学科">
          <el-option v-for="item in subjectMap" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="onSubmit"> 确认 </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>

</style>