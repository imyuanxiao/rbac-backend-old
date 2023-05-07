package com.imyuanxiao.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;


/**
 * @ClassName RbacApplication
 * @Description 应用启动类
 * @Author imyuanxiao
 * @Date 2023/5/3 21:40
 * @Version 1.0
 **/
@EnableOpenApi
@SpringBootApplication
@MapperScan(basePackages = {"com.imyuanxiao.rbac.mapper"})
public class RbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
    }

}
