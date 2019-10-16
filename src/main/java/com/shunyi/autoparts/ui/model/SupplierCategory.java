package com.shunyi.autoparts.ui.model;

/** 供应商分类DTO */
public class SupplierCategory {

    private long id;

    private String name;

    private long parentId;

    private boolean parent;

    public SupplierCategory() {}

    public SupplierCategory(String name, long parentId, boolean parent) {
        this.name = name;
        this.parentId = parentId;
        this.parent = parent;
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

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return name;
    }
}
