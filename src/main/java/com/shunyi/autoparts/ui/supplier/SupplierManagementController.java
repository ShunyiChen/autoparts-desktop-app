package com.shunyi.autoparts.ui.supplier;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.SupplierCategory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

/** 供应商管理控制器 */
public class SupplierManagementController {
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
    CheckBox cbTopName;
    @FXML
    Spinner<Integer> spiTopValue;
    @FXML
    CheckBox cbDynamic;
    @FXML
    SplitPane splitPane;
    @FXML
    TreeView<SupplierCategory> supplierCategoryTree;
    @FXML
    TableView supplierTable;
    @FXML
    GridPane gridPane;
    @FXML
    TextField txtCode;
    @FXML
    TextField txtContact;
    @FXML
    TextField txtPhone;
    @FXML
    TextField txtName;
    @FXML
    TextField txtOthers;
    private Gson gson = new Gson();
    private MainApp application;

    @FXML
    public void newCategory(ActionEvent event) {
        TreeItem<SupplierCategory> selected = supplierCategoryTree.getSelectionModel().getSelectedItem();
        Callback callback = new Callback() {
            @Override
            public Object call(Object param) {
                if(param != null) {
                    selected.getValue().setParent(true);
                    String json = gson.toJson(selected.getValue());
                    System.out.println("json="+json);
                    try {
                         HttpClient.PUT("/supplier/categories/"+selected.getValue().getId(),json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    SupplierCategory sc = new SupplierCategory(param.toString(), selected.getValue().getId(),false);
                    try {
                        json = gson.toJson(sc, SupplierCategory.class);
                        HttpClient.POST("/supplier/categories",json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    TreeItem<SupplierCategory> node = new TreeItem<>(sc);
                    selected.getChildren().add(node);
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
            alert.setHeaderText("根节点不可删除。");
            alert.show();
            return;
        } else {
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
            });
        }
    }

    @FXML
    public void newSupplier(ActionEvent event) {
    }

    @FXML
    public void removeSupplier(ActionEvent event) {
    }

    @FXML
    public void search(ActionEvent event) {
    }

    @FXML
    public void clear(ActionEvent event) {
        txtCode.setText("");
        txtContact.setText("");
        txtPhone.setText("");
        txtName.setText("");
        txtOthers.setText("");
    }

    private void editCategory(Callback callback) {
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
            SupplierCategory[] res = gson.fromJson(data, SupplierCategory[].class);
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
            if(sc.getParentId() == parent.getValue().getId()) {
                TreeItem<SupplierCategory> node = new TreeItem<SupplierCategory>(sc);
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
        final int initialValue = 50;
        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, initialValue);
        spiTopValue.setValueFactory(valueFactory);
        spiTopValue.setEditable(true);

        TreeItem<SupplierCategory> root = new TreeItem<SupplierCategory>(new SupplierCategory("全部供应商",0, true));
        initTreeNodes(root);
        supplierCategoryTree.setRoot(root);
        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建类目");
        MenuItem itemRM = new MenuItem("删除类目");
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
                supplierCategoryTree.edit(supplierCategoryTree.getSelectionModel().getSelectedItem());
            }
        });
        supplierCategoryTree.setOnEditCommit(new EventHandler<TreeView.EditEvent<SupplierCategory>>() {
            @Override
            public void handle(TreeView.EditEvent<SupplierCategory> event) {
                String path = "/supplier/categories/"+event.getNewValue().getId();
                String json = gson.toJson(event.getNewValue());
                try {
                    HttpClient.PUT(path, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}