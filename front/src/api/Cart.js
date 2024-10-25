import request from './common/Request';

/**
 * 购物车接口
 */
export default class Cart {
  // 新增
  static save(data) {
    return request({
      url: '/cart/save',
      method: 'post',
      data: data,
    })
  }
  // 删除
  static delete(id) {
    return request({
      url: '/cart/delete',
      method: 'get',
      params: {id}
    })
  }
  // 更新
  static update(id, data) {
    return request({
      url: `/cart/update/${id}`,
      method: 'post',
      data: data,
    })
  }
  // 根据id查询数据
  static getById(id) {
    return request({
      url: `/cart/id`,
      method: 'get',
      params: {id}
    })
  }
  // 返回所有数据
  static list(param) {
    return request({
      url: `/cart/list`,
      method: 'get',
      params:{
        ...param,
      }
    })
  }
}
