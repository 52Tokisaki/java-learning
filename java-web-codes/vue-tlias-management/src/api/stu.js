import request from '@/utils/request';

export const queryStuList = (params) => request.get('/students', {
  params: {
    name: params.name,
    degree: params.degree,
    clazzId: params.clazzId,
    page: params.page,
    pageSize: params.pageSize,
  },
});

// 新增学员
export const insertStu = (stu) => request.post(`/students`, stu);