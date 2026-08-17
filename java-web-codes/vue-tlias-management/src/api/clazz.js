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

export const insertClazz = (clazz) => request.post('/clazzs', clazz);

export const getClazzById = id => request.get(`/clazzs/${id}`);

export const updateClazz = clazz => request.put('/clazzs', clazz);

