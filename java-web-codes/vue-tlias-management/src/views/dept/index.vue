<script setup>
import { onMounted, ref } from "vue";
import {
  deleteDeptById,
  getDeptById,
  getDeptList,
  insertDept,
  updateDept,
} from "@/api/dept";
import { ElMessage, ElMessageBox } from "element-plus";

const deptList = ref([]);

const handleEdit = async (id) => {
  console.log("修改部门", id);
  const result = await getDeptById(id);
  deptForm.value = result.data;
  dialogTitle.value = "修改部门";
  showDialog.value = true;
  formRef.value.resetFields();
};

const handleDelete = (id) => {
  console.log("删除部门", id);
  ElMessageBox.confirm("确认要删除该部门吗?", "Warning", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      const result = await deleteDeptById(id);
      if (result.code === 1) {
        ElMessage({
          type: "success",
          message: "删除部门成功",
        });
      }
    })
    .catch(() => {});
};

const getTableData = async () => {
  const result = await getDeptList();
  deptList.value = result.data;
};

const showDialog = ref(false);
const dialogTitle = ref("");
const deptForm = ref({ name: "" });
const formRef = ref();
const rules = ref({
  name: [
    { required: true, message: "请输入部门名称", trigger: "blur" },
    { min: 2, max: 10, message: "部门名称长度为2-10位字符", trigger: "blur" },
  ],
});

const addDept = () => {
  dialogTitle.value = "新增部门";
  showDialog.value = true;
  formRef.value.resetFields();
};

const onSubmit = () => {
  console.log(deptForm.value);

  if (!formRef.value) {
    return;
  }

  formRef.value.validate(async (valid) => {
    if (!valid) {
      ElMessage.error("表单校验失败");
      return;
    }
    if (dialogTitle.value === "新增部门") {
      const result = await insertDept(deptForm.value);
      if (result.code === 1) {
        ElMessage.success("新增部门成功");
      } else {
        ElMessage.error("新增部门失败");
      }
    } else if (dialogTitle.value === "修改部门") {
      const result = await updateDept(deptForm.value.id, deptForm.value);
      if (result.code === 1) {
        ElMessage.success("修改部门成功");
      } else {
        ElMessage.error("修改部门失败");
      }
    }
    showDialog.value = false;
  });
};

onMounted(() => {
  getTableData();
});
</script>

<template>
  <h1>部门管理</h1>
  <div style="margin: 20px 0">
    <el-button type="primary" @click="addDept">新增部门</el-button>
  </div>

  <el-table :data="deptList" border style="width: 100%">
    <el-table-column type="index" label="序号" width="100" align="center" />
    <el-table-column prop="name" label="部门名称" width="300" align="center" />
    <el-table-column
      prop="updateTime"
      label="最后修改时间"
      width="300"
      align="center"
    />
    <el-table-column fixed="right" label="操作" align="center">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.row.id)"
        >修改</el-button
        >
        <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope.row.id)"
        >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>

  <el-dialog v-model="showDialog" :title="dialogTitle">
    <el-form :model="deptForm" :rules="rules" ref="formRef">
      <el-form-item label="部门名称" prop="name">
        <el-input v-model="deptForm.name" />
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

<style scoped></style>
