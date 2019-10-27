package com.shunyi.autoparts.ui.model;

import java.math.BigDecimal;
import java.util.Date;

/** 产品（汽车配件）DTO */
public class Product {
    /** ID */
    private Long id;
    /** 产品名称 */
    private String name;
    /** 品牌 */
    private BrandSeries brandSeries;
    /** 车型 */
    private Car car;
    /** 不含税单价/列表价 */
    private BigDecimal priceExcludingTax;
    /** 创建时间 */
    private Date dateCreated;

    public Product() {}

    public Product(String name, BrandSeries brandSeries, BigDecimal priceExcludingTax, Date dateCreated) {
        this.name = name;
        this.brandSeries = brandSeries;
        this.priceExcludingTax = priceExcludingTax;
        this.dateCreated = dateCreated;
    }

    public BrandSeries getBrandSeries() {
        return brandSeries;
    }

    public void setBrandSeries(BrandSeries brandSeries) {
        this.brandSeries = brandSeries;
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

    public BigDecimal getPriceExcludingTax() {
        return priceExcludingTax;
    }

    public void setPriceExcludingTax(BigDecimal priceExcludingTax) {
        this.priceExcludingTax = priceExcludingTax;
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
