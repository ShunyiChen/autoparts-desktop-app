package com.shunyi.autoparts.ui.model;

import java.util.Date;

/** 产品属性名DTO */
public class AttributeName extends AttributeBase {
    /** 是否允许别名 */
    private Boolean aliasAllowed;
    /** 是否颜色属性 */
    private Boolean colorProperty;
    /** 是否枚举属性 */
    private Boolean enumProperty;
    /** 是否输入属性 */
    private Boolean inputProperty;
    /** 是否关键属性 */
    private Boolean keyProperty;
    /** 是否销售属性 */
    private Boolean saleProperty;
    /** 是否搜索字段 */
    private Boolean searchField;
    /** 是否必须 */
    private Boolean required;
    /** 是否多选 */
    private Boolean multiple;

    public AttributeName() {}

    public AttributeName(String name) {
        this.name = name;
    }

    public AttributeName(String name, Category category, Boolean aliasAllowed, Boolean colorProperty, Boolean enumProperty, Boolean inputProperty, Boolean keyProperty, Boolean saleProperty, Boolean searchField, Boolean required, Boolean multiple, String status, Integer sort) {
        this.name = name;
        this.category = category;
        this.aliasAllowed = aliasAllowed;
        this.colorProperty = colorProperty;
        this.enumProperty = enumProperty;
        this.inputProperty = inputProperty;
        this.keyProperty = keyProperty;
        this.saleProperty = saleProperty;
        this.searchField = searchField;
        this.required = required;
        this.multiple = multiple;
        this.status = status;
        this.sort = sort;
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

    public Boolean isAliasAllowed() {
        return aliasAllowed;
    }

    public void setAliasAllowed(Boolean aliasAllowed) {
        this.aliasAllowed = aliasAllowed;
    }

    public Boolean isColorProperty() {
        return colorProperty;
    }

    public void setColorProperty(Boolean colorProperty) {
        this.colorProperty = colorProperty;
    }

    public Boolean isEnumProperty() {
        return enumProperty;
    }

    public void setEnumProperty(Boolean enumProperty) {
        this.enumProperty = enumProperty;
    }

    public Boolean isInputProperty() {
        return inputProperty;
    }

    public void setInputProperty(Boolean inputProperty) {
        this.inputProperty = inputProperty;
    }

    public Boolean isKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(Boolean keyProperty) {
        this.keyProperty = keyProperty;
    }

    public Boolean isSaleProperty() {
        return saleProperty;
    }

    public void setSaleProperty(Boolean saleProperty) {
        this.saleProperty = saleProperty;
    }

    public Boolean isSearchField() {
        return searchField;
    }

    public void setSearchField(Boolean searchField) {
        this.searchField = searchField;
    }

    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
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
