package com.shunyi.autoparts.ui.model;

import java.util.HashSet;
import java.util.Set;

/** 权限 */
public class Permission {

    private long id;

    private String name;

    private String description;

    protected Set<RolePermissionMapping> userRoleUserRolePermissionSet = new HashSet<>();

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

    public Set<RolePermissionMapping> getUserRoleUserRolePermissionSet() {
        return userRoleUserRolePermissionSet;
    }

    public void setUserRoleUserRolePermissionSet(Set<RolePermissionMapping> userRoleUserRolePermissionSet) {
        this.userRoleUserRolePermissionSet = userRoleUserRolePermissionSet;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userRoleUserRolePermissionSet=" + userRoleUserRolePermissionSet +
                '}';
    }
}
