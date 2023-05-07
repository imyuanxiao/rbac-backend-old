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

    /**
     * 登录
     * @author imyuanxiao
     * @date 11:46 2023/5/7
     * @param param 登录表单参数
     * @return com.imyuanxiao.rbac.model.vo.UserVO
     **/
    @ApiOperation(value = "Login by password")
    @PostMapping("/login")
    public UserVO login(@RequestBody  @Valid LoginParam param){
        return userService.login(param);
    }

    /**
     * 发送验证码
     * @author imyuanxiao
     * @date 11:46 2023/5/7
     * @param phone 手机号
     * @return java.lang.String
     **/
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
     * 注册
     * @author imyuanxiao
     * @date 19:00 2023/5/6
     * @param param 注册表单参数
     * @return com.imyuanxiao.rbac.model.vo.UserVO
     **/
    @ApiOperation(value = "Register")
    @PostMapping("/register")
    public UserVO register(@RequestBody @Valid RegisterParam param){
        //TODO get Code from redis according to phone
        //TODO verify code and phone
        return userService.register(param);
    }


}
