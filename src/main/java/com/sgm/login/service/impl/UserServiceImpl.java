package com.sgm.login.service.impl;

import com.sgm.login.exception.BusinessException;
import com.sgm.login.mapper.LoginMapper;
import com.sgm.login.mapper.LoginRecordMapper;
import com.sgm.login.mapper.UserMapper;
import com.sgm.login.mapper.UserRoleMapper;
import com.sgm.login.model.entity.*;
import com.sgm.login.service.RoleService;
import com.sgm.login.service.UserService;
import com.sgm.login.util.MyStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
//    private static final Log log = LogFactory.getLog(MyLog4j.class);
    protected Log log = LogFactory.getLog(getClass());
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private LoginRecordMapper loginRecordMapper;
    /**
     * 只做用户名和密码登录认证。
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("userName:"+userName);
        if(MyStringUtil.isEmpty(userName)){
            throw new BusinessException("用户名不可为空");
        }
        User user = userMapper.findByUserName(userName);
        if(null == user){
            throw new BusinessException("用户信息不存在");
        }
        return new org.springframework.security.core.userdetails.User(userName, passwordEncoder.encode(user.getPassword()), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
    @Override
    public void save(User user) {
        if(null == user.getId()){
            //新建
            //验证名称是否重复。
            if(userMapper.countByUserName(user.getUserName()) > 0){
                throw new BusinessException("用户名称重复");
            }
            userMapper.save(user);
        }else{
            //修改
            User oldUser = userMapper.findById(user.getId());
            if(null == oldUser){
                throw new BusinessException("用户信息不存在");
            }
            //当修改的名称和之前的名称不一样的时候，需要验证名称是否重复。
            if(!oldUser.getUserName().equals(user.getUserName())){
                if(userMapper.countByUserName(user.getUserName()) > 0){
                    throw new BusinessException("用户名称重复");
                }
            }
            userMapper.update(user);
        }
        log.info("id:"+user.getId());
//        return user.getId();
    }
    @Override
    public long countByUserName(String userName) {
        return userMapper.countByUserName(userName);
    }
    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }
    @Override
    public List<User> findByPage() {
        return userMapper.findByPage();
    }
    @Override
    public int count() {
        return userMapper.count();
    }
    @Override
    public void delete(Integer userId){
        userMapper.delete(userId);
    }
    @Override
    public void grantRole2User(Integer userId, List<Integer> roleIds) {
        //先删除该用户之前的角色
        userRoleMapper.deleteRoleByUserId(userId);
        //保存新角色
//        List<UserRole> UserRoles = new List<UserRole>;
        for (Integer roleId:roleIds
             ) {
            log.info("userId:"+userId);
            log.info("roleId:"+roleId);
            UserRole userRole = new UserRole(1,userId,roleId);
//            UserRoles.add(userRole);
            System.out.println(userRole);
            userRoleMapper.saveUserRole(userRole);
        }
    }
    @Override
    public List<Role> findRoleByUserId(Integer userId) {
        List<Integer> roleIds = userRoleMapper.findRoleByUserId(userId);
        List<Role> roles = new ArrayList<>();
        for(Integer roleId : roleIds){
            Role role = roleService.findById(roleId);
            roles.add(role);
        }
        return roles;
    }
    @Override
    public List<Resource> findResourceByUserId(Integer userId) {
        List<Integer> roleIds = userRoleMapper.findRoleByUserId(userId);
        List<Resource> resources = new ArrayList<>();
        for(Integer roleId : roleIds){
            List<Resource> list = roleService.findResourceByRoleId(roleId);
            for(Resource resource : list){
                if(!resources.contains(resource)){
                    resources.add(resource);
                }
            }
        }
        return resources;
    }
    @Override
    public boolean checkLoginUser(String url, String tokenId,String method) {
        log.info("请求地址："+url);
        log.info("请求方式："+method);
        //根据tokenId获取用户名称
        String userName = loginMapper.findUserNameByTokenId(tokenId);
        log.info("用户名："+userName);
        if(null == userName || "".equals(userName)){
            throw new BusinessException("无效的tokenId");
        }
        //如果是超级管理员，不需要验证权限。（管理员帐号为，admin 123456）
        if("admin".equals(userName)){
            return true;
        }
        //根据用户名称获取该用户信息
        User user = userMapper.findByUserName(userName);
        if(null == user){
            throw new BusinessException("用户信息不存在");
        }
        //根据用户信息查询该用户所拥有的资源信息
        List<Resource> resources = findResourceByUserId(user.getId());
        boolean flag = false;
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        //比对url是否在所拥有的资源url中
        for(Resource resource : resources){
            log.info("数据库url："+resource.getUrl());
            if(antPathMatcher.match(resource.getUrl(),url) && method.equals(resource.getMethod())){
                flag = true;
                break;
            }
        }

        if(flag){
            return true;
        }else{
            throw new BusinessException("无权访问");
        }
    }
    @Override
    public void saveLogin(String userName, String tokenId) {
        loginMapper.saveLogin(userName,tokenId);
    }
    @Override
    public void addLogin(String userName) {
        Integer count = loginRecordMapper.countLoginByUserName(userName);
        if(null == count){
            loginRecordMapper.firstLogin(userName);
        }else{
            loginRecordMapper.addLogin(userName);
        }
    }
    @Override
    public Integer countLogin(String userName, String tokenId) {
        return loginMapper.countLogin(userName,tokenId);
    }
    @Override
    public List<LoginRecord> findAllLoginInfo() {
        return loginRecordMapper.findAll();
    }
}
