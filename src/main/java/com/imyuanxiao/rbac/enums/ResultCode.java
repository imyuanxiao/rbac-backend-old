package com.imyuanxiao.rbac.enums;

import lombok.Getter;

/**
 * 响应码
 * @Author: imyuanxiao
 * @Date: 2023/5/3 9:10
 */
@Getter
public enum ResultCode {

    //操作成功
    SUCCESS(0000, "操作成功"),
    // token相关
    UNAUTHORIZED(1001, "没有登录"),
    INVALID_TOKEN(1002, "无效的token"),
    TOKEN_EXPIRED(1003, "token已过期"),
    // 权限相关
    FORBIDDEN(1004, "没有相关权限"),
    UNAUTHORIZED_OPERATION(1005, "未授权的操作"),
    // 参数校验相关
    VALIDATE_FAILED(1006, "参数校验失败"),

    // 未找到资源
    RESOURCE_NOT_FOUND(1007, "资源不存在"),
    USER_NOT_FOUND(1008, "用户不存在"),
    ROLE_NOT_FOUND(1009, "角色不存在"),
    PERMISSION_NOT_FOUND(1010, "权限不存在"),
    // 其他错误
    FAILED(2001, "操作失败"),
    DATABASE_ERROR(2002, "数据库操作异常"),

    ERROR(5000, "未知错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
