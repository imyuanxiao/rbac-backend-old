package com.imyuanxiao.rbac.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.*;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

        DateTime now = DateUtil.date();
        DateTime ddl = DateUtil.offsetMinute(now, 30);

        Map<String, Object> map = new HashMap<String, Object>() {
            {
                put(JWTPayload.ISSUED_AT, now);
                put(JWTPayload.EXPIRES_AT, ddl);
                put(JWTPayload.NOT_BEFORE, now);
                put("username", userName);
            }
        };

        return JWTUtil.createToken(map, secretKeyBytes);

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
            // 解析（包含验证签名）
            jwt = JWTUtil.parseToken(token);

            // 验证算法和时间
            JWTValidator validator = JWTValidator.of(jwt);
            // 验证算法
            validator.validateAlgorithm(JWTSignerUtil.hs256(secretKeyBytes));
            // 验证时间
            JWTValidator.of(jwt).validateDate();
        } catch (Exception e) {
            log.error("token解析和验证失败");
            return null;
        }
        return jwt;
    }

}