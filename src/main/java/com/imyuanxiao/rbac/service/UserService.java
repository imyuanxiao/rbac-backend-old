package com.imyuanxiao.rbac.service;

import com.imyuanxiao.rbac.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import com.imyuanxiao.rbac.model.vo.UserVO;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service
* @createDate 2023-05-03 00:42:01
*/
public interface UserService extends IService<User> {

    /**
     * 登录
     * @param id 用户ID
     * @return 登录成功则返回vo对象，失败则抛出异常
     */
    ResultVO<User> getUserById(Long id);


    User getUserByUsername(String username);

    UserVO login(LoginParam loginParam);
//
//    UserDetailsVO getUserDetailsVO(String username);
}
