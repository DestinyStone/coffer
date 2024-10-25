package com.coffee.common.api;

import java.io.Serializable;

/**
 * 响应码接口
 */
public interface IResultCode extends Serializable {
    // 返回信息
    String getMessage();

    // 状态码
    int getCode();
}
