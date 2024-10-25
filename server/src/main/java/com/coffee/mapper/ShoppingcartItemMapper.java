package com.coffee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coffee.entity.pojo.ShoppingcartItem;
import com.coffee.entity.pojo.ShoppingcartItemVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ShoppingcartItemMapper extends BaseMapper<ShoppingcartItem> {
    @Select("select * from `shoppingcart_item` inner join `item` on `shoppingcart_item`.item_id=`item`.item_id where user_id=#{userId}")
    List<ShoppingcartItemVO> selectByUserId(@Param("userId") Integer userId);
}
