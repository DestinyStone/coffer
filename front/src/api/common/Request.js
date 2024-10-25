import Axios from "axios";
import {Message} from 'element-ui'

const request = Axios.create({
  baseURL: "/api",
  timeout: 30000,
  headers: {"Content-Type":"application/json"}
});

// 接口响应拦截
request.interceptors.response.use((config)=>{
  // 如果用户未登录
  if (config.data.code === 400 && config.data.msg === "用户未登录") {
    Message.warning("权限失效， 请重新登录");

    // 跳转到登录页面
    setTimeout(() => {
      window.location.href = "/#/"
    }, 1000)
    return new Promise(()=>{})
  }

  // 如果接口返回异常
  if (config.data.code === 400) {

    // 提示错误信息
    Message.warning(config.data.msg);
    return new Promise(()=>{})
  }
  return config
},(error)=>{
  console.log(error);
  return Promise.reject(error)
})

export default request;