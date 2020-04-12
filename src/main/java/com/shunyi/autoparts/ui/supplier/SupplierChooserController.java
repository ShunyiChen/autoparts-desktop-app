package com.shunyi.autoparts.ui.supplier;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

/**
 * @Description: 供应商选择器Controller
 * @Author: Shunyi Chen
 * @CreateDate: 2020/4/12
 */
public class SupplierChooserController {
    private Stage dialog;
    private Supplier selectedSupplier;
    private Callback<Supplier, String> callback;

    @FXML
    private TreeView<SupplierCategory> treeView;
    @FXML
    private Button btnSaveAndQuit;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtOther;
    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtContact;
    @FXML
    private TableView<Supplier> tableView;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colPhone;
    @FXML
    private TableColumn colFax;
    @FXML
    private TableColumn colPostcode;

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void selectAndReturn() {
        callback.call(tableView.getSelectionModel().getSelectedItem());
        dialog.close();
    }

    @FXML
    private void newSupplier() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/supplier/SupplierEditor.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage subStage = new Stage();
        SupplierEditorController controller = loader.getController();
        Callback<Supplier, String> callback = new Callback<Supplier, String>() {
            @Override
            public String call(Supplier param) {
                tableView.getItems().add(param);
                tableView.refresh();
                tableView.getSelectionModel().select(param);
                return null;
            }
        };
        controller.initialize(subStage, callback, null);
        subStage.setTitle("新建供应商");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    @FXML
    private void search() {
        Supplier supplier = new Supplier();
        supplier.setCode(txtCode.getText());
        supplier.setName(txtName.getText());
        supplier.setContact(txtContact.getText());
        supplier.setPhone(txtTel.getText());
        supplier.setNotes(txtOther.getText());
        String json = GoogleJson.GET().toJson(supplier);
        try {
            String data = HttpClient.POST("/suppliers/search", json);
            Supplier[] suppliers = GoogleJson.GET().fromJson(data, Supplier[].class);
            tableView.getItems().clear();
            tableView.getItems().addAll(suppliers);
            tableView.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clear() {
        txtCode.setText("");
        txtName.setText("");
        txtContact.setText("");
        txtTel.setText("");
        txtOther.setText("");
    }

    /**
     *
     * @param dialog
     * @param selectedSupplier
     * @param callback
     */
    public void initialize(Stage dialog, Callback<Supplier, String> callback, Supplier selectedSupplier) {
        this.dialog = dialog;
        this.selectedSupplier = selectedSupplier;
        this.callback = callback;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        treeView.getStylesheets().add(css);
//        treeView.setOnMouseClicked(event -> {
//            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
//            }
//        });
        btnSaveAndQuit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        SupplierCategory sc = null;
        try {
            sc = HttpClient.GET("/supplier/categories/root", SupplierCategory.class);
            TreeItem<SupplierCategory> root = new TreeItem<>(sc);
            treeView.setRoot(root);
            initTreeNodes(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //初始化树
        initTree();
        //初始化表
        initTable();
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

    private void createSupplierCategory() {
        Callback<SupplierCategory, String> callback = new Callback<SupplierCategory, String>() {
            @Override
            public String call(SupplierCategory param) {
                if(param != null) {
                    TreeItem<SupplierCategory> parent = treeView.getSelectionModel().getSelectedItem();
                    parent.getValue().setParent(true);
                    String json = GoogleJson.GET().toJson(parent.getValue());
                    try {
                         HttpClient.PUT("/supplier/categories/"+parent.getValue().getId(),json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    param.setParentId(parent.getValue().getId());
                    param.setParent(Constants.PARENT_FALSE);
                    try {
                        json = GoogleJson.GET().toJson(param, SupplierCategory.class);
                        String idStr = HttpClient.POST("/supplier/categories",json);
                        param.setId(Long.valueOf(idStr));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    TreeItem<SupplierCategory> node = new TreeItem<>(param);
                    parent.getChildren().add(node);
                    // 选中新建的节点
                    treeView.getSelectionModel().select(node);
                }
                return null;
            }
        };

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/supplier/SupplierCategoryEditor.fxml"
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
        SupplierCategoryEditorController controller = loader.getController();
        controller.prepare(dialog, callback);
        dialog.setTitle("新建供应商分类");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    private void removeSupplierCategory() {
        TreeItem<SupplierCategory> selected = treeView.getSelectionModel().getSelectedItem();
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
            try {
                Supplier[] suppliers = HttpClient.GET("/suppliers/category/"+selected.getValue().getId(), Supplier[].class);
                if(suppliers.length > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                    alert.setHeaderText("该分类下存在供应商，无法删除");
                    alert.show();
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

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
                    treeView.getSelectionModel().select(parent);
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

    private void initTree() {
        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建分类");
        MenuItem itemRM = new MenuItem("删 除");
        MenuItem itemRN = new MenuItem("重命名");
        menu.getItems().addAll(itemNew, itemRM, itemRN);
        treeView.setEditable(true);
        treeView.setContextMenu(menu);
        treeView.setCellFactory(new Callback<TreeView<SupplierCategory>, TreeCell<SupplierCategory>>() {
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
                createSupplierCategory();
            }
        });
        itemRM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeSupplierCategory();
            }
        });
        itemRN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                treeView.edit(treeView.getSelectionModel().getSelectedItem());
            }
        });
        treeView.setOnEditCommit(new EventHandler<TreeView.EditEvent<SupplierCategory>>() {
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
        treeView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                TreeItem<SupplierCategory> item = treeView.getSelectionModel().getSelectedItem();
                tableView.getItems().clear();
                String json = null;
                try {
                    json = HttpClient.GET("/suppliers/category/recursion/"+item.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Supplier[] suppliers = GoogleJson.GET().fromJson(json, Supplier[].class);
                tableView.getItems().addAll(suppliers);
                tableView.refresh();
            }
        });
    }

    private void initTable() {
        String css = getClass().getResource("/css/styles.css").toExternalForm();
        tableView.getStylesheets().add(css);
        tableView.setId("my-table");
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.setEditable(false);
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                selectAndReturn();
            }
        });
        try {
            String json = HttpClient.GET("/suppliers");
            Supplier[] suppliers = GoogleJson.GET().fromJson(json, Supplier[].class);
            tableView.getItems().clear();
            tableView.getItems().addAll(suppliers);
            tableView.refresh();
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
        colPhone.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("phone")
        );
        //传真
        colFax.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("fax")
        );
        //邮编
        colPostcode.setCellValueFactory(
                new PropertyValueFactory<Supplier, String>("postCode")
        );
    }
}
