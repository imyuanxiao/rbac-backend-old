package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.model.vo.UserVO;
import com.imyuanxiao.rbac.service.PermissionService;
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

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserVO login(LoginParam loginParam) {
        // 从数据库验证用户信息
        User user = this.lambdaQuery()
                .eq(StrUtil.isNotBlank(loginParam.getUsername()), User::getUsername, loginParam.getUsername())
                .eq(StrUtil.isNotBlank(loginParam.getPhone()), User::getPhone, loginParam.getPhone())
                .eq(StrUtil.isNotBlank(loginParam.getPassword()), User::getPassword, loginParam.getPassword())
                .one();

        // no such user, throw error
        if(user == null){
            throw new ApiException(ResultCode.VALIDATE_FAILED, "账号或密码错误！");
        }
        // 验证通过，生成token，获取用户权限，放在UserVO中
        UserVO userVO = new UserVO();
        userVO.setId(user.getId()).setUsername(user.getUsername())
                .setToken(JwtUtil.generate(user.getUsername()))
                .setPermissionIds(permissionService.getIdsByUserId(user.getId()));
        return userVO;
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




