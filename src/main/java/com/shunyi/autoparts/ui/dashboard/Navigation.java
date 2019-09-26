package com.shunyi.autoparts.ui.dashboard;

import com.shunyi.autoparts.ui.MainApp;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class Navigation extends VBox {

    private MainApp application;

    public Navigation(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        this.setBorder(new Border(new BorderStroke(Color.rgb(235,235,235), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setPrefWidth(240);
        this.setId("navigation_bg");
        this.prefHeightProperty().bind(application.getScene().heightProperty());
    }

    public void addClickableItem(ClickableItem... items) {
        this.getChildren().addAll(items);
    }

    public void minimize(boolean bool) {
        Iterator iter = this.getChildren().iterator();
        while (iter.hasNext()) {
            Object obj = iter.next();
            if(obj instanceof ClickableItem) {
                ClickableItem item = (ClickableItem) obj;
                item.showOnlyIcon(bool);
            }
        }
        if(bool) {
            this.setPrefWidth(ClickableItem.DEFAULT_MINI_WIDTH);
        }
        else {
            this.setPrefWidth(ClickableItem.DEFAULT_MAX_WIDTH);
        }
    }
}
