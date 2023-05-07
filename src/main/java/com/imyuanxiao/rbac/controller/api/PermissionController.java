package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.model.entity.Permission;
import com.imyuanxiao.rbac.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @ClassName PermissionController
 * @Description 权限接口
 * @Author imyuanxiao
 * @Date 2023/5/4 15:49
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 获得所有权限信息
     * */
    @ApiOperation(value = "Get all permissions")
    @GetMapping("/list")
    public List<Permission> getPermissionList() {
        return permissionService.list();
    }

}
