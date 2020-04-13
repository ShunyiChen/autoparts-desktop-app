package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Import;
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
 * @description 进口选择器Controller
 *
 * @author Shunyi Chen
 * @date 2020/4/13
 */
public class ImportChooserController {
    private Stage dialog;
    private Callback<Import, String> callback;
    private Import anImport;
    private ObservableList<Import> masterData = FXCollections.observableArrayList();
    private ObservableList<Import> filteredData = FXCollections.observableArrayList();

    @FXML
    private TableView<Import> tableView;
    @FXML
    private TableColumn colName;
    @FXML
    private TextField txtKeyword;
    @FXML
    private Button btnSelectAndReturn;

    /**
     *
     * @param dialog
     * @param callback
     * @param anImport
     */
    public void initialize(Stage dialog, Callback<Import, String> callback, Import anImport) {
        this.dialog = dialog;
        this.callback = callback;
        this.anImport = anImport;
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
        Import selectedCar = tableView.getSelectionModel().getSelectedItem();
        callback.call(selectedCar);
        dialog.close();
    }

    @FXML
    private void newImport() {
        Callback<Import, String> callback = new Callback<Import, String>() {
            @Override
            public String call(Import car) {
                String json = GoogleJson.GET().toJson(car);
                try {
                    String idStr = HttpClient.POST("/imports", json);
                    car.setId(Long.valueOf(idStr));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                masterData.add(car);
                filteredData.add(car);
                tableView.getSelectionModel().select(car);
                return null;
            }
        };
        openImportEditor(callback);
    }

    private void openImportEditor(Callback<Import, String> callback) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/ImportEditor.fxml"
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
        ImportEditorController controller = loader.getController();
        controller.initialize(dialog, callback);
        dialog.setTitle("进口编辑器");
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
        colName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        try {
            Import[] imports = HttpClient.GET("/imports", Import[].class);
            masterData.addAll(imports);
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
            Import car = tableView.getSelectionModel().getSelectedItem();
            try {
                HttpClient.DELETE("/imports/"+car.getId());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            tableView.getItems().remove(car);
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
        for (Import p : masterData) {
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
    private boolean matchesFilter(Import anImport) {
        String filterString = txtKeyword.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }
        if(anImport.getName() != null && anImport.getName().contains(filterString)) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Import, ?>> sortOrder = new ArrayList(tableView.getSortOrder());
        tableView.getSortOrder().clear();
        tableView.getSortOrder().addAll(sortOrder);
        //默认选择第一行
        if(filteredData.size() > 0) {
            tableView.getSelectionModel().select(0);
        }
    }

}
