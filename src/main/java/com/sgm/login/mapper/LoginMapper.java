package com.sgm.login.mapper;

import com.sgm.login.model.entity.LoginRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginMapper {
    /**
     * 保存用户和tokenId的关系
     * @param userName
     * @param tokenId
     */
    void saveLogin(@Param("userName")String userName, @Param("tokenId") String tokenId);
    /**
     * 根据用户名称和tokenId查询记录条数
     * @param userName
     * @param tokenId
     * @return
     */
    Integer countLogin(@Param("userName")String userName,@Param("tokenId") String tokenId);
    /**
     * 根据token获取用户名称
     * @param tokenId
     * @return
     */
    String findUserNameByTokenId(String tokenId);

}