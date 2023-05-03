package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.model.vo.ResultVO;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.mapper.UserMapper;
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
    public ResultVO<User> getUserById(Long id) {
        User user = this.getById(id);
        if(user == null){
            return new ResultVO<>(ResultCode.FAILED, null);
        }
        return new ResultVO<>(user);
    }
}




