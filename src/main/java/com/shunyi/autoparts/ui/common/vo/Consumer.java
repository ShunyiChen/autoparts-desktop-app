package com.shunyi.autoparts.ui.common.vo;

/**
 * @description 客户VO
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class Consumer {
    /** 自增ID */
    private Long id;
    /** 客户编码 */
    private String code;
    /** 客户单位名称 */
    private String name;
    /** 联系人 */
    private String contact;
    /** 电话好 */
    private String phone;
    /** Email */
    private String email;
    /** 车牌号 */
    private String licensePlate;

    public Consumer() {}

    public Consumer(Long id, String code, String name, String contact, String phone, String email, String licensePlate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.licensePlate = licensePlate;
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
}
