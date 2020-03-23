package com.shunyi.autoparts.ui.stock;

import com.shunyi.autoparts.ui.MainApp;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/** 仓库管理 */
public class StockManagement extends BorderPane {
    private MainApp application;

    public StockManagement(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/stock/stock_management.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StockManagementController controller = loader.getController();
        controller.prepare(application);

        root.prefWidthProperty().bind(application.getScene().widthProperty());
        root.prefWidthProperty().bind(application.getStage().widthProperty().subtract(application.getDashboard().getNavigation().widthProperty().add(35)));
        root.prefHeightProperty().bind(application.getStage().heightProperty().subtract(350));


        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getDashboard().getNavigation().widthProperty().add(35));
        root.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

}
