<template>
  <div class="my-table">
    <!-- 搜索表单区域 -->
    <div class="search-form">
      <el-form :inline="true" :model="searchEmp" class="demo-form-inline">
        <el-form-item label="姓名">
          <el-input v-model="searchEmp.name" placeholder="请输入姓名" clearable />
        </el-form-item>

        <el-form-item label="性别" style="width: 200px">
          <el-select v-model="searchEmp.gender" placeholder="请选择性别" clearable>
            <el-option label="男" value="1" />
            <el-option label="女" value="2" />
          </el-select>
        </el-form-item>

        <el-form-item label="职位" style="width: 200px">
          <el-select v-model="searchEmp.job" placeholder="请选择职位" clearable>
            <el-option v-for="job in jobMap" :label="job.label" :value="job.value" />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
        </el-form-item>

        <el-form-item>
          <el-button type="default" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 数据表格区域 -->
    <div class="table-data">
      <el-table :data="tableData" border class="mb-4" style="width: 100%">
        <el-table-column
            v-for="column in baseColumns"
            :prop="column.prop"
            :label="column.label"
            :width="column.width"
            :key="column.prop"
        >
          <!-- 如果是模板列，使用插槽渲染 -->
          <template v-if="column.isTemplate" #default="{ row }">
            <slot :name="column.slotName || column.prop" :row="row">
              <!-- 默认渲染：直接显示数据 -->
              <span>{{ row[column.prop] }}</span>
            </slot>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { onMounted, ref } from "vue";

// 定义 props
const props = defineProps({
  // API 基础路径
  apiUrl: {
    type: String,
    default: 'https://web-server.itheima.net/emps/list'
  },
  // 自定义列配置
  columns: {
    type: Array,
    default: null
  },
  // 搜索表单配置
  searchFields: {
    type: Array,
    default: () => ['name', 'gender', 'job']
  }
});

// 定义 emits
const emit = defineEmits(['search', 'reset', 'data-loaded']);

// 数据
const tableData = ref([]);
const searchEmp = ref({
  name: '',
  gender: '',
  job: '',
});

// 性别映射（供插槽使用）
const genderMap = {
  0: '男',
  1: '女'
};

// 职位映射（供插槽使用）
const jobMap = [
    { label: '班主任', value: 1, },
    { label: '教师', value: 2, },
    { label: '咨询师', value: 3, },
];

// 默认列配置
const defaultColumns = [
  { prop: 'id', label: 'ID', width: '100px', isTemplate: false },
  { prop: 'name', label: '姓名', width: '100px', isTemplate: false },
  { prop: 'gender', label: '性别', width: '100px', isTemplate: true, slotName: 'gender' },
  { prop: 'job', label: '职位', width: '100px', isTemplate: true, slotName: 'job' },
  { prop: 'image', label: '头像', width: '100px', isTemplate: true, slotName: 'image' },
  { prop: 'updatetime', label: '更新时间', width: '180px', isTemplate: false },
  { prop: 'entrydate', label: '入职日期', isTemplate: false },
];

// 使用自定义列或默认列
const baseColumns = ref(props.columns || defaultColumns);

// 搜索方法
const search = async () => {
  try {
    const params = new URLSearchParams();

    // 只添加有值的搜索参数
    Object.keys(searchEmp.value).forEach(key => {
      if (searchEmp.value[key] !== '' && searchEmp.value[key] !== null && searchEmp.value[key] !== undefined) {
        params.append(key, searchEmp.value[key]);
      }
    });

    const url = `${props.apiUrl}?${params.toString()}`;
    const result = await axios.get(url);
    tableData.value = result.data.data || [];
    console.log(tableData.value);

    // 触发事件
    emit('data-loaded', tableData.value);
    emit('search', searchEmp.value);
  } catch (error) {
    console.error('搜索失败:', error);
    ElMessage.error('数据加载失败，请重试');
  }
};

// 重置方法
const reset = () => {
  searchEmp.value = {
    name: '',
    gender: '',
    job: '',
  };
  emit('reset');
  search();
};

// 处理搜索
const handleSearch = () => {
  search();
};

// 处理重置
const handleReset = () => {
  reset();
};

// 对外暴露方法
defineExpose({
  search,
  reset,
  tableData,
  searchEmp
});

// 生命周期
onMounted(() => {
  search();
});
</script>

<style scoped>
.my-table {
  width: 100%;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.search-form {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.table-data {
  margin-top: 10px;
}

/* 响应式样式 */
@media (max-width: 768px) {
  .search-form {
    padding: 10px;
  }

  .el-form-item {
    margin-bottom: 10px;
  }
}
</style>