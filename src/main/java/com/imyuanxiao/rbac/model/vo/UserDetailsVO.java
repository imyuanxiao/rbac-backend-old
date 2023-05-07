package com.imyuanxiao.rbac.model.vo;

import com.imyuanxiao.rbac.model.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @ClassName UserDetailsVO
 * @Description UserDetails对象，用于spring security
 * @Author imyuanxiao
 * @Date 2023/5/4 22:44
 * @Version 1.0
 **/
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserDetailsVO extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserDetailsVO(User user, Collection<? extends GrantedAuthority> authorities) {
        // 必须调用父类的构造方法，初始化用户名、密码、权限
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }

}
