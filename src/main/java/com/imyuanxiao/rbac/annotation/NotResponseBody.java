package com.imyuanxiao.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName NotResponseBody
 * @Description 用于绕过数据统一响应
 * @Author: imyuanxiao
 * @Date: 2023/5/3 10:25
 * @Version 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NotResponseBody {

}
