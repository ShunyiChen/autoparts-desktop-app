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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class Toolbar extends HBox implements EventHandler<MouseEvent> {

    private MainApp application;
    private Label arrowIcon;
    private Label menu = new Label();
    private Label title = new Label("仪表盘");
    private FlowPane leftComponent = new FlowPane();
    private BorderPane rightComponent = new BorderPane();
    private HBox rightSubComponent = new HBox();
    private HBox rightSubComponent2 = new HBox();

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
        arrowIcon = new Label("", imageView);
        arrowIcon.setPadding(new Insets(16,20,15,15));
        this.getChildren().addAll(leftComponent, rightComponent);

        initLeftComponent();

        initRightComponent();

        initMenu();

        initEvents();

//        profileMenu = contextMenu();
//        left.setPrefWidth(240);
//        left.setPrefHeight(65);
//        left.getChildren().addAll(dockedLeft);
//        left.setAlignment(Pos.CENTER_RIGHT);
//        left.setId("dockedleft_bg");

//        this.setId("bg");
//        right.prefWidthProperty().bind(application.getScene().widthProperty().subtract(240));
//        title.setId("title");
//        title.setPadding(new Insets(20));
////        right.setRight(title);
//        right.getChildren().add(title);
//        right.setEffect(new DropShadow());




    }

    private void initLeftComponent() {
        leftComponent.setId("dockedleft_bg");
        leftComponent.setAlignment(Pos.CENTER_RIGHT);
        leftComponent.getChildren().addAll(arrowIcon);
        leftComponent.setPrefWidth(240);
        leftComponent.setPrefHeight(65);
    }

    private void initRightComponent() {
        rightComponent.setId("bg");
        rightComponent.setEffect(new DropShadow());
        rightComponent.prefWidthProperty().bind(application.getScene().widthProperty().subtract(240));
        rightComponent.setLeft(rightSubComponent);
        rightSubComponent.getChildren().addAll(title);
        title.setId("title");
        title.setPadding(new Insets(20));


        Image profile = new Image(getClass().getResourceAsStream("/img/Profile.png"));
        ImageView profileImage = new ImageView(profile);
        profileImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(e.getButton() == MouseButton.PRIMARY) {
                    MenuItem itemQuit = new MenuItem("退 出");
                    MenuItem itemProfiles = new MenuItem("个人设置");
                    ContextMenu menu = new ContextMenu();
                    menu.setAutoHide(true);
                    menu.getItems().addAll(itemProfiles, itemQuit);
                    menu.show(Toolbar.this, e.getScreenX(), e.getScreenY());
                }
            }
        });
        rightSubComponent2.setPadding(new Insets(10));
        rightSubComponent2.getChildren().add(profileImage);
        rightSubComponent2.setAlignment(Pos.CENTER_RIGHT);
        rightComponent.setRight(rightSubComponent2);
    }

    private void initMenu() {
        String MENU_URL = "/img/menu.png";
        Image ICON = new Image(getClass().getResourceAsStream(MENU_URL));
        ImageView menuImg = new ImageView(ICON);
        menu.setGraphic(menuImg);
        menu.setPadding(new Insets(20));
    }

    private void initEvents() {
        arrowIcon.setOnMouseEntered(this);
        arrowIcon.setOnMouseExited(this);
        arrowIcon.setOnMouseReleased(this);
        menu.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                undock();
            }
        });
    }

    private void dock() {
        this.getChildren().remove(leftComponent);
        rightSubComponent.getChildren().add(0, menu);
        rightComponent.prefWidthProperty().bind(application.getScene().widthProperty());
        application.getDashboard().getNavigation().minimize(true);
    }

    private void undock() {
        this.getChildren().add(0, leftComponent);
        rightSubComponent.getChildren().remove(menu);
        rightComponent.prefWidthProperty().bind(application.getScene().widthProperty().subtract(240));
        application.getDashboard().getNavigation().minimize(false);
    }

    @Override
    public void handle(MouseEvent event) {
        if(MouseEvent.MOUSE_ENTERED.getName().equals(event.getEventType().getName())) {
            String URL = "/img/selected_dockedleft.png";
            Image ICON_48 = new Image(getClass().getResourceAsStream(URL));
            ImageView imageView = new ImageView(ICON_48);
            arrowIcon.setGraphic(imageView);
            arrowIcon.setPadding(new Insets(5));
        }
        else if (MouseEvent.MOUSE_EXITED.getName().equals(event.getEventType().getName())) {
            String URL = "/img/dockedleft.png";
            Image ICON_48 = new Image(getClass().getResourceAsStream(URL));
            ImageView imageView = new ImageView(ICON_48);
            arrowIcon.setGraphic(imageView);
            arrowIcon.setPadding(new Insets(16,20,15,15));
        }
        else if (MouseEvent.MOUSE_RELEASED.getName().equals(event.getEventType().getName())) {
            dock();
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
