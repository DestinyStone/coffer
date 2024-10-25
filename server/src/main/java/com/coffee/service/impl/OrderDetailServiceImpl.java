package com.coffee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffee.entity.pojo.OrderDetail;
import com.coffee.mapper.OrderDetailMapper;
import com.coffee.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
