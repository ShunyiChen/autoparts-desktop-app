package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.*;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: 陈顺谊
 * @CreateDate: 2020/3/28 0:02
 * @Version: 1.0
 */
public class AttributeCombinerController {
    private Stage dialog;
    private Product product;
    private SKU updatedSKU = new SKU();
    private LinkedHashMap<Long, List<AttributeValueCheckBox>> checkboxGroup = new LinkedHashMap<>();
    private List<Long> selectedAttributeValueIds = new ArrayList<>();
    private Callback<SKU, String> callback;
    @FXML
    private VBox vbox;
    @FXML
    private TextField txtFieldNotes;
    @FXML
    private Button btnOK;

    public void prepare(Stage dialog, Product product, SKU updatedSKU, Callback<SKU, String> callback) {
        this.dialog = dialog;
        this.product = product;
        this.updatedSKU = updatedSKU;
        this.callback = callback;
        vbox.getChildren().clear();
        btnOK.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        init();
        initCheckBoxesGroup();
    }

    private void init() {
        if (updatedSKU.getProperties() != null) {
            if(updatedSKU.getProperties().contains(";")) {
                String[] kvs = updatedSKU.getProperties().split(";");
                for(String kv : kvs) {
                    String vid = kv.substring(kv.lastIndexOf(":") + 1);
                    selectedAttributeValueIds.add(Long.parseLong(vid));
                }
            }
        }
    }

    private void initCheckBoxesGroup() {
        String json = "";
        try {
            json = HttpClient.GET("/basic/attributes/products/"+product.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BasicAttributes[] basicAttributes = GoogleJson.GET().fromJson(json, BasicAttributes[].class);
        List<AttributeName> attributeNames = new ArrayList<>();
        for(BasicAttributes ba : basicAttributes) {
            attributeNames.add(ba.getAttributeValue().getAttributeName());
        }
        //去重后的属性名
        List<AttributeName> distinctList = attributeNames.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(o-> o.getId()))), ArrayList::new));
        for(AttributeName attributeName : distinctList) {
            checkboxGroup.put(attributeName.getId(), new ArrayList<>());
            vbox.getChildren().add(createCheckBoxFlowPane(attributeName));
        }
        txtFieldNotes.setText("ddd");
    }

    /**
     *
     * @param attributeName
     * @return
     */
    private FlowPane createCheckBoxFlowPane(AttributeName attributeName) {
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_LEFT);
        flowPane.setHgap(20);
        flowPane.setPrefSize(600, 40);
        Label label = new Label(attributeName.getName()+":");
        label.setFont(Font.font(14));
        flowPane.getChildren().add(label);
        String json = null;
        try {
            json = HttpClient.GET("/attributes/value/name/"+attributeName.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttributeValue[] attributeValues = GoogleJson.GET().fromJson(json, AttributeValue[].class);
        for(AttributeValue value : attributeValues) {
            AttributeValueCheckBox checkBox = new AttributeValueCheckBox(value, txtFieldNotes, checkboxGroup, updatedSKU);
            flowPane.getChildren().add(checkBox);
            checkboxGroup.get(attributeName.getId()).add(checkBox);
            //选中
            if(selectedAttributeValueIds.contains(value.getId())) {
                checkBox.setSelected(true);
            }
        }
        return flowPane;
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void ok() {
        dialog.close();
        updatedSKU.setSpecification(txtFieldNotes.getText());
        callback.call(updatedSKU);
    }
}
