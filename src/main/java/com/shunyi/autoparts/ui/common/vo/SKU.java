package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 产品SKU实体类
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class SKU {
    /** 自增ID */
    private Long id;
    /** 产品 */
    private Product product;
    /** SKU编码 */
    private String skuCode;
    /** SKU名称 */
    private String skuName;
    /** 规格 */
    private String specification;
    /** 单位 */
    private String unit;
    /** 库存数量 */
    private Integer quantity;
    /** 销售价 */
    private BigDecimal price;
    /** 折扣价 */
    private BigDecimal discountedPrice;
    /** 状态 */
    private String status;
    /** 属性字符串 */
    private String properties;
    /** 条形码 */
    private String barCode;
    /** SKU与货位映射集合 */
    private Set<SKUSlotMapping> skuSlotMappings = new HashSet<>();
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

    public SKU() {}

    public SKU(Long id, Product product, String skuCode, String skuName, String specification, String unit, Integer quantity, BigDecimal price, BigDecimal discountedPrice, String status, String properties, String barCode, Set<SKUSlotMapping> skuSlotMappings, Date dateCreated, String creator, Date dateUpdated, String updater, Integer updatedCount, Date dateDeleted, Boolean deleteFlag, String deleter) {
        this.id = id;
        this.product = product;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.specification = specification;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.status = status;
        this.properties = properties;
        this.barCode = barCode;
        this.skuSlotMappings = skuSlotMappings;
        this.dateCreated = dateCreated;
        this.creator = creator;
        this.dateUpdated = dateUpdated;
        this.updater = updater;
        this.updatedCount = updatedCount;
        this.dateDeleted = dateDeleted;
        this.deleteFlag = deleteFlag;
        this.deleter = deleter;
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

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Set<SKUSlotMapping> getSkuSlotMappings() {
        return skuSlotMappings;
    }

    public void setSkuSlotMappings(Set<SKUSlotMapping> skuSlotMappings) {
        this.skuSlotMappings = skuSlotMappings;
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
        return skuName;
    }
}
