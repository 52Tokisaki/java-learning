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