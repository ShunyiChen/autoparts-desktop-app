package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.AttributeName;
import com.shunyi.autoparts.ui.model.Category;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import java.io.IOException;

public class AttributeEditorController {
    Stage dialog;
    Category selectedCategory;

    @FXML
    TreeView<Category> categoryTree;
    @FXML
    TreeView<AttributeName> attributeTree;

    public void prepare(Stage dialog, Category selectedCategory) {
        this.dialog = dialog;
        this.selectedCategory = selectedCategory;
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        categoryTree.getStylesheets().add(css);
        attributeTree.getStylesheets().add(css);
        initCategoryTree();
        initAttributesTree();
    }

    private void initCategoryTree() {
        TreeItem<Category> root = new TreeItem<>(new Category("全部类目",0L, true));
        initTreeNodes(root);
        categoryTree.setRoot(root);
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
        for(Category sc : all) {
            if(sc.getParentId() == parent.getValue().getId()) {
                TreeItem<Category> node = new TreeItem<>(sc);
                parent.getChildren().add(node);
                parent.setExpanded(true);
                getNodes(node, all);
            }
        }
    }

    private void initAttributesTree() {
        AttributeName rootName = new AttributeName();
        rootName.setName("全部属性");
        TreeItem<AttributeName> root = new TreeItem<>(rootName);
        try {
            String data = HttpClient.GET("/attributes/name/category/"+selectedCategory.getId());
            AttributeName[] attributeNames = GoogleJson.GET().fromJson(data, AttributeName[].class);
            for(AttributeName name : attributeNames) {
                TreeItem<AttributeName> item = new TreeItem<>(name);
                root.getChildren().add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        attributeTree.setRoot(root);
        ContextMenu menu = new ContextMenu();
        MenuItem itemNew = new MenuItem("新建属性名");
        MenuItem itemRM = new MenuItem("删除");
        itemNew.setOnAction(e -> {
            AttributeName newName = new AttributeName("未命名", selectedCategory, false, false, false, false, false, false, false, false, false, "可用", 1 );
            String json = GoogleJson.GET().toJson(newName);
            try {
                String idStr = HttpClient.POST("/attributes/name", json);
                newName.setId(Long.valueOf(idStr));
                TreeItem<AttributeName> item = new TreeItem<>(newName);
                root.getChildren().add(item);
                root.setExpanded(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        itemRM.setOnAction(e -> {
            attributeTree.edit(attributeTree.getSelectionModel().getSelectedItem());
        });
        menu.getItems().addAll(itemNew, itemRM);
        attributeTree.setEditable(false);
        attributeTree.setContextMenu(menu);
        attributeTree.setCellFactory(new Callback<TreeView<AttributeName>, TreeCell<AttributeName>>() {
            @Override
            public TreeCell<AttributeName> call(TreeView<AttributeName> p) {
                return new TextFieldTreeCell<AttributeName>(new StringConverter<AttributeName>(){

                    @Override
                    public String toString(AttributeName object) {
                        return object.getName();
                    }

                    @Override
                    public AttributeName fromString(String string) {
                        p.getEditingItem().getValue().setName(string);
                        return p.getEditingItem().getValue();
                    }
                });
            }
        });
        attributeTree.setOnEditCommit(new EventHandler<TreeView.EditEvent<AttributeName>>() {
            @Override
            public void handle(TreeView.EditEvent<AttributeName> event) {
                String path = "/attributes/name"+event.getNewValue().getId();
                String json = GoogleJson.GET().toJson(event.getNewValue());
                try {
                    HttpClient.PUT(path, json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
