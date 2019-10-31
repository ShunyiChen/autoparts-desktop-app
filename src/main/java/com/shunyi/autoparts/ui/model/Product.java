package com.shunyi.autoparts.ui.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/** 产品（汽车配件）DTO */
public class Product {
    /** ID */
    private Long id;
    /** 产品编码 */
    private String code;
    /** 产品名称 */
    private String name;
    /** 品牌 */
    private BrandSeries brandSeries;
    /** 车型 */
    private Car car;
    /** 单位 */
    private String unit;
    /** 进口 */
    private String imported;
    /** 产地 */
    private String placeOfOrigin;
    /** 不含税单价 */
    private BigDecimal priceExcludingTax;
    /** 其他 */
    private String notes;
    /** 创建时间 */
    private Date dateCreated;

    public Product() {}

    public Product(String code, String name, BrandSeries brandSeries, Car car, String unit, String imported, String placeOfOrigin, BigDecimal priceExcludingTax, String notes) {
        this.code = code;
        this.name = name;
        this.brandSeries = brandSeries;
        this.car = car;
        this.unit = unit;
        this.imported = imported;
        this.placeOfOrigin = placeOfOrigin;
        this.priceExcludingTax = priceExcludingTax;
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BrandSeries getBrandSeries() {
        return brandSeries;
    }

    public void setBrandSeries(BrandSeries brandSeries) {
        this.brandSeries = brandSeries;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImported() {
        return imported;
    }

    public void setImported(String imported) {
        this.imported = imported;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public BigDecimal getPriceExcludingTax() {
        return priceExcludingTax;
    }

    public void setPriceExcludingTax(BigDecimal priceExcludingTax) {
        this.priceExcludingTax = priceExcludingTax.setScale(2, RoundingMode.HALF_UP);
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", brandSeries=" + brandSeries +
                ", car=" + car +
                ", unit='" + unit + '\'' +
                ", imported='" + imported + '\'' +
                ", placeOfOrigin='" + placeOfOrigin + '\'' +
                ", priceExcludingTax=" + priceExcludingTax +
                ", notes='" + notes + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
