package com.coffee.common.utils;

import com.coffee.common.exception.ServiceException;
import com.coffee.entity.vo.Auth;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;


public class AuthUtil {
    // session 的 key 值
    private static final String TOKEN_KEY = "token";

    // 获取当前登录的用户id
    public static Integer getUserId() {
        return getUser().getId();
    }

    // 设置登录权限
    public static void setToken(Auth auth) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(TOKEN_KEY, auth);
    }

    // 清空权限
    public static void clear() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(TOKEN_KEY, null);
    }

    // 是否为管理员
    public static boolean isAdmin() {
        Auth user = getUser();
        return Objects.equals(user.getRole(), "admin");
    }

    // 获取当前登录用户
    public static Auth getUser() {
        // 获取全局request
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(TOKEN_KEY);

        // 判断是否登录
        if(attribute == null) {
            throw new ServiceException("用户未登录");
        }
        return (Auth) attribute;
    }
}
