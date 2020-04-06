package com.shunyi.autoparts.ui.common;

/**
 * @Description:
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/23 21:36
 * @Version: 1.0
 */
public class Constants {

    public static final String PURCHASE_VIEW = "采购视图";
    public static final String SALES_VIEW = "销售视图";
    public static final String PURCHASE_ORDER = "采购单";
    public static final String PURCHASE_RETURN_ORDER = "采购退货单";
    public static final String SALES_ORDER = "销售单";
    public static final String SALES_RETURN_ORDER = "销售退货单";
    public static final String INVENTORYING_ORDER = "盘点单";
    public static final String PRICE_ADJUSTMENT_ORDER = "调价单";
    public static final String INVENTORY_DETAILS = "库存明细";
    public static final String SYSTEM_SETTINGS = "系统设置";

    public static final Boolean DELETE_FLAG_TRUE = true;
    public static final Boolean DELETE_FLAG_FALSE = false;
    public static final String ROOT = "root";
    public static final Boolean PARENT_TRUE = true;
    public static final Boolean PARENT_FALSE = false;
    public static final Boolean VFS_MASTER_TRUE = true;
    public static final Boolean VFS_MASTER_FALSE = false;
    public static final String HOMEMADE = "国产";
    public static final String ORIGINAL  = "原厂";

    /** 值对象初始ID值 */
    public static final Long ID = 0L;
    /** 值对象初始父ID值 */
    public static final Long PARENT_ID = -1L;
    /** SKU初始库存数量 */
    public static final Integer QUANTITY = 0;
    /** 创建日期pattern */
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    /** 采购订单状态-未结算 */
    public static final String UNCLOSED = "未结算";
    /** 采购订单状态-已结算 */
    public static final String CLOSED = "已结算";
}
