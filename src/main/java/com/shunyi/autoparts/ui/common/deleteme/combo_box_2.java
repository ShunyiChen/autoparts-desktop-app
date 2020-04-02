package com.shunyi.autoparts.ui.common.deleteme;

// Java program to create a combo box and add event handler to it
import com.shunyi.autoparts.ui.common.AutoCompleteBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.*;
import javafx.stage.Stage;

public class combo_box_2 extends Application {

    // Launch the application
    @Override
    public void start(Stage stage)
    {
        // Set title for the stage
        stage.setTitle("creating combo box ");

        // Create a tile pane
        TilePane r = new TilePane();

        // Create a label
        Label description_label =
                new Label("This is a combo box example ");

        // Weekdays
        String week_days[] = new String[100];// { "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday" };

        for(int i = 0;i < 100; i++) {
            week_days[i] = i+"--"+i;
        }

        // Create a combo box
//        ComboBox combo_box =
//                new ComboBox(FXCollections
//                        .observableArrayList(week_days));
        ComboBox myComboBox = new ComboBox(FXCollections.observableArrayList(week_days));
        myComboBox.setStyle("-fx-font-size: 14;");
        //usually list of combobox items here
        new AutoCompleteBox( myComboBox );

//        new AutoCompleteComboBoxListener(myComboBox);

        // Create a tile pane
        HBox hBox = new HBox();
        hBox.getChildren().add(myComboBox);
        // Create a scene
        Scene scene = new Scene(hBox, 400, 400);

        // Set the scene
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String args[])
    {
        // Launch the application
        launch(args);
    }
}