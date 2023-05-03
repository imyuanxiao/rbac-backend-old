package com.imyuanxiao.rbac.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.*;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
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
    private final static byte[] secretKeyBytes = "my_secret_key".getBytes();


    /**
     * 过期时间目前设置成30分钟，这个配置随业务需求而定
     */
    private final static Duration expiration = Duration.ofMinutes(30);

    /**
     * 生成JWT
     *
     * @param userName 用户名
     * @return JWT
     */
    public static String generate(String userName) {
        // 过期时间
        Instant expiryInstant = Instant.now().plus(expiration);
        Date expiryDate = Date.from(expiryInstant);

        return JWT.create().setSubject(userName) // 将userName放进JWT
                .setIssuedAt(DateUtil.date()) // 设置JWT签发时间
                .setExpiresAt(expiryDate) // 设置过期时间
                .sign(JWTSignerUtil.createSigner("HS256", secretKeyBytes));// 设置加密算法和秘钥
    }

    /**
     * 解析JWT
     *
     * @param token JWT字符串
     * @return 解析成功返回Claims对象，解析失败返回null
     */
    public static JWT parse(String token) {
        // 如果是空字符串直接返回null
        if(StrUtil.isBlank(token)){
            return null;
        }
        JWT jwt = null;
        // 解析失败了会抛出异常，所以要捕捉一下。token过期、token非法都会导致解析失败
        try {
            // 此处仅为解析，验证需要另外步骤
            jwt = JWTUtil.parseToken(token);
            System.out.println(jwt.getPayload().toString());
        } catch (Exception e) {
            log.error("token解析失败");
        }
        return jwt;
    }

    public static boolean verify(String token){
        try {
            JWT jwt = JWT.of(token);
            JWTValidator.of(jwt)
                    .validateAlgorithm(JWTSignerUtil.createSigner("HS256", secretKeyBytes))
                    .validateDate(DateUtil.date());
            // 获取JWT中其他属性并验证
            // TODO
            return true;
        } catch (JWTException e) {
            return false;
        }

    }


}