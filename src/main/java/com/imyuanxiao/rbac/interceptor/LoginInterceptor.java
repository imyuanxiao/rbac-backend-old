package com.imyuanxiao.rbac.interceptor;

import cn.hutool.jwt.JWT;
import com.imyuanxiao.rbac.context.TokenContext;
import com.imyuanxiao.rbac.context.UserContext;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 16:35
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从请求头中获取token字符串并解析
        JWT jwt = JwtUtil.parse(request.getHeader("Authorization"));
        // 已登录就直接放行
        if (jwt != null) {
            // 添加上下文对象
            UserContext.set((String)jwt.getPayload("username"));
            return true;
        }

        // 走到这里就代表没有登录
        throw new ApiException(ResultCode.UNAUTHORIZED);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

//        if(TokenContext.get() != null){
//            request.setAttribute("Authorization", TokenContext.get());
//            TokenContext.remove();
//        }
        // 结束后移除上下文对象
        UserContext.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
