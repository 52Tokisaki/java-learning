import request from '@/utils/request';

export const getDeptList = () => request.get('/depts');