package com.coffee.controller;

import com.coffee.common.api.R;
import com.coffee.common.utils.AuthUtil;
import com.coffee.entity.pojo.User;
import com.coffee.entity.vo.Auth;
import com.coffee.entity.vo.LoginFormBean;
import com.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/current")
    public R current() {
        try {
            Auth user = AuthUtil.getUser();
            return R.data(user);
        }catch (Exception e) {
            return R.data(null);
        }
    }

    @GetMapping("/loginOut")
    public R loginOut() {
        AuthUtil.clear();
        return R.success("操作成功");
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginFormBean formBean) {
        AuthUtil.clear();

        // 登录操作
        boolean isLogin = false;
        if (formBean.getStatus().equals("user")) {
            isLogin = userLogin(formBean);
        } else if (formBean.getStatus().equals("admin")) {
            isLogin = adminLogin(formBean);
        }

        return isLogin ? R.success("登录成功") : R.fail("用户名或密码错误");
    }


    /**
     * 管理员登录
     */
    private boolean adminLogin(LoginFormBean formBean) {
        if (Objects.equals(formBean.getAccount(), "admin") && Objects.equals(formBean.getPassword(), "123456")) {
            Auth auth = new Auth();
            auth.setId(1);
            auth.setAccount("admin");
            auth.setPassword("123456");
            auth.setRole(formBean.getStatus());
            AuthUtil.setToken(auth);
            return true;
        }
        return false;
    }

    /**
     * 用户登录
     */
    private boolean userLogin(LoginFormBean formBean) {
        User user = userService.login(formBean.getAccount(), formBean.getPassword());

        if (user != null) {
            Auth auth = new Auth();
            auth.setId(user.getUserId());
            auth.setAccount(formBean.getAccount());
            auth.setPassword(formBean.getPassword());
            auth.setRole(formBean.getStatus());
            AuthUtil.setToken(auth);
            return true;
        }

        return false;
    }

}
