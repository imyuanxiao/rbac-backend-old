package com.imyuanxiao.rbac.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.annotation.Auth;
import com.imyuanxiao.rbac.model.entity.Role;
import com.imyuanxiao.rbac.model.param.RoleParam;
import com.imyuanxiao.rbac.model.vo.RolePageVO;
import com.imyuanxiao.rbac.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.imyuanxiao.rbac.util.CommonUtil.ACTION_SUCCESSFUL;

/**
 * @ClassName RoleController
 * @Description Role Management Interface
 * @Author imyuanxiao
 * @Date 2023/5/4 17:35
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/role")
@Auth(id = 2000, name = "角色管理")
@Api(tags = "Role Management Interface")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    @Auth(id = 1, name = "新增角色")
    @ApiOperation(value = "Add role")
    public String createRole(@RequestBody @Validated(RoleParam.CreateRole.class) RoleParam param) {
        roleService.createRole(param);
        return ACTION_SUCCESSFUL;
    }

    @DeleteMapping("/delete")
    @Auth(id = 2, name = "删除角色")
    @ApiOperation(value = "Delete role")
    public String deleteUser(Long[] ids) {
        roleService.removeByIds(Arrays.asList(ids));
        return ACTION_SUCCESSFUL;
    }

    @PutMapping("/update")
    @Auth(id = 3, name = "编辑角色")
    @ApiOperation(value = "Update role")
    public String updateMenus(@RequestBody @Validated(RoleParam.UpdateResources.class) RoleParam param) {
        roleService.updatePermissions(param);
        return ACTION_SUCCESSFUL;
    }

    @GetMapping("/list")
    @Auth(id = 4, name = "查询所有角色信息")
    @ApiOperation(value = "Get all roles")
    public List<Role> getRoleList() {
        return roleService.list();
    }

    @GetMapping("/page/{current}")
    @Auth(id = 5, name = "分页查询角色信息")
    @ApiOperation(value = "Page through role information")
    public IPage<RolePageVO> getPage(@PathVariable("current") int current) {
        // Set pagination parameters
        Page<RolePageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("id");
        page.setCurrent(current).addOrder(orderItem);
        return roleService.selectPage(page);
    }

}
