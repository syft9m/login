package com.sgm.login.service.impl;

import com.sgm.login.mapper.ResourceMapper;
import com.sgm.login.model.entity.Resource;
import com.sgm.login.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public void save(Resource resource) {
        if(null == resource.getId()) {
            resourceMapper.saveResource(resource);
        } else {
            resourceMapper.updateResource(resource);
        }
    }

    @Override
    public Resource findById(Integer id) {
        return resourceMapper.selectById(id);
    }

    @Override
    public List<Resource> findAll() {
        return resourceMapper.findAll();
    }

    @Override
    public void delete(Integer id) {
        resourceMapper.delete(id);
    }
}
