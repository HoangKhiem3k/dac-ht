import axios from "axios";
import { useRouter } from 'vue-router';
import { API_BASE_URL } from "@/config";
//import { USER_TOKEN_NAME } from '@/utils/constants';

function buildApi() {
  const axiosInstance = axios.create({
    baseURL: API_BASE_URL,
    timeout: 180000,
    headers: {
      "Content-Type": "application/json"
    },
  });

  // Request interceptor
  axiosInstance.interceptors.request.use(request => {
    /*const token = localStorage.getItem(`${USER_TOKEN_NAME}`);
    if (token) {
      request.headers.common.Authorization = `Bearer ${token}`
    }*/
    return request;
  });
    
  // Response interceptor
  axiosInstance.interceptors.response.use(response => {
    return response.data;
  }, error => {
    if (error.response){
      const { status, data } = error.response;
      if (status === 401 && data.redirect) {
        window.location.href = data.redirect;
      }
    }
    return Promise.reject(error);
  });
  return axiosInstance;
}

export const api = buildApi();
