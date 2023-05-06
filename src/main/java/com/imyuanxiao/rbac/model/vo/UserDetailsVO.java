package com.imyuanxiao.rbac.model.vo;

import com.imyuanxiao.rbac.model.entity.Role;
import com.imyuanxiao.rbac.model.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义用户对象
 * @Author: imyuanxiao
 * @Date: 2023/5/4 22:44
 */
@Data
@Accessors(chain = true)
public class UserDetailsVO implements UserDetails {

    private User user;

    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将用户所拥有的角色权限名称生成一个集合
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 用户是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户是否被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户凭证是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否被禁用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
