package com.shunyi.autoparts.ui.model;

import java.util.HashSet;
import java.util.Set;

/** 角色 */
public class Role {
    /** ID */
    private long id;
    /** 名称 */
    private String name;
    /** 描述*/
    private String description;

    protected Set<UserRoleMapping> userUserRoleSet = new HashSet<>();

    protected Set<RolePermissionMapping> userRoleUserRolePermissionSet = new HashSet<>();

    public Role() {
    }

    public Role(long id, String name, String description, Set<UserRoleMapping> userUserRoleSet, Set<RolePermissionMapping> userRoleUserRolePermissionSet) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userUserRoleSet = userUserRoleSet;
        this.userRoleUserRolePermissionSet = userRoleUserRolePermissionSet;
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

    public Set<UserRoleMapping> getUserUserRoleSet() {
        return userUserRoleSet;
    }

    public void setUserUserRoleSet(Set<UserRoleMapping> userUserRoleSet) {
        this.userUserRoleSet = userUserRoleSet;
    }

    public Set<RolePermissionMapping> getUserRoleUserRolePermissionSet() {
        return userRoleUserRolePermissionSet;
    }

    public void setUserRoleUserRolePermissionSet(Set<RolePermissionMapping> userRoleUserRolePermissionSet) {
        this.userRoleUserRolePermissionSet = userRoleUserRolePermissionSet;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userUserRoleSet=" + userUserRoleSet +
                ", userRoleUserRolePermissionSet=" + userRoleUserRolePermissionSet +
                '}';
    }
}
