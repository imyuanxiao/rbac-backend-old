package com.imyuanxiao.rbac.service;

import com.imyuanxiao.rbac.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.param.RegisterParam;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import com.imyuanxiao.rbac.model.vo.UserVO;

/**
 * @ClassName UserService
 * @Description 针对表【user】的数据库操作Service
 * @Author imyuanxiao
 * @Date 2023/5/3 21:42
 * @Version 1.0
 **/
public interface UserService extends IService<User> {


    User getUserByUsername(String username);

    UserVO login(LoginParam loginParam);

    UserVO register(RegisterParam param);

}
