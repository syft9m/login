package com.sgm.login.mapper;

import com.sgm.login.model.entity.Resource;
import com.sgm.login.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RoleMapper {
    /**
     * 保存角色信息
     * @param role
     */
    void saveRole(@Param("role") Role role);
    /**
     * 更新角色信息
     * @param role
     */
    void updateRole(@Param("role")Role role);
    /**
     * 根据角色ID获取该角色详情
     * @param id
     * @return
     */
    Role findById(@Param("id")Integer id);
    /**
     * 查询角色信息
     * @return
     */
    List<Role> findAll();
    /**
     * 查询角色条数
     * @return
     */
    Integer count();
    /**
     * 删除角色
     * @param id
     * @return
     */
    void delete(@Param("id")Integer id);
    /**
     * 根据角色名称查询角色条数
     * @param name
     * @return
     */
    Integer countByName(@Param("name")String name);
}
