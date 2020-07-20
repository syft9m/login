package com.sgm.login.model.entity;

import lombok.Data;

@Data
public class UserRole {
    private Integer id;
    private Integer userId;
    private Integer roleId;
    public UserRole() {}
    public UserRole(Integer id, Integer userId,Integer roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

}