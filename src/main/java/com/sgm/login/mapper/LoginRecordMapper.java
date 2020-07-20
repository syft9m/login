package com.sgm.login.mapper;
import com.sgm.login.model.entity.LoginRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginRecordMapper {
    /**
     * 根据用户名称查询登录次数
     * @param userName
     * @return
     */
    Integer countLoginByUserName(String userName);
    /**
     * 首次登录插入登录记录
     * @param userName
     */
    void firstLogin(String userName);
    /**
     * 登录次数+1
     * @param userName
     */
    void addLogin(String userName);
    /**
     * 查询所有登录记录
     * @return
     */
    List<LoginRecord> findAll();
}
