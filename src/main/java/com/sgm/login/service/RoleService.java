package com.sgm.login.service;

import com.sgm.login.model.entity.Resource;
import com.sgm.login.model.entity.Role;
import com.sgm.login.model.entity.RoleResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    /**
     * 保存角色信息
     * @param role
     * @return
     */
    void saveRole(@Param("role") Role role);
    /**
     * 根据资源id获取角色详情
     * @param id
     * @return
     */
    Role findById(Integer id);
    /**
     * 查询所有的角色信息
     * @return
     */
    List<Role> findAll();
    /**
     * 查询角色条数
     * @return
     */
    int countRole();
    /**
     * 删除角色
     * @param id
     */
    void delete(Integer id);
    /**
     * 将资源授权给角色
     * @param roleId
     * @param resourceIds
     */
    void grantResource2Role(Integer roleId, List<Integer> resourceIds);
    /**
     * 根据roleId获取该角色对应的所有资源
     * @param roleId
     */
    List<Resource> findResourceByRoleId(Integer roleId);



}
