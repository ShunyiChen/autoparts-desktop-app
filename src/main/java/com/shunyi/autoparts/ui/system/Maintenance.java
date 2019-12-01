package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.main.BaseContainer;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/** 系统维护Controller */
public class Maintenance extends BorderPane implements BaseContainer {

    private MainApp application;

    public Maintenance(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/maintenance.fxml"
                )

        );
        TabPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MaintenanceController controller = loader.getController();
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

    public void prepare() {

    }

    @Override
    public void willOpen() {

    }

    @Override
    public void willClose() {

    }
}
