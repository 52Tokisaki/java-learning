import axios from "axios";
import { ElMessage } from "element-plus";
import router from "@/router";

const request = axios.create({
  baseURL: "/api",
  timeout: 60000,
});

request.interceptors.request.use(
  (config) => {
    const loginUser = localStorage.getItem('loginUser');
    config.headers.token = loginUser ? JSON.parse(loginUser).token : '';
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
)

request.interceptors.response.use(
  (result) => {
    return result.data;
  },
  (error) => {
    if (error.response.status === 401) {
      ElMessage.error('登录失效，请重新登录');
      router.push('/login');
    } else {
      ElMessage.error('接口访问异常');
    }
    return Promise.reject(error);
  },
);

export default request;