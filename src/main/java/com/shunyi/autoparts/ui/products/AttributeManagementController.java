package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.AttributeBase;
import com.shunyi.autoparts.ui.model.AttributeName;
import com.shunyi.autoparts.ui.model.AttributeValue;
import com.shunyi.autoparts.ui.model.Category;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

public class AttributeManagementController {
    Stage dialog;
    Category selectedCategory;

    @FXML
    BorderPane pnlCenter;
    @FXML
    TreeView<Category> categoryTree;
    @FXML
    TreeView<AttributeBase> attributeTree;

    public void prepare(Stage dialog, Category selectedCategory) {
        this.dialog = dialog;
        this.selectedCategory = selectedCategory;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        categoryTree.getStylesheets().add(css);
        attributeTree.getStylesheets().add(css);
        initCategoryTree();


    }

    private void initCategoryTree() {
        TreeItem<Category> root = new TreeItem<>(new Category("全部类目",0L, true));
        initTreeNodes(root);
        categoryTree.setRoot(root);
        categoryTree.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                selectedCategory = categoryTree.getSelectionModel().getSelectedItem().getValue();
                initAttributesTree();
            }
        });
    }

    void initTreeNodes(TreeItem<Category> root) {
        try {
            String path = "/categories/sorted";
            String data = HttpClient.GET(path);
            Category[] res = GoogleJson.GET().fromJson(data, Category[].class);
            getNodes(root, res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getNodes(TreeItem<Category> parent, Category[] all) {
        for(Category category : all) {
            if(category.getParentId() == parent.getValue().getId()) {
                TreeItem<Category> node = new TreeItem<>(category);
                parent.getChildren().add(node);
                parent.setExpanded(true);
                getNodes(node, all);
                //默认选中
                if(selectedCategory != null && selectedCategory.getId() == category.getId()) {
                    Platform.runLater(() -> {
                        // This line is the not-so-clearly documented magic.
                        int row = categoryTree.getRow(node);
                        // Now the row can be selected.
                        categoryTree.getSelectionModel().select(row);

                        initAttributesTree();
                    });
                }
            }
        }
    }

    private void initAttributesTree() {
        AttributeName rootName = new AttributeName();
        rootName.setName("全部属性");
        TreeItem<AttributeBase> root = new TreeItem<>(rootName);
        try {
            String data = HttpClient.GET("/attributes/name/category/"+selectedCategory.getId());
            AttributeName[] attributeNames = GoogleJson.GET().fromJson(data, AttributeName[].class);

            String data2 = HttpClient.GET("/attributes/value/category/"+selectedCategory.getId());
            AttributeValue[] attributeValues = GoogleJson.GET().fromJson(data2, AttributeValue[].class);
            for(AttributeBase name : attributeNames) {
                TreeItem<AttributeBase> nameItem = new TreeItem<>(name);
                root.getChildren().add(nameItem);
                for(AttributeValue value : attributeValues) {
                    if(value.getAttributeName().getId() == name.getId()) {
                        TreeItem<AttributeBase> valItem = new TreeItem<>(value);
                        nameItem.getChildren().add(valItem);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        attributeTree.setRoot(root);
        root.setExpanded(true);
        ContextMenu menu = new ContextMenu();
        MenuItem itemNewName = new MenuItem("新建属性名");
        MenuItem itemNewValue = new MenuItem("新建属性值");
        MenuItem itemRM = new MenuItem("删除");
        MenuItem itemRN = new MenuItem("重命名");
        itemNewName.setOnAction(e -> {
            if(selectedCategory.getId() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setHeaderText("无法在根类目下创建属性");
                alert.show();
                return;
            }
            AttributeName newName = new AttributeName("未命名的属性", selectedCategory, false, false, false, false, false, false, false, false, false, "可用", 1 );
            String json = GoogleJson.GET().toJson(newName);
            try {
                String idStr = HttpClient.POST("/attributes/name", json);
                //重新取值包括创建日期
                json = HttpClient.GET("/attributes/name/"+Long.valueOf(idStr));
                newName = GoogleJson.GET().fromJson(json, AttributeName.class);
                TreeItem<AttributeBase> item = new TreeItem<>(newName);
                root.getChildren().add(item);
                root.setExpanded(true);
                Platform.runLater(() -> {
                    attributeTree.getSelectionModel().select(item);
                    attributeTree.edit(item);
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        itemNewValue.setOnAction(e -> {
            TreeItem<AttributeBase> parent =  attributeTree.getSelectionModel().getSelectedItem();
            if(parent.getValue().getId() != 0L) {
                if(parent.getValue() instanceof AttributeName) {
                    AttributeBase newValue = new AttributeValue("未命名的属性值", "0,0,0", selectedCategory, (AttributeName)parent.getValue(),AttributeStatus.AVAILABLE.getName(), 1);
                    String json = GoogleJson.GET().toJson(newValue);
                    try {
                        String idStr = HttpClient.POST("/attributes/value", json);
                        newValue.setId(Long.valueOf(idStr));
                        //重新取值包括创建日期
                        json = HttpClient.GET("/attributes/value/"+Long.valueOf(idStr));
                        newValue = GoogleJson.GET().fromJson(json, AttributeValue.class);
                        TreeItem<AttributeBase> item = new TreeItem<>(newValue);
                        parent.getChildren().add(item);
                        parent.setExpanded(true);
                        Platform.runLater(() -> {
                            attributeTree.getSelectionModel().select(item);
                            attributeTree.edit(item);
                        });
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        itemRM.setOnAction(e -> {
            TreeItem<AttributeBase> selected =  attributeTree.getSelectionModel().getSelectedItem();
            if(selected.getValue().getId() != 0L) {
                if(selected.getValue() instanceof  AttributeName) {
                    Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
                    alertConfirm.setHeaderText("是否删除该属性？");
                    alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
                        try {
                            HttpClient.DELETE("/attributes/name/children/"+selected.getValue().getId());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        root.getChildren().remove(selected);
                    });
                } else if(selected.getValue() instanceof  AttributeValue) {
                    Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
                    alertConfirm.setHeaderText("是否删除该属性值？");
                    alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
                        try {
                            HttpClient.DELETE("/attributes/value/"+selected.getValue().getId());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        selected.getParent().getChildren().remove(selected);
                    });
                }
            }
        });
        itemRN.setOnAction(e -> {
            attributeTree.edit(attributeTree.getSelectionModel().getSelectedItem());
        });
        menu.getItems().addAll(itemNewName, itemNewValue, itemRM, new SeparatorMenuItem(), itemRN);
        attributeTree.setEditable(true);
        attributeTree.setContextMenu(menu);
        attributeTree.setCellFactory(new Callback<TreeView<AttributeBase>, TreeCell<AttributeBase>>() {
            @Override
            public TreeCell<AttributeBase> call(TreeView<AttributeBase> p) {
                return new TextFieldTreeCell<AttributeBase>(new StringConverter<AttributeBase>(){

                    @Override
                    public String toString(AttributeBase object) {
                        return object.getName();
                    }

                    @Override
                    public AttributeBase fromString(String string) {
                        p.getEditingItem().getValue().setName(string);
                        return p.getEditingItem().getValue();
                    }
                });
            }
        });
        attributeTree.setOnEditCommit(new EventHandler<TreeView.EditEvent<AttributeBase>>() {
            @Override
            public void handle(TreeView.EditEvent<AttributeBase> event) {
                if(event.getNewValue().getId() != 0L) {
                    if(event.getNewValue() instanceof AttributeName) {
                        String path = "/attributes/name/"+event.getNewValue().getId();
                        String json = GoogleJson.GET().toJson(event.getNewValue());
                        try {
                            HttpClient.PUT(path, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (event.getNewValue() instanceof AttributeValue) {
                        String path = "/attributes/value/"+event.getNewValue().getId();
                        String json = GoogleJson.GET().toJson(event.getNewValue());
                        try {
                            HttpClient.PUT(path, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        attributeTree.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                TreeItem<AttributeBase> selected = attributeTree.getSelectionModel().getSelectedItem();
                if(selected != null) {
                    if(selected.getValue() instanceof AttributeName) {
                        if(selected == root) {
                            // 显示空白
                            pnlCenter.setCenter(new Pane());
                        } else {
                            showAttributeNameEditor((AttributeName)selected.getValue());
                        }
                    }
                    else if(selected.getValue() instanceof AttributeValue) {
                        showAttributeValueEditor((AttributeValue)selected.getValue());
                    }
                }
            }
        });
    }

    void showAttributeNameEditor(AttributeName selectedAttributeName) {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(
                    "/fxml/products/attributes_name_editor.fxml"
            )
        );
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttributeNameEditorController controller = loader.getController();
        controller.prepare(selectedAttributeName);
        pnlCenter.setCenter(root);
    }

    void showAttributeValueEditor(AttributeValue selectedAttributeValue) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/attributes_value_editor.fxml"
                )
        );
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttributeValueEditorController controller = loader.getController();
        controller.prepare(selectedAttributeValue);
        pnlCenter.setCenter(root);
    }
}
