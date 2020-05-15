package com.shunyi.autoparts.ui.common.vo;

/**
 * @Description: 盘点VO
 * @Author: Shunyi
 * @CreateDate: 2020/5/14
 */
public class Stocktaking {
    /** 自增ID */
    private Long id;
    /** 盘点方式编码 */
    private String code;
    /** 盘点方式名称 */
    private String name;
    /** 备注 */
    private String notes;

    public Stocktaking() {}

    public Stocktaking(Long id, String code, String name, String notes) {
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
