package com.sgm.login.service;

import com.sgm.login.model.entity.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceService {
    /**
     * 保存资源信息
     * @param resource
     * @return
     */
    void save(@Param("resource") Resource resource);
    /**
     * 根据资源id获取资源详情
     * @param id
     * @return
     */
    Resource findById(Integer id);
    /**
     * 查询所有的资源信息
     * @return
     */
    List<Resource> findAll();
    /**
     * 删除资源
     * @param id
     */
    void delete(Integer id);
}
