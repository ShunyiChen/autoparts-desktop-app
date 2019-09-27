package com.shunyi.autoparts.ui;

import com.shunyi.autoparts.ui.login.LoginController;
import com.shunyi.autoparts.ui.main.MainFrame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private Stage stage;
    private Scene scene;
    private MainFrame dashboard;
    private static String Name = "AutoParts-桌面客户端";
    private static String VERSION = "v1.0";

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        scene = new Scene(new Pane());
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        gotoLogin();
        stage.setTitle(Name+" "+VERSION);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoDashboard() {
        dashboard = new MainFrame(this);
        scene.setRoot(dashboard);
        stage.setResizable(true);
        stage.setMaximized(true);
    }

    public void gotoLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(
                    "/fxml/login/login.fxml"
            )
        );
        StackPane root = loader.load();
        LoginController controller = loader.getController();
        controller.initialize(this);
        scene.setRoot(root);
        stage.setResizable(false);
        stage.setMaximized(false);
    }

    public void quit() {
        Platform.exit();
        System.exit(0);
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public MainFrame getDashboard() {
        return dashboard;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
