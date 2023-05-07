package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName company
 */

/**
 * @ClassName Permission
 * @Description Entity class corresponding to table "data".
 * @TableName data
 * @Author imyuanxiao
 * @Version 1.0
 **/
@TableName(value ="company")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Company extends BaseEntity {

    /**
     * company name, unique
     */
    @TableField(value = "name")
    private String name;

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