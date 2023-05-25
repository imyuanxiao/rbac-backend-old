# 项目简介
基于Spring Boot + MySQL + MyBatis Plus + Spring Security + JWT 打造RBAC通用权限管理系统（Role-Based Access Controller基于角色访问控制模型）
# 参考资料
[【项目实践】SpringBoot三招组合拳，手把手教你打出优雅的后端接口](https://zhuanlan.zhihu.com/p/340620501)<br />[【项目实践】后端接口统一规范的同时，如何优雅地扩展规范](https://zhuanlan.zhihu.com/p/342463219)<br />[【项目实践】一文带你搞定Session和JWT](https://zhuanlan.zhihu.com/p/342744060)<br />[【项目实践】一文带你搞定页面权限、按钮权限以及数据权限](https://zhuanlan.zhihu.com/p/342746910)<br />[【项目实践】一文带你搞定Spring Security + JWT实现前后端分离下的认证授权](https://zhuanlan.zhihu.com/p/342755411)
# 项目结构
```
src/main/java
├── com.example.myproject
│   ├── annotation
│   │   └── Auth.java
│   ├── config
│   │   ├── JWTConfig.java
│   │   ├── MyBatisPlusConfig.java
│   │   └── WebSecurityConfig.java
│   ├── controller
│   │   ├── AuthController.java
│   │   ├── UserController.java
│   │   └── ...
│   ├── mapper
│   │   ├── UserMapper.java
│   │   └── ...
│   ├── model
│   │   ├── entity
│   │   │   ├── User.java
│   │   │   └── ...
│   │   ├── param
│   │   └── vo
│   ├── exception
│   │   └── ApiException.java
│   ├── service
│   │   ├── UserService.java
│   │   └── ...
│   ├── util
│   └── MyProjectApplication.java
├── resources
│   ├── application.yml
│   ├── mapper
│   │   ├── UserMapper.xml
│   │   └── ...
│   ├── static
│   └── templates
└── test
└── ...
```

- src/main/java：Java 源代码的根目录。包含项目的核心代码、配置文件和资源文件。
   - com.example.myproject：包名，根据项目实际情况进行命名。
      - annotation：包含自定义注解的目录，比如 Auth 注解，用于权限控制。
      - config：包含各种配置类的目录，如 JWT 配置、MyBatis Plus 配置、Web 安全配置等。
      - controller：控制器目录，包含处理 HTTP 请求的控制器类。
      - mapper：Mapper 目录，包含 MyBatis 映射器接口，用于执行数据库操作。
      - model：模型目录，包含实体类、参数类和值对象等。
      - exception：异常目录，包含自定义异常类，用于捕获和处理异常情况。
      - service：服务目录，包含业务逻辑的服务类。
      - util：工具类目录，包含项目中通用的工具类。
      - MyProjectApplication.java：Spring Boot 应用程序的入口类，包含 main 方法。
- resources：资源目录，包含配置文件和静态资源文件等。
   - application.yml：Spring Boot 应用程序的配置文件，包含数据库配置、日志配置等。
   - mapper：MyBatis 映射器 XML 文件的目录。
   - static：静态资源目录，比如 JS、CSS、图片等。
   - templates：模板文件目录，比如 Thymeleaf 模板文件等。
- test：测试目录，包含测试代码和配置文件等。
# 项目特点

- 权限管理：页面权限、按钮权限（操作权限）、数据权限(数据权限暂未完成）
- 登录鉴权：jwt token + NoSession，轻量级的身份验证和授权机制
- 全局异常处理+全局过滤器+全局拦截器
- 统一响应体
- 数据校验
- spring security 进行登录认证和权限管理的应用
# 数据库设计

1. 用户表（User table）- 用于存储用户的基本信息，如用户名、密码等。该表应该包含一个唯一的用户ID字段，以便在其他表中引用该用户。
2. 角色表（Role table）- 用于存储角色的基本信息，如角色名称、描述等。该表应该包含一个唯一的角色ID字段，以便在其他表中引用该角色。
3. 权限表（Permission table）- 用于存储权限的基本信息，如权限名称、描述等。该表应该包含一个唯一的权限ID字段，以便在其他表中引用该权限。
4. 用户角色关系表（User-Role table）- 用于存储用户和角色之间的关系。该表应该包含两个字段：用户ID和角色ID，以表示某个用户拥有某个角色。
5. 角色权限关系表（Role-Permission table）- 用于存储角色和权限之间的关系。该表应该包含两个字段：角色ID和权限ID，以表示某个角色拥有某个权限。

**注意：阿里JAVA规范中指出，不要建立数据库与数据库之间的外键关联，可以不建立user、role和user_role的外键关系**
## 用户表（User table）
```sql
create table user
(
    id      BIGINT auto_increment comment 'userId, unique',
    username     varchar(50)  not null unique comment 'username, unique',
    password     varchar(256)  not null comment 'password',
    avatar MEDIUMBLOB COMMENT 'avatar',
    phone        varchar(50)  null comment 'phone',
    email        varchar(256)  null comment 'email',
    created_time datetime      null comment 'createdTime',
    updated_time datetime      null comment 'updatedTime',
    constraint key_name
        primary key (id)
);
```

1. 用户名字段需要加上UNIQUE约束，以保证每个用户名都是唯一的，避免出现重复的情况。
2. 密码字段需要进行加密处理（常用MD5），以确保用户密码的安全性。
3. 头像字段可以考虑使用BLOB或者MEDIUMBLOB类型来存储用户头像图片，而不是使用VARCHAR类型存储图片的URL地址。
4. 电话和电子邮件字段需要进行格式校验，以确保输入的电话和电子邮件符合格式要求，并避免出现格式错误的情况。
5. 创建时间和更新时间字段可以使用DATETIME类型，以确保时间的准确性和精度。
6. 在设计RBAC系统的用户表时，还需要考虑如何与角色表、权限表和用户角色表等相关表进行关联和操作。通常情况下，用户表、角色表和权限表之间会建立关联，以实现RBAC系统的权限控制。
## 角色表（Role table）
```sql
CREATE TABLE role (
    id BIGINT AUTO_INCREMENT COMMENT 'roleId, unique',
    name VARCHAR(256) NOT NULL COMMENT 'roleName, unique',
    description VARCHAR(1024) NULL COMMENT 'description',
    created_time DATETIME NULL COMMENT 'createdTime',
    updated_time DATETIME NULL COMMENT 'updatedTime',
    CONSTRAINT key_name PRIMARY KEY (id)
);
```

1. role_id：角色ID，自增类型，用于唯一标识一个角色。
2. role_name：角色名称，VARCHAR类型，用于存储角色的名称，需要设置UNIQUE约束以确保角色名称的唯一性。
3. description：角色描述，VARCHAR类型，用于存储角色的描述信息。
4. created_time：角色创建时间，DATETIME类型，用于记录角色的创建时间。
5. updated_time：角色更新时间，DATETIME类型，用于记录角色的更新时间。
## 权限表（Permission table）
```sql
CREATE TABLE permission (
    id BIGINT AUTO_INCREMENT COMMENT 'permissionId, unique',
    name VARCHAR(256) NOT NULL COMMENT 'permissionName, unique',
    type TINYINT NOT NULL COMMENT 'permissionType, 0-view, 1-action',
    url VARCHAR(256) NOT NULL COMMENT 'permissionUrl',
    description VARCHAR(1024) NULL COMMENT 'description',
    created_time DATETIME NULL COMMENT 'createdTime',
    updated_time DATETIME NULL COMMENT 'updatedTime',
    CONSTRAINT key_name PRIMARY KEY (id)
);
```

1. permission_id：权限ID，自增类型，用于唯一标识一个权限。
2. permission_name：权限名称，VARCHAR类型，用于存储权限的名称，需要设置UNIQUE约束以确保权限名称的唯一性。
3. description：权限描述，VARCHAR类型，用于存储权限的描述信息。
4. created_time：权限创建时间，DATETIME类型，用于记录权限的创建时间。
5. updated_time：权限更新时间，DATETIME类型，用于记录权限的更新时间。
## 用户角色关系表（User-Role table）
```sql
CREATE TABLE user_role (
    user_id BIGINT NOT NULL COMMENT 'userId',
    role_id BIGINT NOT NULL COMMENT 'roleId',
    created_time DATETIME NULL COMMENT 'createdTime',
    updated_time DATETIME NULL COMMENT 'updatedTime',
    CONSTRAINT user_role_pk PRIMARY KEY (user_id, role_id),
);
```

1. user_id：用户ID，INT类型，与用户表中的user_id字段对应。
2. role_id：角色ID，INT类型，与角色表中的role_id字段对应。
3. created_time：用户角色关系创建时间，DATETIME类型，用于记录用户角色关系的创建时间。
4. updated_time：用户角色关系更新时间，DATETIME类型，用于记录用户角色关系的更新时间。
## 角色权限关系表（Role-Permission table）
```sql
CREATE TABLE role_permission (
    role_id BIGINT NOT NULL COMMENT 'roleId',
    permission_id BIGINT NOT NULL COMMENT 'permissionId',
    created_time DATETIME NULL COMMENT 'createdTime',
    updated_time DATETIME NULL COMMENT 'updatedTime',
    CONSTRAINT role_permission_pk PRIMARY KEY (role_id, permission_id),
);
```

1. role_id：角色ID，INT类型，与角色表中的role_id字段对应。
2. permission_id：权限ID，INT类型，与权限表中的permission_id字段对应。
3. created_time：角色权限关系创建时间，DATETIME类型，用于记录角色权限关系的创建时间。
4. updated_time：角色权限关系更新时间，DATETIME类型，用于记录角色权限关系的更新时间。
# 创建项目
工具：IDEA<br />spring-boot版本：2.7.12
```xml
<dependencies>

  <!-- Spring Boot及Spring Security相关依赖 -->
  <!--Spring Boot中基础的安全依赖，提供了安全框架的基础支持-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
  <!--Spring Boot中基础的Web依赖，提供了Web开发所需要的基本功能-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <!--Spring Boot中的开发者工具，可以自动重启应用程序以提高开发效率-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
  </dependency>
  <!--Spring Boot中的测试依赖，提供了单元测试和集成测试的支持-->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
  </dependency>
  <!--Spring Security中的测试依赖，提供了安全框架测试所需要的基本功能-->
  <dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
  </dependency>

  <!--数据库及ORM框架相关依赖-->
  <!--Mybatis-Plus ORM框架的Spring Boot Starter，提供了ORM框架的基本支持-->
  <dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.3.1</version>
  </dependency>
  <!--MySQL数据库连接驱动，连接MySQL必备-->
  <dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
  </dependency>

  <!--工具依赖-->
  <!--注解处理器，可以减少代码冗余-->
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <!--工具库，提供了常见的数据处理、加密解密、日期处理、网络操作等功能-->
  <dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.8.15</version>
  </dependency>

</dependencies>
```
# 统一java注解规范
先定义统一的注释规范，方便对类和方法进行说明。<br />[Intellij IDEA设置类和方法注释（javadoc）_JayXu6888的博客-CSDN博客](https://blog.csdn.net/Sora_Xu/article/details/102707225)
# 根据数据库表建立对应的类
使用插件：MybatisX-generator（不适用于spring3.0以上版本）<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683070915504-3bae7d6c-256c-4b23-b4fe-0c8a0c9e2671.png#averageHue=%233d4144&clientId=u70ac31dc-1256-4&from=paste&height=502&id=u52b5dda1&originHeight=627&originWidth=1144&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=29256&status=done&style=none&taskId=ud34d46aa-7bb6-4514-8a7e-89f1862ce4a&title=&width=915.2)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683070932308-8d24a3c8-abeb-4f9a-9ab1-91b2556bfe88.png#averageHue=%233c4043&clientId=u70ac31dc-1256-4&from=paste&height=502&id=u7f206000&originHeight=627&originWidth=1144&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=41813&status=done&style=none&taskId=u1026657e-e61c-406e-988f-c0fce8e4749&title=&width=915.2)<br />记得在启动类上加mapper包路径
```java
@SpringBootApplication
@MapperScan(basePackages = "com.imyuanxiao.rbac.mapper")
public class RbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
    }

}
```
## 配置数据库信息
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/rbac
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: 123456
```
## LoginParam
用于接收前端数据以及初步校验
```java
@Data
public class LoginParam {

    @NotBlank(message = "用户名不能为空")
    @Length(min = 4, max = 12, message = "用户名长度为4-12位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 4, max = 12, message = "密码长度为4-12位")
    private String password;

}
```
# 参数校验
引入依赖。校验的目的是在接收参数后，如果不符合要求直接抛出异常，不会进入业务层。
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```
## 测试
这块我后补的，所以用的注册表单，可以用其他随意表单代替。
```java
@Data
public class RegisterParam {


    @NotBlank(message = "手机号不能为空")
    @Length(min = 8, max = 20, message = "手机号长度为8-20位")
    @ExceptionCode(value = 100004, message = "手机号验证错误")
    private String phone;

    @NotBlank(message = "密码不能为空")
    @Length(min = 4, max = 12, message = "密码长度为4-12位")
    @ExceptionCode(value = 100003, message = "密码验证错误")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "\\d{4}", message = "验证码必须是4位数字")
    @ExceptionCode(value = 100005, message = "验证码错误")
    private String code;

}
```
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683453128815-7b9f45a2-4857-410b-aa69-6279302b5670.png#averageHue=%23fbfafa&clientId=u567c33e7-6148-4&from=paste&height=461&id=u0a4b513b&originHeight=576&originWidth=593&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=38970&status=done&style=none&taskId=u8f9f7901-ccbf-49ad-904e-c8616feb340&title=&width=474.4)
# 配置日志
设置日志级别
```yaml
logging:
  level:
    root: info    # 设置根日志级别为info
    com.imyuanxiao: debug    # 设置com.imyuanxiao包下的日志级别为debug
```
## 使用日志
在类上加注解@Slf4j，方法内使用log.方法
```java
@Slf4j
@Component
public class AuthFilter  extends AbstractSecurityInterceptor implements Filter {
    ...
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("---AuthFilter---");
        ...
    }
}
```
# 配置mybatisPlus分页插件
```yaml
@Configuration
@MapperScan("com.imyuanxiao.rbac.mapper")
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
      	// 添加 MyBatis Plus 的分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
```
这段代码配置的是 MyBatis Plus 的分页插件。MyBatis Plus 提供了多种分页插件，其中 PaginationInnerInterceptor 是其中一种实现。在上面的代码中，我们创建了一个 MybatisPlusInterceptor 的 Bean，并将 PaginationInnerInterceptor 添加到其中，然后将其返回。<br />如果不使用分页插件，MyBatis Plus 默认使用的是物理分页，也就是在 SQL 语句中添加 LIMIT 和 OFFSET 子句来实现分页。这种分页方式的缺点是，如果数据量非常大，查询的性能会非常差。
# 引入swagger
Swagger是一个开源的API文档生成工具，可以自动生成RESTful API的文档，方便开发者快速了解API的请求方式、请求参数、响应数据等信息，提高开发效率和API使用的便捷性。<br />[Springboot 2.6.7+swagger 3.0.0 集成使用_springboot2.6.7-CSDN博客](https://blog.csdn.net/newposte/article/details/124772142)
```xml
<!--用于解决swagger报错问题-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>

<!--用于测试api和生成api文档-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>3.0.0</version>
</dependency>
```
## 配置swagger
```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.imyuanxiao.rbac.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Rbac API")
            .description("API documentation for Rbac MS")
            .version("1.0.0")
            .build();
    }
}
```
在启动类上添加@EnableOpenApi，注意basePackage里换成controller包路径
```java
@EnableOpenApi
@SpringBootApplication
@MapperScan(basePackages = "com.imyuanxiao.rbac.mapper")
public class RbacApplication {
	...
}
```
## 使用注释
在类上用@Api，在方法上用@ApiOperation
```java
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "User Management Interface")
public class UserController {
    ...
    @PostMapping("/add")
    @ApiOperation(value = "Add user")
    public String createUser(@RequestBody @Validated(UserParam.CreateUser.class) UserParam param) {
        userService.createUser(param);
        return ACTION_SUCCESSFUL;
    }
}
```
## 测试
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)<br />注意：spring security 测试需要登录，默认用户名user，密码在服务器启动时会给<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683074171363-eba55b06-9e11-4e2f-bff0-c24ee0fd4d40.png#averageHue=%23352d2c&clientId=u70ac31dc-1256-4&from=paste&height=111&id=u87ec2bd4&originHeight=139&originWidth=960&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=24177&status=done&style=none&taskId=u95d476d3-a4fe-4f03-bfa3-18c84ff0dea&title=&width=768)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683499601228-6b794868-6728-40e6-a316-406d776e0650.png#averageHue=%23e0c4a0&clientId=u567c33e7-6148-4&from=paste&height=146&id=ue25cef38&originHeight=183&originWidth=751&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=13089&status=done&style=none&taskId=u86096ee2-6878-44fa-86ba-85f11464958&title=&width=600.8)
## 补充：配置spring security后
配置spring security框架后，如果无法访问swagger相关内容，需要做以下配置：
### 修改SpringSecurityConfig
```java
@EnableWebSecurity
public class SpringSecurityConfig {
	...
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        ...
        http.authorizeRequests()
                // 注意这里，是允许前端跨域联调的一个必要配置
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 指定某些接口不需要通过验证即可访问。像登陆、注册接口肯定是不需要认证的
                .antMatchers("/login/**",
                        "/auth/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/images/**",
                        "/webjars/**",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/configuration/security"

                )
                .permitAll()
    ...
        return http.build();
    }
```
### 修改SwaggerConfig
[Swagger中怎么处理认证问题？](https://zhuanlan.zhihu.com/p/444025131)
```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {
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

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Rbac API")
                .description("API documentation for Rbac MS")
                .version("1.0.0")
                .build();
    }

    private SecurityScheme securitySchemes() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("xxx", "描述信息");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }
    
}
```
看到右下角这个授权的按钮就OK了<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683462099758-8f745b2e-211a-4a64-bdeb-f9b04ba80417.png#averageHue=%23fbfbfb&clientId=u567c33e7-6148-4&from=paste&height=291&id=u809328d0&originHeight=364&originWidth=747&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=16358&status=done&style=none&taskId=u25850ea1-f98b-403e-91bf-c01f0678a3d&title=&width=597.6)<br />以下是Swagger常用的注解：

- @Api: 用于控制整个类的Swagger文档，描述接口类的基本信息和可见范围。
- @ApiOperation: 用于描述单个接口的信息，包括接口名称、接口方法、接口说明等。
- @ApiImplicitParam: 描述单个入参信息，包括参数名称、参数类型、参数说明、是否必填等信息。
- @ApiImplicitParams: 用于描述多个入参信息，可以包含多个@ApiImplicitParam注解。
- @ApiModel: 用于描述Java Bean类，包括Java Bean类的基本信息和可见范围。
- @ApiModelProperty: 描述Java Bean类中的属性信息，包括属性名称、属性类型、属性说明等。
- @ApiResponse: 描述接口返回结果的信息，包括返回结果的数据类型、返回结果的说明等。
- @ApiResponses: 用于描述多个返回结果的信息，可以包含多个@ApiResponse注解
# 数据统一响应体
数据统一响应体是一种规范化的数据返回格式，通过定义统一的数据格式，可以方便前后端协作开发、减少开发工作量、提高接口的可读性和可维护性。一般来说，它包括了响应码、响应消息和响应数据等信息。
## 自定义统一响应体
```java
@Getter
public class ResultVO<T> {
    /**
     * 状态码
     */
    private int code;
    /**
     * 响应信息, 来说明响应情况
     */
    private String msg;
    /**
     * 响应的具体数据
     */
    private T data;

    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("{\"code\":%d,\"msg\":\"%s\",\"data\":\"%s\"}", code, msg, data.toString());
    }
}
```
## 业务响应码
```java
@Getter
public enum ResultCode {
    SUCCESS(0000, "操作成功"),

    UNAUTHORIZED(1001, "没有登录"),

    FORBIDDEN(1002, "没有相关权限"),

    VALIDATE_FAILED(1003, "参数校验失败"),

    FAILED(1004, "接口异常"),

    ERROR(5000, "未知错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
```
# 全局异常处理
配置异常处理类，对于异常错误信息也封装成统一响应体返回给前端
```java
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    public String apiExceptionHandler(APIException e) {
        // 返回自定义异常提示信息
        return new ResultVO<>(ResultCode.FAILED, e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        return new ResultVO<>(ResultCode.VALIDATE_FAILED, objectError.getDefaultMessage());
    }

}
```
## 自定义异常类
```java
@Getter
public class APIException extends RuntimeException{
    private int code;
    private String msg;

    public APIException() {
        this(1001, "接口错误");
    }

    public APIException(String msg) {
        this(1001, msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
```
# 全局处理响应数据
不需要在每个接口方法都手动把数据封装进ResultVO，而是通过全局处理类进行统一封装
```java
@RestControllerAdvice(basePackages = {"com.imyuanxiao.rbac.controller"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // If the return type of the interface is already ResultVO, there is no need for additional operations. Return false.
        return !returnType.getParameterType().equals(ResultVO.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if(returnType.getGenericParameterType().equals(String.class)){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(new ResultVO<>(data));
            } catch (JsonProcessingException e) {
                throw new APIException("返回String类型错误");
            }
        }
        return new ResultVO<>(data);
    }
}
```
# 自定义注解
## 拓展错误码和响应信息
```java
/**
 * 自定义注解，用于字段校验
 * @Author: imyuanxiao
 * @Date: 2023/5/3 9:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExceptionCode {
    // 响应码code
    int value() default 100000;
    // 响应信息msg
    String message() default  "参数校验错误";
}

```
在字段上加上注解，遇到异常后会读取字段上的@ExceptionCode注解内容
```java
@Data
public class LoginParam {

    @NotBlank(message = "用户名不能为空")
    @Length(min = 4, max = 12, message = "用户名长度为4-12位")
    @ExceptionCode(value = 100001, message = "账号验证错误")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 4, max = 12, message = "密码长度为4-12位")
    @ExceptionCode(value = 100002, message = "密码验证错误")
    private String password;

}
```
## 绕过数据统一响应
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NotResponseBody {

}
```
在ResponseControllerAdvice进行判断，如果参数本身就是ResultVO或者方法有注解@ResponseControllerAdvice，就不执行beforeBodyWrite方法
```java
@RestControllerAdvice(basePackages = {"com.imyuanxiao.rbac.controller"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // If false, won't carry out beforeBodyWrite()
        // If the return type of the interface is already ResultVO, there is no need for additional operations. Return false.
        // If method has annotation @NotResponseBody, return false
        return !(returnType.getParameterType().equals(ResultVO.class) || returnType.hasMethodAnnotation(NotResponseBody.class)) ;
    }
    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ...
    }
}

```
# 配置JWT
JWT（JSON Web Token）是一种用于身份验证和授权的开放标准（RFC 7519），它定义了一种紧凑且自包含的方式，用于在各方之间安全地以 JSON 对象的形式传输信息。JWT 通常被用于客户端和服务器之间的身份验证和授权，以及在微服务之间传递用户信息。它由三部分组成：头部、载荷和签名。其中头部包含了令牌类型和使用的算法，载荷包含了令牌所携带的信息，签名则用于验证令牌的真实性和完整性。JWT 通过使用密钥对令牌进行签名，保证了令牌的真实性和完整性。JWT 相比于传统的 Session 和 Cookie 方案，更加灵活，因为它可以在各个服务之间共享，并且无状态，避免了服务器端保存 Session 的问题。
## 引入依赖
此处我使用的是hutools工具包里的JWTUtil<br />[Hutool参考文档](https://hutool.cn/docs/#/jwt/JWT%E5%B7%A5%E5%85%B7-JWTUtil)
```xml
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.8.15</version>
</dependency>
```
## 创建工具类
```java
@Slf4j
public final class JwtUtil {
    /**
     * 这个秘钥是防止JWT被篡改的关键，随便写什么都好，但决不能泄露
     */
    private final static byte[] secretKeyBytes = "my_secret_key".getBytes();


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
```
## 测试
用postman测试接口
```java
@ApiOperation(value = "LoginByUsername")
@PostMapping("/username")
public String loginByUsername(@RequestBody LoginParam loginParam){

    if("admin".equals(loginParam.getUsername()) && "admin".equals(loginParam.getPassword())){
        return JwtUtil.generate(loginParam.getUsername());

    }

    return "查无此人";

}
```
```java
@ApiOperation(value = "测试token解析与验证")
@GetMapping("/token")
public String testToken(HttpServletRequest request) {
    // 从请求头中获取token字符串
    String jwt = request.getHeader("Authorization");
    // 解析失败就提示用户登录
    if (JwtUtil.parse(jwt) == null) {
        return "请先登录";
    }
    // 解析成功就执行业务逻辑返回数据
    return "api成功返回数据";
}
```
此处使用POSTMAN客户端进行测试<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683137304595-f8f14e9b-6cb3-4fbb-adb5-69e2fdfb5aba.png#averageHue=%23fbf7f7&clientId=u70ac31dc-1256-4&from=paste&height=443&id=u6a943971&originHeight=554&originWidth=1115&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=68137&status=done&style=none&taskId=ub3d24c07-af16-4925-8976-dfd2672ea42&title=&width=892)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683137351412-3688aff6-a2a2-4544-ab5c-6d5a44d7c1b0.png#averageHue=%23fbfbfa&clientId=u70ac31dc-1256-4&from=paste&height=398&id=u79ae0f7e&originHeight=498&originWidth=1118&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=56727&status=done&style=none&taskId=uccd0e67a-4b00-4745-81ee-9224280cfa4&title=&width=894.4)<br />可以使用[https://jwt.io/](https://jwt.io/)官网提供的debugger根据解析jwt<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683229479541-6439bdcb-13b6-40a8-864b-81d2b80becc0.png#averageHue=%23fdfdfd&clientId=u1c85c078-a627-4&from=paste&height=418&id=ua2d52ea3&originHeight=522&originWidth=1167&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=61316&status=done&style=none&taskId=u8f14a939-27bf-493e-badd-04538c7d366&title=&width=933.6)
# 配置登录拦截器
拦截器（Interceptor）是一种在 Web 应用程序中拦截处理请求的组件，可以在请求被处理前和响应被发送回客户端之后，对请求和响应进行修改和处理。在 Spring 框架中，拦截器通过实现 HandlerInterceptor 接口来定义，并可以通过配置将其与请求路径进行映射，使得拦截器可以被应用到特定的请求路径上。<br />登录拦截器用于在所有请求之前通过jwt token验证是否登录。<br />注：后面配置spring security后，用过滤器代替了拦截器。
```java
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从请求头中获取token字符串并解析
        JWT result = JwtUtil.parse(request.getHeader("Authorization"));
        // 已登录就直接放行
        if (result != null) {
            return true;
        }

        // 走到这里就代表是其他接口，且没有登录
        // 设置响应数据类型为json（前后端分离）
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        // 设置响应内容，结束请求
        out.write("请先登录");
        out.flush();
        out.close();
        return false;
    }
}
```
## 注册拦截器
WebMvcConfigurer是Spring MVC提供的一个接口，用于在Spring MVC配置中添加自定义配置。通过实现该接口，我们可以添加自定义的拦截器、视图解析器、静态资源处理器等。<br />如果要注册拦截器，需要在配置类中重写addInterceptors方法并在其中添加自定义拦截器。这是因为Spring MVC中需要通过WebMvcConfigurer接口的实现类来添加自定义拦截器，而WebMvcConfigurer接口中并没有直接添加拦截器的方法。因此，我们需要在实现类中重写addInterceptors方法，然后通过该方法来添加自定义拦截器。
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置拦截器应用于哪些路径，不应用于哪些路径
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**");
    }
}
```
## 测试
使用postman测试，会发现，除了/login路径，其他路径都被拦截了<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683140852426-19fb5ea9-b9e4-491c-bc06-5cc7e5d2e110.png#averageHue=%23fcfbfb&clientId=u70ac31dc-1256-4&from=paste&height=417&id=ud175e5cc&originHeight=521&originWidth=1130&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=55399&status=done&style=none&taskId=uecb3e7af-64a8-4f0e-8db9-0d8912ce063&title=&width=904)
# 配置上下文对象
在Web应用程序中，上下文对象通常用于在整个应用程序中共享数据和状态，例如存储全局配置、缓存、当前用户等信息。<br />当用户token验证通过后，把用户信息加到上下文，这样所有方法内都可以直接获取该用户信息<br />注：后面配置spring security后，用SecurityContext代替了自定义的上下文对象。
```java
public final class UserContext {
    private static final ThreadLocal<String> user = new ThreadLocal<String>();
    public static void add(String userName) {
        user.set(userName);
    }
    public static void remove() {
        user.remove();
    }
    /**
     * @return 当前登录用户的用户名
     */
    public static String getCurrentUserName() {
        return user.get();
    }
}
```
在拦截器配置添加和移除上下文对象的代码
```java
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	...
        if (jwt != null) {
            // 添加上下文对象
            UserContext.add((String)jwt.getPayload("username"));
            return true;
        }
        ...
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 结束后移除上下文对象
        UserContext.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
```
## 测试
```java
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
	...
    @ApiOperation(value = "测试上下文对象")
    @GetMapping("/context")
    public String testUsercontext() {
        // 解析成功就执行业务逻辑返回数据
        String userName = UserContext.getCurrentUserName();
        return "当前用户为：" + userName;
    }
}
```
# 配置VO
将多个数据封装到一个对象里，方便进行数据传递。Value Object，简称 VO。在 Java 开发中，VO 表示一个数据对象，它通常是用于表示一个业务模型中的数据。与传统的 Java Bean 不同，VO 通常不包含业务逻辑，只是简单地封装了数据。在实际应用中，VO 通常用于在业务层和表现层之间进行数据传递。通常情况下，VO 与实际的数据模型是一一对应的关系。
## UserVO
用于存储用户数据，token和权限数据
```java
@Data
@Accessors(chain = true) // 自动生成链式方法
public class UserVO {

    /**
     * 主键
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 登录认证token
     */
    private String token;
    /**
     * 当前用户的权限资源id集合
     */
    private Set<Long> permissionIds;

}
```
## UserPageVO
用于分页查询用户数据
```java
@Data
public class UserPageVO {
    private Long id;
    private String username;
    private Set<Long> roleIds;
}
```
## RolePageVO
用于分页查询角色数据
```java
@Data
public class RolePageVO {
    private Long id;
    private String name;
    private Set<Long> permissionIds;
}
```
# 页面权限
页面权限指用户是否可以访问某个页面或菜单。通常将页面权限定义为菜单或页面的访问权限，例如管理员可以访问用户管理菜单，普通用户只能访问自己的个人信息页面。

用户（User）和角色（Role）绑定，比如管理员、用户、访客等；<br />角色（Role）和权限（Permission）绑定，这里权限指可以访问的路径，比如/index、/login等；
## 配置Mppaer.xml
```xml
<!--根据用户id批量新增角色-->
<insert id="insertRolesByUserId">
    insert into user_role(user_id, role_id) values
    <foreach collection="roleIds" separator="," item="roleId">
        (#{userId}, #{roleId})
    </foreach>
</insert>

<!--根据用户id删除该用户所有角色-->
<delete id="deleteByUserId">
    delete from user_role where user_id = #{userId}
</delete>

<!--根据用户id查询角色id集合-->
<select id="selectIdsByUserId" resultType="java.lang.Long">
    select role_id from user_role where user_id = #{userId}
</select>

<!--查询分页对象-->
<select id="selectPage" resultType="com.imyuanxiao.rbac.model.vo.RolePageVO">
    select
        id, name
    from
        role
    ${ew.customSqlSegment}
</select>
```
```xml
<!--根据角色id批量增加权限-->
<insert id="insertPermissionsByRoleId">
    insert into role_permission(role_id, permission_id) values
    <foreach collection="permissionIds" separator="," item="permissionId">
        (#{roleId}, #{permissionId})
    </foreach>
</insert>

<!--批量新增权限资源-->
<insert id="insertPermissions">
    insert into resource(id, path, name, type) values
    <foreach collection="permissions" separator="," item="permission">
        (#{permission.id}, #{permission.url}, #{permission.name}, #{permission.type})
    </foreach>
</insert>

<!--根据角色id删除该角色下所有权限-->
<delete id="deleteByRoleId">
    delete from role_permission where role_id = #{roleId}
</delete>

<!--根据用户id获取权限id-->
<select id="selectIdsByUserId" resultType="java.lang.Long">
    SELECT
        rr.permission_id
    FROM
        user_role ur
        INNER JOIN role_permission rr ON ur.role_id = rr.role_id
    WHERE
        ur.user_id = #{userId}
</select>

<!--根据角色id获取权限id-->
<select id="selectIdsByRoleId" resultType="java.lang.Long">
    select permission_id from role_permission where role_id = #{roleId}
</select>

<!--根据用户id获取权限集合-->
<select id="selectListByUserId" resultType="com.imyuanxiao.rbac.model.entity.Permission">
    SELECT
        r.*
    FROM
        user_role ur
        INNER JOIN role_permission rr ON ur.role_id = rr.role_id
        INNER JOIN permission r ON rr.permission_id = r.id
    WHERE
        ur.user_id = #{userId};
</select>
```
## 配置Mapper.java
配置好Mapper.xml后，需要在对应的java文件里配置方法，才可以调用
```java
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    /**
* 根据用户id查询角色id集合
* @param userId 用户id
* @return 属于该用户的角色id集合
*/
    Set<Long> selectIdsByUserId(Long userId);

    /**
* 根据用户id删除该用户所有角色
* @param userId 用户id
* @return 受影响的行数
*/
    int deleteByUserId(Serializable userId);

    /**
* 根据用户id批量新增角色
* @param userId 用户id
* @param roleIds 角色id集合
* @return 受影响的行数
*/
    int insertRolesByUserId(@Param("userId") Long userId, @Param("roleIds") Collection<Long> roleIds);

    /**
* 查询用户分页信息
* @param page 分页条件
* @param wrapper 查询条件
* @return 分页对象
*/
    IPage<RolePageVO> selectPage(Page<RolePageVO> page, @Param(Constants.WRAPPER) Wrapper<RolePageVO> wrapper);
}
```
```java
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色id删除该角色下权限
     * @param roleId 角色id
     * @return 受影响的行数
     */
    int deleteByRoleId(Serializable roleId);

    /**
     * 根据角色id增加角色权限
     * @param roleId 角色id
     * @param permissionIds 权限id集合
     * @return 受影响的行数
     */
    int insertPermissionsByRoleId(@Param("roleId") Long roleId, @Param("permissionIds") Collection<Long> permissionIds);

    /**
     * 根据用户id获取权限id
     * @param userId 用户id
     * @return 权限id集合
     */
    Set<Long> selectIdsByUserId(Long userId);

    /**
     * 根据角色id获取权限id
     * @param roleId 角色id
     * @return 权限id集合
     */
    Set<Long> selectIdsByRoleId(Long roleId);

    /**
     * 批量新增权限资源
     * @param resources 资源对象集合
     * @return 受影响的行数
     */
    int insertPermissions(@Param("resources") Collection<Permission> resources);

    /**
     * 根据用户id获取该用户的所有权限资源对象
     * @param userId 用户id
     * @return 权限资源集合
     */
    List<Permission> selectListByUserId(Long userId);
}
```
```java
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询用户分页信息
     * @param page 分页条件
     * @param wrapper 查询条件
     * @return 分页对象
     */
    IPage<UserPageVO> selectPage(Page<UserPageVO> page, @Param(Constants.WRAPPER) Wrapper<UserPageVO> wrapper);
}
```
## 添加查询方法
```java
@Slf4j
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 获得所有角色信息
     * */
    @ApiOperation(value = "Get all roles")
    @GetMapping("/list")
    public List<Role> getRoleList() {
        return roleService.list();
    }
    
}
```
```java
@Slf4j
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 获得所有权限信息
     * */
    @ApiOperation(value = "Get all permissions")
    @GetMapping("/list")
    public List<Permission> getPermissionList() {
        return permissionService.list();
    }

}
```
# 操作权限
操作权限指用户是否可以进行某个操作，例如新增、修改、删除等。通常将操作权限定义为功能点或按钮的权限，例如管理员可以进行用户管理操作，普通用户不能进行用户管理操作。<br />下图中，红框部分为操作权限，type为1。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683501074817-3f5748c5-68f6-4086-a28c-8d2f65e0f24e.png#averageHue=%23332d2d&clientId=u567c33e7-6148-4&from=paste&height=434&id=ub5cb6a41&originHeight=543&originWidth=701&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=79494&status=done&style=none&taskId=u2c2bce8e-4152-4eb5-8808-a7cfc96e7ef&title=&width=560.8)
## 通过注解管理接口权限
通过代码将带有注解的接口信息批量添加到数据库，无需手动添加到数据库。类上加上Auth注解方便模块化管理接口权限，一个Controller类视为一套接口模块，最终接口权限的id就是模块id + 方法id。
## 设计注解
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE}) // Indicates that this annotation can be applied to both classes and methods.
public @interface Auth {
    /**
     * permission ID，unique
     */
    long id();
    /**
     * permission name
     */
    String name();
}
```
## 配置注解
```java
@Slf4j
@RestController
@RequestMapping("/permission")
@Auth(id = 3000, name = "权限管理")
@Api(tags = "Permission Management Interface")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list")
    @Auth(id = 1, name = "查询所有权限信息")
    @ApiOperation(value = "Get all permissions")
    public List<Permission> getPermissionList() {
        return permissionService.list();
    }

}
```
## 接口扫描
```java
@Component
public class ApplicationStartup implements ApplicationRunner {
    @Autowired
    private RequestMappingInfoHandlerMapping requestMappingInfoHandlerMapping;
    @Autowired
    private PermissionService permissionService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 扫描并获取所有需要权限处理的接口资源(该方法逻辑写在下面)
        List<Permission> list = getAuthResources();
        // 先删除所有操作权限类型的权限资源，待会再新增资源，以实现全量更新（注意哦，数据库中不要设置外键，否则会删除失败）
        permissionService.deletePermissionByType(1);
        // 如果权限资源为空，就不用走后续数据插入步骤
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        // 将资源数据批量添加到数据库
        permissionService.insertPermissions(list);
    }

    /**
     * 扫描并返回所有需要权限处理的接口资源
     */
    private List<Permission> getAuthResources() {
        // 接下来要添加到数据库的资源
        List<Permission> list = new LinkedList<>();
        // 拿到所有接口信息，并开始遍历
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingInfoHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((info, handlerMethod) -> {
            // 拿到类(模块)上的权限注解
            Auth moduleAuth = handlerMethod.getBeanType().getAnnotation(Auth.class);
            // 拿到接口方法上的权限注解
            Auth methodAuth = handlerMethod.getMethod().getAnnotation(Auth.class);
            // 模块注解和方法注解缺一个都代表不进行权限处理
            if (moduleAuth == null || methodAuth == null) {
                return;
            }

            // 拿到该接口方法的请求方式(GET、POST等)
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            // 如果一个接口方法标记了多个请求方式，权限id是无法识别的，不进行处理
            if (methods.size() != 1) {
                return;
            }
            // 将请求方式和路径用`:`拼接起来，以区分接口。比如：GET:/user/{id}、POST:/user/{id}
            String url = methods.toArray()[0] + ":" + info.getPatternsCondition().getPatterns().toArray()[0];
            // 将权限名、资源路径、资源类型组装成资源对象，并添加集合中
            Permission permission = new Permission();
            permission.setType(1)
                    .setUrl(url)
                    .setName(methodAuth.name())
                    .setId(moduleAuth.id() + methodAuth.id());
            list.add(permission);
        });
        return list;
    }
}

```
## 启动项目
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683475680306-be974102-c60f-457b-ae90-71ed2ee9222c.png#averageHue=%232e2d2d&clientId=u567c33e7-6148-4&from=paste&height=234&id=u2c33d32f&originHeight=292&originWidth=1265&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=54180&status=done&style=none&taskId=u234c0f2f-5af8-4b63-bc0d-cc2e281a9a2&title=&width=1012)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683475706443-cae82378-d3be-40cb-964c-1dedd34a0747.png#averageHue=%232f2e2d&clientId=u567c33e7-6148-4&from=paste&height=341&id=u830d0417&originHeight=426&originWidth=714&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=61083&status=done&style=none&taskId=ubb2ed8cc-33b4-4cf1-8f71-725e4948217&title=&width=571.2)
# 数据权限
数据权限指用户是否可以访问、操作某些数据。通常将数据权限定义为数据访问或操作的权限，例如管理员可以访问或修改所有用户的数据，普通用户只能访问或修改自己的数据。数据权限可以细分为行级数据权限和列级数据权限，分别指用户可以访问或操作哪些数据行或数据列。<br />通常数据权限需要在SQL语句中加入额外的限制条件，以实现只返回用户有权限访问的数据。这种限制条件可以基于用户的角色、部门、地区、时间等因素来确定，一般需要在代码中动态生成SQL语句，然后把生成的SQL语句发送到数据库执行。在实现过程中需要注意安全性问题，避免SQL注入等安全问题的出现。
## 创建新表
```sql
CREATE TABLE `company` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'company ID, unique',
                        `name` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT 'company name, unique',
                        `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'createdTime',
                        `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updatedTime',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `data` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'data ID, unique',
                           `name` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT 'data name, unique',
                           `company_id` bigint(20)  COMMENT 'company ID',
                           `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'createdTime',
                           `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updatedTime',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `user_company` (
                             `user_id` bigint(20) NOT NULL COMMENT 'userId',
                             `company_id` bigint(20) NOT NULL COMMENT 'roleId',
                             `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'createdTime',
                             `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updatedTime',
                             PRIMARY KEY (`user_id`,`company_id`),
                             KEY `user_company_company_id_fk` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1
```
## 准备数据
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683478702008-150fe8bc-0019-4306-9b8a-9dd659c357fc.png#averageHue=%23313030&clientId=u567c33e7-6148-4&from=paste&height=125&id=u8a976dda&originHeight=156&originWidth=785&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=16688&status=done&style=none&taskId=uc99c0615-3d28-4f6b-a46d-56366b5235e&title=&width=628)![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683478707520-742f2c46-26e9-4c00-aa5a-168f653d1205.png#averageHue=%23302f2f&clientId=u567c33e7-6148-4&from=paste&height=207&id=ub731023b&originHeight=259&originWidth=873&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=29097&status=done&style=none&taskId=u4d499899-4e31-4c25-b4be-922db1b1129&title=&width=698.4)![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683478713467-d721c548-211c-4825-abea-8fb65b012a76.png#averageHue=%232f2f2e&clientId=u567c33e7-6148-4&from=paste&height=197&id=ud838c1be&originHeight=246&originWidth=883&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=27296&status=done&style=none&taskId=u825a4c5f-503c-43d2-8390-3ae946172aa&title=&width=706.4)
## 根据表生成相关文件
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683480095233-6c0bc562-f9a4-442b-9b1d-82f87cdd7cda.png#averageHue=%233d4144&clientId=u567c33e7-6148-4&from=paste&height=459&id=u1a47c3ab&originHeight=574&originWidth=479&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=34043&status=done&style=none&taskId=u4c238ad5-067c-4231-a559-a8703e6a845&title=&width=383.2)![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683480108429-b45731f1-e1e8-4baf-b3d1-23369787369a.png#averageHue=%233e444b&clientId=u567c33e7-6148-4&from=paste&height=345&id=u4df34000&originHeight=431&originWidth=323&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=14467&status=done&style=none&taskId=uc23c97e8-3d7d-4bc6-92fe-c632631903c&title=&width=258.4)
## 准备接口
```java
@Slf4j
@RestController
@RequestMapping("/data")
@Api(tags = "Data Management Interface")
public class DataController {

    @Autowired
    DataService dataService;

    @GetMapping("/page/{current}")
    @ApiOperation(value = "Get data based on current page")
    public IPage<DataPageVO> getPage(@PathVariable("current") int current) {
        // 设置分页参数
        Page<DataPageVO> page = new Page<>();
        // 设置按创建时间倒序
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("data.created_time");
        orderItem.setAsc(false);
        // 设置按id升序
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setColumn("data.id");
        orderItem2.setAsc(true);
        page.setCurrent(current).addOrder(orderItem).addOrder(orderItem2);
        return dataService.selectPage(page);
    }
}

```
```java
@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, Data>
    implements DataService{
    @Override
    public IPage<DataPageVO> selectPage(Page<DataPageVO> page) {
        QueryWrapper<DataPageVO> queryWrapper = new QueryWrapper<>();
        IPage<DataPageVO> result = baseMapper.selectPage(page, queryWrapper);
        System.out.println(result.getRecords());
        return result;
    }
}

```
## 自定义sql拦截器
自定义的MyPaginationInterceptor类，继承了Mybatis Plus框架的InnerInterceptor接口，实现了拦截器的拦截操作。在beforePrepare()方法中，通过Mybatis的MappedStatement对象获取当前执行的SQL语句，然后判断是否需要拦截。如果是执行分页操作，即方法名为"com.imyuanxiao.rbac.mapper.DataMapper.selectPage"，则对SQL语句进行拦截，创建join条件，加入用户id和公司id的联合查询，并添加到原来的SQL语句中，实现了数据权限的控制。
```java
@Slf4j
public class MyPaginationInterceptor implements InnerInterceptor {
    @Override
    public void beforePrepare(StatementHandler statementHandler, Connection connection, Integer transactionTimeout) {
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // id为执行的mapper方法的全路径名，如com.imyuanxiao.rbac.mapper.selectPage
        String id = mappedStatement.getId();
        log.info("mapper: ==> {}", id);
        // 如果不是指定的方法，直接结束拦截
        // 如果方法多可以存到一个集合里，然后判断当前拦截的是否存在集合中
        if (!id.startsWith("com.imyuanxiao.rbac.mapper.DataMapper.selectPage")) {
            return;
        }

        // 获取到原始sql语句
        String sql = statementHandler.getBoundSql().getSql();
        log.info("原始SQL语句： ==> {}", sql);
        sql = getSql(sql);
        // 修改sql
        metaObject.setValue("delegate.boundSql.sql", sql);
        log.info("拦截后SQL语句：==>{}", sql);
    }

    /**
     * 解析SQL语句，并返回新的SQL语句
     *
     * @param sql 原SQL
     * @return 新SQL
     */
    private String getSql(String sql) {
        try {
            // 解析语句
            Statement stmt = CCJSqlParserUtil.parse(sql);
            Select selectStatement = (Select) stmt;
            PlainSelect ps = (PlainSelect) selectStatement.getSelectBody();
            // 拿到表信息
            FromItem fromItem = ps.getFromItem();
            Table table = (Table) fromItem;
            String mainTable = table.getAlias() == null ? table.getName() : table.getAlias().getName();

            List<Join> joins = ps.getJoins();
            if (joins == null) {
                joins = new ArrayList<>(1);
            }

            // 创建连表join条件
            Join join = new Join();
            join.setInner(true);
            join.setRightItem(new Table("user_company uc"));
            // 第一个：两表通过company_id连接
            EqualsTo joinExpression = new EqualsTo();
            joinExpression.setLeftExpression(new Column(mainTable + ".company_id"));
            joinExpression.setRightExpression(new Column("uc.company_id"));
            // 第二个条件：和当前登录用户id匹配
            EqualsTo userIdExpression = new EqualsTo();
            userIdExpression.setLeftExpression(new Column("uc.user_id"));
            // 拿到当前用户
            UserDetailsVO user = (UserDetailsVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userIdExpression.setRightExpression(new LongValue(user.getUser().getId()));
            // 将两个条件拼接起来
            join.setOnExpression(new AndExpression(joinExpression, userIdExpression));
            joins.add(join);
            ps.setJoins(joins);

            // 修改原语句
            sql = ps.toString();
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return sql;
    }
}
```
## 配置MybatisConfig
在分页插件前面设置自定义sql拦截器
```java
@Configuration
@MapperScan("com.imyuanxiao.rbac.mapper")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 此处为自定义sql拦截器
        interceptor.addInnerInterceptor(new MyPaginationInterceptor());
        // 此处为分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}

```
# SpringSecurity登录认证
[【项目实践】一文带你搞定Spring Security + JWT实现前后端分离下的认证授权](https://zhuanlan.zhihu.com/p/342755411)<br />[springboot 2.7整合spring security 5.7整合jwt实现用户登录注册与鉴权全记录_ricardo.M.Yu的博客-CSDN博客](https://blog.csdn.net/yu619251940/article/details/126872454)<br />[Spring Boot 3 + Spring Security 6 - JWT Authentication and Authorisation [NEW] [2023]](https://www.youtube.com/watch?v=KxqlJblhzfI)
## 配置UserDetailsVO
这个类是Spring Security框架中用于存储用户详情的一个实体类，实现了UserDetails接口，用于封装用户的基本信息和角色权限等信息。在这个类中，可以通过用户对象和角色集合构造方法，生成一个包含用户信息和权限的UserDetailsVO对象，并实现UserDetails接口的方法，用于在Spring Security中进行用户的认证和授权。
```java
@Data
public class UserDetailsVO implements UserDetails {

    private User user;

    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将用户所拥有的角色权限名称生成一个集合
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 用户是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户是否被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户凭证是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否被禁用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
```
## 实现UserDetailsService接口
让用户查询类继承**UserDetailsService**接口，主要实现一个方法**loadUserByUsername（）**。这个方法是实现 Spring Security 中 UserDetailsService 接口的方法，用于根据用户名从数据库中获取用户信息，并将其转换为 UserDetails 对象，以供 Spring Security 进行认证和授权。
```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService, UserDetailsService {
    ...
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user info by username
        User user = this.getUserByUsername(username);
        // get user roles by user id
        Set<Role> roleSet = roleService.roleListByUsername(user.getId());
        return new UserDetailsVO().setUser(user).setRoles(roleSet);
    }
}
```
## 重写JwtUtil工具类
重写JWT（JSON Web Token）管理类，提供生成、验证和解析JWT的方法。其中，generate()方法用于生成JWT Token，verifyToken()方法用于验证JWT Token是否合法，extractUsername()方法用于从JWT Token中提取用户名。
```java
@Slf4j
public final class JwtManager {
    /**
     * This secret key is crucial to prevent tampering of the JWT.
     */
    private final static byte[] secretKeyBytes = "my_secret_key".getBytes();


    /**
     * The expiration time is currently set to 30 minutes,
     * this configuration depends on business requirements.
     */
    private final static Integer EXPIRATION = 30;

    /**
     * Generate jwt token
     * @author imyuanxiao
     * @date 14:54 2023/5/7
     * @param userName username
     * @return jwt token
     **/
    public static String generate(String userName) {
        DateTime now = DateUtil.date();
        DateTime ddl = DateUtil.offsetMinute(now, EXPIRATION);
        Map<String, Object> map = new HashMap<String, Object>() {
            {
                put(JWTPayload.ISSUED_AT, now);
                put(JWTPayload.EXPIRES_AT, ddl);
                put(JWTPayload.NOT_BEFORE, now);
                put(JWTPayload.SUBJECT, userName); //put username in 'sub'
            }
        };
        return "Bearer " + JWTUtil.createToken(map, secretKeyBytes);
    }

    /**
     * Verify token
     * @author imyuanxiao
     * @date 14:54 2023/5/7
     * @param token jwt token
     * @throws RuntimeException Throw an exception if verification fails.
     **/
    public static void verifyToken(String token) {
        // 解析失败了会抛出异常，所以要捕捉一下。token过期、token非法都会导致解析失败
        try {
            //验证签名
            boolean verify = JWTUtil.verify(token, JWTSignerUtil.hs256(secretKeyBytes));
            if(!verify) {
                throw new RuntimeException("Signature verification failed.");
            }
            // 验证算法和时间
            JWTValidator validator = JWTValidator.of(token);
            // 验证算法
            validator.validateAlgorithm(JWTSignerUtil.hs256(secretKeyBytes));
            // 验证时间
            JWTValidator.of(token).validateDate();
        } catch (Exception e) {
            log.error("Signature verification failed:" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Parse token
     * @author imyuanxiao
     * @date 14:56 2023/5/7
     * @param token token to parse
     * @return Parse the JWT token to a JWTPayload object if successful
     **/
    private static Claims extractAllClaims(String token) {
        verifyToken(token);
        return JWTUtil.parseToken(token).getPayload();
    }

    /**
     * Extract username from token
     * @author imyuanxiao
     * @date 14:56 2023/5/7
     * @param token token to parse
     * @return Return username if successful
     **/
    public static String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return String.valueOf(claims.getClaim(JWTPayload.SUBJECT));
    }

}
```
## 配置LoginFilter
这个类是一个JWT认证的过滤器，在Spring Security中用来对接收到的请求进行处理。该过滤器主要作用是从 HTTP 头部提取 JWT Token，并从中提取出用户的认证信息。接着，根据该信息，获取用户详细信息、密码、角色信息等，最终生成包含用户详细信息的 UserDetailsVO 对象，并使用 UsernamePasswordAuthenticationToken 将其设置到当前线程的 SecurityContext 中，表示用户已经通过身份认证，并且具有相应的授权信息。<br />注意，过滤器会在拦截器之前执行
```java
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    UserService userService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = response.getHeader("Authorization");
        final String jwt;
        final String username;
        // 如果token为空或没有以”Bearer "开头，跳过本层过滤
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        // 提取用户名，查询数据库
        // 在提取时会验证token是否有效
        username = JwtUtil.extractUsername(jwt);
        // username有效，并且上下文对象中没有配置用户
        if(StrUtil.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null){
            // 从数据库中获取用户信息、密码、角色信息等，返回一个包含用户详细信息的 UserDetailsVO 对象
            UserDetailsVO userDetailsVO = userService.getUserDetailsVO(username);
            // 创建一个包含了用户的认证信息、凭证信息（之前验证过jwt，不需要凭证）、用户的授权信息的对象
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetailsVO,
                    null,
                    userDetailsVO.getAuthorities()
            );
            // 封装 HTTP 请求的详细信息的对象，包含了请求的 IP 地址、请求的 Session ID、请求的 User Agent 等
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            // 将 authToken 设置到当前线程的 SecurityContext 中，表示用户已经通过身份认证，并且具有相应的授权信息
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
```
## 配置config
这个类是一个 Spring Security 的配置类，主要作用是配置 Spring Security 的安全策略、权限控制等相关设置。具体作用如下：

- 配置 HttpSecurity：通过配置 HttpSecurity 对象，可以定义哪些请求需要认证、哪些请求不需要认证，以及对于不同的请求类型，如 GET、POST 等，应该采取什么方式进行认证。
- 配置 AuthenticationProvider：通过配置 AuthenticationProvider，可以定义认证逻辑，如密码校验等。
- 配置 PasswordEncoder：通过配置 PasswordEncoder，可以定义密码加密方式，保证密码的安全性。
- 配置 AuthenticationManager：通过配置 AuthenticationManager，可以获取认证管理器，进行登录时的认证操作。
- 配置 SecurityFilterChain：通过配置 SecurityFilterChain，可以定义 Spring Security 的安全过滤器链，包括自定义的过滤器。
```java
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private UserServiceImpl userDetailsService;
    @Autowired
    private LoginFilter loginFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 关闭csrf和frameOptions，如果不关闭会影响前端请求接口
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // 开启跨域以便前端调用接口
        http.cors();
        // 这是配置的关键，决定哪些接口开启防护，哪些接口绕过防护
        http.authorizeRequests()
                // 注意这里，是允许前端跨域联调的一个必要配置
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 指定某些接口不需要通过验证即可访问。像登陆、注册接口肯定是不需要认证的
                .antMatchers("/login/**", "/auth/**").permitAll()
                // 这里意思是其它所有接口需要认证才能访问
                .antMatchers("/**").authenticated()
                // 指定认证错误处理器
                .and().exceptionHandling().authenticationEntryPoint(new MyEntryPoint()).accessDeniedHandler(new MyDeniedHandler());
        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 将自定义的认证过滤器替换掉默认的认证过滤器
        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * 密码明文加密方式配置
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
```
## AuthController
原来的LoginController改为AuthController，用于实现用户身份验证、注册等功能。验证码功能需要配合redis使用，后面再完善。
```java
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @ApiOperation(value = "Login by password")
    @PostMapping("/login")
    public UserVO login(@RequestBody  @Valid LoginParam param){
        return userService.login(param);
    }

    @ApiOperation(value = "Get Verification Code")
    @GetMapping("/code/{phone}")
    public String sendCode(@PathVariable("phone") @NotBlank String phone){
        //TODO check whethere this is code for this phone in redis

        //TODO if phone is valid, send captcha to phone
        String code = RandomUtil.randomNumbers(4);
        //TODO send code to phone

        //TODO save phone + code to redis

        return "验证码已发送至手机号：" + phone + ",验证码为："+ code;
    }

    /**
     * 验证手机号和验证码
     *
     * @author imyuanxiao
     * @date 19:00 2023/5/6
     * @param param
     * @return com.imyuanxiao.rbac.model.vo.UserVO
     **/
    @ApiOperation(value = "Register")
    @PostMapping("/register")
    public UserVO register(@RequestBody @Valid RegisterParam param){
        //TODO get Code from redis according to phone
        //TODO verify code and phone
        checkValidationForRegister(param);
        return userService.register(param);
    }

    public void checkValidationForRegister(RegisterParam param){
        if(StrUtil.isBlank(param.getPhone())){
            throw new ApiException(ResultCode.VALIDATE_FAILED, "手机号为空或格式不正确！");
        }
        if(StrUtil.isBlank(param.getPassword())){
            throw new ApiException(ResultCode.VALIDATE_FAILED, "密码为空或格式不正确！");
        }
        if(StrUtil.isBlank(param.getCode())){
            throw new ApiException(ResultCode.VALIDATE_FAILED, "验证码为空或格式不正确！");
        }
    }

}
```
## 测试
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683405706087-31b7c10b-681b-40bd-bb7e-c0a3cb8ea2d0.png#averageHue=%23fcfcfb&clientId=u567c33e7-6148-4&from=paste&height=646&id=u06e9fe21&originHeight=807&originWidth=1184&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=88852&status=done&style=none&taskId=u72fa0141-dd53-4227-be62-3414ffc9c2b&title=&width=947.2)
# SpringSecurity权限认证
## 配置鉴权规则
这个类是一个Spring Security的安全元数据源（SecurityMetadataSource），主要用于获取当前请求所需的访问权限资源，并将其与请求进行匹配，以决定当前请求是否需要授权才能访问。在该类中，通过注入PermissionService来获取当前系统中的所有权限资源，并将它们存储在一个静态变量PERMISSIONS中，以便在getAttributes()方法中遍历这些权限资源，以找到与当前请求所需的权限资源匹配的资源，并将其返回。如果当前请求无需授权即可访问，则该方法返回null。<br />该类还实现了InitializingBean接口，它在MySecurityMetadataSource实例化之后执行init()方法，用于在系统启动时加载所有权限资源。
```java
@Slf4j
@Component("mySecurityMetadataSource")
public class MySecurityMetadataSource implements SecurityMetadataSource {

    @Autowired
    private PermissionService permissionService;
    /**
     * 当前系统所有url资源
     */
    @Getter
    private static final Set<Permission> PERMISSIONS = new HashSet<>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        log.info("---MySecurityMetadataSource---");
        // 该对象是Spring Security帮我们封装好的，可以通过该对象获取request等信息
        FilterInvocation filterInvocation = (FilterInvocation) object;
        HttpServletRequest request = filterInvocation.getRequest();
        // 遍历所有权限资源，以和当前请求所需的权限进行匹配
        for (Permission permission : PERMISSIONS) {
            // 因为我们url资源是这种格式：GET:/API/user/test/{id}，冒号前面是请求方法，冒号后面是请求路径，所以要字符串拆分
            String[] split = permission.getUrl().split(":");
            // 因为/API/user/test/{id}这种路径参数不能直接equals来判断请求路径是否匹配，所以需要用Ant类来匹配
            AntPathRequestMatcher ant = new AntPathRequestMatcher(split[1]);
            // 如果请求方法和请求路径都匹配上了，则代表找到了这个请求所需的权限资源
            if (request.getMethod().equals(split[0]) && ant.matches(request)) {
                // 将我们权限资源id返回
                return Collections.singletonList(new SecurityConfig(permission.getId().toString()));
            }
        }
        // 走到这里就代表该请求无需授权即可访问，返回空
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @PostConstruct
    public void init() {
        log.info("Initializing permissions...");
        loadPermissions();
    }

    private void loadPermissions() {
        List<Permission> permissions = permissionService.list();
        if (permissions != null && !permissions.isEmpty()) {
            List<Permission> filteredPermissions = permissions.stream()
                    .filter(permission -> permission.getType() == 1).toList();
            PERMISSIONS.clear();
            PERMISSIONS.addAll(filteredPermissions);
            log.info("Loaded {} permissions.", filteredPermissions.size());
        } else {
            log.warn("No permissions found.");
        }
    }
}
```
## 用户权限GrantedAuthority
### 重写UserDetailsVO
这个类的作用是扩展Spring Security提供的User类，用于封装用户的详细信息，包括用户信息和权限信息。
```java
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class UserDetailsVO extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserDetailsVO(User user, Collection<? extends GrantedAuthority> authorities) {
        // 必须调用父类的构造方法，初始化用户名、密码、权限
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }
    
}
```
### 重写loadUserByUsername方法
```java
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // get user info by username
    User user = this.getUserByUsername(username);
    // 先将该用户所拥有的资源id全部查询出来，再转换成`SimpleGrantedAuthority`权限对象
    Set<SimpleGrantedAuthority> authorities = permissionService.getIdsByUserId(user.getId())
            .stream()
            .map(String::valueOf)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
    return new UserDetailsVO(user, authorities);
}
```
## 授权组件
### 授权管理AccessDecisionManager
这个类是 Spring Security 中的 AccessDecisionManager 接口的实现类，用于实现决策管理器的决策逻辑，决策管理器在访问受保护的资源时，根据用户所拥有的权限和资源所需的权限进行匹配，决定用户是否可以访问该资源。具体来说，它的 decide 方法中实现了判断授权规则和当前用户所属权限是否匹配的逻辑，如果匹配则代表当前登录用户是有该权限的，否则抛出 AccessDeniedException 异常。supports 方法则是用来判断传入的 ConfigAttribute 和 Class 对象是否被支持，由于这里都返回了 true，因此代表支持任何传入的 ConfigAttribute 和 Class 对象。
```java
@Slf4j
@Component
public class MyDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) {
        // 如果授权规则为空则代表此URL无需授权就能访问
        if (CollectionUtil.isEmpty(configAttributes)) {
            return;
        }
        log.info("---DecisionManager---");
        // 判断授权规则和当前用户所属权限是否匹配
        for (ConfigAttribute ca : configAttributes) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                // 如果匹配上了，代表当前登录用户是有该权限的，直接结束方法
                if (Objects.equals(authority.getAuthority(), ca.getAttribute())) {
                    return;
                }
            }
        }
        // 走到这里就代表没有权限
        throw new AccessDeniedException("没有相关权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
```
### 授权错误处理器AccessDeniedHandler
这个类实现了Spring Security的AccessDeniedHandler接口，用于处理权限不足的情况。在Web应用中，当用户访问某个受保护的资源但没有足够的权限时，AccessDeniedHandler将被调用。这个具体实现中，它将在HTTP响应中输出“没有相关权限”文本。
```java
public class MyDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        out.write("没有相关权限");
        out.flush();
        out.close();
    }
}
```
## 配置授权组件
### 授权过滤器
这个类是一个Spring Security中的拦截器过滤器，继承了AbstractSecurityInterceptor，实现了Filter接口，其作用是拦截请求并进行安全认证和权限校验。
```java
@Slf4j
@Component
public class AuthFilter  extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private SecurityMetadataSource securityMetadataSource;

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        // 将我们自定义的SecurityMetadataSource给返回
        return this.securityMetadataSource;
    }

    @Autowired
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        // 将我们自定义的AccessDecisionManager给注入
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("---AuthFilter---");

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        // 这里调用了父类的AbstractSecurityInterceptor的方法,也就是调用了accessDecisionManager
        InterceptorStatusToken token = super.beforeInvocation(fi);

        try {
            // 执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }  finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}

```
### 配置authFilter
```java
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    ...
    @Autowired
    private AuthFilter authFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        ...
        // 将自定义的认证过滤器替换掉默认的认证过滤器
        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authFilter, FilterSecurityInterceptor.class);
        
        return http.build();
    }
```
## 测试
### 编写测试方法
```java
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "测试有权限1001")
    @GetMapping("/1")
    public String testHasAuth() {
        return "测试成功，你有权限";
    }

    @ApiOperation(value = "测试无权限1002")
    @GetMapping("/2")
    public String testNoAuth() {
        return "测试成功，你有权限";
    }
}
```
![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683412321403-a8f25dcc-904d-4b7d-9512-27b624ee828d.png#averageHue=%23faf9f8&clientId=u567c33e7-6148-4&from=paste&height=414&id=ufd4d9072&originHeight=518&originWidth=602&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=43542&status=done&style=none&taskId=uf7fda0ac-b2db-468f-a7d2-689f7e953d0&title=&width=481.6)<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/29364238/1683412366199-c194395f-47a4-4e6a-94f7-426c9c85a5b9.png#averageHue=%23fbfbfa&clientId=u567c33e7-6148-4&from=paste&height=487&id=u21e0567d&originHeight=609&originWidth=774&originalType=binary&ratio=1.25&rotation=0&showTitle=false&size=52470&status=done&style=none&taskId=u675b87c3-9466-42a8-bf18-bf9e8842f44&title=&width=619.2)
# SpringSecurity跨域问题
前端发送请求到后端，如果没有进行跨域配置，就会被拦截，需要做如下设置。
```java
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    ...
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        ...
        // Enable cross-origin resource sharing (CORS) to facilitate frontend calls to the API.
        http.cors().configurationSource(corsConfigurationSource());
        ...
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    ...
}
```
# 完善controller
详见git仓库<br />[https://github.com/imyuanxiao/rbac](https://github.com/imyuanxiao/rbac)
# 后续更新
根据前端开发进度，完善后端对应接口
## UserVO
增加返回信息：角色ID。注意，只需要返回id，前端根据id匹配名称，而且前端会做国际化工作。
```java
@Data
@Accessors(chain = true)
public class UserVO {

    private Long id;

    private String username;

    private String token;

    private Set<Long> roles;

    private Set<Long> permissionIds;

}
```
## 权限验证接口
用户登录后，前端每次切换路由都会向后端发送请求，获取当前用户的权限信息，以防止用户权限发生变化。
### AuthController
增加方法获取用户信息，前端需要传token和用户名，如果token正确，用户信息会被保存在上下文对象中。之后验证用户名和token解析的用户名是否一致。
```java
public class AuthController {
	...
    @PostMapping("/my-permission")
    @ApiOperation(value = "Get UserVO every time route changes")
    public Set<Long> myPermission(@RequestBody @NotBlank String username){
        // get user in context
        return userService.myPermission(username);
    }
    ...
}
```
注意，这里需要更改SpringSecurityConfig的允许绕过权限验证的路径，否则可以不需要token就调用myPermission()方法，会出错。我这里把原来的"/auth/**"换成了更具体的路径。
```java
public class SpringSecurityConfig {
	...
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        ...
        // This is a key configuration that determines which interfaces are protected and which interfaces bypass protection.
        http.authorizeRequests()
                ...
                // Specifies that certain endpoints can be accessed without authentication.
                .antMatchers(
                        "/auth/login",
                        "/auth/code/**",
                        "/auth/register",
                    ...)
        }
        ...
}
```
### UserServiceImpl
该类里主要提取公用方法，增加通过上下文获取用户信息的方法。<br />注意：用myPermission方法检查登录信息的时候，只返回用户权限合集。
```java
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService, UserDetailsService {
    ....
    @Override
    public UserVO login(LoginParam loginParam) {
        // Verify user from database
        User user = this.lambdaQuery()
                .eq(StrUtil.isNotBlank(loginParam.getUsername()), User::getUsername, loginParam.getUsername())
                .one();

        // Throw error if user or password is wrong
        if(user == null || !passwordEncoder.matches(loginParam.getPassword(), user.getPassword())){
            throw new ApiException(ResultCode.VALIDATE_FAILED, "Username or password is incorrect！");
        }
        // Generate token, get and put user permissions in UserVO object
        return getUserVO(user);
    }
    
    @Override
    public UserVO register(RegisterParam param) {
        // Use phone and code to register, initial username is phone number
        User user = new User().setUsername(param.getPhone())
                .setPassword(passwordEncoder.encode(param.getPassword()))
                .setPhone(param.getPhone());
        try {
            this.save(user);
            // Add default user role - 3L visitor
            roleService.insertRolesByUserId(user.getId(), List.of(3L));
            // Get permissions id
            Set<Long> permissionIds = permissionService.getIdsByUserId(user.getId());
            // Put user info, token, permissions in UserVO object
            return getUserVO(user);
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED, "Phone number already exists.");
        }
    }

    @Override
    public Set<Long> myPermission() {
        Long userId = SecurityContextUtil.getCurrentUserId();
        return permissionService.getIdsByUserId(userId);
    }

    private UserVO getUserVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        userVO.setRoles(roleService.getIdsByUserId(user.getId()))
                .setPermissionIds(permissionService.getIdsByUserId(user.getId()));
        userVO.setToken(JwtManager.generate(user.getUsername()));
        return userVO;
    }
    ...
}
```
### SpringSecurityUtil
```java
public class SecurityContextUtil {
    ...
    /**
     * Get user object from spring security context
     * @author imyuanxiao
     * @date 12:14 2023/5/9  
     * @return User Object
     **/
    public static User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsVO userDetails = (UserDetailsVO)authentication.getPrincipal();
        return userDetails.getUser();
    }
	...
}

```
## token更新接口
```java
public class AuthController {
    ...
    @GetMapping("/update-token")
    @ApiOperation(value = "Update token")
    public String updateToken(){
        return userService.updateToken();
    }
	...
}
```
```java
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService, UserDetailsService {
    ...
    @Overr  ide
    public String updateToken() {
        User user = SecurityContextUtil.getCurrentUser();
        return JwtManager.generate(user.getUsername());
    }
    ...
}
```

# 踩坑记录
## HttpMediaTypeNotAcceptableException
Result类上加上lombok的@Data，在你的代码中，由于Result类没有提供toString()方法，Spring MVC框架在返回结果时就无法将结果转换为可接受的格式，从而抛出了HttpMediaTypeNotAcceptableException异常。通过在Result类上加上@Data注解，可以自动生成toString()方法，从而解决了这个问题。
## 数据库写不了中文
需要在数据库表和字段的定义中指定字符集为utf8或utf8mb4
```sql
CREATE TABLE my_table (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
);

```
