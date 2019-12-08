package com.shunyi.autoparts.ui.model;

/** 商家基类 */
public abstract class Merchant {
    /** ID */
    protected Long id = 0L;
    /** 商家名称 */
    protected String name;
    /** 父节点ID */
    private long parentId;
    /** 父节点 */
    private boolean parent;


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
}
