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
 * @ClassName User
 * @Description Entity class corresponding to table 'user'.
 * @TableName user
 * @Author imyuanxiao
 * @Version 1.0
 **/
@TableName(value ="user")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements Serializable{

    /**
     * username, unique
     */
    @TableField(value = "username")
    private String username;

    /**
     * password
     */
    @TableField(value = "password")
    private String password;

    /**
     * phone
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * email
     */
    @TableField(value = "email")
    private String email;

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

    /**
     * avatar
     */
    @TableField(value = "avatar")
    private byte[] avatar;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}