package com.shunyi.autoparts.ui.common.vo;

import java.util.Date;

/**
 * @description 产品属性名
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class AttributeName implements AttributeIF {
    /** 自增ID */
    private Long id;
    /** 属性名 */
    private String name;
    /** 类目 */
    private Category category;
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
    /** 状态 */
    private String status;
    /** 排序 */
    private Integer sort;
    /** 创建时间 */
    private Date dateCreated;
    /** 创建者 */
    private String creator;
    /** 更新时间 */
    private Date dateUpdated;
    /** 更新者 */
    private String updater;
    /** 更新次数 */
    private Integer updatedCount;
    /** 删除时间 */
    private Date dateDeleted;
    /** 删除标记 */
    private Boolean deleteFlag;
    /** 删除者 */
    private String deleter;

    public AttributeName() {}

    public AttributeName(Long id, String name, Category category, Boolean aliasAllowed, Boolean colorProperty, Boolean enumProperty, Boolean inputProperty, Boolean keyProperty, Boolean saleProperty, Boolean searchField, Boolean required, Boolean multiple, String status, Integer sort, Date dateCreated, String creator, Date dateUpdated, String updater, Integer updatedCount, Date dateDeleted, Boolean deleteFlag, String deleter) {
        this.id = id;
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
        this.dateCreated = dateCreated;
        this.creator = creator;
        this.dateUpdated = dateUpdated;
        this.updater = updater;
        this.updatedCount = updatedCount;
        this.dateDeleted = dateDeleted;
        this.deleteFlag = deleteFlag;
        this.deleter = deleter;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getAliasAllowed() {
        return aliasAllowed;
    }

    public void setAliasAllowed(Boolean aliasAllowed) {
        this.aliasAllowed = aliasAllowed;
    }

    public Boolean getColorProperty() {
        return colorProperty;
    }

    public void setColorProperty(Boolean colorProperty) {
        this.colorProperty = colorProperty;
    }

    public Boolean getEnumProperty() {
        return enumProperty;
    }

    public void setEnumProperty(Boolean enumProperty) {
        this.enumProperty = enumProperty;
    }

    public Boolean getInputProperty() {
        return inputProperty;
    }

    public void setInputProperty(Boolean inputProperty) {
        this.inputProperty = inputProperty;
    }

    public Boolean getKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(Boolean keyProperty) {
        this.keyProperty = keyProperty;
    }

    public Boolean getSaleProperty() {
        return saleProperty;
    }

    public void setSaleProperty(Boolean saleProperty) {
        this.saleProperty = saleProperty;
    }

    public Boolean getSearchField() {
        return searchField;
    }

    public void setSearchField(Boolean searchField) {
        this.searchField = searchField;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getMultiple() {
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Integer getUpdatedCount() {
        return updatedCount;
    }

    public void setUpdatedCount(Integer updatedCount) {
        this.updatedCount = updatedCount;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleter() {
        return deleter;
    }

    public void setDeleter(String deleter) {
        this.deleter = deleter;
    }

    @Override
    public String toString() {
        return name;
    }
}
