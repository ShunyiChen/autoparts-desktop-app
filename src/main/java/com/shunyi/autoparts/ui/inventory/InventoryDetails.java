package com.shunyi.autoparts.ui.inventory;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.main.TabContent;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

import java.io.IOException;

/**
 * @Description: 库存明细
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/23 23:07
 * @Version: 1.0
 */
public class InventoryDetails extends TabContent {

    private MainApp application;
    private InventoryDetailsController controller;

    public InventoryDetails(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/inventory/InventoryDetails.fxml"
                )
        );
        TabPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = loader.getController();
        controller.prepare(application.getStage());

        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getDashboard().getNavigation().widthProperty().add(35));
        root.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

    @Override
    protected void reload() {
        controller.prepare(application.getStage());
    }

    @Override
    protected void willClose() {

    }
}
