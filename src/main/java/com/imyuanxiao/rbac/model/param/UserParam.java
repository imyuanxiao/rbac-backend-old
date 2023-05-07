package com.imyuanxiao.rbac.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName UserParam
 * @Description Receive user-related parameters.
 * @Author imyuanxiao
 * @Date 2023/5/7 11:12
 * @Version 1.0
 **/
@Data
public class UserParam {
    @NotNull(message = "UserID is required.", groups = Update.class)
    private Long id;

    @NotBlank(message = "Username is required.", groups = CreateUser.class)
    @Length(min = 4, max = 12, message = "Username must be between 4 and 12 characters in length.", groups = CreateUser.class)
    private String username;

    private List<Long> roleIds;

//    private List<Long> companyIds;

    public interface Update {}

    public interface CreateUser{}
}