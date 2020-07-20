package com.sgm.login.service.impl;

import com.sgm.login.mapper.ResourceMapper;
import com.sgm.login.mapper.RoleMapper;
import com.sgm.login.mapper.RoleResourceMapper;
import com.sgm.login.model.entity.Resource;
import com.sgm.login.model.entity.Role;
import com.sgm.login.model.entity.RoleResource;
import com.sgm.login.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public void saveRole(Role role) {
        if(null == role.getId()) {
            roleMapper.saveRole(role);
        } else {
            roleMapper.updateRole(role);
        }
    }

    @Override
    public Role findById(Integer id) {
        return roleMapper.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public int countRole() {
        return roleMapper.count();
    }

    @Override
    public void delete(Integer id) {
        roleMapper.delete(id);
    }

    @Override
    public void grantResource2Role(Integer roleId, List<Integer> resourceIds) {
        for (Integer resourceId:resourceIds
             ) {
            RoleResource roleResource = new RoleResource(1,roleId,resourceId);
            roleResourceMapper.saveRoleResource(roleResource);
        }
    }

    @Override
    public List<Resource> findResourceByRoleId(Integer roleId) {
        List<Resource> resource = new ArrayList<>();
        List<Integer> resourceIds = roleResourceMapper.findResourceById(roleId);
        for (Integer resourceId:resourceIds
             ) {
            resource.add(resourceMapper.selectById(resourceId));
        }
        return resource;
    }
}
