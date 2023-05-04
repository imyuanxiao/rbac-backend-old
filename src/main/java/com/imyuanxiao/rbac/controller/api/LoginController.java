package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.vo.UserVO;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    private UserService userService;

    @ApiOperation(value = "Login")
    @PostMapping("/pwd")
    public UserVO login(@RequestBody @Validated LoginParam loginParam){
        return userService.login(loginParam);
    }




}
