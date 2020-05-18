package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.main.TabContent;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

import java.io.IOException;

/**
 * @Description: 系统设置
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/23 23:09
 * @Version: 1.0
 */
public class SystemSettings extends TabContent {
    private MainApp application;
    private SystemSettingsController controller;

    /**
     * Constructor
     *
     * @param application
     */
    public SystemSettings(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/SystemSettings.fxml"
                )
        );
        TabPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller = loader.getController();
        controller.prepare(application);

        root.prefWidthProperty().bind(application.getScene().widthProperty());
        root.prefWidthProperty().bind(application.getStage().widthProperty().subtract(application.getMainFrame().getNavigation().widthProperty().add(35)));
        root.prefHeightProperty().bind(application.getStage().heightProperty().subtract(350));

        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getMainFrame().getNavigation().widthProperty().add(35));
        root.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        root.prefHeightProperty().bind(hBinding);
        this.setLeft(root);
    }

    @Override
    protected void reload() {
        controller.prepare(application);
    }

    @Override
    protected void dispose() {

    }

    @Override
    protected String getTitle() {
        return Constants.SYSTEM_SETTINGS;
    }
}
