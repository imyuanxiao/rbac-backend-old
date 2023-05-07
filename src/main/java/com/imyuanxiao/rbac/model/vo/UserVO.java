package com.imyuanxiao.rbac.model.vo;

import com.imyuanxiao.rbac.model.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @ClassName UserVO
 * @Description 用户对象
 * @Author imyuanxiao
 * @Date 2023/5/4 15:05
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class UserVO {

    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 登录认证token
     */
    private String token;
    /**
     * 当前用户的角色
     */
    private Set<Role> roles;
    /**
     * 当前用户的权限资源id集合
     */
    private Set<Long> permissionIds;


}
