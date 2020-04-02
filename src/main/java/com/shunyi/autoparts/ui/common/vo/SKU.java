package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.*;

/**
 * @description 产品SKU VO
 * @author Shunyi Chen
 * @date 2020/4/2
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
    /** 条形码 */
    private String barCode;
    /** 库存数量 */
    private Integer stockQty;
    /** 属性字符串 */
    private String properties;
    /** 英文名 */
    private String englishName;
    /** 进口 */
    private String imported;
    /** 折扣% */
    private String discountPercentage;
    /** 可用状态 */
    private String status;
    /** 通用件号 */
    private String commonNumber;
    /** 材料 */
    private String materials;
    /** 所属公司 */
    private String company;
    /** 备注 */
    private String notes;
    /** 包装数量 */
    private Integer packingQuantity;
    /** 重量 */
    private BigDecimal weight;
    /** 手册名称 */
    private String manual;
    /** 一级进价 */
    private BigDecimal  purchasingPrice1;
    /** 二级进价 */
    private BigDecimal  purchasingPrice2;
    /** 三级进价 */
    private BigDecimal  purchasingPrice3;
    /** 一级销价 */
    private BigDecimal  sellingPrice1;
    /** 二级销价 */
    private BigDecimal  sellingPrice2;
    /** 三级销价 */
    private BigDecimal  sellingPrice3;
    /** 最低销价 */
    private BigDecimal  bottomPrice;
    /** 外币单位 */
    private String  foreignCurrencyUnit;
    /** 外币价格 */
    private String  foreignCurrencyPrice;
    /** 紧缺件 */
    private Boolean shortage;
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

    public SKU(Long id, Product product, String skuCode, String skuName, String specification, String unit, String barCode, Integer stockQty, String properties, String englishName, String imported, String discountPercentage, String status, String commonNumber, String materials, String company, String notes, Integer packingQuantity, BigDecimal weight, String manual, BigDecimal purchasingPrice1, BigDecimal purchasingPrice2, BigDecimal purchasingPrice3, BigDecimal sellingPrice1, BigDecimal sellingPrice2, BigDecimal sellingPrice3, BigDecimal bottomPrice, String foreignCurrencyUnit, String foreignCurrencyPrice, Boolean shortage, Set<SKUSlotMapping> skuSlotMappingSet, Set<SKUPhoto> photos, Date dateCreated, String creator, Date dateUpdated, String updater, Integer updatedCount, Date dateDeleted, Boolean deleteFlag, String deleter) {
        this.id = id;
        this.product = product;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.specification = specification;
        this.unit = unit;
        this.barCode = barCode;
        this.stockQty = stockQty;
        this.properties = properties;
        this.englishName = englishName;
        this.imported = imported;
        this.discountPercentage = discountPercentage;
        this.status = status;
        this.commonNumber = commonNumber;
        this.materials = materials;
        this.company = company;
        this.notes = notes;
        this.packingQuantity = packingQuantity;
        this.weight = weight;
        this.manual = manual;
        this.purchasingPrice1 = purchasingPrice1;
        this.purchasingPrice2 = purchasingPrice2;
        this.purchasingPrice3 = purchasingPrice3;
        this.sellingPrice1 = sellingPrice1;
        this.sellingPrice2 = sellingPrice2;
        this.sellingPrice3 = sellingPrice3;
        this.bottomPrice = bottomPrice;
        this.foreignCurrencyUnit = foreignCurrencyUnit;
        this.foreignCurrencyPrice = foreignCurrencyPrice;
        this.shortage = shortage;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getImported() {
        return imported;
    }

    public void setImported(String imported) {
        this.imported = imported;
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

    public String getCommonNumber() {
        return commonNumber;
    }

    public void setCommonNumber(String commonNumber) {
        this.commonNumber = commonNumber;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(Integer packingQuantity) {
        this.packingQuantity = packingQuantity;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public BigDecimal getPurchasingPrice1() {
        return purchasingPrice1;
    }

    public void setPurchasingPrice1(BigDecimal purchasingPrice1) {
        this.purchasingPrice1 = purchasingPrice1;
    }

    public BigDecimal getPurchasingPrice2() {
        return purchasingPrice2;
    }

    public void setPurchasingPrice2(BigDecimal purchasingPrice2) {
        this.purchasingPrice2 = purchasingPrice2;
    }

    public BigDecimal getPurchasingPrice3() {
        return purchasingPrice3;
    }

    public void setPurchasingPrice3(BigDecimal purchasingPrice3) {
        this.purchasingPrice3 = purchasingPrice3;
    }

    public BigDecimal getSellingPrice1() {
        return sellingPrice1;
    }

    public void setSellingPrice1(BigDecimal sellingPrice1) {
        this.sellingPrice1 = sellingPrice1;
    }

    public BigDecimal getSellingPrice2() {
        return sellingPrice2;
    }

    public void setSellingPrice2(BigDecimal sellingPrice2) {
        this.sellingPrice2 = sellingPrice2;
    }

    public BigDecimal getSellingPrice3() {
        return sellingPrice3;
    }

    public void setSellingPrice3(BigDecimal sellingPrice3) {
        this.sellingPrice3 = sellingPrice3;
    }

    public BigDecimal getBottomPrice() {
        return bottomPrice;
    }

    public void setBottomPrice(BigDecimal bottomPrice) {
        this.bottomPrice = bottomPrice;
    }

    public String getForeignCurrencyUnit() {
        return foreignCurrencyUnit;
    }

    public void setForeignCurrencyUnit(String foreignCurrencyUnit) {
        this.foreignCurrencyUnit = foreignCurrencyUnit;
    }

    public String getForeignCurrencyPrice() {
        return foreignCurrencyPrice;
    }

    public void setForeignCurrencyPrice(String foreignCurrencyPrice) {
        this.foreignCurrencyPrice = foreignCurrencyPrice;
    }

    public Boolean getShortage() {
        return shortage;
    }

    public void setShortage(Boolean shortage) {
        this.shortage = shortage;
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
