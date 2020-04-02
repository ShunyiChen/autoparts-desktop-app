package com.shunyi.autoparts.ui.common.vo;

import java.util.HashSet;
import java.util.Set;

/**
 * @description 店铺VO
 * @author Shunyi Chen
 * @date 2020/4/2
 */
public class Store {
    /** ID */
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

    public Store() {}

    public Store(Long id, String code, String name, Long parentId, Boolean parent, Set<UserStoreMapping> userStoreMappingSet) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.parentId = parentId;
        this.parent = parent;
        this.userStoreMappingSet = userStoreMappingSet;
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
}
