package com.sgm.login.model.entity;

import lombok.Data;

@Data
public class RoleResource {
    private Integer id;
    private Integer roleId;
    private Integer resourceId;
    public RoleResource(){};
    public RoleResource(Integer id,Integer roleId,Integer resourceId){
        this.id = id;
        this.roleId = roleId;
        this.resourceId = resourceId;
    };
}
