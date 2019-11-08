package com.shunyi.autoparts.ui.common.example;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//        primaryStage.setTitle("");
//        Group root = new Group();
//        Scene scene = new Scene(root, 300, 250, Color.WHITE);
//
//        HBox hbox = new HBox();
//        hbox.setStyle("-fx-background-color:red;");
//        Button button1 = new Button("Add");
//        Button button2 = new Button("Remove");
//        Button button22 = new Button("Remove");
////        HBox.setHgrow(button1, Priority.ALWAYS);
////        HBox.setHgrow(button2, Priority.ALWAYS);
////        button1.setMaxWidth(Double.MAX_VALUE);
////        button2.setMaxWidth(Double.MAX_VALUE);
//        hbox.getChildren().addAll(button1, button2, button22);
//
////        hbox.setPrefWidth(400);
//
//        root.getChildren().add(hbox);
//        primaryStage.setScene(scene);
//        primaryStage.show();

        float[] n = {7.5f, 19.5f, 21.0f,13.5f,9f,13.50f,21.0f,9f,15.0f,7.5f,10.5f ,7.5f, 9.0f, 18.0f, 18.0f, 6f, 18.0f, 3f, 9f, 3f, 13.5f, 7.5f };

        float sum = 0f;
        for(int i = 0; i < n.length; i++) {
            sum = sum + n[i];
        }
        System.out.println("sum="+sum);

    }
}