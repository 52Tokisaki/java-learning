import request from '@/utils/request';

export const getDeptList = () => request.get('/depts');

export const insertDept = (dept) => request.post('/depts', dept);

export const updateDept = (id, dept) => request.put(`/depts/${id}`, dept);

export const getDeptById = (id) => request.get(`depts/${id}`);

export const deleteDeptById = (id) => request.delete(`depts/${id}`);
