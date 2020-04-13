package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 配件VO
 * @author Shunyi Chen
 * @date 2020/4/4
 */
public class Product {
    /** 自增ID */
    private Long id;
    /** 产品编码 */
    private String code;
    /** 类别 */
    private Category category;
    /** 产品名称 */
    private String name;
    /** 单位 */
    private String unit;
    /** 条形码 */
    private String barCode;
    /** 车型 */
    private Car car;
    /** 通用车型 */
    private String relevantModels;
    /** 产地 */
    private Place place;
    /** 品牌 */
    private Brand brand;
    /** 英文名 */
    private String englishName;
    /** 进口 */
    private Import imported;
    /** 通用件号 */
    private String commonNumber;
    /** 材料 */
    private String materials;
    /** 所属公司 */
    private Company company;
    /** 包装数量 */
    private Integer packingQuantity;
    /** 重量 */
    private String weight;
    /** 手册名称 */
    private String manual;
    /** 供应商 */
    private Supplier supplier;
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

    public Product() {
    }

    public Product(Long id, String code, Category category, String name, String unit, String barCode, Car car, String relevantModels, Place place, Brand brand, String englishName, Import imported, String commonNumber, String materials, Company company, Integer packingQuantity, String weight, String manual, Supplier supplier, BigDecimal purchasingPrice1, BigDecimal purchasingPrice2, BigDecimal purchasingPrice3, BigDecimal sellingPrice1, BigDecimal sellingPrice2, BigDecimal sellingPrice3, BigDecimal bottomPrice, String foreignCurrencyUnit, String foreignCurrencyPrice, Boolean shortage, Date dateCreated, String creator, Date dateUpdated, String updater, Integer updatedCount, Date dateDeleted, Boolean deleteFlag, String deleter) {
        this.id = id;
        this.code = code;
        this.category = category;
        this.name = name;
        this.unit = unit;
        this.barCode = barCode;
        this.car = car;
        this.relevantModels = relevantModels;
        this.place = place;
        this.brand = brand;
        this.englishName = englishName;
        this.imported = imported;
        this.commonNumber = commonNumber;
        this.materials = materials;
        this.company = company;
        this.packingQuantity = packingQuantity;
        this.weight = weight;
        this.manual = manual;
        this.supplier = supplier;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getRelevantModels() {
        return relevantModels;
    }

    public void setRelevantModels(String relevantModels) {
        this.relevantModels = relevantModels;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Import getImported() {
        return imported;
    }

    public void setImported(Import imported) {
        this.imported = imported;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Integer getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(Integer packingQuantity) {
        this.packingQuantity = packingQuantity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", barCode='" + barCode + '\'' +
                ", car=" + car +
                ", relevantModels=" + relevantModels +
                ", place=" + place +
                ", brand=" + brand +
                ", englishName='" + englishName + '\'' +
                ", imported='" + imported + '\'' +
                ", commonNumber='" + commonNumber + '\'' +
                ", materials='" + materials + '\'' +
                ", company='" + company + '\'' +
                ", packingQuantity=" + packingQuantity +
                ", weight=" + weight +
                ", manual='" + manual + '\'' +
                ", supplier=" + supplier +
                ", purchasingPrice1=" + purchasingPrice1 +
                ", purchasingPrice2=" + purchasingPrice2 +
                ", purchasingPrice3=" + purchasingPrice3 +
                ", sellingPrice1=" + sellingPrice1 +
                ", sellingPrice2=" + sellingPrice2 +
                ", sellingPrice3=" + sellingPrice3 +
                ", bottomPrice=" + bottomPrice +
                ", foreignCurrencyUnit='" + foreignCurrencyUnit + '\'' +
                ", foreignCurrencyPrice='" + foreignCurrencyPrice + '\'' +
                ", shortage=" + shortage +
                ", dateCreated=" + dateCreated +
                ", creator='" + creator + '\'' +
                ", dateUpdated=" + dateUpdated +
                ", updater='" + updater + '\'' +
                ", updatedCount=" + updatedCount +
                ", dateDeleted=" + dateDeleted +
                ", deleteFlag=" + deleteFlag +
                ", deleter='" + deleter + '\'' +
                '}';
    }
}
