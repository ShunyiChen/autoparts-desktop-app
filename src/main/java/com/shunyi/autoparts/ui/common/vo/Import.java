package com.shunyi.autoparts.ui.common.vo;

/**
 * @description 配件进口VO
 * @author Shunyi Chen
 * @date 2020/4/11
 */
public class Import {
    /** 自增ID */
    private Long id;
    /** 名称 */
    private String name;

    public Import() {}

    public Import(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return name;
    }
}
