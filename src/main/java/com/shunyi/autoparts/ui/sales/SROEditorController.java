package com.shunyi.autoparts.ui.sales;

import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.*;
import com.shunyi.autoparts.ui.products.ProductChooserController;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 销售退货单编辑器Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/9
 */
public class SROEditorController {
    private SimpleDateFormat format = new SimpleDateFormat(Constants.PATTERN_DATETIME);
    /** 表单内容只读 */
    private boolean readOnly;
    private Stage dialog;
    private Callback<SalesReturnOrder, String> callback;
    private Callback<TableColumn<SalesReturnOrderItem, String>, TableCell<SalesReturnOrderItem, String>> cellFactory;
    private List<Long> deletedIds = new ArrayList<>();
    /** 采购退货单 */
    private SalesReturnOrder pro;
    /** 客户 */
    private Consumer consumer;
    /** 发票类型 */
    private InvoiceType invoiceType;
    /** 结算方式 */
    private Payment payment;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnSubmit;
    /** 单据日期 */
    @FXML
    private DatePicker orderDate;
    /** 单号 */
    @FXML
    private TextField txtOrderNo;
    /** 客户选择按钮 */
    @FXML
    private Button btnConsumerChooser;
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
    /** 发票类型 */
    @FXML
    private ComboBox<String> comboBoxInvoiceType;
    /** 发票号 */
    @FXML
    private TextField txtInvoiceNo;
    /** 运费 */
    @FXML
    private TextField txtFreight;
    /** 备注 */
    @FXML
    private TextField txtNotes;
    /** 结算方式 */
    @FXML
    private ComboBox<String> comboBoxPayments;
    /** 系统账号 */
    @FXML
    private TextField txtLoginAccount;
    /**经办人 */
    @FXML
    private TextField txtOperator;
    /** 采购订单明细表 */
    @FXML
    private TableView<SalesReturnOrderItem> tableView;
    /** 行号 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colRowNumber;
    /** 原进货单号 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colOriginalOrderNo;
    /** SKU编码 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colSkuCode;
    /** SKU名称 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colSkuName;
    /** 规格 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colSpecification;
    /** 单位 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colUnit;
    /** 数量 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colQty;
    /** 不含税单价 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colPriceExcludingTax;
    /** 不含税金额 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colAmountExcludingTax;
    /** 仓库 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colWarehouse;
    /** 本库现库存数 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colStockQty;
    /** 备注 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colNotes;
    /** SKU条形码 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colSkuBarCode;
    /** 进货平均价 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colAvgPrice;
    /** 货位 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colSlots;
    /** 图片 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colPhotos;
    /** 类别 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colCategory;
    /** 产品条形码 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colBarCode;
    /** 产品编码 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colCode;
    /** 产品名称 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colName;
    /** 车型 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colCar;
    /** 通用车型 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colCars;
    /** 产地 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colPlace;
    /** 品牌 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colBrand;
    /** 英文名 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colEnglishName;
    /** 进口 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colImported;
    /** 通用件号 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colCommonNumber;
    /** 材料 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colMaterials;
    /** 所属公司 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colCompany;
    /** 包装数量 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colPackingQuantity;
    /** 重量 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colWeight;
    /** 手册名称 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colManual;
    /** 客户 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colSupplier;
    /** 一级进价 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colPurchasingPrice1;
    /** 二级进价 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colPurchasingPrice2;
    /** 三级进价 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colPurchasingPrice3;
    /** 一级销价 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colSellingPrice1;
    /** 二级销价 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colSellingPrice2;
    /** 三级销价 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colSellingPrice3;
    /** 最低销价 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colBottomPrice;
    /** 外币单位 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colForeignCurrencyUnit;
    /** 外币价格 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colForeignCurrencyPrice;
    /** 紧缺件 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colShortage;
    /** 可用状态 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colStatus;
    /** 属性字符串(可隐藏) */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colProperties;
    /** 折扣% */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colDiscountPercentage;
    /** 税率%  */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colTaxRate;
    /** 税额 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colTaxAmount;
    /** 含税单价 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colPriceIncludingTax;
    /** 含税金额 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colAmountIncludingTax;
    /** 创建时间 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colDateCreated;
    /** 创建者 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colCreator;
    /** 更新时间 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colDateUpdated;
    /** 更新者 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colUpdator;
    /** 更新次数 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colUpdateCount;
    /** 删除时间 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colDateDeleted;
    /** 删除标记 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colDeleteFlag;
    /** 删除者 */
    @FXML
    private TableColumn<SalesReturnOrderItem, String> colDeleter;
    /** 总记录数 */
    @FXML
    private Label labelRecords;
    /** 总数量 */
    @FXML
    private Label labelTotalQty;
    /** 总金额 */
    @FXML
    private Label labelTotalAmount;

    /**
     * Constructor
     *
     * @param dialog
     * @param callback
     * @param pro
     * @param readOnly
     */
    public void initialize(Stage dialog, Callback<SalesReturnOrder, String> callback, SalesReturnOrder pro, boolean readOnly) {
        this.dialog = dialog;
        this.callback = callback;
        this.pro = pro;
        this.readOnly = readOnly;
        btnSubmit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        initCellFactory();
        initInputFields();
        initTable();
        if(pro != null) {
            //单据日期
            LocalDate localDate = pro.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            orderDate.setValue(localDate);
            //单号
            txtOrderNo.setText(pro.getOrderNo());
            //客户编码
            comboBoxConsumerCode.setValue(pro.getConsumer().getCode());
            //发票类型
            comboBoxInvoiceType.setValue(pro.getInvoiceType());
            //发票No
            txtInvoiceNo.setText(pro.getInvoiceNo());
            //运费
            txtFreight.setText(pro.getFreight()+"");
            //备注
            txtNotes.setText(pro.getNotes());
            //经办人
            txtOperator.setText(pro.getOperator());
            //结算方式
            comboBoxPayments.setValue(pro.getPayment());
            //系统账号
            txtLoginAccount.setText(pro.getUserName());
//            //货款金额
//            txtPurchaseAmount.setText(pro.getPurchaseAmount().toString());
//            //代垫费用
//            txtDisbursement.setText(pro.getDisbursementAmount().toString());
//            //本次优惠
//            txtDiscountAmount.setText(pro.getDiscountAmount().toString());
//            //应付总额
//            txtAmountPayable.setText(pro.getAmountPayable().toString());
//            //本次付款
//            txtPaymentAmount.setText(pro.getPaymentAmount().toString());
//            //账号
//            comboBoxAccount.setValue(pro.getAccount());
            try {
                SalesReturnOrderItem[] items = HttpClient.GET("/salesReturnOrderItems/order/"+pro.getId(), SalesReturnOrderItem[].class);
                for(SalesReturnOrderItem item : items) {
                    addItem(item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            initConsumer();
            updateSummary();
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
            } else {
                txtConsumerName.setText("");
                txtContact.setText("");
                txtPhone.setText("");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void initInputFields() {
        //订单日期
        orderDate.setValue(LocalDate.now());
        //单号
        try {
            User user = HttpClient.GET("/users/username/"+Env.getInstance().currentUser(), User.class);
            String generatedOrderNo = HttpClient.GET("/salesReturnOrders/generation/orderNo/"+user.getId());
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
        //获取结算方式列表
        try {
            Payment[] payments = HttpClient.GET("/payments", Payment[].class);
            comboBoxPayments.getItems().addAll(Arrays.asList(payments).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxConsumerCode);
        new AutoCompleteBox(comboBoxInvoiceType);
        new AutoCompleteBox(comboBoxPayments);

        txtLoginAccount.setText(Env.getInstance().currentUser());
    }

    private String fetchOriginalOrderNo(String skuCode) {
        SalesOrder salesOrder = new SalesOrder();
        Consumer consumer = new Consumer();
        consumer.setCode(comboBoxConsumerCode.getValue());
        salesOrder.setConsumer(consumer);
        SKU sku = new SKU();
        sku.setSkuCode(skuCode);
        SalesOrderItem condition = new SalesOrderItem();
        condition.setSalesOrder(salesOrder);
        condition.setSku(sku);
        String json = GoogleJson.GET().toJson(condition);
        String orderNo = null;
        try {
            orderNo = HttpClient.POST("/salesOrderItems/orderNo", json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderNo;
    }

    private void initTable() {
        String css = getClass().getResource("/css/styles.css").toExternalForm();
        tableView.getStylesheets().add(css);
        tableView.setId("my-table");
        tableView.setEditable(true);

        //行号
        colRowNumber.setCellFactory(new RowNumberTableCell<>());

        //原采购单号
        colOriginalOrderNo.setCellValueFactory(param -> {
            if(param.getValue().getOriginalOrderNo() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getOriginalOrderNo());
            }
        });

        //SKU编码
        colSkuCode.setCellFactory(TextFieldTableCell.forTableColumn());
        colSkuCode.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getSkuCode() );
            }
        });
        colSkuCode.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SalesReturnOrderItem, String>>) t -> {
            ObservableList<SalesReturnOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SalesReturnOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String skuCode = t.getNewValue();
                        try {
                            String json = HttpClient.GET("/sku/code/"+skuCode);
                            SKU sku = GoogleJson.GET().fromJson(json, SKU.class);
                            if(sku.getId() != null) {
                                selected.setSku(sku);
                                selected.setQuantity(0);
//                                selected.setPriceExcludingTax(sku.getAvgPrice());
                                selected.setAmountExcludingTax(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));

                                //todo
                                //取原采购单号
                                String originalOrderNo = fetchOriginalOrderNo(sku.getSkuCode());
                                selected.setOriginalOrderNo(originalOrderNo);

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
        //规格
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
            ObservableList<SalesReturnOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SalesReturnOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        if(NumberValidationUtils.isPositiveInteger(newValue)) {
                            //Item数量
                            selected.setQuantity(Integer.parseInt(newValue));
                            //Item不含税金额
                            selected.setAmountExcludingTax(new BigDecimal(selected.getQuantity()).multiply(selected.getPriceExcludingTax()).setScale(2, RoundingMode.HALF_UP));
                            //Item金额
                            selected.setAmount(selected.getAmountExcludingTax());
                            data.set(t.getTablePosition().getRow(), selected);
                            updateSummary();
                        }
                    }
                }
            }
        });
        //不含税单价
        colPriceExcludingTax.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>("");
//                return new SimpleObjectProperty<>(param.getValue().getSku().getAvgPrice().toString());
            }
        });
        //不含税金额
        colAmountExcludingTax.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>("");
//                return new SimpleObjectProperty<>(param.getValue().getSku().getAvgPrice().multiply(new BigDecimal(param.getValue().getQuantity())).setScale(2, RoundingMode.HALF_UP).toString());
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
                return new SimpleObjectProperty<>(param.getValue().getSku().getSkuCode());
            }
        });
        //备注
        colNotes.setCellFactory(cellFactory);
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        colNotes.setOnEditCommit(t -> {
            ObservableList<SalesReturnOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SalesReturnOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        selected.setNotes(newValue);
                        data.set(t.getTablePosition().getRow(), selected);
                    }
                }
            }
        });
        //进货平均价
        colAvgPrice.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>("");
//                return new SimpleObjectProperty<>(param.getValue().getSku().getAvgPrice().toString());
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
        //车型
        colCar.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getCar() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getCar().getName());
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
        //品牌
        colBrand.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getBrand() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getBrand().getName());
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
                return new SimpleObjectProperty<>("");
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
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getPurchasingPrice1().toString());
            }
        });
        //二级进价
        colPurchasingPrice2.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getPurchasingPrice2().toString());
            }
        });
        //三级进价
        colPurchasingPrice3.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getPurchasingPrice3().toString());
            }
        });
        //一级销价
        colSellingPrice1.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getSellingPrice1().toString());
            }
        });
        //二级销价
        colSellingPrice2.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
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
                return new SimpleObjectProperty<>("");
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
                return new SimpleObjectProperty<>("");
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
                return new SimpleObjectProperty<>(param.getValue().getSku().getEnabled().toString());
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
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getDiscountPercentage());
            }
        });
        //税率
        colTaxRate.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //税额
        colTaxAmount.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //含税单价
        colPriceIncludingTax.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //含税金额
        colAmountIncludingTax.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                SalesReturnOrderItem selected = tableView.getSelectionModel().getSelectedItem();
                openSalesOrderEditor(selected.getOriginalOrderNo());
            }
        });
    }

    /**
     * 打开销售订单
     *
     * @param salesOrderNo
     */
    private void openSalesOrderEditor(String salesOrderNo) {
        SalesOrder so = null;
        try {
            so = HttpClient.GET("/salesOrders/orderNo/"+salesOrderNo, SalesOrder.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(so == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("销售订单号不存在");
            alert.show();
            return;
        }
        boolean readOnly = so.getStatus().equals(Constants.CLOSED);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/sales/SOEditor.fxml"
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
        SOEditorController soEditorController = loader.getController();
        soEditorController.initialize(dialog, null, so, readOnly);
        dialog.setTitle("销售单");
        dialog.initOwner(this.dialog);
        dialog.setResizable(true);
        dialog.setMaximized(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    private void updateSummary() {
        int totalRecords = 0;
        BigDecimal totalQty = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        ObservableList<SalesReturnOrderItem> list = tableView.getItems();
        for(SalesReturnOrderItem item : list) {
            totalRecords++;
            totalQty = totalQty.add(new BigDecimal(item.getQuantity()));
            totalAmount = totalAmount.add(item.getAmountExcludingTax());
        }
        labelRecords.setText(totalRecords+"");
        labelTotalQty.setText(totalQty.toString());
        labelTotalAmount.setText(totalAmount.toString());
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
            consumer = returnedConsumer;
            comboBoxConsumerCode.setValue(consumer.getCode());
            txtConsumerName.setText(consumer.getName());
            txtContact.setText(consumer.getContact());
            txtPhone.setText(consumer.getPhone());
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

    /**
     *
     * @param item
     */
    private void addItem(SalesReturnOrderItem item) {
        tableView.getItems().add(item);
        tableView.refresh();
        tableView.getSelectionModel().select(item);
    }

    /**
     *
     * @param disabled
     */
    private void disableConsumerFields(boolean disabled) {
        comboBoxConsumerCode.setDisable(disabled);
        txtConsumerName.setDisable(disabled);
        txtContact.setDisable(disabled);
        txtPhone.setDisable(disabled);
        btnConsumerChooser.setDisable(disabled);
    }

    @FXML
    private void addItem() {
        SalesReturnOrderItem item = new SalesReturnOrderItem(Constants.ID, "", null, null, Constants.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,"");
        addItem(item);
        disableConsumerFields(true);
    }

    @FXML
    private void removeItem() {
        SalesReturnOrderItem selectedItem = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selectedItem);
        tableView.refresh();
        //记录删除ID,用于保存更新
        deletedIds.add(selectedItem.getId());

        //如果表格记录存在则设置禁用
        if(tableView.getItems().size() == 0) {
            disableConsumerFields(false);
        }

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
        String header = "采购退货单: "+txtOrderNo.getText()+"\n请谨慎确定以下数据:\n";
        StringBuilder content = new StringBuilder();
        content.append("============================================\n");
        content.append("\n退货总金额: "+labelTotalAmount.getText()+" 元 \n");
//        content.append("\n代垫费用金额: "+txtDisbursement.getText()+" 元 \n");
//        content.append("\n应付货款金额: "+txtPurchaseAmount.getText()+" 元 \n");
//        content.append("\n代垫费用金额: "+txtDisbursement.getText()+" 元 \n");
//        content.append("\n本次优惠金额: "+txtDiscountAmount.getText()+" 元 \n");
//        content.append("\n总计应付金额: "+txtAmountPayable.getText()+" 元 \n");
//        content.append("\n本次实付金额: "+txtPaymentAmount.getText()+" 元 \n\n");

        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText(header);
        alertConfirm.setContentText(content.toString());
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
            saveOrUpdate(Constants.CLOSED);
            dialog.close();
        });
    }

    /**
     * 减库存数
     *
     * @param status
     * @param item
     */
    private void minusStockQty(String status, SalesReturnOrderItem item) {
        //如果结算，则处理入库数量
        if(status.equals(Constants.CLOSED)) {
            String json2 = GoogleJson.GET().toJson(item.getQuantity());
            try {
                HttpClient.PUT("/sku/stockQty/"+item.getSku().getId(), json2);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 插入或更新
     *
     * @param status
     */
    private void saveOrUpdate(String status) {
        if(pro == null) {
            //插入新记录
            pro = new SalesReturnOrder();
            LocalDate localDate = orderDate.getValue();
            Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            pro.setOrderDate(date);
            pro.setOrderNo(txtOrderNo.getText());
            pro.setConsumer(consumer);
            pro.setInvoiceType(comboBoxInvoiceType.getValue());
            pro.setInvoiceNo(txtInvoiceNo.getText());
            pro.setFreight(NumberValidationUtils.isRealNumber(txtFreight.getText())?new BigDecimal(txtFreight.getText()):BigDecimal.ZERO);
            pro.setNotes(txtNotes.getText());
            pro.setOperator(txtOperator.getText());
            pro.setPayment(comboBoxPayments.getValue());
            //退货数量
            pro.setReturnQty(Integer.parseInt(labelTotalQty.getText()));
//            //已入库数量
//            pro.setWarehouseQty(pro.getWarehouseQty());
            //退货数量合计
            pro.setReturnedTotalQty(0);
//            //货款金额
//            pro.setPurchaseAmount(NumberValidationUtils.isRealNumber(txtPurchaseAmount.getText())?new BigDecimal(txtPurchaseAmount.getText()):BigDecimal.ZERO);
//            //代垫费用
//            pro.setDisbursementAmount(NumberValidationUtils.isRealNumber(txtDisbursement.getText())?new BigDecimal(txtDisbursement.getText()):BigDecimal.ZERO);
//            //本次优惠
//            pro.setDiscountAmount(NumberValidationUtils.isRealNumber(txtDiscountAmount.getText())?new BigDecimal(txtDiscountAmount.getText()):BigDecimal.ZERO);
//            //应付总额
//            pro.setAmountPayable(NumberValidationUtils.isRealNumber(txtAmountPayable.getText())?new BigDecimal(txtAmountPayable.getText()):BigDecimal.ZERO);
//            //本次付款
//            pro.setPaymentAmount(NumberValidationUtils.isRealNumber(txtAmountPayable.getText())?new BigDecimal(txtPaymentAmount.getText()):BigDecimal.ZERO);
//            //账号
//            pro.setAccount(comboBoxAccount.getValue());
            //系统登录账号
            pro.setUserName(txtLoginAccount.getText());
            //仓库
            pro.setWarehouse(Env.getInstance().currentStore().getWarehouse());
            //状态
            pro.setStatus(status);
            //创建人
            pro.setCreator(Env.getInstance().currentUser());
            //创建采购退货单对象
            String json = GoogleJson.GET().toJson(pro);
            try {
                String idStr = HttpClient.POST("/salesReturnOrders", json);
                pro.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //创建销售退货单明细
            ObservableList<SalesReturnOrderItem> items = tableView.getItems();
            items.forEach(e -> {
                if(e.getSku() != null && !StringUtils.isEmpty(e.getOriginalOrderNo())) {
                    e.setSalesReturnOrder(pro);
                    String data = GoogleJson.GET().toJson(e);
                    try {
                        String idStr = HttpClient.POST("/salesReturnOrderItems", data);
                        e.setId(Long.valueOf(idStr));
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    //更新库存数量
                    minusStockQty(status, e);
                }
            });
            callback.call(pro);
        } else {
            //更新原有记录
            SalesReturnOrder pro = new SalesReturnOrder();
            pro.setId(this.pro.getId());
            LocalDate localDate = orderDate.getValue();
            Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            pro.setOrderDate(date);
            pro.setOrderNo(txtOrderNo.getText());
            pro.setConsumer(consumer);
            pro.setInvoiceType(comboBoxInvoiceType.getValue());
            pro.setInvoiceNo(txtInvoiceNo.getText());
            pro.setFreight(NumberValidationUtils.isRealNumber(txtFreight.getText())?new BigDecimal(txtFreight.getText()):BigDecimal.ZERO);
            pro.setNotes(txtNotes.getText());
            pro.setOperator(txtOperator.getText());
            pro.setPayment(comboBoxPayments.getValue());
            //退货数量
            pro.setReturnQty(Integer.parseInt(labelTotalQty.getText()));
            //已入库数量
//            pro.setWarehouseQty(pro.getPurchaseQty());
            //退货数量合计
            pro.setReturnedTotalQty(0);
//            //货款金额
//            pro.setPurchaseAmount(NumberValidationUtils.isRealNumber(txtPurchaseAmount.getText())?new BigDecimal(txtPurchaseAmount.getText()):BigDecimal.ZERO);
//            //代垫费用
//            pro.setDisbursementAmount(NumberValidationUtils.isRealNumber(txtDisbursement.getText())?new BigDecimal(txtDisbursement.getText()):BigDecimal.ZERO);
//            //本次优惠
//            pro.setDiscountAmount(NumberValidationUtils.isRealNumber(txtDiscountAmount.getText())?new BigDecimal(txtDiscountAmount.getText()):BigDecimal.ZERO);
//            //应付总额
//            pro.setAmountPayable(NumberValidationUtils.isRealNumber(txtAmountPayable.getText())?new BigDecimal(txtAmountPayable.getText()):BigDecimal.ZERO);
//            //本次付款
//            pro.setPaymentAmount(NumberValidationUtils.isRealNumber(txtAmountPayable.getText())?new BigDecimal(txtPaymentAmount.getText()):BigDecimal.ZERO);
//            //账号
//            pro.setAccount(comboBoxAccount.getValue());
            //系统登录账号
            pro.setUserName(txtLoginAccount.getText());
            //仓库
            pro.setWarehouse(Env.getInstance().currentStore().getWarehouse());
            //状态
            pro.setStatus(status);
            //创建人
            pro.setCreator(Env.getInstance().currentUser());
            String json = GoogleJson.GET().toJson(pro);
            try {
                HttpClient.PUT("/salesReturnOrders/"+pro.getId(), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //更新或创建销售退货单明细
            ObservableList<SalesReturnOrderItem> items = tableView.getItems();
            items.forEach(e -> {
                if(e.getSku() != null && !StringUtils.isEmpty(e.getOriginalOrderNo())) {
                    e.setSalesReturnOrder(pro);
                    String data = GoogleJson.GET().toJson(e);
                    if(e.getId() == 0L) {
                        try {
                            String idStr = HttpClient.POST("/salesReturnOrderItems", data);
                            e.setId(Long.valueOf(idStr));
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        try {
                            HttpClient.PUT("/salesReturnOrderItems/"+e.getId(), data);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    //更新库存数量
                    minusStockQty(status, e);
                }
            });
            //删除已经移除的行
            deletedIds.forEach(SalesReturnOrderItemId -> {
                try {
                    HttpClient.DELETE("/salesReturnOrderItems/"+SalesReturnOrderItemId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            callback.call(pro);
        }
    }

    @FXML
    private void save() {
        saveOrUpdate(Constants.UNCLOSED);
        dialog.close();
    }

    @FXML
    private void submit() {
        confirmAndSubmit();
    }

    private void openProductChooser() {
        Callback<SKU, String> callback = new Callback<SKU, String>() {

            @Override
            public String call(SKU sku) {
                SalesReturnOrderItem item = tableView.getSelectionModel().getSelectedItem();
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
