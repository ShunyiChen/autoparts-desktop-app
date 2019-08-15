package com.shunyi.spareparts.ui.purchase;

import com.shunyi.spareparts.ui.common.EditingCell;
import com.shunyi.spareparts.ui.purchase.model.Order;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderForm extends BorderPane {
    private Button btnNew = new Button("新建");
    private Button btnSave = new Button("保存");
    private Button btnDelete = new Button("作废");
    private Button btnSubmit = new Button("结算");

    private TableView<Order> table;

    private TableColumn codeCol = new TableColumn("配件编码");
    private TableColumn nameCol = new TableColumn("名称");


    public PurchaseOrderForm() {
        initComponents();
    }

    private void initComponents() {
        ToolBar toolBar = new ToolBar();
        btnSave.setDisable(true);
        btnNew.setFont(Font.font(16));
        btnSave.setFont(Font.font(16));
        btnDelete.setFont(Font.font(16));
        btnSubmit.setFont(Font.font(16));

        toolBar.getItems().addAll(btnNew, new Separator(),btnSave, btnDelete, btnSubmit, new Separator());
        this.setTop(toolBar);
        this.setCenter(form());

        initActions();
    }

    private BorderPane form() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase_input_form.fxml"
                )
        );
        GridPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.setPadding(new Insets(10));

        table = dataTable();

        BorderPane mainForm = new BorderPane();
        mainForm.setTop(root);
        mainForm.setCenter(table);


        table.prefWidthProperty().bind(mainForm.widthProperty());
        table.prefHeightProperty().bind(mainForm.heightProperty().subtract(90));
        return mainForm;
    }

    private void initActions() {
        btnNew.setOnAction(e -> {
            newItem();
        });
        btnSave.setOnAction(e -> {
            save();
        });
        btnDelete.setOnAction(e -> {
            deleteRows();
        });
        btnSubmit.setOnAction(e -> {
            submit();
        });
    }

    private Callback<Boolean, Void> updateEvent(){
        return new Callback<Boolean, Void>() {
            @Override
            public Void call(Boolean updated) {
                btnSave.setDisable(!updated);
                return null;
            }
        };
    }

    private void newItem() {
        Order newOrder = new Order("","","",0,0D,0D,"",0,"","","",0D, "");
        newOrder.setUpdateEvent(updateEvent());
        table.getItems().add(0, newOrder);
        // Start editing
        table.edit(0, codeCol);
        // Select row
        TableView.TableViewSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.clearSelection();
        selectionModel.select(0);
    }

    private void save() {
        btnSave.setDisable(true);
    }

    private void deleteRows() {
        List items =  new ArrayList(table.getSelectionModel().getSelectedItems());
        table.getItems().removeAll(items);
        table.getSelectionModel().clearSelection();
    }

    private void submit() {
        table.getItems().clear();
        btnSave.setDisable(true);
    }

    private TableView dataTable() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase_data_table.fxml"
                )
        );
        TableView<Order> root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        codeCol.setCellValueFactory(
                new PropertyValueFactory<Order,String>("code")
        );
        codeCol.setCellFactory(cellFactory);
        codeCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Order, String> t) {
                    ObservableList<Order> data = t.getTableView().getItems();
                    if(data != null) {
                        if(t.getTablePosition().getRow() < data.size()) {
                            Order selected = data.get( t.getTablePosition().getRow());
                            if(selected != null) {
                                selected.setCode(t.getNewValue());
                            }
                        }
                    }
                }
            }
        );

        nameCol.setCellValueFactory(
                new PropertyValueFactory<Order,String>("name")
        );
        nameCol.setCellFactory(cellFactory);
        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Order, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Order, String> t) {
                    ((Order) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                }
            }
        );
        root.getColumns().addAll(codeCol, nameCol);

        //Set the table to multi selection mode
        root.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ObservableList<Order> myObservableList =  root.getItems();
        myObservableList.addListener(new ListChangeListener<Order>(){
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Order> pChange) {
                while(pChange.next()) {
                    // Do your changes here
                    btnSave.setDisable(false);
                }
            }
        });
        return root;
    }
}
