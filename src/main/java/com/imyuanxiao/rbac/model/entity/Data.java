package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @ClassName Permission
 * @Description Entity class corresponding to table "data".
 * @TableName data
 * @Author imyuanxiao
 * @Version 1.0
 **/
@TableName(value ="data")
@lombok.Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Data extends BaseEntity {

    /**
     * data name, unique
     */
    @TableField(value = "name")
    private String name;

    /**
     * company ID
     */
    @TableField(value = "company_id")
    private Long companyId;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}