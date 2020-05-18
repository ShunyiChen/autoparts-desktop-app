package com.shunyi.autoparts.ui.sales;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.main.TabContent;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * @Description: 销售单
 * @Author: Shunyi
 * @CreateDate: 2020/5/12
 */
public class SO extends TabContent {
    private MainApp application;
    private SOController soController;

    public SO(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/sales/SO.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        soController = loader.getController();
        soController.initialize(application);

        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getMainFrame().getNavigation().widthProperty().add(35));
        root.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

    @Override
    protected void reload() {
        soController.clean();
        soController.initialize(application);
    }

    @Override
    protected void dispose() {

    }

    @Override
    protected String getTitle() {
        return Constants.SALES_ORDER;
    }
}
