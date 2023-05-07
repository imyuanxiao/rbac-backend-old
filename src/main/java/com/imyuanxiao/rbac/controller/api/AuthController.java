package com.imyuanxiao.rbac.controller.api;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.param.RegisterParam;
import com.imyuanxiao.rbac.model.vo.UserVO;
import com.imyuanxiao.rbac.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @ClassName AuthController
 * @Description 登录注册接口
 * @Author imyuanxiao
 * @Date 2023/5/3 16:29
 * @Version 1.0
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @ApiOperation(value = "Login by password")
    @PostMapping("/login")
    public UserVO login(@RequestBody  @Valid LoginParam param){
        return userService.login(param);
    }

    @ApiOperation(value = "Get Verification Code")
    @GetMapping("/code/{phone}")
    public String sendCode(@PathVariable("phone") @NotBlank String phone){
        //TODO check whethere this is code for this phone in redis

        //TODO if phone is valid, send captcha to phone
        String code = RandomUtil.randomNumbers(4);
        //TODO send code to phone

        //TODO save phone + code to redis

        return "验证码已发送至手机号：" + phone + ",验证码为："+ code;
    }

    /**
     * 验证手机号和验证码
     *
     * @author imyuanxiao
     * @date 19:00 2023/5/6
     * @param param
     * @return com.imyuanxiao.rbac.model.vo.UserVO
     **/
    @ApiOperation(value = "Register")
    @PostMapping("/register")
    public UserVO register(@RequestBody @Valid RegisterParam param){
        //TODO get Code from redis according to phone
        //TODO verify code and phone
        checkValidationForRegister(param);
        return userService.register(param);
    }

    public void checkValidationForRegister(RegisterParam param){
        if(StrUtil.isBlank(param.getPhone())){
            throw new ApiException(ResultCode.VALIDATE_FAILED, "手机号为空或格式不正确！");
        }
        if(StrUtil.isBlank(param.getPassword())){
            throw new ApiException(ResultCode.VALIDATE_FAILED, "密码为空或格式不正确！");
        }
        if(StrUtil.isBlank(param.getCode())){
            throw new ApiException(ResultCode.VALIDATE_FAILED, "验证码为空或格式不正确！");
        }
    }

}
