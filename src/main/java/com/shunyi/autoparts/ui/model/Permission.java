package com.shunyi.autoparts.ui.model;

import java.util.HashSet;
import java.util.Set;

/** 权限 */
public class Permission {
    /** 权限ID */
    private Long id;
    /** 权限名称 */
    private String name;
    /** 描述 */
    private String description;
    /** 角色与权限映射关系 */
    protected Set<RolePermissionMapping> rolePermissionMappingSet = new HashSet<>();
    /** 权限编码 */
    private String code;

    public Permission() {}

    public Permission(String name, String description, String code) {
        this.name = name;
        this.description = description;
        this.code = code;
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

    public Set<RolePermissionMapping> getRolePermissionMappingSet() {
        return rolePermissionMappingSet;
    }

    public void setRolePermissionMappingSet(Set<RolePermissionMapping> rolePermissionMappingSet) {
        this.rolePermissionMappingSet = rolePermissionMappingSet;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rolePermissionMappingSet=" + rolePermissionMappingSet +
                ", code='" + code + '\'' +
                '}';
    }
}
