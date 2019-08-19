package com.shunyi.spareparts.ui;

import com.shunyi.spareparts.ui.dashboard.Dashboard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage stage;
    private Scene scene;
    private Dashboard dashboard;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        scene = new Scene(new Pane());
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        gotoLogin();
        stage.setTitle("Spare parts");
        stage.setScene(scene);
        stage.show();
    }

    public void gotoDashboard() throws IOException {
        dashboard = new Dashboard(this);
        scene.setRoot(dashboard);
        stage.setResizable(true);
        stage.setMaximized(true);
    }

    public void gotoLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/login.fxml"
                )
        );
        Parent root = loader.load();
        LoginController controller = loader.<LoginController>getController();
        controller.setApp(this);
        scene.setRoot(root);
        stage.setResizable(false);
        stage.setMaximized(false);
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
