package com.shunyi.autoparts.ui.adjustment;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.main.TabContent;
import com.shunyi.autoparts.ui.stocktaking.STOController;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * @Description: 调整单
 * @Author: Shunyi
 * @CreateDate: 2020/5/15
 */
public class PAO extends TabContent {
    private MainApp application;
    private PAOController paoController;

    public PAO(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/adjustment/PAO.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        paoController = loader.getController();
        paoController.initialize(application);

        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getMainFrame().getNavigation().widthProperty().add(35));
        root.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

    @Override
    protected void reload() {
        paoController.clean();
        paoController.initialize(application);
    }

    @Override
    protected void willClose() {

    }
}
