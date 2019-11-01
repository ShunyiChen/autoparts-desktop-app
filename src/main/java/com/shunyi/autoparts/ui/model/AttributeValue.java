package com.shunyi.autoparts.ui.model;

import java.util.Date;

/** 产品属性值DTO */
public class AttributeValue extends AttributeBase{
    /** 属性名 */
    private AttributeName attributeName;
    private String rgb;

    public AttributeValue() {}

    public AttributeValue(String name, String rgb, Category category, AttributeName attributeName, String status, Integer sort) {
        this.name = name;
        this.rgb = rgb;
        this.category = category;
        this.attributeName = attributeName;
        this.status = status;
        this.sort = sort;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
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

    public AttributeName getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(AttributeName attributeName) {
        this.attributeName = attributeName;
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
