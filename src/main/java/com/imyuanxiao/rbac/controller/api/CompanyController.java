package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.annotation.Auth;
import com.imyuanxiao.rbac.model.entity.Company;
import com.imyuanxiao.rbac.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CompanyController
 * @Description Company Management Interface
 * @Author imyuanxiao
 * @Date 2023/5/7 18:23
 * @Version 1.0
 **/

@Slf4j
@RestController
@RequestMapping("/company")
@Auth(id = 2000, name = "组织结构")
@Api(tags = "Company Management Interface")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/list")
    @Auth(id = 1, name = "查询所有公司信息")
    @ApiOperation(value = "Get all companies")
    public List<Company> list() {
        return companyService.list();
    }

}
