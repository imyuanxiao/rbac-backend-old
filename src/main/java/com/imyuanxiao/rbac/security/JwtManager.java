package com.imyuanxiao.rbac.security;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.*;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 16:23
 */
@Slf4j
public final class JwtManager {
    /**
     * 这个秘钥是防止JWT被篡改的关键，随便写什么都好，但决不能泄露
     */
    private final static byte[] secretKeyBytes = "my_secret_key".getBytes();


    /**
     * 过期时间目前设置成30分钟，这个配置随业务需求而定
     */
    private final static Integer EXPIRATION = 30;

    /**
     * 生成JWT
     *
     * @param userName 用户名
     * @return JWTtoken
     */
    public static String generate(String userName) {
        DateTime now = DateUtil.date();
        DateTime ddl = DateUtil.offsetMinute(now, EXPIRATION);
        Map<String, Object> map = new HashMap<String, Object>() {
            {
                put(JWTPayload.ISSUED_AT, now);
                put(JWTPayload.EXPIRES_AT, ddl);
                put(JWTPayload.NOT_BEFORE, now);
                put(JWTPayload.SUBJECT, userName); //把用户名放到sub字段
            }
        };
        return "Bearer " + JWTUtil.createToken(map, secretKeyBytes);
    }

//    /**
//     * 解析JWT
//     *
//     * @param token JWT字符串
//     * @return 解析成功返回JWT对象，解析失败返回null
//     */
//    public static JWT verify(String token) {
//        // 如果是空字符串直接返回null
//        if(StrUtil.isBlank(token)){
//            return null;
//        }
//        JWT jwt = null;
//        // 解析失败了会抛出异常，所以要捕捉一下。token过期、token非法都会导致解析失败
//        try {
//            // 解析（包含验证签名）
//            jwt = JWTUtil.parseToken(token);
//
//            // 验证算法和时间
//            JWTValidator validator = JWTValidator.of(jwt);
//            // 验证算法
//            validator.validateAlgorithm(JWTSignerUtil.hs256(secretKeyBytes));
//            // 验证时间
//            JWTValidator.of(jwt).validateDate();
//        } catch (Exception e) {
//            log.error("token解析和验证失败");
//            return null;
//        }
//        return jwt;
//    }

    /**
     * 验证token，验证失败会抛出异常
     *
     * @param token JWT字符串
     *
     */
    public static void verifyToken(String token) {
        // 解析失败了会抛出异常，所以要捕捉一下。token过期、token非法都会导致解析失败
        try {
            //验证签名
            boolean verify = JWTUtil.verify(token, JWTSignerUtil.hs256(secretKeyBytes));
            if(!verify) {
                throw new ApiException(ResultCode.VALIDATE_FAILED);
            }
            // 验证算法和时间
            JWTValidator validator = JWTValidator.of(token);
            // 验证算法
            validator.validateAlgorithm(JWTSignerUtil.hs256(secretKeyBytes));
            // 验证时间
            JWTValidator.of(token).validateDate();
        } catch (Exception e) {
            log.error("token验证失败:" + e.getMessage());
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
    }

//    public static boolean isValidToken(String token, UserDetailsVO userDetails){
//        final String username = extractUsername(token);
//        return(username.equals(userDetails.getUsername()) && isVerifiedToken(token));
//    }

    /**
     * 解析JWT
     *
     * @param token JWT字符串
     * @return 解析成功返回JWTPayload对象，解析失败抛出异常
     */
    private static Claims extractAllClaims(String token) {
        verifyToken(token);
        return JWTUtil.parseToken(token).getPayload();
    }

    /**
     * 提取JWT中的username
     *
     * @param token JWT字符串
     * @return 解析成功返回username，解析失败抛出异常
     */
    public static String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return String.valueOf(claims.getClaim(JWTPayload.SUBJECT));
    }

}