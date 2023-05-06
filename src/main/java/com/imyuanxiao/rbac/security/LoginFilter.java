package com.imyuanxiao.rbac.security;

import cn.hutool.core.util.StrUtil;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * 认证过滤器
 * @Author: imyuanxiao
 * @Date: 2023/5/6 11:07
 */
@Slf4j
@Component
public class LoginFilter  extends OncePerRequestFilter {
    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        // 如果token为空或没有以”Bearer "开头，跳过本层过滤
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        // 提取用户名，查询数据库
        // 在提取时会验证token是否有效
        username = JwtManager.extractUsername(jwt);
        // username有效，并且上下文对象中没有配置用户
        if (StrUtil.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 从数据库中获取用户信息、密码、角色信息等，返回一个包含用户详细信息的 UserDetailsVO 对象
            UserDetails userDetails = userService.loadUserByUsername(username);
            // 创建一个包含了用户的认证信息、凭证信息（之前验证过jwt，不需要凭证）、用户的授权信息的对象
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            // 封装 HTTP 请求的详细信息的对象，包含了请求的 IP 地址、请求的 Session ID、请求的 User Agent 等
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            // 将 authToken 设置到当前线程的 SecurityContext 中，表示用户已经通过身份认证，并且具有相应的授权信息
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}
