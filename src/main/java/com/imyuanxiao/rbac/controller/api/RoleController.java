package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.model.entity.Role;
import com.imyuanxiao.rbac.service.RoleService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/4 17:35
 */
@Slf4j
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 获得所有角色信息
     * */
    @ApiOperation(value = "Get all roles")
    @GetMapping("/list")
    public List<Role> getRoleList() {
        return roleService.list();
    }

}
