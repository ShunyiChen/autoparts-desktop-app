package com.shunyi.autoparts.ui.supplier;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.main.BaseContainer;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SupplierManagement extends BorderPane implements BaseContainer {

    private MainApp application;

    public SupplierManagement(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/supplier/supplier_management.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SupplierManagementController controller = loader.getController();
        controller.prepare(application);

//        root.prefWidthProperty().bind(application.getScene().widthProperty());
//        root.prefWidthProperty().bind(application.getStage().widthProperty().subtract(application.getDashboard().getNavigation().widthProperty().add(35)));
//        root.prefHeightProperty().bind(application.getStage().heightProperty().subtract(350));


        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getDashboard().getNavigation().widthProperty().add(35));
        root.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

    @Override
    public void willOpen() {

    }

    @Override
    public void willClose() {

    }
}
