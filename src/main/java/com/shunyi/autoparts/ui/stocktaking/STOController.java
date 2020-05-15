package com.shunyi.autoparts.ui.stocktaking;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.Stocktaking;
import com.shunyi.autoparts.ui.common.vo.StocktakingOrder;
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
 * @Description: 盘点单Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/13
 */
public class STOController {
    private MainApp application;
    private STOEditorController stoEditorController;
    private Stocktaking stocktaking;

    @FXML
    private TextField txtNo;
    @FXML
    private ComboBox<String> comboBoxWarehouse;
    @FXML
    private ComboBox<String> comboBoxStocktaking;
    @FXML
    private TextField txtOperator;
    @FXML
    private TextField txtUserName;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private ComboBox<String> comboBoxStatus;
    @FXML
    private TextField txtNotes;
    @FXML
    private TableView<StocktakingOrder> tableView;
    @FXML
    private TableColumn<StocktakingOrder, String> colOrderNo;
    @FXML
    private TableColumn<StocktakingOrder, String> colOrderDate;
    @FXML
    private TableColumn<StocktakingOrder, String> colWarehouseCode;
    @FXML
    private TableColumn<StocktakingOrder, String> colWarehouseName;
    @FXML
    private TableColumn<StocktakingOrder, String> colStocktakingQty;
    @FXML
    private TableColumn<StocktakingOrder, String> colStocktakingAmount;
    @FXML
    private TableColumn<StocktakingOrder, String> colOperator;
    @FXML
    private TableColumn<StocktakingOrder, String> colUserName;
    @FXML
    private TableColumn<StocktakingOrder, String> colNotes;
    @FXML
    private TableColumn<StocktakingOrder, String> colStatus;
    @FXML
    private void create() {
        Callback<StocktakingOrder, String> callback = new Callback<StocktakingOrder, String>() {
            @Override
            public String call(StocktakingOrder stockTakingOrder) {
                tableView.getItems().add(stockTakingOrder);
                tableView.refresh();
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/stocktaking/STOEditor.fxml"
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
        stoEditorController = loader.getController();
        stoEditorController.initialize(dialog, callback, null,false);
        dialog.setTitle("新建盘点单");
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
        StocktakingOrder po = tableView.getSelectionModel().getSelectedItem();
        if(po == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择盘点单");
            alert.show();
            return;
        }
        boolean readOnly = po.getStatus().equals(Constants.CLOSED);
        Callback<StocktakingOrder, String> callback = new Callback<StocktakingOrder, String>() {
            @Override
            public String call(StocktakingOrder updated) {
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
                        "/fxml/stocktaking/STOEditor.fxml"
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
        stoEditorController = loader.getController();
        stoEditorController.initialize(dialog, callback, po, readOnly);
        dialog.setTitle("更改盘点单");
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
        StocktakingOrder selected = tableView.getSelectionModel().getSelectedItem();
        if(selected != null) {
            if(selected.getStatus().equals(Constants.CLOSED)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
                alert.setHeaderText("无法删除已结算的订单");
                alert.show();
                return;
            }
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
            alertConfirm.setHeaderText("请确认是否要删除盘点单？");
            alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {

                //删除订单明细
                try {
                    HttpClient.DELETE("/stocktakingOrdersItems/order/"+selected.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //删除订单
                try {
                    HttpClient.DELETE("/stocktakingOrders/"+selected.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tableView.getItems().remove(selected);
                tableView.refresh();
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择盘点单");
            alert.show();
            return;
        }
    }

    @FXML
    private void refresh() {
        tableView.getItems().clear();
        try {
            StocktakingOrder[] stocktakingOrders = HttpClient.GET("/stocktakingOrders", StocktakingOrder[].class);
            tableView.getItems().addAll(stocktakingOrders);
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
        StocktakingOrder condition = new StocktakingOrder();
        condition.setOrderNo(txtNo.getText());
        condition.setStocktaking(stocktaking);
        condition.setOperator(txtOperator.getText());
        condition.setUserName(txtUserName.getText());
        condition.setWarehouse(Env.getInstance().currentStore().getWarehouse());
        condition.setNotes(txtNotes.getText());
        if(fromDate.getValue() != null && toDate.getValue() != null) {
            condition.setFromDate(Date.from(fromDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            condition.setToDate(Date.from(toDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        }
        String json = GoogleJson.GET().toJson(condition);
        String data = null;
        try {
            data = HttpClient.POST("/stocktakingOrders/search", json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StocktakingOrder[] results = GoogleJson.GET().fromJson(data, StocktakingOrder[].class);
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

    @FXML
    private void openStocktakingChooser() {
        Callback<Stocktaking, String> callback = new Callback<Stocktaking, String>() {
            @Override
            public String call(Stocktaking param) {
                if(param != null) {
                    if(!comboBoxStocktaking.getItems().contains(param.getName())) {
                        comboBoxStocktaking.getItems().add(0, param.getName());
                    }
                    comboBoxStocktaking.setValue(param.getName());
                }
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/stocktaking/StocktakingChooser.fxml"
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
        StocktakingChooserController controller = loader.getController();
        controller.initialize(dialog, callback, null);
        dialog.setTitle("盘点方式选择器");
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
        //获取状态列表
        comboBoxStatus.getItems().addAll(Constants.CLOSED, Constants.UNCLOSED);
        new AutoCompleteBox(comboBoxStatus);
        //仓库
        comboBoxWarehouse.getItems().add(Env.getInstance().currentStore().getWarehouse().getName());
        //盘点方式
        try {
            Stocktaking[] stocktakings = HttpClient.GET("/stocktaking", Stocktaking[].class);
            comboBoxStocktaking.getItems().addAll(Arrays.asList(stocktakings).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxStocktaking);
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
        //盘点数量
        colStocktakingQty.setCellValueFactory(param -> {
            if(param.getValue().getStocktakingQty() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getStocktakingQty()+"");
            }
        });
        //盘点金额
        colStocktakingAmount.setCellValueFactory(param -> {
            if(param.getValue().getStocktakingAmount() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getStocktakingAmount().setScale(2, RoundingMode.HALF_UP).toString());
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
