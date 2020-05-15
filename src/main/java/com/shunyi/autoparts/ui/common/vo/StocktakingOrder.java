package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 盘点单VO
 * @Author: Shunyi
 * @CreateDate: 2020/5/14
 */
public class StocktakingOrder {
    /** ID */
    private Long id;
    /** 业务单号 */
    private String orderNo;
    /** 单据日期 */
    private Date orderDate;
    /** 配件条码 */
    private String productBarcode;
    /** 仓库 */
    private Warehouse warehouse;
    /** 盘点方式 */
    private Stocktaking stocktaking;
    /** 盘点数量 */
    private Integer stocktakingQty;
    /** 盘点金额 */
    private BigDecimal stocktakingAmount;
    /** 经办人 */
    private String operator;
    /** 操作员 */
    private String userName;
    /** 订单状态 */
    private String status;
    /** 备注 */
    private String notes;
    /** 起始日期 */
    private Date fromDate;
    /** 结束日期 */
    private Date toDate;

    public StocktakingOrder() {}

    public StocktakingOrder(Long id, String orderNo, Date orderDate, String productBarcode, Warehouse warehouse, Stocktaking stocktaking, Integer stocktakingQty, BigDecimal stocktakingAmount, String operator, String userName, String status, String notes, Date fromDate, Date toDate) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.productBarcode = productBarcode;
        this.warehouse = warehouse;
        this.stocktaking = stocktaking;
        this.stocktakingQty = stocktakingQty;
        this.stocktakingAmount = stocktakingAmount;
        this.operator = operator;
        this.userName = userName;
        this.status = status;
        this.notes = notes;
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

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Stocktaking getStocktaking() {
        return stocktaking;
    }

    public void setStocktaking(Stocktaking stocktaking) {
        this.stocktaking = stocktaking;
    }

    public Integer getStocktakingQty() {
        return stocktakingQty;
    }

    public void setStocktakingQty(Integer stocktakingQty) {
        this.stocktakingQty = stocktakingQty;
    }

    public BigDecimal getStocktakingAmount() {
        return stocktakingAmount;
    }

    public void setStocktakingAmount(BigDecimal stocktakingAmount) {
        this.stocktakingAmount = stocktakingAmount;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
