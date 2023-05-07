package com.imyuanxiao.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.rbac.model.param.RoleParam;
import com.imyuanxiao.rbac.model.vo.RolePageVO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @ClassName RoleService
 * @Description Database operation service for table 'role'.
 * @Author imyuanxiao
 * @Date 2023/5/3 21:41
 * @Version 1.0
 **/
public interface RoleService extends IService<Role> {
    /**
     * Get role ID based on user ID.
     * @param userId User ID
     * @return Collection of role IDs for this user.
     */
    Set<Long> getIdsByUserId(Long userId);


    /**
     * Get all information about roles based on user ID.
     * @param userId User ID
     * @return Collection of Roles for this user.
     */
    Set<Role> getRolesByUserId(Long userId);

    /**
     * Delete all roles based on user ID
     * @param userId User ID
     */
    void removeByUserId(Serializable userId);

    /**
     * Batch add roles based on user ID.
     * @param userId User ID
     * @param roleIds Collections of role ids
     */
    void insertRolesByUserId(Long userId, Collection<Long> roleIds);

    /**
     * Get pagination information
     * @param page Pagination parameters
     * @return Pagination object
     */
    IPage<RolePageVO> selectPage(Page<RolePageVO> page);

    /**
     * Update r
     * @param param 入参
     */
    void updatePermissions(RoleParam param);

    /**
     * 创建角色
     * @param param 入参
     */
    void createRole(RoleParam param);


}
