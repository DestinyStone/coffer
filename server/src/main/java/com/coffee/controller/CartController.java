package com.coffee.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.coffee.common.api.R;
import com.coffee.common.utils.AuthUtil;
import com.coffee.entity.pojo.*;
import com.coffee.entity.vo.ShoppingcartItemFormBean;
import com.coffee.service.ItemService;
import com.coffee.service.ShoppingcartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private ShoppingcartItemService cartItemService;

    @Autowired
    private ItemService itemService;

    @RequestMapping("/list")
    public R cartUserList() {
        // 获取该用户的购物车信息
        List<ShoppingcartItemVO> items = cartItemService.selectByUserId(AuthUtil.getUserId());
        return R.data(items);
    }

    @RequestMapping("/delete")
    public R userDelete(@RequestParam("id") Integer id) {
        // FormBean转PO
        LambdaQueryWrapper<ShoppingcartItem> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(ShoppingcartItem::getItemId, id)
                .eq(ShoppingcartItem::getUserId, AuthUtil.getUserId());
        cartItemService.remove(updateWrapper);

        return R.success("操作成功");

    }

    @RequestMapping("/update/{id}")
    public R userUpdate(@PathVariable("id") Integer id, @RequestBody ShoppingcartItemFormBean formBean) {
        formBean.setItemId(id);
        // FormBean转PO
        LambdaUpdateWrapper<ShoppingcartItem> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ShoppingcartItem::getItemId, formBean.getItemId())
                .eq(ShoppingcartItem::getUserId, AuthUtil.getUserId());
        updateWrapper.set(ShoppingcartItem::getNumber, formBean.getNumber());
        cartItemService.update(updateWrapper);

        return R.success("操作成功");
    }

    @RequestMapping("/save")
    public R userSave(@RequestBody ShoppingcartItemFormBean formBean) {
        formBean.setUserId(AuthUtil.getUserId());
        // FormBean转PO
        ShoppingcartItem item = BeanUtil.copyProperties(formBean, ShoppingcartItem.class);
        try {
            LambdaQueryWrapper<ShoppingcartItem> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ShoppingcartItem::getItemId, formBean.getItemId())
                    .eq(ShoppingcartItem::getUserId, formBean.getUserId());
            ShoppingcartItem one = cartItemService.getOne(wrapper);
            if(one != null) {
                one.setNumber(formBean.getNumber() + one.getNumber());
                LambdaUpdateWrapper<ShoppingcartItem> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(ShoppingcartItem::getItemId, formBean.getItemId())
                        .eq(ShoppingcartItem::getUserId, formBean.getUserId());
                updateWrapper.set(ShoppingcartItem::getNumber, one.getNumber());
                cartItemService.update(updateWrapper);
                return R.success("操作成功");
            }
            cartItemService.save(item);
            return R.success("操作成功");
        } catch (Exception e) {
            return R.success("操作失败");
        }
    }

}
