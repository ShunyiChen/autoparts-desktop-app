package com.shunyi.autoparts.ui.model;

import java.util.Date;

/** 属性基础类 */
public abstract class AttributeBase {
    /** ID */
    protected Long id = 0L;
    /** 属性值名称 */
    protected String name;
    /** 产品类目 */
    protected Category category;
    /** 状态 */
    protected String status;
    /** 排序 */
    protected Integer sort;
    /** 创建时间 */
    protected Date dateCreated;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
