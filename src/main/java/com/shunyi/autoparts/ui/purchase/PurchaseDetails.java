package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.inventory.InventoryDetailsController;
import com.shunyi.autoparts.ui.main.TabContent;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * @Description:
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/23 23:00
 * @Version: 1.0
 */
public class PurchaseDetails extends TabContent {

    private MainApp application;
    private PurchaseDetailsController controller;


    public PurchaseDetails(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/PurchaseDetails.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = loader.getController();
        controller.prepare(application);

        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getDashboard().getNavigation().widthProperty().add(35));
        root.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

    @Override
    protected void reload() {
        controller.prepare(application);
    }

    @Override
    protected void willClose() {

    }
}
