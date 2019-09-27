package com.shunyi.autoparts.ui.login;

import com.shunyi.autoparts.ui.model.GatewayAddr;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class GatewayAddrEditorController {
    @FXML
    private TableView<GatewayAddr> tableView;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox cBoxProtocol;
    @FXML
    private TextField txtIPAddr;
    @FXML
    private TextField txtPort;
    @FXML
    private Button btnUp;
    @FXML
    private Button btnDown;
    @FXML
    private Button btnDefault;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    private Stage dialog;

    @FXML
    public void up(ActionEvent event) {
    }

    @FXML
    public void down(ActionEvent event) {
    }

    @FXML
    public void setDefault(ActionEvent event) {
    }

    @FXML
    public void add(ActionEvent event) {
    }

    @FXML
    public void remove(ActionEvent event) {
    }

    @FXML
    public void cancel(ActionEvent event) {
        dialog.close();
    }

    @FXML
    public void ok(ActionEvent event) {
        dialog.close();
    }

    public void initComponents(Stage dialog) {
        this.dialog = dialog;
        String[] v = {"http", "https"};
        cBoxProtocol.getItems().addAll(v);
        cBoxProtocol.setValue(v[0]);
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        final ObservableList<GatewayAddr> data = FXCollections.observableArrayList(
                new GatewayAddr("localhost", "http", "localhost", 8080, true),
                new GatewayAddr("remoted", "https", "123.20.1.70", 80, false));
        tableView.setItems(data);
        tableView.setEditable(true);
        //Set the table to multi selection mode
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TableColumn colName= new TableColumn("名称");
        colName.setPrefWidth(300);
        colName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn colProtocol = new TableColumn("协议");
        colProtocol.setPrefWidth(60);
        colProtocol.setCellValueFactory(new PropertyValueFactory("protocol"));

        TableColumn colHost = new TableColumn("地址");
        colHost.setPrefWidth(100);
        colHost.setCellValueFactory(new PropertyValueFactory("host"));

        TableColumn colPort = new TableColumn("端口");
        colPort.setPrefWidth(60);
        colPort.setCellValueFactory(new PropertyValueFactory("port"));
        StringConverter<Object> sc = new StringConverter<Object>() {
            @Override
            public String toString(Object t) {
                boolean bool = t == null ? false : Boolean.parseBoolean(t.toString());
                if(bool) {
                    return "☆";
                }
                return "☆";
            }

            @Override
            public Object fromString(String string) {
                return Boolean.valueOf(string.equals("☆"));
            }
        };

        TableColumn colDefault = new TableColumn("默认");
        colDefault.setCellValueFactory(new PropertyValueFactory("default2"));
        colDefault.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        colDefault.setMinWidth(100);
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        tableView.getStylesheets().add(css);
        tableView.getColumns().addAll(colName, colProtocol, colHost, colPort, colDefault);
        tableView.setEditable(false);
    }
}
