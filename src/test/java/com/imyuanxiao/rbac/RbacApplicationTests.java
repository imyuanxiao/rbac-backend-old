package com.imyuanxiao.rbac;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.*;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Payload;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RbacApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void testHutoolsJWT(){

        byte[] secretKeyBytes = "my_secret_key".getBytes();


        DateTime now = DateUtil.date();
        System.out.println(now);
//        DateTime ddl = now.offset(DateField.MINUTE, 30);
        DateTime ddl = DateUtil.offsetMinute(now, 30);
        System.out.println(ddl);

        Map<String, Object> map = new HashMap<String, Object>() {
            {
                put(JWTPayload.ISSUED_AT, now);
                put(JWTPayload.EXPIRES_AT, ddl);
                put(JWTPayload.NOT_BEFORE, now);
                put("username", "sean");
                put("role", "admin");
            }
        };

        String token = JWTUtil.createToken(map, secretKeyBytes);

        final JWT jwt = JWTUtil.parseToken(token);

        Object jwtHeader = jwt.getHeader(JWTHeader.TYPE);
        System.out.println(jwtHeader);
        Object username = jwt.getPayload("username");
        System.out.println(username);
        Object role = jwt.getPayload("role");
        System.out.println(role);


        // 验证签名
        boolean verify = JWTUtil.verify(token, secretKeyBytes);
        System.out.println(verify);

        try {

            JWTValidator validator = JWTValidator.of(jwt);

            // 验证算法
            validator.validateAlgorithm(JWTSignerUtil.hs256(secretKeyBytes));

            // 验证时间
            JWTValidator.of(jwt).validateDate();

        } catch (ValidateException e) {
            System.out.println(e.getMessage());
        }

    }



}
