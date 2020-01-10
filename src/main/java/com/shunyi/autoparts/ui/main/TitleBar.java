package com.shunyi.autoparts.ui.main;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.ICONS;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * @Description: 主界面标题栏
 * @Author: 陈顺谊
 * @CreateDate: 2020/1/8 16:24
 * @Version: 1.0
 */
public class TitleBar extends HBox implements EventHandler<MouseEvent> {
    private MainApp application;
    private Label arrowIcon;
    private Label menuIcon = new Label();
    private Label title = new Label("仪表盘");
    private FlowPane leftComponent = new FlowPane();
    private BorderPane rightComponent = new BorderPane();
    private HBox rightSubComponent = new HBox();
    private HBox rightSubComponent2 = new HBox();
    private ContextMenu rightClickMenu = new ContextMenu();
    private MenuItem itemProfiles = new MenuItem("个人设置");
    private MenuItem itemLogout = new MenuItem("注 销");
    private MenuItem itemTheme = new MenuItem("主 题");
    private MenuItem itemQuit = new MenuItem("退 出");
    private ICONS icons = ICONS.getInstance();

    /**
     *
     * @param application
     */
    public TitleBar(MainApp application) {
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
        initMenuIcon();
        initRightClickMenu();
        initEvents();
    }

    /**
     *
     */
    private void initLeftComponent() {
        leftComponent.setId("dockedleft_bg");
        leftComponent.setAlignment(Pos.CENTER_RIGHT);
        leftComponent.getChildren().addAll(arrowIcon);
        leftComponent.setPrefWidth(240);
        leftComponent.setPrefHeight(65);
    }

    /**
     *
     */
    private void initRightComponent() {
        rightComponent.setId("bg");
        rightComponent.setEffect(new DropShadow());
        rightComponent.prefWidthProperty().bind(application.getScene().widthProperty().subtract(240));
        rightComponent.setLeft(rightSubComponent);
        rightSubComponent.getChildren().addAll(title);
        title.setId("title");
        title.setPadding(new Insets(20));
        ImageView profileImage = new ImageView(icons.account_circle());
        profileImage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(e.getButton() == MouseButton.PRIMARY) {
                    rightClickMenu.show(TitleBar.this, e.getScreenX(), e.getScreenY());
                }
            }
        });
        rightSubComponent2.setPadding(new Insets(10));
        rightSubComponent2.getChildren().add(profileImage);
        rightSubComponent2.setAlignment(Pos.CENTER_RIGHT);
        rightComponent.setRight(rightSubComponent2);
    }

    private void initRightClickMenu() {
        rightClickMenu.setAutoHide(true);
        rightClickMenu.getItems().addAll(itemProfiles, itemTheme, new SeparatorMenuItem(), itemLogout, itemQuit);
    }

    private void initMenuIcon() {
        String MENU_URL = "/img/menu.png";
        Image ICON = new Image(getClass().getResourceAsStream(MENU_URL));
        ImageView menuImg = new ImageView(ICON);
        menuIcon.setGraphic(menuImg);
        menuIcon.setPadding(new Insets(20));
    }

    private void initEvents() {
        arrowIcon.setOnMouseEntered(this);
        arrowIcon.setOnMouseExited(this);
        arrowIcon.setOnMouseReleased(this);
        menuIcon.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                undock();
            }
        });
        itemProfiles.setOnAction(e -> {
        });
        itemLogout.setOnAction(e -> {
            application.gotoLogin();
        });
        itemQuit.setOnAction(e -> {
            application.quit();
        });
    }

    private void dock() {
        this.getChildren().remove(leftComponent);
        rightSubComponent.getChildren().add(0, menuIcon);
        rightComponent.prefWidthProperty().bind(application.getScene().widthProperty());
        application.getDashboard().getNavigation().minimize(true);
    }

    private void undock() {
        this.getChildren().add(0, leftComponent);
        rightSubComponent.getChildren().remove(menuIcon);
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
}
