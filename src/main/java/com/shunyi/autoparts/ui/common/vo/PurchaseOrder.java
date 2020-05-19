package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 采购订单VO
 * @author Shunyi Chen
 * @date 2020/4/18
 */
public class PurchaseOrder {
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
    /** 发票类型 */
    private String invoiceType;
    /** 发票号 */
    private String invoiceNo;
    /** 运费 */
    private BigDecimal freight;
    /** 备注 */
    private String notes;
    /** 经办人 */
    private String operator;
    /** 系统账号 */
    private String userName;
    /** 结算方式 */
    private String payment;
    /** 进货数量 */
    private Integer purchaseQty;
    /** 已入库数量 */
    private Integer stockedQty;
    /** 退货数量合计 */
    private Integer returnedTotalQty;
    /** 货款金额 */
    private BigDecimal purchaseAmount;
    /** 垫付费用 */
    private BigDecimal disbursementAmount;
    /** 垫付摘要 */
    private String summary;
    /** 本次优惠 */
    private BigDecimal discountAmount;
    /** 应付总额 */
    private BigDecimal amountPayable;
    /** 本次付款 */
    private BigDecimal paymentAmount;
    /** 账号 */
    private String account;
    /** 还款金额 */
    private BigDecimal repaymentAmount;
    /** 还款日期 */
    private Date repaymentDate;
    /** 未税金额 */
    private BigDecimal totalAmountExcludingTax;
    /** 含税金额 */
    private BigDecimal totalAmountIncludingTax;
    /** 订单状态 */
    private String status;
    /** 创建时间 */
    private Date dateCreated;
    /** 创建者 */
    private String creator;
    /** 搜索日期类型 */
    private String dateType;
    /** 起始日期 */
    private Date fromDate;
    /** 结束日期 */
    private Date toDate;
    /** 入库 */
    private Boolean warehousing;
    /** 付款 */
    private Boolean paying;

    public PurchaseOrder() {}

    public PurchaseOrder(Long id, String orderNo, Date orderDate, Warehouse warehouse, Supplier supplier, String invoiceType, String invoiceNo, BigDecimal freight, String notes, String operator, String userName, String payment, Integer purchaseQty, Integer stockedQty, Integer returnedTotalQty, BigDecimal purchaseAmount, BigDecimal disbursementAmount, String summary, BigDecimal discountAmount, BigDecimal amountPayable, BigDecimal paymentAmount, String account, BigDecimal repaymentAmount, Date repaymentDate, BigDecimal totalAmountExcludingTax, BigDecimal totalAmountIncludingTax, String status, Date dateCreated, String creator, String dateType, Date fromDate, Date toDate, Boolean warehousing, Boolean paying) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.warehouse = warehouse;
        this.supplier = supplier;
        this.invoiceType = invoiceType;
        this.invoiceNo = invoiceNo;
        this.freight = freight;
        this.notes = notes;
        this.operator = operator;
        this.userName = userName;
        this.payment = payment;
        this.purchaseQty = purchaseQty;
        this.stockedQty = stockedQty;
        this.returnedTotalQty = returnedTotalQty;
        this.purchaseAmount = purchaseAmount;
        this.disbursementAmount = disbursementAmount;
        this.summary = summary;
        this.discountAmount = discountAmount;
        this.amountPayable = amountPayable;
        this.paymentAmount = paymentAmount;
        this.account = account;
        this.repaymentAmount = repaymentAmount;
        this.repaymentDate = repaymentDate;
        this.totalAmountExcludingTax = totalAmountExcludingTax;
        this.totalAmountIncludingTax = totalAmountIncludingTax;
        this.status = status;
        this.dateCreated = dateCreated;
        this.creator = creator;
        this.dateType = dateType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.warehousing = warehousing;
        this.paying = paying;
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

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
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

    public Integer getPurchaseQty() {
        return purchaseQty;
    }

    public void setPurchaseQty(Integer purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public BigDecimal getDisbursementAmount() {
        return disbursementAmount;
    }

    public void setDisbursementAmount(BigDecimal disbursementAmount) {
        this.disbursementAmount = disbursementAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(BigDecimal amountPayable) {
        this.amountPayable = amountPayable;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getStockedQty() {
        return stockedQty;
    }

    public void setStockedQty(Integer stockedQty) {
        this.stockedQty = stockedQty;
    }

    public Integer getReturnedTotalQty() {
        return returnedTotalQty;
    }

    public void setReturnedTotalQty(Integer returnedTotalQty) {
        this.returnedTotalQty = returnedTotalQty;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getWarehousing() {
        return warehousing;
    }

    public void setWarehousing(Boolean warehousing) {
        this.warehousing = warehousing;
    }

    public Boolean getPaying() {
        return paying;
    }

    public void setPaying(Boolean paying) {
        this.paying = paying;
    }
}
