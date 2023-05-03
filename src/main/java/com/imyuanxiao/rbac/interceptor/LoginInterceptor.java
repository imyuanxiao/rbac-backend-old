package com.imyuanxiao.rbac.interceptor;

import cn.hutool.jwt.JWT;
import com.imyuanxiao.rbac.context.TokenContext;
import com.imyuanxiao.rbac.context.UserContext;
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
@Component
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

        // 走到这里就代表是其他接口，且没有登录
        // 设置响应数据类型为json（前后端分离）
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        // 设置响应内容，结束请求
        out.write("请先登录");
        out.flush();
        out.close();
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if(TokenContext.get() != null){
            request.setAttribute("Authorization", TokenContext.get());
            TokenContext.remove();
        }
        // 结束后移除上下文对象
        UserContext.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
