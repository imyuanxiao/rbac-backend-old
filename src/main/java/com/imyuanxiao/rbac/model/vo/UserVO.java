package com.imyuanxiao.rbac.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/4 15:05
 */
@Data
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
     * 当前用户的权限资源id集合
     */
    private Set<Long> permissionIds;

}
