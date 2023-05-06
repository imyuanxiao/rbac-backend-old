package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.context.UserContext;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.security.JwtManager;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/4 17:51
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @ApiOperation(value = "根据传入参数生成token")
    @GetMapping("/token-generate/{username}")
    public String testTokenGenerate(@PathVariable("username") String username) {
        // 生成token并返回
        return JwtManager.generate(username);
    }

    @ApiOperation(value = "测试token解析，需在请求头中加入token")
    @GetMapping("/token-verify")
    public String testTokenVerify() {
        // 解析成功就执行业务逻辑返回数据
        return "api成功返回数据";
    }

    @ApiOperation(value = "测试上下文对象")
    @GetMapping("/context")
    public String testUsercontext() {
        // token解析成功，会把解析出的用户放入userContext
        String userName = UserContext.get();
        return "当前用户为：" + userName;
    }

    @ApiOperation(value = "测试srping security身份验证")
    @PostMapping
    public String testLogin(@RequestBody LoginParam loginParam){
        // 生成一个包含账号密码的认证信息
        Authentication token = new UsernamePasswordAuthenticationToken(loginParam.getUsername(), loginParam.getPassword());
        // AuthenticationManager校验这个认证信息，返回一个已认证的Authentication
        Authentication authentication = authenticationManager.authenticate(token);
        // 将返回的Authentication存到上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "登录成功";

    }

}
