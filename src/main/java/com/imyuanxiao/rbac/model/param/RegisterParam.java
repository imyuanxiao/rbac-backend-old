package com.imyuanxiao.rbac.model.param;

import com.imyuanxiao.rbac.annotation.ExceptionCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @ClassName RegisterParam
 * @Description Receive registration-related parameters.
 * @Author imyuanxiao
 * @Date 2023/5/3 0:58
 * @Version 1.0
 **/
@Data
public class RegisterParam {


    @NotBlank(message = "Phone number is required.")
    @Length(min = 8, max = 20, message = "Phone number must be between 8-20 digits in length.")
    @ExceptionCode(value = 100004, message = "Invalid phone number.")
    private String phone;

    @NotBlank(message = "Password is required.")
    @Length(min = 4, max = 20, message = "Password must be between 4-20 characters in length.")
    @ExceptionCode(value = 100003, message = "Invalid password.")
    private String password;

    @NotBlank(message = "Verification code is required.")
    @Pattern(regexp = "\\d{4}", message = "Verification code must be 4 digits.")
    @ExceptionCode(value = 100005, message = "Wrong code.")
    private String code;

}
