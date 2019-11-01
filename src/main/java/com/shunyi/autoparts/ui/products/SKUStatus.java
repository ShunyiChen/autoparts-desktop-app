package com.shunyi.autoparts.ui.products;

/** SKU状态 */
public enum SKUStatus {
    AVAILABLE("可用"), DISABLED("不可用");
    /** 状态名 */
    private String name;
    /** 构造方法 */
    private SKUStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
