package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.vo.Store;
import com.shunyi.autoparts.ui.main.TabContent;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * @Description: 配件明细
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/23 23:08
 * @Version: 1.0
 */
public class ProductDetails extends TabContent {
    private MainApp application;
    private ProductDetailsController productDetailsController;

    /**
     * Constructor
     *
     * @param application
     */
    public ProductDetails(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/ProductDetails.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        productDetailsController = loader.getController();
        productDetailsController.prepare(application);

        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getMainFrame().getNavigation().widthProperty().add(35));
        root.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

    @Override
    protected void reload() {
        productDetailsController.prepare(application);
    }

    @Override
    protected void willClose() {
    }
}
