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

export const insertEmp = (emp) => request.post('/emps', emp);

export const getEmpDetailById = (id) => request.get(`/emps/${id}`);

export const updateEmp = (emp) => request.put('/emps', emp);

export const deleteEmp = (ids) => request.delete('/emps', { params: { ids } });
