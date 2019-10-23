package com.shunyi.autoparts.ui.stock;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.*;
import com.shunyi.autoparts.ui.supplier.EditSupplierController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

public class StockManagementController {

    private Gson gson = new Gson();
    private MainApp application;

    @FXML
    TreeView<Warehouse> treeView;
    @FXML
    Button btnNewWarehouse;
    @FXML
    Button btnUpdateWarehouse;
    @FXML
    Button btnRmWarehouse;
    @FXML
    Button btnNewLocation;
    @FXML
    Button btnUpdateLocation;
    @FXML
    Button btnRmLocation;
    @FXML
    TextField txtName;
    @FXML
    TextField txtLevel_1;
    @FXML
    TextField txtLevel_2;
    @FXML
    TextField txtLevel_3;
    @FXML
    TextField txtLevel_4;
    @FXML
    TextField txtLevel_5;
    @FXML
    TableView<CargoSpace> topTable;
    @FXML
    TableView<SKU> bottomTable;

    @FXML
    private void search(ActionEvent event) {
        //定义货位
        CargoSpace cargoSpace = new CargoSpace();
        cargoSpace.setName(txtName.getText());
        cargoSpace.setLevel_1(txtLevel_1.getText());
        cargoSpace.setLevel_2(txtLevel_2.getText());
        cargoSpace.setLevel_3(txtLevel_3.getText());
        cargoSpace.setLevel_4(txtLevel_4.getText());
        cargoSpace.setLevel_5(txtLevel_5.getText());
        String json = gson.toJson(cargoSpace);
        try {
            String data = HttpClient.POST("/cargoSpaces/search", json);
            CargoSpace[] cargoSpaces = gson.fromJson(data, CargoSpace[].class);
            topTable.getItems().clear();
            topTable.getItems().addAll(cargoSpaces);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        txtName.setText("");
        txtLevel_1.setText("");
        txtLevel_2.setText("");
        txtLevel_3.setText("");
        txtLevel_4.setText("");
        txtLevel_5.setText("");
    }

    /**
     * 打开编辑仓库窗口
     *
     * @param callback
     */
    private void editWarehouse(Callback<Warehouse, Object> callback) {
        editWarehouse(callback, null);
    }

    /**
     * 打开编辑仓库窗口
     *
     * @param callback
     * @param updatedWarehouse
     */
    private void editWarehouse(Callback<Warehouse, Object> callback, Warehouse updatedWarehouse) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/stock/edit_warehouse.fxml"
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
        EditWarehouseController controller = loader.getController();
        controller.prepare(dialog, updatedWarehouse, callback);
        dialog.setTitle("新建仓库");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void newWarehouse(ActionEvent event) {
        TreeItem<Warehouse> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个节点");
            alert.show();
            return;
        }
        Callback<Warehouse, Object> cb = new Callback<>() {
            @Override
            public Object call(Warehouse param) {
                if(param != null) {
                    selectedItem.getValue().setParent(true);
                    String json = gson.toJson(selectedItem.getValue());
                    try {
                        HttpClient.PUT("/warehouses/"+selectedItem.getValue().getId(),json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    param.setParentId(selectedItem.getValue().getId()==null?0:selectedItem.getValue().getId());
                    param.setParent(false);
                    json = gson.toJson(param);
                    try {
                        String idStr = HttpClient.POST("/warehouses", json);
                        param.setId(Long.valueOf(idStr));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    TreeItem<Warehouse> node = new TreeItem<>(param);
                    selectedItem.getChildren().add(node);
                    // 选中新建的节点
                    treeView.getSelectionModel().select(node);
                }
                return null;
            }
        };
        editWarehouse(cb);
    }

    @FXML
    private void updateWarehouse(ActionEvent event) {
        TreeItem<Warehouse> selected = treeView.getSelectionModel().getSelectedItem();
        if(selected.getValue().getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("根节点不可更新");
            alert.show();
            return;
        } else {
            Callback<Warehouse, Object> cb = new Callback<>() {
                @Override
                public Object call(Warehouse param) {
                    param.setId(selected.getValue().getId());
                    param.setParent(selected.getValue().isParent());
                    param.setParentId(selected.getValue().getParentId());
                    String json = gson.toJson(param);
                    try {
                        HttpClient.PUT("/warehouses/"+selected.getValue().getId(), json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //刷新节点
                    selected.getValue().setCode(param.getCode());
                    selected.getValue().setName(param.getName());

                    selected.getParent().setExpanded(false);
                    selected.getParent().setExpanded(true);
                    treeView.getSelectionModel().select(selected);
                    return null;
                }
            };
            editWarehouse(cb, selected.getValue());
        }
    }

    @FXML
    private void removeWarehouse(ActionEvent event) {
        TreeItem<Warehouse> selected = treeView.getSelectionModel().getSelectedItem();
        if(selected.getValue().getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("根节点不可删除。");
            alert.show();
            return;
        }
        else if(selected.getValue().isParent()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("无法删除父节点");
            alert.show();
            return;
        } else {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
            alertConfirm.setHeaderText("是否删除该仓库？");
            alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
                try {
                    HttpClient.DELETE("/warehouses/"+selected.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TreeItem<Warehouse> parent = selected.getParent();
                if(parent != null) {
                    parent.getChildren().remove(selected);
                    parent.setExpanded(true);
                    treeView.getSelectionModel().select(parent);
                }

                //设置节点是否为父节点
                if(parent.isLeaf()) {
                    parent.getValue().setParent(false);
                    String json = gson.toJson(parent.getValue());
                    try {
                        HttpClient.PUT("/warehouses/"+parent.getValue().getId(), json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML
    private void newCargoSpace(ActionEvent event) {

    }

    @FXML
    private void updateCargoSpace(ActionEvent event) {

    }

    @FXML
    private void removeCargoSpace(ActionEvent event) {

    }

    public void prepare(MainApp application) {
        this.application = application;
        //初始化树
        Warehouse warehouse = new Warehouse("ALL","全部仓库",0L, true);
        warehouse.setId(0L);
        TreeItem<Warehouse> root = new TreeItem<>(warehouse);
        initTreeNodes(root);
        treeView.setRoot(root);
        treeView.setEditable(true);
        treeView.setCellFactory(new Callback<TreeView<Warehouse>, TreeCell<Warehouse>>() {
            @Override
            public TreeCell<Warehouse> call(TreeView<Warehouse> p) {
                return new TextFieldTreeCell<Warehouse>(new StringConverter<Warehouse>(){

                    @Override
                    public String toString(Warehouse object) {
                        return object.getName();
                    }

                    @Override
                    public Warehouse fromString(String string) {
                        p.getEditingItem().getValue().setName(string);
                        return p.getEditingItem().getValue();
                    }
                });
            }
        });
        treeView.setOnEditCommit(new EventHandler<TreeView.EditEvent<Warehouse>>() {
            @Override
            public void handle(TreeView.EditEvent<Warehouse> event) {
                String path = "/warehouses/"+event.getNewValue().getId();
                String json = gson.toJson(event.getNewValue());
                try {
                    HttpClient.PUT(path, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建仓库");
        MenuItem itemUpdate = new MenuItem("更改仓库");
        MenuItem itemRM = new MenuItem("删除仓库");
        MenuItem itemRN = new MenuItem("重命名");
        menu.getItems().addAll(itemNew, itemUpdate, itemRM, new SeparatorMenuItem(), itemRN);

        itemNew.setOnAction(e -> {
            newWarehouse(e);
        });
        itemUpdate.setOnAction(e -> {
            updateWarehouse(e);
        });
        itemRM.setOnAction(e -> {
            removeWarehouse(e);
        });
        itemRN.setOnAction(e -> {
            treeView.edit(treeView.getSelectionModel().getSelectedItem());
        });

        treeView.setContextMenu(menu);

    }

    /**
     *
     * @param root
     */
    private void initTreeNodes(TreeItem<Warehouse> root) {
        try {
            String path = "/warehouses";
            String data = HttpClient.GET(path);
            Warehouse[] res = gson.fromJson(data, Warehouse[].class);
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
    private void getNodes(TreeItem<Warehouse> parent, Warehouse[] all) {
        for(Warehouse wh : all) {
            if(wh.getParentId() == parent.getValue().getId()) {
                TreeItem<Warehouse> node = new TreeItem<>(wh);
                parent.getChildren().add(node);
                parent.setExpanded(true);
                getNodes(node, all);
            }
        }
    }
}
