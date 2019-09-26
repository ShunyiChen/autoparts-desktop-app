package com.shunyi.spareparts.ui.supplier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChooserController {

    @FXML
    TreeView tree;

    @FXML
    TableView table;

    @FXML
    TextField codeField;

    @FXML
    TextField contactsField;

    @FXML
    TextField phoneField;

    @FXML
    TextField nameField;

    @FXML
    TextField otherField;

    @FXML
    Button searchButton;

    @FXML
    Button clearButton;

    @FXML
    public void cleanAllFields(ActionEvent event) {

    }

    @FXML
    public void search(ActionEvent event) {

    }

    public void initTree() {
        TreeItem<String> root = new TreeItem<String>("Root Node");
        root.setExpanded(true);
        root.getChildren().addAll(
                new TreeItem<String>("Item 1"),
                new TreeItem<String>("Item 2"),
                new TreeItem<String>("Item 3")
        );
//        TreeView<String> treeView = new TreeView<String>(root);
        tree.setRoot(root);
    }

}
