package com.shunyi.autoparts.ui.purchase.example;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TableViewEditOnType extends Application {


    @Override
    public void start(Stage primaryStage) {
        TableView<List<StringProperty>> table = new TableView<>();
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setEditable(true);

        for (int i = 0; i < 10; i++) {
            table.getColumns().add(createColumn(i));

            List<StringProperty> rowData = new ArrayList<>();
            table.getItems().add(rowData);
            for (int j = 0; j < 10 ; j++) {
                rowData.add(new SimpleStringProperty(String.format("Cell [%d, %d]", i, j)));
            }
        }

        table.setOnKeyTyped(event -> {
            TablePosition<List<StringProperty>, String> focusedCell = table.getFocusModel().getFocusedCell();
            if (focusedCell != null) {
                table.getItems().get(focusedCell.getRow()).get(focusedCell.getColumn()).set(event.getCharacter());
                table.edit(focusedCell.getRow(), focusedCell.getTableColumn());
            }
        });

        Scene scene = new Scene(new BorderPane(table), 880, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableColumn<List<StringProperty>, String> createColumn(int colIndex) {
        TableColumn<List<StringProperty>, String> col = new TableColumn<>("Column "+colIndex);
        col.setCellValueFactory(cellData -> cellData.getValue().get(colIndex));
        col.setCellFactory(column -> new EditCell());
        return col ;
    }

    private static class EditCell extends TableCell<List<StringProperty>, String> {

        private final TextField textField = new TextField();

        EditCell() {
            textProperty().bind(itemProperty());
            setGraphic(textField);
            setContentDisplay(ContentDisplay.TEXT_ONLY);

            textField.setOnAction(evt -> commitEdit(textField.getText()));
            textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (! isNowFocused) {
                    commitEdit(textField.getText());
                }
            });
        }

        @Override
        public void startEdit() {
            super.startEdit();
            textField.setText(getItem());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.requestFocus();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void commitEdit(String text) {
            super.commitEdit(text);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}