package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;

/**
 * @description 客户VO
 * @author Shunyi
 * @date 2020/5/12
 */
public class Consumer {
    /** 自增ID */
    private Long id;
    /** 客户类别 */
    private ConsumerCategory consumerCategory;
    /** 客户编码 */
    private String code;
    /** 客户名称 */
    private String name;
    /** 联系人 */
    private String contact;
    /** 电话 */
    private String phone;
    /** 地址 */
    private String address;
    /** Email */
    private String email;
    /** 车牌号 */
    private String licensePlate;
    /** 当前欠款总额 */
    private BigDecimal amountOwing;
    /** 信用额度 */
    private BigDecimal lineOfCredit;
    /** 预收款总额 */
    private BigDecimal totalAmountReceived;
    /** 公司 */
    private String company;
    /** 备注 */
    private String notes;

    public Consumer() {}

    public Consumer(Long id, ConsumerCategory consumerCategory, String code, String name, String contact, String phone, String address, String email, String licensePlate, BigDecimal amountOwing, BigDecimal lineOfCredit, BigDecimal totalAmountReceived, String company, String notes) {
        this.id = id;
        this.consumerCategory = consumerCategory;
        this.code = code;
        this.name = name;
        this.contact = contact;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.licensePlate = licensePlate;
        this.amountOwing = amountOwing;
        this.lineOfCredit = lineOfCredit;
        this.totalAmountReceived = totalAmountReceived;
        this.company = company;
        this.notes = notes;
    }

    public ConsumerCategory getConsumerCategory() {
        return consumerCategory;
    }

    public void setConsumerCategory(ConsumerCategory consumerCategory) {
        this.consumerCategory = consumerCategory;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public BigDecimal getAmountOwing() {
        return amountOwing;
    }

    public void setAmountOwing(BigDecimal amountOwing) {
        this.amountOwing = amountOwing;
    }

    public BigDecimal getLineOfCredit() {
        return lineOfCredit;
    }

    public void setLineOfCredit(BigDecimal lineOfCredit) {
        this.lineOfCredit = lineOfCredit;
    }

    public BigDecimal getTotalAmountReceived() {
        return totalAmountReceived;
    }

    public void setTotalAmountReceived(BigDecimal totalAmountReceived) {
        this.totalAmountReceived = totalAmountReceived;
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

    @Override
    public String toString() {
        return "Consumer{" +
                "id=" + id +
                ", consumerCategory=" + consumerCategory +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", amountOwing=" + amountOwing +
                ", lineOfCredit=" + lineOfCredit +
                ", totalAmountReceived=" + totalAmountReceived +
                ", company='" + company + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
