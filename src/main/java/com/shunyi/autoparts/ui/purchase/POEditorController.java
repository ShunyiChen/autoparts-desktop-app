package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private PurchaseOrder purchaseOrder;

    @FXML
    private Button btnSubmit;
    @FXML
    private DatePicker orderDate;
    @FXML
    private TextField txtOrderNo;
    @FXML
    private ComboBox<Supplier> comboboxSupplierCode;
    @FXML
    private TextField txtSupplierName;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtPhone;
    @FXML
    private ComboBox comboboxInvoiceType;
    @FXML
    private TextField txtInvoiceNo;
    @FXML
    private TextField txtFreight;
    @FXML
    private TextField txtNotes;
    @FXML
    private ComboBox comboboxPayments;
    @FXML
    private ComboBox comboboxAccount;
    @FXML
    private TextField txtAmountPaid;
    @FXML
    private TextField txtAmountIncludingTax;
    @FXML
    private TextField txtTotalQty;
    @FXML
    private TextField txtDiscountedAmount;
    @FXML
    private TextField txtTotalAmount;
    @FXML
    private TextField txtDiscounts;
    @FXML
    private TextField txtDiscountedAmount2;
    @FXML
    private TextField txtDue;
    @FXML
    private TextField txtOperator;

    @FXML
    private TableView<PurchaseOrderItem> tableView;
    @FXML
    private TableColumn<PurchaseOrderItem, String> colSKUCode;
    @FXML
    private TableColumn<PurchaseOrderItem, String> colSKUName;
    @FXML
    private TableColumn<PurchaseOrderItem, String> colSKUSpec;
    @FXML
    private TableColumn<PurchaseOrderItem, String> colCar;
    @FXML
    private TableColumn<PurchaseOrderItem, String> colBrand;
    @FXML
    private TableColumn<PurchaseOrderItem, String> colBarCode;
    @FXML
    private TableColumn<PurchaseOrderItem, String> colUnit;
    @FXML
    private TableColumn<PurchaseOrderItem, String> colQty;
    @FXML
    private TableColumn<PurchaseOrderItem, String> colPrice;
    @FXML
    private TableColumn colAmount;
    @FXML
    private TableColumn<PurchaseOrderItem, String> colDiscountedPrice;
    @FXML
    private TableColumn colDiscountedAmount;
    @FXML
    private TableColumn colCurrentQty;
    @FXML
    private TableColumn<PurchaseOrderItem, String> colSKUSlot;


    public void prepare(Stage dialog, Callback<PurchaseOrder, String> callback, PurchaseOrder purchaseOrder) {
        this.dialog = dialog;
        this.callback = callback;
        this.purchaseOrder = purchaseOrder;
        initInputFields();
        initTable();
    }

    private void initInputFields() {
        orderDate.setValue(LocalDate.now());
        comboboxSupplierCode.setStyle("-fx-font-size: 14;");
        comboboxInvoiceType.setStyle("-fx-font-size: 14;");
        comboboxInvoiceType.getItems().add("普通发票");
        comboboxInvoiceType.getSelectionModel().select(0);
        comboboxPayments.getItems().add("现金");
        comboboxPayments.getSelectionModel().select(0);
        comboboxAccount.getItems().add("00000000");
        comboboxAccount.getSelectionModel().select(0);
        txtOperator.setText(Env.getInstance().currentUser());
        txtOperator.setEditable(false);
        txtFreight.setText("0.00");
        txtAmountPaid.setText("0.00");
        txtAmountIncludingTax.setText("0.00");
        txtTotalQty.setText("0.00");
        txtDiscountedAmount.setText("0.00");
        txtTotalAmount.setText("0.00");
        txtDiscounts.setText("0.00");
        txtDiscountedAmount2.setText("0.00");
        txtDue.setText("0.00");
        txtFreight.setEditable(false);
        txtAmountPaid.setEditable(false);
        txtTotalQty.setEditable(false);
        txtDiscountedAmount.setEditable(false);
        txtTotalAmount.setEditable(false);
        txtDiscounts.setEditable(false);
        txtDiscountedAmount2.setEditable(false);
        txtDue.setEditable(false);
        txtAmountIncludingTax.setEditable(false);
        btnSubmit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));

        try {
            User user = HttpClient.GET("/users/username/"+Env.getInstance().currentUser(), User.class);
            String generatedOrderNo = HttpClient.GET("/purchaseOrders/orderNo/"+user.getId());
            txtOrderNo.setText(generatedOrderNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        comboboxSupplierCode.setOnKeyReleased(e -> {
            if(!e.getCode().isArrowKey()) {
                comboboxSupplierCode.getItems().clear();
                Supplier supplier = new Supplier();
                supplier.setName(comboboxSupplierCode.getEditor().getText());
                String json = GoogleJson.GET().toJson(supplier);
                String data;
                try {
                    data = HttpClient.POST("/suppliers/search", json);
                    Supplier[] suppliers = GoogleJson.GET().fromJson(data, Supplier[].class);
                    comboboxSupplierCode.getItems().addAll(suppliers);
                    comboboxSupplierCode.show();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });

        comboboxSupplierCode.setOnAction(e -> {
            if(comboboxSupplierCode.getValue() instanceof Supplier) {
                Supplier supplier = comboboxSupplierCode.getValue();
                txtSupplierName.setText(supplier.getName());
                txtContact.setText(supplier.getContact());
                txtPhone.setText(supplier.getPhone1()+","+supplier.getPhone2());
            }

        });
    }

    private void initTable() {

        tableView.setId("my-table");
        colSKUCode.setCellValueFactory(param -> {
            if(param.getValue().getSku().getSkuCode() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getSkuCode() );
            }
        });
        colSKUName.setCellValueFactory(param -> {
            if(param.getValue().getSku().getSkuName() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getSkuName() );
            }
        });
        colSKUSpec.setCellValueFactory(param -> {
            if(param.getValue().getSku().getSpecification() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getSpecification() );
            }
        });
        colCar.setCellValueFactory(param -> {
            if(param.getValue().getSku().getProduct().getCar() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getCar().getModel());
            }
        });
        colBrand.setCellValueFactory(param -> {
            if(param.getValue().getSku().getProduct().getBrandSeries() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getProduct().getBrandSeries().getChineseName());
            }
        });
        colBarCode.setCellValueFactory(param -> {
            if(param.getValue().getSku().getBarCode() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getBarCode());
            }
        });
        colUnit.setCellValueFactory(param -> {
            if(param.getValue().getSku().getUnit() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getUnit());
            }
        });
        colQty.setCellValueFactory(param -> {
            if(param.getValue().getSku().getQuantity() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getQuantity().toString());
            }
        });
        colPrice.setCellValueFactory(param -> {
            if(param.getValue().getSku().getPrice() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSku().getPrice().toString());
            }
        });
    }

    @FXML
    private void addItem() {
        SKU sku = new SKU();
        Product product = new Product();
        BrandSeries b = new BrandSeries();
        b.setChineseName("博士");
        product.setBrandSeries(b);
        Car car = new Car();
        product.setCar(car);
        sku.setProduct(product);

        PurchaseOrderItem item = new PurchaseOrderItem(Constants.ID, purchaseOrder, sku, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,"");
        tableView.getItems().add(item);
        tableView.refresh();
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


}
