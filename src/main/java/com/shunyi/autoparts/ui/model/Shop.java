package com.shunyi.autoparts.ui.model;

import java.util.HashSet;
import java.util.Set;

/** 店铺 */
public class Shop {
    /** ID */
    private Long id;
    /** 名称 */
    private String name;
    /** 父类目ID */
    private Long parentId;
    /** 是否父节点 */
    private Boolean parent;
    /** 用户店铺映射关系 */
    protected Set<UserShopMapping> userShopMappingSet = new HashSet<>();

    public Shop() {}

    public Shop(String name, Long parentId, Boolean parent, Set<UserShopMapping> userShopMappingSet) {
        this.name = name;
        this.parentId = parentId;
        this.parent = parent;
        this.userShopMappingSet = userShopMappingSet;
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

    public Set<UserShopMapping> getUserShopMappingSet() {
        return userShopMappingSet;
    }

    public void setUserShopMappingSet(Set<UserShopMapping> userShopMappingSet) {
        this.userShopMappingSet = userShopMappingSet;
    }

    @Override
    public String toString() {
        return name;
    }
}
