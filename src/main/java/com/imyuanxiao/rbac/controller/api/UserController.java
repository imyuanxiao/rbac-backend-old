package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.context.UserContext;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 0:54
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Get user by ID, return User")
    @GetMapping("/user/{id}")
    public User getUserById(@ApiParam(value = "User ID", required = true)
                                      @PathVariable("id") Long id) {
        return userService.getById(id);
    }


    @ApiOperation(value = "测试token解析与验证")
    @GetMapping("/token")
    public String testToken() {
        // 解析成功就执行业务逻辑返回数据
        return "api成功返回数据";
    }

    @ApiOperation(value = "测试上下文对象")
    @GetMapping("/context")
    public String testUsercontext() {
        // 解析成功就执行业务逻辑返回数据
        String userName = UserContext.get();
        return "当前用户为：" + userName;
    }

}
