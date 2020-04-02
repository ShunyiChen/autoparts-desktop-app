package com.shunyi.autoparts.ui.common.vo;

import java.util.HashSet;
import java.util.Set;

/**
 * @description 权限VO
 * @author Shunyi Chen
 * @date 2020/3/23
 */
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
    private Integer code;

    public Permission() {}

    public Permission(Long id, String name, String description, Set<RolePermissionMapping> rolePermissionMappingSet, Integer code) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rolePermissionMappingSet = rolePermissionMappingSet;
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
