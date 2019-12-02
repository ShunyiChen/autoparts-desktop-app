package com.shunyi.autoparts.ui.model;

/** 公司 */
public class Company extends Merchant {

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
