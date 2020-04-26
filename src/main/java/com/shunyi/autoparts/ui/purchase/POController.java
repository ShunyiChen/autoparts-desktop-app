package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.AutoCompleteBox;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.InvoiceType;
import com.shunyi.autoparts.ui.common.vo.Payment;
import com.shunyi.autoparts.ui.common.vo.PurchaseOrder;
import com.shunyi.autoparts.ui.common.vo.Supplier;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Description: 采购订单列表Controller
 * @Author: Shunyi
 * @CreateDate: 2020/4/26
 * @Version: 1.0
 */
public class POController {
    private MainApp application;
    private POEditorController poEditorController;

    @FXML
    private RadioButton radioBtnOrderDate;
    @FXML
    private RadioButton radioBtnRepaymentDate;
    @FXML
    private ComboBox<String> comboBoxSupplier;
    @FXML
    private TextField txtNo;
    @FXML
    private TextField txtOperator;
    @FXML
    private TextField txtUserName;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private ComboBox<String> comboBoxInvoiceType;
    @FXML
    private ComboBox<String> comboBoxPayment;
    @FXML
    private ComboBox<String> comboBoxStatus;
    @FXML
    private TextField txtNotes;
    @FXML
    private TableView<PurchaseOrder> tableView;
    @FXML
    private TableColumn<PurchaseOrder, String> colOrderNo;
    @FXML
    private TableColumn<PurchaseOrder, String> colOrderDate;
    @FXML
    private TableColumn<PurchaseOrder, String> colSupplierName;
    @FXML
    private TableColumn<PurchaseOrder, String> colWarehouse;
    @FXML
    private TableColumn<PurchaseOrder, String> colOperator;
    @FXML
    private TableColumn<PurchaseOrder, String> colUserName;
    @FXML
    private TableColumn<PurchaseOrder, String> colPurchaseQty;
    @FXML
    private TableColumn<PurchaseOrder, String> colWarehouseQty;
    @FXML
    private TableColumn<PurchaseOrder, String> colReturnedTotalQty;
    @FXML
    private TableColumn<PurchaseOrder, String> colAmountExcludingTax;
    @FXML
    private TableColumn<PurchaseOrder, String> colAmountIncludingTax;
    @FXML
    private TableColumn<PurchaseOrder, String> colPayment;
    @FXML
    private TableColumn<PurchaseOrder, String> colInvoiceType;
    @FXML
    private TableColumn<PurchaseOrder, String> colNotes;
    @FXML
    private TableColumn<PurchaseOrder, String> colStatus;
    @FXML
    private TableColumn<PurchaseOrder, String> colRepaymentDate;
    @FXML
    private TableColumn<PurchaseOrder, String> colDisbursementAmount;
    @FXML
    private TableColumn<PurchaseOrder, String> colRepaymentAmount;
    @FXML
    private TableColumn<PurchaseOrder, String> colDiscountAmount;
    @FXML
    private TableColumn<PurchaseOrder, String> colAccount;

    @FXML
    private void create() {
        Callback<PurchaseOrder, String> callback = new Callback<PurchaseOrder, String>() {
            @Override
            public String call(PurchaseOrder purchaseOrder) {
                tableView.getItems().add(purchaseOrder);
                tableView.refresh();
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/POEditor.fxml"
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
        poEditorController = loader.getController();
        poEditorController.initialize(dialog, callback, null,false);
        dialog.setTitle("新建采购订单");
        dialog.initOwner(application.getStage());
        dialog.setResizable(true);
        dialog.setMaximized(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void update() {
        PurchaseOrder po = tableView.getSelectionModel().getSelectedItem();
        if(po == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择采购订单");
            alert.show();
            return;
        }
        Callback<PurchaseOrder, String> callback = new Callback<PurchaseOrder, String>() {
            @Override
            public String call(PurchaseOrder updated) {
                //刷新表格
                int index = tableView.getSelectionModel().getSelectedIndex();
                tableView.getItems().remove(po);
                tableView.getItems().add(index, updated);
                tableView.getSelectionModel().select(updated);
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/POEditor.fxml"
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
        poEditorController = loader.getController();
        poEditorController.initialize(dialog, callback, po,false);
        dialog.setTitle("更新采购订单");
        dialog.initOwner(application.getStage());
        dialog.setResizable(true);
        dialog.setMaximized(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void refresh() {
        tableView.getItems().clear();
        try {
            PurchaseOrder[] purchaseOrders = HttpClient.GET("/purchaseOrders", PurchaseOrder[].class);
            tableView.getItems().addAll(purchaseOrders);
            tableView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void search() {

    }

    @FXML
    private void clearQueryConditions() {
        txtNo.setText("");
        comboBoxInvoiceType.setValue(null);
        txtNotes.setText("");
        comboBoxStatus.setValue(null);
        fromDate.setValue(null);
        toDate.setValue(null);
    }

    public void clean() {
        tableView.getItems().clear();
    }

    public void initialize(MainApp application) {
        this.application = application;

        initSearchFields();

        initTable();
    }

    private void initSearchFields() {
        ToggleGroup tg = new ToggleGroup();
        radioBtnOrderDate.setToggleGroup(tg);
        radioBtnRepaymentDate.setToggleGroup(tg);
        //获取供应商列表
        try {
            Supplier[] suppliers = HttpClient.GET("/suppliers", Supplier[].class);
            comboBoxSupplier.getItems().addAll(Arrays.asList(suppliers).stream().map(e -> e.getCode()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        comboBoxSupplier.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
            }
        });
        comboBoxSupplier.setOnAction(e -> {
        });
        new AutoCompleteBox(comboBoxSupplier);
        //获取发票类型列表
        try {
            InvoiceType[] invoiceTypes = HttpClient.GET("/invoiceTypes", InvoiceType[].class);
            comboBoxInvoiceType.getItems().addAll(Arrays.asList(invoiceTypes).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxInvoiceType);
        //获取结算方式列表
        try {
            Payment[] payments = HttpClient.GET("/payments", Payment[].class);
            comboBoxPayment.getItems().addAll(Arrays.asList(payments).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxPayment);
        //获取状态列表
        comboBoxStatus.getItems().addAll(Constants.CLOSED, Constants.UNCLOSED);
        new AutoCompleteBox(comboBoxStatus);

    }

    private void initTable() {
        tableView.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        tableView.setId("my-table");
        //订单号
        colOrderNo.setCellValueFactory(param -> {
            if(param.getValue().getOrderNo() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getOrderNo());
            }
        });
        //订单日期
        SimpleDateFormat format = new SimpleDateFormat(Constants.PATTERN);
        colOrderDate.setCellValueFactory(param -> {
            if(param.getValue().getOrderDate() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(format.format(param.getValue().getOrderDate()));
            }
        });
        //供应商名称
        colSupplierName.setCellValueFactory(param -> {
            if(param.getValue().getSupplier() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSupplier().getName());
            }
        });
        //仓库名称
        colWarehouse.setCellValueFactory(param -> {
            if(param.getValue().getWarehouse() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getWarehouse().getName());
            }
        });
        //经办人
        colOperator.setCellValueFactory(param -> {
            if(param.getValue().getOperator() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getOperator());
            }
        });
        //操作员
        colUserName.setCellValueFactory(param -> {
            if(param.getValue().getUserName() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getUserName());
            }
        });
        //进货数量
        colPurchaseQty.setCellValueFactory(param -> {
            if(param.getValue().getPurchaseQty() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getPurchaseQty().intValue()+"");
            }
        });
        //入库数量
        colWarehouseQty.setCellValueFactory(param -> {
            if(param.getValue().getWarehouseQty() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getWarehouseQty().intValue()+"");
            }
        });
        //退货数量合计
        colReturnedTotalQty.setCellValueFactory(param -> {
            if(param.getValue().getReturnedTotalQty() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getReturnedTotalQty().intValue()+"");
            }
        });
        //未税金额
        colAmountExcludingTax.setCellValueFactory(param -> {
            if(param.getValue().getAmountPayable() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getAmountPayable().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //含税金额
        colAmountIncludingTax.setCellValueFactory(param -> {
            if(param.getValue().getAmountPayable() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getAmountPayable().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //结算方式
        colPayment.setCellValueFactory(param -> {
            if(param.getValue().getPayment() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getPayment());
            }
        });
        //发票类型
        colInvoiceType.setCellValueFactory(param -> {
            if(param.getValue().getInvoiceType() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getInvoiceType());
            }
        });
        //备注
        colNotes.setCellValueFactory(param -> {
            if(param.getValue().getNotes() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getNotes());
            }
        });
        //状态
        colStatus.setCellValueFactory(param -> {
            if(param.getValue().getStatus() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getStatus());
            }
        });
        //还款日期
        colRepaymentDate.setCellValueFactory(param -> {
            if(param.getValue().getRepaymentDate() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(format.format(param.getValue().getRepaymentDate()));
            }
        });
        //代垫金额
        colDisbursementAmount.setCellValueFactory(param -> {
            if(param.getValue().getDisbursementAmount() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getDisbursementAmount().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //已还金额
        colRepaymentAmount.setCellValueFactory(param -> {
            if(param.getValue().getRepaymentAmount() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getRepaymentAmount().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //优惠金额
        colDiscountAmount.setCellValueFactory(param -> {
            if(param.getValue().getDisbursementAmount() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getDisbursementAmount().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //账号
        colAccount.setCellValueFactory(param -> {
            if(param.getValue().getAccount() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getAccount());
            }
        });

        refresh();
    }
}
