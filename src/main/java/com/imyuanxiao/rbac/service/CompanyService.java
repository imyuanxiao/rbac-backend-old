package com.imyuanxiao.rbac.service;

import com.imyuanxiao.rbac.model.entity.Company;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @ClassName CompanyService
 * @Description Database operation service for table "company"
 * @Author imyuanxiao
 * @Date 2023/5/3 17:59:54
 * @Version 1.0
 **/
public interface CompanyService extends IService<Company> {
    /**
     * 根据用户id获取公司id
     * @param userId 用户id
     * @return 该用户的公司id集合
     */
    Set<Long> getIdsByUserId(Long userId);

    /**
     * 根据用户id删除该用户的所有公司
     * @param userId 用户id
     */
    void removeByUserId(Serializable userId);
    /**
     * 根据用户id批量增加公司
     * @param userId 用户id
     * @param companyIds 公司id集合
     */
    void insertCompanyByUserId(Long userId, Collection<Long> companyIds);
}
