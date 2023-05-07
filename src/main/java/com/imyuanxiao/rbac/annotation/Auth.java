package com.imyuanxiao.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Auth
 * @Description 该注解用于标记该接口是否需要被权限管理
 * @Author imyuanxiao
 * @Date 2023/5/7 11:10
 * @Version 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE}) // 表明该注解可以加在类或方法上
public @interface Auth {
    /**
     * 权限id，需要唯一
     */
    long id();
    /**
     * 权限名称
     */
    String name();
}