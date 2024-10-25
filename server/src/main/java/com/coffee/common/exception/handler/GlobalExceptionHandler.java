package com.coffee.common.exception.handler;

import com.coffee.common.api.R;
import com.coffee.common.exception.ServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 ServiceException 异常
     */
    @ExceptionHandler(ServiceException.class)
    public R serviceExceptionHandler(ServiceException e) {
        e.printStackTrace();
        return R.fail(e.getMessage());
    }

    /**
     * 处理 Exception 异常
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        e.printStackTrace();
        return R.fail("未知异常");
    }
}
