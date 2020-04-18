package com.shunyi.autoparts.ui.common.vo;

/**
 * @description 发票类型VO
 * @author Shunyi Chen
 * @date 2020/4/18
 */
public class InvoiceType {
    /** ID */
    private Long id;
    /** 发票类型编号 */
    private String code;
    /** 发票类型名称 */
    private String name;
    /** 备注 */
    private String notes;

    public InvoiceType() {}

    public InvoiceType(Long id, String code, String name, String notes) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.notes = notes;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
