package com.shunyi.autoparts.ui.model;

/** 店铺 */
public class Shop extends Merchant {
    /** 公司 */
    private Company company;

    public Shop() {}

    public Shop(String name, Company company) {
        this.name = name;
        this.company = company;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return name;
    }
}
