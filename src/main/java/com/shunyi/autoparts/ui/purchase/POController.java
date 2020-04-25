package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.AutoCompleteBox;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.InvoiceType;
import com.shunyi.autoparts.ui.common.vo.Payment;
import com.shunyi.autoparts.ui.common.vo.PurchaseOrder;
import com.shunyi.autoparts.ui.common.vo.Supplier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Description: 采购订单列表Controller
 * @Author: Shunyi
 * @CreateDate: 2020/4/4
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
    private TableColumn colDate;
    @FXML
    private TableColumn colNo;
    @FXML
    private TableColumn colSupplierCode;
    @FXML
    private TableColumn colSupplierName;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colPhone;
    @FXML
    private TableColumn colInvoiceType;
    @FXML
    private TableColumn colInvoiceNo;
    @FXML
    private TableColumn colFreight;
    @FXML
    private TableColumn colNotes;
    @FXML
    private TableColumn colPayment;
    @FXML
    private TableColumn colAccount;
    @FXML
    private TableColumn colOperator;
    @FXML
    private TableColumn colPaymentAmount;
    @FXML
    private TableColumn colTotalQty;
    @FXML
    private TableColumn colDiscountedAmount;
    @FXML
    private TableColumn colTotalAmount;
    @FXML
    private TableColumn colTaxIncludedAmount;
    @FXML
    private TableColumn colDiscount;
    @FXML
    private TableColumn colAmountAfterDiscount;
    @FXML
    private TableColumn colAccountPayable;
    @FXML
    private TableColumn colStatus;

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

        refresh();
    }
}
