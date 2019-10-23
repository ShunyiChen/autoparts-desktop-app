package com.shunyi.autoparts.ui.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/** 产品SKU DTO*/
public class SKU {
    /** 自增ID */
    private Long id;
    /** 产品 */
    private Product product;
    /** SKU编码 */
    private String skuCode;
    /** SKU名称 */
    private String skuName;
    /** 单位 */
    private String unit;
    /** 数量 */
    private Integer quantity;
    /** 单价 */
    private BigDecimal price;
    /** 外部编辑单价 */
    private BigDecimal externalEdit;
    /** 状态 */
    private String status;
    /** 属性字符串 */
    private String properties;
    /** 条形码 */
    private String barCode;
    /** 产品编码 */
    private String productCode;
    /** 创建时间 */
    private Date dateCreated;
    /** SKU与货位映射集合 */
    private Set<SKUCargoSpaceMapping> SKUCargoSpaceMappings = new HashSet<>();

    public SKU() {}

    public SKU(Product product, String skuCode, String skuName, String unit, Integer quantity, BigDecimal price, BigDecimal externalEdit, String status, String properties, String barCode, String productCode, Date dateCreated, Set<SKUCargoSpaceMapping> SKUCargoSpaceMappings) {
        this.product = product;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.externalEdit = externalEdit;
        this.status = status;
        this.properties = properties;
        this.barCode = barCode;
        this.productCode = productCode;
        this.dateCreated = dateCreated;
        this.SKUCargoSpaceMappings = SKUCargoSpaceMappings;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<SKUCargoSpaceMapping> getSKUCargoSpaceMappings() {
        return SKUCargoSpaceMappings;
    }

    public void setSKUCargoSpaceMappings(Set<SKUCargoSpaceMapping> SKUCargoSpaceMappings) {
        this.SKUCargoSpaceMappings = SKUCargoSpaceMappings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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

    public BigDecimal getExternalEdit() {
        return externalEdit;
    }

    public void setExternalEdit(BigDecimal externalEdit) {
        this.externalEdit = externalEdit;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "SKU{" +
                "id=" + id +
                ", product=" + product +
                ", skuCode='" + skuCode + '\'' +
                ", skuName='" + skuName + '\'' +
                ", unit='" + unit + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", externalEdit=" + externalEdit +
                ", status='" + status + '\'' +
                ", properties='" + properties + '\'' +
                ", barCode='" + barCode + '\'' +
                ", productCode='" + productCode + '\'' +
                ", dateCreated=" + dateCreated +
                ", SKUCargoSpaceMappings=" + SKUCargoSpaceMappings +
                '}';
    }
}
