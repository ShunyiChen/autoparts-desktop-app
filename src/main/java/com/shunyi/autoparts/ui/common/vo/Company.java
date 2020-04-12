package com.shunyi.autoparts.ui.common.vo;

/**
 * @description 所属公司VO
 * @author Shunyi Chen
 * @date 2020/4/11
 */
public class Company {
    /** 自增ID */
    private Long id;
    /** 公司名称 */
    private String name;

    public Company() {}

    public Company(Long id, String name) {
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
