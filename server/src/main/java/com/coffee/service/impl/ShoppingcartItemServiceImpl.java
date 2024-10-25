package com.coffee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffee.entity.pojo.ShoppingcartItem;
import com.coffee.entity.pojo.ShoppingcartItemVO;
import com.coffee.mapper.ShoppingcartItemMapper;
import com.coffee.service.ItemService;
import com.coffee.service.ShoppingcartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


@Service
public class ShoppingcartItemServiceImpl extends ServiceImpl<ShoppingcartItemMapper, ShoppingcartItem> implements ShoppingcartItemService {

    @Autowired
    private ItemService itemService;

    @Override
    public double sumTotalMoney(int userId)  {
        LambdaQueryWrapper<ShoppingcartItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingcartItem::getUserId, userId);
        List<ShoppingcartItem> list = list(wrapper);
        double total = 0;
        for (ShoppingcartItem item : list) {
            total += item.getNumber() * itemService.getById(item.getItemId()).getPrice();
        }
        return total;
    }

    @Override
    public List<ShoppingcartItemVO> selectByUserId(int userId) {
        return baseMapper.selectByUserId(userId);
    }

}
