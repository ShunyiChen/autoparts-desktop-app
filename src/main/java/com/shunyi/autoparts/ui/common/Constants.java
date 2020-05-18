package com.shunyi.autoparts.ui.common;

import java.math.BigDecimal;

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
    public static final String STOCKTAKING_ORDER = "盘点单";
    public static final String PRICE_ADJUSTMENT_ORDER = "调价单";
    public static final String INVENTORY_ACCESSORIES = "库存配件";
    public static final String DATA_MAINTENANCE = "数据维护";
    public static final String SYSTEM_SETTINGS = "系统设置";
    public static final String ROOT = "root";
    public static final String EMPTY = "";
    public static final String ROOT_CATEGORY = "全部分类";
    public static final String AVAILABLE = "可用";
    public static final String UNAVAILABLE = "不可用";
    /** 采购订单状态 */
    public static final String CLOSED = "已结订单";
    /** 采购订单状态 */
    public static final String UNCLOSED = "未结订单";

    /** 升序 */
    public static final String ASC = "升序";
    /** 降序 */
    public static final String DESC = "降序";
    public static final Boolean DELETE_FLAG_TRUE = true;
    public static final Boolean DELETE_FLAG_FALSE = false;
    public static final Boolean PARENT_TRUE = true;
    public static final Boolean PARENT_FALSE = false;
    public static final Boolean VFS_MASTER_TRUE = true;
    public static final Boolean VFS_MASTER_FALSE = false;
    public static final BigDecimal AVG_PRICE = BigDecimal.ZERO;





    /** 初始ID值 */
    public static final Long ID = 0L;
    /** 根节点父ID值 */
    public static final Long ROOT_PARENT_ID = 0L;
    /** SKU初始库存数量 */
    public static final Integer ZERO = 0;

    /** 创建日期pattern */
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";



}
