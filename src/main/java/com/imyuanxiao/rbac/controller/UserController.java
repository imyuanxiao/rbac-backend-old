package com.imyuanxiao.rbac.controller;

import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 0:54
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Get user by ID", response = User.class)
    @GetMapping("/{id}")
    public String getUserById(@ApiParam(value = "User ID", required = true)
                                  @PathVariable("id") Long id) {
        return "";
    }

}
