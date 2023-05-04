package com.imyuanxiao.rbac.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/4 15:18
 */
@Data
public class UserPageVO {
    private Long id;
    private String username;
    private Set<Long> roleIds;
//    private Set<Long> companyIds;
}
