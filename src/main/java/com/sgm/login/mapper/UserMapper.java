package com.sgm.login.mapper;
import com.sgm.login.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserMapper {
    /**
     * 保存
     * @param user
     */
    void save(@Param("user") User user);
    /**
     * 更新用户
     * @param user
     */
    void update(@Param("user")User user);
    /**
     * 根据id查询用户信息
     * @param userId
     * @return
     */
    User findById(@Param("userId")Integer userId);
    /**
     * 根据用户名称查询用户信息
     * @param username
     * @return
     */
    User findByUserName(String username);
    /**
     * 分页查询
     * @return
     */
    List<User> findByPage();
    /**
     * 删除
     * @param userId
     */
    void delete(@Param("userId")Integer userId);
    /**
     * 查询记录数
     * @return
     */
    Integer count();
    /**
     * 根据用户名称查询条数
     * @return
     */
    Integer countByUserName(String username);
}
