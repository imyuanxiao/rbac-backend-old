package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.Permission;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.mapper.PermissionMapper;
import com.imyuanxiao.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
* @author Administrator
* @description 针对表【permission】的数据库操作Service实现
* @createDate 2023-05-03 00:42:19
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

    @Autowired
    RoleService roleService;

    @Override
    public Set<String> getPathByUserId(Long id) {
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 通过user_role获取role
        // 通过role_permission获取permission

        return null;
    }
}




