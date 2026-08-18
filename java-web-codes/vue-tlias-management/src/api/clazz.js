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

export const deleteClazz = id => request.delete(`/clazzs/${id}`);

// 查询所有班级
export const getAllClazz = () => request.get('/clazzs/list');