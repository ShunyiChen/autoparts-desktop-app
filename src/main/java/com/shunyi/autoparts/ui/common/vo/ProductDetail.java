package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;

/**
 * @Description: 库存商品明细账
 * @Author: 陈顺谊
 * @CreateDate: 2020/4/1 1:42
 * @Version: 1.0
 */
public class ProductDetail {
    /** 配件编码 */
    private Long productCode;
    /** 配件名称 */
    private String productName;
    /** SKU编码 */
    private String skuCode;
    /** SKU名称 */
    private String skuName;
    /** SKU规格 */
    private String spec;
    /** 进货数量 */
    private Integer qty1;
    /** 进货单价 */
    private BigDecimal price1;
    /** 进货合计金额 */
    private BigDecimal sum1;
    /** 销售数量 */
    private Integer qty2;
    /** 销售单价 */
    private BigDecimal price2;
    /** 销售合计金额 */
    private BigDecimal sum2;
    /** 结存数量 */
    private Integer qty3;
    /** 结存单价 */
    private BigDecimal price3;
    /** 结存合计金额 */
    private BigDecimal sum3;

    public ProductDetail() {}

    public ProductDetail(Long productCode, String productName, String skuCode, String skuName, String spec, Integer qty1, BigDecimal price1, BigDecimal sum1, Integer qty2, BigDecimal price2, BigDecimal sum2, Integer qty3, BigDecimal price3, BigDecimal sum3) {
        this.productCode = productCode;
        this.productName = productName;
        this.skuCode = skuCode;
        this.skuName = skuName;
        this.spec = spec;
        this.qty1 = qty1;
        this.price1 = price1;
        this.sum1 = sum1;
        this.qty2 = qty2;
        this.price2 = price2;
        this.sum2 = sum2;
        this.qty3 = qty3;
        this.price3 = price3;
        this.sum3 = sum3;
    }

    public Long getProductCode() {
        return productCode;
    }

    public void setProductCode(Long productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getQty1() {
        return qty1;
    }

    public void setQty1(Integer qty1) {
        this.qty1 = qty1;
    }

    public BigDecimal getPrice1() {
        return price1;
    }

    public void setPrice1(BigDecimal price1) {
        this.price1 = price1;
    }

    public BigDecimal getSum1() {
        return sum1;
    }

    public void setSum1(BigDecimal sum1) {
        this.sum1 = sum1;
    }

    public Integer getQty2() {
        return qty2;
    }

    public void setQty2(Integer qty2) {
        this.qty2 = qty2;
    }

    public BigDecimal getPrice2() {
        return price2;
    }

    public void setPrice2(BigDecimal price2) {
        this.price2 = price2;
    }

    public BigDecimal getSum2() {
        return sum2;
    }

    public void setSum2(BigDecimal sum2) {
        this.sum2 = sum2;
    }

    public Integer getQty3() {
        return qty3;
    }

    public void setQty3(Integer qty3) {
        this.qty3 = qty3;
    }

    public BigDecimal getPrice3() {
        return price3;
    }

    public void setPrice3(BigDecimal price3) {
        this.price3 = price3;
    }

    public BigDecimal getSum3() {
        return sum3;
    }

    public void setSum3(BigDecimal sum3) {
        this.sum3 = sum3;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "productCode=" + productCode +
                ", productName='" + productName + '\'' +
                ", skuCode='" + skuCode + '\'' +
                ", skuName='" + skuName + '\'' +
                ", spec='" + spec + '\'' +
                ", qty1=" + qty1 +
                ", price1=" + price1 +
                ", sum1=" + sum1 +
                ", qty2=" + qty2 +
                ", price2=" + price2 +
                ", sum2=" + sum2 +
                ", qty3=" + qty3 +
                ", price3=" + price3 +
                ", sum3=" + sum3 +
                '}';
    }
}
