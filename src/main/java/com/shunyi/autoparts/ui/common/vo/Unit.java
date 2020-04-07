package com.shunyi.autoparts.ui.common.vo;

/**
 * @description 配件单位VO
 * @author Shunyi Chen
 * @date 2020/4/7
 */
public class Unit {
    /** 自增ID */
    private Long id;
    /** 单位名称 */
    private String name;

    public Unit() {}

    public Unit(Long id, String name) {
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
}
