package com.shunyi.autoparts.ui.products;

/** 字段排序 */
public enum Sort {
    ASC("升序",1), DESC("降序",-1);
    /** 排序名称 */
    private String name;
    /** 1 为升序排列，-1 为降序排列 */
    private int sort;
    /** 构造方法 */
    private Sort(String name, int sort) {
        this.name = name;
        this.sort = sort;
    }

    public int getSort() {
        return sort;
    }

    public String getName() {
        return name;
    }
}
