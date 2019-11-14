package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.model.AttributeValue;
import com.shunyi.autoparts.ui.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/** 属性值Checkbox */
public class AttributeValueCheckBox extends HBox {
    private Product selectedProduct;
    private CheckBox checkBoxWithoutName = new CheckBox();
    private HBox coloredBox = new HBox();
    private Label title = new Label();
    private AttributeValue attributeValue;
    private LinkedHashMap<Long, List<AttributeValueCheckBox>> groupMap;
    private TableColumn<ObservableList<String>, String> tableColumn;
    private TableView<ObservableList<String>> tableView;

    /**
     * Constructor
     *
     * @param selectedProduct
     * @param attributeValue
     * @param groupMap
     * @param tableColumn
     * @param tableView
     */
    AttributeValueCheckBox(Product selectedProduct,
                           AttributeValue attributeValue,
                           LinkedHashMap<Long, List<AttributeValueCheckBox>> groupMap,
                           TableColumn<ObservableList<String>, String> tableColumn,
                           TableView tableView) {
        this.selectedProduct = selectedProduct;
        this.attributeValue = attributeValue;
        this.groupMap = groupMap;
        this.tableColumn = tableColumn;
        this.tableView = tableView;
        if(attributeValue.getAttributeName().isColorProperty()) {
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
            transformTree();
        });
        coloredBox.setOnMouseClicked(e -> {
            checkBoxWithoutName.setSelected(!checkBoxWithoutName.isSelected());
            transformTree();
        });
        checkBoxWithoutName.setOnAction(e -> {
            transformTree();
        });
    }

    private boolean isSelected() {
        return checkBoxWithoutName.isSelected();
    }

    /** 更改商品属性表 */
    private void transformTree() {
        tableView.getItems().clear();
        List<AttributeValueCheckBox> buttonGroup = groupMap.get(attributeValue.getAttributeName().getId());
        long exist = buttonGroup.stream().filter(e -> e.isSelected()).count();
        tableColumn.setVisible(exist > 0);
        List<List<String>> list = new ArrayList<>();
        groupMap.forEach((k,v) -> {
            List<AttributeValueCheckBox> listBoxes = groupMap.get(k);
            List<String> strlist = listBoxes.stream().filter(e -> e.isSelected()).map(e -> e.attributeValue.getName()).collect(Collectors.toList());
            if(strlist.size() > 0) {
                list.add(strlist);
            }
        });
        String result = multiRound(list, "", 0);
        String[] array = result.split("\\$");


        for(String str : array) {
            ObservableList<String> data = FXCollections.observableArrayList();
            String[] subArray = str.split("/");
            for(String v : subArray) {
                data.add(v);
            }
            data.add("0");//价格
            data.add("0");//数量
            tableView.getItems().add(data);
        }
    }

    public String multiRound(List<List<String>> dataList, String temp, int index) {
        if (index >= dataList.size()) {
            return "";
        }
        StringBuffer out = new StringBuffer();
        String tmp = "";
        List<String> data = dataList.get(index);
        for (int i = 0; i < data.size(); i++) {
            tmp = data.get(i) + "/";
            if (index < dataList.size()) {
                out.append(multiRound(dataList, temp + tmp, index + 1));
            }
            if (index == dataList.size() - 1) {
                out.append(temp).append(tmp).append("$");
            }
        }
        return out.toString();
    }
}
