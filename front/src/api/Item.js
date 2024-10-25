import request from './common/Request';

/**
 * 商品接口
 */
export default class Item {
  // 新增
  static save(data) {
    return request({
      url: '/item/save',
      method: 'post',
      data: data,
    })
  }
  // 删除
  static delete(id) {
    return request({
      url: '/item/delete',
      method: 'get',
      params: {id}
    })
  }
  // 更新
  static update(id, data) {
    return request({
      url: `/item/update/${id}`,
      method: 'post',
      data: data,
    })
  }
  // 根据id查询数据
  static getById(id) {
    return request({
      url: `/item/id`,
      method: 'get',
      params: {id}
    })
  }
  // 返回所有数据
  static list(param) {
    return request({
      url: `/item/list`,
      method: 'get',
      params:{
        ...param,
      }
    })
  }
  // 上传图片
  static upload(id, data) {
    return request({
      url: `/item/upload/${id}`,
      method: 'post',
      data: data,
      headers: 'multipart/form-data',
    })
  }
  // 获取随机三个商品
  static top3() {
    return request({
      url: `/item/top3`,
      method: 'get',
      params:{
      }
    })
  }
}
