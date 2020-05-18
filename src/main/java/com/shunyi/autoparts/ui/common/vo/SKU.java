package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 产品SKU VO
 * @author Shunyi
 * @date 2020/5/18
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
    private String skuBarCode;
    /** 库存数量 */
    private Integer stockQty;
    /** 库存平均价 */
    private Integer stockAvgPrice;
    /** 库存金额 */
    private Integer stockAmount;
    /** 进货平均价 */
    private BigDecimal purchaseAvgPrice;
    /** 进货金额 */
    private BigDecimal purchaseAmount;
    /** 属性字符串 */
    private String properties;
    /** 折扣% */
    private String discountPercentage;
    /** 可用状态 */
    private Boolean enabled;
    /** 备注 */
    private String notes;
    /** SKU与货位映射集合 */
    private Set<SKUSlotMapping> skuSlotMappingSet = new HashSet<>();
    /** SKU图片列表 */
    private Set<SKUPhoto> photos = new HashSet<>();

    public SKU() {}

    public SKU(Long id, Product product, String skuCode, String skuName, String specification, String skuBarCode, Integer stockQty, Integer stockAvgPrice, Integer stockAmount, BigDecimal purchaseAvgPrice, BigDecimal purchaseAmount, String properties, String discountPercentage, Boolean enabled, String notes, Set<SKUSlotMapping> skuSlotMappingSet, Set<SKUPhoto> photos) {
        this.id = id;
        this.product = product;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.specification = specification;
        this.skuBarCode = skuBarCode;
        this.stockQty = stockQty;
        this.stockAvgPrice = stockAvgPrice;
        this.stockAmount = stockAmount;
        this.purchaseAvgPrice = purchaseAvgPrice;
        this.purchaseAmount = purchaseAmount;
        this.properties = properties;
        this.discountPercentage = discountPercentage;
        this.enabled = enabled;
        this.notes = notes;
        this.skuSlotMappingSet = skuSlotMappingSet;
        this.photos = photos;
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

    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    public Integer getStockAvgPrice() {
        return stockAvgPrice;
    }

    public void setStockAvgPrice(Integer stockAvgPrice) {
        this.stockAvgPrice = stockAvgPrice;
    }

    public Integer getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(Integer stockAmount) {
        this.stockAmount = stockAmount;
    }

    public BigDecimal getPurchaseAvgPrice() {
        return purchaseAvgPrice;
    }

    public void setPurchaseAvgPrice(BigDecimal purchaseAvgPrice) {
        this.purchaseAvgPrice = purchaseAvgPrice;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    @Override
    public String toString() {
        return "SKU{" +
                "id=" + id +
                ", product=" + product +
                ", skuCode='" + skuCode + '\'' +
                ", skuName='" + skuName + '\'' +
                ", specification='" + specification + '\'' +
                ", skuBarCode='" + skuBarCode + '\'' +
                ", stockQty=" + stockQty +
                ", stockAvgPrice=" + stockAvgPrice +
                ", stockAmount=" + stockAmount +
                ", purchaseAvgPrice=" + purchaseAvgPrice +
                ", purchaseAmount=" + purchaseAmount +
                ", properties='" + properties + '\'' +
                ", discountPercentage='" + discountPercentage + '\'' +
                ", enabled='" + enabled + '\'' +
                ", notes='" + notes + '\'' +
                ", skuSlotMappingSet=" + skuSlotMappingSet +
                ", photos=" + photos +
                '}';
    }
}
