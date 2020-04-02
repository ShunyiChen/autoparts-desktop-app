package com.shunyi.autoparts.ui.common.vo;

/**
 * @description 仓库VO
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class Warehouse {
    /** ID */
    private Long id;
    /** 仓库编号 */
    private String code;
    /** 名称 */
    private String name;
    /** 备注 */
    private String notes;
    /** 所属店铺 */
    private Store store;

    public Warehouse() {}

    public Warehouse(Long id, String code, String name, String notes, Store store) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.notes = notes;
        this.store = store;
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return name;
    }
}
