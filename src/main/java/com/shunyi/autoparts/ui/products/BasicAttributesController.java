package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.model.AttributeBase;
import com.shunyi.autoparts.ui.model.AttributeName;
import com.shunyi.autoparts.ui.model.AttributeValue;
import com.shunyi.autoparts.ui.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/**
 * @Description:
 * @Author: 陈顺谊
 * @CreateDate: 2020/2/9 20:02
 * @Version: 1.0
 */
public class BasicAttributesController {

    private Stage dialog;
    private Category selectedCategory;

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
        dialog.close();
    }

    public void prepare(Stage dialog, Category selectedCategory) {
        this.dialog = dialog;
        this.selectedCategory = selectedCategory;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        leftListView.getStylesheets().add(css);
        rightListView.getStylesheets().add(css);


        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        labelTitle.setText("所属类目: "+selectedCategory.getName());

        initLeftListView();

        initRightListView();
    }

    private void initLeftListView() {
        String data;
        try {
            data = HttpClient.GET("/attributes/name/category/"+selectedCategory.getId());
            AttributeName[] attributeNames = GoogleJson.GET().fromJson(data, AttributeName[].class);
            leftListView.getItems().addAll(attributeNames);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRightListView() {

    }


//    private void initTreeView() {
//        AttributeName rootName = new AttributeName();
//        rootName.setName("全部属性");
//        TreeItem<AttributeBase> root = new TreeItem<>(rootName);
//        try {
//            String data = HttpClient.GET("/attributes/name/category/"+selectedCategory.getId());
//            AttributeName[] attributeNames = GoogleJson.GET().fromJson(data, AttributeName[].class);
//
//            String data2 = HttpClient.GET("/attributes/value/category/"+selectedCategory.getId());
//            AttributeValue[] attributeValues = GoogleJson.GET().fromJson(data2, AttributeValue[].class);
//            for(AttributeBase name : attributeNames) {
//                TreeItem<AttributeBase> nameItem = new TreeItem<>(name);
//                root.getChildren().add(nameItem);
//                for(AttributeValue value : attributeValues) {
//                    if(value.getAttributeName().getId() == name.getId()) {
//                        TreeItem<AttributeBase> valItem = new TreeItem<>(value);
//                        nameItem.getChildren().add(valItem);
//                    }
//                }
//                nameItem.setExpanded(true);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        treeView.setRoot(root);
//        root.setExpanded(true);
//
//        treeView.setCellFactory(new Callback<TreeView<AttributeBase>, TreeCell<AttributeBase>>() {
//
//            @Override
//            public TreeCell<AttributeBase> call(TreeView<AttributeBase> param) {
//
//                return new CheckBoxTreeCell<AttributeBase>();
//            }
//        });
//
//        ContextMenu menu = new ContextMenu();
//        MenuItem itemNewName = new MenuItem("新建属性名");
//        MenuItem itemNewValue = new MenuItem("新建属性值");
//        MenuItem itemRM = new MenuItem("删  除");
//        MenuItem itemRN = new MenuItem("重命名");
//
//        itemNewName.setStyle("-fx-font-size: 14px;");
//        itemNewValue.setStyle("-fx-font-size: 14px;");
//        itemRM.setStyle("-fx-font-size: 14px;");
//        itemRN.setStyle("-fx-font-size: 14px;");
//    }


    @FXML
    private void moveLeft() {

    }

    @FXML
    private void moveRight() {

    }

    @FXML
    private void moveAllToLeft() {

    }

    @FXML
    private void moveAllToRight() {

    }

}
