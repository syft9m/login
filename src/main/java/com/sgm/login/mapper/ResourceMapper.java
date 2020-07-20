package com.sgm.login.mapper;

import com.sgm.login.model.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ResourceMapper {
    /**
     * 保存资源信息
     * @param resource
     */
    void saveResource(@Param("resource")Resource resource);
    /**
     * 更新资源信息
     * @param resource
     */
    void updateResource(@Param("resource")Resource resource);
    /**
     * 根据资源id获取资源详情
     * @param id
     * @return
     */
    Resource selectById(@Param("id")Integer id);
    /**
     * 查询所有的资源信息
     * @return
     */
    List<Resource> findAll();
    /**
     * 查询总条数
     * @return
     */
    Integer count();
    /**
     * 删除资源
     * @param id
     * @return
     */
    void delete(@Param("id")Integer id);
    /**
     * 根据url和method 获取条数
     * @param url
     * @param method
     * @return
     */
    Integer countByUrl(@Param("url")String url,@Param("method")String method);
}
