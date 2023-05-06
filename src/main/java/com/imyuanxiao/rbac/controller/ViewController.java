package com.imyuanxiao.rbac.controller;

import com.imyuanxiao.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 * @Author: imyuanxiao
 * @Date: 2023/5/3 21:55
 */
@Controller
public class ViewController {

    @Autowired
    PermissionService permissionService;


}
