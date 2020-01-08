package com.shunyi.autoparts.ui.main;

import javafx.geometry.Insets;
import javafx.scene.layout.*;

/**
 * @Description: 主界面内容面板
 * @Author: 陈顺谊
 * @CreateDate: 2020/1/8 16:24
 * @Version: 1.0
 */
public class ContentPane extends BorderPane {

    private BorderPane viewport = new BorderPane();

    public ContentPane() {
        initComponents();
    }

    private void initComponents() {
        this.setPadding(new Insets(5));
        viewport.setId("contentpane_bg");
        this.setCenter(viewport);
    }

    public BorderPane getViewport() {
        return viewport;
    }
}
