package com.shunyi.autoparts.ui.model;

/** 供应商DTO */
public class Supplier {
    private Long id;
    /** 供应商编码 */
    private String code;
    /** 供应商单位名称 */
    private String name;
    /** 联系人 */
    private String contact;
    /** 电话 */
    private String phone;
    /** 其他 */
    private String other;
    /** 供应商类目 */
    private SupplierCategory category;

    public Supplier() {}

    public Supplier(String code, String name, String contact, String phone, String other, SupplierCategory category) {
        this.code = code;
        this.name = name;
        this.contact = contact;
        this.phone = phone;
        this.other = other;
        this.category = category;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
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

    public SupplierCategory getCategory() {
        return category;
    }

    public void setCategory(SupplierCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", other='" + other + '\'' +
                ", category=" + category +
                '}';
    }
}
