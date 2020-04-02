package com.shunyi.autoparts.ui.common.vo;

/**
 * @description 产品基本属性VO
 * @author Shunyi Chen
 * @date 2020/4/2
 */
public class BasicAttributes {
    /** 自增ID */
    private Long id;
    /** 产品 */
    private Product product;
    /** 基本属性名ID */
    private Long attributeNameId;
    /** 基本属性值ID */
    private AttributeValue attributeValue;
    /** 是否SKU */
    private Boolean isSKU;
    /** SKU ID */
    private Long skuId;

    public BasicAttributes() {}

    public BasicAttributes(Long id, Product product, Long attributeNameId, AttributeValue attributeValue, Boolean isSKU, Long skuId) {
        this.id = id;
        this.product = product;
        this.attributeNameId = attributeNameId;
        this.attributeValue = attributeValue;
        this.isSKU = isSKU;
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

    public AttributeValue getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(AttributeValue attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Boolean getSKU() {
        return isSKU;
    }

    public void setSKU(Boolean SKU) {
        isSKU = SKU;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Override
    public String toString() {
        return "BasicAttributes{" +
                "id=" + id +
                ", product=" + product +
                ", attributeNameId=" + attributeNameId +
                ", attributeValue=" + attributeValue +
                ", isSKU=" + isSKU +
                ", skuId=" + skuId +
                '}';
    }
}
