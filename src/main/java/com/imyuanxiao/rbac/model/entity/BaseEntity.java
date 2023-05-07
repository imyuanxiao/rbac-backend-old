package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @ClassName RegisterParam
 * @Description 所有实体对象集成此类
 * @Author imyuanxiao
 * @Version 1.0
 **/
@Data
public abstract class BaseEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
}
