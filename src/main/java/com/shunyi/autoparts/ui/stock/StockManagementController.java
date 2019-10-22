package com.shunyi.autoparts.ui.stock;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.Warehouse;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.IOException;

public class StockManagementController {

    private Gson gson = new Gson();
    private MainApp application;

    @FXML
    TreeView<Warehouse> treeView;

    public void prepare(MainApp application) {
        this.application = application;
        //初始化树
        TreeItem<Warehouse> root = new TreeItem<>(new Warehouse("ALL","全部仓库",0L, true));
        initTreeNodes(root);
        treeView.setRoot(root);
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
        for(Warehouse sc : all) {
            if(sc.getParentId() == parent.getValue().getId()) {
                TreeItem<Warehouse> node = new TreeItem<>(sc);
                parent.getChildren().add(node);
                parent.setExpanded(true);
                getNodes(node, all);
            }
        }
    }
}
