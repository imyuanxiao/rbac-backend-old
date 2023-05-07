package com.imyuanxiao.rbac.model.param;

import com.imyuanxiao.rbac.annotation.ExceptionCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 0:58
 */

@Data
public class LoginParam {

    @Length(min = 4, max = 12, message = "用户名长度为4-12位")
    @ExceptionCode(value = 100001, message = "账号验证错误")
    private String username;

    private String phone;

    @NotBlank(message = "密码不能为空")
    @Length(min = 4, max = 12, message = "密码长度为4-12位")
    @ExceptionCode(value = 100003, message = "密码验证错误")
    private String password;

}
