package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * 基础实体类，所有实体对象集成此类
 * @Author : imyuanxiao
 */
@Data
public abstract class BaseEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
}
