package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.param.RegisterParam;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import com.imyuanxiao.rbac.model.vo.UserVO;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.service.RoleService;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.mapper.UserMapper;
import com.imyuanxiao.rbac.security.JwtManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName UserServiceImpl
 * @Description 针对表【user】的数据库操作Service实现
 * @Author imyuanxiao
 * @Date 2023/5/3 21:42
 * @Version 1.0
 **/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService, UserDetailsService {


    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserVO login(LoginParam loginParam) {
        // 从数据库验证用户信息
        User user = this.lambdaQuery()
                .eq(StrUtil.isNotBlank(loginParam.getUsername()), User::getUsername, loginParam.getUsername())
                .eq(StrUtil.isNotBlank(loginParam.getPhone()), User::getPhone, loginParam.getPhone())
                .one();

        // no such user or password is wrong, throw error
        if(user == null || !passwordEncoder.matches(loginParam.getPassword(), user.getPassword())){
            throw new ApiException(ResultCode.VALIDATE_FAILED, "账号或密码错误！");
        }

        // 验证通过，生成token，获取用户权限，放在UserVO中
        UserVO userVO = new UserVO();
        userVO.setId(user.getId()).setUsername(user.getUsername())
                .setToken(JwtManager.generate(user.getUsername()))
                .setPermissionIds(permissionService.getIdsByUserId(user.getId()));
        return userVO;
    }

    @Override
    public UserVO register(RegisterParam param) {
        // 手机号验证码注册，初始用户名为手机
        User user = new User().setUsername(param.getPhone())
                .setPassword(passwordEncoder.encode(param.getPassword()))
                .setPhone(param.getPhone());
        try {
            this.save(user);
            // 添加默认角色信息
            roleService.insertRolesByUserId(user.getId(), List.of(1L, 2L));
            // 根据角色信息获取权限信息
            Set<Long> permissionIds = permissionService.getIdsByUserId(user.getId());
            // 把User信息，token信息和permissionIds填入UserVO
            UserVO userVO = new UserVO();
            BeanUtil.copyProperties(user, userVO);
            userVO.setPermissionIds(permissionIds)
                    .setToken(JwtManager.generate(user.getUsername()));
            return userVO;
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED, "手机号已被使用！");
        }
    }

    @Override
    public User getUserByUsername(String username) {
        if(StrUtil.isBlank(username)){
            throw new UsernameNotFoundException("没有找到该用户");
        }
        return this.lambdaQuery().eq(User::getUsername, username).one();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user info by username
        User user = this.getUserByUsername(username);
        // 先将该用户所拥有的资源id全部查询出来，再转换成`SimpleGrantedAuthority`权限对象
        Set<SimpleGrantedAuthority> authorities = permissionService.getIdsByUserId(user.getId())
                .stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new UserDetailsVO(user, authorities);
    }

}




