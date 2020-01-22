package com.shunyi.autoparts.ui.common;

/**
 * @Description: 功能权限CODE
 *
 * @Author: 陈顺谊
 * @CreateDate: 2020/1/22 22:18
 * @Version: 1.0
 */
public enum PermissionCodes {

    HOME(1, "主界面"),
    PURCHASE(2, "购货单"),
    PURCHASE_RETURN(3, "购货退货单"),
    SALES(4, "销售单"),
    SALES_RETURN(5, "销售退货单"),
    SUPPLIER(6, "供应商管理"),
    STOCK(7, "库存管理"),
    PARTS(8, "配件管理"),
    SYSTEM(9, "系统维护");

    /** 权限编码 */
    private int code;
    /** 权限名称 */
    private String name;

    /**
     * Constructor
     *
     * @param code
     * @param name
     */
    PermissionCodes(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
