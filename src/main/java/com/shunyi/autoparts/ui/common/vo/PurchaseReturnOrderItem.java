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

    public PurchaseReturnOrderItem() {}

    public PurchaseReturnOrderItem(Long id, String originalOrderNo, PurchaseReturnOrder purchaseReturnOrder, SKU sku, Integer quantity, BigDecimal taxRate, BigDecimal taxAmount, BigDecimal priceIncludingTax, BigDecimal amountIncludingTax, BigDecimal priceExcludingTax, BigDecimal amountExcludingTax, String notes, Integer returnableQty) {
        this.id = id;
        this.originalOrderNo = originalOrderNo;
        this.purchaseReturnOrder = purchaseReturnOrder;
        this.sku = sku;
        this.quantity = quantity;
        this.taxRate = taxRate;
        this.taxAmount = taxAmount;
        this.priceIncludingTax = priceIncludingTax;
        this.amountIncludingTax = amountIncludingTax;
        this.priceExcludingTax = priceExcludingTax;
        this.amountExcludingTax = amountExcludingTax;
        this.notes = notes;
        this.returnableQty = returnableQty;
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

    @Override
    public String toString() {
        return "PurchaseReturnOrderItem{" +
                "id=" + id +
                ", originalOrderNo='" + originalOrderNo + '\'' +
                ", purchaseReturnOrder=" + purchaseReturnOrder +
                ", sku=" + sku +
                ", quantity=" + quantity +
                ", taxRate=" + taxRate +
                ", taxAmount=" + taxAmount +
                ", priceIncludingTax=" + priceIncludingTax +
                ", amountIncludingTax=" + amountIncludingTax +
                ", priceExcludingTax=" + priceExcludingTax +
                ", amountExcludingTax=" + amountExcludingTax +
                ", returnableQty=" + returnableQty +
                ", exceptional=" + exceptional +
                ", notes='" + notes + '\'' +
                '}';
    }
}