package com.sgm.login.service;

import com.sgm.login.model.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    /**
     * 保存用户信息
     * @param user
     * @return
     */
    void save(@Param("user") User user);
    /**
     * 根据用户名称查询该名称对应的用户条数
     * @param userName
     * @return
     */
    long countByUserName(String userName);
    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    User findByUserName(String userName);
    /**
     * 根据用户
     * @param id
     * @return
     */
    User findById(Integer id);
    /**
     * 查询用户列表
     * @return
     */
    List<User> findByPage();
    /**
     * 查询用户条数
     */
    int count();
    /**
     * 删除用户
     * @param userId
     */
    void delete(Integer userId);
    /**
     * 给用户授权角色
     * @param userId
     * @param roleIds
     */
    void grantRole2User(Integer userId, List<Integer> roleIds);
    /**
     * 根据用户id查询该用户所拥有的角色信息
     * @param userId
     */
    List<Role> findRoleByUserId(Integer userId);
    /**
     * 根据用户id查询该用户所有的资源信息
     * @param userId
     */
    List<Resource> findResourceByUserId(Integer userId);
    /**
     * 根据用户访问的url和tokenId判断该用户是否拥有访问该url的资格
     * @param url
     * @param tokenId
     * @param method
     */
    boolean  checkLoginUser(String url, String tokenId,String method);
    /**
     * 保存用户和tokenId
     * @param userName
     * @param tokenId
     */
    void saveLogin(String userName, String tokenId);
    /**
     * 保存用户和tokenId
     * @param userName
     */
    void addLogin(String userName);
    /**
     * 保存用户和tokenId
     * @param userName
     * @param tokenId
     */
    Integer countLogin(String userName, String tokenId);
    /**
     * 保存用户和tokenId
     */
    List<LoginRecord> findAllLoginInfo();

    UserDetails loadUserByUsername(String userName);
}
