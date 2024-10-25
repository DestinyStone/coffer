import request from './common/Request';

/**
 * 权限接口
 */
export default class Auth {
  // 当前登录用户
  static current() {
    return request({
      url: '/auth/current',
      method: 'get',
    })
  }
  // 登录用户
  static login(data) {
    return request({
      url: '/auth/login',
      method: 'post',
      data: data,
    })
  }
  // 退出用户
  static loginOut() {
    return request({
      url: '/auth/loginOut',
      method: 'get',
    })
  }
}