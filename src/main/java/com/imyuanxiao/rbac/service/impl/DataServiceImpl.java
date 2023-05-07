package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.Data;
import com.imyuanxiao.rbac.model.vo.DataPageVO;
import com.imyuanxiao.rbac.service.DataService;
import com.imyuanxiao.rbac.mapper.DataMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName DataServiceImpl
 * @Description Database operation Service implementation for the table "data".
 * @Author imyuanxiao
 * @Date 2023/5/7 18:00
 * @Version 1.0
 **/
@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, Data>
    implements DataService{
    @Override
    public IPage<DataPageVO> selectPage(Page<DataPageVO> page) {
        QueryWrapper<DataPageVO> queryWrapper = new QueryWrapper<>();
        IPage<DataPageVO> result = baseMapper.selectPage(page, queryWrapper);
        System.out.println(result.getRecords());
        return result;
    }
}




