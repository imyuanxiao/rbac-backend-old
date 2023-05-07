package com.imyuanxiao.rbac.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * @ClassName UserPageVO
 * @Description 用户分页对象
 * @Author imyuanxiao
 * @Date 2023/5/4 15:18
 * @Version 1.0
 **/
@Data
public class UserPageVO {
    private Long id;
    private String username;
    private Set<Long> roleIds;
//    private Set<Long> companyIds;
}
