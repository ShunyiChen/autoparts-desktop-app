package com.shunyi.autoparts.ui.model;

/** 仓库DTO */
public class Warehouse {
    /** ID */
    private Long id;
    /** 仓库编码 */
    private String code;
    /** 仓库名称 */
    private String name;
    /** 父节点ID */
    private Long parentId;
    /** 是否是父节点 */
    private Boolean parent;

    public Warehouse() {}

    public Warehouse(String code, String name, Long parentId, Boolean parent) {
        this.code = code;
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

    public Boolean isParent() {
        return parent;
    }

    public void setParent(Boolean parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return name;
    }
}
