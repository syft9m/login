package com.sgm.login.controller;

import com.sgm.login.exception.BusinessException;
import com.sgm.login.model.bo.ResourceForm;
import com.sgm.login.model.entity.LoginRecord;
import com.sgm.login.model.entity.Resource;
import com.sgm.login.model.entity.Role;
import com.sgm.login.model.entity.User;
import com.sgm.login.model.vo.ResourceVo;
import com.sgm.login.service.ResourceService;
import com.sgm.login.service.RoleService;
import com.sgm.login.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

import static com.sgm.login.util.BeanCopyUtil.copy;
import static com.sgm.login.util.BeanCopyUtil.copyList;
@RestController
@RequestMapping("/data")
public class DataController {
    protected Log log = LogFactory.getLog(getClass());
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;


    //*******************************************资源接口
// 新增/修改资源信息
    @PostMapping("/resource")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveResource(@RequestBody ResourceForm resourceForm) {
        Resource resource = copy(resourceForm,Resource.class);
        resourceService.save(resource);
    }
    @GetMapping("/resource/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResourceVo findResourceById(@PathVariable("id") @Min(value=1,message="id不能小于1")Integer id) {
        Resource resource = resourceService.findById(id);
        if(null == resource){
            throw new BusinessException("资源信息不存在");
        }
        log.info("resourceName:"+resource.getName());
        log.info("url:"+resource.getUrl());
        log.info("method:"+resource.getMethod());
        ResourceVo vo = copy(resource,ResourceVo.class);
        return vo;
    }
    @GetMapping("/resource")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ResourceVo> findAllResources() {
        List<Resource> resources = resourceService.findAll();
        List<ResourceVo> vos = copyList(resources,ResourceVo.class);
        return vos;
    }
    @DeleteMapping("/resource/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Boolean deleteResource(@PathVariable("id") @Min(value=1,message="id不能小于1") Integer id) {
        resourceService.delete(id);
        return true;
    }

    //*******************************************角色接口
    @PostMapping("/role")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveRole(@RequestBody Role role) {
        roleService.saveRole(role);
    }
    @GetMapping("/role/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Role findRoleById(@PathVariable("id") @Min(value=1,message="id不能小于1")Integer id) {
        Role role = roleService.findById(id);
        return role;
    }
    @GetMapping("/role")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Role> findAllRoles() {
        List<Role> roles = roleService.findAll();
        return roles;
    }
    @GetMapping("/roleCount")
    @ResponseStatus(value = HttpStatus.OK)
    public int countRole() {
        int count = roleService.countRole();
        return count;
    }
    @DeleteMapping("/deleteRole/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Boolean deleteRole(@PathVariable("id") @Min(value=1,message="id不能小于1") Integer id) {
        roleService.delete(id);
        return true;
    }
    @PostMapping("/role/grant/{roleId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void grantResource2Role(@PathVariable("roleId") Integer roleId,@RequestBody List<Integer> resourceIds ) {
        roleService.grantResource2Role(roleId,resourceIds);
    }
    @GetMapping("/roleSelect/{roleId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void findResourceByRoleId(@PathVariable("roleId") Integer roleId ) {
        roleService.findResourceByRoleId(roleId);
    }

    //*******************************************用户接口
    @GetMapping("/userSelect/{userName}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDetails findByUserName(@PathVariable("userName") String userName) {
        UserDetails user = userService.loadUserByUsername(userName);
        return user;
    }

    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveUser(@RequestBody User user) {

        userService.save(user);
    }

    @GetMapping("/userCount/{userName}")
    @ResponseStatus(value = HttpStatus.OK)
    public long countByUserName(@PathVariable("userName") String userName) {
        long count = userService.countByUserName(userName);
        return count;
    }
    @GetMapping("/findUser/{userName}")
    @ResponseStatus(value = HttpStatus.OK)
    public User findUserByUserName(@PathVariable("userName") String userName) {
        return userService.findByUserName(userName);
    }
    @GetMapping("/user/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public User findUserByUserName(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }
    @GetMapping("/user")
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> findByPage() {
        List<User> users = userService.findByPage();
        return users;
    }
    @GetMapping("/userCount")
    @ResponseStatus(value = HttpStatus.OK)
    public int count() {
        int count = userService.count();
        return count;
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Boolean deleteUser(@PathVariable("id") @Min(value=1,message="id不能小于1") Integer id) {
        userService.delete(id);
        return true;
    }
    @PostMapping("/user/grant/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void grantRole2User(@PathVariable("userId") Integer userId, @RequestBody List<Integer> roleIds) {
        userService.grantRole2User(userId,roleIds);
    }
    @GetMapping("/findRoleByUserId/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Role> findRoleByUserId(@PathVariable("userId") Integer userId) {
        return userService.findRoleByUserId(userId);
    }
    @GetMapping("/findResourceByUserId/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Resource> findResourceByUserId(@PathVariable("userId") Integer userId) {
        return userService.findResourceByUserId(userId);
    }
    @GetMapping("/checkLoginUser/{url}//{tokenId}/{method}")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean checkLoginUser(@PathVariable("url") String url,@PathVariable("tokenId") String tokenId,@PathVariable("method") String method) {
        return userService.checkLoginUser(url,tokenId,method);
    }
    @GetMapping("/saveLogin/{userName}/{tokenId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveLogin(@PathVariable("userName") String userName,@PathVariable("tokenId") String tokenId) {
        userService.saveLogin(userName,tokenId);
    }
    @GetMapping("/addLogin/{userName}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addLogin(@PathVariable("userName") String userName) {
        userService.addLogin(userName);
    }
    @GetMapping("/countLogin/{userName}/{tokenId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Integer countLogin(@PathVariable("userName") String userName,@PathVariable("tokenId") String tokenId) {
        return userService.countLogin(userName,tokenId);
    }
    @GetMapping("/findAllLoginInfo")
    @ResponseStatus(value = HttpStatus.OK)
    public List<LoginRecord> findAllLoginInfo() {
        return userService.findAllLoginInfo();
    }
}
