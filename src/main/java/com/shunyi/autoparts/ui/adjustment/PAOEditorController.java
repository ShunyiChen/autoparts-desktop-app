package com.shunyi.autoparts.ui.adjustment;

import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.SKU;
import com.shunyi.autoparts.ui.common.vo.PriceAdjustmentOrder;
import com.shunyi.autoparts.ui.common.vo.PriceAdjustmentOrderItem;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 调整单编辑器Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/15
 */
public class PAOEditorController {
    /** 表单内容只读 */
    private boolean readOnly;
    private Stage dialog;
    private Callback<PriceAdjustmentOrder, String> callback;
    private Callback<TableColumn<PriceAdjustmentOrderItem, String>, TableCell<PriceAdjustmentOrderItem, String>> cellFactory;
    private List<Long> deletedIds = new ArrayList<>();
    /** 调价单 */
    private PriceAdjustmentOrder pao;
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
    /** 仓库 */
    @FXML
    private ComboBox<String> comboBoxWarehouse;
    /** 盘点说明 */
    @FXML
    private TextField txtNotes;
    /** 调整金额合计 */
    @FXML
    private TextField txtTotalAdjustedAmount;
    /** 经办人 */
    @FXML
    private TextField txtOperator;
    /** 操作员 */
    @FXML
    private TextField txtUserName;
    /** 采购订单明细表 */
    @FXML
    private TableView<PriceAdjustmentOrderItem> tableView;
    /** 行号 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colRowNumber;
    /** SKU编码 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colSkuCode;
    /** SKU名称 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colSkuName;
    /** 规格 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colSpecification;
    /** 单位 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colUnit;
    /** 库存数量 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colStockQty;
    /** 库存平均价 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colAvgPrice;
    /** 调整价 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colAdjustedPrice;
    /** 现库存金额 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colCurrentAmount;
    /** 调整金额 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colAdjustedAmount;
    /** 进口 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colImport;
    /** 车型 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colCar;
    /** 品牌 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colBrand;
    /** 产地 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colPlace;
    /** 备注 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colNotes;
    /** 通用件号 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colCommonNumber;
    /** 通用车型 */
    @FXML
    private TableColumn<PriceAdjustmentOrderItem, String> colRelevantModels;

    /**
     * Constructor
     *
     * @param dialog
     * @param callback
     * @param pao
     * @param readOnly
     */
    public void initialize(Stage dialog, Callback<PriceAdjustmentOrder, String> callback, PriceAdjustmentOrder pao, boolean readOnly) {
        this.dialog = dialog;
        this.callback = callback;
        this.pao = pao;
        this.readOnly = readOnly;
        btnSubmit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        initCellFactory();
        initInputFields();
        initTable();
        if(pao != null) {
            //单据日期
            LocalDate localDate = pao.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            orderDate.setValue(localDate);
            //单号
            txtOrderNo.setText(pao.getOrderNo());
//            //客户编码
//            comboBoxConsumerCode.setValue(pro.getConsumer().getCode());
//            //发票类型
//            comboBoxInvoiceType.setValue(pro.getInvoiceType());
//            //发票No
//            txtInvoiceNo.setText(pro.getInvoiceNo());
//            //运费
//            txtFreight.setText(pro.getFreight()+"");
            //备注
            txtNotes.setText(pao.getNotes());
            //经办人
            txtOperator.setText(pao.getOperator());
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
                PriceAdjustmentOrderItem[] items = HttpClient.GET("/priceAdjustmentOrderItems/order/"+pao.getId(), PriceAdjustmentOrderItem[].class);
                for(PriceAdjustmentOrderItem item : items) {
                    addItem(item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            updateSummary();

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
            String generatedOrderNo = HttpClient.GET("/priceAdjustmentOrders/generation/orderNo/"+user.getId());
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
        colSkuCode.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<PriceAdjustmentOrderItem, String>>) t -> {
            ObservableList<PriceAdjustmentOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    PriceAdjustmentOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String skuCode = t.getNewValue();
                        try {
                            String json = HttpClient.GET("/sku/code/"+skuCode);
                            SKU sku = GoogleJson.GET().fromJson(json, SKU.class);
                            if(sku.getId() != null) {
                                selected.setSku(sku);
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
        //库存数量
        colStockQty.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getStockQty()+"");
            }
        });
        //库存平均价
        colAvgPrice.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getStockQty()+"");
            }
        });
        //调整价
        colAdjustedPrice.setCellFactory(cellFactory);
        colAdjustedPrice.setCellValueFactory(new PropertyValueFactory<>("priceOfAdjustment"));
        colAdjustedPrice.setOnEditCommit(t -> {
            ObservableList<PriceAdjustmentOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    PriceAdjustmentOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        selected.setPriceOfAdjustment(new BigDecimal(newValue).setScale(2, RoundingMode.HALF_UP));
                        data.set(t.getTablePosition().getRow(), selected);
                        updateSummary();
                    }
                }
            }
        });
        //现库存金额
        colCurrentAmount.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getStockQty()+"");
            }
        });
        //调整金额
        colAdjustedAmount.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(new BigDecimal(param.getValue().getSku().getStockQty()).multiply(param.getValue().getPriceOfAdjustment())+"");
            }
        });
        //备注
        colNotes.setCellFactory(cellFactory);
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        colNotes.setOnEditCommit(t -> {
            ObservableList<PriceAdjustmentOrderItem> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    PriceAdjustmentOrderItem selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        String newValue = t.getNewValue();
                        selected.setNotes(newValue);
                        data.set(t.getTablePosition().getRow(), selected);
                    }
                }
            }
        });
        //进口
        colImport.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getImported()+"");
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
        //产地
        colPlace.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null || param.getValue().getSku().getProduct().getPlace() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getPlace().getName());
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
        //通用车型
        colRelevantModels.setCellValueFactory(param -> {
            if(param.getValue().getSku() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getRelevantModels());
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

    private void updateSummary() {
        int totalRecords = 0;
//        BigDecimal totalQty = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        ObservableList<PriceAdjustmentOrderItem> list = tableView.getItems();
        for(PriceAdjustmentOrderItem item : list) {
            totalRecords++;
//            totalQty = totalQty.add(new BigDecimal(item.getSku().getStockQty()));
            totalAmount = totalAmount.add(item.getPriceOfAdjustment().multiply(new BigDecimal(item.getSku().getStockQty())));
        }
//        labelRecords.setText(totalRecords+"");
//        labelTotalQty.setText(totalQty.toString());
        txtTotalAdjustedAmount.setText(totalAmount.toString());
    }

    /**
     *
     * @param item
     */
    private void addItem(PriceAdjustmentOrderItem item) {
        tableView.getItems().add(item);
        tableView.refresh();
        tableView.getSelectionModel().select(item);
    }

    @FXML
    private void addItem() {
        PriceAdjustmentOrderItem item = new PriceAdjustmentOrderItem(Constants.ID, null, null, BigDecimal.ZERO,"");
        addItem(item);
    }

    @FXML
    private void removeItem() {
        PriceAdjustmentOrderItem selectedItem = tableView.getSelectionModel().getSelectedItem();
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
        String header = "调价单: "+txtOrderNo.getText()+"\n请谨慎确定以下数据:\n";
        StringBuilder content = new StringBuilder();
        content.append("============================================\n");
        content.append("\n调价总金额: "+txtTotalAdjustedAmount.getText()+" 元 \n");
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
    private void updateStockQty(String status, PriceAdjustmentOrderItem item) {
        //如果结算，则覆盖原有库存数
//        if(status.equals(Constants.CLOSED)) {
//            String json2 = GoogleJson.GET().toJson(item.getStocktakingQty());
//            try {
//                HttpClient.PUT("/sku/stockQty/overwrite/"+item.getSku().getId(), json2);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
    }

    /**
     * 插入或更新
     *
     * @param status
     */
    private void saveOrUpdate(String status) {
        if(pao == null) {
            //插入新记录
            pao = new PriceAdjustmentOrder();
            LocalDate localDate = orderDate.getValue();
            Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            //日期
            pao.setOrderDate(date);
            //单号
            pao.setOrderNo(txtOrderNo.getText());
            //仓库
            pao.setWarehouse(Env.getInstance().currentStore().getWarehouse());
            //调价说明
            pao.setNotes(txtNotes.getText());
            //合计调整金额
            pao.setAmountOfAdjustment(new BigDecimal(txtTotalAdjustedAmount.getText()));
            //经办人
            pao.setOperator(txtOperator.getText());
            //操作员
            pao.setUserName(Env.getInstance().currentUser());
            //状态
            pao.setStatus(status);
            //创建调价单对象
            String json = GoogleJson.GET().toJson(pao);
            try {
                String idStr = HttpClient.POST("/priceAdjustmentOrders", json);
                pao.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //创建销售退货单明细
            ObservableList<PriceAdjustmentOrderItem> items = tableView.getItems();
            items.forEach(e -> {
                if(e.getSku() != null) {
                    e.setPriceAdjustmentOrder(pao);
                    String data = GoogleJson.GET().toJson(e);
                    try {
                        String idStr = HttpClient.POST("/priceAdjustmentOrderItems", data);
                        e.setId(Long.valueOf(idStr));
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    //更新库存数量
                    updateStockQty(status, e);
                }
            });
            callback.call(pao);
        } else {
            //更新原有记录
            PriceAdjustmentOrder pao = new PriceAdjustmentOrder();
            pao.setId(this.pao.getId());
            LocalDate localDate = orderDate.getValue();
            Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            //日期
            pao.setOrderDate(date);
            //单号
            pao.setOrderNo(txtOrderNo.getText());
            //仓库
            pao.setWarehouse(Env.getInstance().currentStore().getWarehouse());
            //调价说明
            pao.setNotes(txtNotes.getText());
            //合计调整金额
            pao.setAmountOfAdjustment(new BigDecimal(txtTotalAdjustedAmount.getText()));
            //经办人
            pao.setOperator(txtOperator.getText());
            //操作员
            pao.setUserName(Env.getInstance().currentUser());
            //状态
            pao.setStatus(status);
            String json = GoogleJson.GET().toJson(pao);
            try {
                HttpClient.PUT("/priceAdjustmentOrders/"+pao.getId(), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //更新或创建销售退货单明细
            ObservableList<PriceAdjustmentOrderItem> items = tableView.getItems();
            items.forEach(e -> {
                if(e.getSku() != null) {
                    e.setPriceAdjustmentOrder(pao);
                    String data = GoogleJson.GET().toJson(e);
                    if(e.getId() == 0L) {
                        try {
                            String idStr = HttpClient.POST("/priceAdjustmentOrderItems", data);
                            e.setId(Long.valueOf(idStr));
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        try {
                            HttpClient.PUT("/priceAdjustmentOrderItems/"+e.getId(), data);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    //更新库存数量
                    updateStockQty(status, e);
                }
            });
            //删除已经移除的行
            deletedIds.forEach(PriceAdjustmentOrderItemId -> {
                try {
                    HttpClient.DELETE("/priceAdjustmentOrderItems/"+PriceAdjustmentOrderItemId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            callback.call(pao);
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
                PriceAdjustmentOrderItem item = tableView.getSelectionModel().getSelectedItem();
                item.setSku(sku);
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
