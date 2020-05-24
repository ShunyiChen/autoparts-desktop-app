package com.shunyi.autoparts.ui.inventory;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.*;
import com.shunyi.autoparts.ui.products.CarChooserController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 库存配件Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/18
 */
public class IAController {
    private Callback<TableColumn, TableCell> cellFactory;
    private MainApp application;
    private Car car;

    @FXML
    private TextField txtSKUCode;
    @FXML
    private TextField txtSKUName;
    @FXML
    private Spinner spinnerTaxRate;
    @FXML
    private ComboBox<String> comboBoxBrand;
    @FXML
    private TextField txtSKUBarcode;
    @FXML
    private ComboBox<String> comboBoxCar;
    @FXML
    private ComboBox<String> comboBoxShortage;
    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;
    @FXML
    private ComboBox<String> comboBoxCompany;

    /** 库存配件分类 */
    @FXML
    private TreeView<Category> treeView;
    /** 库存配件 */
    @FXML
    private TableView<SKU> tableView;
    @FXML
    private TableColumn<SKU, String> colRowNumber;
    @FXML
    private TableColumn<SKU, String> colSKUCode;
    @FXML
    private TableColumn<SKU, String> colSKUName;
    @FXML
    private TableColumn<SKU, String> colSKUBarcode;
    @FXML
    private TableColumn<SKU, String> colUnit;
    @FXML
    private TableColumn<SKU, String> colStockQty;
    @FXML
    private TableColumn<SKU, String> colAvgPrice;
    @FXML
    private TableColumn<SKU, String> colAmount;
    @FXML
    private TableColumn<SKU, String> colCar;
    @FXML
    private TableColumn<SKU, String> colProductivePlace;
    @FXML
    private TableColumn<SKU, String> colBrand;
    @FXML
    private TableColumn<SKU, String> colSupplier;
    @FXML
    private TableColumn<SKU, String> colImport;
    @FXML
    private TableColumn<SKU, String> colSpec;
    @FXML
    private TableColumn<SKU, String> colSlot;
    /** 子表格 */
    @FXML
    private TableView<SKU> subTableView;
    @FXML
    private TableColumn<SKU, String> colCode;
    @FXML
    private TableColumn<SKU, String> colWarehouse;
    @FXML
    private TableColumn<SKU, String> colStorageLocation;
    @FXML
    private TableColumn<SKU, String> colCurrentStockQty;
    @FXML
    private TableColumn<SKU, String> colAvgUnitPrice;
    @FXML
    private TableColumn<SKU, String> colSum;
    @FXML
    private TableColumn<SKU, String> colAvailableStockQty;
    @FXML
    private TableColumn<SKU, String> colTakeInventoryQty;
    @FXML
    private TableColumn<SKU, String> colDistributionInventoryQty;
    @FXML
    private TableColumn<SKU, String> colOrderingQty;
    @FXML
    private TableColumn<SKU, String> colUpperLimitQty;
    @FXML
    private TableColumn<SKU, String> colLowerLimitQty;
    @FXML
    private TableColumn<SKU, String> colInventoryWarningQty;


    public void initialize(MainApp application) {
        this.application = application;
        initTextFields();
        initTreeView();
        initTableView();
    }

    private void initTextFields() {
        try {
            Car[] cars = HttpClient.GET("/cars", Car[].class);
            comboBoxCar.getItems().addAll(Arrays.asList(cars).stream().map(e ->e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Company[] companies = HttpClient.GET("/companies", Company[].class);
            comboBoxCompany.getItems().addAll(Arrays.asList(companies).stream().map(e ->e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Brand[] brands = HttpClient.GET("/brands", Brand[].class);
            comboBoxBrand.getItems().addAll(Arrays.asList(brands).stream().map(e ->e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ToggleGroup tg = new ToggleGroup();
        radioButton1.setToggleGroup(tg);
        radioButton2.setToggleGroup(tg);
        radioButton1.setSelected(true);
        comboBoxShortage.getItems().addAll("是","否");
        comboBoxShortage.getSelectionModel().select(1);
        spinnerTaxRate.setStyle("-fx-font-size: 14px;");
        spinnerTaxRate.setEditable(true);
        spinnerTaxRate.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,17,1));
        new AutoCompleteBox(comboBoxCar);
        new AutoCompleteBox(comboBoxShortage);
        new AutoCompleteBox(comboBoxCompany);
        new AutoCompleteBox(comboBoxBrand);
    }

    @FXML
    private void clearSearchCondition() {
        txtSKUCode.setText("");
        txtSKUName.setText("");
        spinnerTaxRate.getValueFactory().setValue(17);
        txtSKUBarcode.setText("");
        comboBoxCar.setValue(null);
        comboBoxBrand.setValue(null);
        comboBoxShortage.setValue(null);
        comboBoxCompany.setValue(null);
        radioButton1.setSelected(true);
    }

    @FXML
    private void openCarChooser() {
        Callback<List<Car>, String> callback = new Callback<List<Car>, String>() {
            @Override
            public String call(List<Car> param) {
                if(param.size() > 0) {
                    comboBoxCar.setValue(param.get(0).getName());
                }
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/CarChooser.fxml"
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
        CarChooserController controller = loader.getController();
        controller.initialize(dialog, callback, car, false);
        dialog.setTitle("车型选择器");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void searchSKU() {
        tableView.getItems().clear();
        SKU searchSKU = new SKU();
        Product product = new Product();
//        product.setCode(txtProductCode.getText());
//        product.setName(txtProductName.getText());
        Supplier supplier = new Supplier();
//        supplier.setName(txtSupplier.getText());
        product.setSupplier(supplier);
        searchSKU.setProduct(product);
        searchSKU.setSkuCode(txtSKUCode.getText());
        searchSKU.setSkuName(txtSKUName.getText());
//        searchSKU.setSkuBarCode(txtBarcode.getText());
//        searchSKU.setSpecification(txtSpec.getText());
        String json = GoogleJson.GET().toJson(searchSKU);
        String data;
        try {
            data = HttpClient.POST("/sku/search", json);
            SKU[] skuArray = GoogleJson.GET().fromJson(data, SKU[].class);
            tableView.getItems().addAll(skuArray);
            tableView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTreeView() {
        Category rootCategory = null;
        try {
            rootCategory = HttpClient.GET("/category/root/"+Env.getInstance().currentStore().getWarehouse().getId(), Category.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TreeItem<Category> root = new TreeItem<>(rootCategory);
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
                if(item != null) {
                    try {
                        tableView.getItems().clear();
                        SKU[] skuArray = HttpClient.GET("/sku/category/"+item.getValue().getId(), SKU[].class);
                        tableView.getItems().addAll(skuArray);
                        tableView.refresh();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    /**
     *
     * @param root
     */
    private void initTreeNodes(TreeItem<Category> root) {
        try {
            String data = HttpClient.GET("/categories/warehouse/"+Env.getInstance().currentStore().getWarehouse().getId());
            Category[] res = GoogleJson.GET().fromJson(data, Category[].class);
            getNodes(root, res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void initTableView() {
        String css = getClass().getResource("/css/styles.css").toExternalForm();
        tableView.getStylesheets().add(css);
        tableView.setId("my-table");
        tableView.setEditable(true);
        //行号
        colRowNumber.setCellFactory(new RowNumberTableCell<>());
        //配件编码
        colSKUCode.setCellValueFactory(param -> {
            if(param.getValue() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSkuCode());
            }
        });
        //名称
        colSKUName.setCellValueFactory(param -> {
            if(param.getValue() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSkuName());
            }
        });
        //条形码
        colSKUName.setCellValueFactory(param -> {
            if(param.getValue() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getSkuName());
            }
        });
        //单位
        colUnit.setCellValueFactory(param -> {
            if(param.getValue() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getProduct().getUnit().getName());
            }
        });
        //库存数量
        colStockQty.setCellValueFactory(param -> {
            if(param.getValue() == null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getStockQty()+"");
            }
        });
        //平均价
        colAvgPrice.setCellValueFactory(param -> {
            if(param.getValue() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
        //金额
        colAmount.setCellValueFactory(param -> {
            if(param.getValue() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
        //车型
        colCar.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>(param.getValue().getProduct().getCar().getName());
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //产地
        colProductivePlace.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>(param.getValue().getProduct().getPlace().getName());
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //品牌
        colBrand.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>(param.getValue().getProduct().getBrand().getName());
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //供应商
        colSupplier.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>(param.getValue().getProduct().getSupplier().getName());
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //进口
        colImport.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>(param.getValue().getProduct().getImported().getName());
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //规格
        colSpec.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>(param.getValue().getSpecification());
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //货位
        colSlot.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>("");
            }
        });


        //子表格列
        subTableView.getStylesheets().add(css);
        subTableView.setId("my-table");
        subTableView.setEditable(true);
        //配件编码
        colCode.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>(param.getValue().getSkuCode());
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //仓库名称
        colWarehouse.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //库位
        colStorageLocation.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>("");
            } else {
                return new SimpleObjectProperty<>("");
            }
        });
        //当前库存
        colCurrentStockQty.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>(param.getValue().getStockQty()+"");
            } else {
                return new SimpleObjectProperty<>("0");
            }
        });
        //平均单价
        colAvgUnitPrice.setCellValueFactory(param -> {
            if(param.getValue().getPurchaseAvgPrice() != null) {
                return new SimpleObjectProperty<>(param.getValue().getPurchaseAvgPrice().toString());
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
        //金额
        colSum.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>("0.00");
            }
        });
        //可用库存
        colAvailableStockQty.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>("0");
            } else {
                return new SimpleObjectProperty<>("0");
            }
        });
        //销售占用库存
        colTakeInventoryQty.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>("0");
            } else {
                return new SimpleObjectProperty<>("0");
            }
        });
        //配送库存
        colDistributionInventoryQty.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>("0");
            } else {
                return new SimpleObjectProperty<>("0");
            }
        });
        //订货库存
        colOrderingQty.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>("0");
            } else {
                return new SimpleObjectProperty<>("0");
            }
        });
        //库存上限
        colUpperLimitQty.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>("0");
            } else {
                return new SimpleObjectProperty<>("0");
            }
        });
        //库存下限
        colLowerLimitQty.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>("0");
            } else {
                return new SimpleObjectProperty<>("0");
            }
        });
        //订货警戒库存
        colInventoryWarningQty.setCellValueFactory(param -> {
            if(param.getValue() != null) {
                return new SimpleObjectProperty<>("0");
            } else {
                return new SimpleObjectProperty<>("0");
            }
        });
    }
}
