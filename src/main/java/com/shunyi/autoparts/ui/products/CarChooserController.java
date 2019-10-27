package com.shunyi.autoparts.ui.products;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.Car;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

/** 车辆类目选择器Controller */
public class CarChooserController {

    @FXML
    private Button btnChooser;
    @FXML
    private TreeView<Car> treeView;
    private Stage subStage;
    private Car selectedCar;
    private Callback callback;
    private Gson gson = new Gson();

    @FXML
    private void cancel() {
        subStage.close();
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
        subStage.close();
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
    }

    void rename() {
    }

    void removeCar() {
    }

    public void prepare(Stage subStage, Car selectedCar, Callback callback) {
        this.subStage = subStage;
        this.selectedCar = selectedCar;
        this.callback = callback;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        treeView.getStylesheets().add(css);
        initTreeView();
        initTreeContextMenu();
    }
    
    void initTreeView() {
        treeView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                choose();
            }
        });
        treeView.setCellFactory(new Callback<TreeView<Car>, TreeCell<Car>>() {
            @Override
            public TreeCell<Car> call(TreeView<Car> p) {
                return new TextFieldTreeCell<Car>(new StringConverter<Car>(){

                    @Override
                    public String toString(Car object) {
                        return object.getModel();
                    }

                    @Override
                    public Car fromString(String string) {
                        p.getEditingItem().getValue().setModel(string);
                        return p.getEditingItem().getValue();
                    }
                });
            }
        });
        treeView.setOnEditCommit(new EventHandler<TreeView.EditEvent<Car>>() {
            @Override
            public void handle(TreeView.EditEvent<Car> event) {
                String path = "/cars/"+event.getNewValue().getId();
                String json = gson.toJson(event.getNewValue());
                try {
                    HttpClient.PUT(path, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        
        btnChooser.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        TreeItem<Car> root = new TreeItem<Car>(new Car("","全部车辆类目",0L, true));
        treeView.setRoot(root);
        initTreeNodes(root);
        //默认选中root
        if(selectedCar != null && selectedCar.getId() == 0) {
            treeView.getSelectionModel().select(root);
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
                newCar();
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
