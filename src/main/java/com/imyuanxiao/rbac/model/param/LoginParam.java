package com.imyuanxiao.rbac.model.param;

import com.imyuanxiao.rbac.annotation.ExceptionCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName LoginParam
 * @Description Receive login-related parameters.
 * @Author imyuanxiao
 * @Date 2023/5/3 0:58
 * @Version 1.0
 **/
@Data
public class LoginParam {

    @Length(min = 4, max = 20, message = "Username must be between 4-20 characters in length.")
    @ExceptionCode(value = 100001, message = "Invalid username.")
    private String username;

    private String phone;

    @NotBlank(message = "Password is required.")
    @Length(min = 4, max = 20, message = "Password must be between 4-20 characters in length.")
    @ExceptionCode(value = 100003, message = "Invalid password.")
    private String password;

}
