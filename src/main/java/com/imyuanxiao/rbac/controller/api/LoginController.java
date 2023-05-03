package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.APIException;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 16:29
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    PermissionService permissionService;

    @ApiOperation(value = "LoginByUsername")
    @PostMapping("/username")
    public Set<String> loginByUsername(@RequestBody LoginParam loginParam, HttpServletResponse response){

        //从数据库验证用户信息，验证通过，生成token，放在响应体请求头中
        User user = userService.login(loginParam);

        return null;
    }


//    @ApiOperation(value = "LoginByUsername")
//    @PostMapping("/username")
//    public String loginByUsername(@RequestBody LoginParam loginParam){
//
//        if("admin".equals(loginParam.getUsername()) && "admin".equals(loginParam.getPassword())){
//            return JwtUtil.generate(loginParam.getUsername());
//        }
//
//        return "查无此人";
//
//    }



}
