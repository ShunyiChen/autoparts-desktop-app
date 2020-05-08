package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;

/**
 * @Description: 采购退货单明细VO
 * @Author: Shunyi Chen
 * @CreateDate: 2020/5/9
 */
public class PurchaseReturnOrderItem {
    /** 自增ID */
    private Long id;
    /** 原进货单号 */
    private String originalOrderNo;
    /** 采购订单 */
    private PurchaseReturnOrder purchaseReturnOrder;
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

    public PurchaseReturnOrderItem() {}

    public PurchaseReturnOrderItem(Long id, String originalOrderNo, PurchaseReturnOrder purchaseReturnOrder, SKU sku, Integer quantity, BigDecimal amount, BigDecimal discount, BigDecimal discountedAmount, BigDecimal taxRate, BigDecimal taxAmount, BigDecimal priceIncludingTax, BigDecimal amountIncludingTax, BigDecimal priceExcludingTax, BigDecimal amountExcludingTax, String notes) {
        this.id = id;
        this.originalOrderNo = originalOrderNo;
        this.purchaseReturnOrder = purchaseReturnOrder;
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

    public PurchaseReturnOrder getPurchaseReturnOrder() {
        return purchaseReturnOrder;
    }

    public void setPurchaseReturnOrder(PurchaseReturnOrder purchaseReturnOrder) {
        this.purchaseReturnOrder = purchaseReturnOrder;
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

    @Override
    public String toString() {
        return "PurchaseReturnOrderItem{" +
                "id=" + id +
                ", originalOrderNo='" + originalOrderNo + '\'' +
                ", purchaseReturnOrder=" + purchaseReturnOrder +
                ", sku=" + sku +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", discount=" + discount +
                ", discountedAmount=" + discountedAmount +
                ", taxRate=" + taxRate +
                ", taxAmount=" + taxAmount +
                ", priceIncludingTax=" + priceIncludingTax +
                ", amountIncludingTax=" + amountIncludingTax +
                ", priceExcludingTax=" + priceExcludingTax +
                ", amountExcludingTax=" + amountExcludingTax +
                ", notes='" + notes + '\'' +
                '}';
    }
}
