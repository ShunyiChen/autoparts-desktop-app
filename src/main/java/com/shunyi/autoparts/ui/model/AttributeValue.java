package com.shunyi.autoparts.ui.model;

import java.util.Date;

/** 产品属性值DTO */
public class AttributeValue {
    /** ID */
    private Long id;
    /** 属性值名称 */
    private String name;
    /** 产品类目 */
    private Category category;
    /** 属性名ID */
    private Long attributeNameId;
    /** 状态 */
    private String status;
    /** 排序 */
    private Integer sort;
    /** 创建时间 */
    private Date dateCreated;

    public AttributeValue() {}

    public AttributeValue(String name, Category category, Long attributeNameId, String status, Integer sort, Date dateCreated) {
        this.name = name;
        this.category = category;
        this.attributeNameId = attributeNameId;
        this.status = status;
        this.sort = sort;
        this.dateCreated = dateCreated;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getAttributeNameId() {
        return attributeNameId;
    }

    public void setAttributeNameId(Long attributeNameId) {
        this.attributeNameId = attributeNameId;
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

    @Override
    public String toString() {
        return name;
    }
}
