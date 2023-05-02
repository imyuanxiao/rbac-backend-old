package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.Role;
import com.imyuanxiao.rbac.service.RoleService;
import com.imyuanxiao.rbac.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【role】的数据库操作Service实现
* @createDate 2023-05-03 00:23:55
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




