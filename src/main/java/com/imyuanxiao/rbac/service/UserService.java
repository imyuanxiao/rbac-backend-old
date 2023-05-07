package com.imyuanxiao.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.param.RegisterParam;
import com.imyuanxiao.rbac.model.param.UserParam;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import com.imyuanxiao.rbac.model.vo.UserPageVO;
import com.imyuanxiao.rbac.model.vo.UserVO;

/**
 * @ClassName UserService
 * @Description Database operation service for table "user".
 * @Author imyuanxiao
 * @Date 2023/5/3 21:42
 * @Version 1.0
 **/
public interface UserService extends IService<User> {


    User getUserByUsername(String username);

    /**
     * Log in
     * @author imyuanxiao
     * @date 11:49 2023/5/7
     * @param loginParam Login form parameters
     * @return If the login is successful, the VO object is returned, and an exception is thrown if it fails
     **/
    UserVO login(LoginParam loginParam);

    /**
     * Register
     * @author imyuanxiao
     * @date 11:49 2023/5/7
     * @param registerParam Register form parameters
     * @return If the register is successful, the VO object is returned, and an exception is thrown if it fails
     **/
    UserVO register(RegisterParam registerParam);

    /**
     * Add new user
     * @author imyuanxiao
     * @date 11:56 2023/5/7
     * @param param user form parameters
     **/
    void createUser(UserParam param);

    /**
     * Update user information
     * @author imyuanxiao
     * @date 11:56 2023/5/7
     * @param param user form parameters
     **/
    void update(UserParam param);

    /**
     * Get pagination information
     * @author imyuanxiao
     * @date 11:55 2023/5/7
     * @param page pagination parameters
     * @return pagination object
     **/
    IPage<UserPageVO> selectPage(Page<UserPageVO> page);

}

