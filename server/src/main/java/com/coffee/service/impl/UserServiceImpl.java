package com.coffee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffee.common.exception.UserExistException;
import com.coffee.entity.pojo.User;
import com.coffee.mapper.UserMapper;
import com.coffee.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User login(String account, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, account)
                .eq(User::getPassword, password);
        return getOne(wrapper);
    }

    @Override
    public void register(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, user.getAccount());


        if (getOne(wrapper) != null) {
            throw new UserExistException("注册的用户名已存在！");
        }
        baseMapper.insert(user);
    }

    @Override
    public boolean checkExist(String account) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, account);
        return count(wrapper) > 0;
    }

    @Override
    public void deleteByAccount(String account) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, account);
        remove(wrapper);
    }

    @Override
    public User getByAccount(String account) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, account);
        return getOne(wrapper);
    }
}
