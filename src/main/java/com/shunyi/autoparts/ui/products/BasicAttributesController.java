package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: 陈顺谊
 * @CreateDate: 2020/2/9 20:02
 * @Version: 1.0
 */
public class BasicAttributesController {

    private Stage dialog;
    private Category selectedCategory;
    private Product selectedProject;

    @FXML
    private Label labelTitle;
    @FXML
    private ListView<AttributeName> leftListView;
    @FXML
    private ListView<AttributeName> rightListView;

    @FXML
    private Button btnOk;

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void ok() {
        List<AttributeName> oldAttributeNames = getProductAttributeNames();

        //处理新增属性
        ObservableList<AttributeName> list = rightListView.getItems();
        for(AttributeName an : list) {
            if(!existCheck(an, oldAttributeNames)) {
                try {
                    AttributeValue[] attributeValues = HttpClient.GET("/attributes/value/name/"+an.getId(), AttributeValue[].class);
                    for(AttributeValue attributeValue : attributeValues) {
                        BasicAttributes newBasicAttributes = new BasicAttributes(selectedProject, an.getId(), attributeValue, false, 0L, null);
                        String json = GoogleJson.GET().toJson(newBasicAttributes);;
                        String idStr = HttpClient.POST("/basic/attributes", json);
                        newBasicAttributes.setId(Long.valueOf(idStr));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //处理移除属性
        for(AttributeName an : oldAttributeNames) {
            if(!existCheck(an, list)) {
                try {
                    HttpClient.DELETE("/basic/attributes/"+selectedProject.getId()+"/"+an.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        dialog.close();
    }

    public void prepare(Stage dialog, Category selectedCategory, Product selectedProject) {
        this.dialog = dialog;
        this.selectedCategory = selectedCategory;
        this.selectedProject = selectedProject;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        leftListView.getStylesheets().add(css);
        rightListView.getStylesheets().add(css);
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        labelTitle.setText("所属类目: "+selectedCategory.getName());

        initRightListView();

        initLeftListView();
    }


    private List<AttributeName> getProductAttributeNames() {
        String data;
        BasicAttributes[] basicAttributes = {};
        try {
            data = HttpClient.GET("/basic/attributes/products/"+selectedProject.getId());
            basicAttributes = GoogleJson.GET().fromJson(data, BasicAttributes[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<AttributeName> attributeNames = new ArrayList<>();
        for(BasicAttributes ba : basicAttributes) {
            attributeNames.add(ba.getAttributeValue().getAttributeName());
        }
        //去重
        List<AttributeName> distinctList = attributeNames.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(o-> o.getId()))), ArrayList::new));
        return distinctList;
    }


    private void initRightListView() {
        List<AttributeName> attributeNames = getProductAttributeNames();

        rightListView.getItems().addAll(attributeNames);
    }

    private void initLeftListView() {
        String data;
        try {
            data = HttpClient.GET("/attributes/name/category/"+selectedCategory.getId());
            AttributeName[] attributeNames = GoogleJson.GET().fromJson(data, AttributeName[].class);
            ObservableList<AttributeName> list = rightListView.getItems();
            for(AttributeName an : attributeNames) {
                if(!existCheck(an, list)) {
                    leftListView.getItems().add(an);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean existCheck(AttributeName attributeName, List<AttributeName> list) {
        for(AttributeName an : list) {
            if(an.getId() == attributeName.getId()) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void moveLeft() {
        AttributeName selectedAttributeName = rightListView.getSelectionModel().getSelectedItem();
        if(selectedAttributeName != null) {
            rightListView.getItems().remove(selectedAttributeName);
            add(leftListView, selectedAttributeName);
        }
    }

    @FXML
    private void moveRight() {
        AttributeName selectedAttributeName = leftListView.getSelectionModel().getSelectedItem();
        if(selectedAttributeName != null) {
            leftListView.getItems().remove(selectedAttributeName);
            add(rightListView, selectedAttributeName);
        }
    }

    @FXML
    private void moveAllToLeft() {
        addAll(rightListView, leftListView);
    }

    @FXML
    private void moveAllToRight() {
        addAll(leftListView, rightListView);
    }

    private void add(ListView<AttributeName> destListView, AttributeName attributeName) {
        ObservableList<AttributeName> list = destListView.getItems();
        int insertIndex = 0;
        for(int i=0; i < list.size(); i++) {
            if(list.get(i).getId() < attributeName.getId()) {
                insertIndex++;
            }
        }
        destListView.getItems().add(insertIndex, attributeName);
    }

    private void addAll(ListView<AttributeName> srcListView, ListView<AttributeName> destListView) {
        ObservableList<AttributeName> list = srcListView.getItems();
        list.forEach(e -> {
            add(destListView, e);
        });
        srcListView.getItems().clear();
    }
}
