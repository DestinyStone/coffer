package com.coffee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffee.common.exception.ServiceException;
import com.coffee.entity.pojo.*;
import com.coffee.mapper.OrderDetailMapper;
import com.coffee.mapper.OrderMapper;
import com.coffee.service.ItemService;
import com.coffee.service.OrderDetailService;
import com.coffee.service.OrderService;
import com.coffee.service.ShoppingcartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private ShoppingcartItemService cartService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ItemService itemService;

    @Override
    @Transactional
    public void add(Order order) {
        save(order);
        List<ShoppingcartItemVO> shoppingcartItems = cartService.selectByUserId(order.getUserId());
        for (ShoppingcartItemVO shoppingcartItem : shoppingcartItems) {
            // 插入到订单详情表
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setItemId(shoppingcartItem.getItemId());
            orderDetail.setNumber(shoppingcartItem.getNumber());
            orderDetailService.save(orderDetail);
            // 库存减少
            Item item = itemService.getById(shoppingcartItem.getItemId());
            item.setStock(item.getStock() - shoppingcartItem.getNumber());
            if (item.getStock() < 0) {
                // 库存不足，抛出异常，回滚事务
                throw new ServiceException("库存不足");
            }
            itemService.updateById(item);


            // 从购物车中去除
            LambdaQueryWrapper<ShoppingcartItem> updateWrapper = new LambdaQueryWrapper<>();
            updateWrapper.eq(ShoppingcartItem::getItemId, item.getItemId())
                    .eq(ShoppingcartItem::getUserId, order.getUserId());
            cartService.remove(updateWrapper);
        }

    }

    @Override
    public List<OrderDetailVO> getOrderDetailVOByOrderId(int orderId) {
        return baseMapper.getOrderDetailVOByOrderId(orderId);
    }
}
