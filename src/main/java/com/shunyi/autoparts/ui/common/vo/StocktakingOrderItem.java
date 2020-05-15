package com.shunyi.autoparts.ui.common.vo;

import java.util.Date;

/**
 * @Description: 盘点单明细VO
 * @Author: Shunyi
 * @CreateDate: 2020/5/14
 */
public class StocktakingOrderItem {
    /** ID */
    private Long id;
    /** 盘点单 */
    private StocktakingOrder stocktakingOrder;
    /** SKU */
    private SKU sku;
    /** 点货数量 */
    private Integer stocktakingQty;
    /** 点货时间 */
    private Date stocktakingDate;
    /** 备注 */
    private String notes;

    public StocktakingOrderItem() {}

    public StocktakingOrderItem(Long id, StocktakingOrder stocktakingOrder, SKU sku, Integer stocktakingQty, Date stocktakingDate, String notes) {
        this.id = id;
        this.stocktakingOrder = stocktakingOrder;
        this.sku = sku;
        this.stocktakingQty = stocktakingQty;
        this.stocktakingDate = stocktakingDate;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StocktakingOrder getStocktakingOrder() {
        return stocktakingOrder;
    }

    public void setStocktakingOrder(StocktakingOrder stocktakingOrder) {
        this.stocktakingOrder = stocktakingOrder;
    }

    public SKU getSku() {
        return sku;
    }

    public void setSku(SKU sku) {
        this.sku = sku;
    }

    public Integer getStocktakingQty() {
        return stocktakingQty;
    }

    public void setStocktakingQty(Integer stocktakingQty) {
        this.stocktakingQty = stocktakingQty;
    }

    public Date getStocktakingDate() {
        return stocktakingDate;
    }

    public void setStocktakingDate(Date stocktakingDate) {
        this.stocktakingDate = stocktakingDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
