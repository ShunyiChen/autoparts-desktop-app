package com.shunyi.autoparts.ui.model;

import java.util.Date;

/** 品牌系列DTO */
public class BrandSeries {
    /** ID */
    private Long id;
    /** 类目 */
    private Category category;
    /** 品牌中文名 */
    private String chineseName;
    /** 品牌英文名 */
    private String englishName;
    /** 描述 */
    private String description;
    /** 品牌Logo */
    private Logo logo;
    /** 状态 */
    private String status;
    /** 品牌官方网站 */
    private String officialSite;
    /** 创建时间 */
    private Date dateCreated;

    public BrandSeries() {
    }

    public BrandSeries(Category category, String chineseName, String englishName, String description, Logo logo, String status, String officialSite) {
        this.category = category;
        this.chineseName = chineseName;
        this.englishName = englishName;
        this.description = description;
        this.logo = logo;
        this.status = status;
        this.officialSite = officialSite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOfficialSite() {
        return officialSite;
    }

    public void setOfficialSite(String officialSite) {
        this.officialSite = officialSite;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return chineseName;
    }
}
