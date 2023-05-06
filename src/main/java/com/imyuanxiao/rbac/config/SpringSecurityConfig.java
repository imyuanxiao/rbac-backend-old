package com.imyuanxiao.rbac.config;

import com.imyuanxiao.rbac.security.AuthFilter;
import com.imyuanxiao.rbac.security.MyDeniedHandler;
import com.imyuanxiao.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * 对Spring Security进行自定义配置
 * @Author: imyuanxiao
 * @Date: 2023/5/3 15:35
 */
@Configuration
@EnableWebSecurity	// 添加 security 过滤器
//@RequiredArgsConstructor // 自动创建需要的成员变量构造器
//@EnableGlobalMethodSecurity(prePostEnabled = true)	// 启用方法级别的权限认证
public class SpringSecurityConfig {

    @Autowired
    private AuthFilter jwtAuthFiler;

    @Autowired
    private UserService userService;

//    private final AuthenticationProvider authenticationProvider;


    @Bean
    @Lazy
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 关闭csrf和frameOptions，如果不关闭会影响前端请求接口
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // 开启跨域以便前端调用接口
        http.cors();

        // 这是配置的关键，决定哪些接口开启防护，哪些接口绕过防护
        http.authorizeRequests()
                // 注意这里，是允许前端跨域联调的一个必要配置
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 指定某些接口不需要通过验证即可访问。像登陆、注册接口肯定是不需要认证的
                .antMatchers("/login/**").permitAll()
                // 这里意思是其它所有接口需要认证才能访问
                .antMatchers("/**").authenticated()
                // 指定认证错误处理器
                .and().exceptionHandling().authenticationEntryPoint(new MyEntryPoint()).accessDeniedHandler(new MyDeniedHandler());

        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        // 将我们自定义的认证过滤器替换掉默认的认证过滤器
//        http.addFilterBefore(jwtAuthFiler, UsernamePasswordAuthenticationFilter.class);
        // 将我们自定义的认证过滤器替换掉默认的认证过滤器
        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authFilter, FilterSecurityInterceptor.class);

        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService::getUserDetailsVO);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * 密码明文加密方式配置
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}