package com.shunyi.autoparts.ui.purchase;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class TableStatusBar extends HBox {

    private TableView tableView;

    public TableStatusBar(TableView tableView) {
        this.tableView = tableView;
        initComponents();
    }

    private void initComponents() {

        updateDataPresentation();
    }

    private void updateDataPresentation() {
        Label labelTotal = new Label("合计: "+0);
        Label labelRows = new Label("记录数: "+100);
        Label labelCount = new Label("合计数量: "+500);
        Label labelAmountWithTax = new Label("不含税金额: "+3464.23);

        labelTotal.setFont(Font.font(14));
        labelRows.setFont(Font.font(14));
        labelCount.setFont(Font.font(14));
        labelAmountWithTax.setFont(Font.font(14));

        labelRows.setPrefWidth(200);
        labelTotal.setPrefWidth(200);
        labelCount.setPrefWidth(200);
        labelAmountWithTax.setPrefWidth(200);

        this.getChildren().addAll(labelTotal, labelRows, labelCount, labelAmountWithTax);

    }
}
