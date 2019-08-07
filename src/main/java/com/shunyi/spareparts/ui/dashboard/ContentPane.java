package com.shunyi.spareparts.ui.dashboard;

import javafx.geometry.Insets;
import javafx.scene.layout.*;

public class ContentPane extends BorderPane {

    private BorderPane viewport = new BorderPane();

    public ContentPane() {
        initComponents();
    }

    private void initComponents() {
        this.setPadding(new Insets(10));
        viewport.setId("contentpane_bg");
        this.setCenter(viewport);
    }

    public BorderPane getViewport() {
        return viewport;
    }
}
