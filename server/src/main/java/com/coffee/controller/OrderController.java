package com.coffee.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coffee.common.api.R;
import com.coffee.common.utils.AuthUtil;
import com.coffee.common.utils.PageUtils;
import com.coffee.entity.pojo.*;
import com.coffee.entity.vo.ShoppingcartItemFormBean;
import com.coffee.service.OrderService;
import com.coffee.service.ShoppingcartItemService;
import com.coffee.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ShoppingcartItemService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping("/detail")
    public R orderDetail(@RequestParam("id") Integer id) {
        //通过orderId，来获取一个该订单中的小件详情.
        List<OrderDetailVO> list = orderService.getOrderDetailVOByOrderId(id);
        return R.data(list);
    }

    @RequestMapping("/status")
    public R status(@RequestParam("id") Integer id) {

        LambdaUpdateWrapper<Order> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Order::getOrderId, id);
        wrapper.set(Order::getStatus, "已完成");

        orderService.update(wrapper);

        return R.success("操作成功");
    }

    @RequestMapping("/list")
    public R list() {

        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!AuthUtil.isAdmin(), Order::getUserId, AuthUtil.getUserId());
        List<Order> list = orderService.list(wrapper);


        ArrayList<OrderVO> result = new ArrayList<>();
        for (Order record : list) {
            OrderVO orderVO = BeanUtil.copyProperties(record, OrderVO.class);
            orderVO.setAccount(userService.getById(record.getUserId()).getAccount());
            result.add(orderVO);
        }

        return R.data(result);
    }


    @RequestMapping("/user/save")
    public R orderUserSave(@RequestBody Order dto) {

        System.out.println("------------AddOrderServlet work start-----------");
        // 设置订单详情
        Order order = new Order();
        order.setStatus("处理中");
        order.setAddress(dto.getAddress());
        order.setOrderTotal(cartService.sumTotalMoney(AuthUtil.getUserId()));
        order.setUserId(AuthUtil.getUserId());
        order.setCreatedTime(new Date());

        orderService.add(order);
        return R.success("结算成功");
    }
}
