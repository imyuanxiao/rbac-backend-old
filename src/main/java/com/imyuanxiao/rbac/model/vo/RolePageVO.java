package com.imyuanxiao.rbac.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/4 15:18
 */
@Data
public class RolePageVO {
    private Long id;
    private String name;
    private Set<Long> permissionIds;
}
