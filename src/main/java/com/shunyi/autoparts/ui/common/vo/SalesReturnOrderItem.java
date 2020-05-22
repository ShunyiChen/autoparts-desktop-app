package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;

/**
 * @Description: 销售退货单明细
 * @Author: Shunyi
 * @CreateDate: 2020/5/13
 */
public class SalesReturnOrderItem {
    /** 自增ID */
    private Long id;
    /** 原销售单号 */
    private String originalOrderNo;
    /** 销售退货单 */
    private SalesReturnOrder salesReturnOrder;
    /** SKU */
    private SKU sku;
    /** 数量 */
    private Integer quantity;
    /** 金额 */
    private BigDecimal amount;
    /** 折扣 */
    private BigDecimal discount;
    /** 折后金额 */
    private BigDecimal discountedAmount;
    /** 税率% */
    private BigDecimal taxRate;
    /** 税额 */
    private BigDecimal taxAmount;
    /** 含税单价 */
    private BigDecimal priceIncludingTax;
    /** 含税金额 */
    private BigDecimal amountIncludingTax;
    /** 不含税单价 */
    private BigDecimal priceExcludingTax;
    /** 不含税金额 */
    private BigDecimal amountExcludingTax;
    /** 备注 */
    private String notes;
    /** 可退货数量 */
    private Integer returnableQty;
    /** 异常 */
    private boolean exceptional;
    /** 开单价格 */
    private BigDecimal billingPrice;
    /** 开单金额 */
    private BigDecimal billingAmount;

    public SalesReturnOrderItem() {
    }

    public SalesReturnOrderItem(Long id, String originalOrderNo, SalesReturnOrder salesReturnOrder, SKU sku, Integer quantity, BigDecimal amount, BigDecimal discount, BigDecimal discountedAmount, BigDecimal taxRate, BigDecimal taxAmount, BigDecimal priceIncludingTax, BigDecimal amountIncludingTax, BigDecimal priceExcludingTax, BigDecimal amountExcludingTax, String notes, Integer returnableQty, boolean exceptional, BigDecimal billingPrice, BigDecimal billingAmount) {
        this.id = id;
        this.originalOrderNo = originalOrderNo;
        this.salesReturnOrder = salesReturnOrder;
        this.sku = sku;
        this.quantity = quantity;
        this.amount = amount;
        this.discount = discount;
        this.discountedAmount = discountedAmount;
        this.taxRate = taxRate;
        this.taxAmount = taxAmount;
        this.priceIncludingTax = priceIncludingTax;
        this.amountIncludingTax = amountIncludingTax;
        this.priceExcludingTax = priceExcludingTax;
        this.amountExcludingTax = amountExcludingTax;
        this.notes = notes;
        this.returnableQty = returnableQty;
        this.exceptional = exceptional;
        this.billingPrice = billingPrice;
        this.billingAmount = billingAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalOrderNo() {
        return originalOrderNo;
    }

    public void setOriginalOrderNo(String originalOrderNo) {
        this.originalOrderNo = originalOrderNo;
    }

    public SalesReturnOrder getSalesReturnOrder() {
        return salesReturnOrder;
    }

    public void setSalesReturnOrder(SalesReturnOrder salesReturnOrder) {
        this.salesReturnOrder = salesReturnOrder;
    }

    public SKU getSku() {
        return sku;
    }

    public void setSku(SKU sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(BigDecimal discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getPriceIncludingTax() {
        return priceIncludingTax;
    }

    public void setPriceIncludingTax(BigDecimal priceIncludingTax) {
        this.priceIncludingTax = priceIncludingTax;
    }

    public BigDecimal getAmountIncludingTax() {
        return amountIncludingTax;
    }

    public void setAmountIncludingTax(BigDecimal amountIncludingTax) {
        this.amountIncludingTax = amountIncludingTax;
    }

    public BigDecimal getPriceExcludingTax() {
        return priceExcludingTax;
    }

    public void setPriceExcludingTax(BigDecimal priceExcludingTax) {
        this.priceExcludingTax = priceExcludingTax;
    }

    public BigDecimal getAmountExcludingTax() {
        return amountExcludingTax;
    }

    public void setAmountExcludingTax(BigDecimal amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getReturnableQty() {
        return returnableQty;
    }

    public void setReturnableQty(Integer returnableQty) {
        this.returnableQty = returnableQty;
    }

    public boolean isExceptional() {
        return exceptional;
    }

    public void setExceptional(boolean exceptional) {
        this.exceptional = exceptional;
    }

    public BigDecimal getBillingPrice() {
        return billingPrice;
    }

    public void setBillingPrice(BigDecimal billingPrice) {
        this.billingPrice = billingPrice;
    }

    public BigDecimal getBillingAmount() {
        return billingAmount;
    }

    public void setBillingAmount(BigDecimal billingAmount) {
        this.billingAmount = billingAmount;
    }
}
