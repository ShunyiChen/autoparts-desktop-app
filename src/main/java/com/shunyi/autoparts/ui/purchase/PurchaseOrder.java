package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.EditingCell;
import com.shunyi.autoparts.ui.common.MD5;
import com.shunyi.autoparts.ui.login.GatewayAddrEditorController;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private TableColumn colCode = new TableColumn("配件编码");
    private TableColumn colName = new TableColumn("名称");
    private TableColumn colUnit = new TableColumn("单位");
    private TableColumn colCount = new TableColumn("数量");
    private TableColumn colPriceWithoutTax = new TableColumn("不含税单价");
    private TableColumn colAmountWithoutTax = new TableColumn("不含税金额");
    private TableColumn colWarehouse = new TableColumn("仓库");
    private TableColumn colAmountOfStocks = new TableColumn("本库现库存数");
    private TableColumn colNotes = new TableColumn("备注");
    private TableColumn colModel = new TableColumn("车型");
    private TableColumn colBrand = new TableColumn("品牌");
    private TableColumn colShareEqually = new TableColumn("均摊单价");
    private TableColumn colSlot = new TableColumn("货位");

    private BorderPane formPane;
    private String oldHashText;

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

    private void newOrder() {
        formPane = form();
        setCenter(formPane);
    }

    private void newItem() {
        AutoPart newObj = new AutoPart("","","",0,0D,0D,"",0,"","","",0D, "");
        newObj.setCompareId(System.currentTimeMillis()+"");
        table.getItems().add(0, newObj);
        // Start editing
        table.edit(0, colCode);
        // Select row
        TableView.TableViewSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.clearSelection();
        selectionModel.select(0);
    }

    private void save() {
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
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/printing.fxml"
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
        PrintingController controller = loader.getController();
        controller.initComponents(dialog);
        dialog.setTitle("选择打印机和模板");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
//        dialog.show();
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    private void settings() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/settings.fxml"
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
        SettingsController controller = loader.getController();
        controller.initComponents(dialog);
        dialog.setTitle("自定义显示格式");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
//        dialog.show();
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    private void close() {
        if(formPane != null)
            this.getChildren().remove(formPane);
    }

    private String generateMD5Str() {
        String tableContentStr = getTableContentStr();
        String hashText = MD5.getMD5(tableContentStr);
        return hashText;
    }

    private String getTableContentStr() {
        return "";
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
        colCode.setCellValueFactory(
            new PropertyValueFactory<AutoPart,String>("code")
        );
        colCode.setCellFactory(cellFactory);
        colCode.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AutoPart, String>>() {
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

        colName.setCellValueFactory(
            new PropertyValueFactory<AutoPart,String>("name")
        );
        colName.setCellFactory(cellFactory);
        colName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<AutoPart, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<AutoPart, String> t) {
                    ((AutoPart) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                }
            }
        );

        colName.setPrefWidth(200);
        root.getColumns().addAll(colCode, colName, colUnit, colCount, colPriceWithoutTax, colAmountWithoutTax, colWarehouse, colAmountOfStocks, colNotes, colModel, colBrand, colShareEqually, colSlot);

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
