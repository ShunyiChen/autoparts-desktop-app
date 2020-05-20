package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 采购退货单VO
 * @author Shunyi Chen
 * @date 2020/5/9
 */
public class PurchaseReturnOrder {
    /** ID */
    private Long id;
    /** 业务单号 */
    private String orderNo;
    /** 单据日期 */
    private Date orderDate;
    /** 仓库 */
    private Warehouse warehouse;
    /** 供应商 */
    private Supplier supplier;
    /** 退回数量 */
    private Integer returnQty;
    /** 未税金额 */
    private BigDecimal totalAmountExcludingTax;
    /** 含税金额 */
    private BigDecimal totalAmountIncludingTax;
    /** 税额 */
    private BigDecimal taxAmount;
    /** 费用金额 */
    private BigDecimal totalFees;
    /** 费用已付金额 */
    private BigDecimal totalFeesPaid;
    /** 货款已付金额 */
    private BigDecimal goodsPaymentPaid;
    /** 应付总额 */
    private BigDecimal amountPayable;
    /** 付款金额合计 */
    private BigDecimal totalPaymentAmount;
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
    /** 出库 */
    private Boolean warehousingOut;
    /** 起始日期 */
    private Date fromDate;
    /** 结束日期 */
    private Date toDate;

    public PurchaseReturnOrder() {}

    public PurchaseReturnOrder(Long id, String orderNo, Date orderDate, Warehouse warehouse, Supplier supplier, Supplier supplier1, Integer returnQty, BigDecimal totalAmountExcludingTax, BigDecimal totalAmountIncludingTax, BigDecimal taxAmount, BigDecimal totalFees, BigDecimal totalFeesPaid, BigDecimal goodsPaymentPaid, BigDecimal amountPayable, BigDecimal totalPaymentAmount, String invoiceType, String invoiceNo, String notes, String operator, String userName, String payment, String status, Date dateCreated, String creator, Boolean warehousingOut, Date fromDate, Date toDate) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.warehouse = warehouse;
        this.supplier = supplier;
        this.supplier = supplier1;
        this.returnQty = returnQty;
        this.totalAmountExcludingTax = totalAmountExcludingTax;
        this.totalAmountIncludingTax = totalAmountIncludingTax;
        this.taxAmount = taxAmount;
        this.totalFees = totalFees;
        this.totalFeesPaid = totalFeesPaid;
        this.goodsPaymentPaid = goodsPaymentPaid;
        this.amountPayable = amountPayable;
        this.totalPaymentAmount = totalPaymentAmount;
        this.invoiceType = invoiceType;
        this.invoiceNo = invoiceNo;
        this.notes = notes;
        this.operator = operator;
        this.userName = userName;
        this.payment = payment;
        this.status = status;
        this.dateCreated = dateCreated;
        this.creator = creator;
        this.warehousingOut = warehousingOut;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Integer getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(Integer returnQty) {
        this.returnQty = returnQty;
    }

    public BigDecimal getTotalAmountExcludingTax() {
        return totalAmountExcludingTax;
    }

    public void setTotalAmountExcludingTax(BigDecimal totalAmountExcludingTax) {
        this.totalAmountExcludingTax = totalAmountExcludingTax;
    }

    public BigDecimal getTotalAmountIncludingTax() {
        return totalAmountIncludingTax;
    }

    public void setTotalAmountIncludingTax(BigDecimal totalAmountIncludingTax) {
        this.totalAmountIncludingTax = totalAmountIncludingTax;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(BigDecimal totalFees) {
        this.totalFees = totalFees;
    }

    public BigDecimal getTotalFeesPaid() {
        return totalFeesPaid;
    }

    public void setTotalFeesPaid(BigDecimal totalFeesPaid) {
        this.totalFeesPaid = totalFeesPaid;
    }

    public BigDecimal getGoodsPaymentPaid() {
        return goodsPaymentPaid;
    }

    public void setGoodsPaymentPaid(BigDecimal goodsPaymentPaid) {
        this.goodsPaymentPaid = goodsPaymentPaid;
    }

    public BigDecimal getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(BigDecimal amountPayable) {
        this.amountPayable = amountPayable;
    }

    public BigDecimal getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(BigDecimal totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
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

    public Boolean getWarehousingOut() {
        return warehousingOut;
    }

    public void setWarehousingOut(Boolean warehousingOut) {
        this.warehousingOut = warehousingOut;
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
