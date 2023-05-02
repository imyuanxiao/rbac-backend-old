package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.Permission;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【permission】的数据库操作Service实现
* @createDate 2023-05-03 00:24:14
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService {

}




