package com.shunyi.autoparts.ui.common;

/**
 * @author Shunyi
 */
public enum Status {
    /**
     * 可用
     */
    AVAILABLE("可用"),
    /**
     * 不可用
     */
    DISABLED("不可用");

    /**
     * 状态名
     */
    private String name;

    /**
     * 构造方法
     */
    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
