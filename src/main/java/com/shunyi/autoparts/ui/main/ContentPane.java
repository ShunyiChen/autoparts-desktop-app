package com.shunyi.autoparts.ui.main;

import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;

/**
 * @Description: 主界面内容面板
 * @Author: 陈顺谊
 * @CreateDate: 2020/1/8 16:24
 * @Version: 1.0
 */
public class NewTabPane extends BorderPane {

    private BorderPane viewport = new BorderPane();
    private TabPane tabPane;
    public NewTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
        initComponents();
    }

    private void initComponents() {
        this.setPadding(new Insets(5));
        viewport.setId("contentpane_bg");
        this.setCenter(tabPane);
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void addNewTab(NewTab tab) {
        tabPane.getTabs().add(tab);
    }
}
