package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.Attribute;
import com.shunyi.autoparts.ui.model.AttributeValue;
import com.shunyi.autoparts.ui.model.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.io.IOException;

/** 属性值Checkbox */
public class AttributeValueCheckBox extends HBox {
    private Product selectedProduct;
    private CheckBox checkBoxWithoutName = new CheckBox();
    private HBox coloredBox = new HBox();
    private Label title = new Label();
    private AttributeValue attributeValue;
    private Callback<Void, Void> callback;

    /**
     * Constructor
     *
     * @param attributeValue 属性值
     * @param colored 是否带颜色
     */
    AttributeValueCheckBox(Product selectedProduct, AttributeValue attributeValue, boolean colored) {
        this.selectedProduct = selectedProduct;
        this.attributeValue = attributeValue;
        if(colored) {
            coloredBox.setPrefSize(20, 15);
            String[] str = attributeValue.getRgb().split(",");
            int red = (int) (Double.parseDouble(str[0]) * 255);
            int green = (int) (Double.parseDouble(str[1]) * 255);
            int blue = (int) (Double.parseDouble(str[2]) * 255);
            String style = "-fx-font-size: 14px;" +
                    "-fx-background-color:rgb("+red+","+green+","+blue+");" +
                    "-fx-border-radius:3;" +
                    "-fx-border-color:rgb(244,244,244);" +
                    "-fx-border-width:2;";
            coloredBox.setStyle(style);
            this.getChildren().addAll(checkBoxWithoutName, coloredBox, title);
        } else {
            this.getChildren().addAll(checkBoxWithoutName, title);
        }
        title.setText(attributeValue.getName());
        title.setFont(Font.font(14));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-font-size: 14px;");
        this.setPadding(new Insets(0,20,0,0));
        this.setSpacing(3);
        this.setMinWidth(100);

        initEvents();
    }

    private void initEvents() {
        title.setOnMouseClicked(e -> {
            checkBoxWithoutName.setSelected(!checkBoxWithoutName.isSelected());
            updateAttributeTable();
            callback.call(null);
        });
        coloredBox.setOnMouseClicked(e -> {
            checkBoxWithoutName.setSelected(!checkBoxWithoutName.isSelected());
            updateAttributeTable();
            callback.call(null);
        });
        checkBoxWithoutName.setOnAction(e -> {
            updateAttributeTable();
            callback.call(null);
        });
    }

    /** 更改商品属性表 */
    private void updateAttributeTable() {
        if(checkBoxWithoutName.isSelected()) {
            Attribute attribute = new Attribute(selectedProduct, attributeValue.getAttributeName().getId(), attributeValue.getId(), true, null);
            String json = GoogleJson.GET().toJson(attribute);
            try {
                HttpClient.POST("/attributes", json);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                HttpClient.DELETE("/attributes/"+selectedProduct.getId()+"/"+attributeValue.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCallback(Callback<Void, Void> callback) {
        this.callback = callback;
    }

    public void setSelected(boolean selected) {
        checkBoxWithoutName.setSelected(selected);
    }

    public boolean isSelected() {
        return checkBoxWithoutName.isSelected();
    }
}
