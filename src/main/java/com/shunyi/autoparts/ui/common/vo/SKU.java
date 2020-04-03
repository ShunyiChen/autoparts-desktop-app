package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 产品SKU VO
 * @author Shunyi Chen
 * @date 2020/4/3
 */
public class SKU {
    /** 自增ID */
    private Long id;
    /** 产品SPU */
    private Product product;
    /** SKU编码 */
    private String skuCode;
    /** SKU名称 */
    private String skuName;
    /** 规格 */
    private String specification;
    /** 条形码 */
    private String barCode;
    /** 库存数量 */
    private Integer stockQty;
    /** 属性字符串 */
    private String properties;
    /** 折扣% */
    private String discountPercentage;
    /** 可用状态 */
    private String status;
    /** 备注 */
    private String notes;
    /** 进货平均价 */
    private BigDecimal avgPrice;
    /** SKU与货位映射集合 */
    private Set<SKUSlotMapping> skuSlotMappingSet = new HashSet<>();
    /** SKU图片列表 */
    private Set<SKUPhoto> photos = new HashSet<>();
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

    public SKU(Long id, Product product, String skuCode, String skuName, String specification, String barCode, Integer stockQty, String properties, String discountPercentage, String status, String notes, BigDecimal avgPrice, Set<SKUSlotMapping> skuSlotMappingSet, Set<SKUPhoto> photos, Date dateCreated, String creator, Date dateUpdated, String updater, Integer updatedCount, Date dateDeleted, Boolean deleteFlag, String deleter) {
        this.id = id;
        this.product = product;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.specification = specification;
        this.barCode = barCode;
        this.stockQty = stockQty;
        this.properties = properties;
        this.discountPercentage = discountPercentage;
        this.status = status;
        this.notes = notes;
        this.avgPrice = avgPrice;
        this.skuSlotMappingSet = skuSlotMappingSet;
        this.photos = photos;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Set<SKUSlotMapping> getSkuSlotMappingSet() {
        return skuSlotMappingSet;
    }

    public void setSkuSlotMappingSet(Set<SKUSlotMapping> skuSlotMappingSet) {
        this.skuSlotMappingSet = skuSlotMappingSet;
    }

    public Set<SKUPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<SKUPhoto> photos) {
        this.photos = photos;
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
}
