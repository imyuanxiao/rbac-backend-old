package com.imyuanxiao.rbac.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @ClassName RoleParam
 * @Description Receive role-related parameters.
 * @Author imyuanxiao
 * @Date 2023/5/6 19:13
 * @Version 1.0
 **/
@Data
public class RoleParam {
    @NotNull(message = "User ID is required", groups = UpdateResources.class)
    private Long id;

    @NotBlank(message = "Role name is required", groups = CreateRole.class)
    @Length(min = 1, max = 12, message = "Role name must be between 1 and 12 characters in length.", groups = CreateRole.class)
    private String name;

    private Set<Long> permissionIds;

    public interface CreateRole {
    }

    public interface UpdateResources {
    }
}