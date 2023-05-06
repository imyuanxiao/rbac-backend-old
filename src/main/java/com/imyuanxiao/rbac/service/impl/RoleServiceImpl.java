package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.Role;
import com.imyuanxiao.rbac.service.RoleService;
import com.imyuanxiao.rbac.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【role】的数据库操作Service实现
* @createDate 2023-05-03 00:41:26
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Override
    public Set<Role> roleListByUsername(Long id) {
        Set<Long> roleIds = baseMapper.selectIdsByUserId(id);
        Set<Role> roleList = new HashSet<>();
        for(Long roleId: roleIds){
            roleList.add(this.getById(roleId));
        }
        return roleList;
    }

}




