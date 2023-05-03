package com.imyuanxiao.rbac.exception;

import lombok.Getter;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 9:25
 */
@Getter
public class APIException extends RuntimeException{
    private int code;
    private String msg;

    public APIException() {
        this(1001, "接口错误");
    }

    public APIException(String msg) {
        this(1001, msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
