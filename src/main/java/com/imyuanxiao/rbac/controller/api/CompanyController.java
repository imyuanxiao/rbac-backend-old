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
 * @Description TODO
 * @Author imyuanxiao
 * @Date 2023/5/7 18:23
 * @Version 1.0
 **/

@Slf4j
@RestController
@RequestMapping("/company")
@Api(tags = "Company Management Interface")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/list")
    @ApiOperation(value = "Get all companies")
    public List<Company> list() {
        return companyService.list();
    }

}
