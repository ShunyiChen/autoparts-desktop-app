package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Brand;
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
 * @description 品牌选择器Controller
 *
 * @author Shunyi Chen
 * @date 2020/4/11
 */
public class BrandChooserController {
    private Stage dialog;
    private Callback<Brand, String> callback;
    private Brand selectedCar;
    private ObservableList<Brand> masterData = FXCollections.observableArrayList();
    private ObservableList<Brand> filteredData = FXCollections.observableArrayList();

    @FXML
    private TableView<Brand> tableView;
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
     * @param selectedCar
     */
    public void initialize(Stage dialog, Callback<Brand, String> callback, Brand selectedCar) {
        this.dialog = dialog;
        this.callback = callback;
        this.selectedCar = selectedCar;
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
        Brand selectedCar = tableView.getSelectionModel().getSelectedItem();
        callback.call(selectedCar);
        dialog.close();
    }

    @FXML
    private void newBrand() {
        Callback<Brand, String> callback = new Callback<Brand, String>() {
            @Override
            public String call(Brand car) {
                String json = GoogleJson.GET().toJson(car);
                try {
                    String idStr = HttpClient.POST("/brands", json);
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

        openBrandEditor(callback,null);
    }


    private void openBrandEditor(Callback<Brand, String> callback, Brand selectedCar) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/BrandEditor.fxml"
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
        BrandEditorController controller = loader.getController();
        controller.initialize(dialog, callback);
        dialog.setTitle("品牌编辑器");
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
            Brand[] brands = HttpClient.GET("/brands", Brand[].class);
            masterData.addAll(brands);
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
            Brand car = tableView.getSelectionModel().getSelectedItem();
            try {
                HttpClient.DELETE("/brands/"+car.getId());
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
        for (Brand p : masterData) {
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
    private boolean matchesFilter(Brand car) {
        String filterString = txtKeyword.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }
        if(car.getCode() != null && car.getCode().contains(filterString)) {
            return true;
        } else if(car.getName() != null && car.getName().contains(filterString)) {
            return true;
        }
        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Brand, ?>> sortOrder = new ArrayList(tableView.getSortOrder());
        tableView.getSortOrder().clear();
        tableView.getSortOrder().addAll(sortOrder);
        //默认选择第一行
        if(filteredData.size() > 0) {
            tableView.getSelectionModel().select(0);
        }
    }

}
