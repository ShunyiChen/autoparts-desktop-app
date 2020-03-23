package com.shunyi.autoparts.ui.common;

/**
 * @author Shunyi
 */
public enum Sort {
    /**
     * 升序
     */
    ASC("升序", 1),
    /**
     * 降序
     */
    DESC("降序", -1);
    /**
     * 排序名称
     */
    private String name;
    /**
     * 1 为升序排列，-1 为降序排列
     */
    private int sort;

    /**
     * 构造方法
     */
    Sort(String name, int sort) {
        this.name = name;
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public int getSort() {
        return sort;
    }
}
