package com.shunyi.autoparts.ui.common.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 销售单VO
 * @Author: Shunyi
 * @CreateDate: 2020/5/12
 */
public class SalesOrder {
    /** ID */
    private Long id;
    /** 业务单号 */
    private String orderNo;
    /** 单据日期 */
    private Date orderDate;
    /** 仓库 */
    private Warehouse warehouse;
    /** 客户 */
    private Consumer consumer;
    /** 发票类型 */
    private String invoiceType;
    /** 发票号 */
    private String invoiceNo;
    /** 发货方式 */
    private Delivery delivery;
    /** 货运费 */
    private BigDecimal freight;
    /** 到货地点 */
    private String deliveryAddress;
    /** 收货人 */
    private String consignee;
    /** 收货人电话 */
    private String tel;
    /** 货运站收款 */
    private boolean freightStationCollection;
    /** 备注 */
    private String notes;
    /** 经办人 */
    private String operator;
    /** 系统登录账号 */
    private String userName;
    /** 结算方式 */
    private String payment;
    /** 销售数量 */
    private Integer salesQty;
    /** 已入库数量 */
    private Integer warehouseQty;
    /** 退货数量合计 */
    private Integer returnedTotalQty;
    /** 货款金额 */
    private BigDecimal purchaseAmount;
    /** 垫付费用 */
    private BigDecimal disbursementAmount;
    /** 本次优惠 */
    private BigDecimal discountAmount;
    /** 应收总额 */
    private BigDecimal amountReceivable;
    /** 本次收款 */
    private BigDecimal payeeAmount;
    /** 本次欠款 */
    private BigDecimal amountOwed;
    /** 账号 */
    private String account;
    /** 还款金额 */
    private BigDecimal repaymentAmount;
    /** 还款日期 */
    private Date repaymentDate;
    /** 订单状态 */
    private String status;
    /** 创建时间 */
    private Date dateCreated;
    /** 创建者 */
    private String creator;
    /** 删除时间 */
    private Date dateDeleted;
    /** 删除标记 */
    private Boolean deleteFlag;
    /** 删除者 */
    private String deleter;
    /** 搜索日期类型 */
    private String dateType;
    /** 起始日期 */
    private Date fromDate;
    /** 结束日期 */
    private Date toDate;

    public SalesOrder() {}

    public SalesOrder(Long id, String orderNo, Date orderDate, Warehouse warehouse, Consumer consumer, String invoiceType, String invoiceNo, Delivery delivery, BigDecimal freight, String deliveryAddress, String consignee, String tel, boolean freightStationCollection, String notes, String operator, String userName, String payment, Integer salesQty, Integer warehouseQty, Integer returnedTotalQty, BigDecimal purchaseAmount, BigDecimal disbursementAmount, BigDecimal discountAmount, BigDecimal amountReceivable, BigDecimal payeeAmount, BigDecimal amountOwed, String account, BigDecimal repaymentAmount, Date repaymentDate, String status, Date dateCreated, String creator, Date dateDeleted, Boolean deleteFlag, String deleter, String dateType, Date fromDate, Date toDate) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.warehouse = warehouse;
        this.consumer = consumer;
        this.invoiceType = invoiceType;
        this.invoiceNo = invoiceNo;
        this.delivery = delivery;
        this.freight = freight;
        this.deliveryAddress = deliveryAddress;
        this.consignee = consignee;
        this.tel = tel;
        this.freightStationCollection = freightStationCollection;
        this.notes = notes;
        this.operator = operator;
        this.userName = userName;
        this.payment = payment;
        this.salesQty = salesQty;
        this.warehouseQty = warehouseQty;
        this.returnedTotalQty = returnedTotalQty;
        this.purchaseAmount = purchaseAmount;
        this.disbursementAmount = disbursementAmount;
        this.discountAmount = discountAmount;
        this.amountReceivable = amountReceivable;
        this.payeeAmount = payeeAmount;
        this.amountOwed = amountOwed;
        this.account = account;
        this.repaymentAmount = repaymentAmount;
        this.repaymentDate = repaymentDate;
        this.status = status;
        this.dateCreated = dateCreated;
        this.creator = creator;
        this.dateDeleted = dateDeleted;
        this.deleteFlag = deleteFlag;
        this.deleter = deleter;
        this.dateType = dateType;
        this.fromDate = fromDate;
        this.toDate = toDate;
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

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
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

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean isFreightStationCollection() {
        return freightStationCollection;
    }

    public void setFreightStationCollection(boolean freightStationCollection) {
        this.freightStationCollection = freightStationCollection;
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

    public Integer getSalesQty() {
        return salesQty;
    }

    public void setSalesQty(Integer salesQty) {
        this.salesQty = salesQty;
    }

    public Integer getWarehouseQty() {
        return warehouseQty;
    }

    public void setWarehouseQty(Integer warehouseQty) {
        this.warehouseQty = warehouseQty;
    }

    public Integer getReturnedTotalQty() {
        return returnedTotalQty;
    }

    public void setReturnedTotalQty(Integer returnedTotalQty) {
        this.returnedTotalQty = returnedTotalQty;
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

    public BigDecimal getAmountReceivable() {
        return amountReceivable;
    }

    public void setAmountReceivable(BigDecimal amountReceivable) {
        this.amountReceivable = amountReceivable;
    }

    public BigDecimal getPayeeAmount() {
        return payeeAmount;
    }

    public void setPayeeAmount(BigDecimal payeeAmount) {
        this.payeeAmount = payeeAmount;
    }

    public BigDecimal getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(BigDecimal amountOwed) {
        this.amountOwed = amountOwed;
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
}
