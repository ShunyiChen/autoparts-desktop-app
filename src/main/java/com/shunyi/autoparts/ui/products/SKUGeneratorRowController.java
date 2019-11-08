package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.AttributeName;
import com.shunyi.autoparts.ui.model.AttributeValue;
import com.shunyi.autoparts.ui.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;

import java.io.IOException;

/** SKU */
public class SKUGeneratorRowController {
    @FXML
    Label titleLabel;
    @FXML
    FlowPane rootPanel;

    public void prepare(Product selectedProduct, AttributeName attributeName, Callback<Void, Void> callback) {
        titleLabel.setText(attributeName.getName()+":");
        String json = null;
        try {
            json = HttpClient.GET("/attributes/value/name/"+attributeName.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttributeValue[] attributeValues = GoogleJson.GET().fromJson(json, AttributeValue[].class);
        for(AttributeValue attributeValue : attributeValues) {
            AttributeValueCheckBox coloredCheckBox = new AttributeValueCheckBox(selectedProduct, attributeValue, attributeName.isColorProperty());
            coloredCheckBox.setCallback(callback);
            rootPanel.getChildren().add(coloredCheckBox);
        }
    }
}
