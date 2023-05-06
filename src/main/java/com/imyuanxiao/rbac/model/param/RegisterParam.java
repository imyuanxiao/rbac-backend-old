package com.imyuanxiao.rbac.model.param;

import com.imyuanxiao.rbac.annotation.ExceptionCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 0:58
 */

@Data
public class RegisterParam {


    @NotBlank(message = "手机号不能为空")
    @Length(min = 8, max = 20, message = "手机号长度为8-20位")
    @ExceptionCode(value = 100004, message = "手机号验证错误")
    private String phone;

    @NotBlank(message = "密码不能为空")
    @Length(min = 4, max = 12, message = "密码长度为4-12位")
    @ExceptionCode(value = 100003, message = "密码验证错误")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "\\d{4}", message = "验证码必须是4位数字")
    @ExceptionCode(value = 100005, message = "验证码错误")
    private String code;

}
