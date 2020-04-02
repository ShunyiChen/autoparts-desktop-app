package com.shunyi.autoparts.ui.common.vo;

/**
 * @description 供应商VO
 * @author Shunyi Chen
 * @date 2020/4/2
 */
public class Supplier implements SupplierIF {
    /** ID */
    private Long id;
    /** 供应商编码 */
    private String code;
    /** 供应商类目 */
    private SupplierCategory supplierCategory;
    /** 供应商单位名称 */
    private String name;
    /** 省市 */
    private String city;
    /** 联系人 */
    private String contact;
    /** 电话 */
    private String phone;
    /** 电子邮件 */
    private String email;
    /** 地址 */
    private String address;
    /** 邮编 */
    private String postCode;
    /** 网站 */
    private String webSite;
    /** 全程 */
    private String fullName;
    /** 法人 */
    private String corp;
    /** 银行 */
    private String bank;
    /** 开户账号 */
    private String account;
    /** 税号 */
    private String taxFileNumber;
    /** 开票地址 */
    private String billingAddress;
    /** 备注 */
    private String notes;

    public Supplier() {}

    public Supplier(Long id, String code, SupplierCategory supplierCategory, String name, String city, String contact, String phone, String email, String address, String postCode, String webSite, String fullName, String corp, String bank, String account, String taxFileNumber, String billingAddress, String notes) {
        this.id = id;
        this.code = code;
        this.supplierCategory = supplierCategory;
        this.name = name;
        this.city = city;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.postCode = postCode;
        this.webSite = webSite;
        this.fullName = fullName;
        this.corp = corp;
        this.bank = bank;
        this.account = account;
        this.taxFileNumber = taxFileNumber;
        this.billingAddress = billingAddress;
        this.notes = notes;
    }

    @Override
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

    public SupplierCategory getSupplierCategory() {
        return supplierCategory;
    }

    public void setSupplierCategory(SupplierCategory supplierCategory) {
        this.supplierCategory = supplierCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTaxFileNumber() {
        return taxFileNumber;
    }

    public void setTaxFileNumber(String taxFileNumber) {
        this.taxFileNumber = taxFileNumber;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
