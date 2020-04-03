package com.shunyi.autoparts.ui.supplier;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.main.TabContent;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

/**
 * @Description: 供应商明细
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/23 23:05
 * @Version: 1.0
 */
public class SupplierDetails extends TabContent {

    private MainApp application;

    public SupplierDetails(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/supplier/SupplierDetails.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SupplierDetailsController controller = loader.getController();
        controller.prepare(application);

        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getMainFrame().getNavigation().widthProperty().add(35));
        Objects.requireNonNull(root).prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

    @Override
    protected void reload() {

    }

    @Override
    protected void willClose() {

    }
}
