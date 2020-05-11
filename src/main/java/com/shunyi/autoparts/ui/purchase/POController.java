package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.InvoiceType;
import com.shunyi.autoparts.ui.common.vo.Payment;
import com.shunyi.autoparts.ui.common.vo.PurchaseOrder;
import com.shunyi.autoparts.ui.common.vo.Supplier;
import com.shunyi.autoparts.ui.supplier.SupplierChooserController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
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
    private ComboBox<String> comboBoxWarehouse;
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
        boolean readOnly = po.getStatus().equals(Constants.CLOSED);
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
        poEditorController.initialize(dialog, callback, po,readOnly);
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
    private void delete() {
        PurchaseOrder selected = tableView.getSelectionModel().getSelectedItem();
        if(selected != null) {
            if(selected.getStatus().equals(Constants.CLOSED)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
                alert.setHeaderText("无法删除已结算的订单");
                alert.show();
                return;
            }
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
            alertConfirm.setHeaderText("请确认是否要删除采购单？");
            alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
                //删除订单明细
                try {
                    HttpClient.DELETE("/purchaseOrderItems/order/"+selected.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //删除订单
                try {
                    HttpClient.DELETE("/purchaseOrders/"+selected.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tableView.getItems().remove(selected);
                tableView.refresh();
            });
        }
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
    private void print() {
        HBox node = new HBox();
        Label s = new Label("dsdsddsds");
        node.getChildren().add(s);

        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }

    @FXML
    private void search() {
        PurchaseOrder confition = new PurchaseOrder();
        Supplier supplier = new Supplier();
        supplier.setCode(comboBoxSupplier.getValue());
        confition.setSupplier(supplier);
        confition.setOrderNo(txtNo.getText());
        confition.setOperator(txtOperator.getText());
        confition.setUserName(txtUserName.getText());
        confition.setInvoiceType(comboBoxInvoiceType.getValue());
        confition.setWarehouse(Env.getInstance().currentStore().getWarehouse());
        confition.setPayment(comboBoxPayment.getValue());
        confition.setNotes(txtNotes.getText());
        confition.setStatus(comboBoxStatus.getValue());
        confition.setDateType(radioBtnOrderDate.isSelected()?"单据日期":"还款日期");
        if(fromDate.getValue() != null && toDate.getValue() != null) {
            confition.setFromDate(Date.from(fromDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            confition.setToDate(Date.from(toDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        }
        String json = GoogleJson.GET().toJson(confition);
        String data = null;
        try {
            data = HttpClient.POST("/purchaseOrders/search", json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PurchaseOrder[] results = GoogleJson.GET().fromJson(data, PurchaseOrder[].class);
        tableView.getItems().clear();
        tableView.getItems().addAll(results);
        tableView.refresh();
    }

    @FXML
    private void clearQueryConditions() {
        comboBoxSupplier.setValue(null);
        comboBoxInvoiceType.setValue(null);
        comboBoxStatus.setValue(null);
        txtNo.setText("");
        comboBoxWarehouse.setValue(null);
        radioBtnOrderDate.setSelected(true);
        txtOperator.setText("");
        comboBoxPayment.setValue("");
        fromDate.setValue(null);
        txtUserName.setText("");
        txtNotes.setText("");
        toDate.setValue(null);
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
            if(returnedSupplier != null) {
                if(!comboBoxSupplier.getItems().contains(returnedSupplier.getCode())) {
                    comboBoxSupplier.getItems().add(0, returnedSupplier.getCode());
                }
                comboBoxSupplier.setValue(returnedSupplier.getCode());
            }
            return null;
        };
        controller.initialize(subStage, callback, null);
        subStage.setTitle("选择供应商");
        subStage.initOwner(application.getStage());
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
                if(param != null) {
                    if(!comboBoxInvoiceType.getItems().contains(param.getName())) {
                        comboBoxInvoiceType.getItems().add(0, param.getName());
                    }
                    comboBoxInvoiceType.setValue(param.getName());
                }
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
        controller.initialize(dialog, callback, null);
        dialog.setTitle("发票类型选择器");
        dialog.initOwner(application.getStage());
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
                if(param != null) {
                    if(!comboBoxPayment.getItems().contains(param.getName())) {
                        comboBoxPayment.getItems().add(0, param.getName());
                    }
                    comboBoxPayment.setValue(param.getName());
                }
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
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
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

        //仓库
        comboBoxWarehouse.getItems().add(Env.getInstance().currentStore().getWarehouse().getName());
    }

    private void initTable() {
        tableView.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        tableView.setId("my-table");
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                update();
            }
        });
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
