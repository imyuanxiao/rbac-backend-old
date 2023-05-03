package com.imyuanxiao.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于字段校验
 * @Author: imyuanxiao
 * @Date: 2023/5/3 9:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExceptionCode {
    // 响应码code
    int value() default 100000;
    // 响应信息msg
    String message() default  "参数校验错误";
}
