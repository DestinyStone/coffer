package com.coffee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coffee.entity.pojo.Order;
import com.coffee.entity.pojo.OrderDetailVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface OrderMapper extends BaseMapper<Order> {
    @Select("select * from `order_detail` inner join `item` on `order_detail`.item_id=`item`.item_id where order_id=#{orderId}")
    List<OrderDetailVO> getOrderDetailVOByOrderId(@Param("orderId") Integer orderId);
}
