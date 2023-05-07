package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.Permission;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.mapper.PermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @ClassName PermissionServiceImpl
 * @Description Database operation Service implementation for the table "permission".
 * @Author imyuanxiao
 * @Date 2023/5/3 21:42
 * @Version 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

    @Override
    public Set<Long> getIdsByUserId(Long userId) {
        return baseMapper.selectIdsByUserId(userId);
    }

    @Override
    public void insertPermissions(Collection<Permission> resources) {
        if(CollectionUtil.isEmpty(resources)){
            return;
        }
        baseMapper.insertPermissions(resources);
    }

    @Override
    public void deletePermissionByType(int type) {
        // 先删除所有接口类型的资源
        LambdaUpdateWrapper<Permission> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Permission::getType, type);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<Permission> getPermissionsByUserId(Long userId) {
        return baseMapper.selectListByUserId(userId);
    }


}




