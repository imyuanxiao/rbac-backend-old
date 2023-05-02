package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import lombok.Data;

/**
 * @Author imyuanxiao
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User  extends BaseEntity implements Serializable {

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
    private Date created_time;

    /**
     * updatedTime
     */
    @TableField(value = "updated_time")
    private Date updated_time;

    /**
     * avatar
     */
    @TableField(value = "avatar")
    private byte[] avatar;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getCreated_time() == null ? other.getCreated_time() == null : this.getCreated_time().equals(other.getCreated_time()))
            && (this.getUpdated_time() == null ? other.getUpdated_time() == null : this.getUpdated_time().equals(other.getUpdated_time()))
            && (Arrays.equals(this.getAvatar(), other.getAvatar()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getCreated_time() == null) ? 0 : getCreated_time().hashCode());
        result = prime * result + ((getUpdated_time() == null) ? 0 : getUpdated_time().hashCode());
        result = prime * result + (Arrays.hashCode(getAvatar()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(this.getId());
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", created_time=").append(created_time);
        sb.append(", updated_time=").append(updated_time);
        sb.append(", avatar=").append(avatar);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}