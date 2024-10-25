import request from './common/Request';

/**
 * 用户接口
 */
export default class User {
  // 新增
  static save(data) {
    return request({
      url: '/user/save',
      method: 'post',
      data: data,
    })
  }
  // 删除
  static delete(id) {
    return request({
      url: '/user/delete',
      method: 'get',
      params: {id}
    })
  }
  // 更新
  static update(id, data) {
    return request({
      url: `/user/update/${id}`,
      method: 'post',
      data: data,
    })
  }
  static updateInfo(data) {
    return request({
      url: `/user/update-info`,
      method: 'post',
      data: data,
    })
  }
  static updatePassword(data) {
    return request({
      url: `/user/update-password`,
      method: 'post',
      data: data,
    })
  }
  // 根据id查询数据
  static getById(id) {
    return request({
      url: `/user/id`,
      method: 'get',
      params: {id}
    })
  }
  // 返回所有数据
  static list(param) {
    return request({
      url: `/user/list`,
      method: 'get',
      params:{
        ...param,
      }
    })
  }
}
