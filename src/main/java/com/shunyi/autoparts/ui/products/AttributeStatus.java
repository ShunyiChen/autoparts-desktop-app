package com.shunyi.autoparts.ui.products;

public enum AttributeStatus {
    AVAILABLE("可用"), DISABLED("不可用");
    /** 状态名 */
    private String name;
    /** 构造方法 */
    private AttributeStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
