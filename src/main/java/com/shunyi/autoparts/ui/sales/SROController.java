package com.shunyi.autoparts.ui.sales;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.InvoiceType;
import com.shunyi.autoparts.ui.common.vo.Payment;
import com.shunyi.autoparts.ui.common.vo.SalesReturnOrder;
import com.shunyi.autoparts.ui.common.vo.Consumer;
import com.shunyi.autoparts.ui.purchase.InvoiceTypeChooserController;
import com.shunyi.autoparts.ui.purchase.PaymentChooserController;
import com.shunyi.autoparts.ui.consumer.ConsumerChooserController;
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
 * @Description: 销售退货单Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/9
 */
public class SROController {
    private MainApp application;
    private SROEditorController sroEditorController;
    @FXML
    private ComboBox<String> comboBoxConsumer;
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
    private TableView<SalesReturnOrder> tableView;
    @FXML
    private TableColumn<SalesReturnOrder, String> colOrderNo;
    @FXML
    private TableColumn<SalesReturnOrder, String> colOrderDate;
    @FXML
    private TableColumn<SalesReturnOrder, String> colConsumerCode;
    @FXML
    private TableColumn<SalesReturnOrder, String> colConsumerName;
    @FXML
    private TableColumn<SalesReturnOrder, String> colWarehouse;
    @FXML
    private TableColumn<SalesReturnOrder, String> colOperator;
    @FXML
    private TableColumn<SalesReturnOrder, String> colUserName;
    @FXML
    private TableColumn<SalesReturnOrder, String> colReturnQty;
    @FXML
    private TableColumn<SalesReturnOrder, String> colAmountExcludingTax;
    @FXML
    private TableColumn<SalesReturnOrder, String> colAmountIncludingTax;

    @FXML
    private TableColumn<SalesReturnOrder, String> colTaxAmount;
    @FXML
    private TableColumn<SalesReturnOrder, String> colTotalAmount;
    @FXML
    private TableColumn<SalesReturnOrder, String> colReturnedExpenses;
    /** 货款已退金额 */
    @FXML
    private TableColumn<SalesReturnOrder, String> colReturnedSalesAmount;
    /** 代垫费用已退金额 */
    @FXML
    private TableColumn<SalesReturnOrder, String> colDisbursementReturnedAmount;
    /** 已退款总额 */
    @FXML
    private TableColumn<SalesReturnOrder, String> colTotalReturnedAmount;
    /** 代垫费用金额 */
    @FXML
    private TableColumn<SalesReturnOrder, String> colDisbursementAmount;
    @FXML
    private TableColumn<SalesReturnOrder, String> colPayment;
    @FXML
    private TableColumn<SalesReturnOrder, String> colInvoiceType;
    @FXML
    private TableColumn<SalesReturnOrder, String> colNotes;
    @FXML
    private TableColumn<SalesReturnOrder, String> colStatus;
    @FXML
    private void create() {
        Callback<SalesReturnOrder, String> callback = new Callback<SalesReturnOrder, String>() {
            @Override
            public String call(SalesReturnOrder purchaseOrder) {
                tableView.getItems().add(purchaseOrder);
                tableView.refresh();
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/sales/SROEditor.fxml"
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
        sroEditorController = loader.getController();
        sroEditorController.initialize(dialog, callback, null,false);
        dialog.setTitle("新建销售退货订单");
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
        SalesReturnOrder po = tableView.getSelectionModel().getSelectedItem();
        if(po == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择销售退货单");
            alert.show();
            return;
        }
        boolean readOnly = po.getStatus().equals(Constants.CLOSED);
        Callback<SalesReturnOrder, String> callback = new Callback<SalesReturnOrder, String>() {
            @Override
            public String call(SalesReturnOrder updated) {
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
                        "/fxml/sales/SROEditor.fxml"
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
        sroEditorController = loader.getController();
        sroEditorController.initialize(dialog, callback, po, readOnly);
        dialog.setTitle("更改销售退货单");
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
        SalesReturnOrder selected = tableView.getSelectionModel().getSelectedItem();
        if(selected != null) {
            if(selected.getStatus().equals(Constants.CLOSED)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
                alert.setHeaderText("无法删除已结算的订单");
                alert.show();
                return;
            }
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
            alertConfirm.setHeaderText("请确认是否要删除退货单？");
            alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {

                //删除订单明细
                try {
                    HttpClient.DELETE("/salesReturnOrdersItems/order/"+selected.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //删除订单
                try {
                    HttpClient.DELETE("/salesReturnOrders/"+selected.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tableView.getItems().remove(selected);
                tableView.refresh();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择销售退货单");
            alert.show();
            return;
        }
    }

    @FXML
    private void refresh() {
        tableView.getItems().clear();
        try {
            SalesReturnOrder[] salesReturnOrders = HttpClient.GET("/salesReturnOrders", SalesReturnOrder[].class);
            tableView.getItems().addAll(salesReturnOrders);
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
        SalesReturnOrder confition = new SalesReturnOrder();
        Consumer consumer = new Consumer();
        consumer.setCode(comboBoxConsumer.getValue());
        confition.setConsumer(consumer);
        confition.setOrderNo(txtNo.getText());
        confition.setOperator(txtOperator.getText());
        confition.setUserName(txtUserName.getText());
        confition.setInvoiceType(comboBoxInvoiceType.getValue());
        confition.setWarehouse(Env.getInstance().currentStore().getWarehouse());
        confition.setPayment(comboBoxPayment.getValue());
        confition.setNotes(txtNotes.getText());
        confition.setStatus(comboBoxStatus.getValue());
        if(fromDate.getValue() != null && toDate.getValue() != null) {
            confition.setFromDate(Date.from(fromDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            confition.setToDate(Date.from(toDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        }
        String json = GoogleJson.GET().toJson(confition);
        String data = null;
        try {
            data = HttpClient.POST("/salesReturnOrders/search", json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SalesReturnOrder[] results = GoogleJson.GET().fromJson(data, SalesReturnOrder[].class);
        tableView.getItems().clear();
        tableView.getItems().addAll(results);
        tableView.refresh();
    }

    @FXML
    private void clearQueryConditions() {
        comboBoxConsumer.setValue(null);
        comboBoxInvoiceType.setValue(null);
        comboBoxStatus.setValue(null);
        txtNo.setText("");
        comboBoxWarehouse.setValue(null);
        txtOperator.setText("");
        comboBoxPayment.setValue("");
        fromDate.setValue(null);
        txtUserName.setText("");
        txtNotes.setText("");
        toDate.setValue(null);
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
                if(!comboBoxConsumer.getItems().contains(returnedConsumer.getCode())) {
                    comboBoxConsumer.getItems().add(0, returnedConsumer.getCode());
                }
                comboBoxConsumer.setValue(returnedConsumer.getCode());
            }
            return null;
        };
        controller.initialize(subStage, callback, null);
        subStage.setTitle("选择客户");
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
        //获取供应商列表
        try {
            Consumer[] consumers = HttpClient.GET("/consumers", Consumer[].class);
            comboBoxConsumer.getItems().addAll(Arrays.asList(consumers).stream().map(e -> e.getCode()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        comboBoxConsumer.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
            }
        });
        comboBoxConsumer.setOnAction(e -> {
        });
        new AutoCompleteBox(comboBoxConsumer);
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

        //账号

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
        SimpleDateFormat format = new SimpleDateFormat(Constants.PATTERN_DATE);
        colOrderDate.setCellValueFactory(param -> {
            if(param.getValue().getOrderDate() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(format.format(param.getValue().getOrderDate()));
            }
        });
        //客户编码
        colConsumerCode.setCellValueFactory(param -> {
            if(param.getValue().getConsumer() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getConsumer().getCode());
            }
        });
        //客户名称
        colConsumerName.setCellValueFactory(param -> {
            if(param.getValue().getConsumer() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getConsumer().getName());
            }
        });
        //退货数量
        colReturnQty.setCellValueFactory(param -> {
            if(param.getValue().getReturnQty() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getReturnQty().intValue()+"");
            }
        });
        //未税金额
        colAmountExcludingTax.setCellValueFactory(param -> {
            if(param.getValue().getAmountExcludingTax() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getAmountExcludingTax().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //含税金额
        colAmountIncludingTax.setCellValueFactory(param -> {
            if(param.getValue().getAmountIncludingTax() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getAmountIncludingTax().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //税额
        colTaxAmount.setCellValueFactory(param -> {
            if(param.getValue().getTaxAmount() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getTaxAmount().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //总额
        colTotalAmount.setCellValueFactory(param -> {
            if(param.getValue().getTotalAmount() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getTotalAmount().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //代垫费用金额
        colDisbursementAmount.setCellValueFactory(param -> {
            if(param.getValue().getDisbursementAmount() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getDisbursementAmount().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //货款已退金额
        colReturnedSalesAmount.setCellValueFactory(param -> {
            if(param.getValue().getTotalReturnedAmount() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getTotalReturnedAmount().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //代垫费用已退金额
        colDisbursementReturnedAmount.setCellValueFactory(param -> {
            if(param.getValue().getDisbursementReturnedAmount() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getDisbursementReturnedAmount().setScale(2, RoundingMode.HALF_UP).toString());
            }
        });
        //已退款总额
        colTotalReturnedAmount.setCellValueFactory(param -> {
            if(param.getValue().getTotalReturnedAmount() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getTotalReturnedAmount().setScale(2, RoundingMode.HALF_UP).toString());
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

        refresh();
    }
}
