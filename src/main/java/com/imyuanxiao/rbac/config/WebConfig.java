package com.imyuanxiao.rbac.config;

import com.imyuanxiao.rbac.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 19:56
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor() {return new LoginInterceptor();}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置拦截器应用于哪些路径
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**");
    }
}
