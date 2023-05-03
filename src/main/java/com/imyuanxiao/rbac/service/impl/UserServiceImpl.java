package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.context.TokenContext;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.APIException;
import com.imyuanxiao.rbac.mapper.PermissionMapper;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.mapper.UserMapper;
import com.imyuanxiao.rbac.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-05-03 00:42:01
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public User login(LoginParam loginParam) {
        // 从数据库验证用户信息
        // 用户名和密码都为空，直接结束
        if(StrUtil.isBlank(loginParam.getUsername()) && StrUtil.isBlank(loginParam.getPhone())){
            throw new APIException(ResultCode.VALIDATE_FAILED);
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, loginParam.getUsername()).eq(User::getPhone, loginParam.getPhone());
        User user = this.getOne(lambdaQueryWrapper);
        if(user == null){
            throw new APIException(ResultCode.VALIDATE_FAILED, "账号或密码错误！");
        }
        //从数据库验证用户信息，验证通过，生成token，放在响应体请求头中
        String token = JwtUtil.generate(user.getUsername());
        TokenContext.set(token);
        return user;
    }

    @Override
    public ResultVO<User> getUserById(Long id) {
        User user = this.getById(id);
        if(user == null){
            return new ResultVO<>(ResultCode.FAILED, null);
        }
        return new ResultVO<>(user);
    }

    @Override
    public User getUserByUsername(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        // 通过实体类的属性名来指定查询条件，即字段上的TableFiled
        lambdaQueryWrapper.eq(User::getUsername, username);
        return this.getOne(lambdaQueryWrapper);
    }



}




