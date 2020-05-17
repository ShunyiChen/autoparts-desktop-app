package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 价格调整单VO
 * @Author: Shunyi
 * @CreateDate: 2020/5/15
 */
public class PriceAdjustmentOrder {
    /** ID */
    private Long id;
    /** 业务单号 */
    private String orderNo;
    /** 单据日期 */
    private Date orderDate;
    /** 仓库 */
    private Warehouse warehouse;
    /** 调价金额 */
    private BigDecimal amountOfAdjustment;
    /** 经办人 */
    private String operator;
    /** 操作员 */
    private String userName;
    /** 调价说明 */
    private String notes;
    /** 订单状态 */
    private String status;
    /** 起始日期 */
    private Date fromDate;
    /** 结束日期 */
    private Date toDate;

    public PriceAdjustmentOrder() {}

    public PriceAdjustmentOrder(Long id, String orderNo, Date orderDate, Warehouse warehouse, BigDecimal amountOfAdjustment, String operator, String userName, String notes, String status, Date fromDate, Date toDate) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.warehouse = warehouse;
        this.amountOfAdjustment = amountOfAdjustment;
        this.operator = operator;
        this.userName = userName;
        this.notes = notes;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getAmountOfAdjustment() {
        return amountOfAdjustment;
    }

    public void setAmountOfAdjustment(BigDecimal amountOfAdjustment) {
        this.amountOfAdjustment = amountOfAdjustment;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
