package com.shunyi.autoparts.ui.common.vo;

/**
 * @description 产品分类VO
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class Category {
    /** 自增ID */
    private Long id;
    /** 类目名称 */
    private String name;
    /** 父类目ID */
    private Long parentId;
    /** 是否父节点 */
    private Boolean parent;
    /** 所属门店 */
    private Store store;

    public Category() {}

    public Category(Long id, String name, Long parentId, Boolean parent, Store store) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.parent = parent;
        this.store = store;
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return name;
    }
}
