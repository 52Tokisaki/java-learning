import request from "@/utils/request";

export const getEmpList = (params) => {
  return request.get('/emps', {
    params: {
      name: params.name,
      gender: params.gender,
      begin: params.begin,
      end: params.end,
      page: params.page,
      pageSize: params.pageSize,
    },
  });
};
