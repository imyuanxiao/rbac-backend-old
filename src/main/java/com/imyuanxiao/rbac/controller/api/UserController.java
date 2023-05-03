package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "Get user by ID")
    @GetMapping("/{id}")
    public ResultVO<User> getUserById(@ApiParam(value = "User ID", required = true)
                                  @PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @ApiOperation(value = "Get user by ID, return User")
    @GetMapping("/user/{id}")
    public User getUserByIdReturnUser(@ApiParam(value = "User ID", required = true)
                                      @PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @ApiOperation(value = "Get user by ID, return String")
    @GetMapping("/string/{id}")
    public String getUserByIdReturnString(@ApiParam(value = "User ID", required = true)
                                      @PathVariable("id") Long id) {
        return userService.getById(id).toString();
    }

}
