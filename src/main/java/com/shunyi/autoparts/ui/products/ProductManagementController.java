package com.shunyi.autoparts.ui.products;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.GSON;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.BrandSeries;
import com.shunyi.autoparts.ui.model.Category;
import com.shunyi.autoparts.ui.model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

/** 配件管理Controller */
public class ProductManagementController {
    MainApp application;
    @FXML
    TextField txtCode;
    @FXML
    TextField txtName;
    @FXML
    TextField txtImported;
    @FXML
    TextField txtCar;
    @FXML
    TextField txtBrand;
    @FXML
    TextField txtOther;

    @FXML
    TableColumn colCode;
    @FXML
    TableColumn colName;
    @FXML
    TableColumn colBrand;
    @FXML
    TableColumn colPrice;
    @FXML
    TableColumn colUnit;
    @FXML
    TableColumn colImported;
    @FXML
    TableColumn colCar;
    @FXML
    TableColumn colPlace;
    @FXML
    TableColumn colDateCreated;
    @FXML
    TableColumn colOther;
    @FXML
    TreeView<Category> treeView;
    @FXML
    ListView<BrandSeries> listView;
    @FXML
    TableView<Product> tableView;

    @FXML
    void search(ActionEvent event) {
    }

    @FXML
    void clear(ActionEvent event) {
        txtCode.setText("");
        txtName.setText("");
        txtImported.setText("");
        txtCar.setText("");
        txtBrand.setText("");
        txtOther.setText("");
    }

    @FXML
    void newCategory(ActionEvent event) {
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
                    String json = GSON.getInstance().toJson(parent.getValue());
                    try {
                        HttpClient.PUT("/categories/"+parent.getValue().getId(),json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    category.setParentId(parent.getValue().getId());
                    category.setParent(false);
                    try {
                        json = GSON.getInstance().toJson(category, Category.class);
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
        openCategoryEditor(callback, null);
    }

    @FXML
    void updateCategory(ActionEvent event) {
        TreeItem<Category> selected = treeView.getSelectionModel().getSelectedItem();
        if(selected.getValue().getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("根节点不可更新");
            alert.show();
            return;
        } else {
            Callback<Category, Object> callback = new Callback<>() {
                @Override
                public Object call(Category param) {
                    param.setId(selected.getValue().getId());
                    param.setParent(selected.getValue().isParent());
                    param.setParentId(selected.getValue().getParentId());
                    String json = GSON.getInstance().toJson(param);
                    try {
                        HttpClient.PUT("/categories/"+selected.getValue().getId(), json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //刷新节点
                    selected.getValue().setName(param.getName());

                    selected.getParent().setExpanded(false);
                    selected.getParent().setExpanded(true);
                    treeView.getSelectionModel().select(selected);
                    return null;
                }
            };
            openCategoryEditor(callback, selected.getValue());
        }
    }

    void openCategoryEditor(Callback<Category, Object> callback, Category updatedCategory) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/category_editor.fxml"
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
        controller.prepare(dialog, updatedCategory, callback);
        dialog.setTitle(updatedCategory != null?"更改类目":"新建类目");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    void removeCategory(ActionEvent event) {
        TreeItem<Category> selected = treeView.getSelectionModel().getSelectedItem();
        if(selected.getValue().getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("根节点不可删除");
            alert.show();
            return;
        }
        else if(selected.getValue().isParent()){
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
                if(parent.isLeaf()) {
                    parent.getValue().setParent(false);
                    String json = GSON.getInstance().toJson(parent.getValue());
                    try {
                        HttpClient.PUT("/categories/"+parent.getValue().getId(), json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    void newBrand(ActionEvent event) {
        TreeItem<Category> selectedCategory = treeView.getSelectionModel().getSelectedItem();
        if(selectedCategory.getValue().getId() == 0L) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("不能在根节点下创建品牌，请选中其他类目");
            alert.show();
            return;
        }
        Callback<BrandSeries, Object> callback = e -> {
            String json = GSON.getInstance().toJson(e);
            try {
                String idStr = HttpClient.POST("/brandSeries", json);
                e.setId(Long.valueOf(idStr));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            listView.getItems().add(e);
            return null;
        };
        openBrandSeriesEditor(callback, null, selectedCategory==null?null:selectedCategory.getValue());
    }

    @FXML
    void updateBrand(ActionEvent event) {
        BrandSeries selected = listView.getSelectionModel().getSelectedItem();
        if(selected == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个品牌");
            alert.show();
            return;
        }
        Callback<BrandSeries, Object> callback = e -> {
            e.setId(selected.getId());
            String json = GSON.getInstance().toJson(e);
            try {
                HttpClient.PUT("/brandSeries/"+selected.getId(), json);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            int index = listView.getItems().indexOf(selected);
            listView.getItems().remove(index);
            listView.getItems().add(index, e);
            listView.getSelectionModel().select(e);
            return null;
        };
        //因为不需要重复赋值，所有最后参数设置null
        openBrandSeriesEditor(callback, selected, null);
    }

    @FXML
    void removeBrand(ActionEvent event) {
        BrandSeries selected = listView.getSelectionModel().getSelectedItem();
        if(selected == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个要删除的品牌");
            alert.show();
            return;
        }
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText("是否删除该品牌？");
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
            try {
                HttpClient.DELETE("/brandSeries/"+selected.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            listView.getItems().remove(selected);
        });
    }

    void openBrandSeriesEditor(Callback<BrandSeries, Object> callback, BrandSeries updatedBrandSeries, Category selectedCategory) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/brandseries_editor.fxml"
                )
        );
        HBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        BrandSeriesEditorController controller = loader.getController();
        controller.prepare(dialog, updatedBrandSeries, callback, selectedCategory);
        dialog.setTitle(updatedBrandSeries != null?"更改品牌":"新建品牌");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    void newProduct(ActionEvent event) {
        TreeItem<Category> selectedCategory = treeView.getSelectionModel().getSelectedItem();
        if(selectedCategory == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择产品分类");
            alert.show();
            return;
        }
        BrandSeries selectedBrand = listView.getSelectionModel().getSelectedItem();
        if(selectedBrand == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择品牌");
            alert.show();
            return;
        }
        Callback<Product, Object> callback = e -> {
            String json = GSON.getInstance().toJson(e);
            try {
                String idStr = HttpClient.POST("/products",json);
                e.setId(Long.valueOf(idStr));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            tableView.getItems().add(e);
            return null;
        };
        openProductEditor(callback, null, selectedCategory.getValue(), selectedBrand);
    }

    @FXML
    void updateProduct(ActionEvent event) {
        TreeItem<Category> selectedCategory = treeView.getSelectionModel().getSelectedItem();
        if(selectedCategory == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择产品分类");
            alert.show();
            return;
        }
        BrandSeries selectedBrand = listView.getSelectionModel().getSelectedItem();
        if(selectedBrand == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择品牌");
            alert.show();
            return;
        }
        Product selected = tableView.getSelectionModel().getSelectedItem();
        if(selected == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个配件");
            alert.show();
            return;
        }
        Callback<Product, Object> callback = e -> {
            e.setId(selected.getId());
            String json = GSON.getInstance().toJson(e);
            try {
                HttpClient.PUT("/products/"+selected.getId(),json);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            int index = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().remove(selected);
            tableView.getItems().add(index, e);
            return null;
        };
        openProductEditor(callback, selected, selectedCategory.getValue(), selectedBrand);
    }

    void openProductEditor(Callback<Product, Object> callback, Product updatedProduct, Category selectedCategory, BrandSeries selectedBrand) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/product_editor.fxml"
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
        ProductEditorController controller = loader.getController();
        controller.prepare(dialog, updatedProduct, callback, selectedCategory, selectedBrand);
        dialog.setTitle(updatedProduct != null?"更改配件":"新建配件");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    void removeProduct(ActionEvent event) {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个配件删除");
            alert.show();
            return;
        }
        try {
            HttpClient.DELETE("/products/"+selectedProduct.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableView.getItems().remove(selectedProduct);
    }

    @FXML
    void openProductAttributes(ActionEvent event) {

    }

    public void prepare(MainApp application) {
        this.application = application;
        initTableView();
        initTreeView();
        initTreeContextMenu();
        initListView();
    }

    void initTableView() {
        colCode.setCellValueFactory(new PropertyValueFactory<Product, String>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<Product, String>("brandSeries"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("priceExcludingTax"));
        colUnit.setCellValueFactory(new PropertyValueFactory<Product, String>("unit"));
        colImported.setCellValueFactory(new PropertyValueFactory<Product, String>("imported"));
        colCar.setCellValueFactory(new PropertyValueFactory<Product, String>("car"));
        colPlace.setCellValueFactory(new PropertyValueFactory<Product, String>("placeOfOrigin"));
        colDateCreated.setCellValueFactory(new PropertyValueFactory<Product, String>("dateCreated"));
        colOther.setCellValueFactory(new PropertyValueFactory<Product, String>("notes"));

        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                 updateProduct(null);
            }
        });
    }

    void initTreeView() {
        TreeItem<Category> root = new TreeItem<>(new Category("全部类目",0L, true));
        initTreeNodes(root);
        treeView.setRoot(root);
        treeView.setCellFactory(new Callback<TreeView<Category>, TreeCell<Category>>() {
            @Override
            public TreeCell<Category> call(TreeView<Category> p) {
                return new TextFieldTreeCell<Category>(new StringConverter<Category>(){

                    @Override
                    public String toString(Category object) {
                        return object.getName();
                    }

                    @Override
                    public Category fromString(String string) {
                        p.getEditingItem().getValue().setName(string);
                        return p.getEditingItem().getValue();
                    }
                });
            }
        });
        treeView.setOnEditCommit(new EventHandler<TreeView.EditEvent<Category>>() {
            @Override
            public void handle(TreeView.EditEvent<Category> event) {
                String path = "/categories/"+event.getNewValue().getId();
                String json = GSON.getInstance().toJson(event.getNewValue());
                try {
                    HttpClient.PUT(path, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        treeView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                TreeItem<Category> item = treeView.getSelectionModel().getSelectedItem();
                listView.getItems().clear();
                String json = null;
                try {
                    json = HttpClient.GET("/brandSeries/category/"+item.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BrandSeries[] brands = GSON.getInstance().fromJson(json, BrandSeries[].class);
                listView.getItems().addAll(brands);
            }
        });

    }

    void initTreeNodes(TreeItem<Category> root) {
        try {
            String path = "/categories/sorted";
            String data = HttpClient.GET(path);
            Category[] res = GSON.getInstance().fromJson(data, Category[].class);
            getNodes(root, res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getNodes(TreeItem<Category> parent, Category[] all) {
        for(Category sc : all) {
            if(sc.getParentId() == parent.getValue().getId()) {
                TreeItem<Category> node = new TreeItem<>(sc);
                parent.getChildren().add(node);
                parent.setExpanded(true);
                getNodes(node, all);
            }
        }
    }

    void initTreeContextMenu() {
        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建类目");
        MenuItem itemRM = new MenuItem("删除类目");
        MenuItem itemRN = new MenuItem("重命名");
        menu.getItems().addAll(itemNew, itemRM, itemRN);
        treeView.setEditable(true);
        treeView.setContextMenu(menu);
        itemNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newCategory(event);
            }
        });
        itemRM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeCategory(event);
            }
        });
        itemRN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                treeView.edit(treeView.getSelectionModel().getSelectedItem());
            }
        });
    }

    void initListView() {
        listView.setStyle("-fx-font-size: 14px;");
        try {
            String path = "/brandSeries";
            String data = HttpClient.GET(path);
            BrandSeries[] res = GSON.getInstance().fromJson(data, BrandSeries[].class);
            listView.getItems().addAll(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2) {
                    updateBrand(null);
                } else if(event.getClickCount() == 1) {
                    BrandSeries selectedBrand = listView.getSelectionModel().getSelectedItem();
                    String data = null;
                    try {
                        data = HttpClient.GET("/products/brandSeries/"+selectedBrand.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Product[] products = GSON.getInstance().fromJson(data, Product[].class);
                    tableView.getItems().clear();
                    tableView.getItems().addAll(products);
                }
            }
        });

    }
}
