package com.sgm.login.mapper;

import com.sgm.login.model.entity.RoleResource;
import com.sgm.login.model.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserRoleMapper {
    /**
     * 根据用户id删除该用户的所有角色
     * @param roleId
     * @return
     */
    void deleteRoleByUserId(@Param("roleId")Integer roleId);
    /**
     * 根据用户id获取该用户下的所有角色
     * @param userId
     * @return
     */
    List<Integer> findRoleByUserId(@Param("userId")Integer userId);
    /**
     * 给用户授权角色
     * @param userRole
     */
    void saveUserRole(@Param("userRole") UserRole userRole);
}
