package com.shunyi.autoparts.ui.common.vo;

import java.util.Date;

/**
 * @description 供应商
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class Supplier {
    /** 自增ID */
    private Long id;
    /** 供应商编码 */
    private String code;
    /** 供应商单位名称 */
    private String name;
    /** 联系人 */
    private String contact;
    /** 电话1 */
    private String phone1;
    /** 电话2 */
    private String phone2;
    /** 电子邮件 */
    private String email;
    /** 地址 */
    private String address;
    /** 邮编 */
    private String postCode;
    /** 等级 */
    private String grade;
    /** 付款方式 */
    private String payment;
    /** 备注 */
    private String notes;
    /** 供应商类目 */
    private SupplierCategory category;
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

    public Supplier() {}

    public Supplier(Long id, String code, String name, String contact, String phone1, String phone2, String email, String address, String postCode, String grade, String payment, String notes, SupplierCategory category, Date dateCreated, String creator, Date dateUpdated, String updater, Integer updatedCount, Date dateDeleted, Boolean deleteFlag, String deleter) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.contact = contact;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.address = address;
        this.postCode = postCode;
        this.grade = grade;
        this.payment = payment;
        this.notes = notes;
        this.category = category;
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

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public SupplierCategory getCategory() {
        return category;
    }

    public void setCategory(SupplierCategory category) {
        this.category = category;
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
        return name;
    }
}
