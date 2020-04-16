package com.shunyi.autoparts.ui.common.vo;

/**
 * @description 所属公司VO
 * @author Shunyi Chen
 * @date 2020/4/13
 */
public class Company {
    /** 自增ID */
    private Long id;
    /** 公司编号 */
    private String code;
    /** 公司名称 */
    private String name;
    /** 备注 */
    private String notes;

    public Company() {}

    public Company(String name) {
        this.name = name;
    }

    public Company(Long id, String code, String name, String notes) {
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

    @Override
    public String toString() {
        return name;
    }
}
