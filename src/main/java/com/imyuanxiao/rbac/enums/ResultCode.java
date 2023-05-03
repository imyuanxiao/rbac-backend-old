package com.imyuanxiao.rbac.enums;

import lombok.Getter;

/**
 * 响应码
 * @Author: imyuanxiao
 * @Date: 2023/5/3 9:10
 */
@Getter
public enum ResultCode {
    SUCCESS(0000, "操作成功"),

    UNAUTHORIZED(1001, "没有登录"),

    FORBIDDEN(1002, "没有相关权限"),

    VALIDATE_FAILED(1003, "参数校验失败"),

    FAILED(1004, "接口异常"),

    ERROR(5000, "未知错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}