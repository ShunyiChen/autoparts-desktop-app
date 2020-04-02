package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Store;
import com.shunyi.autoparts.ui.common.vo.User;
import com.shunyi.autoparts.ui.common.vo.Warehouse;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Description: 仓库选择器Controller
 * @Author: 陈顺谊
 * @CreateDate: 2020/4/2 22:51
 * @Version: 1.0
 */
public class WarehouseChooserController {
    private Stage dialog;
    private Callback<Warehouse, String> callback;
    private Store store;
    private ObservableList<Warehouse> masterData = FXCollections.observableArrayList();
    private ObservableList<Warehouse> filteredData = FXCollections.observableArrayList();

    @FXML
    private TableView<Warehouse> tableView;
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



    @FXML
    private void clear() {
        txtKeyword.clear();
    }

    @FXML
    private void newWarehouse() {
        Callback<Warehouse, String> callback = new Callback<Warehouse, String>() {
            @Override
            public String call(Warehouse warehouse) {

                warehouse.setStore(store);
                String json = GoogleJson.GET().toJson(warehouse);
                try {
                    String idStr = HttpClient.POST("/warehouses", json);
                    warehouse.setId(Long.valueOf(idStr));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                masterData.add(warehouse);
                filteredData.add(warehouse);

//                tableView.getItems().add(warehouse);
//                tableView.refresh();
                tableView.getSelectionModel().select(warehouse);
                return null;
            }
        };

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/system/WarehouseEditor.fxml"
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
        WarehouseEditorController controller = loader.getController();
        controller.prepare(dialog, callback);
        dialog.setTitle("仓库编辑器");
        dialog.setResizable(false);
        dialog.initOwner(this.dialog);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void selectAndReturn() {
        Warehouse selectedWarehouse = tableView.getSelectionModel().getSelectedItem();
        callback.call(selectedWarehouse);
        dialog.close();
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    public void prepare(Stage dialog, Callback<Warehouse, String> callback, Store store) {
        this.dialog = dialog;
        this.callback = callback;
        this.store = store;
        btnSelectAndReturn.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        initTable();
        initInputFields();
    }

    private void initTable() {
        tableView.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        tableView.setId("my-table");
        colCode.setCellValueFactory(new PropertyValueFactory<User, String>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        colNotes.setCellValueFactory(new PropertyValueFactory<User, String>("notes"));
        try {
            Warehouse[] warehouses = HttpClient.GET("/warehouses", Warehouse[].class);
            masterData.addAll(warehouses);
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
            Warehouse selectedWarehouse = tableView.getSelectionModel().getSelectedItem();
            try {
                HttpClient.DELETE("/warehouses/"+selectedWarehouse.getId());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            tableView.getItems().remove(selectedWarehouse);
            tableView.refresh();
        });
        tableView.setContextMenu(menu);
        //回车选中事件
        tableView.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
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

        for (Warehouse p : masterData) {
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
    private boolean matchesFilter(Warehouse warehouse) {
        String filterString = txtKeyword.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }
        if(warehouse.getCode() != null && warehouse.getCode().contains(filterString)) {
            return true;
        } else if(warehouse.getName() != null && warehouse.getName().contains(filterString)) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Warehouse, ?>> sortOrder = new ArrayList(tableView.getSortOrder());
        tableView.getSortOrder().clear();
        tableView.getSortOrder().addAll(sortOrder);
        //默认选择第一行
        if(filteredData.size() > 0) {
            tableView.getSelectionModel().select(0);
        }
    }
}
