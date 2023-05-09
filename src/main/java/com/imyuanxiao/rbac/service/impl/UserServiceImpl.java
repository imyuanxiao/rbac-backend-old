package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.param.RegisterParam;
import com.imyuanxiao.rbac.model.param.UserParam;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import com.imyuanxiao.rbac.model.vo.UserPageVO;
import com.imyuanxiao.rbac.model.vo.UserVO;
import com.imyuanxiao.rbac.service.PermissionService;
import com.imyuanxiao.rbac.service.RoleService;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.mapper.UserMapper;
import com.imyuanxiao.rbac.security.JwtManager;
import com.imyuanxiao.rbac.util.SecurityContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName UserServiceImpl
 * @Description Database operation Service implementation for the table "user".
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
        // Verify user from database
        User user = this.lambdaQuery()
                .eq(StrUtil.isNotBlank(loginParam.getUsername()), User::getUsername, loginParam.getUsername())
                .one();

        // Throw error if user or password is wrong
        if(user == null || !passwordEncoder.matches(loginParam.getPassword(), user.getPassword())){
            throw new ApiException(ResultCode.VALIDATE_FAILED, "Username or password is incorrect！");
        }
        // Generate token, get and put user permissions in UserVO object
        return getUserVO(user);
    }
    
    @Override
    public UserVO register(RegisterParam param) {
        // Use phone and code to register, initial username is phone number
        User user = new User().setUsername(param.getPhone())
                .setPassword(passwordEncoder.encode(param.getPassword()))
                .setPhone(param.getPhone());
        try {
            this.save(user);
            // Add default user role - 3L visitor
            roleService.insertRolesByUserId(user.getId(), List.of(3L));
            // Get permissions id
            Set<Long> permissionIds = permissionService.getIdsByUserId(user.getId());
            // Put user info, token, permissions in UserVO object
            return getUserVO(user);
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED, "Phone number already exists.");
        }
    }

    @Override
    public UserVO me() {
        User user = SecurityContextUtil.getCurrentUser();
        return getUserVO(user);
    }

    private UserVO getUserVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        userVO.setToken(JwtManager.generate(user.getUsername()))
                .setRoles(roleService.getIdsByUserId(user.getId()))
                .setPermissionIds(permissionService.getIdsByUserId(user.getId()));
        return userVO;
    }

    @Override
    public void createUser(UserParam param) {
        if (lambdaQuery().eq(User::getUsername, param.getUsername()).one() != null) {
            throw new ApiException(ResultCode.FAILED,"Username already exists.");
        }
        User user = new User();
        // Default password 12345
        user.setUsername(param.getUsername()).setPassword(passwordEncoder.encode("12345"));
        save(user);
        if (CollectionUtil.isEmpty(param.getRoleIds())) {
            return;
        }
        // Add info in table [user-role]
        roleService.insertRolesByUserId(user.getId(), param.getRoleIds());
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            return false;
        }
        // Delete info from table [user-role]
        for (Object userId : idList) {
            roleService.removeByUserId((Long)userId);
        }
        // Delete user
        baseMapper.deleteBatchIds(idList);
        return true;
    }

    @Override
    public void update(UserParam param) {
        updateRoles(param);
    }

    private void updateRoles(UserParam param) {
        // Delete the original user role
        roleService.removeByUserId(param.getId());
        // If roleIds is empty, delete all roles for this user
        if (CollectionUtil.isEmpty(param.getRoleIds())) {
            return;
        }
        // If roleIds not empty, add new roles for this user
        roleService.insertRolesByUserId(param.getId(), param.getRoleIds());
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
        // Get user by username
        User user = this.getUserByUsername(username);
        // Get permissionIds and tranfer them to `SimpleGrantedAuthority` Object
        Set<SimpleGrantedAuthority> authorities = permissionService.getIdsByUserId(user.getId())
                .stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new UserDetailsVO(user, authorities);
    }

    @Override
    public IPage<UserPageVO> selectPage(Page<UserPageVO> page) {
        QueryWrapper<UserPageVO> queryWrapper = new QueryWrapper<>();
        // Don't show super admin (id:1) and current user
        Long myId = SecurityContextUtil.getCurrentUserId();
        queryWrapper.ne("id", myId).ne("id", 1);
        // Get page info
        IPage<UserPageVO> pages = baseMapper.selectPage(page, queryWrapper);
        // Get rolse for all users
        for (UserPageVO vo : pages.getRecords()) {
            vo.setRoleIds(roleService.getIdsByUserId(vo.getId()));
//            vo.setCompanyIds(companyService.getIdsByUserId(vo.getId()));
        }
        return pages;
    }



}




