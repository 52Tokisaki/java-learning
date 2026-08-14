import axios from "axios";

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
    return Promise.reject(error);
  },
);

export default request;