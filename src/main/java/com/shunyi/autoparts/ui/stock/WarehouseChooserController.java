package com.shunyi.autoparts.ui.stock;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Store;
import com.shunyi.autoparts.ui.common.vo.Warehouse;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class WarehouseChooserController {

    @FXML
    private Button btnChooser;
    @FXML
    private TreeView<Warehouse> treeView;
    private Stage subStage;
    private Warehouse selectedWarehouse;
    private Callback<Warehouse, Object> callback;
    private Gson gson = new Gson();

    @FXML
    private void cancel() {
        subStage.close();
    }

    @FXML
    private void choose() {
        TreeItem<Warehouse> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("选择的节点不能为空");
            alert.show();
            return;
        }
        else if(selectedItem.getValue().getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("货位不能在根节点下创建");
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
//        for(Warehouse sc : all) {
//            if(sc.getParentId() == parent.getValue().getId()) {
//                TreeItem<Warehouse> node = new TreeItem<>(sc);
//                parent.getChildren().add(node);
//                parent.setExpanded(true);
//                getNodes(node, all);
//                //默认选中
//                if(selectedWarehouse != null && selectedWarehouse.getId() == sc.getId()) {
//                    // This line is the not-so-clearly documented magic.
//                    int row = treeView.getRow(node);
//                    // Now the row can be selected.
//                    treeView.getSelectionModel().select(row);
//                }
//            }
//        }
    }

    public void prepare(Stage subStage, Warehouse selectedWarehouse, Callback<Warehouse, Object> callback) {
        this.subStage = subStage;
        this.selectedWarehouse = selectedWarehouse;
        this.callback = callback;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        treeView.getStylesheets().add(css);
        treeView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                choose();
            }
        });
        btnChooser.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));

        Warehouse warehouse = new Warehouse(Constants.ID, "", "所有仓库", "");
        TreeItem<Warehouse> root = new TreeItem<>(warehouse);
        treeView.setRoot(root);
        initTreeNodes(root);
        //默认选中root
        if(selectedWarehouse != null && selectedWarehouse.getId() == 0) {
            treeView.getSelectionModel().select(root);
        }
    }
}
