package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.AttributeValue;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class AttributeValueEditorController {
    AttributeValue updated;

    @FXML
    ColorPicker colorPicker;
    @FXML
    ComboBox<String> boxStatus;
    @FXML
    ComboBox<String> boxSort;

    public void prepare(AttributeValue selectedAttributeValue) {
        this.updated = selectedAttributeValue;
        initComboBox();
        boxStatus.setValue(selectedAttributeValue.getStatus());
        boxSort.setValue(selectedAttributeValue.getSort() == 1?Sort.ASC.getName():Sort.DESC.getName());
        String[] str = selectedAttributeValue.getRgb().split(",");
        colorPicker.setValue(new Color(Double.valueOf(str[0]), Double.valueOf(str[1]), Double.valueOf(str[2]), 1));
        initEvents();
    }

    void initComboBox() {
        boxStatus.getItems().addAll(AttributeStatus.AVAILABLE.getName(), AttributeStatus.DISABLED.getName());
        boxSort.getItems().addAll(Sort.ASC.getName(), Sort.DESC.getName());
        boxStatus.setStyle("-fx-font-size: 14px;");
        boxSort.setStyle("-fx-font-size: 14px;");
    }

    void initEvents() {
        colorPicker.setOnAction(e -> {
            updated.setRgb(colorPicker.getValue().getRed()+","+colorPicker.getValue().getGreen()+","+colorPicker.getValue().getBlue());
            update();
        });
        boxStatus.setOnAction(e -> {
            updated.setStatus(boxStatus.getValue());
            update();
        });
        boxSort.setOnAction(e -> {
            updated.setSort(boxSort.getValue().equals(Sort.ASC.getName())?1:-1);
            update();
        });
    }

    void update() {
        String json = GoogleJson.GET().toJson(updated);
        try {
            HttpClient.PUT("/attributes/value/"+updated.getId(), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
