package com.shunyi.spareparts.ui.supplier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

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
}
