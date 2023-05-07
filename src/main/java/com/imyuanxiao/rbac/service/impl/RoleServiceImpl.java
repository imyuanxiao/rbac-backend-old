package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.mapper.PermissionMapper;
import com.imyuanxiao.rbac.model.entity.Role;
import com.imyuanxiao.rbac.model.param.RoleParam;
import com.imyuanxiao.rbac.model.vo.RolePageVO;
import com.imyuanxiao.rbac.service.RoleService;
import com.imyuanxiao.rbac.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * @ClassName RoleServiceImpl
 * @Description Database operation Service implementation for the table "role".
 * @Author imyuanxiao
 * @Date 2023/5/3 21:41
 * @Version 1.0
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Set<Long> getIdsByUserId(Long userId) {
        return baseMapper.selectIdsByUserId(userId);
    }

    @Override
    public Set<Role> getRolesByUserId(Long userId) {
        Set<Long> roleIds = baseMapper.selectIdsByUserId(userId);
        Set<Role> roleList = new HashSet<>();
        for(Long roleId: roleIds){
            roleList.add(this.getById(roleId));
        }
        return roleList;
    }

    @Override
    public void removeByUserId(Serializable userId) {
        baseMapper.deleteById(userId);
    }

    @Override
    public void insertRolesByUserId(Long userId, Collection<Long> roleIds) {
        baseMapper.insertRolesByUserId(userId, roleIds);
    }

    @Override
    public IPage<RolePageVO> selectPage(Page<RolePageVO> page) {
        QueryWrapper<RolePageVO> queryWrapper = new QueryWrapper<>();
        // 获取分页列表
        IPage<RolePageVO> pages = baseMapper.selectPage(page, queryWrapper);
        // 再查询各角色的权限
        for (RolePageVO vo : pages.getRecords()) {
            vo.setPermissionIds(permissionMapper.selectIdsByRoleId(vo.getId()));
        }
        return pages;
    }

    @Override
    public void updatePermissions(RoleParam param) {
        // 先删除原有数据
        permissionMapper.deleteByRoleId(param.getId());
        // 如果角色为空就代表删除所有角色，不用后面新增流程了
        if (CollectionUtil.isEmpty(param.getPermissionIds())) {
            return;
        }
        // 再新增数据
        permissionMapper.insertPermissionsByRoleId(param.getId(), param.getPermissionIds());
    }

    @Override
    public void createRole(RoleParam param) {
        if (lambdaQuery().eq(Role::getName, param.getName()).one() != null) {
            throw new ApiException(ResultCode.FAILED, "角色名重复");
        }
        // 新增角色
        Role role = new Role().setName(param.getName());
        save(role);
        if (CollectionUtil.isEmpty(param.getPermissionIds())) {
            return;
        }
        // 再新增权限数据
        permissionMapper.insertPermissionsByRoleId(role.getId(), param.getPermissionIds());
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            return false;
        }
        // 删除角色下所属的权限
        for (Object roleId : idList) {
            permissionMapper.deleteByRoleId((int)roleId);
        }
        // 删除角色
        baseMapper.deleteBatchIds(idList);
        return true;
    }

}




