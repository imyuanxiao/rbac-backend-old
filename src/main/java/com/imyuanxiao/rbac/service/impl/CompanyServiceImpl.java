package com.imyuanxiao.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.model.entity.Company;
import com.imyuanxiao.rbac.service.CompanyService;
import com.imyuanxiao.rbac.mapper.CompanyMapper;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
* @author Administrator
* @description 针对表【company】的数据库操作Service实现
* @createDate 2023-05-07 17:59:54
*/
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company>
    implements CompanyService{
    @Override
    public Set<Long> getIdsByUserId(Long userId) {
        return baseMapper.selectIdsByUserId(userId);
    }

    @Override
    public void removeByUserId(Serializable userId) {
        baseMapper.deleteByUserId(userId);
    }

    @Override
    public void insertCompanyByUserId(Long userId, Collection<Long> companyIds) {
        baseMapper.insertCompanyByUserId(userId, companyIds);
    }
}




