package com.shunyi.autoparts.ui.login;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.h2.H2;
import com.shunyi.autoparts.ui.model.RemoteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.util.List;

public class RemoteConnectionEditorController {
    @FXML
    private TableView<RemoteConnection> tableView;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> cBoxProtocol;
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
    private Button btnReset;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    private Stage dialog;
    private Gson gson = new Gson();

    @FXML
    public void up(ActionEvent event) {
        RemoteConnection first = tableView.getSelectionModel().getSelectedItem();
        int index = tableView.getSelectionModel().getSelectedIndex();
        RemoteConnection previous = tableView.getItems().get(index-1);
        tableView.getItems().remove(first);
        tableView.getItems().add(index - 1, first);
        tableView.getSelectionModel().select(first);
        first.setaOrder(index - 1);
        previous.setaOrder(index);
        changeButtons(first);
    }

    @FXML
    public void down(ActionEvent event) {
        RemoteConnection first = tableView.getSelectionModel().getSelectedItem();
        int index = tableView.getSelectionModel().getSelectedIndex();
        RemoteConnection next = tableView.getItems().get(index + 1);
        tableView.getItems().remove(first);
        tableView.getItems().add(index + 1, first);
        tableView.getSelectionModel().select(first);
        first.setaOrder(index + 1);
        next.setaOrder(index);
        changeButtons(first);
    }

    @FXML
    public void setDefault(ActionEvent event) {
        ObservableList<RemoteConnection> data = tableView.getItems();
        data.stream().forEach(e -> {
            e.set_default(false);
        });
        RemoteConnection selected = tableView.getSelectionModel().getSelectedItem();
        selected.set_default(true);
        changeButtons(selected);
    }

    @FXML
    public void add(ActionEvent event) {
        String name = txtName.getText()+"("+cBoxProtocol.getValue()+"://"+txtIPAddr.getText()+":"+txtPort.getText()+")";
        RemoteConnection rc = new RemoteConnection(0L, name, cBoxProtocol.getValue(), txtIPAddr.getText(), txtPort.getText(), false, tableView.getItems().size());
        tableView.getItems().add(rc);
        RemoteConnection selected = tableView.getSelectionModel().getSelectedItem();
        tableView.getSelectionModel().select(selected);
    }

    @FXML
    public void remove(ActionEvent event) {
        RemoteConnection selected = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selected);
        selected = tableView.getSelectionModel().getSelectedItem();
        changeButtons(selected);
    }

    @FXML
    public void reset(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"", ButtonType.YES, ButtonType.NO);
        alert.setTitle("重置确认");
        alert.setHeaderText("确定要重置?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            tableView.getItems().clear();
            RemoteConnection rc = new RemoteConnection(0,"默认服务(http://localhost:8080)", "http", "localhost", "8080", true, 0);
            tableView.getItems().addAll(rc);
        }
    }

    @FXML
    public void cancel(ActionEvent event) {
        dialog.close();
    }

    @FXML
    public void ok(ActionEvent event) {
        List<RemoteConnection> list = tableView.getItems();
        try {
            H2.batch(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dialog.close();
    }

    public void prepare(Stage dialog) {
        this.dialog = dialog;
        String[] v = {"http", "https"};
        cBoxProtocol.getItems().addAll(v);
        cBoxProtocol.setValue(v[0]);
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        ObservableList<RemoteConnection> data = FXCollections.observableArrayList();
        try {
            List<RemoteConnection> rs = H2.retrieveAll();
            data.addAll(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.setItems(data);
        tableView.setEditable(true);
        //Set the table to multi selection mode
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1){
                changeButtons(tableView.getSelectionModel().getSelectedItem());
            }
        });
//        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//                changeButtons(newSelection);
//                System.out.println("选择");
//            }
//        });

        TableColumn colName= new TableColumn("名称");
        colName.setPrefWidth(300);
        colName.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn colProtocol = new TableColumn("协议");
        colProtocol.setPrefWidth(60);
        colProtocol.setCellValueFactory(new PropertyValueFactory("protocol"));

        TableColumn colHost = new TableColumn("地址");
        colHost.setPrefWidth(100);
        colHost.setCellValueFactory(new PropertyValueFactory("hostName"));

        TableColumn colPort = new TableColumn("端口");
        colPort.setPrefWidth(60);
        colPort.setCellValueFactory(new PropertyValueFactory("port"));

        StringConverter<Boolean> sc = new StringConverter<Boolean>() {
            @Override
            public String toString(Boolean t) {
                if(t) {
                    return "☆";
                }
                return "";
            }

            @Override
            public Boolean fromString(String string) {
                return Boolean.valueOf(string.equals("☆"));
            }
        };

        TableColumn colDefault = new TableColumn("默认");
        colDefault.setCellValueFactory(new PropertyValueFactory("_default"));
        colDefault.setCellFactory(TextFieldTableCell.forTableColumn(sc));
        colDefault.setMinWidth(100);
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        tableView.getStylesheets().add(css);
        tableView.getColumns().addAll(colName, colProtocol, colHost, colPort, colDefault);
        tableView.setEditable(false);
    }

    private void changeButtons(RemoteConnection selected) {
        btnUp.setDisable(false);
        btnDown.setDisable(false);
        if(selected.is_default()) {
            btnRemove.setDisable(true);
            btnDefault.setDisable(true);
            if(selected.getaOrder() == 0) {
                btnUp.setDisable(true);
            } else if(selected.getaOrder() == tableView.getItems().size()-1) {
                btnDown.setDisable(true);
            } else {
                btnUp.setDisable(false);
                btnDown.setDisable(false);
            }
        } else {
            btnRemove.setDisable(false);
            btnDefault.setDisable(false);
            if(selected.getaOrder() == 0) {
                btnUp.setDisable(true);
            } else if(selected.getaOrder() == tableView.getItems().size()-1) {
                btnDown.setDisable(true);
            } else {
                btnUp.setDisable(false);
                btnDown.setDisable(false);
            }
        }

        if(tableView.getItems().size() == 1) {
            btnUp.setDisable(true);
            btnDown.setDisable(true);
        }
    }
}
