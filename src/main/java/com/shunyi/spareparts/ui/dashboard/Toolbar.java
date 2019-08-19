package com.shunyi.spareparts.ui.dashboard;

import com.shunyi.spareparts.ui.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class Toolbar extends HBox implements EventHandler<MouseEvent> {

    private MainApp application;
    private Label dockedLeft;
    private FlowPane left = new FlowPane();
    private HBox right = new HBox();
    private Label menu = new Label();
    private Label title = new Label("仪表盘");
    private ContextMenu profileMenu;

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

        profileMenu = contextMenu();

        left.setPrefWidth(240);
        left.setPrefHeight(65);
        left.getChildren().addAll(dockedLeft);
        left.setAlignment(Pos.CENTER_RIGHT);
        left.setId("dockedleft_bg");

        right.setId("bg");
        right.prefWidthProperty().bind(application.getScene().widthProperty().subtract(240));
        title.setId("title");
        title.setPadding(new Insets(20));
        right.getChildren().add(title);
        right.setEffect(new DropShadow());


        String MENU_URL = "/img/menu.png";
        Image ICON = new Image(getClass().getResourceAsStream(MENU_URL));
        ImageView menuImg = new ImageView(ICON);
        menu.setGraphic(menuImg);
        menu.setPadding(new Insets(20));

        Image profile = new Image(getClass().getResourceAsStream("/img/profile.png"));
        ImageView profileImgView = new ImageView(profile);

        profileImgView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                if(e.getButton() == MouseButton.PRIMARY) {
                    profileMenu.show(Toolbar.this, e.getScreenX(), e.getScreenY());
                }
            }
        });


        right.getChildren().add(profileImgView);

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

    public void setTitle(String title) {
        this.title.setText(title);
    }


    private ContextMenu contextMenu() {
        ContextMenu menu = new ContextMenu();
        menu.setAutoHide(true);
        MenuItem itemSettings = new MenuItem("个人设置");
        MenuItem itemExit = new MenuItem("退 出");
        itemSettings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        itemExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        menu.getItems().addAll(itemSettings, itemExit);
        return menu;
    }
}
