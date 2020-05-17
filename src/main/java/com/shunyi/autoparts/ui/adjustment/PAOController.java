package com.shunyi.autoparts.ui.adjustment;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.PriceAdjustmentOrder;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
 * @Description: 调整单Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/15
 */
public class PAOController {
    private MainApp application;
    private PAOEditorController paoEditorController;

    @FXML
    private TextField txtNo;
    @FXML
    private TextField txtOperator;
    @FXML
    private TextField txtUserName;
    @FXML
    private ComboBox<String> comboBoxWarehouse;
    @FXML
    private TextField txtNotes;
    @FXML
    private ComboBox<String> comboBoxStatus;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private TableView<PriceAdjustmentOrder> tableView;
    @FXML
    private TableColumn<PriceAdjustmentOrder, String> colOrderNo;
    @FXML
    private TableColumn<PriceAdjustmentOrder, String> colOrderDate;
    @FXML
    private TableColumn<PriceAdjustmentOrder, String> colWarehouseCode;
    @FXML
    private TableColumn<PriceAdjustmentOrder, String> colWarehouseName;
    @FXML
    private TableColumn<PriceAdjustmentOrder, String> colAdjustmentAmount;
    @FXML
    private TableColumn<PriceAdjustmentOrder, String> colOperator;
    @FXML
    private TableColumn<PriceAdjustmentOrder, String> colUserName;
    @FXML
    private TableColumn<PriceAdjustmentOrder, String> colNotes;
    @FXML
    private TableColumn<PriceAdjustmentOrder, String> colStatus;
    @FXML
    private void create() {
        Callback<PriceAdjustmentOrder, String> callback = new Callback<PriceAdjustmentOrder, String>() {
            @Override
            public String call(PriceAdjustmentOrder stockTakingOrder) {
                tableView.getItems().add(stockTakingOrder);
                tableView.refresh();
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/adjustment/PAOEditor.fxml"
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
        paoEditorController = loader.getController();
        paoEditorController.initialize(dialog, callback, null,false);
        dialog.setTitle("新建调价单");
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
        PriceAdjustmentOrder pao = tableView.getSelectionModel().getSelectedItem();
        if(pao == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择调价单");
            alert.show();
            return;
        }
        boolean readOnly = pao.getStatus().equals(Constants.CLOSED);
        Callback<PriceAdjustmentOrder, String> callback = new Callback<PriceAdjustmentOrder, String>() {
            @Override
            public String call(PriceAdjustmentOrder updated) {
                //刷新表格
                int index = tableView.getSelectionModel().getSelectedIndex();
                tableView.getItems().remove(pao);
                tableView.getItems().add(index, updated);
                tableView.getSelectionModel().select(updated);
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/adjustment/PAOEditor.fxml"
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
        paoEditorController = loader.getController();
        paoEditorController.initialize(dialog, callback, pao, readOnly);
        dialog.setTitle("更改调价单");
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
        PriceAdjustmentOrder selected = tableView.getSelectionModel().getSelectedItem();
        if(selected != null) {
            if(selected.getStatus().equals(Constants.CLOSED)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
                alert.setHeaderText("无法删除已结算的订单");
                alert.show();
                return;
            }
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
            alertConfirm.setHeaderText("请确认是否要删除调价单？");
            alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {

                //删除订单明细
                try {
                    HttpClient.DELETE("/priceAdjustmentOrderItems/order/"+selected.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //删除订单
                try {
                    HttpClient.DELETE("/priceAdjustmentOrders/"+selected.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tableView.getItems().remove(selected);
                tableView.refresh();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择调价单");
            alert.show();
            return;
        }
    }

    @FXML
    private void refresh() {
        tableView.getItems().clear();
        try {
            PriceAdjustmentOrder[] priceAdjustmentOrders = HttpClient.GET("/priceAdjustmentOrders", PriceAdjustmentOrder[].class);
            tableView.getItems().addAll(priceAdjustmentOrders);
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
        PriceAdjustmentOrder condition = new PriceAdjustmentOrder();
        condition.setOrderNo(txtNo.getText());
        condition.setOperator(txtOperator.getText());
        condition.setUserName(txtUserName.getText());
        condition.setWarehouse(Env.getInstance().currentStore().getWarehouse());
        condition.setNotes(txtNotes.getText());
        condition.setStatus(comboBoxStatus.getValue());
        if(fromDate.getValue() != null && toDate.getValue() != null) {
            condition.setFromDate(Date.from(fromDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            condition.setToDate(Date.from(toDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        }
        String json = GoogleJson.GET().toJson(condition);
        String data = null;
        try {
            data = HttpClient.POST("/priceAdjustmentOrders/search", json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PriceAdjustmentOrder[] results = GoogleJson.GET().fromJson(data, PriceAdjustmentOrder[].class);
        tableView.getItems().clear();
        tableView.getItems().addAll(results);
        tableView.refresh();
    }

    @FXML
    private void clearQueryConditions() {
        comboBoxStatus.setValue(null);
        txtNo.setText("");
        comboBoxWarehouse.setValue(null);
        txtOperator.setText("");
        fromDate.setValue(null);
        txtUserName.setText("");
        txtNotes.setText("");
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
        SimpleDateFormat format = new SimpleDateFormat(Constants.PATTERN_DATETIME);
        colOrderDate.setCellValueFactory(param -> {
            if(param.getValue().getOrderDate() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(format.format(param.getValue().getOrderDate()));
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
        //仓库编码
        colWarehouseCode.setCellValueFactory(param -> {
            if(param.getValue().getWarehouse().getCode() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getWarehouse().getCode());
            }
        });
        //仓库名称
        colWarehouseName.setCellValueFactory(param -> {
            if(param.getValue().getWarehouse().getName() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getWarehouse().getName());
            }
        });
        //调价金额
        colAdjustmentAmount.setCellValueFactory(param -> {
            if(param.getValue().getAmountOfAdjustment() == null) {
                return new SimpleObjectProperty<>("-----");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getAmountOfAdjustment().toString());
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
