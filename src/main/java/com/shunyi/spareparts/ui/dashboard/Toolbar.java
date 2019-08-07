package com.shunyi.spareparts.ui.dashboard;

import com.shunyi.spareparts.ui.MainApp;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class Toolbar extends HBox implements EventHandler<MouseEvent> {

    private MainApp application;
    private Label dockedLeft;
    private FlowPane left = new FlowPane();
    private HBox right = new HBox();
    private Label menu = new Label();

    /**
     *
     * @param application
     */
    public Toolbar(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        this.setPrefHeight(65);
        String URL = "/img/dockedleft.png";
        Image ICON_48 = new Image(getClass().getResourceAsStream(URL));
        ImageView imageView = new ImageView(ICON_48);
        dockedLeft = new Label("", imageView);
        dockedLeft.setPadding(new Insets(16,20,15,15));
        initEvents();

        left.setPrefWidth(240);
        left.setPrefHeight(65);
        left.getChildren().addAll(dockedLeft);
        left.setAlignment(Pos.CENTER_RIGHT);
        left.setId("dockedleft_bg");

        right.setId("bg");
        right.prefWidthProperty().bind(application.getScene().widthProperty().subtract(240));
        Label title = new Label("Dashboard");
        title.setId("title");
        title.setPadding(new Insets(20));
        right.getChildren().add(title);
        right.setEffect(new DropShadow());


        String MENU_URL = "/img/menu.png";
        Image ICON = new Image(getClass().getResourceAsStream(MENU_URL));
        ImageView menuImg = new ImageView(ICON);
        menu.setGraphic(menuImg);
        menu.setPadding(new Insets(20));

        this.getChildren().addAll(left, right);
    }

    private void initEvents() {
        dockedLeft.setOnMouseEntered(this);
        dockedLeft.setOnMouseExited(this);
        dockedLeft.setOnMouseReleased(this);
        menu.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                undock();
            }
        });
    }

    private void dockedLeft() {
        this.getChildren().remove(0);
        right.getChildren().add(0, menu);
        right.prefWidthProperty().bind(application.getScene().widthProperty());

        application.getDashboard().getNavigation().minimize(true);
    }

    private void undock() {
        this.getChildren().add(0, left);
        right.getChildren().remove(0);
        right.prefWidthProperty().bind(application.getScene().widthProperty().subtract(240));

        application.getDashboard().getNavigation().minimize(false);
    }

    @Override
    public void handle(MouseEvent event) {
        if(MouseEvent.MOUSE_ENTERED.getName().equals(event.getEventType().getName())) {
            String URL = "/img/selected_dockedleft.png";
            Image ICON_48 = new Image(getClass().getResourceAsStream(URL));
            ImageView imageView = new ImageView(ICON_48);
            dockedLeft.setGraphic(imageView);
            dockedLeft.setPadding(new Insets(5));
        }
        else if (MouseEvent.MOUSE_EXITED.getName().equals(event.getEventType().getName())) {
            String URL = "/img/dockedleft.png";
            Image ICON_48 = new Image(getClass().getResourceAsStream(URL));
            ImageView imageView = new ImageView(ICON_48);
            dockedLeft.setGraphic(imageView);
            dockedLeft.setPadding(new Insets(16,20,15,15));
        }
        else if (MouseEvent.MOUSE_RELEASED.getName().equals(event.getEventType().getName())) {
            dockedLeft();
        }
    }
}
