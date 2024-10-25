package com.coffee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coffee.entity.pojo.Order;
import com.coffee.entity.pojo.OrderDetail;
import com.coffee.entity.pojo.OrderDetailVO;

import java.util.List;


public interface OrderService extends IService<Order> {
    void add(Order order);

    List<OrderDetailVO> getOrderDetailVOByOrderId(int orderId);
}
