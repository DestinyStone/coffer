package com.coffee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffee.entity.pojo.Item;
import com.coffee.mapper.ItemMapper;
import com.coffee.service.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
}
