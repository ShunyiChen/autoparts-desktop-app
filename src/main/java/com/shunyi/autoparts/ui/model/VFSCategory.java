package com.shunyi.autoparts.ui.model;

/** VFS分类 */
public class VFSCategory {
    /**  ID */
    private Long id;
    /** 名称 */
    private String name;
    /** 父节点ID */
    private long parentId;
    /** 父节点 */
    private boolean parent;

    public VFSCategory() {}

    public VFSCategory(String name, long parentId, boolean parent) {
        this.name = name;
        this.parentId = parentId;
        this.parent = parent;
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
