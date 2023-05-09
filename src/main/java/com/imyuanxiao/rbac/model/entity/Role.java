package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName Role
 * @Description Entity class corresponding to table 'role'.
 * @TableName role
 * @Author imyuanxiao
 * @Version 1.0
 **/
@TableName(value ="role")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Role  extends BaseEntity{

    /**
     * roleName, unique
     */
    @TableField(value = "name")
    private String name;

    /**
     * description
     */
    @TableField(value = "description")
    private String description;

//    /**
//     * createdTime
//     */
//    @TableField(value = "created_time")
//    private Date createdTime;
//
//    /**
//     * updatedTime
//     */
//    @TableField(value = "updated_time")
//    private Date updatedTime;

}