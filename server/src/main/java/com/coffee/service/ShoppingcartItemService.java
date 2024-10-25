package com.coffee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coffee.entity.pojo.ShoppingcartItem;
import com.coffee.entity.pojo.ShoppingcartItemVO;

import java.util.List;


public interface ShoppingcartItemService extends IService<ShoppingcartItem> {
    double sumTotalMoney(int userId);

    List<ShoppingcartItemVO> selectByUserId(int userId);
}
