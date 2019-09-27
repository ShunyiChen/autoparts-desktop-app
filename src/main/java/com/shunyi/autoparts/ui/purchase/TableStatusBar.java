package com.shunyi.autoparts.ui.purchase;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

public class TableStatusBar extends HBox {

    private TableView tableView;

    public TableStatusBar(TableView tableView) {
        this.tableView = tableView;
        initComponents();
    }

    private void initComponents() {

        ObservableList<TableColumn> columns = tableView.getColumns();
        columns.forEach(c -> {
            Label label = new Label(c.getText());
            label.setPrefWidth(c.getPrefWidth());
            this.getChildren().add(label);
        });
    }
}
