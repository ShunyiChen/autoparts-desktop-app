package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 销售退货单VO
 * @author Shunyi
 * @date 2020/5/13
 */
public class SalesReturnOrder {
    /** ID */
    private Long id;
    /** 单号 */
    private String orderNo;
    /** 单据日期 */
    private Date orderDate;
    /** 仓库 */
    private Warehouse warehouse;
    /** 客户 */
    private Consumer consumer;
    /** 数量合计 */
    private Integer returnQty;
    /** 含税金额 */
    private BigDecimal amountIncludingTax;
    /** 不含税金额 */
    private BigDecimal amountExcludingTax;
    /** 税额 */
    private BigDecimal taxAmount;
    /** 总额 */
    private BigDecimal totalAmount;
    /** 代垫费用金额 */
    private BigDecimal disbursementAmount;
    /** 货款已退金额 */
    private BigDecimal returnedSalesAmount;
    /** 代垫费用已退金额 */
    private BigDecimal disbursementReturnedAmount;
    /** 已退款总额 */
    private BigDecimal totalReturnedAmount;
    /** 发票类型 */
    private String invoiceType;
    /** 发票号 */
    private String invoiceNo;
    /** 备注 */
    private String notes;
    /** 经办人 */
    private String operator;
    /** 操作员 */
    private String userName;
    /** 结算方式 */
    private String payment;
    /** 订单状态 */
    private String status;
    /** 创建时间 */
    private Date dateCreated;
    /** 创建者 */
    private String creator;
    /** 入库 */
    private Boolean warehousing;
    /** 搜索日期类型 */
    private String dateType;
    /** 起始日期 */
    private Date fromDate;
    /** 结束日期 */
    private Date toDate;

    public SalesReturnOrder() {}

    public SalesReturnOrder(Long id, String orderNo, Date orderDate, Warehouse warehouse, Consumer consumer, Integer returnQty, BigDecimal amountIncludingTax, BigDecimal amountExcludingTax, BigDecimal taxAmount, BigDecimal totalAmount, BigDecimal disbursementAmount, BigDecimal returnedSalesAmount, BigDecimal disbursementReturnedAmount, BigDecimal totalReturnedAmount, String invoiceType, String invoiceNo, String notes, String operator, String userName, String payment, String status, Date dateCreated, String creator, Boolean warehousing, String dateType, Date fromDate, Date toDate) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.warehouse = warehouse;
        this.consumer = consumer;
        this.returnQty = returnQty;
        this.amountIncludingTax = amountIncludingTax;
        this.amountExcludingTax = amountExcludingTax;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.disbursementAmount = disbursementAmount;
        this.returnedSalesAmount = returnedSalesAmount;
        this.disbursementReturnedAmount = disbursementReturnedAmount;
        this.totalReturnedAmount = totalReturnedAmount;
        this.invoiceType = invoiceType;
        this.invoiceNo = invoiceNo;
        this.notes = notes;
        this.operator = operator;
        this.userName = userName;
        this.payment = payment;
        this.status = status;
        this.dateCreated = dateCreated;
        this.creator = creator;
        this.warehousing = warehousing;
        this.dateType = dateType;
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

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Integer getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Integer returnQty) {
        this.returnQty = returnQty;
    }

    public BigDecimal getAmountIncludingTax() {
        return amountIncludingTax;
    }

    public void setAmountIncludingTax(BigDecimal amountIncludingTax) {
        this.amountIncludingTax = amountIncludingTax;
    }

    public BigDecimal getAmountExcludingTax() {
        return amountExcludingTax;
    }

    public void setAmountExcludingTax(BigDecimal amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getReturnedSalesAmount() {
        return returnedSalesAmount;
    }

    public void setReturnedSalesAmount(BigDecimal returnedSalesAmount) {
        this.returnedSalesAmount = returnedSalesAmount;
    }

    public BigDecimal getTotalReturnedAmount() {
        return totalReturnedAmount;
    }

    public void setTotalReturnedAmount(BigDecimal totalReturnedAmount) {
        this.totalReturnedAmount = totalReturnedAmount;
    }

    public BigDecimal getDisbursementAmount() {
        return disbursementAmount;
    }

    public void setDisbursementAmount(BigDecimal disbursementAmount) {
        this.disbursementAmount = disbursementAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
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

    public BigDecimal getDisbursementReturnedAmount() {
        return disbursementReturnedAmount;
    }

    public void setDisbursementReturnedAmount(BigDecimal disbursementReturnedAmount) {
        this.disbursementReturnedAmount = disbursementReturnedAmount;
    }

    public Boolean getWarehousing() {
        return warehousing;
    }

    public void setWarehousing(Boolean warehousing) {
        this.warehousing = warehousing;
    }
}
