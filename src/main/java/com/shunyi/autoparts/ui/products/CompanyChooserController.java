package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Company;
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
 * @description 公司选择器Controller
 *
 * @author Shunyi Chen
 * @date 2020/4/13
 */
public class CompanyChooserController {
    private Stage dialog;
    private Callback<Company, String> callback;
    private Company company;
    private ObservableList<Company> masterData = FXCollections.observableArrayList();
    private ObservableList<Company> filteredData = FXCollections.observableArrayList();

    @FXML
    private TableView<Company> tableView;
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
     * @param company
     */
    public void initialize(Stage dialog, Callback<Company, String> callback, Company company) {
        this.dialog = dialog;
        this.callback = callback;
        this.company = company;
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
        Company selectedCom = tableView.getSelectionModel().getSelectedItem();
        callback.call(selectedCom);
        dialog.close();
    }

    @FXML
    private void newCompany() {
        Callback<Company, String> callback = new Callback<Company, String>() {
            @Override
            public String call(Company company) {
                String json = GoogleJson.GET().toJson(company);
                try {
                    String idStr = HttpClient.POST("/companies", json);
                    company.setId(Long.valueOf(idStr));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                masterData.add(company);
                filteredData.add(company);
                tableView.getSelectionModel().select(company);
                return null;
            }
        };
        openCompanyEditor(callback);
    }


    private void openCompanyEditor(Callback<Company, String> callback) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/CompanyEditor.fxml"
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
        CompanyEditorController controller = loader.getController();
        controller.initialize(dialog, callback);
        dialog.setTitle("公司编辑器");
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
            Company[] companies = HttpClient.GET("/companies", Company[].class);
            masterData.addAll(companies);
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
            Company company = tableView.getSelectionModel().getSelectedItem();
            try {
                HttpClient.DELETE("/companies/"+company.getId());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            tableView.getItems().remove(company);
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
        for (Company p : masterData) {
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
    private boolean matchesFilter(Company company) {
        String filterString = txtKeyword.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }
        if(company.getCode() != null && company.getCode().contains(filterString)) {
            return true;
        } else if(company.getName() != null && company.getName().contains(filterString)) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Company, ?>> sortOrder = new ArrayList(tableView.getSortOrder());
        tableView.getSortOrder().clear();
        tableView.getSortOrder().addAll(sortOrder);
        //默认选择第一行
        if(filteredData.size() > 0) {
            tableView.getSelectionModel().select(0);
        }
    }

}
