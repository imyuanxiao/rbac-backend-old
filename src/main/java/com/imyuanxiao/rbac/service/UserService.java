package com.imyuanxiao.rbac.service;

import com.imyuanxiao.rbac.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.rbac.model.param.LoginParam;
import com.imyuanxiao.rbac.model.param.RegisterParam;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import com.imyuanxiao.rbac.model.vo.UserVO;

/**
* @author Administrator
* @description 针对表【user】的数据库操作Service
* @createDate 2023-05-03 00:42:01
*/
public interface UserService extends IService<User> {


    User getUserByUsername(String username);

    UserVO login(LoginParam loginParam);

    UserVO register(RegisterParam param);

}
