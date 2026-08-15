import request from "@/utils/request";


export const getClazzList = (params) => request.get('/clazzs', {
  params: {
    name: params.name,
    begin: params.begin,
    end: params.end,
    page: params.page,
    pageSize: params.pageSize,
  },
});