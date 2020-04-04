package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.main.TabContent;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * @Description: 采购单
 * @Author: Shunyi Chen
 * @CreateDate: 2020/3/23 23:00
 * @Version: 1.0
 */
public class PO extends TabContent {
    private MainApp application;
    private POController purchaseDetailsController;

    public PO(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/PO.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        purchaseDetailsController = loader.getController();
        purchaseDetailsController.initialize(application);

        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getMainFrame().getNavigation().widthProperty().add(35));
        root.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

    @Override
    protected void reload() {
        purchaseDetailsController.clean();
        purchaseDetailsController.initialize(application);
    }

    @Override
    protected void willClose() {

    }
}
