package com.shunyi.autoparts.ui.common.vo;

/**
 * @Description: 发货方式VO
 * @Author: Shunyi
 * @CreateDate: 2020/5/12
 */
public class Delivery {
    /** ID */
    private Long id;
    /** 发货方式名称 */
    private String name;
    /** 货运编码 */
    private String freightCode;
    /** 货运名称 */
    private String freightName;

    public Delivery() {}

    public Delivery(Long id, String name, String freightCode, String freightName) {
        this.id = id;
        this.name = name;
        this.freightCode = freightCode;
        this.freightName = freightName;
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

    public String getFreightCode() {
        return freightCode;
    }

    public void setFreightCode(String freightCode) {
        this.freightCode = freightCode;
    }

    public String getFreightName() {
        return freightName;
    }

    public void setFreightName(String freightName) {
        this.freightName = freightName;
    }
}
