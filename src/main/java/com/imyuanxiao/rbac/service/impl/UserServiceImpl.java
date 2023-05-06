package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.entity.Role;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import com.imyuanxiao.rbac.model.vo.UserVO;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.service.RoleService;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.mapper.UserMapper;
import com.imyuanxiao.rbac.security.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-05-03 00:42:01
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService, UserDetailsService {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

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
                .setToken(JwtManager.generate(user.getUsername()))
                .setPermissionIds(permissionService.getIdsByUserId(user.getId()));
        return userVO;
    }

//    @Override
//    public UserDetailsVO getUserDetailsVO(String username) {
//        // get user info by username
//        User user = this.getUserByUsername(username);
//        // get user roles by user id
//        Set<Role> roleSet = roleService.roleListByUsername(user.getId());
//        return new UserDetailsVO().setUser(user).setRoles(roleSet);
//    }

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
        if(StrUtil.isBlank(username)){
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
        return this.lambdaQuery().eq(User::getUsername, username).one();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user info by username
        User user = this.getUserByUsername(username);
        // get user roles by user id
        Set<Role> roleSet = roleService.roleListByUsername(user.getId());
        return new UserDetailsVO().setUser(user).setRoles(roleSet);
    }
}




