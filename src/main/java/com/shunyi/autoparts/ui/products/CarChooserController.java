package com.shunyi.autoparts.ui.products;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.model.Car;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/** 车辆类目选择器Controller */
public class CarChooserController {

    @FXML
    private Button btnChooser;
    @FXML
    private TreeView<Car> treeView;
    private Stage dialog;
    private Car selectedCar;
    private Callback callback;
    private Gson gson = new Gson();

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void choose() {
        TreeItem<Car> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("选择的节点不能为空");
            alert.show();
            return;
        }
        dialog.close();
        callback.call(selectedItem.getValue());
    }

    /**
     *
     * @param root
     */
    private void initTreeNodes(TreeItem<Car> root) {
        try {
            String path = "/cars";
            String data = HttpClient.GET(path);
            Car[] res = gson.fromJson(data, Car[].class);
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
    private void getNodes(TreeItem<Car> parent, Car[] all) {
        for(Car sc : all) {
            if(sc.getParentId() == parent.getValue().getId()) {
                TreeItem<Car> node = new TreeItem<>(sc);
                parent.getChildren().add(node);
                parent.setExpanded(true);
                getNodes(node, all);
                //默认选中
                if(selectedCar != null && selectedCar.getId() == sc.getId()) {
                    // This line is the not-so-clearly documented magic.
                    int row = treeView.getRow(node);
                    // Now the row can be selected.
                    treeView.getSelectionModel().select(row);
                }
            }
        }
    }
    
    void newCar() {
        TreeItem<Car> parent = treeView.getSelectionModel().getSelectedItem();
        if(parent == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择一个父类目");
            alert.show();
            return;
        }
        Callback<Car, Object> callback = e -> {
            e.setParent(false);
            e.setParentId(parent.getValue().getId());
            String json = GoogleJson.GET().toJson(e);
            try {
                String idStr = HttpClient.POST("/cars", json);
                e.setId(Long.valueOf(idStr));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            TreeItem<Car> newItem = new TreeItem<>(e);
            parent.getChildren().add(newItem);
            parent.setExpanded(true);
            return null;
        };
        openCarEditor(callback, null);
    }

    void updateCar() {
        TreeItem<Car> selected = treeView.getSelectionModel().getSelectedItem();
        if(selected == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择要更新的类目");
            alert.show();
            return;
        } else if(selected.getValue().getId() == 0L) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("无法更改根节点");
            alert.show();
            return;
        }
        Callback<Car, Object> callback = e -> {
            selected.getValue().setCode(e.getCode());
            selected.getValue().setModel(e.getModel());
            String json = GoogleJson.GET().toJson(selected.getValue());
            try {
                HttpClient.PUT("/cars/"+selected.getValue().getId(), json);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //刷新树
            selected.getParent().setExpanded(false);
            selected.getParent().setExpanded(true);
            treeView.getSelectionModel().select(selected);
            return null;
        };
        openCarEditor(callback, selected.getValue());
    }

    void openCarEditor(Callback<Car, Object> callback, Car updatedCar) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/car_editor.fxml"
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
        CarEditorController controller = loader.getController();
        controller.prepare(subStage, updatedCar, callback);
        subStage.setTitle(updatedCar != null?"更改车辆类目":"新建车辆类目");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    void removeCar() {
        TreeItem<Car> selected = treeView.getSelectionModel().getSelectedItem();
        if(selected == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择要删除的类目");
            alert.show();
            return;
        } else if(selected.getValue().getId() == 0L) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("无法删除根节点");
            alert.show();
            return;
        }
        try {
            HttpClient.DELETE("/cars/"+selected.getValue().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //刷新树
        selected.getParent().getChildren().remove(selected);
    }

    public void prepare(Stage dialog, Car selectedCar, Callback callback) {
        this.dialog = dialog;
        this.selectedCar = selectedCar;
        this.callback = callback;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        treeView.getStylesheets().add(css);
        initTreeView();
        initTreeContextMenu();
    }
    
    void initTreeView() {
        treeView.setEditable(false);
        treeView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                choose();
            }
        });
//        treeView.setCellFactory(new Callback<TreeView<Car>, TreeCell<Car>>() {
//            @Override
//            public TreeCell<Car> call(TreeView<Car> p) {
//                return new TextFieldTreeCell<Car>(new StringConverter<Car>(){
//
//                    @Override
//                    public String toString(Car object) {
//                        return object.getModel();
//                    }
//
//                    @Override
//                    public Car fromString(String string) {
//                        p.getEditingItem().getValue().setModel(string);
//                        return p.getEditingItem().getValue();
//                    }
//                });
//            }
//        });
//        treeView.setOnEditCommit(new EventHandler<TreeView.EditEvent<Car>>() {
//            @Override
//            public void handle(TreeView.EditEvent<Car> event) {
//                String path = "/cars/"+event.getNewValue().getId();
//                String json = gson.toJson(event.getNewValue());
//                try {
//                    HttpClient.PUT(path, json);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        
        btnChooser.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        TreeItem<Car> root = new TreeItem<Car>(new Car("","全部车型类目",0L, true));
        treeView.setRoot(root);
        initTreeNodes(root);
        //默认选中root
        if(selectedCar != null && selectedCar.getId() == 0) {
            treeView.getSelectionModel().select(root);
        }
    }

    void initTreeContextMenu() {
        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建车型类目");
        MenuItem itemUpdate = new MenuItem("更改");
        MenuItem itemRM = new MenuItem("删除");
        MenuItem itemRN = new MenuItem("重命名");
        menu.getItems().addAll(itemNew, itemUpdate, itemRM, new SeparatorMenuItem(), itemRN);
        treeView.setEditable(true);
        treeView.setContextMenu(menu);
        itemNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newCar();
            }
        });
        itemUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateCar();
            }
        });
        itemRM.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeCar();
            }
        });
        itemRN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                treeView.edit(treeView.getSelectionModel().getSelectedItem());
            }
        });
    }
}
