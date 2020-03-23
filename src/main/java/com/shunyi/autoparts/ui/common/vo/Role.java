package com.shunyi.autoparts.ui.common.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 角色
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
    /** 创建时间 */
    private Date dateCreated;
    /** 创建者 */
    private String creator;
    /** 更新时间 */
    private Date dateUpdated;
    /** 更新者 */
    private String updater;
    /** 更新次数 */
    private Integer updatedCount;
    /** 删除时间 */
    private Date dateDeleted;
    /** 删除标记 */
    private Boolean deleteFlag;
    /** 删除者 */
    private String deleter;

    public Role() {}

    public Role(Long id, String name, String description, Set<UserRoleMapping> userRoleMappingSet, Set<RolePermissionMapping> rolePermissionMappingSet, Date dateCreated, String creator, Date dateUpdated, String updater, Integer updatedCount, Date dateDeleted, Boolean deleteFlag, String deleter) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userRoleMappingSet = userRoleMappingSet;
        this.rolePermissionMappingSet = rolePermissionMappingSet;
        this.dateCreated = dateCreated;
        this.creator = creator;
        this.dateUpdated = dateUpdated;
        this.updater = updater;
        this.updatedCount = updatedCount;
        this.dateDeleted = dateDeleted;
        this.deleteFlag = deleteFlag;
        this.deleter = deleter;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Integer getUpdatedCount() {
        return updatedCount;
    }

    public void setUpdatedCount(Integer updatedCount) {
        this.updatedCount = updatedCount;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleter() {
        return deleter;
    }

    public void setDeleter(String deleter) {
        this.deleter = deleter;
    }

    @Override
    public String toString() {
        return name;
    }
}
