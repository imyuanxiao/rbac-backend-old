package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 16:29
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "LoginByUsername")
    @PostMapping("/username")
    public String loginByUsername(@RequestBody LoginParam loginParam){

        if("admin".equals(loginParam.getUsername()) && "admin".equals(loginParam.getPassword())){
            return JwtUtil.generate(loginParam.getUsername());

        }

        return "查无此人";

    }



}
