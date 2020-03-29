package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.vo.AttributeValue;
import com.shunyi.autoparts.ui.common.vo.SKU;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 属性值Checkbox
 */
public class AttributeValueCheckBox extends HBox {
    private CheckBox checkBoxWithoutName = new CheckBox();
    private HBox coloredBox = new HBox();
    private Label title = new Label();
    private AttributeValue attributeValue;
    private TextField txtFieldNotes;
    private LinkedHashMap<Long, List<AttributeValueCheckBox>> checkboxGroup;
    private SKU updatedSKU;

    /**
     * Constructor
     *
     * @param attributeValue
     * @param txtFieldNotes
     */
     public AttributeValueCheckBox(AttributeValue attributeValue,
                                   TextField txtFieldNotes,
                                   LinkedHashMap<Long,List<AttributeValueCheckBox>> checkboxGroup, SKU updatedSKU) {
        this.attributeValue = attributeValue;
        this.txtFieldNotes = txtFieldNotes;
        this.checkboxGroup = checkboxGroup;
        this.updatedSKU = updatedSKU;

        if (attributeValue.getAttributeName().getColorProperty()) {
            coloredBox.setPrefSize(40, 15);
            String[] str = attributeValue.getRgb().split(",");
            int red = (int) (Double.parseDouble(str[0]) * 255);
            int green = (int) (Double.parseDouble(str[1]) * 255);
            int blue = (int) (Double.parseDouble(str[2]) * 255);
            String style = "-fx-font-size: 14px;" +
                    "-fx-background-color:rgb(" + red + "," + green + "," + blue + ");" +
                    "-fx-border-radius:0;" +
                    "-fx-border-color:rgb(244,244,244);" +
                    "-fx-border-width:2;";
            coloredBox.setStyle(style);
            this.getChildren().addAll(checkBoxWithoutName, title, coloredBox);
        } else {
            this.getChildren().addAll(checkBoxWithoutName, title);
        }
        title.setText(attributeValue.getName());
        title.setFont(Font.font(14));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-font-size: 14px;");
        this.setPadding(new Insets(0, 20, 0, 0));
        this.setSpacing(3);
        this.setMinWidth(100);

        initEvents();
    }

    private void initEvents() {
        checkBoxWithoutName.setOnAction(e -> {
            fire();
        });
    }

    private void fire() {
        StringBuilder specification = new StringBuilder();
        StringBuilder properties = new StringBuilder();
        Iterator<Long> iter = checkboxGroup.keySet().iterator();
        while(iter.hasNext()) {
            Long key = iter.next();
            List<AttributeValueCheckBox> list = checkboxGroup.get(key);
            for(AttributeValueCheckBox checkBox : list) {
                if(checkBox.isCheckBoxSelected()) {
                    specification.append(checkBox.attributeValue.getName());
                    specification.append(" - ");
                    properties.append(checkBox.attributeValue.getAttributeName().getId()+":"+checkBox.attributeValue.getId());
                    properties.append(";");
                }
            }
        }
        if(specification.toString().endsWith(" - ")) {
            specification.delete(specification.length() - 3, specification.length());
        }
        txtFieldNotes.setText(specification.toString());
        updatedSKU.setProperties(properties.toString());
        updatedSKU.setSpecification(specification.toString());
        updatedSKU.setSkuName(specification.toString());
    }

    private boolean isCheckBoxSelected() {
         return checkBoxWithoutName.isSelected();
    }

    public void setSelected(boolean selected) {
        checkBoxWithoutName.setSelected(selected);
        fire();
    }
}
