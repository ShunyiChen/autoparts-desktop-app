package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.model.AttributeValue;
import com.shunyi.autoparts.ui.model.Product;
import javafx.beans.property.SimpleObjectProperty;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * 属性值Checkbox
 */
public class AttributeValueCheckBox extends HBox {
    private Product selectedProduct;
    private CheckBox checkBoxWithoutName = new CheckBox();
    private HBox coloredBox = new HBox();
    private Label title = new Label();
    private AttributeValue attributeValue;
    private LinkedHashMap<Long, List<AttributeValueCheckBox>> checkboxGroup;
    private TableColumn<ObservableList<String>, String> tableColumn;
    private TableView<ObservableList<String>> tableView;

    /**
     * Constructor
     *
     * @param selectedProduct 选择商品
     * @param attributeValue  属性值
     * @param checkboxGroup   复选框组
     * @param tableColumn     表格列
     * @param tableView       表格
     */
    AttributeValueCheckBox(Product selectedProduct,
                           AttributeValue attributeValue,
                           LinkedHashMap<Long, List<AttributeValueCheckBox>> checkboxGroup,
                           TableColumn<ObservableList<String>, String> tableColumn,
                           TableView tableView) {

        this.selectedProduct = selectedProduct;
        this.attributeValue = attributeValue;
        this.checkboxGroup = checkboxGroup;
        this.tableColumn = tableColumn;
        this.tableView = tableView;

        if (attributeValue.getAttributeName().isColorProperty()) {
            coloredBox.setPrefSize(20, 15);
            String[] str = attributeValue.getRgb().split(",");
            int red = (int) (Double.parseDouble(str[0]) * 255);
            int green = (int) (Double.parseDouble(str[1]) * 255);
            int blue = (int) (Double.parseDouble(str[2]) * 255);
            String style = "-fx-font-size: 14px;" +
                    "-fx-background-color:rgb(" + red + "," + green + "," + blue + ");" +
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
        this.setPadding(new Insets(0, 20, 0, 0));
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

    /**
     * 更改商品属性表
     */
    private void transformTree() {
        List<AttributeValueCheckBox> buttonGroup = checkboxGroup.get(attributeValue.getAttributeName().getId());
        long exist = buttonGroup.stream().filter(e -> e.isSelected()).count();
        if (exist > 0) {
            if (!tableView.getColumns().contains(tableColumn)) {
                tableView.getColumns().add(tableColumn);
            }
        } else {
            tableView.getColumns().remove(tableColumn);
        }
        //表格销售属性列排序
        Collections.sort(tableView.getColumns(), Comparator.comparing(TableColumn::getId));
        for (int i = 0; i < tableView.getColumns().size(); i++) {
            final int index = i;
            TableColumn<ObservableList<String>, String> column = (TableColumn<ObservableList<String>, String>) tableView.getColumns().get(i);
            column.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().get(index))
            );
        }

        //清空表格内容
        tableView.getItems().clear();
        List<List<String>> paramList = new ArrayList<>();
        checkboxGroup.forEach((k, v) -> {
            List<AttributeValueCheckBox> listBoxes = checkboxGroup.get(k);
            List<String> straits = listBoxes.stream().filter(e -> e.isSelected()).map(e -> e.attributeValue.getName()).collect(Collectors.toList());
            if (straits.size() > 0) {
                paramList.add(straits);
            }
        });
        String result = multiRound(paramList, "", 0);
        String[] array = result.split("\\$");
        for (String str : array) {
            ObservableList<String> rowData = FXCollections.observableArrayList();
            if (!str.equals("")) {
                String[] subArray = str.split("/");
                rowData.addAll(subArray);
            }
            rowData.add("0");
            rowData.add("0");
            rowData.add("");
            rowData.add("");
            rowData.add("");
            rowData.add("");
            tableView.getItems().add(rowData);
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
