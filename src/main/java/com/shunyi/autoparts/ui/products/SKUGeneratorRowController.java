package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.Attribute;
import com.shunyi.autoparts.ui.model.AttributeName;
import com.shunyi.autoparts.ui.model.AttributeValue;
import com.shunyi.autoparts.ui.model.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/** SKU */
public class SKUGeneratorRowController {
    @FXML
    Label titleLabel;
    @FXML
    FlowPane rootPanel;

    public void prepare(Product selectedProduct,
                        AttributeName attributeName,
                        LinkedHashMap<Long, List<AttributeValueCheckBox>> checkboxGroup,
                        TableColumn<ObservableList<TableCellMetadata>, String> tableColumn,
                        TableView<ObservableList<TableCellMetadata>> tableView, Attribute[] attributes) {
        titleLabel.setText(attributeName.getName()+":");
        String json = null;
        try {
            json = HttpClient.GET("/attributes/value/name/"+attributeName.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<AttributeValueCheckBox> buttonGroup = new ArrayList<>();
        checkboxGroup.put(attributeName.getId(), buttonGroup);
        AttributeValue[] attributeValues = GoogleJson.GET().fromJson(json, AttributeValue[].class);
        AttributeValueCheckBox coloredCheckBox = null;
        for(AttributeValue attributeValue : attributeValues) {
            coloredCheckBox = new AttributeValueCheckBox(selectedProduct, attributeValue, checkboxGroup, tableColumn, tableView);
            coloredCheckBox.setSelected(shouldBeSelected(attributeValue, attributes));
            buttonGroup.add(coloredCheckBox);
            rootPanel.getChildren().add(coloredCheckBox);
        }

        //当有数据初始化时，执行一次表格
        if(coloredCheckBox != null) {
            coloredCheckBox.updateTable();
        }
    }

    private boolean shouldBeSelected(AttributeValue attributeValue, Attribute[] attributes) {
        for(Attribute attribute : attributes) {
            if(attributeValue.getId() == attribute.getAttributeValueId()) {
                return true;
            }
        }
        return false;
    }
}
