package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;

/**
 * @Description: 价格调整单明细VO
 * @Author: Shunyi
 * @CreateDate: 2020/5/15
 */
public class PriceAdjustmentOrderItem {
    /** 自增ID */
    private Long id;
    /** 调价单 */
    private PriceAdjustmentOrder priceAdjustmentOrder;
    /** SKU */
    private SKU sku;
    /** 调整价 */
    private BigDecimal priceOfAdjustment;
    /** 备注 */
    private String notes;

    public PriceAdjustmentOrderItem() {}

    public PriceAdjustmentOrderItem(Long id, PriceAdjustmentOrder priceAdjustmentOrder, SKU sku, BigDecimal priceOfAdjustment, String notes) {
        this.id = id;
        this.priceAdjustmentOrder = priceAdjustmentOrder;
        this.sku = sku;
        this.priceOfAdjustment = priceOfAdjustment;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PriceAdjustmentOrder getPriceAdjustmentOrder() {
        return priceAdjustmentOrder;
    }

    public void setPriceAdjustmentOrder(PriceAdjustmentOrder priceAdjustmentOrder) {
        this.priceAdjustmentOrder = priceAdjustmentOrder;
    }

    public SKU getSku() {
        return sku;
    }

    public void setSku(SKU sku) {
        this.sku = sku;
    }

    public BigDecimal getPriceOfAdjustment() {
        return priceOfAdjustment;
    }

    public void setPriceOfAdjustment(BigDecimal priceOfAdjustment) {
        this.priceOfAdjustment = priceOfAdjustment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
