package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @ClassName RegisterParam
 * @Description This class is inherited by all entity classes.
 * @Author imyuanxiao
 * @Version 1.0
 **/
@Data
public abstract class BaseEntity {
    /**
     * Primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
}
