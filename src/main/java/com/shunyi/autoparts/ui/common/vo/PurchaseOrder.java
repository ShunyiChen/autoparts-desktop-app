package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 采购订单VO
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class PurchaseOrder {
    /** ID */
    private Long id;
    /** 单号 */
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
    /** 结算方式 */
    private String payment;
    /** 应付账号 */
    private String account;
    /** 合计数量 */
    private Integer totalQty;
    /** 折后金额 */
    private BigDecimal discountedAmount;
    /** 合计金额 */
    private BigDecimal totalAmount;
    /** 付款金额 */
    private BigDecimal paidAmount;
    /** 含税金额 */
    private BigDecimal taxIncludedAmount;
    /** 优惠 */
    private BigDecimal discount;
    /** 优惠后金额 */
    private BigDecimal discountedAmount2;
    /** 应付款 */
    private BigDecimal due;
    /** 经办人 */
    private String operator;
    /** 订单状态 */
    private String status;
    /** 创建时间 */
    private Date dateCreated;
    /** 创建者 */
    private String creator;
    /** 更新时间 */
    private Date dateUpdated;
    /** 更新者 */
    private String updater;
    /** 更新次数 */
    private Integer updatedCount;
    /** 删除时间 */
    private Date dateDeleted;
    /** 删除标记 */
    private Boolean deleteFlag;
    /** 删除者 */
    private String deleter;

    public PurchaseOrder() {}

    public PurchaseOrder(Long id, String orderNo, Date orderDate, Warehouse warehouse, Supplier supplier, String invoiceType, String invoiceNo, BigDecimal freight, String notes, String payment, String account, Integer totalQty, BigDecimal discountedAmount, BigDecimal totalAmount, BigDecimal paidAmount, BigDecimal taxIncludedAmount, BigDecimal discount, BigDecimal discountedAmount2, BigDecimal due, String operator, String status, Date dateCreated, String creator, Date dateUpdated, String updater, Integer updatedCount, Date dateDeleted, Boolean deleteFlag, String deleter) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.warehouse = warehouse;
        this.supplier = supplier;
        this.invoiceType = invoiceType;
        this.invoiceNo = invoiceNo;
        this.freight = freight;
        this.notes = notes;
        this.payment = payment;
        this.account = account;
        this.totalQty = totalQty;
        this.discountedAmount = discountedAmount;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.taxIncludedAmount = taxIncludedAmount;
        this.discount = discount;
        this.discountedAmount2 = discountedAmount2;
        this.due = due;
        this.operator = operator;
        this.status = status;
        this.dateCreated = dateCreated;
        this.creator = creator;
        this.dateUpdated = dateUpdated;
        this.updater = updater;
        this.updatedCount = updatedCount;
        this.dateDeleted = dateDeleted;
        this.deleteFlag = deleteFlag;
        this.deleter = deleter;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(BigDecimal discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getTaxIncludedAmount() {
        return taxIncludedAmount;
    }

    public void setTaxIncludedAmount(BigDecimal taxIncludedAmount) {
        this.taxIncludedAmount = taxIncludedAmount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscountedAmount2() {
        return discountedAmount2;
    }

    public void setDiscountedAmount2(BigDecimal discountedAmount2) {
        this.discountedAmount2 = discountedAmount2;
    }

    public BigDecimal getDue() {
        return due;
    }

    public void setDue(BigDecimal due) {
        this.due = due;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Integer getUpdatedCount() {
        return updatedCount;
    }

    public void setUpdatedCount(Integer updatedCount) {
        this.updatedCount = updatedCount;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleter() {
        return deleter;
    }

    public void setDeleter(String deleter) {
        this.deleter = deleter;
    }
}
