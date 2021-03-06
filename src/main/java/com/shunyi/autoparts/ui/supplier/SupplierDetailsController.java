package com.shunyi.autoparts.ui.supplier;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Grade;
import com.shunyi.autoparts.ui.common.vo.Payment;
import com.shunyi.autoparts.ui.common.vo.Supplier;
import com.shunyi.autoparts.ui.common.vo.SupplierCategory;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

/** 供应商管理控制器 */
public class SupplierDetailsController {

    private MainApp application;

    @FXML
    BorderPane mainPane;
    @FXML
    Button btnSearch;
    @FXML
    Button btnClear;
    @FXML
    Button btnNewCategory;
    @FXML
    Button btnRemoveCategory;
    @FXML
    Button btnNewSupplier;
    @FXML
    Button btnRemoveSupplier;
    @FXML
    SplitPane splitPane;
    @FXML
    TreeView<SupplierCategory> supplierCategoryTree;
    @FXML
    TableView<Supplier> supplierTable;
    @FXML
    GridPane gridPane;
    @FXML
    TextField txtCode;
    @FXML
    TextField txtName;
    @FXML
    ComboBox<Grade> comboGrade;
    @FXML
    TextField txtContact;
    @FXML
    TextField txtPhone;
    @FXML
    ComboBox<Payment> comboPayment;

    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colPhone1;
    @FXML
    private TableColumn colPhone2;
    @FXML
    private TableColumn colEmail;
    @FXML
    private TableColumn colAddress;
    @FXML
    private TableColumn colPostcode;
    @FXML
    private TableColumn colGrade;
    @FXML
    private TableColumn colPayment;
    @FXML
    private TableColumn colNotes;
    @FXML
    private TableColumn colCategory;

    @FXML
    public void newCategory() {
        TreeItem<SupplierCategory> parent = supplierCategoryTree.getSelectionModel().getSelectedItem();
        Callback callback = new Callback() {
            @Override
            public Object call(Object param) {
                if(param != null) {
                    parent.getValue().setParent(true);
                    String json = GoogleJson.GET().toJson(parent.getValue());
                    try {
                         HttpClient.PUT("/supplier/categories/"+parent.getValue().getId(),json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    SupplierCategory sc = new SupplierCategory(0L, param.toString(), parent.getValue().getId(), Constants.PARENT_FALSE, null, Env.getInstance().currentUser(), null, null, null, null, Constants.DELETE_FLAG_FALSE, null);
                    try {
                        json = GoogleJson.GET().toJson(sc, SupplierCategory.class);
                        String idStr = HttpClient.POST("/supplier/categories",json);
                        sc.setId(Long.valueOf(idStr));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    TreeItem<SupplierCategory> node = new TreeItem<>(sc);
                    parent.getChildren().add(node);
                    // 选中新建的节点
                    supplierCategoryTree.getSelectionModel().select(node);
                }
                return null;
            }
        };
        editCategory(callback);
    }

    @FXML
    public void removeCategory(ActionEvent event) {
        TreeItem<SupplierCategory> selected = supplierCategoryTree.getSelectionModel().getSelectedItem();
        if(selected.getValue().getId() == 0) {
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
                    HttpClient.DELETE("/supplier/categories/"+selected.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TreeItem<SupplierCategory> parent = selected.getParent();
                if(parent != null) {
                    parent.getChildren().remove(selected);
                    parent.setExpanded(true);
                    supplierCategoryTree.getSelectionModel().select(parent);
                }
                //设置节点是否为父节点
                if(parent.isLeaf()) {
                    parent.getValue().setParent(false);
                    String json = GoogleJson.GET().toJson(parent.getValue());
                    try {
                        HttpClient.PUT("/supplier/categories/"+parent.getValue().getId(), json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    public void newSupplier(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/supplier/edit_supplier.fxml"
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
        EditSupplierController controller = loader.getController();
        Callback<Supplier, Object> cb = new Callback<Supplier, Object>() {
            @Override
            public Object call(Supplier param) {
                String json = GoogleJson.GET().toJson(param);
                try {
                    HttpClient.POST("/suppliers", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                supplierTable.getItems().add(param);
                return null;
            }
        };
        controller.prepare(dialog, null, cb);
        dialog.setTitle("新建供应商");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    public void updateSupplier() {
        Supplier supplier = supplierTable.getSelectionModel().getSelectedItem();
        if(supplier == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请在表格上选择一行记录");
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/supplier/edit_supplier.fxml"
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
        dialog.setOnHiding( e -> {
        });
        EditSupplierController controller = loader.getController();
        Callback<Supplier, Object> cb = new Callback<Supplier, Object>() {
            @Override
            public Object call(Supplier param) {
                String json = GoogleJson.GET().toJson(param);
                try {
                    HttpClient.PUT("/suppliers/"+param.getId(),json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Supplier supplier1 = supplierTable.getSelectionModel().getSelectedItem();
                int index = supplierTable.getItems().indexOf(supplier1);
                supplierTable.getItems().remove(index);
                supplierTable.getItems().add(param);
                supplierTable.getSelectionModel().select(param);
                return null;
            }
        };
        controller.prepare(dialog, supplier, cb);
        dialog.setTitle("更改供应商");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    public void removeSupplier(ActionEvent event) {
        Supplier supplier = supplierTable.getSelectionModel().getSelectedItem();
        if(supplier == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请在表格上选择一行记录");
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.NO, ButtonType.YES);
        alert.setHeaderText("是否删除该供应商？");
        alert.showAndWait()
                .filter(response -> response == ButtonType.YES)
                .ifPresent(response -> {
                    try {
                        HttpClient.DELETE("/suppliers/"+supplier.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    supplierTable.getItems().remove(supplier);
                });
    }

    @FXML
    private void refresh() {

    }

    @FXML
    public void search(ActionEvent event) {
        Supplier supplier = new Supplier();
        supplier.setCode(txtCode.getText());
        supplier.setName(txtName.getText());
        supplier.setContact(txtContact.getText());
        supplier.setPhone1(txtPhone.getText());
        supplier.setPhone2(txtPhone.getText());
        supplier.setPayment(comboPayment.getValue());
        supplier.setGrade(comboGrade.getValue());

        String json = GoogleJson.GET().toJson(supplier);
        try {
            String data = HttpClient.POST("/suppliers/search", json);
            Supplier[] suppliers = GoogleJson.GET().fromJson(data, Supplier[].class);
            supplierTable.getItems().clear();
            supplierTable.getItems().addAll(suppliers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clear() {
        txtCode.setText("");
        txtName.setText("");
        txtContact.setText("");
        txtPhone.setText("");
        comboGrade.setValue(null);
        comboPayment.setValue(null);
    }

    /**
     *
     * @param callback
     */
    private void editCategory(Callback<Supplier, String> callback) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/supplier/edit_category.fxml"
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
        dialog.setOnHiding( e -> {
        });
        EditCategoryController controller = loader.getController();
        controller.prepare(dialog, callback);
        dialog.setTitle("新建供应商类目");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    /**
     *
     * @param root
     */
    private void initTreeNodes(TreeItem<SupplierCategory> root) {
        try {
            String path = "/supplier/categories";
            String data = HttpClient.GET(path);
            SupplierCategory[] res = GoogleJson.GET().fromJson(data, SupplierCategory[].class);
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
    private void getNodes(TreeItem<SupplierCategory> parent, SupplierCategory[] all) {
        for(SupplierCategory sc : all) {
            if(sc.getParentId().equals(parent.getValue().getId())) {
                TreeItem<SupplierCategory> node = new TreeItem<>(sc);
                parent.getChildren().add(node);
                parent.setExpanded(true);
                getNodes(node, all);
            }
        }
    }

    /**
     *
     * @param application
     */
    public void prepare(MainApp application) {
        this.application = application;
        //初始化分类树
        initCategoryTree();
        //初始化供应商表格
        initSupplierTable();

        initGradeList();

        initPaymentList();
    }

    private void initGradeList() {
        comboGrade.setStyle("-fx-font-size: 14px;");
        comboGrade.getItems().add(null);
        try {
            String json = HttpClient.GET("/grades");
            Grade[] grades = GoogleJson.GET().fromJson(json, Grade[].class);
            comboGrade.getItems().addAll(grades);
            comboGrade.getSelectionModel().select(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPaymentList() {
        comboPayment.setStyle("-fx-font-size: 14px;");
        comboPayment.getItems().add(null);
        try {
            String json = HttpClient.GET("/payments");
            Payment[] payments = GoogleJson.GET().fromJson(json, Payment[].class);
            comboPayment.getItems().addAll(payments);
            comboPayment.getSelectionModel().select(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCategoryTree() {
        SupplierCategory sc = new SupplierCategory(0L, "所有供应商",-1L, Constants.PARENT_TRUE, null, null, null, null, null, null, Constants.DELETE_FLAG_FALSE, null);
        TreeItem<SupplierCategory> root = new TreeItem<SupplierCategory>(sc);
        initTreeNodes(root);
        supplierCategoryTree.setRoot(root);
        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建类目");
        MenuItem itemRM = new MenuItem("删 除");
        MenuItem itemRN = new MenuItem("重命名");
        menu.getItems().addAll(itemNew, itemRM, itemRN);
        supplierCategoryTree.setEditable(true);
        supplierCategoryTree.setContextMenu(menu);
        supplierCategoryTree.setCellFactory(new Callback<TreeView<SupplierCategory>, TreeCell<SupplierCategory>>() {
            @Override
            public TreeCell<SupplierCategory> call(TreeView<SupplierCategory> p) {
                return new TextFieldTreeCell<SupplierCategory>(new StringConverter<SupplierCategory>(){

                    @Override
                    public String toString(SupplierCategory object) {
                        return object.getName();
                    }

                    @Override
                    public SupplierCategory fromString(String string) {
                        p.getEditingItem().getValue().setName(string);
                        return p.getEditingItem().getValue();
                    }
                });
            }
        });
        itemNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newCategory();
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
                supplierCategoryTree.edit(supplierCategoryTree.getSelectionModel().getSelectedItem());
            }
        });
        supplierCategoryTree.setOnEditCommit(new EventHandler<TreeView.EditEvent<SupplierCategory>>() {
            @Override
            public void handle(TreeView.EditEvent<SupplierCategory> event) {
                if(!event.getNewValue().getId().equals(0L)) {
                    String path = "/supplier/categories/"+event.getNewValue().getId();
                    String json = GoogleJson.GET().toJson(event.getNewValue());
                    try {
                        HttpClient.PUT(path, json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        supplierCategoryTree.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                TreeItem<SupplierCategory> item = supplierCategoryTree.getSelectionModel().getSelectedItem();
                supplierTable.getItems().clear();
                String json = null;
                try {
                    json = HttpClient.GET("/suppliers/category/"+item.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Supplier[] suppliers = GoogleJson.GET().fromJson(json, Supplier[].class);
                supplierTable.getItems().addAll(suppliers);
            }
        });
    }

    private void initSupplierTable() {
        // 初始化table
        //Set the table to multi selection mode
        supplierTable.setId("my-table");
        supplierTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        supplierTable.setEditable(false);
        supplierTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                updateSupplier();
            }
        });
        try {
            String json = HttpClient.GET("/suppliers");
            Supplier[] suppliers = GoogleJson.GET().fromJson(json, Supplier[].class);
            supplierTable.getItems().addAll(suppliers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 编码列设置
        colCode.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("code")
        );
        //名称列设置
        colName.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("name")
        );
        //联系人列设置
        colContact.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("contact")
        );
        //电话列设置
        colPhone1.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("phone1")
        );
        colPhone2.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("phone2")
        );
        colEmail.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("email")
        );
        colAddress.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("address")
        );
        colPostcode.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("postCode")
        );
        colGrade.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("grade")
        );
        colPayment.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("payment")
        );
        colNotes.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("notes")
        );
        //类目列设置
        colCategory.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("category")
        );
    }
}