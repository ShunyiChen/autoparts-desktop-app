package com.shunyi.autoparts.ui.products;

//import com.shunyi.autoparts.ui.common.vo.Attribute;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;

/** SKU */
public class SKUGeneratorRowController {
    @FXML
    Label titleLabel;
    @FXML
    FlowPane rootPanel;

//    public void prepare(Product selectedProduct,
//                        AttributeName attributeName,
//                        LinkedHashMap<Long, List<AttributeValueCheckBox>> checkboxGroup,
//                        TableColumn<ObservableList<TableCellMetadata>, String> tableColumn,
//                        TableView<ObservableList<TableCellMetadata>> tableView, Attribute[] attributes) {
//        titleLabel.setText(attributeName.getName()+":");
//        String json = null;
//        try {
//            json = HttpClient.GET("/attributes/value/name/"+attributeName.getId());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        List<AttributeValueCheckBox> buttonGroup = new ArrayList<>();
//        checkboxGroup.put(attributeName.getId(), buttonGroup);
//        AttributeValue[] attributeValues = GoogleJson.GET().fromJson(json, AttributeValue[].class);
//        AttributeValueCheckBox coloredCheckBox = null;
//        for(AttributeValue attributeValue : attributeValues) {
//            coloredCheckBox = new AttributeValueCheckBox(selectedProduct, attributeValue, checkboxGroup, tableColumn, tableView);
//            coloredCheckBox.setSelected(shouldBeSelected(attributeValue, attributes));
//            buttonGroup.add(coloredCheckBox);
//            rootPanel.getChildren().add(coloredCheckBox);
//        }
//
//        rootPanel.setUserData(buttonGroup);
//    }
//
//    private boolean shouldBeSelected(AttributeValue attributeValue, Attribute[] attributes) {
//        for(Attribute attribute : attributes) {
//            if(attributeValue.getId() == attribute.getAttributeValueId()) {
//                return true;
//            }
//        }
//        return false;
//    }
}
