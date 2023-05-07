package com.imyuanxiao.rbac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName SwaggerConfig
 * @Description Configuration for swagger
 * @Author: imyuanxiao
 * @Date: 2023/5/3 0:53
 * @Version 1.0
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Configures the Swagger documentation for the API.
     * It sets the API title, description, version, and security context.
     * @author imyuanxiao
     * @date 14:50 2023/5/7
     * @return springfox.documentation.spring.web.plugins.Docket
     **/
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.imyuanxiao.rbac.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContexts()))
                .securitySchemes(Arrays.asList(securitySchemes()))
                .apiInfo(apiInfo());
    }

    /**
     * Configures the Swagger API information such as title, description, and version.
     * @author imyuanxiao
     * @date 14:50 2023/5/7
     * @return springfox.documentation.service.ApiInfo
     **/
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RbacMS API")
                .description("API documentation for Rbac MS, A permission management system based on the RBAC model.")
                .version("1.0.0")
                .build();
    }

    /**
     * Configures the security scheme used by the API. 
     * It sets the type of authentication and the name of the authorization header.
     * @author imyuanxiao
     * @date 14:51 2023/5/7  
     * @return springfox.documentation.service.SecurityScheme
     **/
    private SecurityScheme securitySchemes() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    /**
     * Configures the security context for the API.
     * @author imyuanxiao
     * @date 14:51 2023/5/7  
     * @return springfox.documentation.spi.service.contexts.SecurityContext
     **/
    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }
    
    /**
     * Configures the default security reference used by the API.
     * It specifies the authorization scope and references the authorization header defined in the security scheme.
     * @author imyuanxiao
     * @date 14:51 2023/5/7
     * @return java.util.List<springfox.documentation.service.SecurityReference>
     **/
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("All", "Use all api");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

}