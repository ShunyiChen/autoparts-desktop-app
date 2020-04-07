package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;

/**
 * @Description: 采购订单明细VO
 * @Author: Shunyi Chen
 * @CreateDate: 2020/4/4
 */
public class PurchaseOrderItem {
    /** 自增ID */
    private Long id;
    /** 采购订单 */
    private PurchaseOrder purchaseOrder;
    /** SKU */
    private SKU sku;
    /** 数量 */
    private Integer quantity;
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

    public PurchaseOrderItem() {}

    public PurchaseOrderItem(Long id, PurchaseOrder purchaseOrder, SKU sku, Integer quantity, BigDecimal discount, BigDecimal discountedAmount, BigDecimal taxRate, BigDecimal taxAmount, BigDecimal priceIncludingTax, BigDecimal amountIncludingTax, BigDecimal priceExcludingTax, BigDecimal amountExcludingTax, String notes) {
        this.id = id;
        this.purchaseOrder = purchaseOrder;
        this.sku = sku;
        this.quantity = quantity;
        this.discount = discount;
        this.discountedAmount = discountedAmount;
        this.taxRate = taxRate;
        this.taxAmount = taxAmount;
        this.priceIncludingTax = priceIncludingTax;
        this.amountIncludingTax = amountIncludingTax;
        this.priceExcludingTax = priceExcludingTax;
        this.amountExcludingTax = amountExcludingTax;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
}
