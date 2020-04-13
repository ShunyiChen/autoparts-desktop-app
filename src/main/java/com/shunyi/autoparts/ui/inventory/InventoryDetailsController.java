package com.shunyi.autoparts.ui.inventory;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.math.RoundingMode;

/**
 * @Description: 库存明细Controller
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/31 23:07
 * @Version: 1.0
 */
public class InventoryDetailsController {

    private Callback<TableColumn, TableCell> cellFactory;

    /** SKU明细分类 */
    @FXML
    private TreeView<Category> treeView;
    /** 库存商品明细账 */
    @FXML
    private TreeView<Category> treeView1;
    /** SKU明细表 */
    @FXML
    private TableView<SKU> tableViewForSKUDetails;
    /** 库存商品明细账表 */
    @FXML
    private TableView<ProductDetail> tableViewForAccount;
    @FXML
    private TableColumn<SKU, String> colProductCode;
    @FXML
    private TableColumn<SKU, String> colProductName;
    @FXML
    private TableColumn<SKU, String> colProductBarcode;
    @FXML
    private TableColumn<SKU, String> colBrand;
    @FXML
    private TableColumn<SKU, String> colCar;
    @FXML
    private TableColumn<SKU, String> colSupplier;
    @FXML
    private TableColumn<SKU, String> colUnit;
    @FXML
    private TableColumn<SKU, String> colListPrice;
    @FXML
    private TableColumn<SKU, String> colImport;
    @FXML
    private TableColumn<SKU, String> colOrigin;
    @FXML
    private TableColumn colSKUCode;
    @FXML
    private TableColumn colSKUName;
    @FXML
    private TableColumn colSpec;
    @FXML
    private TableColumn colQty;
    @FXML
    private TableColumn colPrice;
    @FXML
    private TableColumn colDiscountedPrice;
    @FXML
    private TableColumn colStatus;
    @FXML
    private TableColumn colSKUBarcode;
    @FXML
    private TextField txtProductCode;
    @FXML
    private TextField txtProductName;
    @FXML
    private TextField txtSKUCode;
    @FXML
    private TextField txtSKUName;
    @FXML
    private TextField txtSupplier;
    @FXML
    private TextField txtBarcode;
    @FXML
    private TextField txtSpec;


    @FXML
    private void searchSKU() {
        tableViewForSKUDetails.getItems().clear();
        SKU searchSKU = new SKU();
        Product product = new Product();
        product.setCode(txtProductCode.getText());
        product.setName(txtProductName.getText());
        Supplier supplier = new Supplier();
        supplier.setName(txtSupplier.getText());
        product.setSupplier(supplier);
        searchSKU.setProduct(product);
        searchSKU.setSkuCode(txtSKUCode.getText());
        searchSKU.setSkuName(txtSKUName.getText());
        searchSKU.setSkuBarCode(txtBarcode.getText());
        searchSKU.setSpecification(txtSpec.getText());
        String json = GoogleJson.GET().toJson(searchSKU);
        String data;
        try {
            data = HttpClient.POST("/sku/search", json);
            SKU[] skuArray = GoogleJson.GET().fromJson(data, SKU[].class);
            tableViewForSKUDetails.getItems().addAll(skuArray);
            tableViewForSKUDetails.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearSKUCondition() {
        txtProductCode.setText("");
        txtProductName.setText("");
        txtSKUCode.setText("");
        txtSKUName.setText("");
        txtSupplier.setText("");
        txtBarcode.setText("");
        txtSpec.setText("");
    }

    @FXML
    private void searchAccount() {

    }

    @FXML
    private void clearAccountCondition() {

    }

    public void prepare(Stage dialog) {
        initTreeViewForSKUDetails();
        initTreeViewForAccount();
        initTableViewForSKUDetails();
        initTableViewForAccount();
    }

    private void initTreeViewForSKUDetails() {
        TreeItem<Category> root = new TreeItem<>(new Category(0L, "所有分类", -1L, Constants.PARENT_TRUE, new Store()));
        initTreeNodes(root);
        treeView.setRoot(root);
        treeView.setCellFactory(p -> new TextFieldTreeCell<>(new StringConverter<>() {

            @Override
            public String toString(Category object) {
                return object.getName();
            }

            @Override
            public Category fromString(String string) {
                p.getEditingItem().getValue().setName(string);
                return p.getEditingItem().getValue();
            }
        }));
        treeView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                TreeItem<Category> item = treeView.getSelectionModel().getSelectedItem();
                try {
                    tableViewForSKUDetails.getItems().clear();
                    SKU[] skuArray = HttpClient.GET("/sku/category/"+item.getValue().getId(), SKU[].class);
                    tableViewForSKUDetails.getItems().addAll(skuArray);
                    tableViewForSKUDetails.refresh();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (event.getButton().equals(MouseButton.SECONDARY) && event.getClickCount() == 1) {
//                Category selectedCategory = treeView.getSelectionModel().getSelectedItem().getValue();
//                if(selectedCategory.getId() == 0L) {
//                    menu.getItems().clear();
//                    menu.getItems().addAll(itemNew, new SeparatorMenuItem(), itemProperties);
//                } else {
//                    menu.getItems().clear();
//                    menu.getItems().addAll(itemNew, new SeparatorMenuItem(), itemRM, itemRN, new SeparatorMenuItem(), itemProperties);
//                }
            }
        });
    }

    private void initTreeViewForAccount() {
//        TreeItem<Category> root = new TreeItem<>(new Category(0L, "所有类目",-1L, Constants.PARENT_TRUE, null, null, null, null, null, null, Constants.DELETE_FLAG_FALSE, null));
//        initTreeNodes(root);
//        treeView1.setRoot(root);
//        treeView1.setCellFactory(p -> new TextFieldTreeCell<>(new StringConverter<>(){
//
//            @Override
//            public String toString(Category object) {
//                return object.getName();
//            }
//
//            @Override
//            public Category fromString(String string) {
//                p.getEditingItem().getValue().setName(string);
//                return p.getEditingItem().getValue();
//            }
//        }));
    }

    /**
     *
     * @param root
     */
    private void initTreeNodes(TreeItem<Category> root) {
        try {
            String path = "/categories/store/"+ Env.getInstance().currentStore().getId();
            String data = HttpClient.GET(path);
            Category[] res = GoogleJson.GET().fromJson(data, Category[].class);
            getNodes(root, res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param parent
     * @param all
     */
    private void getNodes(TreeItem<Category> parent, Category[] all) {
        for(Category sc : all) {
            if(sc.getParentId().equals(parent.getValue().getId())) {
                TreeItem<Category> node = new TreeItem<>(sc);
                parent.getChildren().add(node);
                parent.setExpanded(true);
                getNodes(node, all);
            }
        }
    }

    private void initTableViewForSKUDetails() {
        String css = getClass().getResource("/css/styles.css").toExternalForm();
        tableViewForSKUDetails.getStylesheets().add(css);
        tableViewForSKUDetails.setId("my-table");
        tableViewForSKUDetails.setEditable(true);


        colProductCode.setCellValueFactory(param -> {
            if(param.getValue().getProduct().getCode() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getProduct().getCode());
            }
        });
        colProductName.setCellValueFactory(param -> {
            if(param.getValue().getProduct().getName() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getProduct().getName());
            }
        });
        colProductBarcode.setCellValueFactory(param -> {
            if(param.getValue().getProduct().getBarCode() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getProduct().getBarCode());
            }
        });
//        colBrand.setCellValueFactory(param -> {
//            if(param.getValue().getProduct().getBrandSeries() == null) {
//                return new SimpleObjectProperty<>("");
//            } else {
//                return new SimpleObjectProperty<>(param.getValue().getProduct().getBrandSeries().getChineseName());
//            }
//        });
//        colCar.setCellValueFactory(param -> {
//            if(param.getValue().getProduct().getCar() == null) {
//                return new SimpleObjectProperty<>("");
//            } else {
//                return new SimpleObjectProperty<>(param.getValue().getProduct().getCar().getModel());
//            }
//        });
        colSupplier.setCellValueFactory(param -> {
            if(param.getValue().getProduct().getSupplier() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getProduct().getSupplier().getName());
            }
        });
        colUnit.setCellValueFactory(param -> {
            if(param.getValue().getProduct().getUnit() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getProduct().getUnit());
            }
        });
//        colListPrice.setCellValueFactory(param -> {
//            if(param.getValue().getProduct().getListPrice() == null) {
//                return new SimpleObjectProperty<>("");
//            } else {
//                return new SimpleObjectProperty<>(param.getValue().getProduct().getListPrice().setScale(2, RoundingMode.HALF_UP).toString());
//            }
//        });
//        colImport.setCellValueFactory(param -> {
//            if(param.getValue().getProduct().getImported() == null) {
//                return new SimpleObjectProperty<>("");
//            } else {
//                return new SimpleObjectProperty<>(param.getValue().getProduct().getImported());
//            }
//        });
//        colOrigin.setCellValueFactory(param -> {
//            if(param.getValue().getProduct().getImported() == null) {
//                return new SimpleObjectProperty<>("");
//            } else {
//                return new SimpleObjectProperty<>(param.getValue().getProduct().getOrigin());
//            }
//        });
        colSKUCode.setCellValueFactory(new PropertyValueFactory<SKU, String>("skuCode"));
        colSKUName.setCellValueFactory(new PropertyValueFactory<SKU, String>("skuName"));
        colSpec.setCellValueFactory(new PropertyValueFactory<SKU, String>("specification"));
        colQty.setCellValueFactory(new PropertyValueFactory<SKU, String>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<SKU, String>("price"));
        colDiscountedPrice.setCellValueFactory(new PropertyValueFactory<SKU, String>("discountedPrice"));
        colStatus.setCellValueFactory(new PropertyValueFactory<SKU, String>("status"));
        colSKUBarcode.setCellValueFactory(new PropertyValueFactory<SKU, String>("barCode"));
    }


    private void initTableViewForAccount() {

    }
}
