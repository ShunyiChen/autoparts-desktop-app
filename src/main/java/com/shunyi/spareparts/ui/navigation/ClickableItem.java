package com.shunyi.spareparts.ui.navigation;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;


public class ClickableItem extends HBox implements EventHandler<MouseEvent>{
    private Image icon;
    private String txt;
    private Label rightTxt;
    private static final int DEFAULT_ITEM_HEIGHT = 50;
    private static final int DEFAULT_FONT_SIZE = 16;
    public static final int DEFAULT_MAX_WIDTH = 240;
    public static final int DEFAULT_MINI_WIDTH = 60;
    /**
     *
     * @param icon
     * @param txt
     */
    public ClickableItem(Image icon, String txt) {
        this.icon = icon;
        this.txt = txt;
        initComponents();
    }

    private void initComponents() {
        ImageView imageView = new ImageView(icon);
        Label icon = new Label("", imageView);
        icon.setPadding(new Insets(10,15,10,15));
        rightTxt = new Label(txt);
        rightTxt.setContentDisplay(ContentDisplay.LEFT);
        rightTxt.setPadding(new Insets(10,15,10,15));
        rightTxt.setFont(Font.font(DEFAULT_FONT_SIZE));
        this.getChildren().addAll(icon, rightTxt);
        this.setPrefSize(DEFAULT_MAX_WIDTH, DEFAULT_ITEM_HEIGHT);
        this.setAlignment(Pos.CENTER_LEFT);
        initEvents();
    }

    private void initEvents() {
        this.setOnMouseEntered(this);
        this.setOnMouseExited(this);
    }

    @Override
    public void handle(MouseEvent event) {
        if(MouseEvent.MOUSE_ENTERED.getName().equals(event.getEventType().getName())) {
            this.setId("clickableitem_selected_bg");
        }
        else if (MouseEvent.MOUSE_EXITED.getName().equals(event.getEventType().getName())) {
            this.setId("clickableitem_bg");
        }
    }

    public void showOnlyIcon(boolean bool) {
        if (bool) {
            this.getChildren().remove(rightTxt);
            this.setPrefSize(DEFAULT_MINI_WIDTH, DEFAULT_ITEM_HEIGHT);
        } else {
            this.getChildren().add(1,rightTxt);
            this.setPrefSize(DEFAULT_MAX_WIDTH, DEFAULT_ITEM_HEIGHT);
        }
    }
}
