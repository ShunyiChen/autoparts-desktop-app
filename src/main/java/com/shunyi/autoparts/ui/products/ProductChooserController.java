package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 配件选择器Controller
 * @author Shunyi Chen
 * @date 2020/4/15
 */
public class ProductChooserController {
    private Stage dialog;
    private Callback<SKU, String> callback;

    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> comboBoxCar;
    @FXML
    private ComboBox<String> comboBoxCommonCars;
    @FXML
    private ComboBox<String> comboBoxPlace;
    @FXML
    private ComboBox<String> comboBoxSupplier;
    @FXML
    private ComboBox<String> comboBoxBrand;
    @FXML
    private ComboBox<String> comboBoxCompany;
    @FXML
    private Label headerText;
    @FXML
    private Button btnSelectAndReturn;
    @FXML
    private TreeView<Category> treeView;
    @FXML
    private TableView<SKU> tableView;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colSpec;
    @FXML
    private TableColumn<SKU, String> colUnit;
    @FXML
    private TableColumn colBarCode;
    @FXML
    private TableColumn colStockQty;
    @FXML
    private TableColumn<SKU, String> colImported;
    @FXML
    private TableColumn<SKU, String> colPlace;
    @FXML
    private TableColumn<SKU, String> colBrand;
    @FXML
    private TableColumn<SKU, String> colCar;
    @FXML
    private TableColumn<SKU, String> colSupplier;
    @FXML
    private TableColumn<SKU, String> colCompany;
    @FXML
    private TableColumn colDiscountPercentage;
    @FXML
    private TableColumn<SKU, String> colPurchasingPrice1;
    @FXML
    private TableColumn<SKU, String> colPurchasingPrice2;
    @FXML
    private TableColumn<SKU, String> colPurchasingPrice3;
    @FXML
    private TableColumn<SKU, String> colSellingPrice1;
    @FXML
    private TableColumn<SKU, String> colSellingPrice2;
    @FXML
    private TableColumn<SKU, String> colSellingPrice3;
    @FXML
    private TableColumn<SKU, String> colBottomPrice;
    @FXML
    private TableColumn<SKU, String> colShortage;
    @FXML
    private TableColumn colStatus;
    @FXML
    private TableColumn colNotes;
    @FXML
    private TableColumn<SKU, String> colSlot;
    @FXML
    private TableColumn<SKU, String> colPhotos;
    @FXML
    private TableColumn<SKU, String> colStockAvgPrice;
    @FXML
    private TableColumn<SKU, String> colStockAmount;
    @FXML
    private TableColumn<SKU, String> colPurchaseAvgPrice;
    @FXML
    private TableColumn<SKU, String> colPurchaseAmount;

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void selectAndReturn() {
        SKU selectedSku = tableView.getSelectionModel().getSelectedItem();
        if(selectedSku == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个配件");
            alert.show();
            return;
        }
        try {
            SKU sku = HttpClient.GET("/sku/"+selectedSku.getId(), SKU.class);
            callback.call(sku);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.close();
    }

    /**
     *
     * @param dialog
     */
    public void initialize(Stage dialog, Callback<SKU, String> callback) {
        this.dialog = dialog;
        this.callback = callback;
        //初始化查询栏
        initSearchFields();
        //初始化分类树
        initTreeView();
        //初始化SKU表格
        initTable();
        btnSelectAndReturn.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        headerText.setStyle("-fx-text-fill: rgb(255,255,255);");
    }

    /**
     * 初始化查询栏
     */
    private void initSearchFields() {
        try {
            Car[] cars = HttpClient.GET("/cars", Car[].class);
            comboBoxCar.getItems().addAll(Arrays.asList(cars).stream().map(e ->e.getName()).collect(Collectors.toList()));
            comboBoxCommonCars.getItems().addAll(Arrays.asList(cars).stream().map(e ->e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Place[] places = HttpClient.GET("/places", Place[].class);
            comboBoxPlace.getItems().addAll(Arrays.asList(places).stream().map(e ->e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Supplier[] suppliers = HttpClient.GET("/suppliers", Supplier[].class);
            comboBoxSupplier.getItems().addAll(Arrays.asList(suppliers).stream().map(e ->e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Brand[] brands = HttpClient.GET("/brands", Brand[].class);
            comboBoxBrand.getItems().addAll(Arrays.asList(brands).stream().map(e ->e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Company[] companies = HttpClient.GET("/companies", Company[].class);
            comboBoxCompany.getItems().addAll(Arrays.asList(companies).stream().map(e ->e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxCar);
        new AutoCompleteBox(comboBoxCommonCars);
        new AutoCompleteBox(comboBoxPlace);
        new AutoCompleteBox(comboBoxSupplier);
        new AutoCompleteBox(comboBoxBrand);
        new AutoCompleteBox(comboBoxCompany);
    }

    /**
     * 初始化分类树
     */
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
        treeView.setCellFactory(p -> new TextFieldTreeCell<>(new StringConverter<>(){

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
        treeView.setOnEditCommit(event -> {
            if(event.getNewValue().getName().trim().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setHeaderText("分类名称不能为空");
                alert.show();
                return;
            } else {
                String path = "/categories/"+event.getNewValue().getId();
                String json = GoogleJson.GET().toJson(event.getNewValue());
                try {
                    HttpClient.PUT(path, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建类目");
        MenuItem itemRE = new MenuItem("刷 新");
        MenuItem itemRM = new MenuItem("删 除");
        MenuItem itemRN = new MenuItem("重命名");
        MenuItem itemProperties = new MenuItem("分类属性");
        treeView.setEditable(true);
        treeView.setContextMenu(menu);
        itemNew.setOnAction(event -> newCategory());
        itemRE.setOnAction(event -> refreshCategory());
        itemRM.setOnAction(event -> removeCategory());
        itemRN.setOnAction(event -> treeView.edit(treeView.getSelectionModel().getSelectedItem()));
        itemProperties.setOnAction(event -> openCustomAttributes());

        treeView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                tableView.getItems().clear();
                try {
                    TreeItem<Category> selectedItem = treeView.getSelectionModel().getSelectedItem();
                    String data;
                    if(selectedItem.getValue().getParentId().equals(Constants.ROOT_PARENT_ID)) {
                        data = HttpClient.GET("/sku");
                    } else {
                        data = HttpClient.GET("/sku/category/"+selectedItem.getValue().getId());
                    }
                    SKU[] skuArray = GoogleJson.GET().fromJson(data, SKU[].class);
                    tableView.getItems().addAll(skuArray);
                    tableView.refresh();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (event.getButton().equals(MouseButton.SECONDARY) && event.getClickCount() == 1) {
                Category selectedCategory = treeView.getSelectionModel().getSelectedItem().getValue();
                if(selectedCategory.getParentId().equals(Constants.ROOT_PARENT_ID)) {
                    menu.getItems().clear();
                    menu.getItems().addAll(itemNew, new SeparatorMenuItem(), itemRE, itemRN, new SeparatorMenuItem(), itemProperties);
                } else {
                    menu.getItems().clear();
                    menu.getItems().addAll(itemNew, new SeparatorMenuItem(), itemRM, itemRN, new SeparatorMenuItem(), itemProperties);
                }
            }
        });

        //默认选择根节点
        treeView.getSelectionModel().select(root);
    }

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

    private void initTable() {
        tableView.setId("my-table");
        //初始化列
        colCode.setCellValueFactory(new PropertyValueFactory<SKU, String>("skuCode"));
        colName.setCellValueFactory(new PropertyValueFactory<SKU, String>("skuName"));
        colSpec.setCellValueFactory(new PropertyValueFactory<SKU, String>("specification"));
        colUnit.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getUnit()));
        colBarCode.setCellValueFactory(new PropertyValueFactory<SKU, String>("skuBarCode"));
        colStockQty.setCellValueFactory(new PropertyValueFactory<SKU, String>("stockQty"));
        colImported.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getImported()));
        colPlace.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getPlace()));
        colBrand.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getBrand()));
        colCar.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getCar()));
        colSupplier.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getSupplier()));
        colCompany.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getCompany()));
        colDiscountPercentage.setCellValueFactory(new PropertyValueFactory<SKU, String>("discount"));
        colPurchasingPrice1.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getPurchasingPrice1()));
        colPurchasingPrice2.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getPurchasingPrice2()));
        colPurchasingPrice3.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getPurchasingPrice3()));
        colSellingPrice1.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getSellingPrice1()));
        colSellingPrice2.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getSellingPrice2()));
        colSellingPrice3.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getSellingPrice3()));
        colBottomPrice.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getProduct().getBottomPrice()));
        colShortage.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getProduct().getShortage()?"是":"否"));
        colStatus.setCellValueFactory(new PropertyValueFactory<SKU, String>("enabled"));
        colNotes.setCellValueFactory(new PropertyValueFactory<SKU, String>("notes"));
        colStockAvgPrice.setCellValueFactory(param -> {
            if(param.getValue().getStockAvgPrice() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getStockAvgPrice().toString());
            }
        });
        colStockAmount.setCellValueFactory(param -> {
            if(param.getValue().getStockAmount() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getStockAmount().toString());
            }
        });
        colPurchaseAvgPrice.setCellValueFactory(param -> {
            if(param.getValue().getPurchaseAvgPrice() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getPurchaseAvgPrice().toString());
            }
        });
        colPurchaseAmount.setCellValueFactory(param -> {
            if(param.getValue().getPurchaseAmount() == null) {
                return new SimpleObjectProperty<>("0.00");
            } else {
                return new SimpleObjectProperty<>(param.getValue().getPurchaseAmount().toString());
            }
        });

        colSlot.setCellValueFactory(param -> {
            StringBuilder slots = new StringBuilder();
            param.getValue().getSkuSlotMappingSet().stream().sorted(Comparator.comparingLong(e -> e.getSlot().getId())).forEach(e -> {
                slots.append(e.getSlot().getName()+";");
            });
            return new SimpleObjectProperty<>(slots.toString());
        });
        colPhotos.setEditable(false);
        colPhotos.setCellValueFactory(param -> {
            StringBuilder photos = new StringBuilder();
            param.getValue().getPhotos().stream().sorted(Comparator.comparingLong(e -> e.getId())).forEach(e -> {
                photos.append("图片"+e.getId()+"; ");
            });
            return new SimpleObjectProperty<>(photos.toString());
        });
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                updateProductAndSKU();
            }
        });
        ContextMenu menu = new ContextMenu();
        MenuItem itemDuplicate = new MenuItem("复 制");
        MenuItem itemProductSKU = new MenuItem("产品sku");
        MenuItem itemDefineProperties = new MenuItem("产品属性");
        itemDuplicate.setOnAction(e ->{
            duplicate();
        });
        itemProductSKU.setOnAction(e ->{
            openProductSKU();
        });
        itemDefineProperties.setOnAction(e ->{
            openBasicAttributes();
        });
        menu.getItems().addAll(itemDuplicate, new SeparatorMenuItem(), itemProductSKU, new SeparatorMenuItem(), itemDefineProperties);
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, t -> {
            if(t.getButton() == MouseButton.SECONDARY) {
                menu.show(tableView, t.getScreenX(), t.getScreenY());
            } else {
                menu.hide();
            }
        });

        try {
            SKU[] skuArray = HttpClient.GET("/sku", SKU[].class);
            tableView.getItems().addAll(skuArray);
            tableView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void newProductAndSKU() {
        Callback<SKU, String> callback = sku -> {
            //刷新表格
            tableView.getItems().add(sku);
            tableView.refresh();
            tableView.getSelectionModel().select(sku);
            return null;
        };
        openProductEditor(callback, null);
    }

    @FXML
    private void updateProductAndSKU() {
        SKU selectedSku = tableView.getSelectionModel().getSelectedItem();
        if(selectedSku == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个配件");
            alert.show();
            return;
        }
        Callback<SKU, String> callback = sku -> {
            //刷新表格
            int index = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().remove(selectedSku);
            tableView.getItems().add(index, sku);
            tableView.getSelectionModel().select(sku);
            return null;
        };
        openProductEditor(callback, selectedSku);
    }

    private void openProductEditor(Callback<SKU, String> callback, SKU selectedSku) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/ProductEditor.fxml"
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
        ProductEditorController controller = loader.getController();
        controller.initialize(dialog, callback, selectedSku);
        dialog.setTitle(selectedSku != null?"更改配件":"新建配件");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void removeProductAndSKU() {
        SKU selectedSKU = tableView.getSelectionModel().getSelectedItem();
        if(selectedSKU == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个配件删除");
            alert.show();
            return;
        }
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText("请确认是否要删除配件？");
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
            try {
                //删除配件，同时删除其SKU和属性
               String res = HttpClient.DELETE("/products/"+selectedSKU.getProduct().getId());
               if(res.equals("1")) {
                   tableView.getItems().remove(selectedSKU);
               } else {
                   Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                   alert.setHeaderText("当前配件正在使用中，无法删除");
                   alert.show();
               }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    @FXML
    private void search() {
        Product product = new Product();
        product.setCar(new Car(comboBoxCar.getValue()));
        product.setRelevantModels(comboBoxCommonCars.getValue());
        product.setPlace(new Place(comboBoxPlace.getValue()));
        product.setSupplier(new Supplier(comboBoxSupplier.getValue()));
        product.setBrand(new Brand(comboBoxBrand.getValue()));
        product.setCompany(new Company(comboBoxCompany.getValue()));

        SKU condition = new SKU();
        condition.setSkuCode(txtCode.getText());
        condition.setSkuName(txtName.getText());
        condition.setProduct(product);
        String json = GoogleJson.GET().toJson(condition);
        String data = null;
        try {
            data = HttpClient.POST("/sku/search", json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SKU[] skuArray = GoogleJson.GET().fromJson(data, SKU[].class);
        tableView.getItems().clear();
        tableView.getItems().addAll(skuArray);
        tableView.refresh();
    }

    @FXML
    private void clear() {
        txtCode.setText("");
        txtName.setText("");
        comboBoxCar.setValue("");
        comboBoxCommonCars.setValue("");
        comboBoxPlace.setValue("");
        comboBoxSupplier.setValue("");
        comboBoxBrand.setValue("");
        comboBoxCompany.setValue("");
    }

    private void newCategory() {
        TreeItem<Category> parent = treeView.getSelectionModel().getSelectedItem();
        if(parent == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个节点");
            alert.show();
            return;
        }
        Callback<Category, Object> callback = new Callback<>() {
            @Override
            public Object call(Category category) {
                if(category != null) {
                    parent.getValue().setParent(true);
                    String json = GoogleJson.GET().toJson(parent.getValue());
                    try {
                        HttpClient.PUT("/categories/"+parent.getValue().getId(),json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    category.setParentId(parent.getValue().getId());
                    category.setParent(false);
                    try {
                        json = GoogleJson.GET().toJson(category, Category.class);
                        String idStr = HttpClient.POST("/categories",json);
                        category.setId(Long.valueOf(idStr));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    TreeItem<Category> node = new TreeItem<>(category);
                    parent.getChildren().add(node);
                    // 选中新建的节点
                    treeView.getSelectionModel().select(node);
                }
                return null;
            }
        };
        openCategoryEditor(callback);
    }

    @FXML
    private void updateCategory() {
//        TreeItem<Category> selected = treeView.getSelectionModel().getSelectedItem();
//        if(selected == null) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
//            alert.setHeaderText("请选择一个节点");
//            alert.show();
//            return;
//        }
//        if(selected.getValue().getId() == 0) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
//            alert.setHeaderText("根节点不可更新");
//            alert.show();
//            return;
//        } else {
//            Callback<Category, Object> callback = new Callback<>() {
//                @Override
//                public Object call(Category param) {
//                    param.setId(selected.getValue().getId());
//                    param.setParent(selected.getValue().getParent());
//                    param.setParentId(selected.getValue().getParentId());
//                    String json = GoogleJson.GET().toJson(param);
//                    try {
//                        HttpClient.PUT("/categories/"+selected.getValue().getId(), json);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    //刷新节点
//                    selected.getValue().setName(param.getName());
//
//                    selected.getParent().setExpanded(false);
//                    selected.getParent().setExpanded(true);
//                    treeView.getSelectionModel().select(selected);
//                    return null;
//                }
//            };
//            openCategoryEditor(callback);
//        }
    }

    /**
     *
     * @param callback
     */
    private void openCategoryEditor(Callback<Category, Object> callback) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/CategoryEditor.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        CategoryEditorController controller = loader.getController();
        controller.prepare(dialog, callback);
        dialog.setTitle("新建配件分类");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void refreshCategory() {
        initTreeView();
    }

    @FXML
    private void removeCategory() {
        TreeItem<Category> selected = treeView.getSelectionModel().getSelectedItem();
        if(selected == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个类目节点");
            alert.show();
            return;
        }
        else if(selected.getValue().getParentId() == 0L) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("根节点不可删除");
            alert.show();
            return;
        }
        else if(selected.getValue().getParent()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("无法删除父节点");
            alert.show();
            return;
        }
        else {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
            alertConfirm.setHeaderText("是否删除该类目？");
            alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
                try {
                    HttpClient.DELETE("/categories/"+selected.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TreeItem<Category> parent = selected.getParent();
                if(parent != null) {
                    parent.getChildren().remove(selected);
                    parent.setExpanded(true);
                    treeView.getSelectionModel().select(parent);
                }
                //设置节点是否为父节点
                if(parent.getParent() != null) {
                    parent.getValue().setParent(false);
                    String json = GoogleJson.GET().toJson(parent.getValue());
                    try {
                        HttpClient.PUT("/categories/"+parent.getValue().getId(), json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void openCustomAttributes() {
        TreeItem<Category> selectedItem = treeView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/attributes_management.fxml"
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
        AttributeManagementController controller = loader.getController();
        controller.prepare(dialog, selectedItem == null?null:selectedItem.getValue());
        dialog.setTitle("配件属性");
        dialog.initOwner(this.dialog);
        dialog.setResizable(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openBasicAttributes() {
        TreeItem<Category> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个配件类目");
            alert.show();
            return;
        }
        SKU selectedRow = tableView.getSelectionModel().getSelectedItem();
        if(selectedRow == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个配件");
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/basic_attributes.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        BasicAttributesController controller = loader.getController();
//        controller.prepare(dialog, selectedItem.getValue(), selectedRow);
        dialog.setTitle("产品属性");
        dialog.initOwner(this.dialog);
        dialog.setResizable(true);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openProductSKU() {
        SKU selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个配件");
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/ProductSKU.fxml"
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
        ProductSKUController controller = loader.getController();
//        controller.prepare(dialog, selectedProduct);
        dialog.setTitle("产品sku");
        dialog.initOwner(this.dialog);
        dialog.setResizable(true);
//        dialog.setMaximized(true);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }


    private List<AttributeValue> getProductBasicAttributeValues(Product product) {
        String data;
        BasicAttributes[] basicAttributes = {};
        try {
            data = HttpClient.GET("/basic/attributes/products/"+product.getId());
            basicAttributes = GoogleJson.GET().fromJson(data, BasicAttributes[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<AttributeValue> attributeValues = new ArrayList<>();
        for(BasicAttributes ba : basicAttributes) {
            attributeValues.add(ba.getAttributeValue());
        }
        return attributeValues;
    }

    @FXML
    private void duplicate() {
//        Product selected = tableView.getSelectionModel().getSelectedItem();
//        if(selected == null) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
//            alert.setHeaderText("请选择一个配件");
//            alert.show();
//            return;
//        }
//        String json = null;
//        try {
//            json = HttpClient.GET("/products/"+selected.getId());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Product duplicated = GoogleJson.GET().fromJson(json, Product.class);
//        duplicated.setId(null);
//        json = GoogleJson.GET().toJson(duplicated);
//        try {
//            String idStr = HttpClient.POST("/products",json);
//            duplicated.setId(Long.valueOf(idStr));
//
//            List<AttributeValue> attributeValues = getProductBasicAttributeValues(selected);
//            for(AttributeValue v : attributeValues) {
//                BasicAttributes newBasicAttributes = new BasicAttributes(0L, duplicated, v.getAttributeName().getId(), v, false, 0L, null, null, null, null, null, null, false, null);
//                json = GoogleJson.GET().toJson(newBasicAttributes);
//                idStr = HttpClient.POST("/basic/attributes", json);
//                newBasicAttributes.setId(Long.valueOf(idStr));
//            }
//
//            tableView.getItems().add(duplicated);
//            tableView.getSelectionModel().select(duplicated);
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
    }

}
