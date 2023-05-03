package com.imyuanxiao.rbac.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 0:58
 */

@Data
public class LoginParam {

    @NotBlank(message = "用户名不能为空")
    @Length(min = 4, max = 12, message = "用户名长度为4-12位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 4, max = 12, message = "密码长度为4-12位")
    private String password;

}
