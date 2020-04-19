package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Account;
import com.shunyi.autoparts.ui.common.vo.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @description 账号选择器Controller
 *
 * @author Shunyi Chen
 * @date 2020/4/19
 */
public class AccountChooserController {
    private Stage dialog;
    private Callback<Account, String> callback;
    private ObservableList<Account> masterData = FXCollections.observableArrayList();
    private ObservableList<Account> filteredData = FXCollections.observableArrayList();

    @FXML
    private TableView<Account> tableView;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colNotes;
    @FXML
    private TextField txtKeyword;
    @FXML
    private Button btnSelectAndReturn;

    /**
     *
     * @param dialog
     * @param callback
     */
    public void initialize(Stage dialog, Callback<Account, String> callback) {
        this.dialog = dialog;
        this.callback = callback;
        btnSelectAndReturn.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        initTable();
        initInputFields();
    }

    @FXML
    private void clear() { txtKeyword.clear(); }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void selectAndReturn() {
        Account selectedAccount = tableView.getSelectionModel().getSelectedItem();
        callback.call(selectedAccount);
        dialog.close();
    }

    @FXML
    private void newAccount() {
        Callback<Account, String> callback = new Callback<Account, String>() {
            @Override
            public String call(Account place) {
                String json = GoogleJson.GET().toJson(place);
                try {
                    String idStr = HttpClient.POST("/accounts", json);
                    place.setId(Long.valueOf(idStr));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                masterData.add(place);
                filteredData.add(place);
                tableView.getSelectionModel().select(place);
                return null;
            }
        };
        openAccountEditor(callback);
    }


    private void openAccountEditor(Callback<Account, String> callback) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/AccountEditor.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        AccountEditorController controller = loader.getController();
        controller.initialize(dialog, callback);
        dialog.setTitle("账号编辑器");
        dialog.setResizable(false);
        dialog.initOwner(this.dialog);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    private void initTable() {
        tableView.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        tableView.setId("my-table");
        colCode.setCellValueFactory(new PropertyValueFactory<User, String>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        colNotes.setCellValueFactory(new PropertyValueFactory<User, String>("notes"));
        try {
            Account[] accounts = HttpClient.GET("/accounts", Account[].class);
            masterData.addAll(accounts);
            // Initially add all data to filtered data
            filteredData.addAll(masterData);

            // Add filtered data to the table
            tableView.setItems(filteredData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ContextMenu menu = new ContextMenu();
        MenuItem itemDel = new MenuItem("删 除");
        menu.getItems().add(itemDel);
        itemDel.setOnAction(e -> {
            Account account = tableView.getSelectionModel().getSelectedItem();
            try {
                HttpClient.DELETE("/accounts/"+account.getId());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            tableView.getItems().remove(account);
            tableView.refresh();
        });
        tableView.setContextMenu(menu);
        //回车选中事件
        tableView.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                selectAndReturn();
            }
        });
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                selectAndReturn();
            }
        });
    }

    private void initInputFields() {
        // Listen for text changes in the filter text field
        txtKeyword.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                updateFilteredData();
            }
        });
        txtKeyword.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                tableView.requestFocus();
            }
        });
    }

    /**
     * Updates the filteredData to contain all data from the masterData that
     * matches the current filter.
     */
    private void updateFilteredData() {
        filteredData.clear();
        for (Account p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    /**
     * Returns true if the person matches the current filter. Lower/Upper case
     * is ignored.
     *
     * @return
     */
    private boolean matchesFilter(Account place) {
        String filterString = txtKeyword.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }
        if(place.getCode() != null && place.getCode().contains(filterString)) {
            return true;
        } else if(place.getName() != null && place.getName().contains(filterString)) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Account, ?>> sortOrder = new ArrayList(tableView.getSortOrder());
        tableView.getSortOrder().clear();
        tableView.getSortOrder().addAll(sortOrder);
        //默认选择第一行
        if(filteredData.size() > 0) {
            tableView.getSelectionModel().select(0);
        }
    }

}
