package com.imyuanxiao.rbac.service;

import com.imyuanxiao.rbac.model.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【permission】的数据库操作Service
* @createDate 2023-05-03 00:42:19
*/
public interface PermissionService extends IService<Permission> {
    /**
     * 根据用户id获取该用户的所有权限id
     * @param userId 用户id
     * @return 权限id集合
     */
    Set<Long> getIdsByUserId(Long userId);

    /**
     * 批量新增接口类型的资源
     * @param resources 资源对象集合
     */
    void insertPermissions(Collection<Permission> resources);

    /**
     * 根据类型删除资源
     * @param type 资源类型，0为页面权限，1为操作权限
     */
    void deletePermissionByType(int type);

    /**
     * 根据用户id获取该用户的所有权限资源对象
     * @param userId 用户id
     * @return 权限资源集合
     */
    List<Permission> getPermissionsByUserId(Long userId);


}
