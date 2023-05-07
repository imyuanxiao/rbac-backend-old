package com.imyuanxiao.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.entity.Data;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.rbac.model.vo.DataPageVO;

/**
 * @ClassName DataService
 * @Description Database operation service for table "data".
 * @Author imyuanxiao
 * @Date 2023/5/3 18:00:06
 * @Version 1.0
 **/
public interface DataService extends IService<Data> {

    /**
     * Get pagination
     * @author imyuanxiao
     * @date 18:10 2023/5/7
     * @param page pagination parameters
     * @return Pagination Object
     **/
    IPage<DataPageVO> selectPage(Page<DataPageVO> page);
}
