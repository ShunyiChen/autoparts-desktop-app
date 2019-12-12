package com.shunyi.autoparts.ui.model;

import java.util.HashSet;
import java.util.Set;

/** 角色 */
public class Role {
    /** 角色ID */
    private long id;
    /** 角色名称 */
    private String name;
    /** 描述*/
    private String description;
    /** 用户和角色映射关系 */
    protected Set<UserRoleMapping> userRoleMappingSet = new HashSet<>();
    /** 角色和权限映射关系 */
    protected Set<RolePermissionMapping> rolePermissionMappingSet = new HashSet<>();

    public Role() {
    }

    public Role(String name, String description, Set<UserRoleMapping> userRoleMappingSet, Set<RolePermissionMapping> rolePermissionMappingSet) {
        this.name = name;
        this.description = description;
        this.userRoleMappingSet = userRoleMappingSet;
        this.rolePermissionMappingSet = rolePermissionMappingSet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userRoleMappingSet=" + userRoleMappingSet +
                ", rolePermissionMappingSet=" + rolePermissionMappingSet +
                '}';
    }
}
