package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.EditingCell;
import com.shunyi.autoparts.ui.main.BaseContainer;
import com.shunyi.autoparts.ui.model.AutoPart;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;

public class PurchaseOrder extends BorderPane implements BaseContainer {
    private MainApp application;
    private Button btnNew = new Button("新建");
    private Button btnSave = new Button("保存");
    private Button btnDelete = new Button("作废");
    private Button btnSubmit = new Button("结算");
    private Button btnAddAutoPart = new Button("添加配件");
    private Button btnPrint = new Button("打印");
    private Button btnSettings = new Button("设置");
    private Button btnClose = new Button("关闭");
    private TableView<AutoPart> table;
    private TableColumn codeCol = new TableColumn("配件编码");
    private TableColumn nameCol = new TableColumn("名称");
    private ObservableList<AutoPart> copiedDataList = FXCollections.observableList(new ArrayList<>());
    private Map<String, Boolean> compareResults = new HashMap<>();
    private BorderPane formPane;


    /**
     * Constructor
     *
     * @param application
     */
    public PurchaseOrder(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        btnSave.setDisable(true);
        btnNew.setFont(Font.font(16));
        btnSave.setFont(Font.font(16));
        btnDelete.setFont(Font.font(16));
        btnSubmit.setFont(Font.font(16));
        btnAddAutoPart.setFont(Font.font(16));
        btnPrint.setFont(Font.font(16));
        btnSettings.setFont(Font.font(16));
        btnClose.setFont(Font.font(16));
        ToolBar toolBar = new ToolBar();
        toolBar.getItems().addAll(btnNew, new Separator(), btnSave, btnDelete, btnSubmit, btnAddAutoPart, new Separator(), btnPrint, btnSettings, new Separator(), btnClose);
        this.setTop(toolBar);
        initEvents();
    }

    private BorderPane form() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/input_form.fxml"
                )
        );
        GridPane inputForm = null;
        try {
            inputForm = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputFormController controller = loader.getController();
        controller.setApplication(application);
        inputForm.setPadding(new Insets(10));

        VBox vLayout = new VBox();
        table = dataTable();
        TableStatusBar statusBar = new TableStatusBar(table);
        vLayout.getChildren().addAll(table, statusBar);


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


        FXMLLoader loader2 = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/footer.fxml"
                )
        );
        HBox footer = null;
        try {
            footer = loader2.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FlowPane bottomPane = new FlowPane();
        bottomPane.getChildren().add(footer);
        bottomPane.setAlignment(Pos.CENTER_RIGHT);
        BorderPane mainForm = new BorderPane();
        mainForm.setTop(inputForm);
        mainForm.setLeft(vLayout);
        mainForm.setBottom(bottomPane);
        table.prefWidthProperty().bind(application.getStage().widthProperty().subtract(application.getDashboard().getNavigation().widthProperty().add(35)));
        table.prefHeightProperty().bind(application.getStage().heightProperty().subtract(350));
        return mainForm;
    }

    private void initEvents() {
        btnNew.setOnAction(e -> {
            newOrder();
        });
        btnSave.setOnAction(e -> {
            save();
        });
        btnDelete.setOnAction(e -> {
            delete();
        });
        btnSubmit.setOnAction(e -> {
            submit();
        });
        btnPrint.setOnAction(e -> {
            print();
        });
        btnSettings.setOnAction(e -> {
            settings();
        });
        btnClose.setOnAction(e -> {
            close();
        });
    }

    private Callback<AutoPart, Void> comparator(){
        return new Callback<AutoPart, Void>() {

            @Override
            public Void call(AutoPart updated) {
                for(AutoPart sparepart : copiedDataList) {
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

    private void newOrder() {
        formPane = form();
        setCenter(formPane);
    }

    private void newItem() {
        AutoPart newObj = new AutoPart("","","",0,0D,0D,"",0,"","","",0D, "");
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

    private void delete() {
        table.getItems().clear();
        table.getSelectionModel().clearSelection();
        btnSave.setDisable(true);
        close();
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

    private void print() {

    }

    private void settings() {

    }

    private void close() {
        if(formPane != null)
            this.getChildren().remove(formPane);
    }

    private void initOldDataList() {
        compareResults.clear();
        copiedDataList.clear();
        ObservableList<AutoPart> items = table.getItems();
        Iterator<AutoPart> iter = items.iterator();
        while(iter.hasNext()) {
            AutoPart original = iter.next();
            AutoPart copied = new AutoPart(original.getCode(),original.getName(),original.getUnit(),original.getCount(),original.getPriceExcludingTax(),original.getAmountExcludingTax(),original.getWarehouse(),original.getNum(),original.getNotes(),original.getModel(),original.getBrand(),original.getCapitationPrice(),original.getPosition());
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
        TableView<AutoPart> root = null;
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
            new PropertyValueFactory<AutoPart,String>("code")
        );
        codeCol.setCellFactory(cellFactory);
        codeCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AutoPart, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<AutoPart, String> t) {
                    ObservableList<AutoPart> data = t.getTableView().getItems();
                    if(data != null) {
                        if(t.getTablePosition().getRow() < data.size()) {
                            AutoPart selected = data.get( t.getTablePosition().getRow());
                            if(selected != null) {
                                selected.setCode(t.getNewValue());
                            }
                        }
                    }
                }
            }
        );

        nameCol.setCellValueFactory(
            new PropertyValueFactory<AutoPart,String>("name")
        );
        nameCol.setCellFactory(cellFactory);
        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AutoPart, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<AutoPart, String> t) {
                    ((AutoPart) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                }
            }
        );
        root.getColumns().addAll(codeCol, nameCol);

        //Set the table to multi selection mode
        root.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Detect row added or deleted
        ObservableList<AutoPart> myObservableList =  root.getItems();
        myObservableList.addListener(new ListChangeListener<AutoPart>(){
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends AutoPart> pChange) {
                while(pChange.next()) {
                    // Do your changes here
                    btnSave.setDisable(false);
                }
            }
        });
        return root;
    }

    @Override
    public void willOpen() {

    }

    @Override
    public void willClose() {

    }
}
