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
 * @ClassName Permission
 * @Description Entity class corresponding to table 'permission'.
 * @TableName permission
 * @Author imyuanxiao
 * @Version 1.0
 **/
@TableName(value ="permission")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseEntity{

    /**
     * permissionName, unique
     */
    @TableField(value = "name")
    private String name;

    /**
     * permissionType, 0-view, 1-action
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * permissionUrl
     */
    @TableField(value = "url")
    private String url;

    /**
     * description
     */
    @TableField(value = "description")
    private String description;

    /**
     * createdTime
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * updatedTime
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

}