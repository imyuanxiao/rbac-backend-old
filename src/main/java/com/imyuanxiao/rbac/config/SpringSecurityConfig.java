package com.imyuanxiao.rbac.config;

import com.imyuanxiao.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * 对Spring Security进行自定义配置
 * @Author: imyuanxiao
 * @Date: 2023/5/3 15:35
 */
@Configuration
@EnableWebSecurity	// 添加 security 过滤器
@EnableGlobalMethodSecurity(prePostEnabled = true)	// 启用方法级别的权限认证
public class SpringSecurityConfig {

//    @Autowired
//    private UserService userService;
//
//    // 权限不足错误信息处理，包含认证错误与鉴权错误处理
//    @Autowired
//    private JwtAuthError jwtAuthError;
//
//    // jwt 校验过滤器，从 http 头部 Authorization 字段读取 token 并校验
//    @Bean
//    public JwtAuthFilter authFilter() throws Exception {
//        return new JwtAuthFilter();
//    }
//
//    /**
//     * 密码明文加密方式配置
//     * @return
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * 获取AuthenticationManager（认证管理器），登录时认证使用
//     * @param authenticationConfiguration
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/**").permitAll();
        return http.build();

//        return http
//                // 基于 token，不需要 csrf
//                .csrf().disable()
//                // 基于 token，不需要 session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                // 设置 jwtAuthError 处理认证失败、鉴权失败
//                .exceptionHandling().authenticationEntryPoint(jwtAuthError).accessDeniedHandler(jwtAuthError).and()
//                // 下面开始设置权限
//                .authorizeRequests(authorize -> authorize
//                        // 请求放开
//                        .antMatchers("/**").permitAll()
//                        .antMatchers("/**").permitAll()
//                        // 其他地址的访问均需验证权限
//                        .anyRequest().authenticated()
//                )
//                // 添加 JWT 过滤器，JWT 过滤器在用户名密码认证过滤器之前
//                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
//                // 认证用户时用户信息加载配置，注入springAuthUserService
//                .userDetailsService(xxxAuthUserService)
//                .build();
    }
//
//    /**
//     * 配置跨源访问(CORS)
//     * @return
//     */
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }

}