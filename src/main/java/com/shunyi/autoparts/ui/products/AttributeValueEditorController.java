package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.AttributeName;
import com.shunyi.autoparts.ui.common.vo.AttributeValue;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * @author Shunyi Chen
 */
public class AttributeValueEditorController {
    AttributeValue updated;

    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Label colorLabel;
    @FXML
    private ComboBox<String> boxStatus;
    @FXML
    private ComboBox<String> boxSort;

    public void prepare(AttributeName selectedAttributeName, AttributeValue selectedAttributeValue) {
        this.updated = selectedAttributeValue;
        initComboBox();
        boxStatus.setValue(selectedAttributeValue.getStatus());
        boxSort.setValue(selectedAttributeValue.getSort() == 1? Constants.ASC : Constants.DESC);
        String[] str = selectedAttributeValue.getRgb().split(",");
        colorPicker.setValue(new Color(Double.valueOf(str[0]), Double.valueOf(str[1]), Double.valueOf(str[2]), 1));
        initEvents();
        // 如果不是颜色属性，则隐藏颜色选择项

        if(!selectedAttributeName.getColorProperty()) {
            colorLabel.setVisible(false);
            colorPicker.setVisible(false);
        }
    }

    private void initComboBox() {
        boxStatus.getItems().addAll(Constants.AVAILABLE, Constants.UNAVAILABLE);
        boxSort.getItems().addAll(Constants.ASC, Constants.DESC);
        boxStatus.setStyle("-fx-font-size: 14px;");
        boxSort.setStyle("-fx-font-size: 14px;");
    }

    private void initEvents() {
        colorPicker.setOnAction(e -> {
            updated.setRgb(colorPicker.getValue().getRed()+","+colorPicker.getValue().getGreen()+","+colorPicker.getValue().getBlue());
            update();
        });
        boxStatus.setOnAction(e -> {
            updated.setStatus(boxStatus.getValue());
            update();
        });
        boxSort.setOnAction(e -> {
            updated.setSort(boxSort.getValue().equals(Constants.ASC)? 1: -1);
            update();
        });
    }

    private void update() {
        String json = GoogleJson.GET().toJson(updated);
        try {
            HttpClient.PUT("/attributes/value/"+updated.getId(), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
