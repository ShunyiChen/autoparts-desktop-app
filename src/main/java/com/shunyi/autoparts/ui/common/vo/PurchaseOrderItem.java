package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;

/**
 * @Description: 采购订单明细
 * @Author: 陈顺谊
 * @CreateDate: 2020/4/1 12:36
 * @Version: 1.0
 */
public class PurchaseOrderItem {
    /** 自增ID */
    private Long id;
    /** 采购订单 */
    private PurchaseOrder purchaseOrder;
    /** SKU */
    private SKU sku;
    /** 金额 */
    private BigDecimal amount;
    /** 折扣 */
    private BigDecimal discount;
    /** 折后金额 */
    private BigDecimal discountedAmount;
    /** 税率% */
    private BigDecimal taxRate;
    /** 税率% */
    private BigDecimal taxAmount;
    /** 含税单价 */
    private BigDecimal priceIncludingTax;
    /** 含税金额 */
    private BigDecimal amountIncludingTax;
    /** 备注 */
    private String notes;

    public PurchaseOrderItem() {}

    public PurchaseOrderItem(Long id, PurchaseOrder purchaseOrder, SKU sku, BigDecimal amount, BigDecimal discount, BigDecimal discountedAmount, BigDecimal taxRate, BigDecimal taxAmount, BigDecimal priceIncludingTax, BigDecimal amountIncludingTax, String notes) {
        this.id = id;
        this.purchaseOrder = purchaseOrder;
        this.sku = sku;
        this.amount = amount;
        this.discount = discount;
        this.discountedAmount = discountedAmount;
        this.taxRate = taxRate;
        this.taxAmount = taxAmount;
        this.priceIncludingTax = priceIncludingTax;
        this.amountIncludingTax = amountIncludingTax;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public SKU getSku() {
        return sku;
    }

    public void setSku(SKU sku) {
        this.sku = sku;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
