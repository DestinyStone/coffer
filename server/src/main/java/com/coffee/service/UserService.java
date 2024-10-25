package com.coffee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coffee.entity.pojo.User;


public interface UserService  extends IService<User> {
    User login(String account, String password);

    void register(User user);

    boolean checkExist(String account);

    void deleteByAccount(String account);

    User getByAccount(String account);
}
