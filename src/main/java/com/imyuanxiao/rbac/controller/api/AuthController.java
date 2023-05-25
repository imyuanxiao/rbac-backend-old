package com.imyuanxiao.rbac.controller.api;

import cn.hutool.core.util.RandomUtil;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.param.RegisterParam;
import com.imyuanxiao.rbac.model.vo.UserVO;
import com.imyuanxiao.rbac.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Set;

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
@Api(tags = "Auth Management Interface")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Log in
     * @author imyuanxiao
     * @date 11:46 2023/5/7
     * @param param login-related parameters
     * @return User Value Object
     **/
    @PostMapping("/login")
    @ApiOperation(value = "Login by password")
    public UserVO login(@RequestBody  @Valid LoginParam param){
        return userService.login(param);
    }

    @GetMapping("/my-permission")
    @ApiOperation(value = "Get UserVO every time route changes")
    public Set<Long> myPermission(){
        return userService.myPermission();
    }

    @GetMapping("/update-token")
    @ApiOperation(value = "Update token")
    public String updateToken(){
        return userService.updateToken();
    }

    /**
     * Send verification code
     * @author imyuanxiao
     * @date 11:46 2023/5/7
     * @param phone phone number
     * @return show code in frontend (actually should be void)
     **/
    @GetMapping("/code/{phone}")
    @ApiOperation(value = "Get Verification Code")
    public String sendCode(@PathVariable("phone") @NotBlank String phone){
        //TODO check whethere this is code for this phone in redis

        //TODO if phone is valid, send captcha to phone
        String code = RandomUtil.randomNumbers(4);
        //TODO send code to phone

        //TODO save phone + code to redis

        return "验证码已发送至手机号：" + phone + ",验证码为："+ code;
    }

    /**
     * Registration
     * @author imyuanxiao
     * @date 19:00 2023/5/6
     * @param param registration-related parameters
     * @return User Value Object
     **/
    @PostMapping("/register/phone")
    @ApiOperation(value = "Register by phone")
    public UserVO register(@RequestBody @Valid RegisterParam param){
        //TODO get Code from redis according to phone
        //TODO verify code and phone
        return userService.register(param);
    }


}
