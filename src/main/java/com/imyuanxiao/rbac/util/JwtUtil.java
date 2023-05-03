package com.imyuanxiao.rbac.util;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 16:23
 */
@Slf4j
public final class JwtUtil {
    /**
     * 这个秘钥是防止JWT被篡改的关键，随便写什么都好，但决不能泄露
     */
    private final static String secretKey = "my_secret_key";
    /**
     * 过期时间目前设置成2天，这个配置随业务需求而定
     */
    private final static Duration expiration = Duration.ofHours(2);

    /**
     * 生成JWT
     *
     * @param userName 用户名
     * @return JWT
     */
    public static String generate(String userName) {
        // 过期时间
        Date expiryDate = new Date(System.currentTimeMillis() + expiration.toMillis());

        return JWT.create()
                .setPayload("sub", userName) // 将userName放进JWT
                .setPayload("iat", System.currentTimeMillis()) // 设置JWT签发时间
                .setPayload("exp", expiryDate.getTime())  // 设置过期时间
                .sign(JWTSignerUtil.createSigner("HS256", secretKey.getBytes()));// 设置加密算法和秘钥
    }

    /**
     * 解析JWT
     *
     * @param token JWT字符串
     * @return 解析成功返回Claims对象，解析失败返回null
     */
    public static JWTPayload parse(String token) {

        // 如果是空字符串直接返回null
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        JWT jwt = null;
        // 解析失败了会抛出异常，所以要捕捉一下。token过期、token非法都会导致解析失败
        try {
            jwt = JWTUtil.parseToken(token);
        } catch (Exception e) {
            log.error("token解析失败");
        }
        return jwt == null ? null : jwt.getPayload() ;
    }
}