package com.shunyi.autoparts.ui.common.vo;

import java.util.HashSet;
import java.util.Set;

/**
 * @description 角色VO
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class Role {
    /** 角色ID */
    private Long id;
    /** 角色名称 */
    private String name;
    /** 描述 */
    private String description;
    /** 用户与角色映射关系 */
    private Set<UserRoleMapping> userRoleMappingSet = new HashSet<>();
    /** 角色与权限映射关系 */
    private Set<RolePermissionMapping> rolePermissionMappingSet = new HashSet<>();

    public Role() {}

    public Role(Long id, String name, String description, Set<UserRoleMapping> userRoleMappingSet, Set<RolePermissionMapping> rolePermissionMappingSet) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userRoleMappingSet = userRoleMappingSet;
        this.rolePermissionMappingSet = rolePermissionMappingSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserRoleMapping> getUserRoleMappingSet() {
        return userRoleMappingSet;
    }

    public void setUserRoleMappingSet(Set<UserRoleMapping> userRoleMappingSet) {
        this.userRoleMappingSet = userRoleMappingSet;
    }

    public Set<RolePermissionMapping> getRolePermissionMappingSet() {
        return rolePermissionMappingSet;
    }

    public void setRolePermissionMappingSet(Set<RolePermissionMapping> rolePermissionMappingSet) {
        this.rolePermissionMappingSet = rolePermissionMappingSet;
    }
}
