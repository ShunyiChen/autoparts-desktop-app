package com.shunyi.spareparts.ui.purchase;

import com.shunyi.spareparts.ui.MainApp;
import com.shunyi.spareparts.ui.common.EditingCell;
import com.shunyi.spareparts.ui.purchase.model.Sparepart;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;

public class PurchaseOrderForm extends BorderPane {

    private MainApp application;

    private Button btnNew = new Button("新建");
    private Button btnSave = new Button("保存");
    private Button btnDelete = new Button("作废");
    private Button btnSubmit = new Button("结算");

    private TableView<Sparepart> table;

    private TableColumn codeCol = new TableColumn("配件编码");
    private TableColumn nameCol = new TableColumn("名称");

    /** copied data of table  */
    private ObservableList<Sparepart> copiedDataList = FXCollections.observableList(new ArrayList<>());
    /** Compared results */
    private Map<String, Boolean> compareResults = new HashMap<>();

    /**
     * Constructor
     *
     * @param application
     */
    public PurchaseOrderForm(MainApp application) {
        this.application = application;
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
                        "/fxml/purchase/input_form.fxml"
                )
        );
        GridPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputFormController controller = loader.getController();
        controller.setApplication(application);

        root.setPadding(new Insets(10));

        table = dataTable();

        ContextMenu cm = new ContextMenu();
        MenuItem mi1 = new MenuItem("添 加");

        mi1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newItem();
            }
        });

        cm.getItems().add(mi1);
        MenuItem mi2 = new MenuItem("删 除");
        mi2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteSelectedRows();
            }
        });

        cm.getItems().add(mi2);
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if(t.getButton() == MouseButton.SECONDARY) {
                    cm.show(table, t.getScreenX(), t.getScreenY());
                }
            }
        });

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
            cancel();
        });
        btnSubmit.setOnAction(e -> {
            submit();
        });
    }

    private Callback<Sparepart, Void> comparator(){
        return new Callback<Sparepart, Void>() {

            @Override
            public Void call(Sparepart updated) {
                for(Sparepart sparepart : copiedDataList) {
                    if(updated.getCompareId().equals(sparepart.getCompareId())) {
                        compareResults.put(updated.getCompareId(), updated.equals(sparepart));
                        break;
                    }
                }
                if(compareResults.containsKey(updated.getCompareId())) {
                    btnSave.setDisable(!compareResults.containsValue(false));
                } else {
                    btnSave.setDisable(false);
                }
                return null;
            }
        };
    }

    private void newItem() {
        Sparepart newObj = new Sparepart("","","",0,0D,0D,"",0,"","","",0D, "");
        newObj.setComparator(comparator());
        newObj.setCompareId(System.currentTimeMillis()+"");
        table.getItems().add(0, newObj);
        // Start editing
        table.edit(0, codeCol);
        // Select row
        TableView.TableViewSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.clearSelection();
        selectionModel.select(0);

    }

    private void save() {
        initOldDataList();

        btnSave.setDisable(true);
    }

    private void cancel() {
        table.getItems().clear();
        table.getSelectionModel().clearSelection();
        btnSave.setDisable(true);
    }

    private void deleteSelectedRows() {
        List items =  new ArrayList(table.getSelectionModel().getSelectedItems());
        table.getItems().removeAll(items);
        table.getSelectionModel().clearSelection();
    }

    private void submit() {
        table.getItems().clear();
        btnSave.setDisable(true);
    }

    private void initOldDataList() {
        compareResults.clear();
        copiedDataList.clear();
        ObservableList<Sparepart> items = table.getItems();
        Iterator<Sparepart> iter = items.iterator();
        while(iter.hasNext()) {
            Sparepart original = iter.next();
            Sparepart copied = new Sparepart(original.getCode(),original.getName(),original.getUnit(),original.getCount(),original.getPriceExcludingTax(),original.getAmountExcludingTax(),original.getWarehouse(),original.getNum(),original.getNotes(),original.getModel(),original.getBrand(),original.getCapitationPrice(),original.getPosition());
            copied.setCompareId(original.getCompareId());
            copiedDataList.add(copied);
            //Set true as default for each sparepart, true means no changes
            compareResults.put(original.getCompareId(), true);
        }
    }

    private TableView dataTable() {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(
                "/fxml/purchase/data_table.fxml"
            )
        );
        TableView<Sparepart> root = null;
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
            new PropertyValueFactory<Sparepart,String>("code")
        );
        codeCol.setCellFactory(cellFactory);
        codeCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Sparepart, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Sparepart, String> t) {
                    ObservableList<Sparepart> data = t.getTableView().getItems();
                    if(data != null) {
                        if(t.getTablePosition().getRow() < data.size()) {
                            Sparepart selected = data.get( t.getTablePosition().getRow());
                            if(selected != null) {
                                selected.setCode(t.getNewValue());
                            }
                        }
                    }
                }
            }
        );

        nameCol.setCellValueFactory(
            new PropertyValueFactory<Sparepart,String>("name")
        );
        nameCol.setCellFactory(cellFactory);
        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Sparepart, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Sparepart, String> t) {
                    ((Sparepart) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                }
            }
        );
        root.getColumns().addAll(codeCol, nameCol);

        //Set the table to multi selection mode
        root.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Detect row added or deleted
        ObservableList<Sparepart> myObservableList =  root.getItems();
        myObservableList.addListener(new ListChangeListener<Sparepart>(){
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Sparepart> pChange) {
                while(pChange.next()) {
                    // Do your changes here
                    btnSave.setDisable(false);
                }
            }
        });
        return root;
    }
}
