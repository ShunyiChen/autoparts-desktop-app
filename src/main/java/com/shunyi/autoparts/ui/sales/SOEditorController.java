package com.shunyi.autoparts.ui.sales;

import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.*;
import com.shunyi.autoparts.ui.products.ProductChooserController;
import com.shunyi.autoparts.ui.purchase.AccountChooserController;
import com.shunyi.autoparts.ui.purchase.InvoiceTypeChooserController;
import com.shunyi.autoparts.ui.purchase.PaymentChooserController;
import com.shunyi.autoparts.ui.consumer.ConsumerChooserController;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 销售单编辑器Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/12
 */
public class SOEditorController {
    private SimpleDateFormat format = new SimpleDateFormat(Constants.PATTERN_DATETIME);
    /** 表单内容只读 */
    private boolean readOnly;
    private Stage dialog;
    private Callback<SalesOrder, String> callback;
    private Callback<TableColumn<SalesOrderItem, String>, TableCell<SalesOrderItem, String>> cellFactory;
    private List<Long> deletedIds = new ArrayList<>();
    /** 采购订单 */
    private SalesOrder so;
    /** 客户 */
    private Consumer consumer;
    /** 发票类型 */
    private InvoiceType invoiceType;
    /** 发货方式 */
    private Delivery delivery;
    /** 结算方式 */
    private Payment payment;
    /** 账号 */
    private Account account;
    /** 仓库 */
    private Warehouse warehouse;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnSubmit;
    /** 仓库 */
    @FXML
    private ComboBox<String> comboBoxWarehouse;
    /** 单据日期 */
    @FXML
    private DatePicker orderDate;
    /** 单号 */
    @FXML
    private TextField txtOrderNo;
    /** 客户编码 */
    @FXML
    private ComboBox<String> comboBoxConsumerCode;
    /** 客户名称 */
    @FXML
    private TextField txtConsumerName;
    /** 联系人 */
    @FXML
    private TextField txtContact;
    /** 电话 */
    @FXML
    private TextField txtPhone;
    /** 备注 */
    @FXML
    private TextField txtNotes;
    /** 发票类型 */
    @FXML
    private ComboBox<String> comboBoxInvoiceType;
    /** 发货方式 */
    @FXML
    private ComboBox<String> comboBoxDelivery;
    /** 到货地点 */
    @FXML
    private TextField txtDeliveryAddress;
    /** 货运编码 */
    @FXML
    private TextField txtFreightCode;
    /** 货运名称 */
    @FXML
    private TextField txtFreightName;
    /** 收货人 */
    @FXML
    private TextField txtConsignee;
    /** 收货人电话 */
    @FXML
    private TextField txtTel;
    /** 发票号 */
    @FXML
    private TextField txtInvoiceNo;
    /** 运费 */
    @FXML
    private TextField txtFreight;
    /** 是否货运站收款 */
    @FXML
    private CheckBox checkboxStationCollection;
    /** 结算方式 */
    @FXML
    private ComboBox<String> comboBoxPayments;
    /** 系统账号 */
    @FXML
    private TextField txtLoginAccount;
    /** 账号 */
    @FXML
    private ComboBox<String> comboBoxAccount;
    /** 货款金额 */
    @FXML
    private TextField txtPurchaseAmount;
    /** 代垫费用 */
    @FXML
    private TextField txtDisbursement;
    /** 本次优惠 */
    @FXML
    private TextField txtDiscountAmount;
    /** 应收总额 */
    @FXML
    private TextField txtAmountReceivable;
    /** 本次收款 */
    @FXML
    private TextField txtPayeeAmount;
    /** 本次欠款 */
    @FXML
    private TextField txtAmountOwed;
    /**经办人 */
    @FXML
    private TextField txtOperator;
    /**当前欠款总额 */
    @FXML
    private Label labelAmountOwing;
    /**信用额度 */
    @FXML
    private Label labelLineOfCredit;
    /**预收款总额 */
    @FXML
    private Label labelTotalAmountReceived;

    /** 采购订单明细表 */
    @FXML
    private TableView<SalesOrderItem> tableView;
    /** 行号 */
    @FXML
    private TableColumn<SalesOrderItem, String> colRowNumber;
    /** SKU编码 */
    @FXML
    private TableColumn<SalesOrderItem, String> colSkuCode;
    /** SKU名称 */
    @FXML
    private TableColumn<SalesOrderItem, String> colSkuName;
    /** 规格 */
    @FXML
    private TableColumn<SalesOrderItem, String> colSpecification;
    /** 单位 */
    @FXML
    private TableColumn<SalesOrderItem, String> colUnit;
    /** 数量 */
    @FXML
    private TableColumn<SalesOrderItem, String> colQty;
    /** 不含税单价 */
    @FXML
    private TableColumn<SalesOrderItem, String> colPriceExcludingTax;
    /** 不含税金额 */
    @FXML
    private TableColumn<SalesOrderItem, String> colAmountExcludingTax;
    /** 仓库 */
    @FXML
    private TableColumn<SalesOrderItem, String> colWarehouse;
    /** 本库现库存数 */
    @FXML
    private TableColumn<SalesOrderItem, String> colStockQty;
    /** 备注 */
    @FXML
    private TableColumn<SalesOrderItem, String> colNotes;
    /** SKU条形码 */
    @FXML
    private TableColumn<SalesOrderItem, String> colSkuBarCode;
    /** 货位 */
    @FXML
    private TableColumn<SalesOrderItem, String> colSlots;
    /** 图片 */
    @FXML
    private TableColumn<SalesOrderItem, String> colPhotos;
    /** 类别 */
    @FXML
    private TableColumn<SalesOrderItem, String> colCategory;
    /** 产品条形码 */
    @FXML
    private TableColumn<SalesOrderItem, String> colBarCode;
    /** 产品编码 */
    @FXML
    private TableColumn<SalesOrderItem, String> colCode;
    /** 产品名称 */
    @FXML
    private TableColumn<SalesOrderItem, String> colName;
    /** 车型 */
    @FXML
    private TableColumn<SalesOrderItem, String> colCar;
    /** 通用车型 */
    @FXML
    private TableColumn<SalesOrderItem, String> colCars;
    /** 产地 */
    @FXML
    private TableColumn<SalesOrderItem, String> colPlace;
    /** 品牌 */
    @FXML
    private TableColumn<SalesOrderItem, String> colBrand;
    /** 英文名 */
    @FXML
    private TableColumn<SalesOrderItem, String> colEnglishName;
    /** 进口 */
    @FXML
    private TableColumn<SalesOrderItem, String> colImported;
    /** 通用件号 */
    @FXML
    private TableColumn<SalesOrderItem, String> colCommonNumber;
    /** 材料 */
    @FXML
    private TableColumn<SalesOrderItem, String> colMaterials;
    /** 所属公司 */
    @FXML
    private TableColumn<SalesOrderItem, String> colCompany;
    /** 包装数量 */
    @FXML
    private TableColumn<SalesOrderItem, String> colPackingQuantity;
    /** 重量 */
    @FXML
    private TableColumn<SalesOrderItem, String> colWeight;
    /** 手册名称 */
    @FXML
    private TableColumn<SalesOrderItem, String> colManual;
    /** 客户 */
    @FXML
    private TableColumn<SalesOrderItem, String> colSupplier;
    /** 一级进价 */
    @FXML
    private TableColumn<SalesOrderItem, String> colPurchasingPrice1;
    /** 二级进价 */
    @FXML
    private TableColumn<SalesOrderItem, String> colPurchasingPrice2;
    /** 三级进价 */
    @FXML
    private TableColumn<SalesOrderItem, String> colPurchasingPrice3;
    /** 一级销价 */
    @FXML
    private TableColumn<SalesOrderItem, String> colSellingPrice1;
    /** 二级销价 */
    @FXML
    private TableColumn<SalesOrderItem, String> colSellingPrice2;
    /** 三级销价 */
    @FXML
    private TableColumn<SalesOrderItem, String> colSellingPrice3;
    /** 最低销价 */
    @FXML
    private TableColumn<SalesOrderItem, String> colBottomPrice;
    /** 外币单位 */
    @FXML
    private TableColumn<SalesOrderItem, String> colForeignCurrencyUnit;
    /** 外币价格 */
    @FXML
    private TableColumn<SalesOrderItem, String> colForeignCurrencyPrice;
    /** 紧缺件 */
    @FXML
    private TableColumn<SalesOrderItem, String> colShortage;
    /** 可用状态 */
    @FXML
    private TableColumn<SalesOrderItem, String> colStatus;
    /** 属性字符串(可隐藏) */
    @FXML
    private TableColumn<SalesOrderItem, String> colProperties;
    /** 折扣% */
    @FXML
    private TableColumn<SalesOrderItem, String> colDiscountPercentage;
    /** 税率%  */
    @FXML
    private TableColumn<SalesOrderItem, String> colTaxRate;
    /** 税额 */
    @FXML
    private TableColumn<SalesOrderItem, String> colTaxAmount;
    /** 含税单价 */
    @FXML
    private TableColumn<SalesOrderItem, String> colPriceIncludingTax;
    /** 含税金额 */
    @FXML
    private TableColumn<SalesOrderItem, String> colAmountIncludingTax;
    /** 库存平均价 */
    @FXML
    private TableColumn<SalesOrderItem, String> colStockAvgPrice;
    /** 库存金额 */
    @FXML
    private TableColumn<SalesOrderItem, String> colStockAmount;
    /** 进货平均价 */
    @FXML
    private TableColumn<SalesOrderItem, String> colPurchaseAvgPrice;
    /** 进货金额 */
    @FXML
    private TableColumn<SalesOrderItem, String> colPurchaseAmount;
    /** 总记录数 */
    @FXML
    private Label labelRecords;
    /** 总数量 */
    @FXML
    private Label labelTotalQty;
    /** 不含税金额 */
    @FXML
    private Label labelTotalAmountWithoutTax;
    /** 含税金额 */
    @FXML
    private Label labelTotalAmountWithTax;
    /** 出库 */
    @FXML
    private CheckBox checkboxWarehousingOut;
    /** 收款 */
    @FXML
    private CheckBox checkboxReceive;

    /**
     * Constructor
     *
     * @param dialog
     * @param callback
     * @param so
     * @param readOnly
     */
    public void initialize(Stage dialog, Callback<SalesOrder, String> callback, SalesOrder so, boolean readOnly) {
        this.dialog = dialog;
        this.callback = callback;
        this.so = so;
        this.readOnly = readOnly;
        btnSubmit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        initCellFactory();
        initInputFields();
        initTable();
        if(so == null) {
            addItem();
        } else {
            //单据日期
            LocalDate localDate = so.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            orderDate.setValue(localDate);
            //单号
            txtOrderNo.setText(so.getOrderNo());
            //客户编码
            comboBoxConsumerCode.setValue(so.getConsumer().getCode());
            //发货方式
            comboBoxDelivery.setValue(so.getDelivery().getName());
            //到货地点
            txtDeliveryAddress.setText(so.getDeliveryAddress());
            //发票类型
            comboBoxInvoiceType.setValue(so.getInvoiceType());
            //发票No
            txtInvoiceNo.setText(so.getInvoiceNo());
            //运费
            txtFreight.setText(so.getFreight()+"");
            //备注
            txtNotes.setText(so.getNotes());
            //经办人
            txtOperator.setText(so.getOperator());
            //结算方式
            comboBoxPayments.setValue(so.getPayment());
            //系统账号
            txtLoginAccount.setText(so.getUserName());
            //货款金额
            txtPurchaseAmount.setText(so.getPurchaseAmount().toString());
            //代垫费用
            txtDisbursement.setText(so.getDisbursementAmount().toString());
            //本次优惠
            txtDiscountAmount.setText(so.getDiscountAmount().toString());
            //应收总额
            txtAmountReceivable.setText(so.getAmountReceivable().toString());
            //本次收款
            txtPayeeAmount.setText(so.getPayeeAmount().toString());
            //本次欠款
            txtAmountOwed.setText(so.getAmountOwed().toString());
            //账号
            comboBoxAccount.setValue(so.getAccount());
            //货运站收款
            checkboxStationCollection.setSelected(so.isFreightStationCollection());

            try {
                SalesOrderItem[] items = HttpClient.GET("/salesOrderItems/order/"+so.getId(), SalesOrderItem[].class);
                for(SalesOrderItem item : items) {
                    addItem(item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            initConsumer();
            initDelivery();
            updateSummary();

            txtTel.setText(so.getTel());
            txtConsignee.setText(so.getConsignee());
        }
        if(readOnly) {
            btnSave.setDisable(true);
            btnSubmit.setDisable(true);
            tableView.setEditable(false);
        }
    }

    /**
     *
     */
    private void initCellFactory() {
        cellFactory = p -> new EditingCell();
    }

    /**
     * 初始化客户
     */
    private void initConsumer() {
        Consumer condition = new Consumer();
        condition.setCode(comboBoxConsumerCode.getValue());
        String json = GoogleJson.GET().toJson(condition);
        String data;
        try {
            data = HttpClient.POST("/consumers/search", json);
            Consumer[] consumers = GoogleJson.GET().fromJson(data, Consumer[].class);
            if(consumers.length > 0) {
                consumer = consumers[0];
                txtConsumerName.setText(consumer.getName());
                txtContact.setText(consumer.getContact());
                txtPhone.setText(consumer.getPhone());
                txtConsignee.setText(consumer.getContact());
                txtTel.setText(consumer.getPhone());
                labelAmountOwing.setText(consumer.getAmountOwing().toString());
                labelLineOfCredit.setText(consumer.getLineOfCredit().toString());
                labelTotalAmountReceived.setText(consumer.getTotalAmountReceived().toString());

            } else {
                txtConsumerName.setText("");
                txtContact.setText("");
                txtPhone.setText("");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化发货方式
     */
    private void initDelivery() {
        Delivery condition = new Delivery();
        condition.setName(comboBoxDelivery.getValue());
        String json = GoogleJson.GET().toJson(condition);
        String data;
        try {
            data = HttpClient.POST("/deliveries/search", json);
            Delivery[] deliveries = GoogleJson.GET().fromJson(data, Delivery[].class);
            if(deliveries.length > 0) {
                delivery = deliveries[0];
                txtFreightCode.setText(delivery.getFreightCode());
                txtFreightName.setText(delivery.getFreightName());
            } else {
                txtFreightCode.setText("");
                txtFreightName.setText("");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void initInputFields() {
        //仓库
        comboBoxWarehouse.getItems().addAll(Env.getInstance().currentStore().getWarehouse().getName());
        comboBoxWarehouse.getSelectionModel().select(0);
        //订单日期
        orderDate.setValue(LocalDate.now());
        //单号
        try {
            User user = HttpClient.GET("/users/username/"+Env.getInstance().currentUser(), User.class);
            String generatedOrderNo = HttpClient.GET("/salesOrders/generation/orderNo/"+user.getId());
            txtOrderNo.setText(generatedOrderNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取客户列表
        try {
            Consumer[] consumers = HttpClient.GET("/consumers", Consumer[].class);
            comboBoxConsumerCode.getItems().addAll(Arrays.asList(consumers).stream().map(e -> e.getCode()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        comboBoxConsumerCode.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                initConsumer();
            }
        });
        comboBoxConsumerCode.setOnAction(e -> {
            initConsumer();
        });
        //获取发票类型列表
        try {
            InvoiceType[] invoiceTypes = HttpClient.GET("/invoiceTypes", InvoiceType[].class);
            comboBoxInvoiceType.getItems().addAll(Arrays.asList(invoiceTypes).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取发货方式列表
        try {
            Delivery[] deliveries = HttpClient.GET("/deliveries", Delivery[].class);
            comboBoxDelivery.getItems().addAll(Arrays.asList(deliveries).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        comboBoxDelivery.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                initDelivery();
            }
        });
        comboBoxDelivery.setOnAction(e -> {
            initDelivery();
        });

        //获取结算方式列表
        try {
            Payment[] payments = HttpClient.GET("/payments", Payment[].class);
            comboBoxPayments.getItems().addAll(Arrays.asList(payments).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取账号列表
        try {
            Account[] accounts = HttpClient.GET("/accounts", Account[].class);
            comboBoxAccount.getItems().addAll(Arrays.asList(accounts).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxConsumerCode);
        new AutoCompleteBox(comboBoxInvoiceType);
        new AutoCompleteBox(comboBoxPayments);
        new AutoCompleteBox(comboBoxAccount);
        new AutoCompleteBox(comboBoxDelivery);
        txtLoginAccount.setText(Env.getInstance().currentUser());
    }

    private void initTable() {
        String css = getClass().getResource("/css/styles.css").toExternalForm();
        tableView.getStylesheets().add(css);
        tableView.setId("my-table");
        tableView.setEditable(true);
        //行号
        colRowNumber.setCellFactory(new RowNumberTableCell<>());
        //SKU编码
        colSkuCode.setCellFactory(TextFieldTableCell.forTableColumn());
        colSkuCode.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getSkuCode() );
            }
        });
        colSkuCode.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SalesOrderItem, String>>) t -> {
            ObservableList<SalesOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SalesOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String skuCode = t.getNewValue();
                        try {
                            String json = HttpClient.GET("/sku/code/"+skuCode);
                            SKU sku = GoogleJson.GET().fromJson(json, SKU.class);
                            if(sku.getId() != null) {
                                selected.setSku(sku);
                                selected.setQuantity(0);
                                data.set(t.getTablePosition().getRow(), selected);
                                updateSummary();
                            } else {
                                Platform.runLater(() -> {
                                    openProductChooser();
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        //SKU名称
        colSkuName.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getSkuName() );
            }
        });
        //规格
        colSpecification.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getSpecification() );
            }
        });
        //单位
        colUnit.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getUnit().getName());
            }
        });
        //数量
        colQty.setCellFactory(cellFactory);
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQty.setOnEditCommit(t -> {
            ObservableList<SalesOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SalesOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        if(NumberValidationUtils.isPositiveInteger(newValue)) {
                            //数量
                            selected.setQuantity(Integer.parseInt(newValue));
                            //不含税金额
                            selected.setAmountExcludingTax(selected.getPriceExcludingTax().multiply(new BigDecimal(selected.getQuantity())).setScale(2, RoundingMode.HALF_UP));

                            //税额=不含税价*税率
                            BigDecimal rate = selected.getTaxRate().divide(new BigDecimal(100));
                            selected.setTaxAmount(rate.multiply(selected.getPriceExcludingTax()).setScale(2, RoundingMode.HALF_UP));
                            //不含税价+税额=含税价（总金额）
                            selected.setPriceIncludingTax(selected.getPriceExcludingTax().add(selected.getTaxAmount()).setScale(2, RoundingMode.HALF_UP));
                            //含税金额
                            selected.setAmountIncludingTax(new BigDecimal(selected.getQuantity()).multiply(selected.getPriceIncludingTax()).setScale(2, RoundingMode.HALF_UP));

                            data.set(t.getTablePosition().getRow(), selected);
                            updateSummary();
                        }
                    }
                }
            }
        });
        //不含税单价
        colPriceExcludingTax.setCellFactory(cellFactory);
        colPriceExcludingTax.setCellValueFactory(param -> {
            if(param.getValue().getPriceExcludingTax() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getPriceExcludingTax().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        colPriceExcludingTax.setOnEditCommit(t -> {
            ObservableList<SalesOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SalesOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        if(NumberValidationUtils.isRealNumber(newValue)) {
                            //不含税单价
                            selected.setPriceExcludingTax(new BigDecimal(newValue).setScale(2, RoundingMode.HALF_UP));
                            //不含税金额
                            selected.setAmountExcludingTax(selected.getPriceExcludingTax().multiply(new BigDecimal(selected.getQuantity())).setScale(2, RoundingMode.HALF_UP));

                            //税额=不含税价*税率
                            BigDecimal rate = selected.getTaxRate().divide(new BigDecimal(100));
                            selected.setTaxAmount(rate.multiply(selected.getPriceExcludingTax()).setScale(2, RoundingMode.HALF_UP));
                            //不含税价+税额=含税价（总金额）
                            selected.setPriceIncludingTax(selected.getPriceExcludingTax().add(selected.getTaxAmount()).setScale(2, RoundingMode.HALF_UP));
                            //含税金额
                            selected.setAmountIncludingTax(new BigDecimal(selected.getQuantity()).multiply(selected.getPriceIncludingTax()).setScale(2, RoundingMode.HALF_UP));

                            data.set(t.getTablePosition().getRow(), selected);
                            updateSummary();
                        }
                    }
                }
            }
        });
        //不含税金额
        colAmountExcludingTax.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getAmountExcludingTax().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //品牌
        colBrand.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getBrand() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getBrand().getName());
            }
        });
        //车型
        colCar.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getCar() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getCar().getName());
            }
        });
        //货位
        colSlots.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                StringBuilder stb = new StringBuilder();
                param.getValue().getSku().getSkuSlotMappingSet().stream().sorted(Comparator.comparingLong(e -> e.getSlot().getId())).forEach(e -> {
                    stb.append(e.getSlot().getName());
                });
                return new SimpleObjectProperty<>(stb.toString());
            }
        });
        //备注
        colNotes.setCellFactory(cellFactory);
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        colNotes.setOnEditCommit(t -> {
            ObservableList<SalesOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SalesOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        selected.setNotes(newValue);
                        data.set(t.getTablePosition().getRow(), selected);
                    }
                }
            }
        });
        //仓库
        colWarehouse.setCellValueFactory(param -> {
            return new SimpleObjectProperty<>(Env.getInstance().currentStore().getWarehouse().getName());
        });
        //本库现库存数
        colStockQty.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getStockQty().toString());
            }
        });
        //SKU条形码
        colSkuBarCode.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getSkuBarCode());
            }
        });
        //图片
        colPhotos.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                StringBuilder stb = new StringBuilder();
                param.getValue().getSku().getPhotos().stream().sorted(Comparator.comparingLong(e -> e.getId())).forEach(e -> {
                    stb.append(e.getName());
                });
                return new SimpleObjectProperty<>(stb.toString());
            }
        });
        //类别
        colCategory.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getCategory() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getCategory().getName());
            }
        });
        //产品编码
        colCode.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getCode());
            }
        });
        //产品名称
        colName.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getName());
            }
        });
        //通用车型
        colCars.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getRelevantModels());
            }
        });
        //产地
        colPlace.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getPlace() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getPlace().getName());
            }
        });
        //英文名
        colEnglishName.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getEnglishName());
            }
        });
        //进口
        colImported.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getImported() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getImported().getName());
            }
        });
        //通用件号
        colCommonNumber.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getCommonNumber());
            }
        });
        //材料
        colMaterials.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getMaterials());
            }
        });
        //所属公司
        colCompany.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getCompany() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getCompany().getName());
            }
        });
        //包装数量
        colPackingQuantity.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("0");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getPackingQuantity().toString());
            }
        });
        //重量
        colWeight.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getWeight());
            }
        });
        //手册
        colManual.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getMaterials());
            }
        });
        //供应商
        colSupplier.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getSupplier() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getSupplier().getName());
            }
        });
        //一级进价
        colPurchasingPrice1.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getPurchasingPrice1().toString());
            }
        });
        //二级进价
        colPurchasingPrice2.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getPurchasingPrice2().toString());
            }
        });
        //三级进价
        colPurchasingPrice3.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getPurchasingPrice3().toString());
            }
        });
        //一级销价
        colSellingPrice1.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getSellingPrice1().toString());
            }
        });
        //二级销价
        colSellingPrice2.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getSellingPrice2().toString());
            }
        });
        //三级销价
        colSellingPrice3.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getSellingPrice3().toString());
            }
        });
        //底价
        colBottomPrice.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getBottomPrice().toString());
            }
        });
        //外币单位
        colForeignCurrencyUnit.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getForeignCurrencyUnit());
            }
        });
        //外币价格
        colForeignCurrencyPrice.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getForeignCurrencyPrice());
            }
        });
        //紧缺件
        colShortage.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getShortage()?"紧缺":"");
            }
        });
        //状态
        colStatus.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getEnabled()?Constants.AVAILABLE:Constants.UNAVAILABLE);
            }
        });
        //SKU属性
        colProperties.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProperties());
            }
        });
        //折扣%
        colDiscountPercentage.setCellValueFactory(param -> {
            if(param.getValue().getSku() != null && param.getValue().getSku().getDiscount() != null) {
                return new SimpleObjectProperty<>(param.getValue().getSku().getDiscount().toString());
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
        //税率%
        colTaxRate.setCellFactory(cellFactory);
        colTaxRate.setCellValueFactory(param -> {
            if(param.getValue().getTaxRate() != null) {
                return new SimpleObjectProperty<>(param.getValue().getTaxRate().setScale(2, RoundingMode.HALF_UP).toString());
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
        colTaxRate.setOnEditCommit(t -> {
            ObservableList<SalesOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SalesOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        if(NumberValidationUtils.isRealNumber(newValue)) {
                            //税率
                            selected.setTaxRate(new BigDecimal(newValue));
                            //税额=不含税价*税率
                            BigDecimal rate = selected.getTaxRate().divide(new BigDecimal(100));
                            selected.setTaxAmount(rate.multiply(selected.getPriceExcludingTax()).setScale(2, RoundingMode.HALF_UP));
                            //不含税价+税额=含税价（总金额）
                            selected.setPriceIncludingTax(selected.getPriceExcludingTax().add(selected.getTaxAmount()).setScale(2, RoundingMode.HALF_UP));
                            //含税金额
                            selected.setAmountIncludingTax(new BigDecimal(selected.getQuantity()).multiply(selected.getPriceIncludingTax()).setScale(2, RoundingMode.HALF_UP));
                            data.set(t.getTablePosition().getRow(), selected);
                            updateSummary();
                        }
                    }
                }
            }
        });
        //税额
        colTaxAmount.setCellValueFactory(param -> {
            if(param.getValue().getTaxAmount() != null) {
                return new SimpleObjectProperty<>(param.getValue().getTaxAmount().setScale(2, RoundingMode.HALF_UP).toString());
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
        //含税单价
        colPriceIncludingTax.setCellFactory(cellFactory);
        colPriceIncludingTax.setCellValueFactory(param -> {
            if(param.getValue().getPriceIncludingTax() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getPriceIncludingTax().toString());
            }
        });
        colPriceIncludingTax.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SalesOrderItem, String>>) t -> {
            ObservableList<SalesOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SalesOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String priceIncludingTaxStr = t.getNewValue();
                        if(NumberValidationUtils.isRealNumber(priceIncludingTaxStr)) {
                            //含税单价
                            selected.setPriceIncludingTax(new BigDecimal(priceIncludingTaxStr).setScale(2, RoundingMode.HALF_UP));
                            //含税金额
                            selected.setAmountIncludingTax(new BigDecimal(selected.getQuantity()).multiply(selected.getPriceIncludingTax()).setScale(2, RoundingMode.HALF_UP));
                            data.set(t.getTablePosition().getRow(), selected);
                            updateSummary();
                        }
                    }
                }
            }
        });
        //含税金额
        colAmountIncludingTax.setCellValueFactory(param -> {
            if(param.getValue().getSku() != null && param.getValue().getAmountIncludingTax() != null) {
                return new SimpleObjectProperty<>(param.getValue().getAmountIncludingTax().setScale(2, RoundingMode.HALF_UP).toString());
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
        //库存平均价
        colStockAvgPrice.setCellValueFactory(param -> {
            if(param.getValue().getSku() != null && param.getValue().getSku().getStockAvgPrice() != null) {
                return new SimpleObjectProperty<>(param.getValue().getSku().getStockAvgPrice().toString());
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
        //库存金额
        colStockAmount.setCellValueFactory(param -> {
            if(param.getValue().getSku() != null && param.getValue().getSku().getStockAmount() != null) {
                return new SimpleObjectProperty<>(param.getValue().getSku().getStockAmount().toString());
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
        //进货平均价
        colPurchaseAvgPrice.setCellValueFactory(param -> {
            if(param.getValue().getSku() != null && param.getValue().getSku().getPurchaseAvgPrice() != null) {
                return new SimpleObjectProperty<>(param.getValue().getSku().getPurchaseAvgPrice().toString());
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
        //进货金额
        colPurchaseAmount.setCellValueFactory(param -> {
            if(param.getValue().getSku() != null && param.getValue().getSku().getPurchaseAmount() != null) {
                return new SimpleObjectProperty<>(param.getValue().getSku().getPurchaseAmount().toString());
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
    }

    private void updateSummary() {
        int totalRecords = 0;
        BigDecimal totalQty = BigDecimal.ZERO;
        BigDecimal totalAmountWithoutTax = BigDecimal.ZERO;
        BigDecimal totalAmountWithTax = BigDecimal.ZERO;
        ObservableList<SalesOrderItem> list = tableView.getItems();
        for(SalesOrderItem item : list) {
            if(item.getSku() != null) {
                totalRecords++;
                totalQty = totalQty.add(new BigDecimal(item.getQuantity()));
                totalAmountWithoutTax = totalAmountWithoutTax.add(item.getAmountExcludingTax());
                totalAmountWithTax = totalAmountWithTax.add(item.getAmountIncludingTax());
            }
        }
        labelRecords.setText(totalRecords+"");
        labelTotalQty.setText(totalQty.toString());
        //不含税金额
        labelTotalAmountWithoutTax.setText(totalAmountWithoutTax.toString());
        //含税金额
        labelTotalAmountWithTax.setText(totalAmountWithTax.toString());
        //货款金额
        txtPurchaseAmount.setText(totalAmountWithoutTax.toString());
        //应收总额
        txtAmountReceivable.setText(totalAmountWithoutTax.toString());
    }

    @FXML
    private void openConsumerChooser() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/consumer/ConsumerChooser.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage subStage = new Stage();
        ConsumerChooserController controller = loader.getController();
        Callback<Consumer, String> callback = returnedConsumer -> {
            if(returnedConsumer != null) {
                consumer = returnedConsumer;
                comboBoxConsumerCode.setValue(consumer.getCode());
                txtConsumerName.setText(consumer.getName());
                txtContact.setText(consumer.getContact());
                txtPhone.setText(consumer.getPhone());
                txtConsignee.setText(consumer.getContact());
                txtTel.setText(consumer.getPhone());
            }
            return null;
        };
        controller.initialize(subStage, callback, consumer);
        subStage.setTitle("选择客户");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    @FXML
    private void openInvoiceTypeChooser() {
        Callback<InvoiceType, String> callback = new Callback<InvoiceType, String>() {
            @Override
            public String call(InvoiceType param) {
                invoiceType = param;
                comboBoxInvoiceType.setValue(invoiceType.getName());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/InvoiceTypeChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        InvoiceTypeChooserController controller = loader.getController();
        controller.initialize(dialog, callback, invoiceType);
        dialog.setTitle("发票类型选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openDeliveryChooser() {
        Callback<Delivery, String> callback = new Callback<Delivery, String>() {
            @Override
            public String call(Delivery param) {
                delivery = param;
                comboBoxDelivery.setValue(delivery.getName());
                txtFreightCode.setText(delivery.getFreightCode());
                txtFreightName.setText(delivery.getFreightName());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/sales/DeliveryChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        DeliveryChooserController controller = loader.getController();
        controller.initialize(dialog, callback, delivery);
        dialog.setTitle("发货方式选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openPaymentChooser() {
        Callback<Payment, String> callback = new Callback<Payment, String>() {
            @Override
            public String call(Payment param) {
                payment = param;
                comboBoxPayments.setValue(payment.getName());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/PaymentChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        PaymentChooserController controller = loader.getController();
        controller.initialize(dialog, callback);
        dialog.setTitle("结算方式选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openAccountChooser() {
        Callback<Account, String> callback = new Callback<Account, String>() {
            @Override
            public String call(Account param) {
                account = param;
                comboBoxAccount.setValue(account.getName());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/AccountChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        AccountChooserController controller = loader.getController();
        controller.initialize(dialog, callback);
        dialog.setTitle("账号选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    /**
     *
     * @param item
     */
    private void addItem(SalesOrderItem item) {
        tableView.getItems().add(item);
        tableView.refresh();
        tableView.getSelectionModel().select(item);
    }

    @FXML
    private void addItem() {
        SalesOrderItem item = new SalesOrderItem(Constants.ID, null, null, Constants.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,"");
        addItem(item);
    }

    @FXML
    private void removeItem() {
        SalesOrderItem selectedItem = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selectedItem);
        tableView.refresh();
        //记录删除ID,用于保存更新
        deletedIds.add(selectedItem.getId());

        updateSummary();
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    /**
     * 确认提交
     */
    private void confirmAndSubmit() {
        String header = "销售单: "+txtOrderNo.getText()+"\n请谨慎确定以下数据:\n";
        StringBuilder content = new StringBuilder();
        content.append("===========================================\n");
        content.append("\n应付货款金额: "+txtPurchaseAmount.getText()+" 元 \n");
        content.append("\n代垫费用金额: "+txtDisbursement.getText()+" 元 \n");
        content.append("\n本次优惠金额: "+txtDiscountAmount.getText()+" 元 \n");
        content.append("\n总计应收金额: "+txtAmountReceivable.getText()+" 元 \n\n");

        content.append("\n本次实收金额: "+txtPayeeAmount.getText()+" 元 \n");
        content.append("\n用于货款结算: "+txtPurchaseAmount.getText()+" 元 \n");
        content.append("\n用于代垫费用: "+txtDisbursement.getText()+" 元 \n");
        content.append("\n本次欠款: "+txtAmountOwed.getText()+" 元 \n\n");

        content.append("\n结算同时自动出库: "+(checkboxWarehousingOut.isSelected()?"是":"否")+"\n\n");

        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText(header);
        alertConfirm.setContentText(content.toString());
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
            saveOrUpdate(Constants.CLOSED);
            dialog.close();
        });
    }

    private boolean validate() {
        try {
            warehouse = HttpClient.GET("/warehouses/name/"+comboBoxWarehouse.getValue(), Warehouse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(orderDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("单据日期不能空");
            alert.show();
            return false;
        }
        else if(warehouse.getId() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("不存在的仓库");
            alert.show();
            return false;
        }
        else if(consumer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("不存在的客户");
            alert.show();
            return false;
        }
        else if(comboBoxInvoiceType.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("不存在的发票类型");
            alert.show();
            return false;
        }
        else if(txtOperator.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("经办人不能空");
            alert.show();
            return false;
        }
        else if(comboBoxPayments.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("不存在的结算方式");
            alert.show();
            return false;
        }
        else if(txtPayeeAmount.getText().equals("") || !NumberValidationUtils.isRealNumber(txtPayeeAmount.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("本次收款输入无效");
            alert.show();
            return false;
        }
        else if(comboBoxAccount.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("不存在的账号");
            alert.show();
            return false;
        }
        return true;
    }

    /**
     * 插入或更新
     *
     * @param status
     */
    private void saveOrUpdate(String status) {
        if(so == null) {
            //插入新记录
            so = new SalesOrder();
            LocalDate localDate = orderDate.getValue();
            Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            //订单日期
            so.setOrderDate(date);
            //单号
            so.setOrderNo(txtOrderNo.getText());
            //仓库
            so.setWarehouse(warehouse);
            //客户
            so.setConsumer(consumer);
            //发票类型
            so.setInvoiceType(comboBoxInvoiceType.getValue());
            //发票号
            so.setInvoiceNo(txtInvoiceNo.getText());
            //发货方式
            so.setDelivery(delivery);
            //到货地点
            so.setDeliveryAddress(txtDeliveryAddress.getText());
            //货运费
            so.setFreight(NumberValidationUtils.isRealNumber(txtFreight.getText())?new BigDecimal(txtFreight.getText()):BigDecimal.ZERO);
            //收货人
            so.setConsignee(txtConsignee.getText());
            //收货人电话
            so.setTel(txtTel.getText());
            //货运站收款
            so.setFreightStationCollection(checkboxStationCollection.isSelected());
            //备注
            so.setNotes(txtNotes.getText());
            //经办人
            so.setOperator(txtOperator.getText());
            //操作员
            so.setUserName(txtLoginAccount.getText());
            //结算方式
            so.setPayment(comboBoxPayments.getValue());
            //销售数量
            so.setSalesQty(Integer.parseInt(labelTotalQty.getText()));
            //已出库数量
            so.setOutboundQty(status.equals(Constants.CLOSED)?so.getSalesQty():0);
            //退货数量合计
            so.setReturnedTotalQty(0);
            //含税金额
            so.setTotalAmountIncludingTax(new BigDecimal(labelTotalAmountWithTax.getText()));
            //未税金额
            so.setTotalAmountExcludingTax(new BigDecimal(labelTotalAmountWithoutTax.getText()));
            //货款金额
            so.setPurchaseAmount(NumberValidationUtils.isRealNumber(txtPurchaseAmount.getText())?new BigDecimal(txtPurchaseAmount.getText()):BigDecimal.ZERO);
            //代垫费用
            so.setDisbursementAmount(NumberValidationUtils.isRealNumber(txtDisbursement.getText())?new BigDecimal(txtDisbursement.getText()):BigDecimal.ZERO);
            //本次优惠
            so.setDiscountAmount(NumberValidationUtils.isRealNumber(txtDiscountAmount.getText())?new BigDecimal(txtDiscountAmount.getText()):BigDecimal.ZERO);
            //应收总额
            so.setAmountReceivable(NumberValidationUtils.isRealNumber(txtAmountReceivable.getText())?new BigDecimal(txtAmountReceivable.getText()):BigDecimal.ZERO);
            //总额
            so.setTotalAmount(so.getTotalAmountIncludingTax());
            //已开票总额
            so.setTotalInvoiceAmount(so.getTotalAmountIncludingTax());
            //本次收款
            so.setPayeeAmount(NumberValidationUtils.isRealNumber(txtPayeeAmount.getText())?new BigDecimal(txtPayeeAmount.getText()):BigDecimal.ZERO);
            //本次欠款
            so.setAmountOwed(NumberValidationUtils.isRealNumber(txtAmountOwed.getText())?new BigDecimal(txtAmountOwed.getText()):BigDecimal.ZERO);
            //账号
            so.setAccount(comboBoxAccount.getValue());
            //订单状态
            so.setStatus(status);
            //创建人
            so.setCreator(Env.getInstance().currentUser());
            //出库
            so.setWarehousingOut(checkboxWarehousingOut.isSelected());
            //收款
            so.setGathering(checkboxReceive.isSelected());

            //创建采购订单对象
            String json = GoogleJson.GET().toJson(so);
            try {
                String idStr = HttpClient.POST("/salesOrders", json);
                so.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //创建采购订单明细
            ObservableList<SalesOrderItem> items = tableView.getItems();
            items.forEach(e -> {
                if(e.getSku() != null) {
                    e.setSalesOrder(so);
                    String data = GoogleJson.GET().toJson(e);
                    try {
                        String idStr = HttpClient.POST("/salesOrderItems", data);
                        e.setId(Long.valueOf(idStr));
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    //执行结算
                    execute(status, checkboxWarehousingOut.isSelected(), e);
                }
            });
            callback.call(so);
        } else {
            //更新原有记录
            SalesOrder so = new SalesOrder();
            so.setId(this.so.getId());
            LocalDate localDate = orderDate.getValue();
            Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            //订单日期
            so.setOrderDate(date);
            //单号
            so.setOrderNo(txtOrderNo.getText());
            //仓库
            so.setWarehouse(warehouse);
            //客户
            so.setConsumer(consumer);
            //发票类型
            so.setInvoiceType(comboBoxInvoiceType.getValue());
            //发票号
            so.setInvoiceNo(txtInvoiceNo.getText());
            //发货方式
            so.setDelivery(delivery);
            //到货地点
            so.setDeliveryAddress(txtDeliveryAddress.getText());
            //货运费
            so.setFreight(NumberValidationUtils.isRealNumber(txtFreight.getText())?new BigDecimal(txtFreight.getText()):BigDecimal.ZERO);
            //收货人
            so.setConsignee(txtConsignee.getText());
            //收货人电话
            so.setTel(txtTel.getText());
            //货运站收款
            so.setFreightStationCollection(checkboxStationCollection.isSelected());
            //备注
            so.setNotes(txtNotes.getText());
            //经办人
            so.setOperator(txtOperator.getText());
            //操作员
            so.setUserName(txtLoginAccount.getText());
            //结算方式
            so.setPayment(comboBoxPayments.getValue());
            //销售数量
            so.setSalesQty(Integer.parseInt(labelTotalQty.getText()));
            //已出库数量
            so.setOutboundQty(status.equals(Constants.CLOSED)?so.getSalesQty():0);
            //退货数量合计
            so.setReturnedTotalQty(0);
            //含税金额
            so.setTotalAmountIncludingTax(new BigDecimal(labelTotalAmountWithTax.getText()));
            //未税金额
            so.setTotalAmountExcludingTax(new BigDecimal(labelTotalAmountWithoutTax.getText()));
            //货款金额
            so.setPurchaseAmount(NumberValidationUtils.isRealNumber(txtPurchaseAmount.getText())?new BigDecimal(txtPurchaseAmount.getText()):BigDecimal.ZERO);
            //代垫费用
            so.setDisbursementAmount(NumberValidationUtils.isRealNumber(txtDisbursement.getText())?new BigDecimal(txtDisbursement.getText()):BigDecimal.ZERO);
            //本次优惠
            so.setDiscountAmount(NumberValidationUtils.isRealNumber(txtDiscountAmount.getText())?new BigDecimal(txtDiscountAmount.getText()):BigDecimal.ZERO);
            //应收总额
            so.setAmountReceivable(NumberValidationUtils.isRealNumber(txtAmountReceivable.getText())?new BigDecimal(txtAmountReceivable.getText()):BigDecimal.ZERO);
            //总额
            so.setTotalAmount(so.getTotalAmountIncludingTax());
            //已开票总额
            so.setTotalInvoiceAmount(so.getTotalAmountIncludingTax());
            //本次收款
            so.setPayeeAmount(NumberValidationUtils.isRealNumber(txtPayeeAmount.getText())?new BigDecimal(txtPayeeAmount.getText()):BigDecimal.ZERO);
            //本次欠款
            so.setAmountOwed(NumberValidationUtils.isRealNumber(txtAmountOwed.getText())?new BigDecimal(txtAmountOwed.getText()):BigDecimal.ZERO);
            //账号
            so.setAccount(comboBoxAccount.getValue());
            //订单状态
            so.setStatus(status);
            //创建人
            so.setCreator(Env.getInstance().currentUser());
            //出库
            so.setWarehousingOut(checkboxWarehousingOut.isSelected());
            //收款
            so.setGathering(checkboxReceive.isSelected());

            String json = GoogleJson.GET().toJson(so);
            try {
                HttpClient.PUT("/salesOrders/"+so.getId(), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //更新或创建采购订单明细
            ObservableList<SalesOrderItem> items = tableView.getItems();
            items.forEach(e -> {
                if(e.getSku() != null) {
                    e.setSalesOrder(so);
                    String data = GoogleJson.GET().toJson(e);
                    if(e.getId() == 0L) {
                        try {
                            String idStr = HttpClient.POST("/salesOrderItems", data);
                            e.setId(Long.valueOf(idStr));
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        try {
                            HttpClient.PUT("/salesOrderItems/"+e.getId(), data);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    //执行结算
                    execute(status, checkboxWarehousingOut.isSelected(), e);
                }
            });
            //删除已经移除的行
            deletedIds.forEach(SalesOrderItemId -> {
                try {
                    HttpClient.DELETE("/salesOrderItems/"+SalesOrderItemId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            callback.call(so);
        }
    }

    /**
     * 执行结算
     */
    private void execute(String orderStatus, boolean warehousingOut, SalesOrderItem item) {
        //如果订单状态为已结算，则执行结算
        if(orderStatus.equals(Constants.CLOSED)) {
            //是否出库
            if(warehousingOut) {
                //以下更新SKU库存数量和进货平均单价
                SKU sku = null;
                try {
                    sku = HttpClient.GET("/sku/"+item.getSku().getId(), SKU.class);
//                    BigDecimal purchaseAvgPrice = BigDecimal.ZERO;
//                    BigDecimal totalPurchaseAmount = BigDecimal.ZERO;
//                    int totalQty = 0;
//                    String json = GoogleJson.GET().toJson(item);
//                    String results = HttpClient.POST("/purchaseOrderItems/search", json);
//                    PurchaseOrderItem[] items = GoogleJson.GET().fromJson(results, PurchaseOrderItem[].class);
//                    //平均进货价格计算公式， 平均价格 = (10M + 9N)/(M+N)，（注10元进货M件，9元进货N件）
//                    for(PurchaseOrderItem i : items) {
//                        totalPurchaseAmount = totalPurchaseAmount.add(i.getPriceExcludingTax().multiply(new BigDecimal(i.getQuantity())));
//                        totalQty += i.getQuantity();
//                    }
//                    if(totalQty > 0) {
//                        purchaseAvgPrice = totalPurchaseAmount.divide(new BigDecimal(totalQty), 2, RoundingMode.HALF_UP);
//                        sku.setPurchaseAvgPrice(purchaseAvgPrice);
//                    }
                    sku.setStockQty(sku.getStockQty() - item.getQuantity());
                    String json = GoogleJson.GET().toJson(sku);
                    HttpClient.PUT("/sku/"+sku.getId(), json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void save() {
        if(validate()) {
            saveOrUpdate(Constants.UNCLOSED);
            dialog.close();
        }
    }

    @FXML
    private void submit() {
        if(validate()) {
            confirmAndSubmit();
        }
    }

    private void openProductChooser() {
        Callback<SKU, String> callback = new Callback<SKU, String>() {

            @Override
            public String call(SKU sku) {
                SalesOrderItem item = tableView.getSelectionModel().getSelectedItem();
                item.setSku(sku);
                item.setQuantity(0);
//                item.setPriceExcludingTax(sku.getAvgPrice());
                item.setAmountExcludingTax(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));

                tableView.refresh();
                tableView.getSelectionModel().select(item);
                updateSummary();
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/ProductChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        Stage dialog = new Stage();
        ProductChooserController skuChooserController = loader.getController();
        skuChooserController.initialize(dialog, callback);
        dialog.setTitle("配件选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(true);
        dialog.setMaximized(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }
}
