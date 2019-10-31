package com.shunyi.autoparts.ui.stock;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.CargoSpace;
import com.shunyi.autoparts.ui.model.SKU;
import com.shunyi.autoparts.ui.model.Warehouse;
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

public class StockManagementController {

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
    TableColumn colWarehouse;
    @FXML
    TableColumn colCargoSpaceName;
    @FXML
    TableColumn colLevel_1;
    @FXML
    TableColumn colLevel_2;
    @FXML
    TableColumn colLevel_3;
    @FXML
    TableColumn colLevel_4;
    @FXML
    TableColumn colLevel_5;
    @FXML
    TableColumn colCargoSpaceBarCode;

    @FXML
    TableView<SKU> bottomTable;
    @FXML
    TableColumn colProductName;
    @FXML
    TableColumn colBrand;
    @FXML
    TableColumn colPriceExcludingTax;
    @FXML
    TableColumn colDateCreated;
    @FXML
    TableColumn colSkuCode;
    @FXML
    TableColumn colSkuName;
    @FXML
    TableColumn colUnit;
    @FXML
    TableColumn colStockQty;
    @FXML
    TableColumn colPrice;
    @FXML
    TableColumn colStatus;
    @FXML
    TableColumn colProperty;
    @FXML
    TableColumn colProductBarCode;

    @FXML
    private void search(ActionEvent event) {
        //定义货位
        CargoSpace cargoSpace = new CargoSpace();
        cargoSpace.setBarCode("");
        cargoSpace.setName(txtName.getText());
        cargoSpace.setLevel_1(txtLevel_1.getText());
        cargoSpace.setLevel_2(txtLevel_2.getText());
        cargoSpace.setLevel_3(txtLevel_3.getText());
        cargoSpace.setLevel_4(txtLevel_4.getText());
        cargoSpace.setLevel_5(txtLevel_5.getText());
        String json = GoogleJson.GET().toJson(cargoSpace);
        try {
            String data = HttpClient.POST("/cargoSpaces/search", json);
            CargoSpace[] cargoSpaces = GoogleJson.GET().fromJson(data, CargoSpace[].class);
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
                    String json = GoogleJson.GET().toJson(selectedItem.getValue());
                    try {
                        HttpClient.PUT("/warehouses/"+selectedItem.getValue().getId(),json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    param.setParentId(selectedItem.getValue().getId()==null?0:selectedItem.getValue().getId());
                    param.setParent(false);
                    json = GoogleJson.GET().toJson(param);
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
                    String json = GoogleJson.GET().toJson(param);
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
        if(selected.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("选择一个节点删除");
            alert.show();
            return;
        }
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
                    String json = GoogleJson.GET().toJson(parent.getValue());
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
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/stock/edit_cargospace.fxml"
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
        EditCargoSpaceController controller = loader.getController();
        Callback<CargoSpace, Object> cb = new Callback<CargoSpace, Object>() {
            @Override
            public Object call(CargoSpace param) {
                String json = GoogleJson.GET().toJson(param);
                try {
                    HttpClient.POST("/cargoSpaces", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                topTable.getItems().add(param);
                return null;
            }
        };
        controller.prepare(dialog, null, cb);
        dialog.setTitle("新建库位");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void updateCargoSpace() {
        CargoSpace cargoSpace = topTable.getSelectionModel().getSelectedItem();
        if(cargoSpace == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请在表格上选择一行记录");
            alert.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/stock/edit_cargospace.fxml"
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
        EditCargoSpaceController controller = loader.getController();
        Callback<CargoSpace, Object> cb = new Callback<CargoSpace, Object>() {
            @Override
            public Object call(CargoSpace param) {
                String json = GoogleJson.GET().toJson(param);
                try {
                    HttpClient.PUT("/cargoSpaces/"+param.getId(),json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CargoSpace cargoSpace1 = topTable.getSelectionModel().getSelectedItem();
                int index = topTable.getItems().indexOf(cargoSpace1);
                topTable.getItems().remove(index);
                topTable.getItems().add(param);
                topTable.getSelectionModel().select(param);
                return null;
            }
        };
        controller.prepare(dialog, cargoSpace, cb);
        dialog.setTitle("更改库位");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void removeCargoSpace(ActionEvent event) {
        CargoSpace cargoSpace = topTable.getSelectionModel().getSelectedItem();
        if(cargoSpace == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请在表格上选择一行记录");
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.NO, ButtonType.YES);
        alert.setHeaderText("是否删除该库位？");
        alert.showAndWait()
                .filter(response -> response == ButtonType.YES)
                .ifPresent(response -> {
                    try {
                        HttpClient.DELETE("/cargoSpaces/"+cargoSpace.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    topTable.getItems().remove(cargoSpace);
                });
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
                String json = GoogleJson.GET().toJson(event.getNewValue());
                try {
                    HttpClient.PUT(path, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        treeView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                TreeItem<Warehouse> item = treeView.getSelectionModel().getSelectedItem();
                topTable.getItems().clear();
                bottomTable.getItems().clear();
                String json = null;
                try {
                    json = HttpClient.GET("/cargoSpaces/warehouse/"+item.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CargoSpace[] suppliers = GoogleJson.GET().fromJson(json, CargoSpace[].class);
                topTable.getItems().addAll(suppliers);
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

        // 初始化上表格
        initTopTable();
        // 初始化下表格
        initBottomTable();
    }

    private void initTopTable() {
        topTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        topTable.setEditable(false);
        topTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                updateCargoSpace();
            }
        });
        try {
            String json = HttpClient.GET("/cargoSpaces");
            CargoSpace[] cargoSpaces = GoogleJson.GET().fromJson(json, CargoSpace[].class);
            topTable.getItems().addAll(cargoSpaces);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 编码列设置
        colWarehouse.setCellValueFactory(
                new PropertyValueFactory<CargoSpace, String>("warehouse")
        );
        colCargoSpaceName.setCellValueFactory(
                new PropertyValueFactory<CargoSpace, String>("name")
        );
        colLevel_1.setCellValueFactory(
                new PropertyValueFactory<CargoSpace, String>("level_1")
        );
        colLevel_2.setCellValueFactory(
                new PropertyValueFactory<CargoSpace, String>("level_2")
        );
        colLevel_3.setCellValueFactory(
                new PropertyValueFactory<CargoSpace, String>("level_3")
        );
        colLevel_4.setCellValueFactory(
                new PropertyValueFactory<CargoSpace, String>("level_4")
        );
        colLevel_5.setCellValueFactory(
                new PropertyValueFactory<CargoSpace, String>("level_5")
        );
        colCargoSpaceBarCode.setCellValueFactory(
                new PropertyValueFactory<CargoSpace, String>("barCode")
        );
    }

    private void initBottomTable() {

    }

    /**
     *
     * @param root
     */
    private void initTreeNodes(TreeItem<Warehouse> root) {
        try {
            String path = "/warehouses";
            String data = HttpClient.GET(path);
            Warehouse[] res = GoogleJson.GET().fromJson(data, Warehouse[].class);
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
