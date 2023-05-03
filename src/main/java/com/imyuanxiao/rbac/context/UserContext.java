package com.imyuanxiao.rbac.context;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 20:12
 */
public final class UserContext {
    private static final ThreadLocal<String> USER_HOLDER = new ThreadLocal<String>();

    public static void set(String userName) {
        USER_HOLDER.set(userName);
    }

    public static void remove() {
        USER_HOLDER.remove();
    }

    /**
     * @return 当前登录用户的用户名
     */
    public static String get() {
        return USER_HOLDER.get();
    }
}
