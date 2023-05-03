package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.annotation.NotResponseBody;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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


    @ApiOperation(value = "测试token解析与验证")
    @GetMapping("/token")
    public String testToken(HttpServletRequest request) {
        // 从请求头中获取token字符串
        String token = request.getHeader("Authorization");
        // 解析失败就提示用户登录
        if (JwtUtil.parse(token) == null) {
            return "请先登录";
        }
        // 解析成功就执行业务逻辑返回数据
        return "api成功返回数据";
    }


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

    @NotResponseBody
    @ApiOperation(value = "Get user by ID, return String")
    @GetMapping("/string/{id}")
    public String getUserByIdReturnString(@ApiParam(value = "User ID", required = true)
                                      @PathVariable("id") Long id) {
        return userService.getById(id).toString();
    }


}
