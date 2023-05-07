package com.imyuanxiao.rbac.util;

import com.imyuanxiao.rbac.model.vo.UserDetailsVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ClassName SecurityContextUtil
 * @Description TODO
 * @Author imyuanxiao
 * @Date 2023/5/7 12:44
 * @Version 1.0
 **/
public class SecurityContextUtil {

    /**
     * Get user ID from spring security context
     * @author imyuanxiao
     * @date 14:52 2023/5/7
     * @return java.lang.Long
     **/
    public static Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsVO userDetails = (UserDetailsVO)authentication.getPrincipal();
        return userDetails.getId();
    }

}
