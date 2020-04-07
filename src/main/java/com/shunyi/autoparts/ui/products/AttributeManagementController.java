package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

/**
 * 配件属性UI
 */
public class AttributeManagementController {
    private Stage dialog;
    private Category selectedCategory;

    @FXML
    BorderPane pnlCenter;
    @FXML
    TreeView<Category> categoryTree;
    @FXML
    TreeView<AttributeIF> attributeTree;

    public void prepare(Stage dialog, Category selectedCategory) {
        this.dialog = dialog;
        this.selectedCategory = selectedCategory;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        categoryTree.getStylesheets().add(css);
        attributeTree.getStylesheets().add(css);
        initCategoryTree();
    }

    private void initCategoryTree() {
        TreeItem<Category> root = new TreeItem<>(new Category(0L, "所有分类",-1L, Constants.PARENT_TRUE, new Store()));
        initTreeNodes(root);
        categoryTree.setRoot(root);

        categoryTree.setCellFactory(p -> new TextFieldTreeCell<>(new StringConverter<>(){

            @Override
            public String toString(Category object) {
                return object.getName();
            }

            @Override
            public Category fromString(String string) {
                p.getEditingItem().getValue().setName(string);
                return p.getEditingItem().getValue();
            }
        }));
        categoryTree.setOnEditCommit(event -> {
            String path = "/categories/"+event.getNewValue().getId();
            String json = GoogleJson.GET().toJson(event.getNewValue());
            try {
                HttpClient.PUT(path, json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建类目");
        MenuItem itemRM = new MenuItem("删除类目");
        MenuItem itemRN = new MenuItem("重命名");

        itemNew.setStyle("-fx-font-size: 14px;");
        itemRM.setStyle("-fx-font-size: 14px;");
        itemRN.setStyle("-fx-font-size: 14px;");

        categoryTree.setEditable(true);
        categoryTree.setContextMenu(menu);
        itemNew.setOnAction(event -> newCategory());
        itemRM.setOnAction(event -> removeCategory());
        itemRN.setOnAction(event -> categoryTree.edit(categoryTree.getSelectionModel().getSelectedItem()));

        categoryTree.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                selectedCategory = categoryTree.getSelectionModel().getSelectedItem().getValue();
                initAttributesTree();
            }
            else if (event.getButton().equals(MouseButton.SECONDARY) && event.getClickCount() == 1) {
                Category selectedCategory = categoryTree.getSelectionModel().getSelectedItem().getValue();
                if(selectedCategory.getId() == 0L) {
                    menu.getItems().clear();
                    menu.getItems().addAll(itemNew);
                } else {
                    menu.getItems().clear();
                    menu.getItems().addAll(itemNew, itemRM, itemRN);
                }
            }
        });
    }

    private void newCategory() {
        TreeItem<Category> parent = categoryTree.getSelectionModel().getSelectedItem();
        if(parent == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个节点");
            alert.show();
            return;
        }
        Callback<Category, Object> callback = new Callback<>() {
            @Override
            public Object call(Category category) {
                if(category != null) {
                    parent.getValue().setParent(true);
                    String json = GoogleJson.GET().toJson(parent.getValue());
                    try {
                        HttpClient.PUT("/categories/"+parent.getValue().getId(),json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    category.setParentId(parent.getValue().getId());
                    category.setParent(false);
                    try {
                        json = GoogleJson.GET().toJson(category, Category.class);
                        String idStr = HttpClient.POST("/categories",json);
                        category.setId(Long.valueOf(idStr));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    TreeItem<Category> node = new TreeItem<>(category);
                    parent.getChildren().add(node);
                    // 选中新建的节点
                    categoryTree.getSelectionModel().select(node);
                }
                return null;
            }
        };
        openCategoryEditor(callback, null);
    }

    private void openCategoryEditor(Callback<Category, Object> callback, Category updatedCategory) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/category_editor.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        CategoryEditorController controller = loader.getController();
        controller.prepare(dialog, updatedCategory, callback);
        dialog.setTitle(updatedCategory != null?"更改类目":"新建类目");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void removeCategory() {
        TreeItem<Category> selected = categoryTree.getSelectionModel().getSelectedItem();
        if(selected == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择一个类目节点");
            alert.show();
            return;
        }
        else if(selected.getValue().getId() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("根节点不可删除");
            alert.show();
            return;
        }
        else if(selected.getValue().getParent()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("无法删除父节点");
            alert.show();
            return;
        }
        else {
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
            alertConfirm.setHeaderText("是否删除该类目？");
            alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
                try {
                    HttpClient.DELETE("/categories/"+selected.getValue().getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TreeItem<Category> parent = selected.getParent();
                if(parent != null) {
                    parent.getChildren().remove(selected);
                    parent.setExpanded(true);
                    categoryTree.getSelectionModel().select(parent);
                }
                //设置节点是否为父节点
                if(parent.isLeaf()) {
                    parent.getValue().setParent(false);
                    String json = GoogleJson.GET().toJson(parent.getValue());
                    try {
                        HttpClient.PUT("/categories/"+parent.getValue().getId(), json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void initTreeNodes(TreeItem<Category> root) {
        try {
            String path = "/categories/sorted/"+Env.getInstance().currentStore().getId();
            String data = HttpClient.GET(path);
            Category[] res = GoogleJson.GET().fromJson(data, Category[].class);
            getNodes(root, res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getNodes(TreeItem<Category> parent, Category[] all) {
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
        AttributeName rootName = new AttributeName(0L, "所有属性", selectedCategory, false, false, false, false, false, false, false, false, false, "可用", Sort.ASC.getSort());
        TreeItem<AttributeIF> root = new TreeItem<>(rootName);
        try {
            String data = HttpClient.GET("/attributes/name/category/"+selectedCategory.getId());
            AttributeName[] attributeNames = GoogleJson.GET().fromJson(data, AttributeName[].class);

            String data2 = HttpClient.GET("/attributes/value/category/"+selectedCategory.getId());
            AttributeValue[] attributeValues = GoogleJson.GET().fromJson(data2, AttributeValue[].class);
            for(AttributeIF name : attributeNames) {
                TreeItem<AttributeIF> nameItem = new TreeItem<>(name);
                root.getChildren().add(nameItem);
                for(AttributeValue value : attributeValues) {
                    if(value.getAttributeName().getId().equals(name.getId())) {
                        TreeItem<AttributeIF> valItem = new TreeItem<>(value);
                        nameItem.getChildren().add(valItem);
                    }
                }
                nameItem.setExpanded(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        attributeTree.setRoot(root);
        root.setExpanded(true);

        ContextMenu menu = new ContextMenu();
        MenuItem itemNewName = new MenuItem("新建属性名");
        MenuItem itemNewValue = new MenuItem("新建属性值");
        MenuItem itemRM = new MenuItem("删 除");
        MenuItem itemRN = new MenuItem("重命名");

        itemNewName.setStyle("-fx-font-size: 14px;");
        itemNewValue.setStyle("-fx-font-size: 14px;");
        itemRM.setStyle("-fx-font-size: 14px;");
        itemRN.setStyle("-fx-font-size: 14px;");

        itemNewName.setOnAction(e -> {
            if(selectedCategory.getId() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setHeaderText("无法在根类目下创建属性");
                alert.show();
                return;
            }
            AttributeName newName = new AttributeName(0L, "新建属性", selectedCategory, false, false, false, false, false, false, false, false, false, "可用", Sort.ASC.getSort());
            String json = GoogleJson.GET().toJson(newName);
            try {
                String idStr = HttpClient.POST("/attributes/name", json);
                //重新取值包括创建日期
                json = HttpClient.GET("/attributes/name/"+Long.valueOf(idStr));
                newName = GoogleJson.GET().fromJson(json, AttributeName.class);
                TreeItem<AttributeIF> item = new TreeItem<>(newName);
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
            TreeItem<AttributeIF> parent =  attributeTree.getSelectionModel().getSelectedItem();
            if(parent.getValue().getId() != 0L) {
                if(parent.getValue() instanceof AttributeName) {
                    AttributeValue newValue = new AttributeValue(0L, "新建属性值", "0,0,0", selectedCategory, (AttributeName)parent.getValue(), Status.AVAILABLE.getText(), Sort.ASC.getSort());
                    String json = GoogleJson.GET().toJson(newValue);
                    try {
                        String idStr = HttpClient.POST("/attributes/value", json);
                        newValue.setId(Long.valueOf(idStr));
                        //重新取值包括创建日期
                        json = HttpClient.GET("/attributes/value/"+Long.valueOf(idStr));
                        newValue = GoogleJson.GET().fromJson(json, AttributeValue.class);
                        TreeItem<AttributeIF> item = new TreeItem<>(newValue);
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
            TreeItem<AttributeIF> selected =  attributeTree.getSelectionModel().getSelectedItem();
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
        attributeTree.setEditable(true);
        attributeTree.setContextMenu(menu);
        attributeTree.setCellFactory(new Callback<TreeView<AttributeIF>, TreeCell<AttributeIF>>() {
            @Override
            public TreeCell<AttributeIF> call(TreeView<AttributeIF> p) {
                return new TextFieldTreeCell<AttributeIF>(new StringConverter<AttributeIF>(){

                    @Override
                    public String toString(AttributeIF object) {
                        return object.getName();
                    }

                    @Override
                    public AttributeIF fromString(String string) {
                        p.getEditingItem().getValue().setName(string);
                        return p.getEditingItem().getValue();
                    }
                });
            }
        });
        attributeTree.setOnEditCommit(new EventHandler<TreeView.EditEvent<AttributeIF>>() {
            @Override
            public void handle(TreeView.EditEvent<AttributeIF> event) {
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
                TreeItem<AttributeIF> selected = attributeTree.getSelectionModel().getSelectedItem();
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
                        showAttributeValueEditor((AttributeName) selected.getParent().getValue(), (AttributeValue)selected.getValue());
                    }
                }
            }
            else if (event.getButton().equals(MouseButton.SECONDARY) && event.getClickCount() == 1) {
                TreeItem<AttributeIF> selected = attributeTree.getSelectionModel().getSelectedItem();
                if(selected.getValue().getId() == 0L) {
                    menu.getItems().clear();
                    menu.getItems().addAll(itemNewName);
                } else if (selected.getValue() instanceof AttributeValue){
                    menu.getItems().clear();
                    menu.getItems().addAll(itemRM, itemRN);
                } else {
                    menu.getItems().clear();
                    menu.getItems().addAll(itemNewValue, new SeparatorMenuItem(), itemRM, itemRN);
                }
            }
        });
    }

    private void showAttributeNameEditor(AttributeName selectedAttributeName) {
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

    private void showAttributeValueEditor(AttributeName selectedAttributeName, AttributeValue selectedAttributeValue) {
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
        controller.prepare(selectedAttributeName, selectedAttributeValue);
        pnlCenter.setCenter(root);
    }
}
