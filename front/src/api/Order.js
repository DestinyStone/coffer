import request from './common/Request';

/**
 * 订单接口
 */
export default class Order {
  // 返回所有数据
  static list(param) {
    return request({
      url: `/order/list`,
      method: 'get',
      params:{
        ...param,
      }
    })
  }
  // 查询详情
  static detail(id) {
    return request({
      url: `/order/detail`,
      method: 'get',
      params:{
        id,
      }
    })
  }
  // 查询详情
  static status(id) {
    return request({
      url: `/order/status`,
      method: 'get',
      params:{
        id,
      }
    })
  }
  // 提交订单
  static userSave(data) {
    return request({
      url: `/order/user/save`,
      method: 'post',
      data: data
    })
  }
}
