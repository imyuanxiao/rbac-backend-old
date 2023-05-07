package com.imyuanxiao.rbac.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.annotation.Auth;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.param.UserParam;
import com.imyuanxiao.rbac.model.vo.UserPageVO;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Set;

/**
 * @ClassName UserController
 * @Description 用户接口
 * @Author imyuanxiao
 * @Date 2023/5/3 0:54
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "User management interface")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "Add user")
    @PostMapping
    @Auth(id = 1, name = "新增用户")
    public String createUser(@RequestBody @Validated(UserParam.CreateUser.class) UserParam param) {
        userService.createUser(param);
        return "Action successful.";
    }

    @ApiOperation(value = "Delete user")
    @DeleteMapping
    @Auth(id = 2, name = "删除用户")
    public String deleteUser(Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
        userService.removeByIds(Arrays.asList(ids));
        return "Action successful.";
    }

    @ApiOperation(value = "Update user")
    @PutMapping
    @Auth(id = 3, name = "编辑用户")
    public String updateRoles(@RequestBody @Validated(UserParam.Update.class) UserParam param) {
        userService.update(param);
        return "Action successful.";
    }

    @ApiOperation(value = "Get user info based on user ID")
    @GetMapping("/user/{id}")
    public User getUserById(@ApiParam(value = "User ID", required = true)
                            @PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @ApiOperation(value = "Get permissions based on user ID")
    @GetMapping("/permissions/{id}")
    public Set<Long> getPermissionsByUserId(@PathVariable("id") Long userId) {
        return permissionService.getIdsByUserId(userId);
    }

    @ApiOperation(value = "Page through user information.")
    @GetMapping("/page/{current}")
    public IPage<UserPageVO> getPage(@PathVariable("current") int current) {
        // 设置分页参数
        Page<UserPageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("id");
        page.setCurrent(current).addOrder(orderItem);
        return userService.selectPage(page);
    }

}
