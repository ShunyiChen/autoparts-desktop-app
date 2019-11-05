package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.AttributeName;
import com.shunyi.autoparts.ui.model.Category;
import com.shunyi.autoparts.ui.model.SKU;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Arrays;

public class SKUGeneratorController {

    Stage subStage;
    Category selectedCategory;

    @FXML
    VBox pnlRows;

    @FXML
    void ok(ActionEvent actionEvent) {
        subStage.close();
    }

    @FXML
    void cancel(ActionEvent actionEvent) {
        subStage.close();
    }

    public void prepare(Stage subStage, Category selectedCategory, Callback<SKU, Void> callback) {
        this.subStage = subStage;
        this.selectedCategory = selectedCategory;
        initRows();
    }

    void initRows() {
        String json = "";
        try {
            json = HttpClient.GET("/attributes/name/category/"+selectedCategory.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttributeName[] attributeNames = GoogleJson.GET().fromJson(json, AttributeName[].class);
        for(AttributeName attributeName : attributeNames) {
            FlowPane row = createRow(attributeName);
            pnlRows.getChildren().add(row);
        }
    }

    FlowPane createRow(AttributeName attributeName) {
        FlowPane row = new FlowPane();
        row.setPrefHeight(49);
        Label title = new Label();
        title.setFont(Font.font(14));
        title.setText(attributeName.getName());
        row.getChildren().add(title);
        row.setAlignment(Pos.CENTER_LEFT);

        return row;
    }
}
