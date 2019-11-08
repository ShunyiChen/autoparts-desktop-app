package com.shunyi.autoparts.ui.model;

import java.util.Date;

/** 产品属性DTO */
public class Attribute {
    /** ID */
    private Long id;
    /** 产品 */
    private Product product;
    /** 属性名ID */
    private Long attributeNameId;
    /** 属性值ID */
    private Long attributeValueId;
    /** 是否SKU */
    private Boolean sku;
    /** SKU ID */
    private Long skuId;
    /** 创建时间 */
    private Date dateCreated;

    public Attribute() {}

    public Attribute(Product product, Long attributeNameId, Long attributeValueId, Boolean sku, Long skuId) {
        this.product = product;
        this.attributeNameId = attributeNameId;
        this.attributeValueId = attributeValueId;
        this.sku = sku;
        this.skuId = skuId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getAttributeNameId() {
        return attributeNameId;
    }

    public void setAttributeNameId(Long attributeNameId) {
        this.attributeNameId = attributeNameId;
    }

    public Long getAttributeValueId() {
        return attributeValueId;
    }

    public void setAttributeValueId(Long attributeValueId) {
        this.attributeValueId = attributeValueId;
    }

    public Boolean getSku() {
        return sku;
    }

    public void setSku(Boolean sku) {
        this.sku = sku;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", product=" + product +
                ", attributeNameId=" + attributeNameId +
                ", attributeValueId=" + attributeValueId +
                ", sku=" + sku +
                ", skuId=" + skuId +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
