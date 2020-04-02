package com.shunyi.autoparts.ui.common.vo;

/**
 * @description 产品属性值VO
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class AttributeValue implements AttributeIF {
    /** 自增ID*/
    private Long id;
    /** 属性值名称 */
    private String name;
    /** 颜色RGB */
    private String rgb;
    /** 类目 */
    private Category category;
    /** 属性名 */
    private AttributeName attributeName;
    /** 状态 */
    private String status;
    /** 排序 */
    private Integer sort;

    public AttributeValue() {}

    public AttributeValue(Long id, String name, String rgb, Category category, AttributeName attributeName, String status, Integer sort) {
        this.id = id;
        this.name = name;
        this.rgb = rgb;
        this.category = category;
        this.attributeName = attributeName;
        this.status = status;
        this.sort = sort;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
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

    @Override
    public String toString() {
        return name;
    }
}
