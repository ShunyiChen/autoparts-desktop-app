package com.shunyi.autoparts.ui.stocktaking;

import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.SKU;
import com.shunyi.autoparts.ui.common.vo.StocktakingOrder;
import com.shunyi.autoparts.ui.common.vo.StocktakingOrderItem;
import com.shunyi.autoparts.ui.common.vo.User;
import com.shunyi.autoparts.ui.products.ProductChooserController;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 销盘点单编辑器Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/14
 */
public class STOEditorController {
    /** 表单内容只读 */
    private boolean readOnly;
    private Stage dialog;
    private Callback<StocktakingOrder, String> callback;
    private Callback<TableColumn<StocktakingOrderItem, String>, TableCell<StocktakingOrderItem, String>> cellFactory;
    private List<Long> deletedIds = new ArrayList<>();
    /** 盘点单 */
    private StocktakingOrder so;
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
    /** 配件条码 */
    @FXML
    private TextField txtProductBarcode;
    /** 仓库 */
    @FXML
    private ComboBox<String> comboBoxWarehouse;
    /** 盘点说明 */
    @FXML
    private TextField txtNotes;
    /** 经办人 */
    @FXML
    private TextField txtOperator;
    /** 操作员 */
    @FXML
    private TextField txtUserName;
    /** 采购订单明细表 */
    @FXML
    private TableView<StocktakingOrderItem> tableView;
    /** 行号 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colRowNumber;
    /** SKU编码 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colSkuCode;
    /** SKU名称 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colSkuName;
    /** 规格 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colSpecification;
    /** 单位 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colUnit;
    /** 点货数量 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colQty;
    /** 点货时间 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colDate;
    /** 车型 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colCar;
    /** 品牌 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colBrand;
    /** 账面 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colAccount;
    /** 账面-数量 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colAccountQty;
    /** 账面-金额 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colAccountAmount;
    /** 调整 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colAdjustment;
    /** 调整-数量 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colAdjustmentQty;
    /** 调整-单价 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colAdjustmentPrice;
    /** 调整-金额 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colAdjustmentAmount;
    /** 实际 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colReality;
    /** 实际-数量 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colRealityQty;
    /** 实际-金额 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colRealityAmount;
    /** 产地 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colPlace;
    /** 备注 */
    @FXML
    private TableColumn<StocktakingOrderItem, String> colNotes;

    /**
     * Constructor
     *
     * @param dialog
     * @param callback
     * @param so
     * @param readOnly
     */
    public void initialize(Stage dialog, Callback<StocktakingOrder, String> callback, StocktakingOrder so, boolean readOnly) {
        this.dialog = dialog;
        this.callback = callback;
        this.so = so;
        this.readOnly = readOnly;
        btnSubmit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        initCellFactory();
        initInputFields();
        initTable();
        if(so != null) {
            //单据日期
            LocalDate localDate = so.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            orderDate.setValue(localDate);
            //单号
            txtOrderNo.setText(so.getOrderNo());
//            //客户编码
//            comboBoxConsumerCode.setValue(pro.getConsumer().getCode());
//            //发票类型
//            comboBoxInvoiceType.setValue(pro.getInvoiceType());
//            //发票No
//            txtInvoiceNo.setText(pro.getInvoiceNo());
//            //运费
//            txtFreight.setText(pro.getFreight()+"");
            //备注
            txtNotes.setText(so.getNotes());
            //经办人
            txtOperator.setText(so.getOperator());
//            //结算方式
//            comboBoxPayments.setValue(pro.getPayment());
//            //系统账号
//            txtLoginAccount.setText(pro.getUserName());
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
                StocktakingOrderItem[] items = HttpClient.GET("/stocktakingOrderItems/order/"+so.getId(), StocktakingOrderItem[].class);
                for(StocktakingOrderItem item : items) {
                    addItem(item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            addItem();
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

    private void initInputFields() {
        //订单日期
        orderDate.setValue(LocalDate.now());
        //单号
        try {
            User user = HttpClient.GET("/users/username/"+Env.getInstance().currentUser(), User.class);
            String generatedOrderNo = HttpClient.GET("/stocktakingOrders/generation/orderNo/"+user.getId());
            txtOrderNo.setText(generatedOrderNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //仓库
        comboBoxWarehouse.getItems().add(Env.getInstance().currentStore().getWarehouse().getName());
        comboBoxWarehouse.setStyle("-fx-font-size: 14px;");
        comboBoxWarehouse.getSelectionModel().select(0);
        //登录账号
        txtUserName.setText(Env.getInstance().currentUser());
    }

    private void initTable() {
        SimpleDateFormat format = new SimpleDateFormat(Constants.PATTERN_DATE);
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
        colSkuCode.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<StocktakingOrderItem, String>>) t -> {
            ObservableList<StocktakingOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    StocktakingOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String skuCode = t.getNewValue();
                        try {
                            String json = HttpClient.GET("/sku/code/"+skuCode);
                            SKU sku = GoogleJson.GET().fromJson(json, SKU.class);
                            if(sku.getId() != null) {
                                selected.setSku(sku);
                                selected.setStocktakingQty(0);
                                selected.setStocktakingDate(new Date());
                                data.set(t.getTablePosition().getRow(), selected);

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
        //点货数量
        colQty.setCellFactory(cellFactory);
        colQty.setCellValueFactory(new PropertyValueFactory<>("stocktakingQty"));
        colQty.setOnEditCommit(t -> {
            ObservableList<StocktakingOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    StocktakingOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        if(NumberValidationUtils.isPositiveInteger(newValue)) {
                            //手动输入点货数量
                            selected.setStocktakingQty(Integer.parseInt(newValue));
                            data.set(t.getTablePosition().getRow(), selected);
                        }
                    }
                }
            }
        });
        //点货时间
        colDate.setCellFactory(TextFieldTableCell.forTableColumn());
        colDate.setCellValueFactory(param -> {
            if(param.getValue().getStocktakingDate() == null) {
                return new SimpleObjectProperty<>(format.format(new Date()));
            } else {
                return new SimpleObjectProperty<>(format.format(param.getValue().getStocktakingDate()));
            }
        });
        colDate.setOnEditCommit(t -> {
            ObservableList<StocktakingOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    StocktakingOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        System.out.println("newValue="+newValue);
                        //手动输入点货时间
                        try {
                            selected.setStocktakingDate(format.parse(newValue));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        data.set(t.getTablePosition().getRow(), selected);
                    }
                }
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
        //品牌
        colBrand.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getBrand() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getBrand().getName());
            }
        });
        //账面数量
        colAccountQty.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getBrand() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getStockQty().toString());
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
        //备注
        colNotes.setCellFactory(cellFactory);
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        colNotes.setOnEditCommit(t -> {
            ObservableList<StocktakingOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    StocktakingOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        selected.setNotes(newValue);
                        data.set(t.getTablePosition().getRow(), selected);
                    }
                }
            }
        });
//        tableView.setOnMouseClicked((MouseEvent event) -> {
//            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
//                TablePosition tp = tableView.getFocusModel().getFocusedCell();
//                if(tp.getTableColumn() == colDate) {
//                }
//            }
//        });
    }

    /**
     *
     * @param item
     */
    private void addItem(StocktakingOrderItem item) {
        tableView.getItems().add(item);
        tableView.refresh();
        tableView.getSelectionModel().select(item);
    }

    @FXML
    private void addItem() {
        StocktakingOrderItem item = new StocktakingOrderItem(Constants.ID, null, null, Constants.ZERO, null, "");
        addItem(item);
    }

    @FXML
    private void removeItem() {
        StocktakingOrderItem selectedItem = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selectedItem);
        tableView.refresh();
        //记录删除ID,用于保存更新
        deletedIds.add(selectedItem.getId());
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    /**
     * 确认提交
     */
    private void confirmAndSubmit() {
        String header = "盘点单: "+txtOrderNo.getText()+"\n请谨慎确定以下数据:\n";
        StringBuilder content = new StringBuilder();
        content.append("============================================\n");
//        content.append("\n退货总金额: "+labelTotalAmount.getText()+" 元 \n");
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
     * 更改库存数
     *
     * @param status
     * @param item
     */
    private void updateStockQty(String status, StocktakingOrderItem item) {
        //如果结算，则覆盖原有库存数
        if(status.equals(Constants.CLOSED)) {
            String json2 = GoogleJson.GET().toJson(item.getStocktakingQty());
            try {
                HttpClient.PUT("/sku/stockQty/overwrite/"+item.getSku().getId(), json2);
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
        if(so == null) {
            //插入新记录
            so = new StocktakingOrder();
            LocalDate localDate = orderDate.getValue();
            Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            //日期
            so.setOrderDate(date);
            //单号
            so.setOrderNo(txtOrderNo.getText());
            //配件条码
            so.setProductBarcode(txtProductBarcode.getText());
            //仓库
            so.setWarehouse(Env.getInstance().currentStore().getWarehouse());
            //盘点说明
            so.setNotes(txtNotes.getText());
            //经办人
            so.setOperator(txtOperator.getText());
            //操作员
            so.setUserName(Env.getInstance().currentUser());
            //状态
            so.setStatus(status);
            //


            //创建盘点单对象
            String json = GoogleJson.GET().toJson(so);
            try {
                String idStr = HttpClient.POST("/stocktakingOrders", json);
                so.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //创建销售退货单明细
            ObservableList<StocktakingOrderItem> items = tableView.getItems();
            items.forEach(e -> {
                if(e.getSku() != null) {
                    e.setStocktakingOrder(so);
                    String data = GoogleJson.GET().toJson(e);
                    try {
                        String idStr = HttpClient.POST("/stocktakingOrderItems", data);
                        e.setId(Long.valueOf(idStr));
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    //更新库存数量
                    updateStockQty(status, e);
                }
            });
            callback.call(so);
        } else {
            //更新原有记录
            StocktakingOrder so = new StocktakingOrder();
            so.setId(this.so.getId());
            LocalDate localDate = orderDate.getValue();
            Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            //日期
            so.setOrderDate(date);
            //单号
            so.setOrderNo(txtOrderNo.getText());
            //配件条码
            so.setProductBarcode(txtProductBarcode.getText());
            //仓库
            so.setWarehouse(Env.getInstance().currentStore().getWarehouse());
            //盘点说明
            so.setNotes(txtNotes.getText());
            //经办人
            so.setOperator(txtOperator.getText());
            //操作员
            so.setUserName(Env.getInstance().currentUser());
            //状态
            so.setStatus(status);
            String json = GoogleJson.GET().toJson(so);
            try {
                HttpClient.PUT("/stocktakingOrders/"+so.getId(), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //更新或创建销售退货单明细
            ObservableList<StocktakingOrderItem> items = tableView.getItems();
            items.forEach(e -> {
                if(e.getSku() != null) {
                    e.setStocktakingOrder(so);
                    String data = GoogleJson.GET().toJson(e);
                    if(e.getId() == 0L) {
                        try {
                            String idStr = HttpClient.POST("/stocktakingOrderItems", data);
                            e.setId(Long.valueOf(idStr));
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        try {
                            HttpClient.PUT("/stocktakingOrderItems/"+e.getId(), data);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    //更新库存数量
                    updateStockQty(status, e);
                }
            });
            //删除已经移除的行
            deletedIds.forEach(StocktakingOrderItemId -> {
                try {
                    HttpClient.DELETE("/stocktakingOrderItems/"+StocktakingOrderItemId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            callback.call(so);
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
                StocktakingOrderItem item = tableView.getSelectionModel().getSelectedItem();
                item.setSku(sku);
                tableView.refresh();
                tableView.getSelectionModel().select(item);
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
