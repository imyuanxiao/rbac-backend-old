package com.imyuanxiao.rbac.context;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 23:16
 */
public class TokenContext {
    private static final ThreadLocal<String> TOKEN_HOLDER = new ThreadLocal<String>();

    public static void set(String token) {
        TOKEN_HOLDER.set(token);
    }

    public static void remove() {
        TOKEN_HOLDER.remove();
    }

    /**
     * @return 当前登录用户的用户名
     */
    public static String get() {
        return TOKEN_HOLDER.get();
    }
}
