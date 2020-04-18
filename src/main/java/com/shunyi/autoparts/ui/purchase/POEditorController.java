package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.*;
import com.shunyi.autoparts.ui.products.ProductChooserController;
import com.shunyi.autoparts.ui.supplier.SupplierChooserController;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Description: 采购订单编辑器Controller
 * @Author: 陈顺谊
 * @CreateDate: 2020/4/1 4:37
 * @Version: 1.0
 */
public class POEditorController {
    private Stage dialog;
    private Callback<PurchaseOrder, String> callback;
    /** 采购订单 */
    private PurchaseOrder order;
    private boolean readOnly;
    private Supplier supplier;
    private Callback<TableColumn<PurchaseOrderItem, String>, TableCell<PurchaseOrderItem, String>> cellFactory;
    @FXML
    private Button btnSubmit;
    @FXML
    private DatePicker orderDate;
    @FXML
    private TextField txtOrderNo;
    /** 供应商编码 */
    @FXML
    private ComboBox<String> comboBoxSupplierCode;
    /** 供应商名称 */
    @FXML
    private TextField txtSupplierName;
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
    private ComboBox comboBoxPayments;
    @FXML
    private ComboBox comboBoxAccount;
//    @FXML
//    private TextField txtAmountPaid;
//    @FXML
//    private TextField txtAmountIncludingTax;
    @FXML
    private TextField txtTotalQty;
    @FXML
    private TextField txtDiscountedAmount;
    @FXML
    private TextField txtTotalAmount;
//    @FXML
//    private TextField txtDiscounts;
//    @FXML
//    private TextField txtDiscountedAmount2;
//    @FXML
//    private TextField txtDue;
    @FXML
    private TextField txtOperator;
    /** 采购订单明细表 */
    @FXML
    private TableView<PurchaseOrderItem> tableView;
    /** 行号 */
    @FXML
    private TableColumn<PurchaseOrderItem, String> colRowNumber;
    /** SKU编码 */
    @FXML
    private TableColumn<PurchaseOrderItem, String> colSkuCode;
    /** SKU名称 */
    @FXML
    private TableColumn<PurchaseOrderItem, String> colSkuName;
    /** 规格 */
    @FXML
    private TableColumn<PurchaseOrderItem, String> colSpecification;
    /** 数量 */
    @FXML
    private TableColumn<PurchaseOrderItem, String> colQty;
    /** 条形码 */
    @FXML
    private TableColumn colSkuBarCode;
    /** 库存数量 */
    @FXML
    private TableColumn colStockQty;
    /** 属性字符串(可隐藏) */
    @FXML
    private TableColumn colProperties;
    /** 折扣% */
    @FXML
    private TableColumn colDiscountPercentage;
    /** 税率%  */
    @FXML
    private TableColumn colTaxRate;
    /** 税额 */
    @FXML
    private TableColumn colTaxAmount;
    /** 含税单价 */
    @FXML
    private TableColumn colPriceIncludingTax;
    /** 含税金额 */
    @FXML
    private TableColumn colAmountIncludingTax;
    /** 不含税单价 */
    @FXML
    private TableColumn colPriceExcludingTax;
    /** 不含税金额 */
    @FXML
    private TableColumn colAmountExcludingTax;
    /** 可用状态 */
    @FXML
    private TableColumn colStatus;
    /** 备注 */
    @FXML
    private TableColumn colNotes;
    /** 进货平均价 */
    @FXML
    private TableColumn colAvgPrice;
    /** 所有货位 */
    @FXML
    private TableColumn colSlots;
    /** 所有图片 */
    @FXML
    private TableColumn colPhotos;
    /** 产品编码 */
    @FXML
    private TableColumn colCode;
    /** 类别 */
    @FXML
    private TableColumn colCategory;
    /** 产品名称 */
    @FXML
    private TableColumn colName;
    /** 单位 */
    @FXML
    private TableColumn colUnit;
    /** 产品条形码 */
    @FXML
    private TableColumn colBarCode;
    /** 车型 */
    @FXML
    private TableColumn colCar;
    /** 通用车型 */
    @FXML
    private TableColumn colCars;
    /** 产地 */
    @FXML
    private TableColumn colPlace;
    /** 品牌 */
    @FXML
    private TableColumn colBrand;
    /** 英文名 */
    @FXML
    private TableColumn colEnglishName;
    /** 进口 */
    @FXML
    private TableColumn colImported;
    /** 通用件号 */
    @FXML
    private TableColumn colCommonNumber;
    /** 材料 */
    @FXML
    private TableColumn colMaterials;
    /** 所属公司 */
    @FXML
    private TableColumn colCompany;
    /** 包装数量 */
    @FXML
    private TableColumn colPackingQuantity;
    /** 重量 */
    @FXML
    private TableColumn colWeight;
    /** 手册名称 */
    @FXML
    private TableColumn colManual;
    /** 供应商 */
    @FXML
    private TableColumn colSupplier;
    /** 一级进价 */
    @FXML
    private TableColumn colPurchasingPrice1;
    /** 二级进价 */
    @FXML
    private TableColumn colPurchasingPrice2;
    /** 三级进价 */
    @FXML
    private TableColumn colPurchasingPrice3;
    /** 一级销价 */
    @FXML
    private TableColumn colSellingPrice1;
    /** 二级销价 */
    @FXML
    private TableColumn colSellingPrice2;
    /** 三级销价 */
    @FXML
    private TableColumn colSellingPrice3;
    /** 最低销价 */
    @FXML
    private TableColumn colBottomPrice;
    /** 外币单位 */
    @FXML
    private TableColumn colForeignCurrencyUnit;
    /** 外币价格 */
    @FXML
    private TableColumn colForeignCurrencyPrice;
    /** 紧缺件 */
    @FXML
    private TableColumn colShortage;
    /** 创建时间 */
    @FXML
    private TableColumn colDateCreated;
    /** 创建者 */
    @FXML
    private TableColumn colCreator;
    /** 更新时间 */
    @FXML
    private TableColumn colDateUpdated;
    /** 更新者 */
    @FXML
    private TableColumn colUpdator;
    /** 更新次数 */
    @FXML
    private TableColumn colUpdateCount;
    /** 删除时间 */
    @FXML
    private TableColumn colDateDeleted;
    /** 删除标记 */
    @FXML
    private TableColumn colDeleteFlag;
    /** 删除者 */
    @FXML
    private TableColumn colDeleter;


    /**
     * Constructor
     *
     * @param dialog
     * @param callback
     * @param order
     * @param readOnly
     */
    public void initialize(Stage dialog, Callback<PurchaseOrder, String> callback, PurchaseOrder order, boolean readOnly) {
        this.dialog = dialog;
        this.callback = callback;
        if(order == null) {
            this.order = new PurchaseOrder();
        }
        this.readOnly = readOnly;
        initCellFactory();
        initInputFields();
        initTable();
        addItem();
    }

    private void initCellFactory() {
        cellFactory = p -> new EditingCell();
    }

    private void initInputFields() {
        orderDate.setValue(LocalDate.now());
        comboBoxSupplierCode.setStyle("-fx-font-size: 14;");
        comboBoxInvoiceType.setStyle("-fx-font-size: 14;");
        comboBoxInvoiceType.getItems().add("普通发票");
        comboBoxInvoiceType.getSelectionModel().select(0);
        comboBoxPayments.getItems().add("现金");
        comboBoxPayments.getSelectionModel().select(0);
//        comboBoxAccount.getItems().add("00000000");
//        comboBoxAccount.getSelectionModel().select(0);
//        txtOperator.setText(Env.getInstance().currentUser());
//        txtOperator.setEditable(false);
//        txtFreight.setText("0.00");
//        txtAmountPaid.setText("0.00");
//        txtAmountIncludingTax.setText("0.00");
//        txtTotalQty.setText("0.00");
//        txtDiscountedAmount.setText("0.00");
//        txtTotalAmount.setText("0.00");
//        txtDiscounts.setText("0.00");
//        txtDiscountedAmount2.setText("0.00");
//        txtDue.setText("0.00");
//        txtFreight.setEditable(false);
//        txtAmountPaid.setEditable(false);
//        txtTotalQty.setEditable(false);
//        txtDiscountedAmount.setEditable(false);
//        txtTotalAmount.setEditable(false);
//        txtDiscounts.setEditable(false);
//        txtDiscountedAmount2.setEditable(false);
//        txtDue.setEditable(false);
//        txtAmountIncludingTax.setEditable(false);
        btnSubmit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));

        try {
            User user = HttpClient.GET("/users/username/"+Env.getInstance().currentUser(), User.class);
            String generatedOrderNo = HttpClient.GET("/purchaseOrders/orderNo/"+user.getId());
            txtOrderNo.setText(generatedOrderNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        comboBoxSupplierCode.setOnKeyReleased(e -> {
            if(!e.getCode().isArrowKey()) {
//                comboBoxSupplierCode.getItems().clear();
//                Supplier supplier = new Supplier();
//                supplier.setName(comboBoxSupplierCode.getEditor().getText());
//                String json = GoogleJson.GET().toJson(supplier);
//                String data;
//                try {
//                    data = HttpClient.POST("/suppliers/search", json);
//                    Supplier[] suppliers = GoogleJson.GET().fromJson(data, Supplier[].class);
//                    comboBoxSupplierCode.getItems().addAll(suppliers);
//                    comboBoxSupplierCode.show();
//
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
            }

        });

        comboBoxSupplierCode.setOnAction(e -> {
//            if(comboboxSupplierCode.getValue() instanceof Supplier) {
//                Supplier supplier = comboboxSupplierCode.getValue();
//                txtSupplierName.setText(supplier.getName());
//                txtContact.setText(supplier.getContact());
//                txtPhone.setText(supplier.getPhone1()+","+supplier.getPhone2());
//            }

        });
    }

    private void initTable() {
        String css = getClass().getResource("/css/styles.css").toExternalForm();
        tableView.getStylesheets().add(css);
        tableView.setId("my-table");
        tableView.setEditable(true);

//        colBarCode.setCellFactory(cellFactory);
//        colBarCode.setCellValueFactory(
//                new PropertyValueFactory<SKU, String>("barCode")
//        );
//        colBarCode.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SKU, String>>) t -> {
//            ObservableList<SKU> data = t.getTableView().getItems();
//            if(data != null) {
//                if(t.getTablePosition().getRow() < data.size()) {
//                    SKU selected = data.get( t.getTablePosition().getRow());
//                    if(selected != null) {
//                        selected.setSkuBarCode(t.getNewValue());
//                        data.set(t.getTablePosition().getRow(), selected);
//                    }
//                }
//            }
//        });

        colRowNumber.setCellFactory(new RowNumberTableCell<>());

//        colSkuCode.setCellFactory(cellFactory);
        colSkuCode.setCellFactory(TextFieldTableCell.forTableColumn());
        colSkuCode.setCellValueFactory(param -> {
            if(param.getValue().getSku().getSkuCode() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getSkuCode() );
            }
        });
        colSkuCode.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<PurchaseOrderItem, String>>) t -> {
            ObservableList<PurchaseOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    PurchaseOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
//                        selected.getSku().setSkuCode(t.getNewValue());
//                        data.set(t.getTablePosition().getRow(), selected);

                        String skuCode = t.getNewValue();
                        try {
                            String json = HttpClient.GET("/sku/code/"+skuCode);
                            SKU sku = GoogleJson.GET().fromJson(json, SKU.class);
                            if(sku.getId() != null) {
                                selected.getSku().setSkuCode(sku.getSkuCode());
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
        colSkuName.setCellValueFactory(param -> {
            if(param.getValue().getSku().getSkuName() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getSkuName() );
            }
        });
        colSpecification.setCellValueFactory(param -> {
            if(param.getValue().getSku().getSpecification() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getSpecification() );
            }
        });
        colQty.setCellFactory(cellFactory);
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQty.setOnEditCommit(t -> {
            ObservableList<PurchaseOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    PurchaseOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        if(NumberValidationUtils.isPositiveInteger(newValue)) {
                            selected.setQuantity(Integer.parseInt(newValue));
                            data.set(t.getTablePosition().getRow(), selected);
                        }
                    }
                }
            }
        });

//        colBrand.setCellValueFactory(param -> {
//            if(param.getValue().getSku().getProduct().getBrandSeries() == null) {
//                return new SimpleObjectProperty<>("");
//            } else {
//                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getBrandSeries().getChineseName());
//            }
//        });
//        colBarCode.setCellValueFactory(param -> {
//            if(param.getValue().getSku().getBarCode() == null) {
//                return new SimpleObjectProperty<>("");
//            } else {
//                return new SimpleObjectProperty<>(param.getValue().getSku().getBarCode());
//            }
//        });
//        colUnit.setCellValueFactory(param -> {
//            if(param.getValue().getSku().getUnit() == null) {
//                return new SimpleObjectProperty<>("");
//            } else {
//                return new SimpleObjectProperty<>(param.getValue().getSku().getUnit());
//            }
//        });
//        colQty.setCellValueFactory(param -> {
//            if(param.getValue().getSku().getQuantity() == null) {
//                return new SimpleObjectProperty<>("");
//            } else {
//                return new SimpleObjectProperty<>(param.getValue().getSku().getQuantity().toString());
//            }
//        });
//        colPrice.setCellValueFactory(param -> {
//            if(param.getValue().getSku().getPrice() == null) {
//                return new SimpleObjectProperty<>("");
//            } else {
//                return new SimpleObjectProperty<>(param.getValue().getSku().getPrice().toString());
//            }
//        });
    }

    @FXML
    private void openSupplierChooser() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/supplier/SupplierChooser.fxml"
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
        SupplierChooserController controller = loader.getController();
        Callback<Supplier, String> callback = returnedSupplier -> {
            supplier = returnedSupplier;
            comboBoxSupplierCode.setValue(supplier.getCode());
            txtSupplierName.setText(supplier.getName());
            return null;
        };
        controller.initialize(subStage, callback, supplier);
        subStage.setTitle("选择供应商");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    @FXML
    private void addItem() {
        SKU sku = new SKU();
        sku.setSkuCode("");
        sku.setSkuName("");
        sku.setSpecification("");

        //初始化第一行空行
        PurchaseOrderItem item = new PurchaseOrderItem(Constants.ID, order, sku,0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,"");
        tableView.getItems().add(item);
        tableView.refresh();
        tableView.getSelectionModel().select(item);
    }

    @FXML
    private void removeItem() {
        PurchaseOrderItem selectedItem = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selectedItem);
        tableView.refresh();
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void save() {


        dialog.close();
    }

    @FXML
    private void submit() {
        dialog.close();
    }


    private void openProductChooser() {
        //Open product chooser
        Callback<PO, String> callback = new Callback<PO, String>() {
            @Override
            public String call(PO param) {
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
        skuChooserController.initialize(dialog);
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
