package com.imyuanxiao.rbac.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.imyuanxiao.rbac.model.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @ClassName UserVO
 * @Description User Value Object
 * @Author imyuanxiao
 * @Date 2023/5/4 15:05
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class UserVO {

    private Long id;

    private String username;

    private String phone;

    private String email;

    private String avatar;

    private Set<Long> roleIds;

    private Set<Long> permissionIds;

    private String token;

}
