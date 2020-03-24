package com.shunyi.autoparts.ui.common.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 店铺
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class Store {
    /** 自增ID */
    private Long id;
    /** 店铺编码 */
    private String code;
    /** 名称 */
    private String name;
    /** 父类目ID */
    private Long parentId;
    /** 是否父节点 */
    private Boolean parent;
    /** 用户店铺映射关系 */
    protected Set<UserStoreMapping> userStoreMappingSet = new HashSet<>();
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

    public Store() {}

    public Store(Long id, String code, String name, Long parentId, Boolean parent, Set<UserStoreMapping> userStoreMappingSet, Date dateCreated, String creator, Date dateUpdated, String updater, Integer updatedCount, Date dateDeleted, Boolean deleteFlag, String deleter) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
        this.parent = parent;
        this.userStoreMappingSet = userStoreMappingSet;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getParent() {
        return parent;
    }

    public void setParent(Boolean parent) {
        this.parent = parent;
    }

    public Set<UserStoreMapping> getUserStoreMappingSet() {
        return userStoreMappingSet;
    }

    public void setUserStoreMappingSet(Set<UserStoreMapping> userStoreMappingSet) {
        this.userStoreMappingSet = userStoreMappingSet;
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
        if(updatedCount == null) {
            return 0;
        }
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
