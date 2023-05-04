package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.context.UserContext;
import com.imyuanxiao.rbac.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/4 17:51
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "根据传入参数生成token")
    @GetMapping("/token-generate/{username}")
    public String testTokenGenerate(@PathVariable("username") String username) {
        // 生成token并返回
        return JwtUtil.generate(username);
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
}
