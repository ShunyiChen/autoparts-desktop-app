package com.shunyi.autoparts.ui.main;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 主界面内容面板
 * @Author: 陈顺谊
 * @CreateDate: 2020/1/8 16:24
 * @Version: 1.0
 */
public class ContentPane extends BorderPane {

    private BorderPane viewport = new BorderPane();
    private TabPane tabPane;
//    private List<String> tabTextList = new ArrayList<>();

    public ContentPane(TabPane tabPane) {
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

    public void addNewTab(NewTab newTab) {
        Tab existedTab = null;
        ObservableList<Tab> list = tabPane.getTabs();
        for(Tab tab : list) {
            if(tab.getText().equals(newTab.getText())) {
                existedTab = tab;
            }
        }
        if(existedTab == null) {
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        } else {
            tabPane.getSelectionModel().select(existedTab);
        }
    }
}
