package com.shunyi.autoparts.ui.maintenance;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.main.TabContent;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * @Description: 库存配件
 * @Author: Shunyi
 * @CreateDate: 2020/5/18
 */
public class Maintenance extends TabContent {
    private MainApp application;
    private MaintenanceController controller;

    public Maintenance(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/inventory/IA.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        controller = loader.getController();
//        controller.prepare(application.getStage());

        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getMainFrame().getNavigation().widthProperty().add(35));
        root.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

    @Override
    protected void reload() {
//        controller.prepare(application.getStage());
    }

    @Override
    protected void dispose() {
    }

    @Override
    protected String getTitle() {
        return Constants.DATA_MAINTENANCE;
    }
}
