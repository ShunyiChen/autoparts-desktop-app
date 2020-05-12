package com.shunyi.autoparts.ui.sales;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.main.TabContent;
import com.shunyi.autoparts.ui.purchase.PROController;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * @Description: 采购退货单
 * @Author: Shunyi Chen
 * @CreateDate: 2020/5/9
 */
public class SRO extends TabContent {

    private MainApp application;
    private PROController proController;

    public SRO(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/PRO.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        proController = loader.getController();
        proController.initialize(application);

        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getMainFrame().getNavigation().widthProperty().add(35));
        root.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

    @Override
    protected void reload() {
        proController.clean();
        proController.initialize(application);
    }

    @Override
    protected void willClose() {

    }
}
